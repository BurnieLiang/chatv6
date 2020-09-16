package com.github.BurnieLiang.chatv6.service.impl;

import cn.hutool.extra.qrcode.BufferedImageLuminanceSource;
import com.github.BurnieLiang.chatv6.service.QRCodeService;
import com.github.BurnieLiang.chatv6.service.config.MatrixToLogoImageConfig;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Map;

/**
 * @author Boning Liang
 * @date 2020-09-16 13:43:53
 */
@Slf4j
@Service("qrCodeService")
public class QRCodeServiceImpl implements QRCodeService {

    @Override
    public BufferedImage qrCode(String data) {
        return qrCode(data, 800);
    }

    @Override
    public BufferedImage qrCode(String data, int length) {
        try {
            BitMatrix bitMatrix = createQRCodeMatrix(data, length);
            return toBufferedImage(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据内容生成二维码数据
     *
     * @param content 二维码文字内容[为了信息安全性，一般都要先进行数据加密]
     * @param length  二维码图片宽度和高度
     */
    private BitMatrix createQRCodeMatrix(String content, int length) {
        Map<EncodeHintType, Object> hints = Maps.newEnumMap(EncodeHintType.class);
        // 设置字符编码
        hints.put(EncodeHintType.CHARACTER_SET, Charsets.UTF_8.name());
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        try {
            return new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, length, length, hints);
        } catch (Exception e) {
            log.warn("内容为：【" + content + "】的二维码生成失败！", e);
            return null;
        }
    }

    /**
     * 根据指定边长创建生成的二维码，允许配置logo属性
     *
     * @param content  二维码内容
     * @param length   二维码的高度和宽度
     * @param logoFile logo 文件对象，可以为空
     * @param logoConfig logo配置，可设置logo展示长宽，边框颜色
     * @return 二维码图片的字节数组
     */
    public byte[] createQRCode(String content, int length, File logoFile, MatrixToLogoImageConfig logoConfig) {
        if (logoFile != null && !logoFile.exists()) {
            throw new IllegalArgumentException("请提供正确的logo文件！");
        }

        BitMatrix qrCodeMatrix = createQRCodeMatrix(content, length);
        if (qrCodeMatrix == null) {
            return null;
        }
        try {
            // 生成二维码的格式
            String FORMAT = "jpg";
            File file = Files.createTempFile("qrcode_", "." + FORMAT).toFile();
            log.debug(file.getAbsolutePath());

            writeToFile(qrCodeMatrix, FORMAT, file);
            if (logoFile != null) {
                // 添加logo图片, 此处一定需要重新进行读取，而不能直接使用二维码的BufferedImage 对象
                BufferedImage img = ImageIO.read(file);
                overlapImage(img, FORMAT, file.getAbsolutePath(), logoFile, logoConfig);
            }

            return toByteArray(file);
        } catch (Exception e) {
            log.warn("内容为：【" + content + "】的二维码生成失败！", e);
            return null;
        }
    }

    /**
     * 根据指定边长创建生成的二维码
     *
     * @param content  二维码内容
     * @param length   二维码的高度和宽度
     * @param logoFile logo 文件对象，可以为空
     * @return 二维码图片的字节数组
     */
    public byte[] createQRCode(String content, int length, File logoFile) {
        return  createQRCode(content, length, logoFile, new MatrixToLogoImageConfig());
    }

    /**
     * 创建生成默认高度(400)的二维码图片
     * 可以指定是否贷logo
     *
     * @param content  二维码内容
     * @param logoFile logo 文件对象，可以为空
     * @return 二维码图片的字节数组
     */
    public byte[] createQRCode(String content, File logoFile) {
        // 生成二维码的默认边长，因为是正方形的，所以高度和宽度一致
        int DEFAULT_LENGTH = 400;
        return createQRCode(content, DEFAULT_LENGTH, logoFile);
    }

    /**
     * 将文件转换为字节数组，
     * 使用MappedByteBuffer，可以在处理大文件时，提升性能
     *
     * @param file 文件
     * @return 二维码图片的字节数组
     */
    private byte[] toByteArray(File file) {
        try (FileChannel fc = new RandomAccessFile(file, "r").getChannel();) {
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).load();
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (Exception e) {
            log.warn("文件转换成byte[]发生异常！", e);
            return null;
        }
    }

    /**
     * 将logo添加到二维码中间
     *
     * @param image     生成的二维码图片对象
     * @param imagePath 图片保存路径
     * @param logoFile  logo文件对象
     * @param format    图片格式
     */
    private void overlapImage(BufferedImage image, String format, String imagePath, File logoFile,
                                     MatrixToLogoImageConfig logoConfig) throws IOException {
        try {
            BufferedImage logo = ImageIO.read(logoFile);
            Graphics2D g = image.createGraphics();
            // 考虑到logo图片贴到二维码中，建议大小不要超过二维码的1/5;
            int width = image.getWidth() / logoConfig.getLogoPart();
            int height = image.getHeight() / logoConfig.getLogoPart();
            // logo起始位置，此目的是为logo居中显示
            int x = (image.getWidth() - width) / 2;
            int y = (image.getHeight() - height) / 2;
            // 绘制图
            g.drawImage(logo, x, y, width, height, null);

            // 给logo画边框
            // 构造一个具有指定线条宽度以及 cap 和 join 风格的默认值的实心 BasicStroke
            g.setStroke(new BasicStroke(logoConfig.getBorder()));
            g.setColor(logoConfig.getBorderColor());
            g.drawRect(x, y, width, height);

            g.dispose();
            // 写入logo图片到二维码
            ImageIO.write(image, format, new File(imagePath));
        } catch (Exception e) {
            throw new IOException("二维码添加logo时发生异常！", e);
        }
    }

    /**
     * 解析二维码
     *
     * @param file 二维码文件内容
     * @return 二维码的内容
     */
    public String decodeQRCode(File file) throws IOException, NotFoundException {
        BufferedImage image = ImageIO.read(file);
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Map<DecodeHintType, Object> hints = Maps.newEnumMap(DecodeHintType.class);
        hints.put(DecodeHintType.CHARACTER_SET, Charsets.UTF_8.name());
        return new MultiFormatReader().decode(binaryBitmap, hints).getText();
    }


    private BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int BLACK = 0xFF000000;
                int WHITE = 0xFFFFFFFF;
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }

        return image;
    }

    public void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    public void writeToStream(BitMatrix matrix, String format, OutputStream stream)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }


}

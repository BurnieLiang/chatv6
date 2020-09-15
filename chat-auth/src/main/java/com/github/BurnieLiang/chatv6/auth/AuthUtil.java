package com.github.BurnieLiang.chatv6.auth;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import com.github.BurnieLiang.chatv6.core.Constant;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
//import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.symmetric.AES;
//import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Boning Liang
 * @date 2020-09-14 10:54:30
 */
//@Slf4j
@UtilityClass
public class AuthUtil {

    @SneakyThrows
    public static String decryptAES(String data, String pass) {
        byte[] result = decryptAesBytes(Base64.decode(data.getBytes(StandardCharsets.UTF_8)), pass.getBytes());
        return new String(result, StandardCharsets.UTF_8).trim();
    }


    @SneakyThrows
    public static String encryptAES(String data, String pass) {
        byte[] result = encryptAesBytes(data.getBytes(StandardCharsets.UTF_8), pass.getBytes());
        return Base64.encodeUrlSafe(result);
    }


    private static byte[] decryptAesBytes(byte[] data, byte[] pass) {
        AES aes = new AES(Mode.CBC, Padding.NoPadding,
                new SecretKeySpec(pass, Constant.AES_ALGORITHM),
                new IvParameterSpec(pass));
        byte[] result = aes.decrypt(data);
        return result;
    }


    private static byte[] encryptAesBytes(byte[] data, byte[] pass) {
        AES aes = new AES(Mode.CBC, Padding.NoPadding,
                new SecretKeySpec(pass, Constant.AES_ALGORITHM),
                new IvParameterSpec(pass));
        int len = data.length % 16 == 0 ? data.length : (data.length / 16 + 1) * 16;
        byte[] paddingdata = new byte[len];
        System.arraycopy(data, 0, paddingdata, 0, data.length);
        byte[] result = aes.encrypt(paddingdata);
        return result;
    }

}

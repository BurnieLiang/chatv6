package com.github.BurnieLiang.chatv6.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.BurnieLiang.chatv6.util.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Boning Liang
 * @date 2020-09-11 15:21:12
 */
//@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Message {

    /**
     * 编号
     */
    private String messageId;

    /**
     * 消息接收方的公钥信息
     */
    private PublicKey recipient;

    /**
     * 消息发送方的公钥信息
     */
    private PublicKey sender;

    /**
     * 签名
     */
    private byte[] signature;

    /**
     * 发送方客户端的版本
     */
    private String version;

    /**
     * 生成时间，包含发送方的时区
     */
    private ZonedDateTime createdDateTime;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息类型
     *  - text 文本信息
     *  - compressedFile 压缩文件
     *  - video 视频
     *  -
     *  - voice 音频
     *  - picture 图片
     *  - document
     *  - others
     *  - source
     */
    private String type;

    /**
     * 文件扩展名
     *  - compressedFile 压缩文件
     *      - zip
     *      - rar
     *  - video 视频
     *      - mp4
     *      - avi
     *      - mov
     *  - voice 音频
     *      - mp3
     *      -
     *  - picture 图片
     *      - gif
     *      - png
     *      - jpeg
     *      - jpg
     *      - svg
     *  - adobe
     *      - psd
     *      - ai
     *  - document
     *      - pdf
     *      - doc
     *      - ppt
     *      - pptx
     *      - xls
     *      - xlsx
     *  - source
     *      - .m        (Objective-C)
     *      - .c        (C)
     *      - .h        (C, Objective-C)
     *      - .java     (JAVA)
     *      - .cs       (C#)
     *      - .cpp      (C++)
     *      - .json     (JSON)
     *      - .xml      (XML)
     *      - .txt      (Plain Text)
     *      - .py       (Python)
     *      - .sql      (SQL)
     *      - .matlab   (MATLAB)
     *      - .scala    (SCALA)
     *      - .yaml     (YAML)
     *      - .yml      (YAML)
     *      - .css      (CSS)
     *      - .lua      (LUA)
     *      - .php      (PHP)
     *      - .js       (JavaScript)
     *      - .ts       (TypeScript)
     */
    private String fileExt;

    /**
     * 文件唯一id
     */
    private String fileId;

    /**
     * 文件名(不包含扩展名)
     */
    private String fileName;

    /**
     * 大致统计发送/接收了多少条信息
     */
    private static long sequence = 0;


    public Message() {

    }


    public Message(PublicKey recipient,
                   PublicKey sender,
                   String content,
                   PrivateKey privateKey) {
        this.recipient = recipient;
        this.sender = sender;
        if (content == null) {
            content = "";
        }
        this.content = content;
        this.messageId = calculateHash();
        generateSignature(privateKey);
        this.createdDateTime = ZonedDateTime.now(ZoneId.systemDefault());
    }


    private String calculateHash() {
        sequence++;
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(sender) +
                        StringUtil.getStringFromKey(recipient) +
                        content.length() + sequence
        );
    }

    private void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender) +
                StringUtil.getStringFromKey(recipient)
                + content.length();
        this.signature = StringUtil.applyECDSASig(privateKey, data);
    }

    //Verifies the data we signed hasnt been tampered with
    public boolean verifySignature() {
        String data = StringUtil.getStringFromKey(sender)
                + StringUtil.getStringFromKey(recipient)
                + content.length();
        return StringUtil.verifyECDSASig(sender, data, signature);
    }


}

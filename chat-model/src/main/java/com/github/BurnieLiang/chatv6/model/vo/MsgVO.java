package com.github.BurnieLiang.chatv6.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;

/**
 * @author Boning Liang
 * @date 2020-09-11 15:21:12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MsgVO {

    /**
     * 客户端的版本
     */
    private String version;

    /**
     * 生成时间，记录了生成方的时区
     */
    private ZonedDateTime createdDateTime;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息类型
     *  - text 文本信息
     *  - file 文件
     *  - video 视频
     *  - picture 图片
     */
    private String type;

    /**
     * 文件扩展名
     */
    private String fileExt;


    private MultipartFile file;

//    public String getContent() {
//        return new String(Base64.decode(content.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8).trim();
//    }
//
//    public void setContent(String content) {
//        this.content = Base64.encodeUrlSafe(content);
//    }

}

package com.github.BurnieLiang.chatv6.service;

import java.awt.image.BufferedImage;

/**
 * @author Boning Liang
 * @date 2020-09-16 13:40:37
 */
public interface QRCodeService {

    BufferedImage qrCode(String data);

    BufferedImage qrCode(String data, int length);

}

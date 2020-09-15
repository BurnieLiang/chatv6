package com.github.BurnieLiang.chatv6.model.entity;

import cn.hutool.core.codec.Base64;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Boning Liang
 * @date 2020-09-15 09:43:55
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Account {

    /**
     * 私钥
     */
    private PrivateKey privateKey;

    /**
     * 公钥
     */
    private PublicKey publicKey;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;


    public Account() {
        generateKeyPair();
    }

    public void setPrivateKey(String privateKey) {
        try {
            byte[] keyBytes = Base64.decode(privateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.privateKey = keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setPublicKey(String publicKey) {

        try {
            byte[] keyBytes = Base64.decode(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.publicKey = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String getPrivateKey() {
        return Base64.encode(this.privateKey.getEncoded());
    }

    public String getPublicKey() {
        return Base64.encode(this.publicKey.getEncoded());
    }

    public String getNickname() {
        if (nickname == null) {
            return username;
        }
        return nickname;
    }

    private void generateKeyPair() {
        try {
            int keySize = 1408;
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(new RSAKeyGenParameterSpec(keySize, RSAKeyGenParameterSpec.F0));
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    private void generateKeyPair() {
//        try {
//            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
//            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
//            // Initialize the key generator and generate a KeyPair
//            keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
//            KeyPair keyPair = keyGen.generateKeyPair();
//            // Set the public and private keys from the keyPair
//            this.privateKey = keyPair.getPrivate();
//            this.publicKey = keyPair.getPublic();
//        }catch(Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

}

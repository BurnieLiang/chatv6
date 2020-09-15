package com.github.BurnieLiang.chatv6.auth;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

/**
 * @author Boning Liang
 * @date 2020-09-15 09:17:46
 */
public class Wallet {

    public PrivateKey privateKey;

    public PublicKey publicKey;


    public Wallet() {
        generateKeyPair();
    }


    public void generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            keyPairGenerator.initialize(ecSpec, random);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

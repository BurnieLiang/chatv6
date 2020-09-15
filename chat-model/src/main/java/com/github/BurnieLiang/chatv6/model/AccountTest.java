package com.github.BurnieLiang.chatv6.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.BurnieLiang.chatv6.model.entity.Account;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.security.Security;

/**
 * @author Boning Liang
 * @date 2020-09-15 14:01:51
 */
public class AccountTest {

    public static void main(String[] args) throws IOException {
        Security.addProvider(new BouncyCastleProvider());
        Account account = new Account();
        account.setUsername("test");
        ObjectMapper objectMapper = new ObjectMapper();
        String accountString = objectMapper.writeValueAsString(account);
        System.out.println(accountString);

        Account account1 = objectMapper.readValue(accountString, Account.class);
        System.out.println(account1);
    }

}

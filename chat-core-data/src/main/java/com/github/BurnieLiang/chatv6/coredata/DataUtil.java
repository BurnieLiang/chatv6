package com.github.BurnieLiang.chatv6.coredata;

import com.github.BurnieLiang.chatv6.model.entity.Account;
import com.github.BurnieLiang.chatv6.model.entity.Friend;
import com.github.BurnieLiang.chatv6.model.entity.Message;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.MMapDirectory;

import java.io.IOException;
import java.nio.file.FileSystems;

/**
 * @author Boning Liang
 * @date 2020-09-14 11:36:37
 */
public class DataUtil {

    public static boolean storeMessage(Message message, String username) {
        String indexPath = "coredata/" + username + "/msg";
        IndexWriter indexWriter = indexWriter(indexPath);

        try {
            Document document = new Document();

            document.add(new StringField("messageId", message.getMessageId(), Field.Store.YES));
//            document.add(new TextField("nickname", account.getNickname(), Field.Store.YES));
//            document.add(new StoredField("password", account.getPassword()));
//            document.add(new StoredField("privateKey", account.getPrivateKey()));
//            document.add(new StoredField("publicKey", account.getPublicKey()));

            indexWriter.addDocument(document);

            if (indexWriter.isOpen()) {
                indexWriter.commit();
                indexWriter.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean storeAccount(Account account) {

        String indexPath = "coredata/" + account.getUsername() + "/account";
        IndexWriter indexWriter = indexWriter(indexPath);

        try {
            Document document = new Document();

            document.add(new StringField("username", account.getUsername(), Field.Store.YES));
            document.add(new TextField("nickname", account.getNickname(), Field.Store.YES));
            document.add(new StoredField("password", account.getPassword()));
            document.add(new StoredField("privateKey", account.getPrivateKey()));
            document.add(new StoredField("publicKey", account.getPublicKey()));

            indexWriter.addDocument(document);

            if (indexWriter.isOpen()) {
                indexWriter.commit();
                indexWriter.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean storeFriend(Friend friend, String username) {
        try {
            String indexPath = "coredata/" + username + "/friends";
            IndexWriter indexWriter = indexWriter(indexPath);


            Document document = new Document();

            document.add(new StringField("aliasName", friend.getAliasName(), Field.Store.YES));
            document.add(new StringField("nickname", friend.getNickname(), Field.Store.YES));

            indexWriter.addDocument(document);

            if (indexWriter.isOpen()) {
                indexWriter.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static IndexWriter indexWriter(String indexPath) {
        IndexWriter indexWriter = null;
        try {
            MMapDirectory directory = new MMapDirectory(FileSystems.getDefault().getPath(indexPath));
            StandardAnalyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            indexWriter = new IndexWriter(directory, config);
        } catch (IOException e) {
//            log.error("error");
        }
        return indexWriter;
    }


    public static void main(String[] args) throws IOException {
        Account account1 = new Account();
        account1.setUsername("test1");
        account1.setNickname("nickname1");
        account1.setPassword("password1");

        Account account2 = new Account();
        account2.setUsername("test2");
        account2.setNickname("nickname2");
        account2.setPassword("password2");

        DataUtil.storeAccount(account1);
        DataUtil.storeAccount(account2);


        Friend friend = new Friend();
        friend.setFriendsUsername("test");
        friend.setNickname("nickname");
        friend.setAliasName("alias");
        DataUtil.storeFriend(friend, account1.getUsername());
//        dataService.storeFriend(friend);

    }

}

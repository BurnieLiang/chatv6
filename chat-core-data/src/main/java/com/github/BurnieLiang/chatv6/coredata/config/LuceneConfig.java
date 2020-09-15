package com.github.BurnieLiang.chatv6.coredata.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.MMapDirectory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileSystems;

/**
 * @author Boning Liang
 * @date 2020-09-14 11:37:17
 */
@Component
//@Slf4j
public class LuceneConfig {

    @Bean
    public IndexWriter accountWriter() {
        return indexWriter("coredata/account");
    }

    @Bean
    public IndexWriter friendsWriter() {
        return indexWriter("coredata/friends");
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

}

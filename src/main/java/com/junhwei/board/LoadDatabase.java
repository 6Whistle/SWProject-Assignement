package com.junhwei.board;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(InfoRepository repository){
        return args -> {
            for(int i =0; i < 100; i++){
                Date now = new Date();
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

                log.info("Preloading" + repository.save(new Info(i+"count title",
                        i+"count text", date.format(now), 0)));
            }
        };
    }
}

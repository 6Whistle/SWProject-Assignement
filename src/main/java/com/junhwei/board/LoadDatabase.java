package com.junhwei.board;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;

//Database
@Configuration
public class LoadDatabase {
    //Log
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    //initialing repository(Bean)
    @Bean
    CommandLineRunner initDatabase(InfoRepository repository){
        return args -> {
            for(int i =0; i < 100; i++){        //100 of initial data
                Date now = new Date();          //get current date
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");  //save date format
                //Save data in repository and print log
                log.info("Preloading" + repository.save(new Info(i+" count title",
                        i+" count text", date.format(new Date(now.getTime() - (99 - i) * 10000000)), (99 - i))));
            }
        };
    }
}

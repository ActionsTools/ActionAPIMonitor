package com.jdqc.aam;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Log4j2
@Profile("auto")
@Component
public class AutoExecutor {

    @PostConstruct
    void execute(){
        log.info("Starting Application");

        log.info("Closing Application");
    }
}

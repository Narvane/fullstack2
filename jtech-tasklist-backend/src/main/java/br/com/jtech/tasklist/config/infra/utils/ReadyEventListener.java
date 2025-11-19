package br.com.jtech.tasklist.config.infra.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ReadyEventListener {


    @Value("${application.environment:LOCAL}")
    private String environment;

    @EventListener(ApplicationReadyEvent.class)
    public void start(ApplicationReadyEvent event) {
        log.info(">>> Connector Ready");
        show();
    }

    private void show() {
        log.info("======================================================");
        log.info("== Execute mode:.....................'{}'", environment);
        log.info("======================================================");
    }
}
package com.flowdesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlowdeskApplication {
    public static void main(String[] args) { SpringApplication.run(FlowdeskApplication.class, args); }
}

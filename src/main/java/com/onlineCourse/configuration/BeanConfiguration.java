package com.onlineCourse.configuration;


import com.onlineCourse.service.impl.EmailServiceImpl;
import com.onlineCourse.service.interfaces.EmailService;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.mail.javamail.JavaMailSender;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class BeanConfiguration {
    @Autowired
    private JavaMailSender javaMailSender;

    @Bean
    public EmailService emailService(){
        log.info("Creating EmailService Bean..! JavaMailSender={}", javaMailSender);
        return new EmailServiceImpl(javaMailSender);
    }

}

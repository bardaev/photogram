package com.my.photogram.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@Slf4j
public class UtilsBean {

    @Bean(name = "logo")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public byte[] logo() {
        try {
            File file = ResourceUtils.getFile("classpath:static/instagram.png");
            FileInputStream in = new FileInputStream(file);
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            return buffer;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}

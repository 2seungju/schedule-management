package com.example.schedulemanagement.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
class DBConfiguration {
    lateinit var driverClassName: String;
    lateinit var url: String;
    lateinit var username: String;
    lateinit var password: String;

    @Bean
    fun dataSource(): DataSource {
        val dataSource: DataSource = DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();

        return dataSource;
    }
}
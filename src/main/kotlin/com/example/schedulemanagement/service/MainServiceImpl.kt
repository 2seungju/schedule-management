package com.example.schedulemanagement.service

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class MainServiceImpl(dataSource: DataSource) : MainService {
    private val jdbcTemplate = JdbcTemplate(dataSource);

    override fun getUser(): String {
        val sql = "SELECT name FROM temp WHERE id = 1";

        return jdbcTemplate.queryForObject(sql, String::class.java) ?: "failed";
    }
}
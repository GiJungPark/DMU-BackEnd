package com.dmforu.admin

import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(
    scanBasePackages = [
        "com.dmforu.admin",
        "com.dmforu.fcm",
        "com.dmforu.storage.db.mongo",
        "com.dmforu.storage.db.mysql"
    ]
)
@EnableScheduling
class AdminApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(AdminApplication::class.java)
        .web(WebApplicationType.NONE)
        .run(*args)
}

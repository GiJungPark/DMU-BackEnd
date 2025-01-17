package com.dmforu.api.controller.v1

import com.dmforu.api.ControllerTestSupport
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class DietControllerTest : ControllerTestSupport() {

    @DisplayName("식단을 불러온다.")
    @Test
    fun readDiet() {
        mockMvc.perform(
            get("/api/v1/cafeteria")
                .contentType(APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").isArray)
    }
}
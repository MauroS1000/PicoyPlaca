package com.mauro.picoyplaca.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mauro.picoyplaca.service.PicoPlacaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PicoPlacaController.class)
class PicoPlacaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PicoPlacaService picoPlacaService;

    @Test
    void healthDevuelveOkParaProbesDeKubernetes() throws Exception {
        mockMvc.perform(get("/api/health"))
            .andExpect(status().isOk())
            .andExpect(content().string("ok"));
    }
}

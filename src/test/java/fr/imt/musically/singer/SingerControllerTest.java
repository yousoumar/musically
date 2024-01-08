package fr.imt.musically.singer;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class SingerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllSingers() throws Exception {
        mockMvc.perform(get("/api/singers"))
                .andExpect(status().isOk());
    }

    @Test
    void createSinger() throws Exception {
        mockMvc.perform(post("/api/singers")
                .contentType("application/json")
                .content("{\"firstName\":\"Jana\",\"lastName\":\"Zebian\"}"))
            .andExpect(status().isOk());
    }

}
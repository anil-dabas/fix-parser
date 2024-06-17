package com.fix.parser.unittest;

import com.fix.parser.controller.ParserController;
import com.fix.parser.model.FixMessage;
import com.fix.parser.service.ParserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParserController.class)
public class ParserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParserService parserService;

    @Test
    public void parseFixMessage_shouldReturnParsedMessage() throws Exception {
        // Given
        FixMessage fixMessage = new FixMessage();
        fixMessage.addField("8", "FIX.4.2");
        fixMessage.addField("9", "72");
        fixMessage.addField("35", "A");
        fixMessage.addField("49", "BRKR");
        fixMessage.addField("56", "INVMGR");
        fixMessage.addField("34", "239");
        fixMessage.addField("52", "19980604-07:58:28");
        fixMessage.addField("98", "0");
        fixMessage.addField("108", "30");
        fixMessage.addField("10", "139");


        Mockito.when(parserService.parse(any(byte[].class))).thenReturn(fixMessage);

        // When & Then
        mockMvc.perform(post("/api/fix/parse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[56, 61, 70, 73, 88, 46, 52, 46, 50, 1, 57, 61, 55, 50, 1, 51, 53, 61, 65, 1, 52, 57, 61, 66, 82, 75, 82, 1, 53, 54, 61, 73, 78, 86, 77, 71, 82, 1, 51, 52, 61, 50, 51, 57, 1, 53, 50, 61, 49, 57, 57, 56, 48, 54, 48, 52, 45, 48, 55, 58, 53, 56, 58, 50, 56, 1, 57, 56, 61, 48, 1, 49, 48, 56, 61, 51, 48, 1, 49, 48, 61, 49, 51, 57, 1]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fields['9']").value(fixMessage.getField("9")))
                .andExpect(jsonPath("$.fields['35']").value(fixMessage.getField("35")))
                .andExpect(jsonPath("$.fields['49']").value(fixMessage.getField("49")));
    }
}

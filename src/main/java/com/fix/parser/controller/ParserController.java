package com.fix.parser.controller;

import com.fix.parser.model.FixMessage;
import com.fix.parser.service.ParserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fix")
public class ParserController {

    private final ParserService fixParser;

    public ParserController(ParserService fixParser) {
        this.fixParser = fixParser;
    }

    @PostMapping("/parse")
    public FixMessage parseFixMessage(@RequestBody List<Integer> message) {
        // Convert List<Integer> to byte[]
        byte[] msg = new byte[message.size()];
        for (int i = 0; i < message.size(); i++) {
            msg[i] = message.get(i).byteValue();
        }
        return fixParser.parse(msg);
    }
}

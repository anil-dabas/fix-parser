package com.fix.parser.service;

import com.fix.parser.model.FixMessage;
import org.springframework.stereotype.Service;

@Service
public class ParserService {

    public FixMessage parse(byte[] msg) {
        FixMessage fixMessage = new FixMessage();
        int start = 0;
        int length = msg.length;
        for (int i = 0; i < length; i++) {
            if (msg[i] == '=') {
                String tag = new String(msg, start, i - start);
                start = i + 1;
                while (i < length && msg[i] != '\u0001') {
                    i++;
                }
                String value = new String(msg, start, i - start);
                fixMessage.addField(tag, value);
                start = i + 1;
            }
        }
        return fixMessage;
    }

}

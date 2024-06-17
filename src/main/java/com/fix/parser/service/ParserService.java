package com.fix.parser.service;

import com.fix.parser.model.FixMessage;
import org.springframework.stereotype.Service;

@Service
public class ParserService {

    public FixMessage parse(byte[] msg) {
        FixMessage fixMessage = new FixMessage();
        int start = 0;
        int length = msg.length;

        while (start < length) {
            int equalsIndex = -1;
            int sohIndex = -1;

            // Find '=' and SOH within the segment
            for (int i = start; i < length; i++) {
                if (msg[i] == '=') {
                    equalsIndex = i;
                } else if (msg[i] == '\u0001') {
                    sohIndex = i;
                    break;
                }
            }

            // If both '=' and SOH are found in the correct order, parse the field
            if (equalsIndex != -1 && sohIndex != -1 && equalsIndex < sohIndex) {
                String tag = new String(msg, start, equalsIndex - start);
                String value = new String(msg, equalsIndex + 1, sohIndex - equalsIndex - 1);
                fixMessage.addField(tag, value);
                start = sohIndex + 1; // Move to the next field
            } else {
                break; // Invalid segment, exit parsing
            }
        }

        return fixMessage;
    }
}

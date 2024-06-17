package com.fix.parser.unittest;

import com.fix.parser.model.FixMessage;
import com.fix.parser.service.ParserService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserServiceTest {

    @Test
    public void testParseValidMessage() {
        ParserService parser = new ParserService();
        String fixMessageStr = "8=FIX.4.2\u00019=178\u000135=D\u000149=BRKR\u000156=INVMGR\u000134=4\u000152=20030615-19:11:01.000\u000111=ID12345\u000121=1\u000155=MSFT\u000154=1\u000160=20030615-19:11:01.000\u000138=100\u000140=2\u000144=20.00\u000110=220\u0001";
        byte[] fixMessageBytes = fixMessageStr.getBytes();

        FixMessage parsedMessage = parser.parse(fixMessageBytes);

        assertEquals("FIX.4.2", parsedMessage.getFields().get("8"));
        assertEquals("178", parsedMessage.getFields().get("9"));
        assertEquals("D", parsedMessage.getFields().get("35"));
        assertEquals("BRKR", parsedMessage.getFields().get("49"));
        assertEquals("INVMGR", parsedMessage.getFields().get("56"));
        assertEquals("4", parsedMessage.getFields().get("34"));
        assertEquals("20030615-19:11:01.000", parsedMessage.getFields().get("52"));
        assertEquals("ID12345", parsedMessage.getFields().get("11"));
        assertEquals("1", parsedMessage.getFields().get("21"));
        assertEquals("MSFT", parsedMessage.getFields().get("55"));
        assertEquals("1", parsedMessage.getFields().get("54"));
        assertEquals("20030615-19:11:01.000", parsedMessage.getFields().get("60"));
        assertEquals("100", parsedMessage.getFields().get("38"));
        assertEquals("2", parsedMessage.getFields().get("40"));
        assertEquals("20.00", parsedMessage.getFields().get("44"));
        assertEquals("220", parsedMessage.getFields().get("10"));
    }

    @Test
    public void testParseEmptyMessage() {
        ParserService parser = new ParserService();
        byte[] fixMessageBytes = new byte[0];

        FixMessage parsedMessage = parser.parse(fixMessageBytes);
        assertTrue(parsedMessage.getFields().isEmpty());
    }

    @Test
    public void testParseMessageWithMissingSOH() {
        ParserService parser = new ParserService();
        String fixMessageStr = "8=FIX.4.29=17835=D49=BRKR56=INVMGR34=452=20030615-19:11:01.00011=ID1234521=155=MSFT54=160=20030615-19:11:01.00038=10040=244=20.0010=220";
        byte[] fixMessageBytes = fixMessageStr.getBytes();

        FixMessage parsedMessage = parser.parse(fixMessageBytes);
        // Verify that the parsing stops at the first missing SOH
        assertNull(parsedMessage.getFields().get("35"));
    }

    @Test
    public void testParseMessageWithMissingEquals() {
        ParserService parser = new ParserService();
        String fixMessageStr = "8FIX.4.2\u00019=178\u000135D\u000149=BRKR\u000156=INVMGR\u000134=4\u000152=20030615-19:11:01.000\u000111=ID12345\u000121=1\u000155=MSFT\u000154=1\u000160=20030615-19:11:01.000\u000138=100\u000140=2\u000144=20.00\u000110=220\u0001";
        byte[] fixMessageBytes = fixMessageStr.getBytes();

        FixMessage parsedMessage = parser.parse(fixMessageBytes);

        // Verify that the parsing stops at the first missing equals
        assertNull(parsedMessage.getFields().get("8"));
        assertNull(parsedMessage.getFields().get("35"));
    }

    @Test
    public void testParseMessageWithMultipleSohAndEquals() {
        ParserService parser = new ParserService();
        String fixMessageStr = "8=FIX.4.2\u00019=178\u000135=D\u000149=BRKR\u000156=INVMGR\u000134=4\u000152=20030615-19:11:01.000\u000111=ID12345\u000121=1\u000155=MSFT\u000154=1\u000160=20030615-19:11:01.000\u000138=100\u000140=2\u000144=20.00\u000110=220\u0001";
        byte[] fixMessageBytes = fixMessageStr.getBytes();

        FixMessage parsedMessage = parser.parse(fixMessageBytes);

        // Check if all fields are parsed correctly
        assertEquals("FIX.4.2", parsedMessage.getFields().get("8"));
        assertEquals("178", parsedMessage.getFields().get("9"));
        assertEquals("D", parsedMessage.getFields().get("35"));
        assertEquals("BRKR", parsedMessage.getFields().get("49"));
        assertEquals("INVMGR", parsedMessage.getFields().get("56"));
        assertEquals("4", parsedMessage.getFields().get("34"));
        assertEquals("20030615-19:11:01.000", parsedMessage.getFields().get("52"));
        assertEquals("ID12345", parsedMessage.getFields().get("11"));
        assertEquals("1", parsedMessage.getFields().get("21"));
        assertEquals("MSFT", parsedMessage.getFields().get("55"));
        assertEquals("1", parsedMessage.getFields().get("54"));
        assertEquals("20030615-19:11:01.000", parsedMessage.getFields().get("60"));
        assertEquals("100", parsedMessage.getFields().get("38"));
        assertEquals("2", parsedMessage.getFields().get("40"));
        assertEquals("20.00", parsedMessage.getFields().get("44"));
        assertEquals("220", parsedMessage.getFields().get("10"));
    }
}

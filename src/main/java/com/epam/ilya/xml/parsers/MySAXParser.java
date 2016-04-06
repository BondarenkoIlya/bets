package com.epam.ilya.xml.parsers;

import com.epam.ilya.exceptions.ParseException;
import com.epam.ilya.xml.Parser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MySAXParser implements Parser {
    @Override
    public <T> List<T> parse(String xmlFileName, Class<T> clazz) throws ParseException {
        InputStream is = MySAXParser.class.getClassLoader().getResourceAsStream(xmlFileName);
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXHandler handler = new SAXHandler(clazz);
        try {
            javax.xml.parsers.SAXParser saxParser = saxFactory.newSAXParser();
            saxParser.parse(is,handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ParseException(e);
        }
        return (List<T>)handler.getResult();
    }
}

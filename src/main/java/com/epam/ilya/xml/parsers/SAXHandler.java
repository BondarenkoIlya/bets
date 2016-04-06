package com.epam.ilya.xml.parsers;

import com.epam.ilya.xml.adapters.AdaptersMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;


public class SAXHandler<T> extends DefaultHandler {
    private static final Logger log = LoggerFactory.getLogger(String.valueOf(SAXHandler.class));

    private Class<T> inputClass;
    private T currentEntity;
    private List<T> entityList;
    private StringBuilder sb = new StringBuilder();


    public SAXHandler() {

    }

    public SAXHandler(Class<T> clazz) {
        this.inputClass = clazz;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase(inputClass.getSimpleName().toLowerCase() + "List")) {
            entityList = new ArrayList<T>();
        }else if (qName.equalsIgnoreCase(inputClass.getSimpleName().toLowerCase())) {
            try {
                currentEntity = inputClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        sb.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        try {
            if (qName.equals(inputClass.getSimpleName().toLowerCase())){//нужно ставить слеш закрытия ?
                entityList.add(currentEntity);
            }else if (qName.equalsIgnoreCase(inputClass.getSimpleName().toLowerCase() + "List")){

            }else{
                Field currentField = inputClass.getDeclaredField(qName);
                currentField.setAccessible(true);
                log.info(currentField.getType().getSimpleName().toLowerCase());
                //сомнительное дело
                AdaptersMap adapters = new AdaptersMap();
                currentField.set(currentEntity,adapters.getAdapters().get(currentField.getType().getSimpleName().toLowerCase()).unmarshal(new String(sb).trim()));
            }
        } catch (Exception e) {
            e.printStackTrace();// не хочет меняться на парсекскпшн
        }
        sb.setLength(0);
    }

    public List<T> getResult() {
        return entityList;
    }
}

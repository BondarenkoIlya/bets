package com.epam.ilya.xml.parsers;

import com.epam.ilya.Runner;
import com.epam.ilya.exceptions.ParseException;
import com.epam.ilya.xml.adapters.AdaptersMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DOMHandler<T> {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(Runner.class));
    private Class<T> inputClass;
    private T currentEntity;
    private List<T> entityList;

    public DOMHandler() {
    }

    public void handle(String xmlFileName, Class<T> clazz) throws ParseException {
        this.inputClass = clazz;
        InputStream inputStream = DOMHandler.class.getClassLoader().getResourceAsStream(xmlFileName);
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();
            log.info(doc.getDocumentElement().getNodeName());
            if (doc.getElementsByTagName(inputClass.getSimpleName().toLowerCase()+"List")!=null){
                entityList= new ArrayList<T>();
            }
            AdaptersMap adapters = new AdaptersMap();

            NodeList nodeList = doc.getElementsByTagName(inputClass.getSimpleName().toLowerCase());
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                currentEntity = inputClass.newInstance();
                entityList.add(currentEntity);
                Node node = nodeList.item(temp);
                log.info("nodName "+ node.getNodeName());
                log.info(node.getNodeValue());
                Field[] declaredFields = inputClass.getDeclaredFields();
                log.info("Current element " + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    log.info("tag name "+ element.getTagName());
                    log.info("name " + element.getNodeName());
                    for (Field currentField : declaredFields) {
                        currentField.setAccessible(true);
                        log.info(currentField.getName());
                        Node currentElement = element.getElementsByTagName(currentField.getName()).item(0);
                        if (currentElement!= null) {
                            currentField.set(currentEntity,adapters.getAdapters().get(currentField.getType().getSimpleName().toLowerCase()).unmarshal(currentElement.getTextContent().trim()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ParseException(e);
        }
    }

    public List<T> getResult(){
        return entityList;
    }


}

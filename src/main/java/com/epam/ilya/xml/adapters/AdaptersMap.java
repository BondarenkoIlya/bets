package com.epam.ilya.xml.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AdaptersMap {
    private static Map<String, XmlAdapter<String, Object>> adapters = new HashMap<String, XmlAdapter<String, Object>>();

    static {
        Properties p = new Properties();
        try {
            p.load(AdaptersMap.class.getClassLoader().getResourceAsStream("adapters.properties"));
            for (Map.Entry<Object, Object> e : p.entrySet()) {
                String className = (String) e.getKey();
                Class<?> aClass = Class.forName(className);
                className = aClass.getSimpleName().toLowerCase();
                String adapterClassName = (String) e.getValue();
                Class<?> adapterClass = Class.forName(adapterClassName);
                XmlAdapter<String, Object> adapter = (XmlAdapter<String, Object>) adapterClass.newInstance();
                adapters.put(className, adapter);
            }
        } catch (IOException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, XmlAdapter<String, Object>> getAdapters() {
        return adapters;
    }
}

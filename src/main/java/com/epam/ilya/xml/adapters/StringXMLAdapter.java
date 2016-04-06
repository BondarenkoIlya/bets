package com.epam.ilya.xml.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class StringXMLAdapter extends XmlAdapter<String,String> {
    @Override
    public String unmarshal(String v) throws Exception {
        return v;
    }

    @Override
    public String marshal(String v) throws Exception {
        return v;
    }
}

package com.epam.ilya.xml.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Scanner;

public class DoubleXMLAdapter extends XmlAdapter<String, Double> {

    @Override
    public Double unmarshal(String st) throws Exception {
        Scanner sc = new Scanner(st);
        double v = sc.nextDouble();
        return v;
    }

    @Override
    public String marshal(Double v) throws Exception {
        return String.valueOf(v);
    }
}

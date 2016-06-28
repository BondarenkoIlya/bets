package com.epam.ilya.xml.adapters;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateTimeXMLAdapter extends XmlAdapter<String, DateTime> {

    @Override
    public DateTime unmarshal(String st) throws Exception {
        DateTime date = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").parseDateTime(st);
        return date;

        //getTimeStamp для того что бы вытаскивать из базы данных
    }

    @Override
    public String marshal(DateTime v) throws Exception {
        return String.valueOf(v);
    }
}

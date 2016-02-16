package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Дом on 09.02.2016.
 */
public class Bookmaker extends Person {// Его можно сделать статиком как и сервис ?
    static List<Customer> customerList = new ArrayList<Customer>();
    static List<Match> matchList = new ArrayList<Match>();


    public Bookmaker(){

    }

    public Bookmaker(String name){
        this.setName(name);


    }

}

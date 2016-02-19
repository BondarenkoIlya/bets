package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Дом on 09.02.2016.
 */
public class Customer extends Person {
    List<BetEntity> bets = new ArrayList<BetEntity>();

    public Customer() {

    }

    public Customer(String name){
        this.setName(name);
    }
}

package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Дом on 09.02.2016.
 */
public class Customer extends Person {
    List<BetByWinSide> betByWinSides = new ArrayList<BetByWinSide>();
    List<BetByScore> betByScores = new ArrayList<BetByScore>();//стоит вообще делать листы ? или можно что бы обьяекты никуда ни складывались ? или обязательно для сравнения нужно что бы были листы ?

    public Customer() {

    }

    public Customer(String name){
        this.setName(name);
    }
}

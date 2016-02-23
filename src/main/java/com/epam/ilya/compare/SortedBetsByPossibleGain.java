package com.epam.ilya.compare;

import com.epam.ilya.model.Bet;
import org.joda.money.Money;

import java.util.Comparator;

/**
 * Created by Дом on 19.02.2016.
 */
public class SortedBetsByPossibleGain implements Comparator<Bet> {

    public int compare(Bet b1, Bet b2) {
        Money val1 = b1.getPossibleGain();
        Money val2 = b2.getPossibleGain();
        return val1.compareTo(val2);  // как пофиксить это ?
    }
}

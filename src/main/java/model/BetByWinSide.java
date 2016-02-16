package model;

import org.joda.money.Money;

/**
 * Created by Дом on 14.02.2016.
 */
public class BetByWinSide extends BetsEntity {
    private String nameOfWinSide;

    public BetByWinSide(){

    }

    public BetByWinSide(Money value,Match match, String nameOfWinSide , boolean result){//как реализовать что бы можно было выбирать что ставка делается на определенный матч ?
        this.setValue(value);
        this.match=match;
        this.setNameOfWinSide(nameOfWinSide);
    }
    // переписать "получит если выйграет " и написать формулу которая задействует коэффициенты (но нужно четко знать от какого матча будут эти коэффициенты)

    public String getNameOfWinSide() {
        return nameOfWinSide;
    }

    public void setNameOfWinSide(String nameOfWinSide) {
        this.nameOfWinSide = nameOfWinSide;
    }
}

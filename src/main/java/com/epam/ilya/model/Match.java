package com.epam.ilya.model;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Match describe the event, to which can be bet
 *
 * @author Bondarenko Ilya
 */

public class Match extends BaseEntity {

    private String sportsName;
    private String leaguesName;
    private DateTime date;
    private String firstSidesName;
    private String secondSidesName;
    private List<Condition> conditionList = new ArrayList<Condition>();

    public Match() {
    }

    public Match(String sportsName, String leagueName, DateTime date, String nameOfSide1, String nameOfSide2) {
        this.setSportsName(sportsName);
        this.setLeaguesName(leagueName);
        this.setDate(date);
        this.setFirstSidesName(nameOfSide1);
        this.setSecondSidesName(nameOfSide2);
    }

    public void setResults(String nameOfWinSide, int differenceInScore) {
    }

    public void setRandomResults() {
    }

    public String getLeaguesName() {
        return leaguesName;
    }

    public void setLeaguesName(String leaguesName) {
        this.leaguesName = leaguesName;
    }

    public String getSportsName() {
        return sportsName;
    }

    public void setSportsName(String sportsName) {
        this.sportsName = sportsName;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getFirstSidesName() {
        return firstSidesName;
    }

    public void setFirstSidesName(String firstSidesName) {
        this.firstSidesName = firstSidesName;
    }

    public String getSecondSidesName() {
        return secondSidesName;
    }

    public void setSecondSidesName(String secondSidesName) {
        this.secondSidesName = secondSidesName;
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<Condition> conditionList) {
        this.conditionList = conditionList;
    }

    public void addCondition(Condition condition) {
        conditionList.add(condition);
    }

    public void removeCondition(Condition condition) {
        conditionList.remove(condition);
    }

    @Override
    public String toString() {
        return "Match{" +
                "sportsName='" + sportsName + '\'' +
                ", date=" + date +
                ", firstSidesName='" + firstSidesName + '\'' +
                ", secondSidesName='" + secondSidesName + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 37 + getId();
        hash = hash * 37 + sportsName.hashCode();
        hash = hash * 37 + leaguesName.hashCode();
        hash = hash * 37 + date.hashCode();
        hash = hash * 37 + firstSidesName.hashCode();
        hash = hash * 37 + secondSidesName.hashCode();
        hash = hash * 37 + conditionList.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Match)) {
            return false;
        } else {
            Match match = (Match) obj;
            return this.hashCode() == match.hashCode();
        }
    }
}


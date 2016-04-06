package com.epam.ilya.model;

import com.epam.ilya.Runner;
import com.epam.ilya.factory.MatchFactory;
import com.epam.ilya.xml.parsers.SAXHandler;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Match extends BaseEntity {

    private String sportName;
    private String leagueName;
    private DateTime date;
    private String nameOfSide1;
    private String nameOfSide2;
    private List<Condition> conditionList= new ArrayList<Condition>();

    public Match() {
    }

    public Match(String sportName, String leagueName, DateTime date, String nameOfSide1, String nameOfSide2) {
        this.setSportName(sportName);
        this.setLeagueName(leagueName);
        this.setDate(date);
        this.setNameOfSide1(nameOfSide1);
        this.setNameOfSide2(nameOfSide2);
    }

    public void setResults(String nameOfWinSide,int differenceInScore){
    }

    public void setRandomResults(){
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getNameOfSide1() {
        return nameOfSide1;
    }

    public void setNameOfSide1(String nameOfSide1) {
        this.nameOfSide1 = nameOfSide1;
    }

    public String getNameOfSide2() {
        return nameOfSide2;
    }

    public void setNameOfSide2(String nameOfSide2) {
        this.nameOfSide2 = nameOfSide2;
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<Condition> conditionList) {
        this.conditionList = conditionList;
    }

    public void addCondition (Condition condition){
        conditionList.add(condition);
    }

    public void removeCondition (Condition condition){
        conditionList.remove(condition);
    }

    @Override
    public String toString() {
        return "Match{" +
                "sportName='" + sportName + '\'' +
                ", date=" + date +
                ", nameOfSide1='" + nameOfSide1 + '\'' +
                ", nameOfSide2='" + nameOfSide2 + '\'' +
                '}';
    }
}


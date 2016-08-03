package com.epam.ilya;


import com.epam.ilya.exceptions.ParseException;
import com.epam.ilya.factory.BetFactory;
import com.epam.ilya.factory.CustomerFactory;
import com.epam.ilya.factory.MatchFactory;
import com.epam.ilya.model.Customer;
import com.epam.ilya.services.BetService;
import com.epam.ilya.services.MatchService;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class Runner {

    static final Logger log = LoggerFactory.getLogger(Runner.class);
    private static BetService betService = new BetService();
    private static MatchService matchService = new MatchService();
    private static PersonService personService = new PersonService();
    private static MatchFactory matchFactory = new MatchFactory();
    private static BetFactory betFactory = new BetFactory();
    private static CustomerFactory customerFactory = new CustomerFactory();


    public static void main(String[] args) throws  ParserConfigurationException, SAXException, IOException, ParseException {//как обработать эксепшон ранее

        Customer customer = new Customer();
        try {
            customer.setFirstName("Илья");
            customer.setLastName("Бондаренко");
            customer.setPassword("qwdqdwqd");
            customer.setEmail("iiillliiiaaa@mail.ru");
            personService.registerCustomer(customer);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        /*personService.addPurseToPerson(Bookmaker.bookmaker);
        personService.depositOnPersonsAccount(Bookmaker.bookmaker, Money.of(CurrencyUnit.of("KZT"), 300000));

        Customer customerPetya = customerFactory.createCustomer("Петя");
        personService.depositOnPersonsAccount(customerPetya, Money.of(CurrencyUnit.of("KZT"), 4000));

        Customer customerVasya = customerFactory.createCustomer("Вася");
        personService.depositOnPersonsAccount(customerVasya, Money.of(CurrencyUnit.of("KZT"), 1000));

        Match match = matchFactory.createMatch("Football", "Champion League", new DateTime(2016, 4, 22, 19, 0, 0), "Arsenal", "Manchester City");

        Condition condition1 = new Condition(1.5,"Arsenal win");
        matchService.addConditionToMatch(condition1,match);
        Condition condition2 = new Condition(2.1,"MC win");
        matchService.addConditionToMatch(condition2,match);
        Condition condition3 = new Condition(3.0,"Dead heat");
        matchService.addConditionToMatch(condition3,match);

        Bet bet1 = betFactory.createBet(Money.of(CurrencyUnit.of("KZT"), 500));
        betService.addConditionToBet(condition1,bet1);
        personService.addBetToCustomer(customerPetya,bet1);

        Bet bet2 = betFactory.createBet(Money.of(CurrencyUnit.of("KZT"), 1000));
        betService.addConditionToBet(condition2,bet2);
        personService.addBetToCustomer(customerVasya,bet2);

        matchService.fillResultOfCondition(condition1,true);
        matchService.fillResultOfCondition(condition2,false);
        matchService.fillResultOfCondition(condition3,false);

        betService.putDownBetsResult(customerPetya,bet1);// не обязательно знать человека что бы проставить результаты
        betService.putDownBetsResult(customerVasya,bet2);
        personService.showPersonsBalance(Bookmaker.bookmaker);*/

//        service.depositOnPersonsAccount(bookmaker,Money.of(CurrencyUnit.of("KZT"),300000));
//
//        Customer customerPetya = customerFactory.createCustomer("Петя");
//        service.depositOnPersonsAccount(customerPetya,Money.of(CurrencyUnit.of("KZT"),4000));// is that must to be in factory ?
//        Customer customerVasya = customerFactory.createCustomer("Вася",4000);
//
//        Match match = matchFactory.createMatch();
//
//        ScoreBet bs1 = betFactory.createBetByScore(match,customerPetya);
//        customerPetya.add(bs1);
//
//        WinSideBet bws1 = betFactory.createBetByWinSide(match,customerPetya);
//        customerPetya.add(bws1);
//
//        ScoreBet bs2 = betFactory.createBetByScore(match,customerVasya);
//        customerVasya.add(bs2);
//
//        WinSideBet bws2 = betFactory.createBetByWinSide(match,customerVasya);
//        customerVasya.add(bws2);
//
//        match.setRandomResults();
//
//        service.computeBetsResult(customerPetya,match);
//        service.computeBetsResult(customerVasya,match);
//
//        service.showPersonsBalance(customerPetya);
//        service.showPersonsBalance(customerVasya);
//
//        List<Bet> wonBets = service.filterWonBets(customerPetya);
//        service.showBetsList(wonBets);
//
//        service.sort(customerPetya.getBets());
        //c1.getBets().sort(Comparator.comparing(Bet::getPossibleGain));


    }
}

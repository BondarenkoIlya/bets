package com.epam.ilya.action;

import com.epam.ilya.action.get.*;
import com.epam.ilya.action.post.*;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private Map<String,Action> actions;

    public ActionFactory() {
        actions = new HashMap<>();
//Get request
        //Main operations
        actions.put("GET/welcome", new ShowPageAction("welcome"));
        actions.put("GET/register", new ShowPageAction("register"));
        actions.put("GET/logout", new LogoutAction());
        //Bookmaker's operations with customers
        actions.put("GET/bookmaker/home", new ShowBookmakerHomePageAction());
        actions.put("GET/customer/edit", new ShowCustomerEditionPageAction());
        //Bookmaker's operations with match
        actions.put("GET/matches/edit", new ShowMatchesEditionPageAction());
        actions.put("GET/match/create", new ShowPageAction("create-match"));
        actions.put("GET/match/new/edit", new ShowPageAction("new-match-edit"));
        actions.put("GET/match/create/condition", new ShowPageAction("create-condition"));
        actions.put("GET/match/edit", new ShowMatchEditionPageAction());
        //Customer's operation with bet
        actions.put("GET/home", new ShowCustomersHomePageAction());
        actions.put("GET/bets", new ShowCustomersBetsPageAction());
        actions.put("GET/bet/create", new ShowPageAction("create-bet"));
        actions.put("GET/bet/edit", new ShowBetEditPageAction());
        actions.put("GET/bet/add/condition", new ShowAddConditionToBetPageAction());//здесь показывает все матчи и коеффициенты
        //actions.put("GET/bet/edit/delete/condition", new DeleteConditionFromBetAction());

// Post request
        //Main operations
        actions.put("POST/login", new LoginAction());
        actions.put("POST/register", new RegisterAction());
        //Bookmaker's operations with customers
        actions.put("POST/replenish/bookmaker", new ReplenishBookmakersBalanceAction());
        actions.put("POST/replenish/customer", new ReplenishCustomersBalanceAction());
        actions.put("POST/find/customer", new FindCustomersAction());
        //Bookmaker's operations with match
        actions.put("POST/match/create", new CreateEmptyMatchAction());
        actions.put("POST/match/create/condition", new AddConditionToMatchAction());
        actions.put("POST/match/submit", new SaveNewMatchAction());
        //Customer's operation with bet
        actions.put("POST/bet/create", new CreateEmptyBetAction());
        actions.put("POST/bet/add/condition", new AddConditionToBetAction());
        actions.put("POST/bet/submit", new SaveNewBetAction());

    }

    public Action getAction(String actionName) {
        return actions.get(actionName);
    }
}

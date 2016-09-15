package com.epam.ilya.action;

import com.epam.ilya.action.get.*;
import com.epam.ilya.action.post.*;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private Map<String, Action> actions;

    public ActionFactory() {
        actions = new HashMap<>();
//Get request
        //Main operations
        actions.put("GET/welcome", new ShowPageAction("welcome"));
        actions.put("GET/register", new ShowPageAction("register"));
        //Bookmaker's operations with customers
        actions.put("GET/bookmaker/home", new ShowBookmakerHomePageAction());
        actions.put("GET/customer/edit", new ShowCustomerEditionPageAction());
        //Bookmaker's operations with match
        actions.put("GET/matches/edit/active", new ShowActiveMatchesEditionPageAction());
        actions.put("GET/matches/edit/inactive", new ShowInactiveMatchesEditionPageAction());
        actions.put("GET/match/create", new ShowPageAction("create-match"));
        actions.put("GET/match/new/edit", new ShowPageAction("new-match-edit"));
        actions.put("GET/match/create/condition", new ShowPageAction("create-condition"));
        actions.put("GET/match/edit", new ShowMatchEditionPageAction());
        actions.put("GET/match/edit/sum/up", new ShowSumUpMatchsResultPageAction());
        //Customer's main operation
        actions.put("GET/avatar/upload", new ShowPageAction("upload-avatar"));
        //Customer's operation with bet
        actions.put("GET/home", new ShowCustomersHomePageAction());
        actions.put("GET/cabinet", new ShowPageAction("customers-cabinet"));
        actions.put("GET/bets/active", new ShowCustomersActiveBetsPageAction());
        actions.put("GET/bets/inactive", new ShowCustomersInactiveBetsPageAction());
        actions.put("GET/bet/create", new ShowPageAction("create-bet"));
        actions.put("GET/bet/edit", new ShowBetEditPageAction());
        actions.put("GET/bet/add/condition", new ShowAddConditionToBetPageAction());

// Post request
        //Main operations
        actions.put("POST/login", new LoginAction());
        actions.put("POST/register", new RegisterAction());
        actions.put("POST/locale", new ChangeLocaleAction());
        actions.put("GET/logout", new LogoutAction());// по хорошему это пост запрос но как тогда редеректать из секьюрити фильтра
        //Bookmaker's operations with customers
        actions.put("POST/bookmaker/replenish", new ReplenishBookmakersBalanceAction());
        actions.put("POST/customer/replenish", new ReplenishCustomersBalanceAction());
        //Bookmaker's operations with match
        actions.put("POST/match/create", new CreateEmptyMatchAction());
        actions.put("POST/match/create/cancel", new CancelMatchCreationAction());
        actions.put("POST/match/create/condition", new AddConditionToMatchAction());
        actions.put("GET/match/create/condition/delete", new DeleteConditionFromMatchAction());
        actions.put("POST/match/submit", new SaveNewMatchAction());
        actions.put("POST/match/edit/sum/up", new SaveConditionsResultAction());
        //Customer's main operation
        actions.put("POST/avatar/upload", new UploadAvatarAction());
        actions.put("POST/avatar/update", new UpdateAvatarAction());
        //Customer's operation with bet
        actions.put("POST/bet/create", new CreateEmptyBetAction());
        actions.put("POST/bet/create/cancel", new CancelBetCreationAction());
        actions.put("GET/bet/edit/delete/condition", new DeleteConditionFromBetAction());
        actions.put("POST/bet/add/condition", new AddConditionToBetAction());
        actions.put("POST/bet/submit", new SaveNewBetAction());

    }

    public Action getAction(String actionName) {
        return actions.get(actionName);
    }
}

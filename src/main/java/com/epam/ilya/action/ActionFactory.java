package com.epam.ilya.action;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private Map<String,Action> actions;

    public ActionFactory() {
        actions = new HashMap<>();

        actions.put("GET/welcome", new ShowPageAction("welcome"));
        actions.put("GET/register", new ShowPageAction("register"));
        actions.put("GET/home", new ShowPageAction("home"));
        actions.put("GET/bookmaker-home", new ShowBookmakerHomePageAction());
        actions.put("GET/bookmaker-home/customer-edit", new ShowCustomerEditionPageAction());
        actions.put("GET/match-editor", new ShowBookmakerMatchEditorPageAction());
        actions.put("GET/logout", new LogoutAction());

        actions.put("POST/login", new LoginAction());
        actions.put("POST/register", new RegisterAction());
        actions.put("POST/replenish/bookmaker", new ReplenishBookmakersBalanceAction());
        actions.put("POST/replenish/customer", new ReplenishCustomersBalanceAction());
        actions.put("POST/withdraw/customer", new WithdrawCustomersBalanceAction());
        actions.put("POST/find/customer", new FindCustomersAction());
    }

    public Action getAction(String actionName) {
        return actions.get(actionName);
    }
}

package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Condition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteConditionFromBetAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Bet bet = (Bet) req.getSession(false).getAttribute("bet");
        String current_id = req.getParameter("id");
        Condition deletedCondition = null;
        for (Condition condition : bet.getConditions()) {
            if (condition.getId() == Integer.parseInt(current_id)) {
                deletedCondition = condition;
            }
        }
        bet.getConditions().remove(deletedCondition);
        req.getSession(false).setAttribute("bet", bet);
        return new ActionResult("bet/edit", true);
    }
}

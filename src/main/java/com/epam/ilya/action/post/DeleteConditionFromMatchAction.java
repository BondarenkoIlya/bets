package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Condition;
import com.epam.ilya.model.Match;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteConditionFromMatchAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Match match = (Match) req.getSession(false).getAttribute("match");
        String current_id = req.getParameter("id");
        Condition deletedCondition = null;
        for (Condition condition : match.getConditionList()) {
            if (condition.getId() == Integer.parseInt(current_id)) {
                deletedCondition = condition;
            }
        }
        match.getConditionList().remove(deletedCondition);
        req.getSession(false).setAttribute("match", match);
        return new ActionResult("match/new/edit", true);
    }
}

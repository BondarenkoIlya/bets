package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Condition;
import com.epam.ilya.model.Match;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddConditionToMatchAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Match match = (Match) req.getSession(false).getAttribute("match");
        String conditionsName = req.getParameter("conditionsName");
        String coefficient = req.getParameter("coefficient");

        Condition condition = new Condition();
        condition.setCoefficient(Double.parseDouble(coefficient));
        condition.setConditionsName(conditionsName);

        match.addCondition(condition);
        req.getSession(false).setAttribute("match",match);
        return new ActionResult("/match/new/edit",true);
    }
}

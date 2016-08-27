package com.epam.ilya.action.post;

import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Condition;
import com.epam.ilya.services.MatchService;
import com.epam.ilya.services.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddConditionToBetAction implements com.epam.ilya.action.Action {


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        MatchService service = new MatchService();
        String id = req.getParameter("conditionId");


        Condition condition= null;
        try {
            condition = service.getConditionById(id);
        } catch (ServiceException e) {
            throw new ActionException("Cannot get condition by id",e);
        }

        Bet bet = (Bet) req.getSession(false).getAttribute("bet");
        bet.addCondition(condition);
        req.getSession(false).setAttribute("bet",bet);
        return new ActionResult("bet/edit",true);
    }
}

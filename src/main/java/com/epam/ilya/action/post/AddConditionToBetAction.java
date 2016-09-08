package com.epam.ilya.action.post;

import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Condition;
import com.epam.ilya.services.MatchService;
import com.epam.ilya.services.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddConditionToBetAction implements com.epam.ilya.action.Action {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(AddConditionToBetAction.class));

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        MatchService service = new MatchService();
        String id = req.getParameter("conditionId");
        log.debug("Condition's id - {}",id);

        Condition condition = null;
        try {
            condition = service.getConditionById(id);
            log.debug("Get condition - {} by id",condition);
        } catch (ServiceException e) {
            throw new ActionException("Cannot get condition by id", e);
        }

        Bet bet = (Bet) req.getSession(false).getAttribute("bet");
        bet.addCondition(condition);
        log.debug("Add condition to bet - {}",bet);
        req.getSession(false).setAttribute("bet", bet);
        return new ActionResult("bet/edit", true);
    }
}

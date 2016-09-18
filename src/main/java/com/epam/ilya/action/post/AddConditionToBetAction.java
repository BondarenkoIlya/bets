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
    static final Logger log = LoggerFactory.getLogger(AddConditionToBetAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        MatchService service = new MatchService();
        Bet bet = (Bet) req.getSession(false).getAttribute("bet");
        String id = req.getParameter("conditionId");
        log.debug("Condition's id - {}", id);
        Condition condition;
        try {
            condition = service.getConditionById(id);
            log.debug("Get condition - {} by id", condition);
        } catch (ServiceException e) {
            throw new ActionException("Cannot get condition by id", e);
        }
        boolean result = true;
        log.debug("Take all bet's condition and compare it with just added");
        if (!bet.getConditions().isEmpty()) {
            for (Condition betCondition : bet.getConditions()) {
                if (condition.equals(betCondition)) {
                    log.debug("{} equals - {}", condition, betCondition);
                    result = false;
                }

            }
        }
        if (result) {
            bet.addCondition(condition);
            log.debug("Have no one same condition. Add condition to bet - {}", bet);
        } else {
            req.setAttribute("flash.equalsError", "true");
        }
        req.getSession(false).setAttribute("bet", bet);
        return new ActionResult("bet/edit", true);
    }
}

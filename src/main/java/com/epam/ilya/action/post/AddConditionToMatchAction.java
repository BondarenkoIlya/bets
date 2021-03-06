package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Condition;
import com.epam.ilya.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * Class takes all information about the new condition and validates it. If all information is correct then class
 * creates and adds a new condition to the match from session and makes a redirect on match/new/edit. If it's
 * incorrect then class writes the information about error in attribute for create-condition page.
 *
 * @author Bondarenko Ilya
 */

public class AddConditionToMatchAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(AddConditionToBetAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Match match = (Match) req.getSession(false).getAttribute("match");
        String conditionsName = req.getParameter("conditionsName");
        String coefficient = req.getParameter("coefficient");
        Condition condition = new Condition();
        Properties properties = new Properties();
        boolean result = true;
        try {
            properties.load(CreateEmptyMatchAction.class.getClassLoader().getResourceAsStream("validation.properties"));
        } catch (IOException e) {
            throw new ActionException("Cannot load validation properties", e);
        }
        if (conditionsName.matches(properties.getProperty("stringWithSpaces.regex"))) {
            condition.setConditionsName(conditionsName);
        } else {
            LOG.debug("Cannot matches condition name - {}", conditionsName);
            req.setAttribute("conditionsNameError", "true");
            result = false;
        }
        if (coefficient.matches(properties.getProperty("doubleNumber.regex"))) {
            condition.setCoefficient(Double.parseDouble(coefficient));
        } else {
            req.setAttribute("coefficientError", "true");
            result = false;
        }

        if (!result) {
            result = true;
            return new ActionResult("create-condition");
        }

        match.addCondition(condition);
        req.getSession(false).setAttribute("match", match);
        return new ActionResult("match/new/edit", true);
    }
}

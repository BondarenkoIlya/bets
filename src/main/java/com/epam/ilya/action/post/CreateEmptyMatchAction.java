package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Match;
import com.epam.ilya.services.MatchService;
import com.epam.ilya.services.ServiceException;
import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class creates a match without any conditions if all information about this match is correct.
 *
 * @author Bondarenko Ilya
 */

public class CreateEmptyMatchAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(CreateEmptyMatchAction.class);
    private boolean invalid = false;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Properties properties = new Properties();
        MatchService service = new MatchService();
        Match match = new Match();
        String sportsName = req.getParameter("sportsName");
        String leaguesName = req.getParameter("leaguesName");
        String eventsDate = req.getParameter("eventsDate");
        String firstSidesName = req.getParameter("firstSidesName");
        String secondSidesName = req.getParameter("secondSidesName");
        LOG.debug("Parameters for creating new match {}, {}, {}, {}, {}", sportsName, leaguesName, eventsDate, firstSidesName, secondSidesName);
        try {
            properties.load(CreateEmptyMatchAction.class.getClassLoader().getResourceAsStream("validation.properties"));
        } catch (IOException e) {
            throw new ActionException("Cannot load validation properties", e);
        }
        checkParameterBeRegex(sportsName, "sportsName", properties.getProperty("stringWithSpaces.regex"), req);
        checkParameterBeRegex(leaguesName, "leaguesName", properties.getProperty("stringWithSpaces.regex"), req);
        checkParameterBeRegex(firstSidesName, "firstSidesName", properties.getProperty("stringWithSpaces.regex"), req);
        checkParameterBeRegex(secondSidesName, "secondSidesName", properties.getProperty("stringWithSpaces.regex"), req);
        DateTime dateTime = null;
        if (eventsDate.matches(properties.getProperty("dateTime.regex"))) {
            try {
                DateTimeFormatter pattern = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
                dateTime = pattern.parseDateTime(eventsDate);
            } catch (IllegalFieldValueException e) {
                LOG.warn("{} -  Incorrect date value", eventsDate);
                invalid = true;
                req.setAttribute("eventsDateError", "true");
            }
            if (dateTime.isBeforeNow()) {
                invalid = true;
                req.setAttribute("eventsDateError", "beforeNow");
            }
        } else {
            invalid = true;
            req.setAttribute("eventsDateError", "true");
        }
        if (invalid) {
            invalid = false;
            return new ActionResult("create-match");
        } else {
            LOG.info("All parameters is correct");
            match.setSportsName(sportsName);
            match.setLeaguesName(leaguesName);
            match.setDate(dateTime);
            match.setFirstSidesName(firstSidesName);
            match.setSecondSidesName(secondSidesName);

            Match registeredMatch;
            try {
                LOG.debug("Try to register match - {}");
                registeredMatch = service.createEmptyMatch(match);
            } catch (ServiceException e) {
                throw new ActionException("Cannot create empty match", e);
            }
            req.getSession(false).setAttribute("match", registeredMatch);
            return new ActionResult("match/new/edit", true);
        }
    }

    private void checkParameterBeRegex(String parameter, String parameterName, String regex, HttpServletRequest req) {
        LOG.debug("Check parameter '{}' with value '{}' by regex '{}'", parameterName, parameter, regex);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parameter);
        if (!matcher.matches()) {
            LOG.debug("Parameter '{}' with value '{}' is unsuitable.", parameterName, parameter);
            req.setAttribute(parameterName + "Error", "true");
            invalid = true;
        }
    }
}

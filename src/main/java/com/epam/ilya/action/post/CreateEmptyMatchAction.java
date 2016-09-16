package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Match;
import com.epam.ilya.services.MatchService;
import com.epam.ilya.services.ServiceException;
import org.joda.time.DateTime;
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

public class CreateEmptyMatchAction implements Action {
    static final Logger log = LoggerFactory.getLogger(CreateEmptyMatchAction.class);
    boolean invalid = false;

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
        log.debug("Parameters for creating new match {}, {}, {}, {}, {}", sportsName, leaguesName, eventsDate, firstSidesName, secondSidesName);
        try {
            properties.load(CreateEmptyMatchAction.class.getClassLoader().getResourceAsStream("validation.properties"));
        } catch (IOException e) {
            throw new ActionException("Cannot load validation properties", e);
        }
        checkParameterBeRegex(sportsName, "sportsName", properties.getProperty("notEmptyText.regex"), req);
        checkParameterBeRegex(leaguesName, "leaguesName", properties.getProperty("notEmptyText.regex"), req);
        checkParameterBeRegex(eventsDate, "eventsDate", properties.getProperty("dateTime.regex"), req);
        checkParameterBeRegex(firstSidesName, "firstSidesName", properties.getProperty("notEmptyText.regex"), req);
        checkParameterBeRegex(secondSidesName, "secondSidesName", properties.getProperty("notEmptyText.regex"), req);

        DateTimeFormatter pattern = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
        DateTime dateTime = pattern.parseDateTime(eventsDate);
        if (dateTime==null){
            invalid = true;
            req.setAttribute("eventsDateError","true");
        }else if (dateTime.isBeforeNow()){
            invalid=true;
            req.setAttribute("eventsDateError","beforeNow");
        }
        if (invalid) {
            invalid = false;
            return new ActionResult("create-match");
        } else {
            log.info("All parameters is correct");
            match.setSportsName(sportsName);
            match.setLeaguesName(leaguesName);
            match.setDate(dateTime);
            match.setFirstSidesName(firstSidesName);
            match.setSecondSidesName(secondSidesName);

            Match registeredMatch;
            try {
                log.debug("Try to register match - {}");
                registeredMatch = service.createEmptyMatch(match);
            } catch (ServiceException e) {
                throw new ActionException("Cannot create empty match", e);
            }
            req.getSession(false).setAttribute("match", registeredMatch);
            return new ActionResult("match/new/edit", true);
        }
    }

    private void checkParameterBeRegex(String parameter, String parameterName, String regex, HttpServletRequest req) {
        log.debug("Check parameter '{}' with value '{}' by regex '{}'", parameterName, parameter, regex);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parameter);
        if (!matcher.matches()) {
            log.debug("Parameter '{}' with value '{}' is unsuitable.", parameterName, parameter);
            req.setAttribute(parameterName + "Error", "true");
            invalid = true;
        }
    }
}

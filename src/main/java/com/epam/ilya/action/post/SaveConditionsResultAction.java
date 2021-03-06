package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Bookmaker;
import com.epam.ilya.model.Condition;
import com.epam.ilya.model.Match;
import com.epam.ilya.services.BetService;
import com.epam.ilya.services.MatchService;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class takes all information about result of each condition and validates it. If it's correct then class saves the
 * result , otherwise it writes the information about an error in attribute.
 *
 * @author Bondarenko Ilya
 */

public class SaveConditionsResultAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(SaveConditionsResultAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        MatchService matchService = new MatchService();
        BetService betService = new BetService();
        PersonService personService = new PersonService();
        Bookmaker bookmaker = (Bookmaker) req.getSession(false).getAttribute("bookmaker");
        String matchId = req.getParameter("match_id");
        boolean invalid = false;
        try {
            Match match = matchService.getMatchById(matchId);
            for (Condition condition : match.getConditionList()) {
                String parameter = req.getParameter(String.valueOf(condition.getId()));
                Boolean parseResult;
                if (parameter != null) {
                    if (parameter.equals("true") || parameter.equals("false")) {
                        parseResult = Boolean.parseBoolean(parameter);
                        condition.setResult(parseResult);
                    } else {
                        invalid = true;
                    }
                } else {
                    invalid = true;
                }
            }
            if (invalid) {
                req.setAttribute("match", match);
                req.setAttribute("inputError", "true");
                return new ActionResult("sum-up-result");
            }
            matchService.sumUpConditionsResult(match);
            List<Bet> playedBets = betService.sumUpBetsResultByFinishedMatch(match);
            for (Bet bet : playedBets) {
                LOG.debug("Bet's customer - {}", bet.getCustomer());
                personService.summarizeBet(bet, bookmaker);
            }
        } catch (ServiceException e) {
            throw new ActionException("Cannot get match by id", e);
        }
        req.getSession(false).setAttribute("bookmaker", bookmaker);
        return new ActionResult("matches/edit/active", true);
    }
}

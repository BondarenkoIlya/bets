package com.epam.ilya.action;

import com.epam.ilya.model.Match;
import com.epam.ilya.services.MatchService;
import com.epam.ilya.services.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowBookmakerMatchEditorPageAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        MatchService service = new MatchService();

        List<Match> matches;
        try {
            matches = service.getAllMatchFromDao();
        } catch (ServiceException e) {
            throw new ActionException("Cannot get customers list in action", e);
        }

        req.setAttribute("matches", matches);


        return new ActionResult("match-editor");
    }
}

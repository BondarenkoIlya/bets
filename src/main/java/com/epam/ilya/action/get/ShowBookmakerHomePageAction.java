package com.epam.ilya.action.get;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Customer;
import com.epam.ilya.model.PaginatedList;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class takes certain amount of customers to write it in attribute for bookmaker-home page.
 *
 * @author Bondarenko Ilya
 */

public class ShowBookmakerHomePageAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowBookmakerHomePageAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        PersonService service = new PersonService();
        String pageNumberParam = req.getParameter("pageNumber");
        PaginatedList<Customer> customers;
        int pageSize = 5;
        int pageNumber;
        if (pageNumberParam == null) {
            LOG.debug("Do not get page number parameter. Set page number 1");
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(pageNumberParam);
        }
        try {
            customers = service.getAllCustomers(pageNumber, pageSize);
            LOG.debug("Get customers paginated list with {} page numbers of {} pages at all and {} page size ", customers.getPageNumber(), customers.getPageCount(), customers.getPageSize());
        } catch (ServiceException e) {
            throw new ActionException("Cannot get customers list in action", e);
        }
        req.setAttribute("customers", customers);
        return new ActionResult("bookmaker-home");
    }
}

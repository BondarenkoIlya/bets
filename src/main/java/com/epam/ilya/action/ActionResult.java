package com.epam.ilya.action;

public class ActionResult {
    private final String view;
    private final boolean redirect;

    public ActionResult(String page, boolean redirect) {
        this.view = page;
        this.redirect = redirect;
    }

    public ActionResult(String page) {
        this.view = page;
        this.redirect = false;
    }

    public String getView() {
        return view;
    }

    public boolean isRedirect() {
        return redirect;
    }
}


<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<p style="text-align: center"><img style="align-items: center; align-self: center;"
                                   src="<c:url value="/images/${requestScope['javax.servlet.error.status_code']}.jpg"/>">
</p>
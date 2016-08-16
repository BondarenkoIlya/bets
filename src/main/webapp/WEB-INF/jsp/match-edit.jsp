<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<fmt:bundle basename="i18n">
    <fmt:message key="bookmaker.home.customers" var="customers_editor"/>
</fmt:bundle>

<my:page-pattern role="bookmaker">
    <div>
        <h3>${match_editor}</h3>
        <div class="container">
            <div class="row">
                <div class="col-md-2">${match_date}</div>
                <div class="col-md-2">${match_side_1}</div>
                <div class="col-md-2">${match_side_2}</div>
            </div>
            <c:forEach items="${matches}" var="match">
                <div class="row">
                    <div class="col-md-2">${match.getDate()}</div>
                    <div class="col-md-2">${match.getNameOfSide1()}</div>
                    <div class="col-md-2">${match.getNameOfSide2()}</div>
                </div>
            </c:forEach>
        </div>
    </div>
</my:page-pattern>


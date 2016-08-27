<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<fmt:bundle basename="i18n">
    <fmt:message key="bookmaker.match.create.condition" var="create_condition"/>
    <fmt:message key="bookmaker.match.create.condition.name" var="conditions_name"/>
    <fmt:message key="bookmaker.match.create.condition.name.error" var="conditionsNameErrorAlert"/>
    <fmt:message key="bookmaker.match.create.condition.coefficient" var="coefficient"/>
    <fmt:message key="bookmaker.match.create.condition.coefficient.error" var="coefficientErrorAlert"/>
    <fmt:message key="bookmaker.match.create.condition.button" var="create_condition_button"/>
    <fmt:message key="bookmaker.match.create.condition.back.button" var="back"/>
</fmt:bundle>

<c:url var="create_condition_url" value="/do/match/create/condition"/>
<c:url var="add_condition_url" value="/do/match/new/edit"/>

<my:page-pattern role="stepTwo">
    <div class="container">
            ${create_condition}
        <form class="form-group" method="post" action="${create_condition_url}">
                ${conditions_name}<br/>
            <input type="text" name="conditionsName" value=""/><br/>
            <c:if test="${not empty conditionsNameError}">
                <p class="alert alert-danger"
                   style="width: auto;height: auto">${conditionsNameErrorAlert}</p>
            </c:if>
                ${coefficient}<br/>
            <input type="te" name="coefficient" value=""/><br/>
            <c:if test="${not empty coefficientError}">
                <p class="alert alert-danger"
                   style="width: auto;height: auto">${coefficientErrorAlert}</p>
            </c:if>
            <input type="submit" class="btn btn-default" value=${create_condition_button}><br/>
        </form>
        <button type="button" class="button btn-link"><a href="${add_condition_url}">${back}</a></button>
    </div>
</my:page-pattern>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<c:url var="create_condition_url" value="/do/match/create/condition"/>
<c:url var="add_condition_url" value="/do/match/new/edit"/>

<my:page-pattern role="stepTwo">
    <fmt:message key="bookmaker.match.create.condition" var="create_condition"/>
    <fmt:message key="bookmaker.match.create.condition.name" var="conditions_name"/>
    <fmt:message key="bookmaker.match.create.condition.name.error" var="conditionsNameErrorAlert"/>
    <fmt:message key="bookmaker.match.create.condition.coefficient" var="coefficient"/>
    <fmt:message key="bookmaker.match.create.condition.coefficient.error" var="coefficientErrorAlert"/>
    <fmt:message key="bookmaker.match.create.condition.button" var="create_condition_button"/>
    <fmt:message key="bookmaker.match.create.condition.back.button" var="back"/>
    <div class="container">
            ${create_condition}
        <form class="form-group" method="post" action="${create_condition_url}">
                ${conditions_name}<br/>
            <input type="text" name="conditionsName" value="<c:out value="${param.conditionsName}"/>"/><br/>
            <c:if test="${not empty conditionsNameError}">
                <p class="alert alert-danger"
                   style="width: auto;height: auto">${conditionsNameErrorAlert}</p>
            </c:if>
                ${coefficient}<br/>
            <input type="text" name="coefficient" value=""/><br/>
            <c:if test="${not empty coefficientError}">
                <p class="alert alert-danger"
                   style="width: auto;height: auto">${coefficientErrorAlert}</p>
            </c:if>
            <input type="submit" class="btn btn-default" value=${create_condition_button}><br/>
        </form>
        <a href="<c:url value="/do/match/new/edit"/>" role="button" class="btn btn-default"><fmt:message
                key="back"/></a>
    </div>

</my:page-pattern>
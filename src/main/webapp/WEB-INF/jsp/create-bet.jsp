<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<c:url value="/do/bet/create" var="create_bet_url"/>

<my:page-pattern role="stepOne">
    <fmt:message key="customer.create.bet" var="create_bet"/>
    <fmt:message key="customer.create.bet.step.one" var="step_one"/>
    <fmt:message key="customer.create.bet.value" var="input_value"/>
    <fmt:message key="customer.create.bet.error.available" var="available_error"/>
    <fmt:message key="customer.create.bet.error" var="incorrect_error"/>
    <fmt:message key="customer.create.bet.next.button" var="next_step_button"/>
    <div class="container">
            ${create_bet}<br/>
        <h2>${step_one}<br/></h2>
        <form role="form" action="${create_bet_url}" method="POST">
            <div class="form-group">
                <label for="bets_value" class="control-label">${input_value}</label><br/>
                <input type="text" id="bets_value" name="value" value=""><br/>
                <c:if test="${valueError.equals('notAvailable')}">
                    <p class="alert alert-danger" style="width: auto;height: auto">${available_error}</p>
                </c:if>
                <c:if test="${valueError.equals('incorrect')}">
                    <p class="alert alert-danger" style="width: auto;height: auto">${incorrect_error}</p>
                </c:if>
                <input type="submit" class="btn btn-default" value=${next_step_button}><br/>
            </div>
        </form>
        <a href="<c:url value="/do/home"/>" role="button" class="btn btn-default"><fmt:message key="back"/></a>
    </div>
</my:page-pattern>
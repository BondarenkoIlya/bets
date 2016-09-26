<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<my:page-pattern role="customer">
    <fmt:message key="customer.bets" var="bets_edit"/>
    <fmt:message key="customer.bets.new" var="new_bet_message"/>
    <fmt:message key="customer.bets.active" var="active_bets"/>
    <fmt:message key="customer.bets.inactive" var="inactive_bets"/>
    <fmt:message key="customer.bets.value" var="value"/>
    <fmt:message key="customer.bets.possible.gain" var="possible_gain"/>
    <fmt:message key="customer.bets.conditions" var="bets_conditions"/>
    <fmt:message key="customer.bets.final.coefficient" var="final_coefficient"/>
    <fmt:message key="customer.bets.date" var="date"/>
    <fmt:message key="customer.bets.result" var="result"/>
    <fmt:message key="customer.bets.message.win" var="win_message"/>
    <fmt:message key="customer.bets.message.lose" var="lose_message"/>
    <div class="container">
        <c:if test="${create_bet_successfully.equals('true')}">
            <p class="alert alert-info"
               style="width: auto;height: auto">${new_bet_message}</p>
        </c:if>
        <h3>${bets_edit}</h3>
        <ul class="nav nav-tabs">
            <li class="active">${active_bets}</li>
            <li><a href="<c:url value="/do/bets/inactive"/>">${inactive_bets}</a></li>
        </ul>
        <div class="row">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>${value}</th>
                    <th>${possible_gain}</th>
                    <th>${bets_conditions}</th>
                    <th>${final_coefficient}</th>
                    <th>${date}</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${activeBets}" var="bet">
                    <tr>
                        <td><fmt:formatNumber value="${bet.value.getAmount().doubleValue()}"/>Тг</td>
                        <td><fmt:formatNumber value="${bet.possibleGain.getAmount().doubleValue()}"/>Тг</td>
                        <td>
                            <c:forEach items="${bet.conditions}" var="condition">
                                ${condition.conditionsName} ${condition.coefficient} <c:if
                                    test="${condition.result!=null}">${condition.result}</c:if><br/>
                            </c:forEach>
                        </td>
                        <td><fmt:formatNumber value="${bet.finalCoefficient}"/></td>
                        <td><fmt:formatDate type="both" value="${bet.date.toDate()}" /></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <my:pagination pageNumber="${activeBets.getPageNumber()}" pageCount="${activeBets.getPageCount()}"
                           url="/do/bets/active"/>
        </div>
    </div>
</my:page-pattern>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
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
    <c:if test="${create_bet_successfully.equals('true')}">
        <p class="alert alert-info"
           style="width: auto;height: auto">${new_bet_message}</p>
    </c:if>
    <h3>${bets_edit}</h3>
    <div class="container">
        <h2>${active_bets}</h2>
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
                        <td>${bet.value.getAmount().doubleValue()}</td>
                        <td>${bet.possibleGain.getAmount().doubleValue()}</td>
                        <td>
                            <c:forEach items="${bet.conditions}" var="condition">
                                ${condition.conditionsName} ${condition.coefficient}<br/>
                            </c:forEach>
                        </td>
                        <td>${bet.finalCoefficient}</td>
                        <td>${bet.date}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <h2>${inactive_bets}</h2>
        <div class="row">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>${value}</th>
                    <th>${bets_conditions}</th>
                    <th>${final_coefficient}</th>
                    <th>${result}</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${inactiveBets}" var="bet">
                    <tr>
                        <td>${bet.value.getAmount().doubleValue()}</td>
                        <td>
                            <c:forEach items="${bet.conditions}" var="condition">
                                ${condition.conditionsName} ${condition.coefficient} ${condition.result}<br/>
                            </c:forEach>
                        </td>
                        <td>${bet.finalCoefficient}</td>
                        <td>
                            <c:if test="${bet.finalResult.equals(true)}">
                                <p class="alert alert-success"
                                   style="width: 250px;height: auto">${win_message} ${bet.possibleGain.getAmount().doubleValue()}</p>
                            </c:if>
                            <c:if test="${bet.finalResult.equals(false)}">
                                <p class="alert alert-warning"
                                   style="width: 250px;height: auto">${lose_message} ${bet.value.getAmount().doubleValue()}</p>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</my:page-pattern>
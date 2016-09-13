<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<my:page-pattern role="stepTwo">
    <fmt:message key="customer.bet.edit" var="bets_constructor"/>
    <fmt:message key="customer.bet.edit.value" var="bet_value"/>
    <fmt:message key="customer.bet.edit.condition" var="conditions_name"/>
    <fmt:message key="customer.bet.edit.coefficient" var="coefficient"/>
    <fmt:message key="customer.bet.edit.final.coefficient" var="bet_final_coefficient"/>
    <fmt:message key="customer.bet.edit.possible.gain" var="bet_possible_gain"/>
    <fmt:message key="customer.bet.edit.add.condition.button" var="add_condition_button"/>
    <fmt:message key="customer.bet.edit.submit.button" var="submit_button"/>
    <div class="container">
            ${bets_constructor}<br/>
        <h3>${bet_value} - ${bet.value.getAmount().doubleValue()}<br/></h3>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>${conditions_name}</th>
                <th>${coefficient}</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${bet.conditions}" var="condition">
                <tr>
                    <td>${condition.conditionsName}</td>
                    <td>${condition.coefficient}</td>
                        <%--<td><a href="<c:url value="/do/bet/edit/delete/condition?id=${condition.id}"/> ">${delete_condition_button}
                        </a></td>--%>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <h3>${bet_final_coefficient} - ${bet.finalCoefficient}<br/></h3>
        <h3>${bet_possible_gain} - ${bet.possibleGain.getAmount().doubleValue()}<br/></h3>
        <button type="button" class="button btn-link"><a
                href="<c:url value="/do/bet/add/condition"/>">${add_condition_button}</a></button>
        <form action="<c:url value="/do/bet/submit"/>" method="post" role="form">
            <input type="submit" class="button btn-link" value="${submit_button}"/>
        </form>

    </div>
</my:page-pattern>
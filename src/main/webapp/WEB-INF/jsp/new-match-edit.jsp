<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<fmt:bundle basename="i18n">
    <fmt:message key="bookmaker.match.add.condition" var="add_conditions"/>
    <fmt:message key="bookmaker.match.add.condition.name" var="conditions_name"/>
    <fmt:message key="bookmaker.match.add.condition.coefficient" var="coefficient"/>
    <fmt:message key="bookmaker.match.add.condition.delete.button" var="delete_condition_button"/>
    <fmt:message key="bookmaker.match.add.condition.new.button" var="create_new_condition_button"/>
    <fmt:message key="bookmaker.match.submit.button" var="submit_button"/>
</fmt:bundle>

<my:page-pattern role="stepTwo">
    <div>
        <h3>${add_conditions}</h3>

        <div class="container">
            <div class="row">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>${conditions_name}</th>
                        <th>${coefficient}</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${match.conditionList}" var="condition">
                        <tr>
                            <td>${condition.conditionsName}</td>
                            <td>${condition.coefficient}</td>
                            <td><%--<a href="<c:url value="/do/match/create/condition/delete?id=${condition.id}"/> ">${delete_condition_button}
                        </a>--%></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <button type="button" class="button btn-link"><a
                        href="<c:url value="/do/match/create/condition"/>">${create_new_condition_button}</a></button>
                <form role="form" method="post" action="<c:url value="/do/match/submit"/>">
                    <input type="submit" class="button btn-link" value="${submit_button}"/>
                </form>
            </div>
        </div>
    </div>
</my:page-pattern>
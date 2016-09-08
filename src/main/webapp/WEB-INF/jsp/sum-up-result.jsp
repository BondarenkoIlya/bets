<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="match" type="com.epam.ilya.model.Match"--%>

<fmt:bundle basename="i18n">
    <fmt:message key="bookmaker.match.conditions.result" var="button"/>
    <fmt:message key="bookmaker.match.add.condition.coefficient" var="coefficient"/>
    <fmt:message key="bookmaker.match.add.condition.name" var="conditions_name"/>
    <fmt:message key="bookmaker.match.result.error.message" var="result_error_message"/>
</fmt:bundle>

<c:url value="/do/match/edit/sum/up?match_id=${match.id}" var="sum_up_result_url"/>

<my:page-pattern role="bookmaker">
    <div>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>${conditions_name}</th>
                <th>${coefficient}</th>
                <th></th>
            </tr>
            </thead>
            <form action="${sum_up_result_url}" method="post" role="form">
                <tbody>
                <c:forEach items="${match.conditionList}" var="condition">
                    <tr>
                        <td>${condition.conditionsName}</td>
                        <td>${condition.coefficient}</td>
                        <td><input type="checkbox" name="${condition.id}" value="true"/></td>
                    </tr>
                </c:forEach>
                </tbody>
                <input type="submit" class="btn btn-default" value=${button}><br/>
            </form>
        </table>
        <c:if test="${not empty inputError}">
            <p class="alert alert-danger"
               style="width: auto;height: auto">${result_error_message}</p>
        </c:if>
    </div>
</my:page-pattern>
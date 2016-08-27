<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="match" type="com.epam.ilya.model.Match"--%>

<fmt:bundle basename="i18n">
    <fmt:message key="bookmaker.match.conditions.result" var="button"/>
    <fmt:message key="bookmaker.match.add.condition.coefficient" var="coefficient"/>
    <fmt:message key="bookmaker.match.add.condition.name" var="conditions_name"/>
</fmt:bundle>

<c:url value="/do/match/edit/sum/up" var="sum_up_result_url"/>

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
                        <td><input type="checkbox" name="${condition.id}" value=""/></td>
                    </tr>
                </c:forEach>
                </tbody>
        </table>
        <input type="submit" class="btn btn-default" value=${button}><br/>
        </form>
    </div>
</my:page-pattern>
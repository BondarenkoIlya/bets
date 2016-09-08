<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<fmt:bundle basename="i18n">

</fmt:bundle>

<c:url value="/do/bet/add/condition" var="add_condition_url"/>
<my:page-pattern role="stepTwo">
    <div class="container">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>${date}</th>
                <th>${sport}</th>
                <th>${league}</th>
                <th>${first_side}</th>
                <th>${second_side}</th>
                <th>${conditions}</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${matches}" var="match">
                <tr>
                    <td>${match.date}</td>
                    <td>${match.sportsName}</td>
                    <td>${match.leaguesName}</td>
                    <td>${match.firstSidesName}</td>
                    <td>${match.secondSidesName}</td>
                    <td>${match.secondSidesName}</td>
                    <td>
                        <c:forEach items="${match.conditionList}" var="condition">
                            ${condition.conditionsName}
                            <form role="form" method="post" action="${add_condition_url}">
                                <input type="hidden" name="conditionId" value="${condition.id}"/>
                                <input type="submit" class="btn btn-default" value="${condition.coefficient}"/>
                            </form>
                            <br/>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</my:page-pattern>
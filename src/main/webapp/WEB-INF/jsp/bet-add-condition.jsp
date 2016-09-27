<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>


<c:url value="/do/bet/add/condition" var="add_condition_url"/>

<my:page-pattern role="stepTwo">
    <div class="container">
        <table class="table table-striped">
            <thead>
            <tr>
                <th><fmt:message key="bookmaker.match.new.eventsDate"/></th>
                <th><fmt:message key="bookmaker.match.new.sportsName"/></th>
                <th><fmt:message key="bookmaker.match.new.leaguesName"/></th>
                <th><fmt:message key="bookmaker.match.new.firstSidesName"/></th>
                <th><fmt:message key="bookmaker.match.new.secondSidesName"/></th>
                <th><fmt:message key="customer.bets.conditions"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${matches}" var="match">
                <tr>
                    <td><fmt:formatDate value="${match.date.toDate()}" type="both"/></td>
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
        <my:pagination pageNumber="${matches.getPageNumber()}" pageCount="${matches.getPageCount()}"
                       url="/do/bet/add/condition"/>
        <a href="<c:url value="/do/bet/edit"/>" role="button" class="btn btn-default"><fmt:message key="back"/></a>
    </div>
</my:page-pattern>
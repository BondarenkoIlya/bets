<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<my:page-pattern role="bookmaker">
    <fmt:message key="bookmaker.matches.edit" var="match_edition"/>
    <fmt:message key="bookmaker.matches.new" var="create_new_match"/>
    <fmt:message key="bookmaker.matches.date" var="match_date"/>
    <fmt:message key="bookmaker.matches.side1" var="match_side_1"/>
    <fmt:message key="bookmaker.matches.side2" var="match_side_2"/>
    <fmt:message key="bookmaker.matches.button.edit" var="edit_match_button"/>
    <fmt:message key="bookmaker.matches.active" var="active_matches"/>
    <fmt:message key="bookmaker.matches.inactive" var="inactive_matches"/>
    <div class="container">
        <h3>${match_edition}</h3>
        <ul class="nav nav-tabs">
            <li class="active">${active_matches}</li>
            <li><a href="<c:url value="/do/matches/edit/inactive"/>">${inactive_matches}</a></li>
        </ul>
        <div class="row">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>${match_date}</th>
                    <th>${match_side_1}</th>
                    <th>${match_side_2}</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${activeMatches}" var="match">
                    <tr>
                        <td><fmt:formatDate type="both" value="${match.date.toDate()}"/></td>
                        <td>${match.firstSidesName}</td>
                        <td>${match.secondSidesName}</td>
                        <td><a href="<c:url value="/do/match/edit?id=${match.id}"/> ">${edit_match_button}
                        </a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <my:pagination pageNumber="${activeMatches.getPageNumber()}" pageCount="${activeMatches.getPageCount()}"
                           url="/do/matches/edit/active"/>
            <a class="btn btn-default" role="button" href="<c:url value="/do/match/create"/>">${create_new_match}</a>
            <c:if test="${not empty cancelMatch}">
                <p class="alert alert-info" style="width: 250px;height: auto"><fmt:message
                        key="bookmaker.matches.new.cancel"/></p>
            </c:if>
        </div>
    </div>
</my:page-pattern>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<fmt:bundle basename="i18n">
    <fmt:message key="bookmaker.matches.edit" var="match_edition"/>
    <fmt:message key="bookmaker.matches.new" var="create_new_match"/>
    <fmt:message key="bookmaker.matches.date" var="match_date"/>
    <fmt:message key="bookmaker.matches.side1" var="match_side_1"/>
    <fmt:message key="bookmaker.matches.side2" var="match_side_2"/>
    <fmt:message key="bookmaker.matches.button.edit" var="edit_match_button"/>
</fmt:bundle>

<my:page-pattern role="bookmaker">
    <div>
    <h3>${match_edition}</h3>
    <h2>${active_matches}</h2>
    <div class="container">
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
                    <td>${match.date}</td>
                    <td>${match.firstSidesName}</td>
                    <td>${match.secondSidesName}</td>
                    <td><a href="<c:url value="/do/match/edit?id=${match.id}"/> ">${edit_match_button}
                    </a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <h2>${inactive_matches}</h2>
        <div class="container">
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
                    <c:forEach items="${inactiveMatches}" var="match">
                        <tr>
                            <td>${match.date}</td>
                            <td>${match.firstSidesName}</td>
                            <td>${match.secondSidesName}</td>
                            <td><a href="<c:url value="/do/match/edit?id=${match.id}"/> ">${edit_match_button}
                            </a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <button type="button" class="button btn-link"><a
                        href="<c:url value="/do/match/create"/>">${create_new_match}</a></button>
            </div>
        </div>
    </div>
</my:page-pattern>


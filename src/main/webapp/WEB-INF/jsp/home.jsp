<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>


<fmt:bundle basename="i18n">
    <fmt:message key="customer.home.create.bet.button" var="create_bet_button"/>
    <fmt:message key="customer.home.matches.table" var="matches_table"/>
    <fmt:message key="customer.home.matches.date" var="date"/>
    <fmt:message key="customer.home.matches.sport" var="sport"/>
    <fmt:message key="customer.home.matches.league" var="league"/>
    <fmt:message key="customer.home.matches.side.first" var="first_side"/>
    <fmt:message key="customer.home.matches.side.second" var="second_side"/>
</fmt:bundle>
<c:url value="/do/bet/create" var="create_bet_url"/>

<my:page-pattern role="customer">
    <div>
        <div class="container">
            <div class="row">
                <div class="col-md-6 col-lg-6 col-sm-6">
                    ${matches_table}
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>${date}</th>
                            <th>${sport}</th>
                            <th>${league}</th>
                            <th>${first_side}</th>
                            <th>${second_side}</th>
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
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <button type="button" class="btn btn-default"><a
                        href="${create_bet_url}">${create_bet_button}</a></button>
            </div>
        </div>
    </div>
</my:page-pattern>


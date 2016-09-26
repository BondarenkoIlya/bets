<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<c:url value="/do/bet/create" var="create_bet_url"/>

<my:page-pattern role="customer">
    <fmt:message key="customer.home.create.bet.button" var="create_bet_button"/>
    <fmt:message key="customer.home.success.register.message" var="success_register_message"/>
    <fmt:message key="customer.home.matches.table" var="matches_table"/>
    <fmt:message key="customer.home.matches.date" var="date"/>
    <fmt:message key="customer.home.matches.sport" var="sport"/>
    <fmt:message key="customer.home.matches.league" var="league"/>
    <fmt:message key="customer.home.matches.side.first" var="first_side"/>
    <fmt:message key="customer.home.matches.side.second" var="second_side"/>
    <div>
        <div class="container">
            <c:if test="${not empty registerMessage}">
                <p class="alert alert-success" style="width: 250px;height: auto">${success_register_message}</p>
            </c:if>
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
                                <td><fmt:formatDate value="${match.date.toDate()}" type="both"/></td>
                                <td>${match.sportsName}</td>
                                <td>${match.leaguesName}</td>
                                <td>${match.firstSidesName}</td>
                                <td>${match.secondSidesName}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <my:pagination pageNumber="${matches.getPageNumber()}" pageCount="${matches.getPageCount()}"
                                   url="/do/home"/>
                </div>
                <a class="btn btn-default" role="button"
                   href="${create_bet_url}">${create_bet_button}</a>

                <c:if test="${not empty cancelBet}">
                    <p class="alert alert-info" style="width: 250px;height: auto"><fmt:message
                            key="customer.home.create.bet.cancel"/></p>
                </c:if>
            </div>
        </div>
    </div>
</my:page-pattern>


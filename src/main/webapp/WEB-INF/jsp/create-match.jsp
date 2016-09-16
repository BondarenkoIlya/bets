<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<c:url value="/do/match/create" var="new_match_url"/>

<my:page-pattern role="stepOne">
    <fmt:message key="bookmaker.match.create" var="match_creation"/>
    <fmt:message key="bookmaker.match.new.next.button" var="next_step_button"/>
    <fmt:message key="bookmaker.match.new.sportsName" var="sportsName"/>
    <fmt:message key="bookmaker.match.new.leaguesName" var="leaguesName"/>
    <fmt:message key="bookmaker.match.new.eventsDate" var="eventsDate"/>
    <fmt:message key="bookmaker.match.new.firstSidesName" var="firstSidesName"/>
    <fmt:message key="bookmaker.match.new.secondSidesName" var="secondSidesName"/>
    <fmt:message key="bookmaker.match.new.eventsDate.example" var="example"/>

    <div class="container">
            ${match_creation}
        <form role="form" action="${new_match_url}" method="POST">
            <div class="form-group">
                    ${sportsName}<br/>
                <input type="text" name="sportsName" value=""/><br/>
                <c:if test="${not empty sportsNameError}">
                    <p class="alert alert-danger"
                       style="width: auto;height: auto"><fmt:message
                            key="bookmaker.match.new.error.alert.sportsName"/></p>
                </c:if>
                    ${leaguesName}<br/>
                <input type="text" name="leaguesName" value=""/><br/>
                <c:if test="${not empty leaguesNameError}">
                    <p class="alert alert-danger"
                       style="width: auto;height: auto"><fmt:message
                            key="bookmaker.match.new.error.alert.leagueName"/></p>
                </c:if>
                    ${eventsDate}<br/>
                <input type="datetime" name="eventsDate" value=""/><br/>
                <c:if test="${eventsDateError.equals('true')}">
                    <p class="alert alert-danger"
                       style="width: auto;height: auto"><fmt:message
                            key="bookmaker.match.new.error.alert.eventsDate"/></p>
                </c:if>
                <c:if test="${eventsDateError.equals('beforeNow')}">
                    <p class="alert alert-danger"
                       style="width: auto;height: auto"><fmt:message
                            key="bookmaker.match.new.error.alert.eventsDate.before"/></p>
                </c:if>
                <p class="alert alert-info" style="width: auto;height: auto">${example}</p>
                    ${firstSidesName}<br/>
                <input type="text" name="firstSidesName" value=""/><br/>
                <c:if test="${not empty firstSidesNameError}">
                    <p class="alert alert-danger"
                       style="width: auto;height: auto"><fmt:message
                            key="bookmaker.match.new.error.alert.firstSideName"/></p>
                </c:if>
                    ${secondSidesName}<br/>
                <input type="text" name="secondSidesName" value=""/><br/>
                <c:if test="${not empty secondSidesNameError}">
                    <p class="alert alert-danger"
                       style="width: auto;height: auto"><fmt:message
                            key="bookmaker.match.new.error.alert.secondSideName"/></p>
                </c:if>
            </div>
            <input type="submit" class="btn btn-default" value=${next_step_button}><br/>
        </form>
        <a class="btn btn-default" role="button" href="<c:url value="/do/matches/edit/active"/>"><fmt:message
                key="back"/></a>
    </div>
</my:page-pattern>
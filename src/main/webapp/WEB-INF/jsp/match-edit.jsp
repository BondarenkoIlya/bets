<%--@elvariable id="match" type="com.epam.ilya.model.Match"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<c:url var="sum_up_result_of_match_url" value="/do/match/edit/sum/up?id=${match.id}"/>

<my:page-pattern role="bookmaker">
    <fmt:message key="bookmaker.match.edit" var="edit_match"/>
    <fmt:message key="bookmaker.match.edit.sides.against" var="against"/>
    <fmt:message key="bookmaker.match.edit.sum.up.button" var="sum_up_button"/>
    <div class="container">
            ${edit_match}<br/>
            ${match.firstSidesName} ${against} ${match.secondSidesName}<br/>
        <a class="btn btn-default" role="button"
           href="${sum_up_result_of_match_url}">${sum_up_button}</a>
    </div>
</my:page-pattern>

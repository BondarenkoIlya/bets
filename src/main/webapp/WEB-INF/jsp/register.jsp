<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<fmt:bundle basename="i18n">
    <fmt:message key="register" var="register"/>
    <fmt:message key="register.firstName" var="firstName"/>
    <fmt:message key="register.lastName" var="lastName"/>
    <fmt:message key="register.email" var="email"/>
    <fmt:message key="register.pasword" var="pasword"/>
    <fmt:message key="register.repeatPassword" var="repeatPassword"/>
    <fmt:message key="register.button" var="button"/>
    <fmt:message key="register.firstName.error" var="firstNameErrorAlert"/>
    <fmt:message key="register.lastName.error" var="lastNameErrorAlert"/>
    <fmt:message key="register.email.error.busy" var="emailBusyErrorAlert"/>
    <fmt:message key="register.email.error.unsuiteble" var="emailErrorAlert"/>
    <fmt:message key="register.password.error.repeat" var="wrongRepeatPasswordErrorAlert"/>
    <fmt:message key="register.password.error.unsuitable" var="passwordErrorAlert"/>

</fmt:bundle>
<c:url value="/do/register" var="register_url"/>

<my:page-pattern role="guest">
    <div class="container">
            ${register}<br/>
        <form role="form" action="${register_url}" method="POST">
            <div class="form-group">
                    ${firstName}<br/>
                <input type="text" name="firstName" value=""/><br/>
                <c:if test="${not empty firstNameError}">
                    <p class="alert alert-danger"
                       style="width: 250px;height: 30px;padding: 5px">${firstNameErrorAlert}</p>
                </c:if>
                    ${lastName}<br/>
                <input type="text" name="lastName" value=""/><br/>
                <c:if test="${not empty lastNameError}">
                    <p class="alert alert-danger"
                       style="width: 250px;height: 30px;padding: 5px">${lastNameErrorAlert}</p>
                </c:if>
                    ${email}<br/>
                <input type="text" name="email" value=""/><br/>
                <c:if test="${emailError.equals('busy')}">
                    <p class="alert alert-danger"
                       style="width: 250px;height: 30px;padding: 5px">${emailBusyErrorAlert}</p>
                </c:if>
                <c:if test="${emailError.equals('true')}">
                    <p class="alert alert-danger" style="width: 250px;height: 30px;padding: 5px">${emailErrorAlert}</p>
                </c:if>
                    ${pasword}<br/>
                <input type="text" name="password" value=""/><br/>
                    ${repeatPassword}<br/>
                <input type="text" name="repeatPassword" value=""/><br/>
                <c:if test="${passwordError.equals('wrong repeat')}">
                    <p class="alert alert-danger"
                       style="width: 250px;height: 30px;padding: 5px">${wrongRepeatPasswordErrorAlert}</p>
                </c:if>
                <c:if test="${passwordError.equals('true')}">
                    <p class="alert alert-danger"
                       style="width: 250px;height: 30px;padding: 5px">${passwordErrorAlert}</p>
                </c:if>
            </div>
            <input type="submit" class="btn btn-default" value=${button}><br/>
        </form>
    </div>
</my:page-pattern>
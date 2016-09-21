<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<my:page-pattern role="guest">

    <fmt:message key="welcome.login" var="login"/>
    <fmt:message key="welcome.login.button" var="loginButton"/>
    <fmt:message key="welcome.password" var="password"/>
    <fmt:message key="welcome.register" var="register"/>
    <fmt:message key="welcome.error" var="error"/>
    <fmt:message key="welcome.error.authorizationError.customer" var="reloggin_customer_error_message"/>
    <fmt:message key="welcome.error.authorizationError.bookmaker" var="reloggin_bookmaker_error_message"/>


    <c:url var="login_url" value="/do/login"/>
    <c:url var="register_url" value="/do/register"/>

    <div class="container">
        <div class="col-lg-10 " align="center">
            <form role="form" action="${login_url}" method="POST">
                <div class="form-group">
                    <label for="mail" class="control-label">${login}</label><br/>
                    <input type="text" id="mail" name="login" value=""><br/>
                    <label for="pass" class="control-label">${password}</label> <br/>
                    <input type="password" id="pass" name="password" value=""><br/>
                    <c:if test="${not empty loginError}">
                        <p class="alert alert-danger" style="width: 250px;height: auto">${error}</p>
                    </c:if>
                    <input type="submit" class="btn btn-default" value=${loginButton}><br/>
                </div>
            </form>
            <a class="btn btn-default" role="button"
               href="${register_url}">${register}</a>
        </div>
        <c:if test="${authorizationError.equals('customer')}">
            <p class="alert alert-danger" style="width: 250px;height: auto">${reloggin_customer_error_message}</p>
        </c:if>
        <c:if test="${authorizationError.equals('bookmaker')}">
            <p class="alert alert-danger" style="width: 250px;height: auto">${reloggin_bookmaker_error_message}</p>
        </c:if>
    </div>
</my:page-pattern>


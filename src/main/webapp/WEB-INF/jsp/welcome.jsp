<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<fmt:bundle basename="i18n">
    <fmt:message key="welcome.login" var="login"/>
    <fmt:message key="welcome.login.button" var="loginButton"/>
    <fmt:message key="welcome.password" var="password"/>
    <fmt:message key="welcome.register" var="register"/>
    <fmt:message key="welcome.error" var="error"/>
</fmt:bundle>

<c:url var="login_url" value="/do/login"/>
<c:url var="register_url" value="/do/register"/>

<my:page-pattern role="guest">
    <div>
        <div class="col-lg-10 " align="center">
            <form role="form" action="${login_url}" method="POST">
                <div class="form-group">
                    <label for="mail" class="control-label">${login}</label><br/>
                    <input type="text" id="mail" name="login" value=""><br/>
                    <label for="pass" class="control-label">${password}</label> <br/>
                    <input type="password" id="pass" name="password" value=""><br/>
                    <c:if test="${not empty loginError}">
                        <p class="alert alert-danger" style="width: 250px;height: 30px;padding: 5px">${error}</p>
                    </c:if>
                    <input type="submit" class="btn btn-default" value=${loginButton}><br/>
                </div>
            </form>
            <button type="button" class="btn btn-default"><a
                    href="${register_url}">${register}</a></button>
        </div>
    </div>
</my:page-pattern>


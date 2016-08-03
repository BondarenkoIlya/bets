<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:bundle basename="i18n">
    <fmt:message key="welcome.login" var="login"/>
    <fmt:message key="welcome.login.button" var="loginButton"/>
    <fmt:message key="welcome.password" var="password"/>
    <fmt:message key="welcome.register" var="register"/>
    <fmt:message key="welcome.register.success" var="successRegisterMessage"/>
</fmt:bundle>


<my:page-pattern>
    <div class="col-lg-10 " align="center">
        <h3>Welcome</h3><br/>
        <form action="do/login" method="POST">
                ${login}<br/>
            <input type="text" name="login" value=""/><br/>
                ${password}<br/>
            <input type="text" name="password" value=""/><br/>
            <input type="submit" value=${loginButton} /><br/>
        </form>
        <button type="button" class="btn btn-default"><a
                href="${pageContext.request.contextPath}/do/register">${register}</a></button>
    </div>
</my:page-pattern>


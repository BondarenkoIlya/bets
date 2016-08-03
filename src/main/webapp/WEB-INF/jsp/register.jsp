<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:bundle basename="i18n">
    <fmt:message key="register" var="register"/>
    <fmt:message key="register.firstName" var="firstName"/>
    <fmt:message key="register.lastName" var="lastName"/>
    <fmt:message key="register.email" var="email"/>
    <fmt:message key="register.pasword" var="pasword"/>
    <fmt:message key="register.repeatPassword" var="repeatPassword"/>
    <fmt:message key="register.button" var="button"/>
    <fmt:message key="register.password.different" var="differentPasswordsMessage"/>
</fmt:bundle>

<html>
<head>
    <title>Register</title>
</head>
<body>
${register}<br/>
<form action="do/register" method="POST">
    ${firstName}<br/>
    <input type="text" name="firstName" value=""/><br/>
    ${lastName}<br/>
    <input type="text" name="lastName" value=""/><br/>
    ${email}<br/>
    <input type="text" name="email" value=""/><br/>
    ${pasword}<br/>
    <input type="text" name="password" value=""/><br/>
    ${repeatPassword}<br/>
    <input type="text" name="repeatPassword" value=""/><br/>
    <input type="submit" value=${button}/><br/>
</form>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<c:url value="/do/avatar/upload" var="avatar_upload_url"/>

<my:page-pattern role="stepTwo">
    <fmt:message key="avatar.upload.button" var="upload_button"/>
    <fmt:message key="avatar.upload" var="upload"/>
    <div class="container">
            ${upload}
        <form role="form" action="${avatar_upload_url}" method="post" enctype="multipart/form-data">
            <input type="file" name="avatar"/>
            <fmt:message key="customer.cabinet.weight"/>
            <input type="submit" class="btn btn-link" value="${upload_button}"/>
        </form>
        <a href="<c:url value="/do/home?successRegister=true"/>" role="button" class="btn btn-default"><fmt:message
                key="skip"/></a>
    </div>
    <c:if test="${avatarError.equals('empty')}">
        <p class="alert alert-warning"
           style="width: 250px;height: auto;padding: 5px"><fmt:message key="customer.cabinet.error.empty"/></p>
    </c:if>
    <c:if test="${avatarError.equals('notImage')}">
        <p class="alert alert-warning"
           style="width: 250px;height: auto;padding: 5px"><fmt:message key="customer.cabinet.error.not.image"/></p>
    </c:if>
    <c:if test="${avatarError.equals('tooBig')}">
        <p class="alert alert-warning"
           style="width: 250px;height: auto;padding: 5px"><fmt:message key="customer.cabinet.error.big"/></p>
    </c:if>
</my:page-pattern>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<my:page-pattern role="customer">
    <div class="container">
        <fmt:message key="customer.cabinet.change.avatar"/>
        <form role="form" action="<c:url value="/do/avatar/upload"/>" method="post" enctype="multipart/form-data">
            <input type="file" name="avatar"/>
            <fmt:message key="customer.cabinet.weight"/>
            <input type="submit" class="btn btn-link" value="<fmt:message key="avatar.upload.button"/> "/>
        </form>
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
    </div>
</my:page-pattern>
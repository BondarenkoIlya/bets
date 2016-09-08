<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<fmt:bundle basename="i18n">
    <fmt:message key="avatar.upload.button" var="upload_button"/>
    <fmt:message key="avatar.upload" var="upload"/>
</fmt:bundle>

<c:url value="/do/avatar/upload" var="avatar_upload_url"/>

<my:page-pattern role="stetTwo">
<div class="container">
    ${upload}

    <form role="form" action="${avatar_upload_url}" method="post" enctype="multipart/form-data">
        <input type="file" name="avatar"/>
        <input type="submit" class="btn btn-link" value="${upload_button}" />
    </form>
</div>

</my:page-pattern>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<my:page-pattern role="customer">
    <div class="container">
        <fmt:message key="castomer.cabinet.change.avatar"/>
        <form role="form" action="<c:url value="/do/avatar/upload"/>" method="post" enctype="multipart/form-data">
            <input type="file" name="avatar" />
            <input type="submit" class="btn btn-link" value="<fmt:message key="avatar.upload.button"/> "/>
        </form>
    </div>
</my:page-pattern>
<%--@elvariable id="match" type="com.epam.ilya.model.Match"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:bundle basename="i18n">

</fmt:bundle>



<div class="container">
    ${edit_match}
    ${match_sides} - ${match.firstSidesName} ${} ${match.secondSidesName}
</div>

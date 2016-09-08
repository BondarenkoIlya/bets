<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ attribute name="pageNumber" required="true" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="pageCount" required="true" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="url" required="true" rtexprvalue="true" type="java.lang.String" %>

<fmt:bundle basename="i18n">
</fmt:bundle>

<ul class="pagination">
    <c:forEach begin="1" end="${pageCount}" step="1" var="counter">
        <li><a href="<c:url value="${url}?pageNumber=${counter}"/>" <c:if
                test="${counter==pageNumber}"> class="active" </c:if> >${counter}</a></li>
    </c:forEach>
</ul>


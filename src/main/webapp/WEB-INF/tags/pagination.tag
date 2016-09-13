<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="pageNumber" required="true" type="java.lang.Integer" %>
<%@ attribute name="pageCount" required="true" type="java.lang.Integer" %>
<%@ attribute name="url" required="true" %>

<ul class="pagination">
    <c:if test="${pageCount<=8}">
        <c:forEach begin="1" end="${pageCount}" step="1" var="counter">
            <li <c:if test="${counter==pageNumber}"> class="active" </c:if>><a
                    href="<c:url value="${url}?pageNumber=${counter}"/>">${counter}</a></li>
        </c:forEach>
    </c:if>
    <c:if test="${pageCount>8}">
        <c:if test="${pageNumber<5}">
            <c:forEach begin="1" end="5" step="1" var="counter">
                <li <c:if test="${counter==pageNumber}"> class="active" </c:if>><a
                        href="<c:url value="${url}?pageNumber=${counter}"/>">${counter}</a></li>
            </c:forEach>
            <li class="disabled"><a>...</a></li>
            <li><a href="<c:url value="${url}?pageNumber=${pageCount}"/>">${pageCount}</a></li>
        </c:if>
        <c:if test="${pageNumber>=5}">
            <c:if test="${pageNumber<(pageCount-3)}">
                <li><a href="<c:url value="${url}?pageNumber=1"/>">1</a></li>
                <li class="disabled"><a>...</a></li>
                <c:forEach begin="${pageNumber-2}" end="${pageNumber+2}" step="1" var="counter">
                    <li <c:if test="${counter==pageNumber}"> class="active" </c:if>><a
                            href="<c:url value="${url}?pageNumber=${counter}"/>">${counter}</a></li>
                </c:forEach>
                <li class="disabled"><a>...</a></li>
                <li><a href="<c:url value="${url}?pageNumber=${pageCount}"/>">${pageCount}</a></li>
            </c:if>
            <c:if test="${pageNumber>=(pageCount-3)}">
                <li><a href="<c:url value="${url}?pageNumber=1"/>">1</a></li>
                <li class="disabled"><a>...</a></li>
                <c:forEach begin="${pageCount-4}" end="${pageCount}" step="1" var="counter">
                    <li <c:if test="${counter==pageNumber}"> class="active" </c:if>><a
                            href="<c:url value="${url}?pageNumber=${counter}"/>">${counter}</a></li>
                </c:forEach>
            </c:if>
        </c:if>
    </c:if>
</ul>


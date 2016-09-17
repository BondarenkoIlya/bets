<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<c:url var="add_to_customer_balance_url" value="/do/customer/replenish"/>
<c:url var="back_url" value="/do/bookmaker/home"/>
<c:url var="no_avatar" value="/images/noavatar.png"/>

<%--@elvariable id="customer" type="com.epam.ilya.model.Customer"--%>

<my:page-pattern role="bookmaker">
    <fmt:message key="bookmaker.customer.edit" var="customer_edition"/>
    <fmt:message key="bookmaker.customer.edit.name" var="customer_edition_name"/>
    <fmt:message key="bookmaker.customer.edit.balance" var="customer_edition_balance"/>
    <fmt:message key="bookmaker.customer.edit.add.balance" var="add_to_customer"/>
    <fmt:message key="bookmaker.home.add.sum" var="sum"/>
    <fmt:message key="bookmaker.home.add.button" var="add_button"/>
    <fmt:message key="bookmaker.home.back.button" var="back_button"/>
    <fmt:message key="bookmaker.home.customers.replenish.success.message" var="replenish_success_masage"/>
    <fmt:message key="bookmaker.home.customers.replenish.error.message" var="replenish_error_masage"/>
    <div class="container">
            ${customer_edition}<br/>
            ${customer_edition_name}-${customer.firstName} ${customer.lastName}<br/>

        <c:if test="${ not empty customer.avatar}">
            <img src="<c:url value="/image/avatar?customer_id=${customer.id}"/>" style="width: auto;height: 250px"
                 class="img-responsive">
        </c:if>
        <c:if test="${empty customer.avatar}">
            <img src="${no_avatar}" class="img-responsive">
        </c:if>
            ${customer_edition_balance} - ${customer.personsPurse.balance.getAmount().doubleValue()}<br/>

        <form role="form" action="${add_to_customer_balance_url}" method="post">
            <p>${add_to_customer}</p>
            <input type="number" id="addToCustomer" required name="addToCustomerBalance" value=""
                   placeholder="${sum}">Тг
            <input type="hidden" name="customersId" value="${customer.id}">
            <input type="submit" class="btn btn-default" value=${add_button}><br/>
        </form>
        <c:if test="${add_message.equals('success')}">
            <p class="alert alert-success"
               style="width: 250px;height: 30px;padding: 5px">${replenish_success_masage}</p>
        </c:if>
        <c:if test="${add_message.equals('error')}">
            <p class="alert alert-warning" style="width: 250px;height: 30px;padding: 5px">${replenish_error_masage}</p>
        </c:if>
        <button type="button" class="btn btn-default"><a
                href="${back_url}">${back_button}</a></button>
    </div>
</my:page-pattern>

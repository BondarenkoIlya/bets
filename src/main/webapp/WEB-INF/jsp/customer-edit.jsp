<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<fmt:bundle basename="i18n">
    <fmt:message key="bookmaker.customer.edit" var="customer_edition"/>
    <fmt:message key="bookmaker.customer.edit.name" var="customer_edition_name"/>
    <fmt:message key="bookmaker.customer.edit.balance" var="customer_edition_balance"/>
    <fmt:message key="bookmaker.customer.edit.add.balance" var="add_to_customer"/>
    <fmt:message key="bookmaker.customer.edit.remove.balance" var="remove_from_customer"/>
    <fmt:message key="bookmaker.home.add.sum" var="sum"/>
    <fmt:message key="bookmaker.home.add.button" var="add_button"/>
    <fmt:message key="bookmaker.home.remove.button" var="remove_button"/>
    <fmt:message key="bookmaker.home.back.button" var="back_button"/>
    <fmt:message key="bookmaker.home.customers.replenish.success.message" var="replenish_success_masage"/>
    <fmt:message key="bookmaker.home.customers.replenish.error.message" var="replenish_error_masage"/>

</fmt:bundle>

<c:url var="add_to_customer_balance_url" value="/do/replenish/customer"/>
<c:url var="remove_from_customer_balance_url" value="/do/withdraw/customer"/>
<c:url var="back_url" value="/do/bookmaker-home"/>

<my:page-pattern role="bookmaker">
    <div>
            ${customer_edition}<br/>
            ${customer_edition_name}-${customer.firstName} ${customer.lastName}<br/>
            ${customer_edition_balance} - ${customer.personsPurse.balance.getAmount().doubleValue()}<br/>

        <form role="form" action="${add_to_customer_balance_url}" method="post">
            <p>${add_to_customer}</p>
            <input type="number" id="addToCustomer" required name="addToCustomerBalance" value=""
                   placeholder="${sum}">
            <input type="hidden" name="customersId" value="${customer.id}">
            <input type="submit" class="btn btn-default" value=${add_button}><br/>
        </form>
        <form role="form" action="${remove_from_customer_balance_url}" method="post">
            <p>${remove_from_customer}</p>
            <input type="number" id="removeFromCustomer" required name="removeFromCustomerBalance" value=""
                   placeholder="${sum}">
            <input type="hidden" name="customersId" value="${customer.id}">
            <input type="submit" class="btn btn-default" value=${remove_button}><br/>
        </form>

        <button type="button" class="btn btn-default"><a
                href="${back_url}">${back_button}</a></button>
    </div>
</my:page-pattern>

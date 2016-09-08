<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<fmt:bundle basename="i18n">
    <fmt:message key="bookmaker.home.customers" var="customers_editor"/>
    <fmt:message key="bookmaker.home.customers.name.first" var="customer_first_name"/>
    <fmt:message key="bookmaker.home.customers.name.last" var="customer_last_name"/>
    <fmt:message key="bookmaker.home.customers.email" var="customer_email"/>
    <fmt:message key="bookmaker.home.customers.balance" var="customer_balance"/>
    <fmt:message key="bookmaker.home.customers.search" var="search"/>
    <fmt:message key="bookmaker.home.customers.search.button" var="search_button"/>
    <fmt:message key="bookmaker.home.customers.search.error.message" var="search_error_massage"/>
    <fmt:message key="bookmaker.home.customers.replenish.success.message" var="replenish_success_masage"/>
    <fmt:message key="bookmaker.home.customers.replenish.error.message" var="replenish_error_masage"/>
    <fmt:message key="bookmaker.home.add.button" var="add_button"/>
    <fmt:message key="bookmaker.home.add.sum" var="sum"/>
    <fmt:message key="bookmaker.home.add.to.bookmaker" var="add_to_bookmaker"/>
    <fmt:message key="bookmaker.home.customers.edit.button" var="edit_button"/>
</fmt:bundle>

<c:url var="search_url" value="/do/customer/find"/>
<c:url var="add_to_bookmaker_balance_url" value="/do/bookmaker/replenish"/>
<c:url var="add_to_customer_balance_url" value="/do/customer/replenish"/>

<%--@elvariable id="customers" type="com.epam.ilya.model.PaginatedList"--%>

<my:page-pattern role="bookmaker">
    <div>
        <h3>${customers_editor}</h3>
        <c:if test="${add_message.equals('success')}">
            <p class="alert alert-success"
               style="width: 250px;height: 30px;padding: 5px">${replenish_success_masage}</p>
        </c:if>
        <c:if test="${add_message.equals('error')}">
            <p class="alert alert-warning" style="width: 250px;height: 30px;padding: 5px">${replenish_error_masage}</p>
        </c:if>
        <div class="container">
            <form role="form" action="${search_url}" method="post">
                <input type="search" required name="search" value="" placeholder="${search}">
                <input type="submit" class="btn btn-default" value=${search_button}><br/>
            </form>
            <div class="row">
                <div class="col-md-6 col-lg-6 col-sm-6">
                    <c:if test="${empty searchError}">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>${customer_first_name}</th>
                                <th>${customer_last_name}</th>
                                <th>${customer_email}</th>
                                <th>${customer_balance}</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${customers}" var="customer">
                                <tr>
                                    <td>${customer.getFirstName()}</td>
                                    <td>${customer.getLastName()}</td>
                                    <td>${customer.getEmail()}</td>
                                    <td>${customer.getPersonsPurse().getBalance().getAmount().doubleValue()}</td>
                                    <td><a href="<c:url value="/do/customer/edit?id=${customer.id}"/> ">${edit_button}
                                    </a></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <my:pagination pageCount="${customers.pageCount}" pageNumber="${customers.pageNumber}" url="do/bookmaker/home"/>
                    </c:if>
                    <c:if test="${not empty searchError}">
                        <p class="alert alert-info"
                           style="width: 250px;height: 30px;padding: 5px">${search_error_massage}</p>
                    </c:if>
                </div>
                <div class="col-md-6 col-lg-6 col-sm-6">
                    <form role="form" action="${add_to_bookmaker_balance_url}" method="post">
                        <p>${add_to_bookmaker}</p>
                        <input type="number" id="addToBookmaker" required name="addToBookmakerBalance" value=""
                               placeholder="${sum}">
                        <input type="submit" class="btn btn-default" value=${add_button}><br/>
                    </form>
                    <c:if test="${message.equals('success')}">
                        <p class="alert alert-success"
                           style="width: 250px;height: 30px;padding: 5px">${replenish_success_masage}</p>
                    </c:if>
                    <c:if test="${message.equals('error')}">
                        <p class="alert alert-warning"
                           style="width: 250px;height: 30px;padding: 5px">${replenish_error_masage}</p>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</my:page-pattern>
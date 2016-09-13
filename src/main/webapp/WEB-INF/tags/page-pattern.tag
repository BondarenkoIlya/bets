<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="role" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<fmt:setBundle basename="i18n" scope="request"/>
    <fmt:message key="pattern.welcome" var="welcome"/>
    <fmt:message key="pattern.step.one" var="step_one"/>
    <fmt:message key="pattern.step.two" var="step_two"/>
    <fmt:message key="pattern.inset.home" var="home_inset"/>
    <fmt:message key="pattern.inset.bets" var="bets_inset"/>
    <fmt:message key="pattern.inset.cabinet" var="cabinet_inset"/>
    <fmt:message key="pattern.logout" var="logout"/>
    <fmt:message key="pattern.customer.balance" var="balance"/>
    <fmt:message key="pattern.customer.name" var="name"/>
    <fmt:message key="pattern.bookmaker.name" var="bookmaker_name"/>
    <fmt:message key="pattern.bookmaker.inset.match" var="match_editor_inset"/>
    <fmt:message key="pattern.bookmaker.inset.home" var="bookmaker_home_inset"/>


<c:url value="/do/home" var="home"/>
<c:url value="/do/cabinet" var="cabinet"/>
<c:url value="/do/bookmaker/home" var="bookmaker_home"/>
<c:url value="/do/matches/edit/active" var="matches_editor"/>
<c:url value="/do/bets/active" var="bets"/>
<c:url value="/do/locale?locale=en" var="en_locale_url"/>
<c:url value="/do/locale?locale=ru" var="ru_locale_url"/>
<c:url value="/images/noavatar.png" var="no_avatar" />

<html>
<head>
    <title>Bets</title>
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7/css/bootstrap.css"/>">
    <script src="<c:url value="/webjars/jquery/1.11.1/jquery.js"/>"></script>
</head>
<body>
<%--@elvariable id="loggedCustomer" type="com.epam.ilya.model.Customer"--%>
<%--@elvariable id="bookmaker" type="com.epam.ilya.model.Person"--%>
<div id="header">
    <div align="center" style="width: 1200px;margin: auto; color: #66afe9">
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <c:if test="${role.equals('customer')}">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-1 col-lg-1"><a href="${home}">${home_inset}</a></div>
                                <div class="col-md-1 col-lg-1"><a href="${cabinet}">${cabinet_inset}</a></div>
                                <div class="col-md-1 col-lg-1"><a href="${bets}">${bets_inset}</a></div>
                                <div class="col-md-1 col-lg-1"><a
                                        href="<c:url value="/do/logout"/>">${logout}</a>
                                </div>
                                <div class="col-md-2 col-lg-2 ">
                                    <ruby>${loggedCustomer.firstName}
                                        <rt>${name}</rt>
                                    </ruby>
                                </div>
                                <div class="col-md-2 col-lg-2">
                                    <c:if test="${not empty loggedCustomer.avatar}">
                                        <img src="<c:url value="/image/avatar"/>" style="width: auto;height: 80px"
                                             class="img-responsive">
                                    </c:if>
                                    <c:if test="${empty loggedCustomer.avatar}">
                                        <img src="${no_avatar}" style="width: 80px;height: 80px" class="img-responsive">
                                    </c:if>
                                </div>
                                <div class="col-md-1 col-lg-1">
                                    <ruby>${loggedCustomer.personsPurse.balance.getAmount().doubleValue()}
                                        <rt>${balance}</rt>
                                    </ruby>
                                </div>
                                <div class="col-md-1 col-lg-1">
                                    <form role="form" action="${en_locale_url}" method="post">
                                        <input type="submit" class="button btn-link" value="English"/>
                                    </form>
                                    /
                                    <form role="form" action="${ru_locale_url}" method="post">
                                        <input type="submit" class="button btn-link" value="Русский"/>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${role.equals('bookmaker')}">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-2 col-lg-2"><a href="${bookmaker_home}">${bookmaker_home_inset}</a>
                                </div>
                                <div class="col-md-4 col-lg-4"><a href="${matches_editor}">${match_editor_inset}</a>
                                </div>
                                <div class="col-md-2 col-lg-2"><a
                                        href="<c:url value="/do/logout"/>">${logout}</a>
                                </div>
                                <div class="col-md-2 col-lg-2 ">
                                    <ruby>${bookmaker.firstName}
                                        <rt>${bookmaker_name}</rt>
                                    </ruby>
                                </div>
                                <div class="col-md-1 col-lg-1">
                                    <ruby>${bookmaker.personsPurse.balance.amount.doubleValue()}
                                        <rt>${balance}</rt>
                                    </ruby>
                                </div>
                                <div class=" col-md-1 col-lg-1">
                                    <form role="form" action="${en_locale_url}" method="post">
                                        <input type="submit" class="button btn-link" value="English"/>
                                    </form>
                                    /
                                    <form role="form" action="${ru_locale_url}" method="post">
                                        <input type="submit" class="button btn-link" value="Русский"/>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${role.equals('guest')}">
                        <div class="row">
                            <div class="col-md-10 col-lg-10">
                                <h3>${welcome}</h3>
                            </div>
                            <div class=" col-md-1 col-lg-1">
                                <form role="form" action="${en_locale_url}" method="post">
                                    <input type="submit" class="button btn-link" value="English"/>
                                </form>
                                /
                                <form role="form" action="${ru_locale_url}" method="post">
                                    <input type="submit" class="button btn-link" value="Русский"/>
                                </form>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${role.equals('stepOne')}">
                        <h3>${step_one}</h3>
                    </c:if>
                    <c:if test="${role.equals('stepTwo')}">
                        <h3>${step_two}</h3>
                    </c:if>
                </div>
            </div>
        </nav>
    </div>
    <jsp:invoke fragment="header"/>
</div>

<div id="body" style="height: 100%">
    <jsp:doBody/>
</div>

<div id="footer" style="flex: 0 0 auto">
    <footer class="modal-footer" style="height: 80px; position: relative;">
        <ruby>Bondarenko Ilya
            <rt>Author</rt>
        </ruby>
    </footer>
    <jsp:invoke fragment="footer"/>
</div>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ attribute name="role" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<fmt:bundle basename="i18n">
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
</fmt:bundle>

<c:url value="/do/home" var="home"/>
<c:url value="/do/cabinet" var="cabinet"/>
<c:url value="/do/bookmaker/home" var="bookmaker_home"/>
<c:url value="/do/match/edit" var="match_editor"/>
<c:url value="/do/bets" var="bets"/>


<html>
<head>
    <title>Bets</title>
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7/css/bootstrap.css"/>">
    <script src="<c:url value="/webjars/jquery/1.11.1/jquery.js"/>"></script>
</head>
<body>
<div id="header">
    <div align="center" style="width: 1200px;margin: auto; color: #66afe9">
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <c:if test="${role.equals('customer')}">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-2 col-lg-2"><a href="${home}">${home_inset}</a></div>
                                <div class="col-md-2 col-lg-2"><a href="${cabinet}">${cabinet_inset}</a></div>
                                <div class="col-md-2 col-lg-2"><a href="${bets}">${bets_inset}</a></div>
                                <div class="col-md-2 col-lg-2"><a
                                        href="<c:url value="/do/logout"/>">${logout}</a>
                                </div>
                                <div class="col-md-2 col-lg-2 "><a>
                                    <ruby>${loggedCustomer.getFirstName()}
                                        <rt>${name}</rt>
                                    </ruby>
                                </a></div>
                                <div class="col-md-2 col-lg-2"><a>
                                    <ruby>${loggedCustomer.getPersonsPurse().getBalance().getAmount().doubleValue()}
                                        <rt>${balance}</rt>
                                    </ruby>
                                </a></div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${role.equals('bookmaker')}">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-2 col-lg-2"><a href="${bookmaker_home}">${bookmaker_home_inset}</a>
                                </div>
                                <div class="col-md-4 col-lg-4"><a href="${match_editor}">${match_editor_inset}</a></div>
                                <div class="col-md-2 col-lg-2"><a
                                        href="<c:url value="/do/logout"/>">${logout}</a>
                                </div>
                                <div class="col-md-2 col-lg-2 "><a>
                                    <ruby>${bookmaker.getFirstName()}
                                        <rt>${bookmaker_name}</rt>
                                    </ruby>
                                </a></div>
                                <div class="col-md-2 col-lg-2"><a>
                                    <ruby>${bookmaker.getPersonsPurse().getBalance().getAmount().doubleValue()}
                                        <rt>${balance}</rt>
                                    </ruby>
                                </a></div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${role.equals('guest')}">
                        <h3>${welcome}</h3>
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
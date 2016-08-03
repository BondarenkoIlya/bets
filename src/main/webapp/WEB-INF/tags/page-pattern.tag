<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<html>
<head>
    <title>Bets</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.css"/>">
</head>
<body>
<div id="header">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-3">
                <div class="icon-bar " >Bets</div>
            </div>
            <div class="col-md-6">
                <div class="label-success"></div>
            </div>
            <div class="col-md-3 ">
                <div class="top-right">User</div>
            </div>
        </div>
    </div>
    <jsp:invoke fragment="header"/>
</div>
<div id="body">
    <jsp:doBody/>
</div>

<div id="footer">
    <footer class="modal-footer" style="position: relative;" >
        <ruby><h2>Bondarenko Ilya</h2>
            <rt>Author</rt>
        </ruby>
    </footer>
    <jsp:invoke fragment="footer"/>
</div>
</body>
</html>
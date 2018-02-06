<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page session="true" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request"/>

<!DOCTYPE html>
<script type="text/javascript">
    window.onload = function () {

        var getUrlParameter = function getUrlParameter(sParam) {
            var sPageURL = decodeURIComponent(window.location.search.substring(1)),
                sURLVariables = sPageURL.split('&'),
                sParameterName,
                i;

            for (i = 0; i < sURLVariables.length; i++) {
                sParameterName = sURLVariables[i].split('=');

                if (sParameterName[0] === sParam) {
                    return sParameterName[1] === undefined ? true : sParameterName[1];
                }
            }
        };

        var sid = getUrlParameter('sid');

        $.cookie("sid", sid, {expires: 14});

        $('#loginForm').submit();
    }
</script>
<html>
<body>

<c:url var="loginUrl" value="/login"/>

<form action="${loginUrl}" method="POST" autocomplete="off" name='loginForm' id="loginForm">

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <div class="form-group has-feedback">
        <input type="hidden" id="username" name="username" value="${domainName}" class="form-control"
               placeholder="Domain Name" autocomplete="off" required autofocus>
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
    </div>

    <div class="form-group has-feedback">
        <input type="hidden" name="password" value="admin" class="form-control" placeholder="Password"
               autocomplete="off" required>
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
    </div>
</form>

<script src="${cp}/resources/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${cp}/resources/plugins/cookie/jquery.cookie.js"></script>

<script type="text/javascript">
    window.onload = function () {

        var getUrlParameter = function getUrlParameter(sParam) {
            var sPageURL = decodeURIComponent(window.location.search.substring(1)),
                sURLVariables = sPageURL.split('&'),
                sParameterName,
                i;

            for (i = 0; i < sURLVariables.length; i++) {
                sParameterName = sURLVariables[i].split('=');

                if (sParameterName[0] === sParam) {
                    return sParameterName[1] === undefined ? true : sParameterName[1];
                }
            }
        };

        var sid = getUrlParameter('sid');

        $.cookie("sid", sid, {expires: 14});

        $('#loginForm').submit();
    }
</script>

</body>
</html>

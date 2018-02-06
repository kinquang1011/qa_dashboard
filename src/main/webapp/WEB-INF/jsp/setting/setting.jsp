<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags"
           prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:set var="cp"
       value="${pageContext.request.servletContext.contextPath}"
       scope="request"/>


<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>QA Data Dashboard</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="${cp}/resources/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <!-- DataTables -->
    <link rel="stylesheet" href="${cp}/resources/plugins/datatables/dataTables.bootstrap.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${cp}/resources/dist/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="${cp}/resources/dist/css/skins/_all-skins.min.css">
    <!-- bootstrap datepicker -->
    <link rel="stylesheet" href="${cp}/resources/plugins/datepicker/datepicker3.css">
    <!-- daterange picker -->
    <link rel="stylesheet" href="${cp}/resources/plugins/daterangepicker/daterangepicker.css">
    <link rel="stylesheet" href="${cp}/resources/plugins/bootstrap-select/dist/css/bootstrap-select.css">
    <link rel="stylesheet" href="${cp}/resources/plugins/foo-table/footable.bootstrap.min.css">


    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="../pageconstant/header.jsp" %>
    <%@ include file="../pageconstant/mainsidebar.jsp" %>

    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-xs-12">
                    <div class="nav-tabs-custom">
                        <ul id="ulmenu" class="nav nav-tabs">
                            <li id="ligameconfig" class="active"><a href="#gameconfig" data-toggle="tab">Game Config</a>
                            </li>
                            <li id="liusersetting"><a href="#usersetting" data-toggle="tab">User</a></li>
                        </ul>

                        <input id="_csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <div class="tab-content">
                            <div class="tab-pane active" id="gameconfig">
                                <%@ include file="./game/game.jsp" %>
                            </div>

                            <div class="tab-pane" id="usersetting">
                                <%@ include file="./user/user.jsp" %>
                            </div>

                        </div>
                    </div>
                    <!-- /.nav-tabs-custom -->
                </div>
                <!-- /.col -->
            </div>
        </section>
    </div>
</div>
<!-- ./wrapper -->

<!-- jQuery 2.2.3 -->
<script src="${cp}/resources/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${cp}/resources/bootstrap/js/bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="${cp}/resources/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="${cp}/resources/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="${cp}/resources/dist/js/app.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${cp}/resources/dist/js/demo.js"></script>
<!-- date-range-picker -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.min.js"></script>
<script src="${cp}/resources/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap datepicker -->
<script src="${cp}/resources/plugins/datepicker/bootstrap-datepicker.js"></script>
<!-- page script -->
<script src="${cp}/resources/pagejs/header.js"></script>
<script src="${cp}/resources/pagejs/setting_game.js"></script>
<script src="${cp}/resources/pagejs/setting_user.js"></script>
<script src="${cp}/resources/pagejs/mainsidebar.js"></script>

<script src="${cp}/resources/plugins/bootstrap-select/dist/js/bootstrap-select.js"></script>
<script src="https://code.highcharts.com/stock/highstock.js"></script>
<script src="https://code.highcharts.com/stock/modules/exporting.js"></script>

<script src="${cp}/resources/plugins/cookie/jquery.cookie.js"></script>
<script src="${cp}/resources/plugins/foo-table/footable.min.js"></script>
</body>
</html>
<%@ page import="edu.fpt.comp1640.model.user.User" %>
<%@ page import="edu.fpt.comp1640.model.user.Coordinator" %>

<%
    Object _user = session.getAttribute("user");
    if (_user == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    User user = (User) _user;
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8"/>
    <link rel="apple-touch-icon" sizes="76x76" href="assets/img/apple-icon.png">
    <link rel="icon" type="image/png" href="./assets/img/favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>Submissions</title>
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no'
          name='viewport'/>
    <!--     Fonts and icons     -->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700,200" rel="stylesheet"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css"/>
    <!-- CSS Files -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="assets/css/light-bootstrap-dashboard.css?v=2.0.1" rel="stylesheet"/>
    <!-- CSS Just for demo purpose, don't include it in your project -->
    <link rel="stylesheet" href="assets/css/demo.css"/>
</head>

<body>
<div class="wrapper">
    <%@ include file="template/sidebar.jsp" %>
    <div class="main-panel">
        <%@ include file="template/navbar.jsp" %>
        <div class="content">
            <div class="container-fluid">
                <h4 id="submit-name"></h4>
                <h5 id="submit-time"></h5>

                <table role="presentation" class="table table-striped">
                    <tbody id="preview" class="files"></tbody>
                </table>

                <br>
                <hr>
                <h5>Coordinators' comments</h5>
                <form class="form-inline col-12">
                    <div class="form-group mb-2">
                        <label for="txt-comment" class="sr-only"></label>
                        <input type="text" class="form-control-plaintext" id="txt-comment" value="">
                    </div>
                    <button id="btn-send" type="submit" class="btn btn-primary mb-2">Send</button>
                </form>

                <table role="presentation" class="table table-striped">
                    <tbody class="files" id="comments">
                    <tr>
                        <td><b>A Coordinator</b></td>
                        <td>Some comment</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>

<script src="assets/js/core/jquery.3.2.1.min.js" type="text/javascript"></script>
<script src="assets/js/core/popper.min.js" type="text/javascript"></script>
<script src="assets/js/core/bootstrap.min.js" type="text/javascript"></script>
<script src="assets/js/plugins/bootstrap-switch.js"></script>
<script src="assets/js/plugins/chartist.min.js"></script>
<script src="assets/js/plugins/bootstrap-notify.js"></script>
<script src="assets/js/plugins/jquery-jvectormap.js" type="text/javascript"></script>
<script src="assets/js/plugins/moment.min.js"></script>
<script src="assets/js/plugins/bootstrap-datetimepicker.js"></script>
<script src="assets/js/plugins/sweetalert2.min.js" type="text/javascript"></script>
<script src="assets/js/plugins/bootstrap-tagsinput.js" type="text/javascript"></script>
<script src="assets/js/plugins/nouislider.js" type="text/javascript"></script>
<script src="assets/js/plugins/bootstrap-selectpicker.js" type="text/javascript"></script>
<script src="assets/js/plugins/jquery.validate.min.js" type="text/javascript"></script>
<script src="assets/js/plugins/jquery.bootstrap-wizard.js"></script>
<script src="assets/js/plugins/bootstrap-table.js"></script>
<script src="assets/js/plugins/jquery.dataTables.min.js"></script>
<script src="assets/js/plugins/fullcalendar.min.js"></script>
<script src="assets/js/light-bootstrap-dashboard.js?v=2.0.1" type="text/javascript"></script>
<script src="assets/js/article.js"></script>
</html>

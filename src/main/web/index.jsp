<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Object u = request.getSession().getAttribute("user");
    if (u != null) {
        response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
    }
%>

<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8"/>
    <link rel="apple-touch-icon" sizes="76x76" href="assets/img/apple-icon.png">
    <link rel="icon" type="image/png" href="./assets/img/favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>Login</title>
    <meta name='viewport'
          content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no'/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,700,200"/>
    <link rel="stylesheet" href="./assets/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="assets/css/light-bootstrap-dashboard.css?v=2.0.1"/>
    <link rel="stylesheet" href="assets/css/demo.css"/>
</head>

<body>
<div class="wrapper wrapper-full-page">
    <div class="full-page  section-image" data-color="black" data-image="./assets/img/university-of-greenwich.jpg">
        <div class="content">
            <div class="container">
                <div class="col-lg-4 col-md-6 col-sm-8 ml-auto mr-auto">
                    <form class="form">
                        <div class="card card-login card-hidden">
                            <div class="card-header ">
                                <h3 class="header text-center">Login</h3>
                            </div>
                            <div class="card-body ">
                                <div class="card-body">
                                    <div class="form-group">
                                        <label>User name</label>
                                        <input id="signin-username" type="text" name="username"
                                               placeholder="Enter username" class="form-control">
                                    </div>
                                    <div class="form-group">
                                        <label>Password</label>
                                        <input id="signin-password" type="password" name="password"
                                               placeholder="Password" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer ml-auto mr-auto">
                                <button id="btn-signin" type="submit" class="btn btn-warning btn-wd">Login</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript" src="assets/js/core/jquery.3.2.1.min.js"></script>
<script type="text/javascript" src="assets/js/core/popper.min.js"></script>
<script type="text/javascript" src="assets/js/core/bootstrap.min.js"></script>
<script type="text/javascript" src="assets/js/plugins/bootstrap-switch.js"></script>
<script type="text/javascript" src="assets/js/plugins/chartist.min.js"></script>
<script type="text/javascript" src="assets/js/plugins/bootstrap-notify.js"></script>
<script type="text/javascript" src="assets/js/plugins/jquery-jvectormap.js"></script>
<script type="text/javascript" src="assets/js/plugins/moment.min.js"></script>
<script type="text/javascript" src="assets/js/plugins/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="assets/js/plugins/sweetalert2.min.js"></script>
<script type="text/javascript" src="assets/js/plugins/bootstrap-tagsinput.js"></script>
<script type="text/javascript" src="assets/js/plugins/nouislider.js"></script>
<script type="text/javascript" src="assets/js/plugins/bootstrap-selectpicker.js"></script>
<script type="text/javascript" src="assets/js/plugins/jquery.validate.min.js"></script>
<script type="text/javascript" src="assets/js/plugins/jquery.bootstrap-wizard.js"></script>
<script type="text/javascript" src="assets/js/light-bootstrap-dashboard.js?v=2.0.1"></script>
<script type="text/javascript" src="assets/js/demo.js"></script>

<script>
    $(document).ready(function () {
        demo.checkFullPageBackgroundImage();

        setTimeout(function ()  {
            // after 1000 ms we add the class animated to the login/register card
            $('.card').removeClass('card-hidden');
        }, 500)
    });

    const error = $("#error-msg");
    const alert = $("#alert");

    $("#btn-signin").on("click", e => {
        e.preventDefault();
        const username = $("#signin-username").val();
        const password = $("#signin-password").val();
        if (!(/^[a-zA-Z]\w{4,}$/).test(username)) {
            error.html("User name must starts with alphabet character and cannot be shorter than 5 characters!");
            alert.addClass("show");
            return;
        }

        if (password.length < 6) {
            error.html("Password cannot be shorter than 6 characters!");
            alert.addClass("show");
            return;
        }

        console.log("Start sign in request");
        $.ajax({
            url: "login",
            method: "POST",
            data: {username, password},
            success: (result, status, xhr) => {
                try {
                    const msg = JSON.parse(result).error;
                    if (msg) {
                        error.html(msg);
                        alert.addClass("show");
                        setTimeout(() => alert.removeClass("show"), 3000);
                    }
                } catch (e) {
                    window.location = "/dashboard.jsp"
                }
            }
        })
    });
</script>

</html>

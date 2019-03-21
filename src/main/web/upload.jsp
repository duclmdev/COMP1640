<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Upload</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,700,200"/>
    <link rel="stylesheet" href="./assets/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="assets/css/light-bootstrap-dashboard.css?v=2.0.1"/>
    <link rel="stylesheet" href="assets/css/demo.css"/>
    <link rel="stylesheet" href="assets/css/custom-upload.css"/>
</head>

<body>
<div>

    <jsp:include page="template/sidebar.jsp">
        <jsp:param name="user" value=""/>
    </jsp:include>
    <div class="main-panel">
        <%@ include file="template/navbar.jsp" %>

        <div class="content">
            <ul class="nav nav-tabs" id="myTab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab"
                       aria-controls="home" aria-selected="true">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab"
                       aria-controls="profile" aria-selected="false">Profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="contact-tab" data-toggle="tab" href="#contact" role="tab"
                       aria-controls="contact" aria-selected="false">Contact</a>
                </li>
            </ul>
            <div class="tab-content" id="myTabContent">
                <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                    <h3>Choose File to Upload in Server</h3>
                    <form action="upload" method="post" enctype="multipart/form-data">
                        <input type="file" name="file"/>
                        <input type="submit" value="upload"/>
                    </form>
                </div>
                <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">...</div>
                <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">...</div>
            </div>
        </div>

        <%@ include file="template/footer.jsp" %>
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
</html>

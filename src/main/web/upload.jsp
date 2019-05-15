<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="edu.fpt.comp1640.model.user.User" %>

<%
    Object _user = session.getAttribute("user");
    if (_user == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    User user = (User) _user;
%>

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
    <link rel="stylesheet" href="assets/css/upload.css"/>
</head>

<body>
<div>
    <%@ include file="template/sidebar.jsp" %>
    <div class="main-panel">
        <%@ include file="template/navbar.jsp" %>

        <div class="content">
            <form id="fileupload" enctype="multipart/form-data">
                <div class="form-row">
                    <div class="form-group col-lg-6 col-md-8">
                        <label for="txt-name">Submission name</label>
                        <input type="text" class="form-control" id="txt-name" placeholder="Enter your submission's name">
                    </div>
                    <div class="form-group col-lg-6 col-md-8">
                        <label for="txt-publish">Publish Event</label>
                        <select id="txt-publish" class="form-control">
                            <option value="1">Annual Magazine 2019</option>
                            <option value="2" selected>Special Magazine 2019</option>
                        </select>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-lg-7 col-12">
                        <span class="btn btn-success btn-file">
                            <i class="fa fa-plus"></i>
                            <span>Add files...</span>
                            <input id="files" type="file" name="files[]" multiple>
                        </span>
                        <button id="btn-submit" class="btn btn-primary start" data-toggle="modal" data-target="#term-modal">
                            <i class="fa fa-upload"></i>
                            <span>Start upload</span>
                        </button>
<%--                        <button id="btn-cancel" class="btn btn-warning cancel">--%>
<%--                            <i class="fa fa-ban"></i>--%>
<%--                            <span>Cancel upload</span>--%>
<%--                        </button>--%>
                    </div>
                </div>
                <table role="presentation" class="table table-striped">
                    <tbody id="preview" class="files"></tbody>
                </table>
            </form>
        </div>
    </div>
</div>


<div class="modal fade" id="term-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Term &amp; Condition</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div id="term-content" class="modal-body"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="start-upload">Agree & Upload</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="exampleLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div id="modal-content" class="modal-body">
                <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                    <ol id="carousel-indicators" class="carousel-indicators">
                        <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                        <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                        <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                    </ol>
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="" class="d-block w-100" alt="...">
                        </div>
                        <div class="carousel-item">
                            <img src="" class="d-block w-100" alt="...">
                        </div>
                        <div class="carousel-item">
                            <img src="" class="d-block w-100" alt="...">
                        </div>
                    </div>
                    <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="assets/js/core/jquery.3.2.1.min.js"></script>
<script type="text/javascript" src="assets/js/core/popper.min.js"></script>
<script type="text/javascript" src="assets/js/core/bootstrap.min.js"></script>
<script type="text/javascript" src="assets/js/plugins/bootstrap-switch.js"></script>
<script type="text/javascript" src="assets/js/plugins/bootstrap-notify.js"></script>
<script type="text/javascript" src="assets/js/plugins/moment.min.js"></script>
<script type="text/javascript" src="assets/js/plugins/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="assets/js/plugins/sweetalert2.min.js"></script>
<script type="text/javascript" src="assets/js/plugins/bootstrap-selectpicker.js"></script>
<script type="text/javascript" src="assets/js/plugins/jquery.validate.min.js"></script>
<script type="text/javascript" src="assets/js/plugins/jquery.bootstrap-wizard.js"></script>
<script type="text/javascript" src="assets/js/light-bootstrap-dashboard.js?v=2.0.1"></script>
<script type="text/javascript" src="assets/js/upload.js"></script>

</body>
</html>

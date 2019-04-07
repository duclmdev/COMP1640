<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="edu.fpt.comp1640.model.user.User" %>

<%
    Object _user = session.getAttribute("user");
    if (_user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
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
    <link rel="stylesheet" href="assets/css/demo.css"/>
    <link rel="stylesheet" href="assets/css/custom-upload.css"/>
    <link rel="stylesheet" href="css/style.css">
    <!-- blueimp Gallery styles -->
    <link rel="stylesheet" href="https://blueimp.github.io/Gallery/css/blueimp-gallery.min.css">
    <!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
    <link rel="stylesheet" href="css/jquery.fileupload.css">
    <link rel="stylesheet" href="css/jquery.fileupload-ui.css">
    <!-- CSS adjustments for browsers with JavaScript disabled -->
    <noscript><link rel="stylesheet" href="css/jquery.fileupload-noscript.css"></noscript>
    <noscript><link rel="stylesheet" href="css/jquery.fileupload-ui-noscript.css"></noscript>
</head>

<body>
<div>

    <%@ include file="template/sidebar.jsp" %>
    <div class="main-panel">
        <%@ include file="template/navbar.jsp" %>

        <div class="content">
            <form id="fileupload" action="${pageContext.request.contextPath}/upload" method="POST"
                  enctype="multipart/form-data">
                <!-- Redirect browsers with JavaScript disabled to the origin page -->
                <noscript><input type="hidden" name="redirect" value="https://blueimp.github.io/jQuery-File-Upload/">
                </noscript>
                <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
                <div class="row fileupload-buttonbar">
                    <div class="col-lg-7">
                        <!-- The fileinput-button span is used to style the file input field as button -->
                        <span class="btn btn-success fileinput-button">
                    <i class="fa fa-plus"></i>
                    <span>Add files...</span>
                    <input type="file" name="files[]" multiple>
                </span>
                        <button type="submit" class="btn btn-primary start">
                            <i class="fa fa-upload"></i>
                            <span>Start upload</span>
                        </button>
                        <button type="reset" class="btn btn-warning cancel">
                            <i class="fa fa-ban"></i>
                            <span>Cancel upload</span>
                        </button>
                        <button type="button" class="btn btn-danger delete">
                            <i class="fa fa-trash"></i>
                            <span>Delete</span>
                        </button>
                        <input type="checkbox" class="toggle">
                        <!-- The global file processing state -->
                        <span class="fileupload-process"></span>
                    </div>
                    <!-- The global progress state -->
                    <div class="col-lg-5 fileupload-progress fade">
                        <!-- The global progress bar -->
                        <div class="progress progress-striped active" role="progressbar" aria-valuemin="0"
                             aria-valuemax="100">
                            <div class="progress-bar progress-bar-success" style="width:0"></div>
                        </div>
                        <!-- The extended global progress state -->
                        <div class="progress-extended">&nbsp;</div>
                    </div>
                </div>
                <!-- The table listing the files available for upload/download -->
                <table role="presentation" class="table table-striped">
                    <tbody class="files"></tbody>
                </table>
            </form>
            <div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls" data-filter=":even">
                <div class="slides"></div>
                <h3 class="title"></h3>
                <a class="prev">‹</a>
                <a class="next">›</a>
                <a class="close">×</a>
                <a class="play-pause"></a>
                <ol class="indicator"></ol>
            </div>
        </div>

        <%@ include file="template/footer.jsp" %>
    </div>
</div>

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

<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
        <td>
            <p class="size">Processing...</p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start" disabled>
                    <i class="fa fa-upload"></i>
                    <span>Start</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <i class="fa fa-ban-circle"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}

</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name">
                {% if (file.url) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">Error</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}
                <button class="btn btn-danger delete" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <i class="fa fa-trash"></i>
                    <span>Delete</span>
                </button>
                <input type="checkbox" name="delete" value="1" class="toggle">
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="fa fa-ban-circle"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}

</script>
<script type="text/javascript" src="js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="js/tmpl.min.js"></script>
<script type="text/javascript" src="js/load-image.all.min.js"></script>
<script type="text/javascript" src="js/canvas-to-blob.min.js"></script>
<script type="text/javascript" src="BS3/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/jquery.blueimp-gallery.min.js"></script>
<script type="text/javascript" src="js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="js/jquery.fileupload.js"></script>
<script type="text/javascript" src="js/jquery.fileupload-process.js"></script>
<script type="text/javascript" src="js/jquery.fileupload-image.js"></script>
<script type="text/javascript" src="js/jquery.fileupload-audio.js"></script>
<script type="text/javascript" src="js/jquery.fileupload-video.js"></script>
<script type="text/javascript" src="js/jquery.fileupload-validate.js"></script>
<script type="text/javascript" src="js/jquery.fileupload-ui.js"></script>
<script type="text/javascript" src="js/main.js"></script>

</body>
</html>

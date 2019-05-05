const files = $("#files");
files.on("change", e => {
    addUpload(e.target.files);
});

let fileCount = 0;
let fileList = {};
const addUpload = (files) => {
    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const images = ['jpg', 'jpeg', 'png'];
        const reader = new FileReader();
        reader.onload = e => {
            const ext = file.name.split('.').pop().toLowerCase();
            let src;

            if (images.includes(ext)) {
                src = e.target.result;
            } else if (ext === "docx") {
                src = "/assets/img/word.png"
            } else return;

            const btnPreview = $(`<button class="btn btn-block btn-secondary"><i class="fa fa-image"></i><span>Preview</span></button>`).on("click", previewFile);
            const btnRemove = $(`<button class="btn btn-block btn-danger"><i class="fa fa-trash"></i><span>Remove</span></button>`).on("click", removeFile);

            $("#preview").append(
                $(`<tr class="col-12" data-file="${fileCount}"></tr>`)
                    .append($(`<td></td>`).append(`<a href="#" data-gallery=""><img id="id-${i}" class="preview" src="${src}" style="max-width: 100px; max-height: 100px" alt="Upload image"/></a>`))
                    .append($(`<td class="col-4"></td>`).append(`<p class="name">${file.name}</p><strong class="error text-danger"></strong><div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0;"></div>`))
                    .append($(`<td></td>`).append(btnPreview).append(btnRemove))
            );
            fileList[fileCount++] = file;
            console.log(fileList);
        };
        reader.readAsDataURL(file);
    }
};

const previewFile = e => {
    console.log("jadbjabjdwbjabjwbdjb");

    e.preventDefault();
};

const removeFile = e => {
    e.preventDefault();

    let el = e.target;
    while (el && el.tagName !== 'TR') {el = el.parentNode}
    delete fileList[$(e.target).attr("data-file")];
    $(el).remove();
};

$.ajax({
    url: "/term&condition.txt",
    success: data => {
        $("#term-content").html(data)
    }
});
$("#btn-submit").on("click", e => {e.preventDefault()});
$("#start-upload").on("click", e => {
    $("#term-modal").modal('hide');

    for (const file in fileList) {
        if (fileList.hasOwnProperty(file)) {
            const up = new Upload(fileList[file])
        }
    }

    e.preventDefault()
});

$("#btn-cancel").on("click", e => {

});

$('body').on('drag dragstart dragend dragover dragenter dragleave drop', e => {
    e.preventDefault();
    e.stopPropagation();
}).on('drop', e => {
    addUpload(e.originalEvent.dataTransfer.files);
    e.preventDefault();
});

//region Upload
const Upload = function (file) {
    this.file = file;
    return this;
};

Upload.prototype.getType = function () {return this.file.type;};
Upload.prototype.getSize = function () {return this.file.size;};
Upload.prototype.getName = function () {return this.file.name;};

Upload.prototype.doUpload = function (success, error) {
    const that = this;
    const formData = new FormData();

    // add assoc key values, this will be posts values
    formData.append("file", this.file, this.getName());
    formData.append("upload_file", true);
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "POST",
            url: "/upload",
            xhr: () => {
                const myXhr = $.ajaxSettings.xhr();
                if (myXhr.upload) {
                    myXhr.upload.addEventListener('progress', that.progressHandling, false);
                }
                return myXhr;
            },
            success: resolve,
            error: reject,
            async: true,
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            timeout: 60000
        });
    });
};

Upload.prototype.progressHandling = event => {
    let percent = 0;
    const position = event.loaded || event.position;
    const total = event.total;
    const progress_bar_id = "#progress-wrp";
    if (event.lengthComputable) {
        percent = Math.ceil(position / total * 100);
    }
    // update progressbars classes so it fits your code
    $(progress_bar_id + " .progress-bar").css("width", +percent + "%");
    $(progress_bar_id + " .status").text(percent + "%");
};
//endregion Upload


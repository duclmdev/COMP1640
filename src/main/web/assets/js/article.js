var queryDict = {}
location.search.substr(1).split("&").forEach(function (item) {queryDict[item.split("=")[0]] = item.split("=")[1]});
$.ajax({
    url: "/article",
    data: {id: queryDict.id},

    success: data => {
        const {info, files} = data;
        console.log(files);
        console.log(info);
        const {name, submit_time} = info[0];
        $("#submit-name").html(name);
        $("#submit-time").html(submit_time);
        for (const datum in files) {

            let split = files[datum].disk_location.split("\\")
            const name = split[split.length - 1];
            let src = "/assets/img/word.png";
            if (!name.endsWith("docx")) {
                src = `/download?id=${files[datum].id}`
            }
            $("#preview").append(
                $(`<tr class="col-12"></tr>`)
                    .append($(`<td></td>`).append(`<a href="#" data-gallery=""><img class="preview" src="${src}" style="max-width: 100px; max-height: 100px" alt="Upload image"/></a>`))
                    .append($(`<td class="col-4"></td>`).append(`<p class="name">${name}</p><strong class="error text-danger"></strong><div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0;"></div>`))
                    .append($(`<td><a href="${src}">Download</a></td>`)))

        }
    }
});

$("#btn-send").on("click", e => {
    const cmt = $("#txt-comment");
    const txt = cmt.val();
    if (txt.length > 0) {
        const time = new Date().toISOString().substring(0, 18).replace("T", " ");
        $("#comments").prepend($(`<tr><td><b>A Coordinator</b></td><td>${txt}</td><td>${time}</td></tr>`));
        cmt.val("");
    }
    e.preventDefault();
});
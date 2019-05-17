var queryDict = {};
location.search.substr(1).split("&").forEach(function (item) {queryDict[item.split("=")[0]] = item.split("=")[1]});

let selected;
$.ajax({
    url: "/article",
    data: {id: queryDict.id},
    success: data => {
        const {info, files} = data;
        console.log(files);
        console.log(info);
        const {name, submit_time, chosen} = info[0];
        $("#submit-name").html(name);
        $("#submit-time").html(submit_time);
        select(chosen);
        for (const datum in files) {
            if (files.hasOwnProperty(datum)) {
                let split = files[datum].disk_location.split("\\");
                const name = split[split.length - 1];
                const srcDownload = `/download?id=${files[datum].id}`;
                const srcImage = name.endsWith("docx") ? "/assets/img/word.png" : srcDownload;
                $("#preview").append(
                    $(`<tr class="col-12"></tr>`)
                        .append($(`<td></td>`).append(`<a href="#" data-gallery=""><img class="preview" src="${srcImage}" style="max-width: 100px; max-height: 100px" alt="Upload image"/></a>`))
                        .append($(`<td class="col-4"></td>`).append(`<p class="name">${name}</p><strong class="error text-danger"></strong><div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0;"></div>`))
                        .append($(`<td><a href="${srcDownload}">Download</a></td>`)))
            }
        }
    }
});

const select = chosen => {
    selected = chosen;
    console.log(chosen);
    if (chosen) {
        $("#selection").addClass("btn-danger");
        $("#selection-icon").addClass("fa-ban");
        $("#select-txt").html("Remove from Publication");
    } else {
        $("#selection").addClass("btn-primary");
        $("#selection-icon").addClass("fa-check");
        $("#select-txt").html("Select for Publication");
    }
};

$("#btn-send").on("click", e => {
    const cmt = $("#txt-comment");
    const txt = cmt.val();
    if (txt.length > 0) {
        const time = new Date().toISOString().substring(0, 19).replace("T", " ");
        $("#comments").prepend($(`<tr><td><b>A Coordinator</b></td><td>${txt}</td><td>${time}</td></tr>`));
        cmt.val("");
    }
    e.preventDefault();
});

$(".btn-download").on("click", e => {
    e.preventDefault();
    let el = e.target;
    while (el && el.tagName !== 'TR') {el = el.parentNode}
    const id = $(el).attr("data-id");
    window.open(`/download?submission=${queryDict.id}`, "_blank")
});

$("#selection").on("click", e => {
    selected = !selected;
    $.ajax({
        url: "/article",
        data: {select: selected, select_id: queryDict.id},
        success: data => {
            console.log("something");
            select(selected)
        }
    })
});

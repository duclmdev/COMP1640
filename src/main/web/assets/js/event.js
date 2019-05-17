let event_list;

const fetchEvents = () => $.ajax({
    url: "/admin",
    data: {
        get: 1
    },
    success: data => {
        console.log(data);
        renderEvents(data);
        event_list = data.reduce((a, b) => {
            a[b.id] = b;
            return a;
        }, {});
    }
});
fetchEvents();

const events = $("#events");
const renderEvents = (data) => {
    events.html("");
    data.forEach(datum => {
        const {id, name, year, first_deadline, second_deadline} = datum;
        console.log(datum);
        events.append(`<tr data-id="${id}">
            <td class="name">${name}</td>
            <td class="first">${first_deadline}</td>
            <td class="second">${second_deadline}</td>
            <td class="update">
                <button class="btn btn-block btn-primary btn-edit"><i class="fa fa-pencil"></i> Edit</button>
                <button class="btn btn-block btn-danger btn-delete"><i class="fa fa-trash"></i> Delete</button>
            </td>
        </tr>`)
    });
    setEvent()
};

const get_id = (e) => {
    let el = e.target;
    while (el && el.tagName !== 'TR') {el = el.parentNode}
    return $(el).attr("data-id");
};

const setEvent = () => {
    $(".btn-edit").on("click", e => {
        const id = get_id(e);
        console.log(`edit - id = ${id}`);
        const evt = $(`tr[data-id=${id}]`);

        const update = evt.find(".update");
        update.html(`
            <button class="btn btn-block btn-primary btn-update"><i class="fa fa-upload"></i> Update</button>
            <button class="btn btn-block btn-danger btn-cancel"><i class="fa fa-ban"></i> Cancel</button>
        `);

        const name = evt.find(".name");
        const first = evt.find(".first");
        const second = evt.find(".second");

        name.html($(`<input type="text" class="edit-name form-control">`).val(name.html()));
        first.html($(`<input type="date" class="edit-first form-control">`).val(first.html()));
        second.html($(`<input type="date" class="edit-second form-control">`).val(second.html()));

        setUpdateEvent();
    });

    $(".btn-delete").on("click", e => {
        const id = get_id(e);
        $(`tr[data-id=${id}]`).remove();
        $.ajax({
            url: "/admin",
            data: {"delete": 1, id},
            success: data => {
                notify(data)
            }
        })
    })
};

const setUpdateEvent = () => {
    $(".btn-update").on("click", e => {
        const id = get_id(e);
        const evt = $(`tr[data-id=${id}]`);

        const name = evt.find(".edit-name").val();
        const first_deadline = evt.find(".edit-first").val();
        const second_deadline = evt.find(".edit-second").val();
        $.ajax({
            url: "/admin",
            data: {"update": 1, name, year: new Date(second_deadline).getFullYear(), first_deadline, second_deadline, id},
            success: data => {
                notify(data);
                reset({name, first_deadline, second_deadline}, evt)
            }
        })
    });

    $(".btn-cancel").on("click", e => {
        const id = get_id(e);
        const evt = $(`tr[data-id=${id}]`);

        reset(event_list[id], evt);
    });

    const reset = (data, evt) => {
        const {name, first_deadline, second_deadline} = data;
        evt.find(".name").html(name);
        evt.find(".first").html(first_deadline);
        evt.find(".second").html(second_deadline);

        const update = evt.find(".update");
        update.html(`
            <button class="btn btn-block btn-primary btn-edit"><i class="fa fa-pencil"></i> Edit</button>
            <button class="btn btn-block btn-danger btn-delete"><i class="fa fa-trash"></i> Delete</button>
        `);
        setEvent()
    }
};

$("#btn-modal-add").on("click", e => {
    $("#modal").modal("show");
});

$("#btn-add").on("click", e => {
    const name = $("#name").val();
    const year = $("#year").val();
    const first_deadline = $("#first").val();
    const second_deadline = $("#second").val();
    $.ajax({
        url: "/admin",
        data: {"create": 1, name, year, first_deadline, second_deadline},
        success: data => {
            $("#modal").modal("hide");
            console.log(data);
            notify(data);
            fetchEvents();
        }
    })
});

const notify = (data) => {
    if (data.error) {
        $("#content").html(`<strong>ERROR</strong> ${data.error}`);
        $("#alert").removeClass("alert-success").addClass("alert-warning").addClass("show");
    } else {
        $("#content").html(`<strong>SUCCESS</strong> ${data.success}`);
        $("#alert").removeClass("alert-warning").addClass("alert-success").addClass("show");
    }
};
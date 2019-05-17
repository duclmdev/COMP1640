let currentPage = 0;
const fetchSubmissions = (page) => {
    page = page || 1;
    $.ajax({
        url: "/submission",
        data: {page, year: 2},
        success: data => {
            currentPage = page;
            console.log(data);
            renderSubmissions(data)
        }
    });
};
fetchSubmissions(1);

$(".page-link").on("click", e => {
    const page = $(e.target).attr("data-page");
    if (page === "prev" && currentPage > 1) {
        fetchSubmissions(currentPage + 1)
    } else if (page === "next" && currentPage < 3) {
        fetchSubmissions(currentPage - 1)
    } else {
        fetchSubmissions(page)
    }
});

const renderSubmissions = (data) => {
    let render;
    let submissions = $("#submissions").html("");
    if (!data.length) return;

    if (data[0].hasOwnProperty("rollnumber")) {
        $("#header").html(`<th>Submission Name</th><th>Roll Number</th><th>Student Name</th><th>Submitting Time</th><th>Chosen</th><th>Options</th>`);
        render = renderFacultySubmission;
    } else {
        $("#header").html(`<th>Submission Name</th><th>Submitting Time</th><th>Chosen</th><th>Options</th>`);
        render = renderStudentSubmission;
    }

    for (const datum in data) {
        if (data.hasOwnProperty(datum)) {
            submissions.append(render(data[datum]))
        }
    }
    setEvent();
};

const renderStudentSubmission = (submission) => {
    const {id, name, submit_time, chosen} = submission;
    return `<tr data-id="${id}">
    <td>${name}</td>
    <td>${submit_time}</td>
    <td>${chosen === 1 ? "YES" : "NO"}</td>
    <td>
        <button class="btn btn-block btn-view btn-danger" data-id="">
            <i class="fa fa-eye"></i> View
        </button>
        <button class="btn btn-block btn-download btn-primary" data-id="">
            <i class="fa fa-download"></i> Download
        </button>
    </td>
</tr>`;
};

const renderFacultySubmission = (submission) => {
    const {id, sm_name, rollnumber, student_name, submit_time, chosen} = submission;
    return `<tr data-id="${id}">
    <td>${sm_name}</td>
    <td>${rollnumber}</td>
    <td>${student_name}</td>
    <td>${submit_time}</td>
    <td>${chosen === 1 ? "YES" : "NO"}</td>
    <td>
        <button class="btn btn-block btn-view btn-danger" data-id="">
            <i class="fa fa-eye"></i> View
        </button>
        <button class="btn btn-block btn-download btn-primary" data-id="">
            <i class="fa fa-download"></i> Download
        </button>
    </td>
</tr>`;
};

const setEvent = () => {
    $(".btn-view").on("click", e => {
        e.preventDefault();
        let el = e.target;
        while (el && el.tagName !== 'TR') {el = el.parentNode}
        const id = $(el).attr("data-id");
        window.location = `/article.jsp?id=${id}`;
    });

    $(".btn-download").on("click", e => {
        e.preventDefault();
        let el = e.target;
        while (el && el.tagName !== 'TR') {el = el.parentNode}
        const id = $(el).attr("data-id");
        window.open(`/download?submission=${id}`, "_blank")
    });
};
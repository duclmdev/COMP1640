
const renderSubmission = () => {

};
const renderStudentSubmission = (submission) => {

    return `<tr>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
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

$(".btn-view").on("click", e => {
    e.preventDefault();
    console.log(e.target);
});

$(".btn-download").on("click", e => {
    e.preventDefault();
    window.open("", "_blank")
});
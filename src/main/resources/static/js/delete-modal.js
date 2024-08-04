$(document).ready(function () {
    // Delete Modal Form
    $('.table .deleteRecordBtn').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');
        // delete button id assigning to delete modal - delete id
        $('#deleteModal #deleteId').attr('href', href);
        // show the modal
        $('#deleteModal').modal('show');
    });
});
/*<script th:inline="javascript">*/
$(document).ready(function () {
    // EDIT RETAINER
    $('.table .editRetainerAccountBtn').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function(tempRetainer, status) {
            $('#editRetainerAccountForm #clientAccountId').val(tempRetainer.clientAccountId);
            if (tempRetainer.client) {
                $('#editRetainerAccountForm #editClient').val(tempRetainer.client.contactId);
            }
            if (tempRetainer.retainerAccount) {
                $('#editRetainerAccountForm #editRetainerId').val(tempRetainer.retainerAccount.retainerId);
                $('#editRetainerAccountForm #editStatus').val(tempRetainer.retainerAccount.status);
                $('#editRetainerAccountForm #editStartDate').val(formatDate(tempRetainer.retainerAccount.startDate));
                $('#editRetainerAccountForm #editEndDate').val(formatDate(tempRetainer.retainerAccount.endDate));
            }
            $('#editRetainerModal').modal('show');
        });
    });
    // Function to format date
    function formatDate(date) {
        if (!date) return ''; // Return empty if no date

        // If date is a string, try to parse it as a Date object
        if (typeof date === 'string') {
            date = new Date(date);
        } else if (typeof date === 'number') {
            date = new Date(date); // Handle timestamps
        }

        // Format date as YYYY-MM-DD
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
        const day = String(date.getDate()).padStart(2, '0');

        return `${year}-${month}-${day}`; // Return formatted date
    }
});
/*</script>*/
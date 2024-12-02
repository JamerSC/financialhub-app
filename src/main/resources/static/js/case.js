/*<script th:inline="javascript">*/
$(document).ready(function () {
    // UPDATE CASE ACCOUNT
    $('.table .editCaseAccountBtn').on('click', function(event) {
        event.preventDefault();  // Prevent the default link behavior
        var href = $(this).attr('href');  // Get the URL from the edit button

        // Make an AJAX GET request to fetch the case data
        $.get(href, function(tempCase, status) {
            // Populate modal fields with the case data
            $('#editCaseAccountForm #clientAccountId').val(tempCase.clientAccountId);
            $('#editCaseAccountForm #editCaseTitle').val(tempCase.accountTitle);
            if (tempCase.client) {
                $('#editCaseAccountForm #editClient').val(tempCase.client.contactId); // Make sure client is not undefined
            }
            if (tempCase.caseAccount) {
                $('#editCaseAccountForm #editCaseId').val(tempCase.caseAccount.caseId);
                $('#editCaseAccountForm #editCaseType').val(tempCase.caseAccount.caseType);
                $('#editCaseAccountForm #editDocketNo').val(tempCase.caseAccount.docketNo);
                $('#editCaseAccountForm #editNature').val(tempCase.caseAccount.nature);
                $('#editCaseAccountForm #editCourt').val(tempCase.caseAccount.court);
                $('#editCaseAccountForm #editBranch').val(tempCase.caseAccount.branch);
                $('#editCaseAccountForm #editJudge').val(tempCase.caseAccount.judge);
                $('#editCaseAccountForm #editCourtEmail').val(tempCase.caseAccount.courtEmail);
                $('#editCaseAccountForm #editProsecutor').val(tempCase.caseAccount.prosecutor);
                $('#editCaseAccountForm #editProsecutorOffice').val(tempCase.caseAccount.prosecutorOffice);
                $('#editCaseAccountForm #editProsecutorEmail').val(tempCase.caseAccount.prosecutorEmail);
                $('#editCaseAccountForm #editOpposingParty').val(tempCase.caseAccount.opposingParty);
                $('#editCaseAccountForm #editOpposingCounsel').val(tempCase.caseAccount.opposingCounsel);
                $('#editCaseAccountForm #editCounselEmail').val(tempCase.caseAccount.counselEmail);
                $('#editCaseAccountForm #editStatus').val(tempCase.caseAccount.status);
                $('#editCaseAccountForm #editStage').val(tempCase.caseAccount.stage);
                // Format startDate and endDate
                $('#editCaseAccountForm #editStartDate').val(formatDate(tempCase.caseAccount.startDate));
                $('#editCaseAccountForm #editEndDate').val(formatDate(tempCase.caseAccount.endDate));
            }
            // Open the modal after data is set
            $('#editCaseModal').modal('show');
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
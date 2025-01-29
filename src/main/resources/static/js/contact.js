/*<script th:inline="javascript">*/
$(document).ready(function() {
    // Existing modal handling for individual/company buttons
    $('#individualBtn').click(function() {
        $('#contactTypeModal').modal('hide');
        $('#individualModal').modal('show');
    });
    $('#companyBtn').click(function() {
        $('#contactTypeModal').modal('hide');
        $('#companyModal').modal('show');
    });

    // UPDATE INDIVIDUAL CONTACT

    $('.table .editContactIndividualBtn').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');

        // Make an AJAX GET request to fetch the case data
        $.get(href, function(tempContact, status) {

            $('#editIndividualContactForm #individualContactId').val(tempContact.contactId);
            $('#editIndividualContactForm #editIndividualCategoryType').val(tempContact.contactCategoryType);
            $('#editIndividualContactForm #editIndividualChannel').val(tempContact.bestChannelToContact);
            $('#editIndividualContactForm #editIndividualEngagementDate').val(formatDate(tempContact.engagementDate));

            if (tempContact.individual) {
                $('#editIndividualContactForm #editIndividualId').val(tempContact.individual.individualId);
                $('#editIndividualContactForm #editIndividualTitle').val(tempContact.individual.title);
                $('#editIndividualContactForm #editIndividualFirstName').val(tempContact.individual.firstName);
                $('#editIndividualContactForm #editIndividualMiddleName').val(tempContact.individual.middleName);
                $('#editIndividualContactForm #editIndividualLastName').val(tempContact.individual.lastName);
                $('#editIndividualContactForm #editIndividualSuffix').val(tempContact.individual.suffix);
                $('#editIndividualContactForm #editIndividualMobileNumber').val(tempContact.individual.mobileNumber);
                $('#editIndividualContactForm #editIndividualEmail').val(tempContact.individual.emailAddress);
                $('#editIndividualContactForm #editIndividualAddress').val(tempContact.individual.address);
            }

            if (tempContact.additionalDetails) {
                $('#editIndividualContactForm #editDetailId').val(tempContact.additionalDetails.detailId);
                $('#editIndividualContactForm #editIndividualDesignationFor').val(tempContact.additionalDetails.designationFor);
                $('#editIndividualContactForm #editIndividualBankName').val(tempContact.additionalDetails.bankName);
                $('#editIndividualContactForm #editIndividualAccountNo').val(tempContact.additionalDetails.accountNo);
            }

            $('#editIndividualModal').modal('show');
        });
    });

    // UPDATE COMPANY CONTACT

    $('.table .editContactCompanyBtn').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');

        // Make an AJAX GET request to fetch the case data
        $.get(href, function(tempContact, status) {

            $('#editContactCompanyForm #companyContactId').val(tempContact.contactId);
            $('#editContactCompanyForm #editCompanyCategoryType').val(tempContact.contactCategoryType);
            $('#editContactCompanyForm #editCompanyChannel').val(tempContact.bestChannelToContact);
            $('#editContactCompanyForm #editCompanyEngagementDate').val(formatDate(tempContact.engagementDate));

            if (tempContact.company) {
                $('#editContactCompanyForm #editCompanyId').val(tempContact.company.companyId);
                $('#editContactCompanyForm #editCompanyName').val(tempContact.company.companyName);
                $('#editContactCompanyForm #editRegistrationType').val(tempContact.company.registrationType);
                $('#editContactCompanyForm #editRepresentativeName').val(tempContact.company.representativeName);
                $('#editContactCompanyForm #editRepresentativeDesignation').val(tempContact.company.representativeDesignation);
                $('#editContactCompanyForm #editCompanyMobileNumber').val(tempContact.company.mobileNumber);
                $('#editContactCompanyForm #editCompanyEmail').val(tempContact.company.emailAddress);
                $('#editContactCompanyForm #editCompanyAddress').val(tempContact.company.address);
            }

            if (tempContact.additionalDetails) {
                $('#editContactCompanyForm #editCompanyDetailId').val(tempContact.additionalDetails.detailId);
                $('#editContactCompanyForm #editCompanyDesignationFor').val(tempContact.additionalDetails.designationFor);
                $('#editContactCompanyForm #editCompanyBankName').val(tempContact.additionalDetails.bankName);
                $('#editContactCompanyForm #editCompanyAccountNo').val(tempContact.additionalDetails.accountNo);
            }

            $('#editCompanyModal').modal('show');
        });
    });

    function formatDate(date) {
        if (!date) return '';
        if (typeof date === 'string') {
            date = new Date(date);
        } else if (typeof date === 'number') {
            date = new Date(date); // Handle timestamps
        }
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    // RESET SPECIFIC FORMS
    //$('#editIndividualModal').on('hide.bs.modal', function () {
        // Reset the form when the modal is closed
        //$('#editIndividualContactForm')[0].reset();
    //});

    // RESET ALL HIDE FORMS
    // Listen for the Bootstrap modal's hide event for all modals
    $('.modal').on('hide.bs.modal', function () {
        // Find the form within the modal (if it exists) and reset it
        const form = $(this).find('form')[0];
        if (form) {
            form.reset();
        }
    });
});
/*</script>*/
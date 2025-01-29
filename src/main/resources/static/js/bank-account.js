<!-- Validation Script -->
/*<script th:inline="javascript">*/
$(document).ready(function () {
    // Validate Add Account Form
    $('#addAccountForm').on('submit', function (event) {
        var isValid = true;

        // Validate Account Name
        if ($('#accountHolderName').val().trim() === '') {
            $('#accountHolderName').addClass('is-invalid');
            isValid = false;
        } else {
            $('#accountHolderName').removeClass('is-invalid');
        }

        // Validate Account Number
        if ($('#accountNumber').val().trim() === '') {
            $('#accountNumber').addClass('is-invalid');
            isValid = false;
        } else {
            $('#accountNumber').removeClass('is-invalid');
        }

        // Validate Bank Selection
        if ($('#bank').val() === '') {
            $('#bank').addClass('is-invalid');
            isValid = false;
        } else {
            $('#bank').removeClass('is-invalid');
        }

        // Prevent form submission if invalid
        if (!isValid) {
            event.preventDefault();
        }
    });

    // EDIT BANK ACCOUNT
    // EDIT BANK ACCOUNT
    $('.table .editBankAccountBtn').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function(tempAccount, status) {
            $('#editAccountForm #bankAccountId').val(tempAccount.bankAccountId);
            $('#editAccountForm #editAccountHolderName').val(tempAccount.accountHolderName);
            $('#editAccountForm #editAccountNumber').val(tempAccount.accountNumber);
            //$('#editAccountForm #editBank').val(tempAccount.bank.bankId);
            // Ensure to access bank details correctly
            if (tempAccount.bank) {
                $('#editAccountForm #editBank').val(tempAccount.bank.bankId); // assuming bankId is the property
            }
            $('#editAccountModal').modal('show');
        });
    });

    // VALIDATE BANK ACCOUNT
    // VALIDATE BANK ACCOUNT
    $('#editAccountForm').on('submit', function (event) {
        var isValid = true;

        // Validate Bank Name
        if ($('#editBank').val() === '') {
            $('#editBank').addClass('is-invalid');
            isValid = false;
        } else {
            $('#editBank').removeClass('is-invalid');
        }

        // Validate Account Holder Name
        if ($('#editAccountHolderName').val().trim() === '') {
            $('#editAccountHolderName').addClass('is-invalid');
            isValid = false;
        } else {
            $('#editAccountHolderName').removeClass('is-invalid');
        }

        // Validate Bank Account Number
        if ($('#editAccountNumber').val().trim() === '') {
            $('#editAccountNumber').addClass('is-invalid');
            isValid = false;
        } else {
            $('#editAccountNumber').removeClass('is-invalid');
        }

        // Prevent form submission if invalid
        if (!isValid) {
            event.preventDefault();
        }
    });

    // CREATE BANK
    // CREATE BANK
    $('#addBankForm').on('submit', function (event) {
        var isValid = true;

        // Validate Bank Name
        if ($('#bankName').val().trim() === '') {
            $('#bankName').addClass('is-invalid');
            isValid = false;
        } else {
            $('#bankName').removeClass('is-invalid');
        }

        // Validate Abbreviation
        if ($('#bankAbbreviation').val().trim() === '') {
            $('#bankAbbreviation').addClass('is-invalid');
            isValid = false;
        } else {
            $('#bankAbbreviation').removeClass('is-invalid');
        }

        // Validate Bank Branch
        if ($('#branch').val().trim() === '') {
            $('#branch').addClass('is-invalid');
            isValid = false;
        } else {
            $('#branch').removeClass('is-invalid');
        }

        // Prevent form submission if invalid
        if (!isValid) {
            event.preventDefault();
        }
    });


    // EDIT BANK
    // EDIT BANK
    $('.table .editBankBtn').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function(tempBank, status) {
            $('#editBankForm #id').val(tempBank.bankId);
            $('#editBankForm #editBankName').val(tempBank.name);
            $('#editBankForm #editBankAbbreviation').val(tempBank.abbreviation);
            $('#editBankForm #editBranch').val(tempBank.branch);
            $('#editBankModal').modal('show');
        });
    });

    // Validate EDIT BANK
    // Validate EDIT BANK
    $('#editBankForm').on('submit', function (event) {
        var isValid = true;

        // Validate Bank Name
        if ($('#editBankName').val().trim() === '') {
            $('#editBankName').addClass('is-invalid');
            isValid = false;
        } else {
            $('#editBankName').removeClass('is-invalid');
        }

        // Validate Abbreviation
        if ($('#editBankAbbreviation').val().trim() === '') {
            $('#editBankAbbreviation').addClass('is-invalid');
            isValid = false;
        } else {
            $('#editBankAbbreviation').removeClass('is-invalid');
        }

        // Validate Bank Branch
        if ($('#editBranch').val().trim() === '') {
            $('#editBranch').addClass('is-invalid');
            isValid = false;
        } else {
            $('#editBranch').removeClass('is-invalid');
        }

        // Prevent form submission if invalid
        if (!isValid) {
            event.preventDefault();
        }
    });
});
/*
</script>*/

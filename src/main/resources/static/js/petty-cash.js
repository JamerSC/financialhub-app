$(document).ready(function() {
    // Initialize Tagify for "Accounts" field in Add Modal
    //const accountData = /*[[${listOfAccounts}]]*/ || [];
    const formattedAccounts = accountData.map(account => ({
        id: account.clientAccountId,
        value: `${account.accountTitle || ''} (${account.clientAccountType || ''})`
    }));

    // Initialize Tagify on "accounts" input in Add Modal
    const inputAdd = document.querySelector('#accounts');
    const tagifyAdd = new Tagify(inputAdd, {
        enforceWhitelist: true,
        whitelist: formattedAccounts,
        dropdown: {
            enabled: 0,
            maxItems: 10
        }
    });

    // Update hidden input field with selected tag IDs for Add Modal
    tagifyAdd.on('add', function() {
        const values = tagifyAdd.value.map(tag => tag.id);
        $("#accountsJson").val(JSON.stringify(values));
        //console.log("Accounts added in Add Modal:", JSON.stringify(values)); // Log added accounts
    });

    tagifyAdd.on('remove', function() {
        const values = tagifyAdd.value.map(tag => tag.id);
        $("#accountsJson").val(JSON.stringify(values));
        //console.log("Accounts removed in Add Modal:", JSON.stringify(values)); // Log removed accounts
    });

    // Initialize Tagify on "accounts" input in Add Modal
    const inputAddAdmin = document.querySelector('#adminAccounts');
    const tagifyAddAdmin = new Tagify(inputAddAdmin, {
        enforceWhitelist: true,
        whitelist: formattedAccounts,
        dropdown: {
            enabled: 0,
            maxItems: 10
        }
    });

    tagifyAddAdmin.on('add', function() {
        const values = tagifyAddAdmin.value.map(tag => tag.id);
        $("#adminAccountsJson").val(JSON.stringify(values));
        //console.log("Accounts added in Add Modal:", JSON.stringify(values)); // Log added accounts
    });

    tagifyAddAdmin.on('remove', function() {
        const values = tagifyAddAdmin.value.map(tag => tag.id);
        $("#adminAccountsJson").val(JSON.stringify(values));
        //console.log("Accounts removed in Add Modal:", JSON.stringify(values)); // Log removed accounts
    });

    // Initialize Tagify on "editAccounts" input in Edit Modal
    const inputEdit = document.querySelector('#editAccounts');
    const tagifyEdit = new Tagify(inputEdit, {
        enforceWhitelist: true,
        whitelist: formattedAccounts,
        dropdown: {
            enabled: 0,
            maxItems: 10
        }
    });

    // UPDATE ADMIN PETTY CASH
    $('.table .ePettyCashBtn').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function(tempPettyCash, status) {
            /*console.log(tempPettyCash);*/

            $('#editPettyCashForm #editPettyCashId').val(tempPettyCash.pcActivityId);

            if (tempPettyCash.fund) {
               $('#editPettyCashForm #editFundId').val(tempPettyCash.fund.fundId);
            }

            if (tempPettyCash.receivedBy) {
               $('#editPettyCashForm #editReceivedBy').val(tempPettyCash.receivedBy.userId);
            }

            $('#editPettyCashForm #editVoucherNo').val(tempPettyCash.pcActivityNo);
            $('#editPettyCashForm #editDate').val(formatDate(tempPettyCash.date));
            $('#editPettyCashForm #editActivityDescription').val(tempPettyCash.activityDescription);
            $('#editPettyCashForm #editActivityCategory').val(tempPettyCash.activityCategory);
            $('#editPettyCashForm #editSoaCategory').val(tempPettyCash.soaCategory);
            $('#editPettyCashForm #editTotalAmount').val(tempPettyCash.totalAmount);
            $('#editPettyCashForm #editApproved').prop('checked', tempPettyCash.approved);

            // Populate accounts in Tagify for Edit Modal
            tagifyEdit.removeAllTags();
            if (tempPettyCash.accounts) {
                const accountTags = tempPettyCash.accounts.map(account => ({
                    id: account.clientAccountId,
                    value: `${account.accountTitle} (${account.clientAccountType})`
                }));
                tagifyEdit.addTags(accountTags);
            }

            $('#editPettyCashModal').modal('show');
        });
    });

    // Log the accounts value in Edit Modal on add/remove
    //tagifyEdit.on('add', function() {
    //    const values = tagifyEdit.value.map(tag => tag.id);
    //    //console.log("Accounts added in Edit Modal:", JSON.stringify(values)); // Log added accounts
    //});
    //tagifyEdit.on('remove', function() {
    //    const values = tagifyEdit.value.map(tag => tag.id);
    //    //console.log("Accounts removed in Edit Modal:", JSON.stringify(values)); // Log removed accounts
    //});


    // UPDATE ADMIN WORKS PETTY CASH

    // Initialize Tagify on "editAccounts" input in Edit Modal
    const inputEditAdmin = document.querySelector('#editAdminAccounts');
    const tagifyEditAdmin = new Tagify(inputEditAdmin, {
        enforceWhitelist: true,
        whitelist: formattedAccounts,
        dropdown: {
            enabled: 0,
            maxItems: 10
        }
    });

    $('.table .eAdminPettyCashBtn').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function(tempPettyCash, status) {
            //console.log(tempPettyCash);

            $('#editAdminPettyCashForm #editAdminPettyCashId').val(tempPettyCash.pcActivityId);

            if (tempPettyCash.fund) {
               $('#editAdminPettyCashForm #editAdminFundId').val(tempPettyCash.fund.fundId);
            }

            $('#editAdminPettyCashForm #editAdminVoucherNo').val(tempPettyCash.pcActivityNo);
            $('#editAdminPettyCashForm #editAdminDate').val(formatDate(tempPettyCash.date));
            $('#editAdminPettyCashForm #editAdminActivityDescription').val(tempPettyCash.activityDescription);
            $('#editAdminPettyCashForm #editAdminActivityCategory').val(tempPettyCash.activityCategory);
            $('#editAdminPettyCashForm #editAdminSoaCategory').val(tempPettyCash.soaCategory);
            $('#editAdminPettyCashForm #editAdminTotalAmount').val(tempPettyCash.totalAmount);

            // Populate accounts in Tagify for Edit Modal
            tagifyEditAdmin.removeAllTags();
            if (tempPettyCash.accounts) {
                const accountTags = tempPettyCash.accounts.map(account => ({
                    id: account.clientAccountId,
                    value: `${account.accountTitle} (${account.clientAccountType})`
                }));
                tagifyEditAdmin.addTags(accountTags);
            }

            $('#editAdminPettyCashModal').modal('show');
        });
    });

    tagifyEditAdmin.on('add', function() {
        const values = tagifyEditAdmin.value.map(tag => tag.id);
        console.log("Accounts added in Edit Modal:", JSON.stringify(values)); // Log added accounts
    });
    tagifyEditAdmin.on('remove', function() {
        const values = tagifyEditAdmin.value.map(tag => tag.id);
        console.log("Accounts removed in Edit Modal:", JSON.stringify(values)); // Log removed accounts
    });


    function formatDate(date) {
        if (!date) return '';

        if (typeof date === 'string') {
            date = new Date(date);
        } else if (typeof date === 'number') {
            date = new Date(date);
        }

        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');

        return `${year}-${month}-${day}`;
    }
});
$(document).ready(function() {
    $('#togglePassword').click(function() {
        const passwordField = $('#floatingPassword');
        const passwordFieldType = passwordField.attr('type');
        if (passwordFieldType === 'password') {
            passwordField.attr('type', 'text');
            $(this).html('<i class="lni lni-close"></i>');
        } else {
            passwordField.attr('type', 'password');
            $(this).html('<i class="lni lni-eye"></i>');
        }
    });
});

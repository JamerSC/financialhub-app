$(document).ready(function() {
    $('#togglePassword').click(function() {
        const passwordField = $('#floatingPassword');
        const passwordFieldType = passwordField.attr('type');
        if (passwordFieldType === 'password') {
            passwordField.attr('type', 'text');
            $(this).html('<i class="lni lni-xmark-circle" title="Hide"></i>');
        } else {
            passwordField.attr('type', 'password');
            $(this).html('<i class="lni lni-eye" title="Show"></i>');
        }
    });
});

$(document).ready(function() {
    // project category
    $('#propertiesBtn').click(function() {
        $('#projectTypeModal').modal('hide');
        $('#propertiesModal').modal('show');
    });
    $('#businessBtn').click(function() {
        $('#projectTypeModal').modal('hide');
        $('#businessModal').modal('show');
    });
    $('#secBtn').click(function() {
        $('#projectTypeModal').modal('hide');
        $('#secModal').modal('show');
    });

    // Properties
    $('#transferOfTitleBtn').click(function() {
        $('#propertiesModal').modal('hide');
        $('#transferOfTitleModal').modal('show');
    });
    $('#settlementOfEstateBtn').click(function() {
        $('#propertiesModal').modal('hide');
        $('#settlementOfEstateModal').modal('show');
    });
    $('#titleAnnotationBtn').click(function() {
        $('#propertiesModal').modal('hide');
        $('#titleAnnotationModal').modal('show');
    });
    $('#titleOtherProcessBtn').click(function() {
        $('#propertiesModal').modal('hide');
        $('#titleOtherProcessModal').modal('show');
    });


    // Business
    $('#businessRegBtn').click(function() {
        $('#businessModal').modal('hide');
        $('#businessRegModal').modal('show');
    });
    $('#businessRenewalBtn').click(function() {
        $('#businessModal').modal('hide');
        $('#businessRenewalModal').modal('show');
    });
    $('#businessClosureBtn').click(function() {
        $('#businessModal').modal('hide');
        $('#businessClosureModal').modal('show');
    });
    $('#businessOtherProcessBtn').click(function() {
        $('#businessModal').modal('hide');
        $('#businessOtherProcessModal').modal('show');
    });

    // SEC
    $('#secRegistrationBtn').click(function() {
        $('#secModal').modal('hide');
        $('#secRegistrationModal').modal('show');
    });
    $('#secAmendmentBtn').click(function() {
        $('#secModal').modal('hide');
        $('#secAmendmentModal').modal('show');
    });
    $('#secStockIncreaseBtn').click(function() {
        $('#secModal').modal('hide');
        $('#secStockIncreaseModal').modal('show');
    });

});
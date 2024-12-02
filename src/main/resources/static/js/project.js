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

// Update Transfer of Title

   $('.table .editTransferOfTitleBtn').on('click', function(event) {
       event.preventDefault();
       var href = $(this).attr('href');

       $.get(href, function(tempProject, status) {
           $('#editTransferOfTitleForm #editTransferOfTitleAccountId').val(tempProject.clientAccountId);
           $('#editTransferOfTitleForm #editTransferProjectTitle').val(tempProject.accountTitle);
           if (tempProject.client) {
               $('#editTransferOfTitleForm #editTransferClient').val(tempProject.client.contactId);
           }
           if (tempProject.projectAccount) {
               $('#editTransferOfTitleForm #editTransferOfTitleProjectId').val(tempProject.projectAccount.projectId);
               $('#editTransferOfTitleForm #editTransferTitleNo').val(tempProject.projectAccount.titleNo);
               $('#editTransferOfTitleForm #editTransferTaxDeclarationNo').val(tempProject.projectAccount.taxDecNo);
               $('#editTransferOfTitleForm #editTransferLotNo').val(tempProject.projectAccount.lotNo);
               $('#editTransferOfTitleForm #editTransferLotArea').val(tempProject.projectAccount.lotArea);
               $('#editTransferOfTitleForm #editTransferLocation').val(tempProject.projectAccount.location);
               $('#editTransferOfTitleForm #editTransferBir').val(tempProject.projectAccount.bir);
               $('#editTransferOfTitleForm #editTransferRd').val(tempProject.projectAccount.rd);
               $('#editTransferOfTitleForm #editTransferZonalValue').val(tempProject.projectAccount.zonalValue);
               $('#editTransferOfTitleForm #editTransferPurchasePrice').val(tempProject.projectAccount.purchasePrice);
               $('#editTransferOfTitleForm #editTransferRemarks').val(tempProject.projectAccount.remarks);
               $('#editTransferOfTitleForm #editTransferStatus').val(tempProject.projectAccount.status);
           }
           $('#editTransferOfTitleModal').modal('show');
       });
   });

   // Update Settlement of Estate

   $('.table .editSettlementOfEstateBtn').on('click', function(event) {
       event.preventDefault();
       var href = $(this).attr('href');

       $.get(href, function(tempProject, status) {
           $('#editSettlementOfEstateForm #editSettlementOfEstateAccountId').val(tempProject.clientAccountId);
           $('#editSettlementOfEstateForm #editSettlementProjectTitle').val(tempProject.accountTitle);
           if (tempProject.client) {
               $('#editSettlementOfEstateForm #editSettlementClient').val(tempProject.client.contactId);
           }
           if (tempProject.projectAccount) {
               $('#editSettlementOfEstateForm #editSettlementOfEstateProjectId').val(tempProject.projectAccount.projectId);
               $('#editSettlementOfEstateForm #editSettlementTitleNo').val(tempProject.projectAccount.titleNo);
               $('#editSettlementOfEstateForm #editSettlementDeceased').val(tempProject.projectAccount.deceased);
               $('#editSettlementOfEstateForm #editSettlementBir').val(tempProject.projectAccount.bir);
               $('#editSettlementOfEstateForm #editSettlementRd').val(tempProject.projectAccount.rd);
               $('#editSettlementOfEstateForm #editSettlementHeirs').val(tempProject.projectAccount.heirs);
               $('#editSettlementOfEstateForm #editSettlementAddress').val(tempProject.projectAccount.address);
               $('#editSettlementOfEstateForm #editSettlementRemarks').val(tempProject.projectAccount.remarks);
               $('#editSettlementOfEstateForm #editSettlementStatus').val(tempProject.projectAccount.status);
           }
           $('#editSettlementOfEstateModal').modal('show');
       });
   });

   // Update Title Annotation

   $('.table .editTitleAnnotationBtn').on('click', function(event) {
       event.preventDefault();
       var href = $(this).attr('href');

       $.get(href, function(tempProject, status) {
           $('#editTitleAnnotationForm #editTitleAnnotationAccountId').val(tempProject.clientAccountId);
           $('#editTitleAnnotationForm #editAnnotationProjectTitle').val(tempProject.accountTitle);
           if (tempProject.client) {
               $('#editTitleAnnotationForm #editAnnotationClient').val(tempProject.client.contactId);
           }
           if (tempProject.projectAccount) {
               $('#editTitleAnnotationForm #editTitleAnnotationProjectId').val(tempProject.projectAccount.projectId);

               $('#editTitleAnnotationForm #editAnnotationTitleNo').val(tempProject.projectAccount.titleNo);
               $('#editTitleAnnotationForm #editAnnotationTaxDeclarationNo').val(tempProject.projectAccount.taxDecNo);
               $('#editTitleAnnotationForm #editAnnotationLotNo').val(tempProject.projectAccount.lotNo);
               $('#editTitleAnnotationForm #editAnnotationLotArea').val(tempProject.projectAccount.lotArea);
               $('#editTitleAnnotationForm #editAnnotationLocation').val(tempProject.projectAccount.location);
               $('#editTitleAnnotationForm #editAnnotationBir').val(tempProject.projectAccount.bir);
               $('#editTitleAnnotationForm #editAnnotationRd').val(tempProject.projectAccount.rd);
               $('#editTitleAnnotationForm #editAnnotationZonalValue').val(tempProject.projectAccount.zonalValue);
               $('#editTitleAnnotationForm #editAnnotationPurchasePrice').val(tempProject.projectAccount.purchasePrice);
               $('#editTitleAnnotationForm #editAnnotationRemarks').val(tempProject.projectAccount.remarks);
               $('#editTitleAnnotationForm #editAnnotationStatus').val(tempProject.projectAccount.status);
           }
           $('#editTitleAnnotationModal').modal('show');
       });
   });

   // Update Title Other Transaction

   $('.table .editTitleOtherBtn').on('click', function(event) {
       event.preventDefault();
       var href = $(this).attr('href');

       $.get(href, function(tempProject, status) {
           $('#editTitleOtherForm #editTitleOtherAccountId').val(tempProject.clientAccountId);
           $('#editTitleOtherForm #editTitleOtherProjectTitle').val(tempProject.accountTitle);
           if (tempProject.client) {
               $('#editTitleOtherForm #editTitleOtherClient').val(tempProject.client.contactId);
           }
           if (tempProject.projectAccount) {
               $('#editTitleOtherForm #editTitleOtherProjectId').val(tempProject.projectAccount.projectId);

               $('#editTitleOtherForm #editTitleOtherTitleNo').val(tempProject.projectAccount.titleNo);
               $('#editTitleOtherForm #editTitleOtherTaxDeclarationNo').val(tempProject.projectAccount.taxDecNo);
               $('#editTitleOtherForm #editTitleOtherLotNo').val(tempProject.projectAccount.lotNo);
               $('#editTitleOtherForm #editTitleOtherLotArea').val(tempProject.projectAccount.lotArea);
               $('#editTitleOtherForm #editTitleOtherLocation').val(tempProject.projectAccount.location);
               $('#editTitleOtherForm #editTitleOtherBir').val(tempProject.projectAccount.bir);
               $('#editTitleOtherForm #editTitleOtherRd').val(tempProject.projectAccount.rd);
               $('#editTitleOtherForm #editTitleOtherZonalValue').val(tempProject.projectAccount.zonalValue);
               $('#editTitleOtherForm #editTitleOtherPurchasePrice').val(tempProject.projectAccount.purchasePrice);
               $('#editTitleOtherForm #editTitleOtherRemarks').val(tempProject.projectAccount.remarks);
               $('#editTitleOtherForm #editTitleOtherStatus').val(tempProject.projectAccount.status);
           }
           $('#editTitleOtherModal').modal('show');
       });
   });

   // Update Business Registration

   $('.table .editBusinessRegistrationBtn').on('click', function(event) {
       event.preventDefault();
       var href = $(this).attr('href');

       $.get(href, function(tempProject, status) {
           $('#editBusinessRegistrationForm #editBusinessRegistrationAccountId').val(tempProject.clientAccountId);
           $('#editBusinessRegistrationForm #editBusinessRegProjectTitle').val(tempProject.accountTitle);
           if (tempProject.client) {
               $('#editBusinessRegistrationForm #editBusinessRegClient').val(tempProject.client.contactId);
           }
           if (tempProject.projectAccount) {
               $('#editBusinessRegistrationForm #editBusinessRegistrationProjectId').val(tempProject.projectAccount.projectId);
               $('#editBusinessRegistrationForm #editBusinessRegRemarks').val(tempProject.projectAccount.remarks);
               $('#editBusinessRegistrationForm #editBusinessRegStatus').val(tempProject.projectAccount.status);
           }

           $('#editBusinessRegModal').modal('show');
       });
   });

   // Update Business Renewal

   $('.table .editBusinessRenewalBtn').on('click', function(event) {
       event.preventDefault();
       var href = $(this).attr('href');

       $.get(href, function(tempProject, status) {
           $('#editBusinessRenewalForm #editBusinessRenewalAccountId').val(tempProject.clientAccountId);
           $('#editBusinessRenewalForm #editBusinessRenewalProjectTitle').val(tempProject.accountTitle);
           if (tempProject.client) {
               $('#editBusinessRenewalForm #editBusinessRenewalClient').val(tempProject.client.contactId);
           }
           if (tempProject.projectAccount) {
               $('#editBusinessRenewalForm #editBusinessRenewalProjectId').val(tempProject.projectAccount.projectId);
               $('#editBusinessRenewalForm #editBusinessRenewalRemarks').val(tempProject.projectAccount.remarks);
               $('#editBusinessRenewalForm #editBusinessRenewalStatus').val(tempProject.projectAccount.status);
           }

           $('#editBusinessRenewalModal').modal('show');
       });
   });

   // Update Business Closure

   $('.table .editBusinessClosureBtn').on('click', function(event) {
       event.preventDefault();
       var href = $(this).attr('href');

       $.get(href, function(tempProject, status) {
           $('#editBusinessClosureForm #editBusinessClosureAccountId').val(tempProject.clientAccountId);
           $('#editBusinessClosureForm #editBusinessClosureProjectTitle').val(tempProject.accountTitle);
           if (tempProject.client) {
               $('#editBusinessClosureForm #editBusinessClosureClient').val(tempProject.client.contactId);
           }
           if (tempProject.projectAccount) {
               $('#editBusinessClosureForm #editBusinessClosureProjectId').val(tempProject.projectAccount.projectId);
               $('#editBusinessClosureForm #editBusinessClosureRemarks').val(tempProject.projectAccount.remarks);
               $('#editBusinessClosureForm #editBusinessClosureStatus').val(tempProject.projectAccount.status);
           }

           $('#editBusinessClosureModal').modal('show');
       });
   });

   // Update Business Other Transaction

   $('.table .editBusinessOtherBtn').on('click', function(event) {
       event.preventDefault();
       var href = $(this).attr('href');

       $.get(href, function(tempProject, status) {
           $('#editBusinessOtherForm #editBusinessOtherAccountId').val(tempProject.clientAccountId);
           $('#editBusinessOtherForm #editBusinessOtherProjectTitle').val(tempProject.accountTitle);
           if (tempProject.client) {
               $('#editBusinessOtherForm #editBusinessOtherClient').val(tempProject.client.contactId);
           }
           if (tempProject.projectAccount) {
               $('#editBusinessOtherForm #editBusinessOtherProjectId').val(tempProject.projectAccount.projectId);
               $('#editBusinessOtherForm #editBusinessOtherRemarks').val(tempProject.projectAccount.remarks);
               $('#editBusinessOtherForm #editBusinessOtherStatus').val(tempProject.projectAccount.status);
           }

           $('#editBusinessOtherModal').modal('show');
       });
   });

   // Update SEC Registration

   $('.table .editSecRegistrationBtn').on('click', function(event) {
       event.preventDefault();
       var href = $(this).attr('href');

       $.get(href, function(tempProject, status) {
           $('#editSecRegistrationForm #editSecRegistrationAccountId').val(tempProject.clientAccountId);
           $('#editSecRegistrationForm #editSecRegistrationProjectTitle').val(tempProject.accountTitle);
           if (tempProject.client) {
               $('#editSecRegistrationForm #editSecRegistrationClient').val(tempProject.client.contactId);
           }
           if (tempProject.projectAccount) {
               $('#editSecRegistrationForm #editSecRegistrationProjectId').val(tempProject.projectAccount.projectId);
               $('#editSecRegistrationForm #editSecRegistrationRemarks').val(tempProject.projectAccount.remarks);
               $('#editSecRegistrationForm #editSecRegistrationStatus').val(tempProject.projectAccount.status);
           }

           $('#editSecRegistrationModal').modal('show');
       });
   });

   // Update SEC Amendment of Articles

   $('.table .editSecAmendmentBtn').on('click', function(event) {
       event.preventDefault();
       var href = $(this).attr('href');

       $.get(href, function(tempProject, status) {
           $('#editSecAmendmentForm #editSecAmendmentAccountId').val(tempProject.clientAccountId);
           $('#editSecAmendmentForm #editSecAmendmentProjectTitle').val(tempProject.accountTitle);
           if (tempProject.client) {
               $('#editSecAmendmentForm #editSecAmendmentClient').val(tempProject.client.contactId);
           }
           if (tempProject.projectAccount) {
               $('#editSecAmendmentForm #editSecAmendmentProjectId').val(tempProject.projectAccount.projectId);
               $('#editSecAmendmentForm #editSecAmendmentRemarks').val(tempProject.projectAccount.remarks);
               $('#editSecAmendmentForm #editSecAmendmentStatus').val(tempProject.projectAccount.status);
           }

           $('#editSecAmendmentModal').modal('show');
       });
   });

   // Update SEC Stock Increase

   $('.table .editSecStockIncreaseBtn').on('click', function(event) {
       event.preventDefault();
       var href = $(this).attr('href');

       $.get(href, function(tempProject, status) {
           $('#editSecStockIncreaseForm #editSecStockIncreaseAccountId').val(tempProject.clientAccountId);
           $('#editSecStockIncreaseForm #editSecStockIncreaseProjectTitle').val(tempProject.accountTitle);
           if (tempProject.client) {
               $('#editSecStockIncreaseForm #editSecStockIncreaseClient').val(tempProject.client.contactId);
           }
           if (tempProject.projectAccount) {
               $('#editSecStockIncreaseForm #editSecStockIncreaseProjectId').val(tempProject.projectAccount.projectId);
               $('#editSecStockIncreaseForm #editSecStockIncreaseRemarks').val(tempProject.projectAccount.remarks);
               $('#editSecStockIncreaseForm #editSecStockIncreaseStatus').val(tempProject.projectAccount.status);
           }

           $('#editSecStockIncreaseModal').modal('show');
       });
   });
});
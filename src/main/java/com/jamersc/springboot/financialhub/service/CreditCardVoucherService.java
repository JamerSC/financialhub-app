package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.CheckDto;
import com.jamersc.springboot.financialhub.dto.CreditCardDto;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

@Service
public class CreditCardVoucherService {

    public ByteArrayInputStream generateCreditCardVoucher(CreditCardDto creditCardDto) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Set font style, size, and color
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.BLACK);
            Font regularFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);

            // 1st Row - Company Name, Address, Check Voucher Title, and CV Number
            PdfPTable firstRow = new PdfPTable(2);
            firstRow.setWidthPercentage(100);
            firstRow.setWidths(new float[]{75, 25});

            // Company Name (Left)
            PdfPCell companyNameCell = new PdfPCell(new Paragraph("Gomez Legal Services", titleFont));
            companyNameCell.setBorder(Rectangle.NO_BORDER);
            companyNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            firstRow.addCell(companyNameCell);

            // Check Voucher Title (Right)
            PdfPCell checkVoucherCell = new PdfPCell(new Paragraph("Credit Card", titleFont));
            checkVoucherCell.setBorder(Rectangle.NO_BORDER);
            checkVoucherCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            firstRow.addCell(checkVoucherCell);

            // Company Address (Left)
            String address = "Unit 302 New JM Building Km 25 McArthur Highway, Lolomboy, Bocaue, Bulacan";
            PdfPCell companyAddressCell = new PdfPCell(new Paragraph(address, regularFont));
            companyAddressCell.setBorder(Rectangle.NO_BORDER);
            companyAddressCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            firstRow.addCell(companyAddressCell);

            // CV Number (Right)
            PdfPCell cvNumberCell = new PdfPCell(new Paragraph("CV No.: " + creditCardDto.getCcvNumber(), regularFont));
            cvNumberCell.setBorder(Rectangle.NO_BORDER);
            cvNumberCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            firstRow.addCell(cvNumberCell);

            // Company Contact (Left)
            String contact = "Contact: (123) 456-7890 | Email: info@gomezlegal.com";
            PdfPCell companyContactCell = new PdfPCell(new Paragraph(contact, regularFont));
            companyContactCell.setBorder(Rectangle.NO_BORDER);
            companyContactCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            firstRow.addCell(companyContactCell);

            // Empty cell to align with the CV Number
            PdfPCell emptyCell = new PdfPCell(new Paragraph("", regularFont));
            emptyCell.setBorder(Rectangle.NO_BORDER);
            firstRow.addCell(emptyCell);

            document.add(firstRow);

            // Horizontal Solid Line
            document.add(new Paragraph(new Chunk(new LineSeparator())));

            // 2nd Row - Pay To and Date
            PdfPTable secondRow = new PdfPTable(2);
            secondRow.setWidthPercentage(100);
            secondRow.setWidths(new float[]{75, 25});

            // Pay To (Left)
            PdfPCell payToCell = new PdfPCell(new Paragraph("Pay to: " + creditCardDto.getPayeeName(), regularFont));
            payToCell.setBorder(Rectangle.NO_BORDER);
            payToCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            secondRow.addCell(payToCell);

            // Date (Right)
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
            String dateFormatted = dateFormat.format(creditCardDto.getDate());
            PdfPCell dateCell = new PdfPCell(new Paragraph("Date: " + dateFormatted , regularFont));
            dateCell.setBorder(Rectangle.NO_BORDER);
            dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            secondRow.addCell(dateCell);

            document.add(secondRow);

            // Horizontal Solid Line
            document.add(new Paragraph(new Chunk(new LineSeparator())));

            // 3rd Row - Particulars and Amount
            PdfPTable thirdRow = new PdfPTable(2);
            thirdRow.setWidthPercentage(100);
            thirdRow.setWidths(new float[]{75, 25});

            // Particulars Title (Left)
            PdfPCell particularsTitleCell = new PdfPCell(new Paragraph("Particulars", regularFont));
            particularsTitleCell.setBorder(Rectangle.NO_BORDER);
            particularsTitleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            thirdRow.addCell(particularsTitleCell);

            // Amount Title (Right)
            PdfPCell amountTitleCell = new PdfPCell(new Paragraph("Amount", regularFont));
            amountTitleCell.setBorder(Rectangle.NO_BORDER);
            amountTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            thirdRow.addCell(amountTitleCell);

            // Particulars Detail (Left)
            PdfPCell particularsDetailCell = new PdfPCell(new Paragraph(creditCardDto.getCcvNumber(), regularFont));
            particularsDetailCell.setBorder(Rectangle.NO_BORDER);
            particularsDetailCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            thirdRow.addCell(particularsDetailCell);

            // Amount Detail (Right)
            PdfPCell amountDetailCell = new PdfPCell(new Paragraph(String.format("%.2f", creditCardDto.getTotalAmount()), regularFont));
            amountDetailCell.setBorder(Rectangle.NO_BORDER);
            amountDetailCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            thirdRow.addCell(amountDetailCell);

            document.add(thirdRow);

            // Add a New Line
            document.add(Chunk.NEWLINE);

            // "Nothing Follows"
            Paragraph nothingFollows = new Paragraph("Nothing Follows", regularFont);
            nothingFollows.setAlignment(Element.ALIGN_CENTER);
            document.add(nothingFollows);

            // Horizontal Solid Line
            document.add(new Paragraph(new Chunk(new LineSeparator())));

            // Grand Total
            PdfPTable grandTotalRow = new PdfPTable(2);
            grandTotalRow.setWidthPercentage(100);
            grandTotalRow.setWidths(new float[]{75, 25});

            PdfPCell grandTotalTitleCell = new PdfPCell(new Paragraph("Grand Total:", regularFont));
            grandTotalTitleCell.setBorder(Rectangle.NO_BORDER);
            grandTotalTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            grandTotalRow.addCell(grandTotalTitleCell);

            PdfPCell grandTotalAmountCell = new PdfPCell(new Paragraph(String.format("%.2f", creditCardDto.getTotalAmount()), regularFont));
            grandTotalAmountCell.setBorder(Rectangle.NO_BORDER);
            grandTotalAmountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            grandTotalRow.addCell(grandTotalAmountCell);

            document.add(grandTotalRow);

            // Add new lines
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            // Amount in Words
            Paragraph amountInWords = new Paragraph("Amount in words: " + creditCardDto.getAmountInWords(), regularFont);
            amountInWords.setAlignment(Element.ALIGN_LEFT);
            document.add(amountInWords);

            // Bank, Check No., and Check Date
            Paragraph bankDetails = new Paragraph("Bank: " + creditCardDto.getBank() + ", Check No.: 0000-0000-0000-0000, Check Date: " + creditCardDto.getDate(), regularFont);
            bankDetails.setAlignment(Element.ALIGN_LEFT);
            document.add(bankDetails);

            // Horizontal Line
            document.add(new Paragraph(new Chunk(new LineSeparator())));

            // Prepared by, Checked by, Approved by, Received by
            PdfPTable signatoriesTable = new PdfPTable(4);
            signatoriesTable.setWidthPercentage(100);
            signatoriesTable.setSpacingBefore(10f);
            signatoriesTable.setSpacingAfter(10f);
            signatoriesTable.setWidths(new float[]{25, 25, 25, 25});

            PdfPCell preparedByCell = new PdfPCell(new Paragraph("Prepared by:", regularFont));
            preparedByCell.setBorder(Rectangle.NO_BORDER);
            preparedByCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            signatoriesTable.addCell(preparedByCell);

            PdfPCell checkedByCell = new PdfPCell(new Paragraph("Checked by:", regularFont));
            checkedByCell.setBorder(Rectangle.NO_BORDER);
            checkedByCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            signatoriesTable.addCell(checkedByCell);

            PdfPCell approvedByCell = new PdfPCell(new Paragraph("Approved by:", regularFont));
            approvedByCell.setBorder(Rectangle.NO_BORDER);
            approvedByCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            signatoriesTable.addCell(approvedByCell);

            PdfPCell receivedByCell = new PdfPCell(new Paragraph("Received by:", regularFont));
            receivedByCell.setBorder(Rectangle.NO_BORDER);
            receivedByCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            signatoriesTable.addCell(receivedByCell);

            // Add space for signatures/names
            PdfPCell preparedByNameCell = new PdfPCell(new Paragraph("__________________", regularFont));
            preparedByNameCell.setBorder(Rectangle.NO_BORDER);
            preparedByNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            signatoriesTable.addCell(preparedByNameCell);

            PdfPCell checkedByNameCell = new PdfPCell(new Paragraph("__________________", regularFont));
            checkedByNameCell.setBorder(Rectangle.NO_BORDER);
            checkedByNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            signatoriesTable.addCell(checkedByNameCell);

            PdfPCell approvedByNameCell = new PdfPCell(new Paragraph("__________________", regularFont));
            approvedByNameCell.setBorder(Rectangle.NO_BORDER);
            approvedByNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            signatoriesTable.addCell(approvedByNameCell);

            PdfPCell receivedByNameCell = new PdfPCell(new Paragraph("__________________", regularFont));
            receivedByNameCell.setBorder(Rectangle.NO_BORDER);
            receivedByNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            signatoriesTable.addCell(receivedByNameCell);

            document.add(signatoriesTable);

            // Close document
            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Error generating PDF", e);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}

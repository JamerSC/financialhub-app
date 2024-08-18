package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.DottedLineSeparator;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

@Service
public class PettyCashVoucherService {

    public ByteArrayInputStream generatePettyCashVoucher(PettyCashDto pettyCashDto) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Set font style, size, and color
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.BLACK);
            Font regularFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);
            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.GRAY);

            // Function to create the copy (original or duplicate)
            Runnable createCopy = (Runnable) () -> {
                try {
                    // Create a table for the title with two columns
                    PdfPTable titleTable = new PdfPTable(2);
                    titleTable.setWidthPercentage(100); // Full width
                    titleTable.setSpacingBefore(10f);   // Space before table
                    titleTable.setSpacingAfter(10f);    // Space after table
                    titleTable.setWidths(new float[]{1f, 1f}); // Equal column widths

                    // Left-aligned title
                    PdfPCell leftTitleCell = new PdfPCell(new Phrase("Petty Cash Voucher", titleFont));
                    leftTitleCell.setBorder(Rectangle.NO_BORDER); // No border
                    leftTitleCell.setHorizontalAlignment(Element.ALIGN_LEFT);

                    // Right-aligned title
                    PdfPCell rightTitleCell = new PdfPCell(new Phrase("No. " + pettyCashDto.getPcvNumber(), titleFont));
                    rightTitleCell.setBorder(Rectangle.NO_BORDER); // No border
                    rightTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

                    // Add cells to the title table
                    titleTable.addCell(leftTitleCell);
                    titleTable.addCell(rightTitleCell);

                    // Add the title table to the document
                    document.add(titleTable);

                    // Add space after the title
                    // document.add(Chunk.NEWLINE);

                    // Create a table for the receiver and date (First Row)
                    PdfPTable receiverDateTable = new PdfPTable(2);
                    receiverDateTable.setWidthPercentage(100);
                    receiverDateTable.setSpacingBefore(0f); // No spacing
                    receiverDateTable.setSpacingAfter(0f);
                    receiverDateTable.setWidths(new float[]{1f, 1f});

                    // Receiver name
                    PdfPCell receiverNameCell = new PdfPCell(new Phrase("To: " + pettyCashDto.getReceivedBy(), regularFont));
                    receiverNameCell.setBorder(Rectangle.BOX); // Border added
                    receiverNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    receiverNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    // Received date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
                    String formattedDate = dateFormat.format(pettyCashDto.getDate());
                    PdfPCell receivedDateCell = new PdfPCell(new Phrase("Date: " + formattedDate, regularFont));
                    receivedDateCell.setBorder(Rectangle.BOX); // Border added
                    receivedDateCell.setHorizontalAlignment(Element.ALIGN_LEFT); // Align to left
                    receivedDateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    // Add cells to the receiver and date table
                    receiverDateTable.addCell(receiverNameCell);
                    receiverDateTable.addCell(receivedDateCell);

                    // Add the receiver and date table to the document
                    document.add(receiverDateTable);

                    // Create a table for the particulars and amount headers (Second Row)
                    PdfPTable particularsHeaderTable = new PdfPTable(2);
                    particularsHeaderTable.setWidthPercentage(100);
                    particularsHeaderTable.setSpacingBefore(0f); // No spacing
                    particularsHeaderTable.setSpacingAfter(0f);
                    particularsHeaderTable.setWidths(new float[]{7f, 3f}); // 70% and 30% widths

                    // Particulars header
                    PdfPCell particularsHeaderCell = new PdfPCell(new Phrase("Particulars", regularFont));
                    particularsHeaderCell.setBorder(Rectangle.BOX); // Border added
                    particularsHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    particularsHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    // Amount header
                    PdfPCell amountHeaderCell = new PdfPCell(new Phrase("Amount", regularFont));
                    amountHeaderCell.setBorder(Rectangle.BOX); // Border added
                    amountHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    amountHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    // Add header cells to the table
                    particularsHeaderTable.addCell(particularsHeaderCell);
                    particularsHeaderTable.addCell(amountHeaderCell);

                    // Add the particulars and amount header table to the document
                    document.add(particularsHeaderTable);

                    // Create a table for the particulars details and amount (Third Row)
                    PdfPTable particularsDetailTable = new PdfPTable(2);
                    particularsDetailTable.setWidthPercentage(100);
                    particularsDetailTable.setSpacingBefore(0f); // No spacing
                    particularsDetailTable.setSpacingAfter(0f);
                    particularsDetailTable.setWidths(new float[]{7f, 3f}); // 70% and 30% widths

                    // Particulars details
                    PdfPCell particularsDetailCell = new PdfPCell(new Phrase(pettyCashDto.getParticulars(), regularFont));
                    particularsDetailCell.setBorder(Rectangle.BOX); // Border added
                    particularsDetailCell.setHorizontalAlignment(Element.ALIGN_LEFT); // Align to left
                    particularsDetailCell.setVerticalAlignment(Element.ALIGN_TOP); // Align to top
                    particularsDetailCell.setFixedHeight(200f); // Fixed height

                    // Amount details
                    String formattedAmount = String.format("%.2f", pettyCashDto.getTotalAmount());
                    PdfPCell amountDetailCell = new PdfPCell(new Phrase(formattedAmount, regularFont));
                    amountDetailCell.setBorder(Rectangle.BOX); // Border added
                    amountDetailCell.setHorizontalAlignment(Element.ALIGN_CENTER); // Centered
                    amountDetailCell.setVerticalAlignment(Element.ALIGN_TOP); // Align to top
                    amountDetailCell.setFixedHeight(200f); // Fixed height

                    // Add detail cells to the table
                    particularsDetailTable.addCell(particularsDetailCell);
                    particularsDetailTable.addCell(amountDetailCell);

                    // Add the particulars details and amount table to the document
                    document.add(particularsDetailTable);

                    // Create a table for the total (Fourth Row)
                    PdfPTable totalTable = new PdfPTable(2);
                    totalTable.setWidthPercentage(100);
                    totalTable.setSpacingBefore(0f); // No spacing
                    totalTable.setSpacingAfter(0f);
                    totalTable.setWidths(new float[]{7f, 3f}); // 70% and 30% widths

                    // Total header
                    PdfPCell totalHeaderCell = new PdfPCell(new Phrase("Total", regularFont));
                    totalHeaderCell.setBorder(Rectangle.BOX); // Border added
                    totalHeaderCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    totalHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    // Total amount cell
                    PdfPCell totalAmountCell = new PdfPCell(new Phrase(formattedAmount, regularFont));
                    totalAmountCell.setBorder(Rectangle.BOX); // Border added
                    totalAmountCell.setHorizontalAlignment(Element.ALIGN_CENTER); // Centered
                    totalAmountCell.setVerticalAlignment(Element.ALIGN_TOP); // Align to top

                    // Add cells to the total table
                    totalTable.addCell(totalHeaderCell);
                    totalTable.addCell(totalAmountCell);

                    // Add the total table to the document
                    document.add(totalTable);

                    // Create a table for approved by and received by (Fifth Row)
                    PdfPTable approvalTable = new PdfPTable(2);
                    approvalTable.setWidthPercentage(100);
                    approvalTable.setSpacingBefore(0f); // No spacing
                    approvalTable.setSpacingAfter(0f);
                    approvalTable.setWidths(new float[]{1f, 1f}); // Equal column widths

                    // Approved by
                    PdfPCell approvedByCell = new PdfPCell(new Phrase("Approved By: " + pettyCashDto.getApprovedBy(), regularFont));
                    approvedByCell.setBorder(Rectangle.BOX); // Border added
                    approvedByCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    approvedByCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    // Received by
                    PdfPCell receivedByCell = new PdfPCell(new Phrase("Received By: " + pettyCashDto.getReceivedBy(), regularFont));
                    receivedByCell.setBorder(Rectangle.BOX); // Border added
                    receivedByCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    receivedByCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    // Add cells to the approval table
                    approvalTable.addCell(approvedByCell);
                    approvalTable.addCell(receivedByCell);

                    // Add the approval table to the document
                    document.add(approvalTable);
                } catch (DocumentException e) {
                    throw new RuntimeException("Error generating PDF", e);
                }
            };

            // Add "Original Copy" label
            document.add(new Paragraph("Original Copy", labelFont));

            // Generate the original copy
            createCopy.run();

            // Add a horizontal broken line
            document.add(new Paragraph(new Chunk(new DottedLineSeparator())));

            // Add "Duplicate Copy" label
            document.add(new Paragraph("Duplicate Copy", labelFont));

            // Generate the duplicate copy
            createCopy.run();

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Error generating PDF", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }


}

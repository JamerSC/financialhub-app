package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    // Create my PDF Service

    //ByteArrayInputStream contains an internal buffer that contains bytes that may be read from the stream.
    public ByteArrayInputStream generatePettyCashList(List<PettyCash> pettyCash) {
        //Document - Maven: com.github.librepdf:openpdf:1.3.27 (openpdf-1.3.27.jar)
        Document document = new Document();
        //this class implements an output stream in which the data is written into a byte array.
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("Petty Cash"));
            for (PettyCash tempPettyCash : pettyCash) {
                document.add(new Paragraph("PCV No.: " + tempPettyCash.getPcvNumber()));
                document.add(new Paragraph("Particulars: " + tempPettyCash.getParticulars()));
                // Format the amount to two decimal places
                String formattedAmount = String.format("%.2f", tempPettyCash.getTotalAmount());
                document.add(new Paragraph("Amount: " + formattedAmount));
                document.add(new Paragraph("Received By: " + tempPettyCash.getReceivedBy()));
                document.add(new Paragraph("Approved By: " + tempPettyCash.getApprovedBy()));
                document.add(new Paragraph("------------------------------"));
            }
            document.close();
        } catch (DocumentException e) {
           throw new RuntimeException("Error generating PDF", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

/*    public ByteArrayInputStream generatePettyCashVoucher(PettyCashDto pettyCashDto) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("Petty Cash Voucher No. " + pettyCashDto.getPcvNumber()));
            document.add(new Paragraph("PCV No.: " + pettyCashDto.getPcvNumber()));
            document.add(new Paragraph("Particulars: " + pettyCashDto.getParticulars()));
            // Format the amount to two decimal places
            String formattedAmount = String.format("%.2f", pettyCashDto.getTotalAmount());
            document.add(new Paragraph("Amount: " + formattedAmount));
            document.add(new Paragraph("Received By: " + pettyCashDto.getReceivedBy()));
            document.add(new Paragraph("Approved By: " + pettyCashDto.getApprovedBy()));
            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Error generating PDF", e);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }*/
public ByteArrayInputStream generatePettyCashVoucher(PettyCashDto pettyCashDto) {
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try {
        PdfWriter.getInstance(document, out);
        document.open();

        // Set font style, size, and color
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.BLUE);
        Font regularFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);

        // Title
        Paragraph title = new Paragraph("Petty Cash Voucher No. " + pettyCashDto.getPcvNumber(), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);  // Align title to the center
        document.add(title);

        // Adding some space after the title
        document.add(Chunk.NEWLINE);

        // PCV Number
        Paragraph pcvNumber = new Paragraph("PCV No.: " + pettyCashDto.getPcvNumber(), regularFont);
        pcvNumber.setAlignment(Element.ALIGN_LEFT);  // Align to the left
        document.add(pcvNumber);

        // Particulars
        Paragraph particulars = new Paragraph("Particulars: " + pettyCashDto.getParticulars(), regularFont);
        particulars.setAlignment(Element.ALIGN_LEFT);
        document.add(particulars);

        // Format the amount to two decimal places
        String formattedAmount = String.format("%.2f", pettyCashDto.getTotalAmount());
        Paragraph amount = new Paragraph("Amount: " + formattedAmount, regularFont);
        amount.setAlignment(Element.ALIGN_LEFT);
        document.add(amount);

        // Received By
        Paragraph receivedBy = new Paragraph("Received By: " + pettyCashDto.getReceivedBy(), regularFont);
        receivedBy.setAlignment(Element.ALIGN_LEFT);
        document.add(receivedBy);

        // Approved By
        Paragraph approvedBy = new Paragraph("Approved By: " + pettyCashDto.getApprovedBy(), regularFont);
        approvedBy.setAlignment(Element.ALIGN_LEFT);
        document.add(approvedBy);

        // Close document
        document.close();
    } catch (DocumentException e) {
        throw new RuntimeException("Error generating PDF", e);
    }

    return new ByteArrayInputStream(out.toByteArray());
}
}

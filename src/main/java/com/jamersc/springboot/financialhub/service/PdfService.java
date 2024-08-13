package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.model.PettyCash;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    // Create my PDF Service

    //ByteArrayInputStream contains an internal buffer that contains bytes that may be read from the stream.
    public ByteArrayInputStream generatePettyCashVoucher(List<PettyCash> pettyCash) {
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
                document.add(new Paragraph("Amount: " + tempPettyCash.getTotalAmount()));
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
}

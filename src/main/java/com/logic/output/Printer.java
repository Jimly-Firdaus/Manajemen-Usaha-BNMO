package com.logic.output;

import java.util.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.Chunk;
import java.io.FileOutputStream;

import com.logic.constant.Payment;
import com.logic.feature.Bill;

public class Printer{
    public static void printPayments(List<Payment> payments) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("../Payments.pdf"));
            document.open();
            LineSeparator ls = new LineSeparator();
            for (Payment payment : payments) {
                document.add(new Paragraph(payment.toString()));
                document.add(new Chunk(ls));
                // Sleep for 1 second
                Thread.sleep(100);
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printBills(List<Bill> bills) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("../Bills.pdf"));
            document.open();
            LineSeparator ls = new LineSeparator();
            for (Bill bill : bills) {
                document.add(new Paragraph(bill.toString()));
                document.add(new Chunk(ls));
                // Sleep for 1 second
                Thread.sleep(100);
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
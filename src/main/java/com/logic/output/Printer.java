package com.logic.output;

import java.util.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import lombok.AllArgsConstructor;
import lombok.Setter;

import com.itextpdf.text.Chunk;
import java.io.FileOutputStream;

@AllArgsConstructor
@Setter
public class Printer <T> extends Thread {
    private List<T> data;
    private String outputPathName;

    public static <T> void print(List<T> data, String outputPathName) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPathName));
            document.open();
            LineSeparator ls = new LineSeparator();
            for (T line : data) {
                document.add(new Paragraph(line.toString()));
                document.add(new Chunk(ls));
                // Sleep for 0.1 second
                Thread.sleep(100);
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        print(this.data, this.outputPathName);
    }
}
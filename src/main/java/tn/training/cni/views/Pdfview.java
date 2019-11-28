package tn.training.cni.views;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/19 0019.
 */
public class Pdfview extends AbstractITextPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document, PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        Paragraph header = new Paragraph(new Chunk("PDF File"));
        document.add(header);
        document.add(new Paragraph("List Users"));
    }
}

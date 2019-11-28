package tn.training.cni.views;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.core.io.ClassPathResource;
import org.xhtmlrenderer.pdf.ITextRenderer;
import tn.training.cni.utilities.FreemarkerUtils;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.Map;

public class HtmlPdfview extends AbstractITextPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document, PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        URL fileResource = HtmlPdfview.class.getResource("/templates");
        String html = FreemarkerUtils.loadFtlHtml(new File(fileResource.getFile()), "simpleForm.ftl", model);
        CSSResolver cssResolver = new StyleAttrCSSResolver();
        InputStream inputstream = new ClassPathResource("css\\bootstrap.css").getInputStream();
        ByteArrayInputStream bis = new ByteArrayInputStream(html.getBytes());
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, bis,inputstream);
    }
}

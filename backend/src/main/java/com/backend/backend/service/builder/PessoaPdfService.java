package com.backend.backend.service.builder;

import com.backend.backend.dto.PessoaDTO;
import com.backend.backend.service.PessoaService;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.lowagie.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
public class PessoaPdfService extends PdfPageEventHelper {

    private PessoaService pessoaService;

    public final CSSResolver cssResolver;


    public byte[] constroiPdf(Long id) throws Exception {
        byte[] bFile = this.geraPdf(pessoaService.buscarPessoa(id));
        return bFile;
    }

    private byte[] geraPdf(PessoaDTO pessoa) throws IOException, com.itextpdf.text.DocumentException, DocumentException {
        String html = this.parseThymeleafTemplate(pessoa);
        return this.generatePdfFromHtml(html);
    }

    private byte[] generatePdfFromHtml(String html) throws IOException, DocumentException, com.itextpdf.text.DocumentException {

        Document document = new Document();

        ByteArrayOutputStream documentoStream = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, documentoStream);

        InputStream templateStream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
        document.open();

        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline htmlDocumento = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, htmlDocumento);


        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(templateStream);

        document.close();
        return documentoStream.toByteArray();
    }

    private String parseThymeleafTemplate(PessoaDTO pessoa) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("nome", pessoa.getNome());
        context.setVariable("apelido", pessoa.getApelido());
        context.setVariable("timeCoracao", pessoa.getTimeCoracao());
        context.setVariable("cpf", pessoa.getCpf());
        context.setVariable("hobbie", pessoa.getHobbie());
        context.setVariable("cidade", pessoa.getCidade().getNome());

        return templateEngine.process("templates/pessoaTemplate", context);
    }
}

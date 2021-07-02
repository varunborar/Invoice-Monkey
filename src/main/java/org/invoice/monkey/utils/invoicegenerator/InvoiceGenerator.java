package org.invoice.monkey.utils.invoicegenerator;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFontFamilies;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import org.invoice.monkey.model.Configurations.Configuration;
import org.invoice.monkey.model.Configurations.InvoiceDetails;
import org.invoice.monkey.model.Customer;
import org.invoice.monkey.model.Invoice;
import org.invoice.monkey.model.InvoiceItem;
import org.invoice.monkey.utils.invoicegenerator.background.Background;
import org.invoice.monkey.utils.invoicegenerator.background.BackgroundType;
import org.invoice.monkey.utils.invoicegenerator.templates.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;

public class InvoiceGenerator {

    private final Invoice invoice;
    private final Configuration configuration;
    private InvoiceTemplate template;
    private final String destination;

    private final BackgroundType backgroundType;

    private PdfDocument pdfDoc;
    private Document document;

    private PdfFont font;

    public InvoiceGenerator(Invoice invoice) {
        this.invoice = invoice;
        this.configuration = new Configuration();
        this.backgroundType = configuration.getInvoiceDetails().getBackground();

        TemplateType template = configuration.getInvoiceDetails().getTemplate();

        switch(template)
        {
            case DefaultTemplate:
                this.template = new DefaultInvoiceTemplate(configuration, invoice);
                break;
            case TemplateA:
                this.template = new TemplateA(configuration, invoice);
                break;
            case TemplateB:
                this.template = new TemplateB(configuration, invoice);
                break;
        }


        this.destination = configuration.getAppConfigurations().getDefaultLocation() + "\\" + invoice.getRawInvoiceID() + ".pdf";

        try {
            font = PdfFontFactory.createFont(FontProgramFactory.createFont(
                    new File(getClass().getResource("Poppins.ttf").toURI()).getAbsolutePath()
            ),
                    PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        }catch(IOException | URISyntaxException e)
        {
            e.printStackTrace();
        }
    }

    public void generate()
    {
        try{
            PdfWriter writer = new PdfWriter(destination);
            pdfDoc = new PdfDocument(writer);


            pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, template.getFooterEvent());
            pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, template.getHeaderEvent());
            pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new Background(backgroundType));

            pdfDoc.setDefaultPageSize(PageSize.A4);
            pdfDoc.addNewPage();

            document = new Document(pdfDoc);
            document.setMargins( 250f,36f,36f,36f );

            document.setFont(font);
            // Adding items to Invoice
            Vector<Table> itemTables = template.getItemTables(invoice.getItemList());

            document.add(itemTables.get(0));  //.setMarginTop(template.toPoints(1.1f)).setMarginBottom(0.7f));
            for(int i = 1; i < itemTables.size(); i++)
            {
                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                document.add(itemTables.get(i));
            }

            document.close();

        }catch(Exception e)
        {
            //System.out.println("generate" + e.getClass().getName() + ": " + e.getMessage() + " " + e.getCause());
            e.printStackTrace();
        }
    }

    public String getDestination()
    {
        return this.destination;
    }

    public static void main(String[] args)
    {

        Customer customer = new Customer("Test Person");
        customer.setEmail("testmail@example.com");

        InvoiceItem item1 = new InvoiceItem("Web-Site Design", 4999.00f);
        item1.setQuantity(2);

        InvoiceItem item2 = new InvoiceItem("Logo Mockup", 799.00f);
        item2.setQuantity(1);

        Vector<InvoiceItem> itemSet = new Vector<>();
        itemSet.add(item1);
        itemSet.add(item2);

        for(int i = 0; i < 20; i++)
        {
            InvoiceItem item = new InvoiceItem("Test Item : " + (i+1) , new Random().nextFloat()*100);
            item.setQuantity(new Random().nextInt(10) + 1);
            itemSet.add(item);
        }


        Invoice invoice = new Invoice();
        invoice.setDescription("Test Invoice");

        invoice.setType("TEST");
        invoice.setDue("PAID");

        invoice.setDate(Date.valueOf("2021-06-12"));
        invoice.setTime(Time.valueOf("23:08:00"));

        invoice.setCustomer(customer);
        invoice.addAllItems(itemSet);

        invoice.calcSubTotal();
        invoice.setDiscount(0f);
        invoice.calTotal();


        InvoiceGenerator ig = new InvoiceGenerator(invoice);
        ig.generate();
        //System.out.println("\u20B9");
    }
}


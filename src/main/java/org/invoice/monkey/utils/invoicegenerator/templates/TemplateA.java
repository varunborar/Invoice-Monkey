package org.invoice.monkey.utils.invoicegenerator.templates;

import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import org.invoice.monkey.model.Configurations.Address;
import org.invoice.monkey.model.Configurations.Configuration;
import org.invoice.monkey.model.Configurations.OrgDetails;
import org.invoice.monkey.model.Customer;
import org.invoice.monkey.model.Invoice;
import org.invoice.monkey.model.InvoiceItem;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Vector;

public class TemplateA implements InvoiceTemplate{

    private final OrgDetails orgDetails;
    private final Address address;
    private final Customer customer;
    private final String InvoiceID;
    private final String logo;
    private final String Date;
    private final String Time;
    private final String Due;
    private final String Type;
    private final String Description;
    private final String subTotal;
    private final String discount;
    private final String total;
    private final String currency;
    private PdfFont font;

    public TemplateA(Configuration configuration, Invoice invoice)
    {
        this.orgDetails = configuration.getOrgDetails();
        this.address = configuration.getAddress();
        this.customer = invoice.getCustomer();
        this.InvoiceID = invoice.getRawInvoiceID().toString();
        this.logo = configuration.getAppConfigurations().getLogoLocation();
        this.Date = invoice.getDate().toString();
        this.Time = invoice.getTime().toString();
        this.Due = invoice.getDue();
        this.Type = invoice.getType();
        this.Description = invoice.getDescription();
        this.subTotal = String.format("%.2f",invoice.getSubTotal());
        this.discount = String.format("%.2f",invoice.getDiscount());
        this.total = String.format("%.2f",invoice.getTotal());
        this.currency = configuration.getInvoiceDetails().getCurrency().getCurrencySymbol();

        try {
            this.font = PdfFontFactory.createFont(FontProgramFactory.createFont(
                    new File(getClass().getResource("Poppins.ttf").toURI()).getAbsolutePath()
                    ),
                    PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        }catch(IOException | URISyntaxException e)
        {
            e.printStackTrace();
        }
    }

    private Cell getCell(String content, Float Size)
    {
        return new Cell().add(new Paragraph(content).setFont(font).setFontSize(Size).setFixedLeading(12)).setBorder(Border.NO_BORDER);
    }

    public Table getHeader()
    {
        float[] colWidths = {toPoints(5.25f), toPoints(13.20f)};
        Table header = new Table(colWidths);
        header.setMarginBottom(15f);

        try {
            header.addCell(new Cell(2, 1)
                    .add(new Image(ImageDataFactory.create(logo))
                            .setAutoScale(true)
                            .setMarginLeft(9f))
                    .setBorder(Border.NO_BORDER)
                    .setHeight(toPoints(4f)));
        }catch(MalformedURLException mf)
        {
            System.out.println(mf.getClass().getName() + ": " + mf.getMessage());
        }

        header.addCell(getCell(orgDetails.getOrgName().toUpperCase(),16f)
                .setTextAlignment(TextAlignment.RIGHT)
                .setVerticalAlignment(VerticalAlignment.BOTTOM));

        header.addCell(getCell( String.format("%s,\n%s, %s,\n Postal-Code: %s\n%s\n%s",
                address.getOrgAddress(),
                address.getOrgCity(),
                address.getOrgState(),
                address.getOrgPostalCode(),
                orgDetails.getOrgNumber(),
                orgDetails.getOrgEmail()),
                9f)
                .setTextAlignment(TextAlignment.RIGHT)
                .setVerticalAlignment(VerticalAlignment.BOTTOM));

        header.setBorder(Border.NO_BORDER);

        return header;
    }

    public Table getDetails()
    {

        float[] colWidths = {toPoints(9.2f), toPoints(9.2f)};
        Table header = new Table(colWidths);
        header.setMarginBottom(15f);

        header.addCell(getCell("Invoice ID:" + String.format("#%s", InvoiceID),
                9f )
                .setBold()
                .setTextAlignment(TextAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.MIDDLE));

        header.addCell(getCell(String.format("Date: %s", this.Date), 9f)
                .setTextAlignment(TextAlignment.RIGHT)
                .setVerticalAlignment(VerticalAlignment.MIDDLE));

        header.addCell(getCell("Billed To,\n" +
                String.format(" %s\n", customer.getName()) +
                String.format(" %s", customer.getPhoneNumber().equals("")?customer.getEmail():customer.getPhoneNumber()) +
                String.format("\n%s - %s", Due, Type),
                9f)
                .setTextAlignment(TextAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.BOTTOM));

        header.addCell(getCell(customer.getAddress(), 9f)
                .setTextAlignment(TextAlignment.RIGHT)
                .setVerticalAlignment(VerticalAlignment.MIDDLE));

        header.setBorder(Border.NO_BORDER);
        return header;
    }

    public Table getFooter()
    {
        float[] colWidths = {368.5040f, 154.4882f};
        Table footer = new Table(colWidths);
        footer.setMarginTop(35f);

        footer.addCell(getCell(String.format("%s", Description)
                , 9f).setItalic()
                .setTextAlignment(TextAlignment.LEFT));

        footer.addCell(getCell(String.format("Authorized Signatory\n %s", orgDetails.getOrgSignatory()),
                9f)
                .setBold()
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER));

        return footer;
    }

    public Vector<Table> getItemTables(Vector<InvoiceItem> itemList)
    {
        Vector<Table> tables = new Vector<>();

        InvoiceItem[] items = new InvoiceItem[itemList.size()];
        int m = 0;
        for(InvoiceItem i: itemList)
        {
            items[m] = i;
            m++;
        }

        int n = items.length;
        int i = 0;

        while(i < n) {
            float[] colWidths = {toPoints(9.24f), toPoints(3f), toPoints(3f), toPoints(3f)};
            Table itemTableTemplate = new Table(colWidths);
            itemTableTemplate.addCell(new Cell().add(
                    new Paragraph("Description")
                    ).setTextAlignment(TextAlignment.CENTER)
                            .setVerticalAlignment(VerticalAlignment.MIDDLE)
                            .setBold()
                            .setBorder(Border.NO_BORDER)
                            .setBackgroundColor(ColorConstants.DARK_GRAY)
                            .setFontColor(ColorConstants.WHITE)
                            .setHeight(toPoints(0.8f))
                            .setFontSize(10f)
            );

            itemTableTemplate.addCell(new Cell().add(
                    new Paragraph("Rate")
                    ).setTextAlignment(TextAlignment.CENTER)
                            .setVerticalAlignment(VerticalAlignment.MIDDLE)
                            .setBold()
                            .setBorder(Border.NO_BORDER)
                            .setBackgroundColor(ColorConstants.DARK_GRAY)
                            .setFontColor(ColorConstants.WHITE)
                            .setHeight(toPoints(0.8f))
                            .setFontSize(10f)
            );

            itemTableTemplate.addCell(new Cell().add(
                    new Paragraph("Quantity")
                    ).setTextAlignment(TextAlignment.CENTER)
                            .setVerticalAlignment(VerticalAlignment.MIDDLE)
                            .setBold()
                            .setBorder(Border.NO_BORDER)
                            .setBackgroundColor(ColorConstants.DARK_GRAY)
                            .setFontColor(ColorConstants.WHITE)
                            .setHeight(toPoints(0.8f))
                            .setFontSize(10f)
            );

            itemTableTemplate.addCell(new Cell().add(
                    new Paragraph("Amount")
                    ).setTextAlignment(TextAlignment.CENTER)
                            .setVerticalAlignment(VerticalAlignment.MIDDLE)
                            .setBold()
                            .setBorder(Border.NO_BORDER)
                            .setBackgroundColor(ColorConstants.DARK_GRAY)
                            .setFontColor(ColorConstants.WHITE)
                            .setHeight(toPoints(0.8f))
                            .setFontSize(10f)
            );

            if(n%15 == 1)
            {
                itemTableTemplate.addCell(new Cell().add(
                        new Paragraph("\t\t" + items[i].getDescription())
                        ).setTextAlignment(TextAlignment.LEFT)
                                .setBorder(Border.NO_BORDER)
                                .setHeight(toPoints(0.8f))
                                .setFontSize(10f)
                );

                itemTableTemplate.addCell(new Cell().add(
                        new Paragraph(String.format("%s%.2f",currency, items[i].getPrice()))
                        ).setTextAlignment(TextAlignment.RIGHT)
                                .setBorder(Border.NO_BORDER)
                                .setHeight(toPoints(0.8f))
                                .setFontSize(10f)
                );

                itemTableTemplate.addCell(new Cell().add(
                        new Paragraph(String.format("%d", items[i].getQuantity()))
                        ).setTextAlignment(TextAlignment.CENTER)
                                .setBorder(Border.NO_BORDER)
                                .setHeight(toPoints(0.8f))
                                .setFontSize(10f)
                );

                itemTableTemplate.addCell(new Cell().add(
                        new Paragraph(String.format("%s%.2f",currency, items[i].getTotal()))
                        ).setTextAlignment(TextAlignment.RIGHT)
                                .setBorder(Border.NO_BORDER)
                                .setHeight(toPoints(0.8f))
                                .setFontSize(10f)
                );
            }
            else {

                itemTableTemplate.addCell(new Cell().add(
                        new Paragraph("\t" + items[i].getDescription())
                        ).setTextAlignment(TextAlignment.LEFT)
                                .setBorder(Border.NO_BORDER)
                                .setHeight(toPoints(0.8f))
                                .setFontSize(10f)
                );

                itemTableTemplate.addCell(new Cell().add(
                        new Paragraph(String.format("%s%.2f",currency, items[i].getPrice()))
                        ).setTextAlignment(TextAlignment.RIGHT)
                                .setBorder(Border.NO_BORDER)
                                .setHeight(toPoints(0.8f))
                                .setFontSize(10f)
                );

                itemTableTemplate.addCell(new Cell().add(
                        new Paragraph(String.format("%d", items[i].getQuantity()))
                        ).setTextAlignment(TextAlignment.CENTER)
                                .setBorder(Border.NO_BORDER)
                                .setHeight(toPoints(0.8f))
                                .setFontSize(10f)
                );

                itemTableTemplate.addCell(new Cell().add(
                        new Paragraph(String.format("%s%.2f",currency, items[i].getTotal()))
                        ).setTextAlignment(TextAlignment.RIGHT)
                                .setBorder(Border.NO_BORDER)
                                .setHeight(toPoints(0.8f))
                                .setFontSize(10f)
                );
            }
            i++;

            while(i%15 != 0 && i < n)
            {
                //System.out.print("\t\t" + i + ": " + n);
                if(i%15 == 14 || i == n-1)
                {
                    //System.out.print("Case 1 \n");
                    itemTableTemplate.addCell(new Cell().add(
                            new Paragraph("\t" + items[i].getDescription())
                            ).setTextAlignment(TextAlignment.LEFT)
                                    .setBorder(Border.NO_BORDER)
                                    .setHeight(toPoints(0.8f))
                                    .setFontSize(10f)
                    );

                    itemTableTemplate.addCell(new Cell().add(
                            new Paragraph(String.format("%s%.2f",currency, items[i].getPrice()))
                            ).setTextAlignment(TextAlignment.RIGHT)
                                    .setBorder(Border.NO_BORDER)
                                    .setHeight(toPoints(0.8f))
                                    .setFontSize(10f)
                    );

                    itemTableTemplate.addCell(new Cell().add(
                            new Paragraph(String.format("%d", items[i].getQuantity()))
                            ).setTextAlignment(TextAlignment.CENTER)
                                    .setBorder(Border.NO_BORDER)
                                    .setHeight(toPoints(0.8f))
                                    .setFontSize(10f)
                    );

                    itemTableTemplate.addCell(new Cell().add(
                            new Paragraph(String.format("%s%.2f",currency, items[i].getTotal()))
                            ).setTextAlignment(TextAlignment.RIGHT)
                                    .setBorder(Border.NO_BORDER)
                                    .setHeight(toPoints(0.8f))
                                    .setFontSize(10f)
                    );
                }
                else{
                    //System.out.print("Case 2 \n");
                    itemTableTemplate.addCell(new Cell().add(
                            new Paragraph("\t" + items[i].getDescription())
                            ).setTextAlignment(TextAlignment.LEFT)
                                    .setBorder(Border.NO_BORDER)
                                    .setHeight(toPoints(0.8f))
                                    .setFontSize(10f)
                    );

                    itemTableTemplate.addCell(new Cell().add(
                            new Paragraph(String.format("%s%.2f",currency, items[i].getPrice()))
                            ).setTextAlignment(TextAlignment.RIGHT)
                                    .setBorder(Border.NO_BORDER)
                                    .setHeight(toPoints(0.8f))
                                    .setFontSize(10f)
                    );

                    itemTableTemplate.addCell(new Cell().add(
                            new Paragraph(String.format("%d", items[i].getQuantity()))
                            ).setTextAlignment(TextAlignment.CENTER)
                                    .setBorder(Border.NO_BORDER)
                                    .setHeight(toPoints(0.8f))
                                    .setFontSize(10f)
                    );

                    itemTableTemplate.addCell(new Cell().add(
                            new Paragraph(String.format("%s%.2f",currency, items[i].getTotal()))
                            ).setTextAlignment(TextAlignment.RIGHT)
                                    .setBorder(Border.NO_BORDER)
                                    .setHeight(toPoints(0.8f))
                                    .setFontSize(10f)
                    );
                }
                i++;
            }
            tables.add(itemTableTemplate);

        }
        // Summary
        tables.lastElement().addCell(getCell("", 10f).setHeight(toPoints(1f)));
        tables.lastElement().addCell(getCell("", 10f).setHeight(toPoints(1f)));
        tables.lastElement().addCell(getCell("", 10f).setHeight(toPoints(1f)));
        tables.lastElement().addCell(getCell("", 10f).setHeight(toPoints(1f)));

        tables.lastElement().addCell(getCell("", 10f));
        tables.lastElement().addCell(getCell("", 10f));
        tables.lastElement().addCell(getCell("Sub-Total: ", 10f).setTextAlignment(TextAlignment.RIGHT));
        tables.lastElement().addCell(getCell(currency + " " + subTotal, 10f).setTextAlignment(TextAlignment.RIGHT));
        System.out.println(currency + " " + subTotal);

        tables.lastElement().addCell(getCell("", 10f));
        tables.lastElement().addCell(getCell("", 10f));
        tables.lastElement().addCell(getCell("Discount: ", 10f).setTextAlignment(TextAlignment.RIGHT));
        tables.lastElement().addCell(getCell(currency + " " + discount, 10f).setTextAlignment(TextAlignment.RIGHT));

        tables.lastElement().addCell(getCell("", 10f));
        tables.lastElement().addCell(getCell("", 10f));
        tables.lastElement().addCell(getCell("Total: ", 10f).setTextAlignment(TextAlignment.RIGHT));
        tables.lastElement().addCell(getCell(currency + " " + total, 10f).setTextAlignment(TextAlignment.RIGHT));

        return tables;
    }

    public IEventHandler getHeaderEvent()
    {
        return new TemplateA.HeaderEvent(getHeader(), getDetails());
    }

    public IEventHandler getFooterEvent()
    {
        return new TemplateA.FooterEvent(getFooter());
    }

    private class HeaderEvent implements IEventHandler{
        private final Table table1;
        private final Table table2;

        public HeaderEvent(Table table1, Table table2) {
            this.table1 = table1;
            this.table2 = table2;
        }

        @Override
        public void handleEvent(Event event)
        {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

            new Canvas(canvas, new Rectangle(
                    toPoints(1.27f),
                    page.getPageSize().getHeight() - toPoints(8f) - toPoints(2.5f),
                    page.getPageSize().getWidth() - 72,
                    toPoints(8f) + toPoints(1.27f)))
                    .add(table1)
                    .add(table2)
                    .close();
        }
    }

    private class FooterEvent implements IEventHandler{
        private final Table table;

        public FooterEvent(Table table) {
            this.table = table;
        }

        @Override
        public void handleEvent(Event event)
        {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

            new Canvas(canvas, new Rectangle(
                    toPoints(1.27f),
                    toPoints(1.27f) ,
                    page.getPageSize().getWidth() - 72,
                    toPoints(2.1f) + toPoints(1.27f)))
                    .add(table)
                    .close();

        }
    }

}

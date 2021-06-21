package org.invoice.monkey.utils.invoicegenerator.background;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;

import java.net.MalformedURLException;
import java.util.Objects;

public class Background implements IEventHandler {

    private BackgroundType backgroundType;

    public Background(BackgroundType backgroundType)
    {
        this.backgroundType = backgroundType;
    }

    public void handleEvent(Event event)
    {

            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();

            PdfCanvas pdfCanvas = new PdfCanvas(
                    page.newContentStreamBefore(), page.getResources(), pdfDoc
            );

            Canvas canvas = new Canvas(pdfCanvas, pdfDoc, page.getPageSize());
            canvas.add(new Image(ImageDataFactory.create(Objects.requireNonNull(getClass().getResource(backgroundType.getType())))));
            pdfCanvas.release();

    }
}

package org.invoice.monkey.utils.invoicegenerator;

import org.invoice.monkey.model.Invoice;

public class InvoiceGeneratorThread extends Thread{

    private InvoiceGenerator invoiceGenerator;
    private String invoiceLocation;

    public InvoiceGeneratorThread(InvoiceGenerator invoiceGenerator)
    {
        this.invoiceGenerator = invoiceGenerator;
    }

    public void run()
    {
        invoiceGenerator.generate();
    }


}

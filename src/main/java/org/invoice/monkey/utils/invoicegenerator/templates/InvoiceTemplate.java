package org.invoice.monkey.utils.invoicegenerator.templates;

import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.layout.element.Table;
import org.invoice.monkey.model.Configurations.OrgDetails;
import org.invoice.monkey.model.Customer;
import org.invoice.monkey.model.InvoiceItem;

import java.net.MalformedURLException;
import java.util.Set;
import java.util.Vector;

public interface InvoiceTemplate {

    default Float toPoints(Float cm)
    {
        return cm * 28.346f;
    }

    Table getHeader();

    Table getFooter();

    Vector<Table> getItemTables(Vector<InvoiceItem> itemList);

    IEventHandler getBackgroundEventHandler();

    IEventHandler getFooterEvent();

    IEventHandler getHeaderEvent();
}

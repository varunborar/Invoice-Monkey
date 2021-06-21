package org.invoice.monkey.model.Configurations;

import org.invoice.monkey.utils.CurrencyType;
import org.invoice.monkey.utils.invoicegenerator.background.BackgroundType;
import org.invoice.monkey.utils.invoicegenerator.templates.TemplateType;
import org.json.simple.JSONObject;

public class InvoiceDetails {

    private String template;
    private String background;
    private String currency;

    public InvoiceDetails(JSONObject invoiceDetails)
    {
        this.template = (String) invoiceDetails.get("template");
        this.background = (String) invoiceDetails.get("background");
        this.currency = (String) invoiceDetails.get("currency");
    }

    public void setTemplate(TemplateType template)
    {
        this.template = template.toString();
    }

    public void setBackground(BackgroundType background)
    {
        this.background = background.toString();
    }

    public void setCurrency(CurrencyType currency)
    {
        this.currency = currency.toString();
    }

    public TemplateType getTemplate()
    {
        return TemplateType.valueOf(template);
    }

    public BackgroundType getBackground()
    {
        return BackgroundType.valueOf(background);
    }

    public CurrencyType getCurrency()
    {
        return CurrencyType.valueOf(currency);
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSONObject()
    {
        JSONObject obj = new JSONObject();

        obj.put("template", template);
        obj.put("background", background);
        obj.put("currency", currency);

        return obj;
    }


}

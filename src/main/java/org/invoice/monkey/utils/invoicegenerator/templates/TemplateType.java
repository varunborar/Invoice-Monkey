package org.invoice.monkey.utils.invoicegenerator.templates;


import org.invoice.monkey.utils.invoicegenerator.background.Background;
import org.invoice.monkey.utils.invoicegenerator.background.BackgroundType;

public enum TemplateType {

    DefaultTemplate("DefaultTemplate.png"),
    TemplateA("TemplateA.png"),
    TemplateB("TemplateB.png");

    private String Type;

    TemplateType(String Type)
    {
        this.Type = Type;
    }

    public String getType()
    {
        return this.Type;
    }

    public String getURL()
    {
        return TemplateType.class.getResource(this.Type).toString();
    }

    public static TemplateType getByURL(String URL)
    {
        for(TemplateType t: TemplateType.values())
        {
            if(t.getURL().equals(URL))
                return t;
        }
        return null;
    }

}

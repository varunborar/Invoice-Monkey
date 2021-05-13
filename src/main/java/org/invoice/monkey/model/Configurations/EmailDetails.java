package org.invoice.monkey.model.Configurations;

import org.json.simple.JSONObject;

public class EmailDetails{
    private boolean isEmailServiceReady;
    private String senderEmail;
    private JSONObject email;

    public EmailDetails(JSONObject email)
    {
        this.isEmailServiceReady = (boolean) email.get("is-email-service-ready");

        if(isEmailServiceReady())
        {
            // Add code for reading email configuration
        }
    }
    public boolean isEmailServiceReady()
    {
        return this.isEmailServiceReady;
    }


    @SuppressWarnings("unchecked")
    public JSONObject getJSONObject()
    {
        JSONObject obj = new JSONObject();

        obj.put("is-email-service-ready", this.isEmailServiceReady);
        obj.put("email", this.email);

        return obj;
    }
}

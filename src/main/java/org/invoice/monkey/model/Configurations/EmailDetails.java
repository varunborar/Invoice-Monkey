package org.invoice.monkey.model.Configurations;

import org.invoice.monkey.App;
import org.invoice.monkey.utils.File;
import org.json.simple.JSONObject;

public class EmailDetails {
    private boolean isEmailServiceReady;
    private boolean customSet;


    private String subject;
    private String message;
    private Boolean link;

    private String host;
    private String port;
    private String email;
    private String password;
    private Boolean ssl;
    private Boolean authentication;


    public EmailDetails(JSONObject Email) {
        this.isEmailServiceReady = (boolean) Email.get("is-email-service-ready");

        if (isEmailServiceReady()) {
            JSONObject email = (JSONObject) Email.get("email");
            this.subject = (String) email.get("subject");
            this.message = (String) email.get("message");
            this.link = (Boolean) email.get("link");
        }

        this.customSet = (Boolean) Email.get("custom");
        if(isCustomSet())
        {
            JSONObject advanced = (JSONObject) Email.get("advanced");

            this.host = (String) advanced.get("host");
            this.port = (String) advanced.get("port");
            this.ssl = (Boolean) advanced.get("ssl");
            this.authentication = (Boolean) advanced.get("authentication");

            if(getAuthentication())
            {
                this.email = (String) advanced.get("email");
                this.password = (String) advanced.get("password");
            }

        }
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLink(Boolean include)
    {
        this.link = include;
    }

    public void setHost(String Host)
    {
        this.host = Host;
    }

    public void setPort(String Port)
    {
        this.port = Port;
    }

    public void setAuthentication(Boolean authentication, String email, String password)
    {
        this.authentication = authentication;
        if(authentication)
        {
            this.email = File.encryptString64(email);
            this.password = File.encryptString64(password);
        }
        else{
            this.email = null;
            this.password = null;
        }
    }

    public void setSSL(Boolean set)
    {
        this.ssl = set;
    }


    public void setCustom(Boolean set)
    {
        this.customSet = set;
    }

    public void setEmailServiceReady(Boolean set)
    {
        this.isEmailServiceReady = set;
        if(set)
        {
            String orgName =  App.getConfiguration().getOrgDetails().getOrgName();
            String contact = App.getConfiguration().getOrgDetails().getOrgEmail() + "\n"
                    + App.getConfiguration().getOrgDetails().getOrgNumber() + "\n"
                    + App.getConfiguration().getOrgDetails().getOrgSignatory();
            this.subject = "Invoice from " + orgName;
            this.message = "Dear Customer, \n\n\nPlease find attached invoice from" +
                    " your recent purchase with " + orgName + ". \n\nThank you for shopping with us. \nFor more information:\n" + contact;
            this.link = true;
        }
    }

    public String getSubject()
    {
        return this.subject;
    }

    public String getMessage()
    {
        return this.message;
    }

    public String getHost()
    {
        return this.host;
    }

    public String getPort()
    {
        return this.port;
    }

    public String getEmail()
    {
        return File.decryptString64(this.email);
    }

    public String getPassword()
    {
        return File.decryptString64(this.password);
    }

    public Boolean getSSL()
    {
        return this.ssl;
    }

    public Boolean getLink()
    {
        return this.link;
    }

    public Boolean getAuthentication()
    {
        return this.authentication;
    }

    public boolean isEmailServiceReady()
    {
        return this.isEmailServiceReady;
    }

    public Boolean isCustomSet()
    {
        return this.customSet;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSONObject()
    {
        JSONObject email = new JSONObject();

        email.put("subject", this.subject);
        email.put("message", this.message);
        email.put("link", this.link);

        JSONObject advanced = new JSONObject();

        advanced.put("host", this.host);
        advanced.put("port", this.port);
        advanced.put("ssl", this.ssl);
        advanced.put("authentication", this.authentication);
        advanced.put("email", this.email);
        advanced.put("password", this.password);

        JSONObject obj = new JSONObject();

        obj.put("is-email-service-ready", this.isEmailServiceReady);
        obj.put("email", email);
        obj.put("custom", this.customSet);
        obj.put("advanced", advanced);


        return obj;
    }
}

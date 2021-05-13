package org.invoice.monkey.model.Configurations;

import org.json.simple.JSONObject;

public class OrgDetails{

    private String orgName;
    private String orgNumber;
    private String orgEmail;
    private String orgSignatory;

    public OrgDetails(JSONObject orgDetails)
    {
        this.orgName = (String) orgDetails.get("Org-Name");
        this.orgNumber = (String) orgDetails.get("Org-Number");
        this.orgEmail = (String) orgDetails.get("Org-Email");
        this.orgSignatory = (String) orgDetails.get("Org-Signatory");
    }

    public String getOrgName()
    {
        return this.orgName;
    }

    public String getOrgNumber()
    {
        return this.orgNumber;
    }

    public String getOrgEmail()
    {
        return this.orgEmail;
    }

    public String getOrgSignatory()
    {
        return this.orgSignatory;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

    public void setOrgNumber(String orgNumber)
    {
        this.orgNumber = orgNumber;
    }

    public void setOrgEmail(String orgEmail)
    {
        this.orgEmail = orgEmail;
    }

    public void setOrgSignatory(String orgSignatory)
    {
        this.orgSignatory = orgSignatory;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSONObject()
    {
        JSONObject obj = new JSONObject();

        obj.put("Org-Number", this.orgNumber);
        obj.put("Org-Name", this.orgName);
        obj.put("Org-Signatory", this.orgSignatory);
        obj.put("Org-Email", this.orgEmail);

        return obj;
    }
}


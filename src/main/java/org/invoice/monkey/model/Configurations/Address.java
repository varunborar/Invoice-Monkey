package org.invoice.monkey.model.Configurations;

import org.json.simple.JSONObject;

public class Address{
    private String orgAddress;
    private String orgCity;
    private String orgState;
    private String orgPostalCode;

    public Address(JSONObject address)
    {
        this.orgAddress = (String) address.get("Building");
        this.orgCity = (String) address.get("City");
        this.orgState = (String) address.get("State");
        this.orgPostalCode = (String) address.get("Postal-Code");
    }

    public void setOrgAddress(String orgAddress)
    {
        this.orgAddress = orgAddress;
    }

    public void setOrgCity(String orgCity)
    {
        this.orgCity = orgCity;
    }

    public void setOrgState(String orgState)
    {
        this.orgState = orgState;
    }

    public void setOrgPostalCode(String orgPostalCode)
    {
        this.orgPostalCode = orgPostalCode;
    }



    public String getOrgAddress()
    {
        return this.orgAddress;
    }

    public String getOrgCity()
    {
        return this.orgCity;
    }

    public String getOrgState()
    {
        return this.orgState;
    }

    public String getOrgPostalCode()
    {
        return this.orgPostalCode;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSONObject()
    {
        JSONObject obj = new JSONObject();

        obj.put("Building", this.orgAddress);
        obj.put("State", this.orgState);
        obj.put("City", this.orgCity);
        obj.put("Postal-Code", this.orgPostalCode);

        return obj;
    }

}


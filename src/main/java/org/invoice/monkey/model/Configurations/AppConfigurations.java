package org.invoice.monkey.model.Configurations;


import org.json.simple.JSONObject;

public class AppConfigurations{
    private String defaultLocation;
    private String logoLocation;

    AppConfigurations(JSONObject appConfig)
    {
        this.defaultLocation = (String) appConfig.get("default-folder");
        this.logoLocation = "org.data/logo.png";
    }

    public String getDefaultLocation()
    {
        return this.defaultLocation;
    }

    public String getLogoLocation()
    {
        return this.logoLocation;
    }

    public void setDefaultLocation(String defaultLocation)
    {
        this.defaultLocation = defaultLocation;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSONObject()
    {
        JSONObject obj = new JSONObject();

        obj.put("default-folder", this.defaultLocation);
        obj.put("Logo", this.logoLocation);
        return obj;
    }
}

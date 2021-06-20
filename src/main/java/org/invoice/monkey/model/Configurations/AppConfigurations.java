package org.invoice.monkey.model.Configurations;


import org.json.simple.JSONObject;

public class AppConfigurations{
    private String defaultLocation;
    private String logoLocation;
    private String appTheme;

    AppConfigurations(JSONObject appConfig)
    {
        this.defaultLocation = (String) appConfig.get("default-folder");
        this.logoLocation = "org.data/logo.png";
        this.appTheme = (String) appConfig.get("Theme");
    }

    public String getDefaultLocation()
    {
        return this.defaultLocation;
    }

    public String getLogoLocation()
    {
        return this.logoLocation;
    }

    public String getAppTheme()
    {
        return this.appTheme;
    }

    public void setAppTheme(String theme)
    {
        this.appTheme = theme;
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
        obj.put("Theme", this.appTheme);
        return obj;
    }
}

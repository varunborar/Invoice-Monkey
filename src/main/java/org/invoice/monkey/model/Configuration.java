package org.invoice.monkey.model;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Configuration {

    // Org Details
    private String orgName;
    private String orgNumber;
    private String orgEmail;
    private String orgSignatory;

    //Address Details
    private String orgAddress;
    private String orgCity;
    private String orgState;
    private String orgPostalCode;

    // Configuration details
    private String defaultLocation;
    private String logoLocation;

    // Database details
    private boolean isCustomDatabaseSet;
    private String localDatabasePath;

    // Email details
    private boolean isEmailServiceReady;
    private String senderEmail;

    public Configuration()
    {
        // Reading appConfig.json File to get details

        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader("org.data/appConfig.json"));

            JSONObject config = (JSONObject) obj;

            //ADDRESS
            JSONObject address = (JSONObject) config.get("Address");
            this.orgAddress = (String) address.get("Building");
            this.orgCity = (String) address.get("City");
            this.orgState = (String) address.get("State");
            this.orgPostalCode = (String) address.get("Postal-Code");

            //ORG DETAILS
            JSONObject orgDetails = (JSONObject) config.get("Org-Details");
            this.orgName = (String) orgDetails.get("Org-Name");
            this.orgNumber = (String) orgDetails.get("Org-Number");
            this.orgEmail = (String) orgDetails.get("Org-Email");
            this.orgSignatory = (String) orgDetails.get("Org-Signatory");

            //APP CONFIGURATION
            JSONObject appConfig = (JSONObject) config.get("App-Configurations");
            this.defaultLocation = (String) appConfig.get("default-folder");
            this.logoLocation = "org.data/logo.png";


            //DATABASE
            JSONObject database = (JSONObject) config.get("Database");
            this.isCustomDatabaseSet = (boolean) database.get("is-custom-database-set");


            if(!isCustomDatabaseSet()) {
                this.localDatabasePath = (String) database.get("local-database-path");
            }else{
                //Add code for custom database fields retrieval
            }


            //EMAIL
            JSONObject email = (JSONObject) config.get("Email");
            this.isEmailServiceReady = (boolean) email.get("is-email-service-ready");

            if(isEmailServiceReady())
            {
                // Add code for reading email configuration
            }



            //Test Code
            System.out.println("Org Details" + orgName + ", " + orgNumber + ", " + orgEmail + ", " + orgSignatory);
            System.out.println("Address" + orgAddress + ", " + orgCity + ", " + orgState + ", " + orgPostalCode);
            System.out.println("App Configuration" + defaultLocation + ", " + logoLocation);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    // GETTERS

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

    public String getDefaultLocation()
    {
        return this.defaultLocation;
    }

    public String getLogoLocation()
    {
        return this.logoLocation;
    }

    public boolean isCustomDatabaseSet()
    {
        return this.isCustomDatabaseSet;
    }

    public boolean isEmailServiceReady()
    {
        return this.isEmailServiceReady;
    }

    public String getLocalDatabasePath()
    {
        return this.localDatabasePath;
    }



    // SETTERS

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

    public void setDefaultLocation(String defaultLocation)
    {
        this.defaultLocation = defaultLocation;
    }




}

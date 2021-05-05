package org.invoice.monkey.model;

import org.json.simple.JSONArray;
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
    private boolean customDatabase;
    private String pathToDataBase;

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

            JSONObject address = (JSONObject) config.get("Address");
            this.orgAddress = (String) address.get("Building");
            this.orgCity = (String) address.get("City");
            this.orgState = (String) address.get("State");
            this.orgPostalCode = (String) address.get("Postal-Code");

            JSONObject orgDetails = (JSONObject) config.get("Org-Details");
            this.orgName = (String) orgDetails.get("Org-Name");
            this.orgNumber = (String) orgDetails.get("Org-Number");
            this.orgEmail = (String) orgDetails.get("Org-Email");
            this.orgSignatory = (String) orgDetails.get("Org-Signatory");


            JSONObject appConfig = (JSONObject) config.get("App-Configurations");
            this.defaultLocation = (String) appConfig.get("default-folder");
            this.logoLocation = "org.data/logo.png";

            //Test Code
            System.out.println("Org Details" + orgName + ", " + orgNumber + ", " + orgEmail + ", " + orgSignatory);
            System.out.println("Address" + orgAddress + ", " + orgCity + ", " + orgState + ", " + orgPostalCode);
            System.out.println("App Configuration" + defaultLocation + ", " + logoLocation);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void configure()
    {

    }

}

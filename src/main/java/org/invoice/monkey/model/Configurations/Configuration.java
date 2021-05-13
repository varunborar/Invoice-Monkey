package org.invoice.monkey.model.Configurations;


import org.invoice.monkey.App;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Configuration {

    // Org Details
    OrgDetails orgDetails;

    //Address Details
    Address address;

    // Configuration details
    AppConfigurations appConfigurations;

    // Database details
    DatabaseDetails databaseDetails;

    // Email details
    EmailDetails emailDetails;

    public Configuration()
    {
        // Reading appConfig.json File to get details

        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader("org.data/appConfig.json"));

            JSONObject config = (JSONObject) obj;

            //ADDRESS
            JSONObject addressObject = (JSONObject) config.get("Address");
            this.address = new Address(addressObject);

            //ORG DETAILS
            JSONObject orgDetails = (JSONObject) config.get("Org-Details");
            this.orgDetails = new OrgDetails(orgDetails);

            //APP CONFIGURATION
            JSONObject appConfig = (JSONObject) config.get("App-Configurations");
            appConfigurations = new AppConfigurations(appConfig);

            //DATABASE
            JSONObject database = (JSONObject) config.get("Database");
            databaseDetails = new DatabaseDetails(database);

            //EMAIL
            JSONObject email = (JSONObject) config.get("Email");
            emailDetails = new EmailDetails(email);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    // GETTERS

    public OrgDetails getOrgDetails()
    {
        return this.orgDetails;
    }

    public Address getAddress()
    {
        return this.address;
    }

    public EmailDetails getEmailDetails()
    {
        return this.emailDetails;
    }

    public DatabaseDetails getDatabaseDetails()
    {
        return this.databaseDetails;
    }

    public AppConfigurations getAppConfigurations()
    {
        return this.appConfigurations;
    }

    @SuppressWarnings("unchecked")
    public void refresh()
    {
        JSONObject obj = new JSONObject();

        obj.put("Address", address.getJSONObject());
        obj.put("Email", emailDetails.getJSONObject());
        obj.put("Database", databaseDetails.getJSONObject());
        obj.put("App-Configurations", appConfigurations.getJSONObject());
        obj.put("Org-Details", orgDetails.getJSONObject());

        try {
            File appConfigFile = new File("org.data\\appConfig.json");
            appConfigFile.delete();
            FileWriter fw = new FileWriter(appConfigFile);
            fw.write(obj.toJSONString());
            fw.flush();
            App.refreshConfiguration();
        }catch(Exception e)
        {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }
}







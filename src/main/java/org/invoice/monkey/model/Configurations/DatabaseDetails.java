package org.invoice.monkey.model.Configurations;

import org.invoice.monkey.utils.File;
import org.json.simple.JSONObject;

public class DatabaseDetails{
    private boolean isCustomDatabaseSet;
    private String localDatabasePath;
    private JSONObject customDatabaseDetails;

    //CUSTOM DATABASE DETAILS (MYSQL)
    private String host;
    private Integer port;
    private String databaseName;
    private String userName;
    private String password;

    public DatabaseDetails(JSONObject database)
    {
        this.isCustomDatabaseSet = (boolean) database.get("is-custom-database-set");
        this.localDatabasePath = (String) database.get("local-database-path");

        if (isCustomDatabaseSet()){
             customDatabaseDetails = (JSONObject) database.get("custom-database");
             this.host = (String) customDatabaseDetails.get("host");
             this.port = (Integer) customDatabaseDetails.get("port");
             this.databaseName = (String) customDatabaseDetails.get("database-name");
             this.userName = (String) customDatabaseDetails.get("user-name");
             this.password = (String) customDatabaseDetails.get("password");
        }
    }

    public boolean isCustomDatabaseSet()
    {
        return this.isCustomDatabaseSet;
    }
    public String getLocalDatabasePath()
    {
        return this.localDatabasePath;
    }

    public String getFormattedURL()
    {
        return String.format("jdbc:mysql//%s:%d/%s",this.host, this.port, this.databaseName);
    }

    public String getHost()
    {
        return this.host;
    }

    public String getDatabaseName()
    {
        return this.databaseName;
    }

    public String getPort()
    {
        return this.port.toString();
    }

    public String getUserName()
    {
        return File.decryptString64(this.userName);
    }

    public String getPassword()
    {
        return File.decryptString64(this.password);
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSONObject()
    {
        JSONObject customDB = new JSONObject();
        customDB.put("host", this.host);
        customDB.put("port", this.port);
        customDB.put("database-name", this.databaseName);
        customDB.put("user-name", this.userName);
        customDB.put("password", this.password);

        JSONObject obj = new JSONObject();
        obj.put("is-custom-database-set", isCustomDatabaseSet);
        obj.put("local-database-path", localDatabasePath);
        obj.put("custom-database", customDB);

        return obj;
    }

    public void setCustomDatabase(String host, Integer port, String databaseName, String userName, String password)
    {
        this.host = host;
        this.port = port;
        this.databaseName = databaseName;
        this.userName = File.encryptString64(userName);
        this.password = File.encryptString64(password);

        this.isCustomDatabaseSet = true;
    }

    public void setCustomDatabase(Boolean set)
    {
        isCustomDatabaseSet = set;
    }
}

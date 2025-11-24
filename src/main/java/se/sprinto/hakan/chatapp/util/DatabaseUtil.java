package se.sprinto.hakan.chatapp.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
    private final Properties properties;
    private static DatabaseUtil instance;

    private DatabaseUtil(){
        properties = new Properties();

        try(InputStream inputStream =
                    ClassLoader.getSystemResourceAsStream("application.properties")){
            properties.load(inputStream);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static DatabaseUtil getInstance(){
        if(instance == null){
            instance = new DatabaseUtil();
        }
        return instance;
    }
    public String getProperty (String key){
        return properties.getProperty(key);
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getProperty("db.url"),getProperty("db.username"), getProperty("db.password"));

    }
}

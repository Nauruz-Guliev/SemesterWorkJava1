package ru.kpfu.itis.gnt.DAO.datasource;

import org.postgresql.jdbc2.optional.SimpleDataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class DataSource{
    private static final String driverClassName = "org.postgresql.Driver";

    public DriverManagerDataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties properties = new Properties();

        try (InputStream in = Files.newInputStream(Paths.get("C:\\Windows.old\\Users\\nauru\\Desktop\\java\\java3-examples\\hwServlets\\src\\main\\properties\\db.properties"))) {
            properties.load(in);
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(properties.getProperty("db.url"));
            dataSource.setUsername(properties.getProperty("db.username"));
            dataSource.setPassword(properties.getProperty("db.password"));
            return dataSource;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    private static Connection connection;
    public Connection getPostgresDbConnection() throws DBException {
        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(Paths.get("C:\\Windows.old\\Users\\nauru\\Desktop\\java\\java3-examples\\hwServlets\\src\\main\\properties\\db.properties")));
            Class.forName("org.postgresql.Driver");
            if (connection == null) {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(properties.getProperty("db.url"),
                        properties.getProperty("db.username"),
                        properties.getProperty("db.password"));
            }
        } catch (ClassNotFoundException ex) {
            throw new DBException("Couldn't find DataBase driver.");
        } catch (SQLException ex) {
            throw new DBException("Couldn't connect to DataBase due to " + ex.getMessage());
        } catch (IOException e) {
            throw new DBException("Couldn't open DataBase file. ");
        }
        return connection;
    }

     */


}

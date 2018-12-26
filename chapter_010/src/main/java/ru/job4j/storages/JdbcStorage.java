package ru.job4j.storages;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Aleksandr Belov (whiterabbit.nsk@gmail.com)
 * @since 26.12.2018
 */
@Component
public class JdbcStorage implements Storage {
    private BasicDataSource source;
    private String path;

    @Autowired
    public JdbcStorage(final String path) {
        this.path = path;
        try {
            initSource(getProperties());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(final User user) {
        String sql = "INSERT INTO users(name) VALUES(?);";
        try (Connection connection = this.source.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isEmpty() {
        boolean result = false;
        String sql = "SELECT EXISTS (SELECT * FROM users);";
        try (Connection connection = this.source.getConnection();
            ResultSet rs = connection.createStatement().executeQuery(sql)) {
            rs.next();
            result = rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void initSource(final Properties properties) {
        this.source = new BasicDataSource();
        this.source.setDriverClassName(properties.getProperty("jdbc.driver"));
        this.source.setUrl(properties.getProperty("jdbc.url"));
        this.source.setUsername(properties.getProperty("jdbc.username"));
        this.source.setPassword(properties.getProperty("jdbc.password"));
    }

    private Properties getProperties() throws IOException {
        Properties properties = new Properties();
        FileInputStream in = new FileInputStream(this.path);
        properties.load(in);
        in.close();
        return properties;
    }
}

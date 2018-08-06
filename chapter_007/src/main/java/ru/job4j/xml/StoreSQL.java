package ru.job4j.xml;

import java.sql.*;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 28.07.18
 */

public class StoreSQL {
    private final String config;
    private Connection conn = null;

    /**
     * Создает таблицу по заданному адресу (config).
     * @param config адрес
     */
    public StoreSQL(String config) {
        this.config = config;
        try {
            conn = DriverManager.getConnection(config);
            Statement st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS entry (field integer);");
            st.executeUpdate("DELETE FROM entry;");
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void generate(int n) {
        int count = 0;
        while (count < n) {
            try {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO entry(field) VALUES(?)");
                ps.setInt(1, count);
                ps.execute();
                count++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

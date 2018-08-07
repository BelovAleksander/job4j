package ru.job4j.xml;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 07.08.18
 */

public class StoreSQL {
    private final String url;
    private final String psInsert;
    private final String pullOut;

    public StoreSQL(final String url, final String create, final String delete, final String ps, final String pullOut) {
        this.url = url;
        this.psInsert = ps;
        this.pullOut = pullOut;
        try (Connection conn = DriverManager.getConnection(url);
             Statement st = conn.createStatement()) {
            st.executeUpdate(create);
            st.executeUpdate(delete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void generate(int n) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.url);
            conn.setAutoCommit(false);
            PreparedStatement ps = createPreparedStatement(conn, n);
            ps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    private PreparedStatement createPreparedStatement(Connection con, int n) throws SQLException {
        int count = 0;
        String sql = this.psInsert;
        PreparedStatement ps = con.prepareStatement(sql);
        while (count < n) {
            ps.setInt(1, count);
            ps.addBatch();
            count++;
        }
        return ps;
    }

    public List<StoreXML.Entry> getValues() throws SQLException {
        List<StoreXML.Entry> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(this.url);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(this.pullOut)) {

            while (rs.next()) {
                int x = rs.getInt("field");
                StoreXML.Entry entry = new StoreXML.Entry(x);
                System.out.println(x);
                list.add(entry);
            }
        }
        return list;
    }
}

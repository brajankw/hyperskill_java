package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class Database {
    private final String fileName;
    public Database(String fileName) {
        this.fileName = fileName;
        connectToDatabase();
    }
    private Connection connectToDatabase() {

        String url = "jdbc:sqlite:" + fileName;
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        Connection con = null;
        try {
            con = dataSource.getConnection();
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY," +
                        "number TEXT NOT NULL," +
                        "pin TEXT NOT NULL," +
                        "balance INTEGER DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public void insert(String cardId, String cardPin) {
        String sql = "INSERT INTO card(number, pin) VALUES(?,?)";

        try (Connection con = this.connectToDatabase();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, cardId);
            pstmt.setString(2, cardPin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(String cardId, int balance) {
        String sql = "UPDATE card SET balance = ? WHERE number = ?";


        try (Connection con = this.connectToDatabase();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, balance);
            pstmt.setString(2, cardId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(String cardId) {
        String sql = "DELETE FROM card WHERE number = ?";

        try (Connection con = this.connectToDatabase();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, cardId);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Card checkCredentials(String cardId, String cardPin) {
        String sql = "SELECT number, pin, balance FROM card";

        try (Connection con = this.connectToDatabase();
             Statement stmt  = con.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                if(rs.getString("number").equals(cardId) && rs.getString("pin").equals(cardPin)) {
                    return new Card(cardId, cardPin, rs.getInt("balance"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean checkIfExist(String cardId) {
        String sql = "SELECT number FROM card";

        try (Connection con = this.connectToDatabase();
             Statement stmt  = con.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                if(rs.getString("number").equals(cardId)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}


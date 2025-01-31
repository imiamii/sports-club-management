import java.sql.*;

public class SportsClubManagement {
    private static final String URL = "jdbc:postgresql://localhost:5432/sports_club";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to the database!");

            // Create tables
            createTables(conn);

            // Insert sample data
            insertPlayer(conn, "John Doe", 22, 1);
            insertPlayer(conn, "Mike Smith", 25, 2);

            // Read data
            System.out.println("All Players:");
            getAllPlayers(conn);

            // Update data
            updatePlayer(conn, 1, "John Updated");
            System.out.println("After Update:");
            getAllPlayers(conn);

            // Delete data
            deletePlayer(conn, 1);
            System.out.println("After Deletion:");
            getAllPlayers(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables(Connection conn) throws SQLException {
        String sportTable = "CREATE TABLE IF NOT EXISTS Sport (id SERIAL PRIMARY KEY, name VARCHAR(100) NOT NULL);";
        String playerTable = "CREATE TABLE IF NOT EXISTS Player (id SERIAL PRIMARY KEY, name VARCHAR(100) NOT NULL, age INTEGER NOT NULL, sport_id INTEGER REFERENCES Sport(id) ON DELETE CASCADE);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sportTable);
            stmt.execute(playerTable);
        }
    }

    private static void insertPlayer(Connection conn, String name, int age, int sportId) throws SQLException {
        String sql = "INSERT INTO Player (name, age, sport_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setInt(3, sportId);
            stmt.executeUpdate();
        }
    }

    private static void getAllPlayers(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Player";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Age: " + rs.getInt("age") + ", Sport ID: " + rs.getInt("sport_id"));
            }
        }
    }

    private static void updatePlayer(Connection conn, int playerId, String newName) throws SQLException {
        String sql = "UPDATE Player SET name = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setInt(2, playerId);
            stmt.executeUpdate();
        }
    }

    private static void deletePlayer(Connection conn, int playerId) throws SQLException {
        String sql = "DELETE FROM Player WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            stmt.executeUpdate();
        }
    }
}

package control;

import utility.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnedObjectDao {

    public boolean isObjectOwned(int playerId, String objectName) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return false;
        String sql = "SELECT 1 FROM owned_object WHERE player_id = ? AND object_name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            stmt.setString(2, objectName);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true jika ditemukan
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try { conn.close(); } catch (Exception e) {}
        }
    }

    public void saveOwnedObject(int playerId, String objectName) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return;
        String sql = "INSERT IGNORE INTO owned_object (player_id, object_name) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            stmt.setString(2, objectName);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
        }
    }

    public void resetOwnedObjects(int playerId) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return;
        String sql = "DELETE FROM owned_object WHERE player_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            stmt.executeUpdate();

            System.out.println("Owned objects direset untuk player ID " + playerId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
        }
    }
}

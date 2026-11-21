package control;

import utility.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.WorldItem;

public class WorldItemDAO {

    public void collectWorldItem(int worldItemId, int playerId) {
    String sql = "UPDATE world_items SET is_collected_by_player_id = ? WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection()) {
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerId);
            pstmt.setInt(2, worldItemId);
            pstmt.executeUpdate();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public void resetWorldItemsForPlayer(int playerId) {
    String resetCollectedSql = "UPDATE world_items SET is_collected_by_player_id = NULL WHERE is_collected_by_player_id = ?";
    String resetDoorSql = "UPDATE world_items SET is_collected_by_player_id = NULL WHERE item_id = 9";

    try (Connection conn = DatabaseConnection.getConnection()) {
        conn.setAutoCommit(false); 

        try (
            PreparedStatement resetCollectedStmt = conn.prepareStatement(resetCollectedSql);
            PreparedStatement resetDoorStmt = conn.prepareStatement(resetDoorSql)
        ) {
            // Reset semua item 
            resetCollectedStmt.setInt(1, playerId);
            resetCollectedStmt.executeUpdate();
            resetDoorStmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback(); 
            e.printStackTrace();
        } finally {
            conn.setAutoCommit(true); 
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public List<WorldItem> loadAllWorldItemsForMap(int mapId) {
        List<WorldItem> worldItems = new ArrayList<>();
        String sql = "SELECT * FROM world_items WHERE map_id = ? AND is_collected_by_player_id IS NULL";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, mapId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                WorldItem itemModel = new WorldItem();
                itemModel.setId(rs.getInt("id"));
                itemModel.setItemId(rs.getInt("item_id"));
                itemModel.setWorldX(rs.getInt("world_x"));
                itemModel.setWorldY(rs.getInt("world_y"));
                itemModel.setMapId(rs.getInt("map_id"));

                itemModel.setIsCollectedByPlayerId((Integer)rs.getObject("is_collected_by_player_id"));
                worldItems.add(itemModel);
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan");
            e.printStackTrace();
        }
        return worldItems;
    }
}

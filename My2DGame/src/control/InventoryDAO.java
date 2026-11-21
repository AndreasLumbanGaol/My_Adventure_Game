package control;

import model.PlayerInventory;
import utility.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class InventoryDAO {


    public void saveInventory(List<PlayerInventory> inventoryList, int playerId) {
        String deleteSql = "DELETE FROM player_inventory WHERE player_id = ?";  
        String insertSql = "INSERT INTO player_inventory (id, player_id, item_id, quantity) VALUES (?, ?, ?, ?)";  

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.out.println("Koneksi database null");
                return;
            }

            conn.setAutoCommit(false);

            //Hapus inventaris 
            try (PreparedStatement delStmt = conn.prepareStatement(deleteSql)) {
                delStmt.setInt(1, playerId);
                delStmt.executeUpdate();
            }

            //Masukkan inventaris 
            try (PreparedStatement insStmt = conn.prepareStatement(insertSql)) {
                for (PlayerInventory itemModel : inventoryList) {
                    insStmt.setInt(1, itemModel.getId());
                    insStmt.setInt(2, itemModel.getPlayerId());
                    insStmt.setInt(3, itemModel.getItemId());
                    insStmt.setInt(4, itemModel.getQuantity());
                    insStmt.addBatch();
                }
                insStmt.executeBatch();
            }

            conn.commit();

        } catch (SQLException e) {
            System.out.println("Gagal menyimpan inventaris, rollback transaksi.");
            e.printStackTrace();
            // Jika error, batalkan semua 
            try {
                if (conn != null) {
                    conn.rollback();//Balik ke awal databasenya
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public List<PlayerInventory> loadInventory(int playerId) {
        List<PlayerInventory> inventoryList = new ArrayList<>();
        String sql = "SELECT * FROM player_inventory WHERE player_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, playerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                PlayerInventory itemModel = new PlayerInventory();
                itemModel.setId(rs.getInt("id"));
                itemModel.setPlayerId(rs.getInt("player_id"));
                itemModel.setItemId(rs.getInt("item_id"));
                itemModel.setQuantity(rs.getInt("quantity"));
                inventoryList.add(itemModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryList;
    }

    public void resetInventory(int playerId) {
        String sql = "DELETE FROM player_inventory WHERE player_id = ?"; // 
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            stmt.executeUpdate();
            System.out.println("Inventaris untuk player ID " + playerId + " telah direset.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
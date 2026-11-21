//Andreas, Kevin, Derrick, Seno
package control;

import model.Item;
import entity.Entity;
import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Shield;
import object.OBJ_Sword;
import object.OBJ_Key;
import utility.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import object.OBJ_Door;

public class OwnItemDao {

    public ArrayList<Item> getInventoryByPlayerId(int playerId) {
        ArrayList<Item> inventory = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return inventory;

        try {
            String sql = "SELECT item.* FROM ownitem JOIN item ON ownitem.item_id = item.id WHERE ownitem.player_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, playerId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setTipe(rs.getInt("tipe"));
                item.setNilaiAttack(rs.getInt("nilai_attack"));
                item.setNilaiDefense(rs.getInt("nilai_defense"));
                item.setDeskripsi(rs.getString("deskripsi"));
                inventory.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
        }

        return inventory;
    }

    public void saveInventory(int playerId, List<Item> items) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return;
        String deleteSql = "DELETE FROM ownitem WHERE player_id=?";
        String insertSql = "INSERT INTO ownitem (player_id, item_id) VALUES (?, ?)";
        String checkItemExistsSql = "SELECT 1 FROM item WHERE id = ?";

        try {
            // Hapus data inventory lama
            PreparedStatement delStmt = conn.prepareStatement(deleteSql);
            delStmt.setInt(1, playerId);
            delStmt.executeUpdate();

            // Prepare statement untuk insert dan cek keberadaan item
            PreparedStatement checkStmt = conn.prepareStatement(checkItemExistsSql);
            PreparedStatement insStmt = conn.prepareStatement(insertSql);

            for (Item item : items) {
                System.out.println("coba simpan item: " + item.getName() + " (ID: " + item.getId() + ")");

                if (item.getId() <= 0) {
                    System.out.println("Lewatkan item tanpa ID: " + item.getName());
                    continue;
                }

                // Cek apakah item.id ada di tabel item
                checkStmt.setInt(1, item.getId());
                ResultSet rs = checkStmt.executeQuery();
                if (!rs.next()) {
                    System.out.println("Item ID " + item.getId() + " (" + item.getName() + ") tidak ditemukan di tabel item. Lewatkan.");
                    continue;
                }

                insStmt.setInt(1, playerId);
                insStmt.setInt(2, item.getId());

                try {
                    insStmt.executeUpdate();
                    System.out.println("Item tersimpan: " + item.getName() + " (ID: " + item.getId() + ")");
                } catch (SQLException e) {
                    System.out.println("Gagal menyimpan item: " + item.getName() + " (ID: " + item.getId() + ")");
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal menyimpan inventory ke database.");
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
        }
    }

    public Item convertEntityToItem(Entity entity) {
        Item item = new Item();
        item.setId(entity.getId());
        item.setName(entity.name);
        item.setNilaiAttack(entity.nilaiAttack);
        item.setNilaiDefense(entity.nilaiDefense);
        item.setDeskripsi(entity.deskripsi);
        item.setTipe(entity.tipe);
        return item;
    }

    public Entity convertItemToEntity(Item item, GamePanel gp) {
        Entity entity = null;

        System.out.println("Konversi item: " + item.getName() + ", tipe=" + item.getTipe());

        switch (item.getTipe()) {
            case 3:
                entity = new OBJ_Sword(gp);
                break;
            case 4:
                entity = new OBJ_Axe(gp);
                break;
            case 5:
                entity = new OBJ_Shield(gp);
                break;
            case 6:
                if (item.getName().equalsIgnoreCase("Key")) {
                    entity = new OBJ_Key(gp);
                } else if (item.getName().equalsIgnoreCase("Door")) {
                    entity = new OBJ_Door(gp);
                } else {
                    System.out.println("Item consumable tidak dikenali: " + item.getName());
                }
                break;
            default:
                System.out.println("Tipe item tidak dikenali: " + item.getTipe() + " (" + item.getName() + ")");
                break;
        }

        if (entity != null) {
            entity.setId(item.getId());
            entity.name = item.getName();
            entity.nilaiAttack = item.getNilaiAttack();
            entity.nilaiDefense = item.getNilaiDefense();
            entity.tipe = item.getTipe();
            entity.deskripsi = item.getDeskripsi();
        }

        return entity;
    }

    public void resetInventoryByPlayerId(int playerId) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return;
        String sql = "DELETE FROM ownitem WHERE player_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            int rows = stmt.executeUpdate();
            System.out.println("Inventory dihapus untuk player ID " + playerId + ". Rows: " + rows);

        } catch (SQLException e) {
            System.out.println("Gagal reset inventory untuk New Game.");
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
        }
    }
}

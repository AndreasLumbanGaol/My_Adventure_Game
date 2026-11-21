package control;

import model.PlayerModel;
import utility.DatabaseConnection;
import java.sql.*;

public class PlayerDAO {

    public void savePlayer(PlayerModel playerModel) {
        String sql = "INSERT INTO players (id, player_name, level, max_health, current_health, max_arrow, current_arrow, " +
                     "strength, agility, experience, next_level_exp, coin, world_x, world_y, current_map, " +
                     "equipped_weapon_id, equipped_shield_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE " +
                     "player_name=VALUES(player_name), level=VALUES(level), max_health=VALUES(max_health), current_health=VALUES(current_health), " +
                     "max_arrow=VALUES(max_arrow), current_arrow=VALUES(current_arrow), strength=VALUES(strength), " +
                     "agility=VALUES(agility), experience=VALUES(experience), next_level_exp=VALUES(next_level_exp), " +
                     "coin=VALUES(coin), world_x=VALUES(world_x), world_y=VALUES(world_y), current_map=VALUES(current_map), " +
                     "equipped_weapon_id=VALUES(equipped_weapon_id), equipped_shield_id=VALUES(equipped_shield_id)";
//Update untuk id player sama
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, playerModel.getId());
            pstmt.setString(2, playerModel.getPlayerName());
            pstmt.setInt(3, playerModel.getLevel());
            pstmt.setInt(4, playerModel.getMaxHealth());
            pstmt.setInt(5, playerModel.getCurrentHealth());
            pstmt.setInt(6, playerModel.getMaxArrow());
            pstmt.setInt(7, playerModel.getCurrentArrow());
            pstmt.setInt(8, playerModel.getStrength());
            pstmt.setInt(9, playerModel.getAgility());
            pstmt.setInt(10, playerModel.getExperience());
            pstmt.setInt(11, playerModel.getNextLevelExp());
            pstmt.setInt(12, playerModel.getCoin());
            pstmt.setInt(13, playerModel.getWorldX());
            pstmt.setInt(14, playerModel.getWorldY());
            pstmt.setInt(15, playerModel.getCurrentMap());
            pstmt.setObject(16, playerModel.getEquippedWeaponId()); 
            pstmt.setObject(17, playerModel.getEquippedShieldId());//bisa null
            
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PlayerModel loadPlayer(int playerId) {
        String sql = "SELECT * FROM players WHERE id = ?";
        PlayerModel playerModel = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, playerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                playerModel = new PlayerModel();
                playerModel.setId(rs.getInt("id"));
                playerModel.setPlayerName(rs.getString("player_name"));
                playerModel.setLevel(rs.getInt("level"));
                playerModel.setMaxHealth(rs.getInt("max_health"));
                playerModel.setCurrentHealth(rs.getInt("current_health"));
                playerModel.setMaxArrow(rs.getInt("max_arrow"));
                playerModel.setCurrentArrow(rs.getInt("current_arrow"));
                playerModel.setStrength(rs.getInt("strength"));
                playerModel.setAgility(rs.getInt("agility"));
                playerModel.setExperience(rs.getInt("experience"));
                playerModel.setNextLevelExp(rs.getInt("next_level_exp"));
                playerModel.setCoin(rs.getInt("coin"));
                playerModel.setWorldX(rs.getInt("world_x"));
                playerModel.setWorldY(rs.getInt("world_y"));
                playerModel.setCurrentMap(rs.getInt("current_map"));
                playerModel.setEquippedWeaponId((Integer) rs.getObject("equipped_weapon_id"));
                playerModel.setEquippedShieldId((Integer) rs.getObject("equipped_shield_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerModel;
    }
}

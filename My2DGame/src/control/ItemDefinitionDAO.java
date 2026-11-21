package control;

import model.ItemDefinition;
import utility.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDefinitionDAO {


    public ItemDefinition findById(int id) {
        String sql = "SELECT * FROM item_definitions WHERE id = ?";
        ItemDefinition itemDef = null;

        try (Connection conn = DatabaseConnection.getConnection(); 
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                itemDef = new ItemDefinition();
                itemDef.setId(rs.getInt("id"));
                itemDef.setName(rs.getString("name"));
                itemDef.setType(rs.getString("type"));
                itemDef.setAttackValue(rs.getInt("attack_value"));
                itemDef.setDefenseValue(rs.getInt("defense_value"));
                itemDef.setDescription(rs.getString("description"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemDef;
    }
}

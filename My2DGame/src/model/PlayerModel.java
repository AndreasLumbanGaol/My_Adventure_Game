package model;

public class PlayerModel {

    private int id;
    private String playerName;
    private int level;
    private int maxHealth;
    private int currentHealth;
    private int maxArrow;
    private int currentArrow;
    private int strength;
    private int agility;
    private int experience;
    private int nextLevelExp;
    private int coin;
    private int worldX;
    private int worldY;
    private int currentMap;
    private Integer equippedWeaponId; 
    private Integer equippedShieldId; //bisa null

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getMaxArrow() {
        return maxArrow;
    }

    public void setMaxArrow(int maxArrow) {
        this.maxArrow = maxArrow;
    }

    public int getCurrentArrow() {
        return currentArrow;
    }

    public void setCurrentArrow(int currentArrow) {
        this.currentArrow = currentArrow;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getNextLevelExp() {
        return nextLevelExp;
    }

    public void setNextLevelExp(int nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(int currentMap) {
        this.currentMap = currentMap;
    }

    public Integer getEquippedWeaponId() {
        return equippedWeaponId;
    }

    public void setEquippedWeaponId(Integer equippedWeaponId) {
        this.equippedWeaponId = equippedWeaponId;
    }

    public Integer getEquippedShieldId() {
        return equippedShieldId;
    }

    public void setEquippedShieldId(Integer equippedShieldId) {
        this.equippedShieldId = equippedShieldId;
    }
}

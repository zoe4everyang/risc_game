package edu.duke.ece651.risk_game.shared;

public class UpgradeUnitRequest extends Message{

    Integer territoryID;
    Integer level;
    Integer levelUpgraded;
    Integer amount;
    public UpgradeUnitRequest(int playerID, int territoryID, int level, int levelUpgraded, int amount) {
        super(playerID);
        this.territoryID = territoryID;
        this.level = level;
        this.levelUpgraded = levelUpgraded;
        this.amount = amount;
    }

    public Integer getTerritoryID() {
        return territoryID;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getLevelUpgraded() {
        return levelUpgraded;
    }

    public Integer getAmount() {
        return amount;
    }
}

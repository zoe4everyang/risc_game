package edu.duke.ece651.risk_game.shared;

public class UpgradeUnitRequest extends Message{

    Integer territoryID;
    Integer level;
    Integer levelUpgraded;

    public UpgradeUnitRequest(int playerID, int territoryID, int level, int levelUpgraded) {
        super(playerID);
        this.territoryID = territoryID;
        this.level = level;
        this.levelUpgraded = levelUpgraded;
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
}

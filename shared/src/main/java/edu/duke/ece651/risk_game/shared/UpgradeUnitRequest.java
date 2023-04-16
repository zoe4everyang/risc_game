package edu.duke.ece651.risk_game.shared;

public class UpgradeUnitRequest extends Message{

    Integer territoryID;
    Integer unitID;
    Integer levelUpgraded;

    public UpgradeUnitRequest(int playerID, int territoryID, int unitID, int levelUpgraded) {
        super(playerID);
        this.territoryID = territoryID;
        this.unitID = unitID;
        this.levelUpgraded = levelUpgraded;
    }

    public Integer getTerritoryID() {
        return territoryID;
    }

    public Integer getUnitID() {
        return unitID;
    }

    public Integer getLevelUpgraded() {
        return levelUpgraded;
    }
}

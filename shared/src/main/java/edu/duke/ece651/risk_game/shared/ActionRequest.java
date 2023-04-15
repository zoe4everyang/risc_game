package edu.duke.ece651.risk_game.shared;

import java.util.List;

/**
 * This class is used to create a message that contains the information of the player's action.
 * The message contains the information of the player's action. The player can move units from one territory to another, and attack from one territory to another.
 * The player can also choose to not move or attack.
 */
public class ActionRequest extends Message {
    List<Integer> moveFrom;
    List<Integer> moveTo;
    List<Troop> moveTroop;
    List<Integer> attackFrom;
    List<Integer> attackTo;
    List<Troop> attackTroop;

    List<Integer> upgradeUnitTerritory;
    List<Integer> upgradeUnitId;
    List<Integer> upgradeUnitLevel;
    Boolean upgradeTech;

    /**
     * This constructor is used to create a message that contains the information of the player's action.
     * @param playerId the ID of the player
     * @param moveFrom  the list of territories that the player wants to move units from
     * @param moveTo the list of territories that the player wants to move units to
     * @param moveTroop the list of numbers of units that the player wants to move from each territory
     * @param attackFrom the list of territories that the player wants to attack from
     * @param attackTo the list of territories that the player wants to attack to
     * @param attackTroop  the list of numbers of units that the player wants to attack with from each territory
     * @param upgradeUnitTerritory the list of territories that the player wants to upgrade units
     * @param upgradeUnitId the list of units that the player wants to upgrade
     * @param upgradeUnitLevel the list of levels that the player wants to upgrade to
     * @param upgradeTech  the boolean value that indicates whether the player wants to upgrade tech
     */
    public ActionRequest(Integer playerId,
                         List<Integer> moveFrom, List<Integer> moveTo, List<Troop> moveTroop,
                         List<Integer> attackFrom, List<Integer> attackTo, List<Troop> attackTroop,
                         List<Integer> upgradeUnitTerritory, List<Integer> upgradeUnitId, List<Integer> upgradeUnitLevel, Boolean upgradeTech) {
        super(playerId);
        if (moveFrom.size() != moveTo.size()) // || moveFrom.size() != moveNums.size())
            throw new IllegalArgumentException("The size of moveFrom, moveTo, and moveNums should be the same.");
        if (attackFrom.size() != attackTo.size())
            throw new IllegalArgumentException("The size of attackFrom and attackTo should be the same.");
        this.moveFrom = moveFrom;
        this.moveTo = moveTo;
        this.moveTroop = moveTroop;
        this.attackFrom = attackFrom;
        this.attackTo = attackTo;
        this.attackTroop = attackTroop;
        this.upgradeUnitTerritory = upgradeUnitTerritory;
        this.upgradeUnitId = upgradeUnitId;
        this.upgradeUnitLevel = upgradeUnitLevel;
        this.upgradeTech = upgradeTech;
    }

    // This method is used to get the list of territories that the player wants to move units from.
    public List<Integer> getAttackFrom() {
        return attackFrom;
    }

    // This method is used to get the list of territories that the player wants to attack from.
    public List<Integer> getAttackTo() {
        return attackTo;
    }

    // This method is used to get the list of territories that the player wants to upgrade units.
    public List<Troop> getAttackTroop() {
        return attackTroop;
    }

    // This method is used to get the list of numbers of units that the player wants to attack with.
    public List<Integer> getMoveFrom() {
        return moveFrom;
    }

    // This method is used to get the list of territories that the player wants to move units to.
    public List<Integer> getMoveTo() {
        return moveTo;
    }

    // This method is used to get the list of numbers of units that the player wants to move from each territory.
    public List<Troop> getMoveTroop() {
        return moveTroop;
    }

    // This method is used to get the list of territories that the player wants to upgrade units.
    public List<Integer> getUpgradeUnitTerritory() {
        return upgradeUnitTerritory;
    }

    // This method is used to get the list of units that the player wants to upgrade.
    public List<Integer> getUpgradeUnitId() {
        return upgradeUnitId;
    }

    // This method is used to get the list of levels that the player wants to upgrade to.
    public List<Integer> getUpgradeUnitLevel() {
        return upgradeUnitLevel;
    }

    // This method is used to get the boolean value that indicates whether the player wants to upgrade tech.
    public Boolean getUpgradeTech() {
        return upgradeTech;
    }

}

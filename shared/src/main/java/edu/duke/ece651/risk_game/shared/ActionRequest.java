package edu.duke.ece651.risk_game.shared;

import java.util.List;

/**
 * This class is used to create a message that contains the information of the player's action.
 * The message contains the information of the player's action. The player can move units from one territory to another, and attack from one territory to another.
 * The player can also choose to not move or attack.
 */
public class ActionRequest extends Message {
    private final List<Integer> moveFrom;
    private final List<Integer> moveTo;
    private final List<Integer> moveNums;
    private final List<Integer> attackFrom;
    private final List<Integer> attackTo;
    private final List<Integer> attackNums;

    /**
     * This constructor is used to create a message that contains the information of the player's action.
     *
     * @param playerID   the ID of the player
     * @param moveFrom   the list of territories that the player wants to move units from
     * @param moveTo     the list of territories that the player wants to move units to
     * @param moveNums   the list of numbers of units that the player wants to move
     * @param attackFrom the list of territories that the player wants to attack from
     * @param attackTo   the list of territories that the player wants to attack to
     * @param attackNums the list of numbers of units that the player wants to attack with
     */
    public ActionRequest(Integer playerID, List<Integer> moveFrom, List<Integer> moveTo, List<Integer> moveNums, List<Integer> attackFrom, List<Integer> attackTo, List<Integer> attackNums) {
        super(playerID);
        if (moveFrom.size() != moveTo.size() || moveFrom.size() != moveNums.size())
            throw new IllegalArgumentException("The size of moveFrom, moveTo, and moveNums should be the same.");
        if (attackFrom.size() != attackTo.size())
            throw new IllegalArgumentException("The size of attackFrom and attackTo should be the same.");
        this.moveFrom = moveFrom;
        this.moveTo = moveTo;
        this.moveNums = moveNums;
        this.attackFrom = attackFrom;
        this.attackTo = attackTo;
        this.attackNums = attackNums;
    }

//    /**
//     * This constructor is used to create a message that contains the information of the player's action.
//     *
//     * @param playerID the ID of the player
//     * @param actions  the list of actions that the player wants to take
//     */
//    public ActionRequest(Integer playerID, List<String> actions) {
//        super(playerID);
//        this.moveFrom = new ArrayList<>();
//        this.moveTo = new ArrayList<>();
//        this.moveNums = new ArrayList<>();
//        this.attackFrom = new ArrayList<>();
//        this.attackTo = new ArrayList<>();
//        this.attackNums = new ArrayList<>();
//        for (String action : actions) {
//            String[] actionInfo = action.split(" ");
//            if (actionInfo[0].equals("move")) {
//                moveFrom.add(Integer.parseInt(actionInfo[1]));
//                moveTo.add(Integer.parseInt(actionInfo[2]));
//                moveNums.add(Integer.parseInt(actionInfo[3]));
//            } else if (actionInfo[0].equals("attack")) {
//                attackFrom.add(Integer.parseInt(actionInfo[1]));
//                attackTo.add(Integer.parseInt(actionInfo[2]));
//                attackNums.add(Integer.parseInt(actionInfo[3]));
//            } else {
//                throw new IllegalArgumentException("The action should be either move or attack.");
//            }
//        }
//    }

    // This method is used to get the list of territories that the player wants to move units from.
    public List<Integer> getAttackFrom() {
        return attackFrom;
    }

    // This method is used to get the list of territories that the player wants to attack from.
    public List<Integer> getAttackTo() {
        return attackTo;
    }

    // This method is used to get the list of territories that the player wants to attack to.
    public List<Integer> getAttackNums() {
        return attackNums;
    }

    // This method is used to get the list of numbers of units that the player wants to attack with.
    public List<Integer> getMoveFrom() {
        return moveFrom;
    }

    // This method is used to get the list of territories that the player wants to move units to.
    public List<Integer> getMoveTo() {
        return moveTo;
    }

    //  This method is used to get the list of numbers of units that the player wants to move.
    public List<Integer> getMoveNums() {
        return moveNums;
    }
}

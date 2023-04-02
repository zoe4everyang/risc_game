package edu.duke.ece651.risk_game.client;

import java.util.ArrayList;

public class MoveCommitRequest extends Message{
    private final ArrayList<Integer> operation;
    private final ArrayList<Integer> from;
    private final ArrayList<Integer> to;
    private final ArrayList<Integer> nums;

    public MoveCommitRequest(Integer playerID, ArrayList<Integer> operation, ArrayList<Integer> from, ArrayList<Integer> to, ArrayList<Integer> nums) {
        super(playerID);

//        if (operation.size() != from.size() || operation.size() != to.size() || operation.size() != nums.size())
//            throw new IllegalArgumentException("The size of operation, from, to, and nums should be the same.");

        this.operation = operation;
        this.from = from;
        this.to = to;
        this.nums = nums;
    }

    public ArrayList<Integer> getOperation() {
        return operation;
    }

    public Integer getOperationAt(Integer i) {
        return operation.get(i);
    }

    public ArrayList<Integer> getFrom() {
        return from;
    }

    public Integer getFromAt(Integer i) {
        return from.get(i);
    }

    public ArrayList<Integer> getTo() {
        return to;
    }

    public Integer getToAt(Integer i) {
        return to.get(i);
    }

    public ArrayList<Integer> getNums() {
        return nums;
    }

    public Integer getNumsAt(Integer i) {
        return nums.get(i);
    }
}

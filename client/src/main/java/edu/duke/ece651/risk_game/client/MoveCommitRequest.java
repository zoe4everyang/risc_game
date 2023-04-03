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
}

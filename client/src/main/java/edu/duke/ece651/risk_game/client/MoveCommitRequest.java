package edu.duke.ece651.risk_game.client;

import java.util.List;

public class MoveCommitRequest extends Message{
    private final List<Integer> operation;
    private final List<Integer> from;
    private final List<Integer> to;
    private final List<Integer> nums;

    public MoveCommitRequest(Integer playerID, List<Integer> operation, List<Integer> from, List<Integer> to, List<Integer> nums) {
        super(playerID);

        if (operation.size() != from.size() || operation.size() != to.size() || operation.size() != nums.size())
            throw new IllegalArgumentException("The size of operation, from, to, and nums should be the same.");

        this.operation = operation;
        this.from = from;
        this.to = to;
        this.nums = nums;
    }
    public List<Integer> getOperation() {
        return operation;
    }
    public List<Integer> getFrom() {
        return from;
    }
    public List<Integer> getTo() {
        return to;
    }
    public List<Integer> getNums() {
        return nums;
    }
}

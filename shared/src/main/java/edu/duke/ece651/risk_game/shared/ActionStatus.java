package edu.duke.ece651.risk_game.shared;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
public class ActionStatus {
    private final boolean success;
    private final Integer errorCode;
    private final String errorMessage;

    @JsonCreator
    public ActionStatus(@JsonProperty("success") boolean success,
                        @JsonProperty("errorCode") Integer errorCode,
                        @JsonProperty("errorMessage") String errorMessage) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

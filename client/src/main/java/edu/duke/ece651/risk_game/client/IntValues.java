package edu.duke.ece651.risk_game.client;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IntValues {
    private Integer[] intValues;
    public Integer[] getIntValues() {
        return intValues;
    }
    public void setIntValues(Integer[] intValues) {
        this.intValues = intValues;
    }
}
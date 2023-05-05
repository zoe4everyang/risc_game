package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.ActionRequest;
import edu.duke.ece651.risk_game.shared.ActionStatus;
import edu.duke.ece651.risk_game.shared.Territory;
import edu.duke.ece651.risk_game.shared.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class ActionController extends UIController{
    @FXML
    ComboBox<String> toComboBox;
    @FXML
    Slider level0NumSlider;
    @FXML
    Slider level1NumSlider;
    @FXML
    Slider level2NumSlider;
    @FXML
    Slider level3NumSlider;
    @FXML
    Slider level4NumSlider;
    @FXML
    Slider level5NumSlider;
    @FXML
    Slider level6NumSlider;
    List<Slider> numList = new ArrayList<>();
    @FXML
    Button cancel;

    public void initialize() {
        numList.add(level0NumSlider);
        numList.add(level1NumSlider);
        numList.add(level2NumSlider);
        numList.add(level3NumSlider);
        numList.add(level4NumSlider);
        numList.add(level5NumSlider);
        numList.add(level6NumSlider);
        List<Integer> levelList = new ArrayList<>(Collections.nCopies(7, 0));
        Territory curTerritory = gameContext.territories.get(gameContext.finalClickedTerritoryID);
        for (Unit u : curTerritory.getTroop().getUnits()) {
            levelList.set(u.getLevel(), levelList.get(u.getLevel()) + 1);
        }
        for (int i = 0; i < numList.size(); i++) {
            Slider slider = numList.get(i);
            slider.setMin(0);
            slider.setMax(levelList.get(i));
            slider.setValue(0);
            slider.setBlockIncrement(1);
            slider.setSnapToTicks(true);
        }
    }

    public void handleActionButton(String action) {
        int from = gameContext.finalClickedTerritoryID;
        int to = gameContext.territoryIDMaps.get(gameContext.currentRoomID).get(toComboBox.getValue());
        ArrayList<Integer> units = new ArrayList<>();
        for (Slider slider : numList) {
            units.add((int) slider.getValue());
        }
        ActionRequest request = new ActionRequest(gameContext.playerIDMap.get(gameContext.currentRoomID), from, to, units);
        ActionStatus status = gameContext.actionFns.get(action).apply(gameContext.currentRoomID, request);
        if (!status.isSuccess()) {
            String errorHeader;
            if (Objects.equals(action, "M")) {
                errorHeader = "Your move command is invalid.";
            } else {
                errorHeader = "Your attack command is invalid.";
            }
            gameContext.showErrorAlert(errorHeader,status.getErrorMessage());
        } else {
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void handleCancelButton() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}

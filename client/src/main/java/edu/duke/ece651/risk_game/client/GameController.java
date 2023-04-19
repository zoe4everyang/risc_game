package edu.duke.ece651.risk_game.client;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;

public abstract class GameController extends UIController{
    @FXML
    TextFlow roomIDTextFlow;
    @FXML
    Rectangle myColor;
    @FXML
    TextFlow TechPointTextFlow;
    @FXML
    TextFlow FoodPointTextFlow;
    @FXML
    TableView<Entry> territoryInfo;
    @FXML
    TableColumn<Entry, String> nameColumn;
    @FXML
    TableColumn<Entry, Integer> valueColumn;
    @FXML
    Polygon territory0;
    @FXML
    Polygon territory1;
    @FXML
    Polygon territory2;
    @FXML
    Polygon territory3;
    @FXML
    Polygon territory4;
    @FXML
    Polygon territory5;
    @FXML
    Polygon territory6;
    @FXML
    Polygon territory7;
    @FXML
    Polygon territory8;
    @FXML
    Polygon territory9;
    @FXML
    Polygon territory10;
    @FXML
    Polygon territory11;
    List<Polygon> territoryList;
    public void initialize(){
        territoryList = new ArrayList<>();
        territoryList.add(territory0);
        territoryList.add(territory1);
        territoryList.add(territory2);
        territoryList.add(territory3);
        territoryList.add(territory4);
        territoryList.add(territory5);
        territoryList.add(territory6);
        territoryList.add(territory7);
        territoryList.add(territory8);
        territoryList.add(territory9);
        territoryList.add(territory10);
        territoryList.add(territory11);
        myColor.setFill(Color.web(gameContext.colorList.get(gameContext.playerIDMap.get(gameContext.currentRoomID))));
        for (int i = 0; i < territoryList.size(); i++) {
            territoryList.get(i).setFill(Color.web(gameContext.colorList.get(gameContext.territories.get(i).getOwner())));
        }
        Text roomIDText = new Text(gameContext.currentRoomID.toString());
        roomIDTextFlow.getChildren().add(roomIDText);
        Text techPointText = new Text(gameContext.playerInfo.getResource().getTechPoint().toString());
        roomIDTextFlow.getChildren().add(techPointText);
        Text foodPointText = new Text(gameContext.playerInfo.getResource().getFoodPoint().toString());
        roomIDTextFlow.getChildren().add(foodPointText);
        territoryInfo.setStyle("-fx-table-header-visible: false;");

        Entry ownerEntry = new Entry("Owner", null);
        Entry levelOneEntry = new Entry("Level1 Unit", null);
        Entry levelTwoEntry = new Entry("Level2 Unit", null);
        Entry levelThreeEntry = new Entry("Level3 Unit", null);
        Entry levelFourEntry = new Entry("Level4 Unit", null);
        Entry levelFiveEntry = new Entry("Level5 Unit", null);
        Entry levelSixEntry = new Entry("Level6 Unit", null);
        Entry techProductionEntry = new Entry("Tech Production", null);
        Entry foodProductionEntry = new Entry("Food Production", null);
        Entry territorySizeEntry = new Entry("Territory Size", null);
        territoryInfo.getItems().addAll(ownerEntry, levelOneEntry, levelTwoEntry, levelThreeEntry,
                levelFourEntry, levelFiveEntry, levelSixEntry, techProductionEntry, foodProductionEntry,
                territorySizeEntry);

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        valueColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getValue()).asObject());

    }

    public static class Entry {
        private final String name;
        private Integer value;
        public Entry(String name, Integer value) {
            this.name = name;
            this.value = value;
        }
        public String getName() {
            return name;
        }
        public Integer getValue() {
            return value;
        }
        public void setValue(Integer value) {
            this.value = value;
        }
    }

    @FXML
    public void handleTerritory0Button(){

    }

}

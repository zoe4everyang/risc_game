package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.Territory;
import edu.duke.ece651.risk_game.shared.Unit;
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
import java.util.Collections;
import java.util.List;

public abstract class GameController extends UIController{
    @FXML
    TextFlow roomIDTextFlow;
    @FXML
    Rectangle myColor;
    @FXML
    TextFlow techLevelTextFlow;
    @FXML
    TextFlow techPointTextFlow;
    @FXML
    TextFlow foodPointTextFlow;
    @FXML
    TableView<Entry> territoryInfo;
    @FXML
    TableColumn<Entry, String> nameColumn;
    @FXML
    TableColumn<Entry, String> valueColumn;
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
            // TODO: DEBUG
            int ownerId = gameContext.territories.get(i).getOwner();
            String color = gameContext.colorList.get(ownerId);
            territoryList.get(i).setFill(Color.web(color));
        }
        Text roomIDText = new Text(gameContext.currentRoomID.toString());
        roomIDTextFlow.getChildren().add(roomIDText);
        Text techLevelText = new Text(gameContext.playerInfo.getTechLevel().toString());
        techLevelTextFlow.getChildren().add(techLevelText);
        Text techPointText = new Text(gameContext.playerInfo.getResource().getTechPoint().toString());
        techPointTextFlow.getChildren().add(techPointText);
        Text foodPointText = new Text(gameContext.playerInfo.getResource().getFoodPoint().toString());
        foodPointTextFlow.getChildren().add(foodPointText);
        territoryInfo.setStyle("-fx-table-header-visible: false;");

        Entry territoryNameEntry = new Entry("Name", null);
        Entry ownerEntry = new Entry("Owner", null);
        Entry levelZeroEntry = new Entry("Level0 Unit", null);
        Entry levelOneEntry = new Entry("Level1 Unit", null);
        Entry levelTwoEntry = new Entry("Level2 Unit", null);
        Entry levelThreeEntry = new Entry("Level3 Unit", null);
        Entry levelFourEntry = new Entry("Level4 Unit", null);
        Entry levelFiveEntry = new Entry("Level5 Unit", null);
        Entry levelSixEntry = new Entry("Level6 Unit", null);
        Entry techProductionEntry = new Entry("Tech Production", null);
        Entry foodProductionEntry = new Entry("Food Production", null);
        Entry territorySizeEntry = new Entry("Territory Size", null);
        territoryInfo.getItems().addAll(territoryNameEntry, ownerEntry, levelZeroEntry, levelOneEntry, levelTwoEntry, levelThreeEntry,
                levelFourEntry, levelFiveEntry, levelSixEntry, techProductionEntry, foodProductionEntry,
                territorySizeEntry);

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        valueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));

    }

    public static class Entry {
        private final String name;
        private String value;
        public Entry(String name, String value) {
            this.name = name;
            this.value = value;
        }
        public String getName() {
            return name;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }

    public void updateTableView(Territory territory) {
        ArrayList<String> updateList = new ArrayList<>();
        //Troop troop = territory.getTroop();
        updateList.add(territory.getName());
        updateList.add(gameContext.playerList.get(territory.getOwner()));
        List<Integer> levelList = new ArrayList<>(Collections.nCopies(7, 0));
        for (Unit u : territory.getTroop().getUnits()) {
            levelList.set(u.getLevel(), levelList.get(u.getLevel()) + 1);
        }
        updateList.add(Integer.toString(levelList.get(0)));
        updateList.add(Integer.toString(levelList.get(1)));
        updateList.add(Integer.toString(levelList.get(2)));
        updateList.add(Integer.toString(levelList.get(3)));
        updateList.add(Integer.toString(levelList.get(4)));
        updateList.add(Integer.toString(levelList.get(5)));
        updateList.add(Integer.toString(levelList.get(6)));
        updateList.add(Integer.toString(territory.getFoodProduction()));
        updateList.add(Integer.toString(territory.getTechProduction()));
        updateList.add(Integer.toString(territory.getCost()));
        for (int i = 0; i < updateList.size(); i++) {
            territoryInfo.getItems().get(i).setValue(updateList.get(i));
        }
        territoryInfo.refresh();
    }


    @FXML
    public void handleTerritory0Button(){
        Territory territory = gameContext.territories.get(0);
        updateTableView(territory);
    }

    @FXML
    public void handleTerritory1Button(){
        Territory territory = gameContext.territories.get(1);
        updateTableView(territory);
    }

    @FXML
    public void handleTerritory2Button(){
        Territory territory = gameContext.territories.get(2);
        updateTableView(territory);
    }

    @FXML
    public void handleTerritory3Button(){
        Territory territory = gameContext.territories.get(3);
        updateTableView(territory);
    }

    @FXML
    public void handleTerritory4Button(){
        Territory territory = gameContext.territories.get(4);
        updateTableView(territory);
    }

    @FXML
    public void handleTerritory5Button(){
        Territory territory = gameContext.territories.get(5);
        updateTableView(territory);
    }

    @FXML
    public void handleTerritory6Button(){
        Territory territory = gameContext.territories.get(6);
        updateTableView(territory);
    }

    @FXML
    public void handleTerritory7Button(){
        Territory territory = gameContext.territories.get(7);
        updateTableView(territory);
    }

    @FXML
    public void handleTerritory8Button(){
        Territory territory = gameContext.territories.get(8);
        updateTableView(territory);
    }

    @FXML
    public void handleTerritory9Button(){
        Territory territory = gameContext.territories.get(9);
        updateTableView(territory);
    }

    @FXML
    public void handleTerritory10Button(){
        Territory territory = gameContext.territories.get(10);
        updateTableView(territory);
    }

    @FXML
    public void handleTerritory11Button() {
        Territory territory = gameContext.territories.get(11);
        updateTableView(territory);
    }

}

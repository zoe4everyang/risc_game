package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.Territory;
import edu.duke.ece651.risk_game.shared.Unit;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    CheckBox spyCheckBox;
    @FXML
    CheckBox cloakTechCheckBox;
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
    Polygon[] territoryList;
    protected Runnable[] clickList;
    //int clickOnTerritoryID;
    @FXML
    ImageView spy0;
    @FXML
    ImageView spy1;
    @FXML
    ImageView spy2;
    @FXML
    ImageView spy3;
    @FXML
    ImageView spy4;
    @FXML
    ImageView spy5;
    @FXML
    ImageView spy6;
    @FXML
    ImageView spy7;
    @FXML
    ImageView spy8;
    @FXML
    ImageView spy9;
    @FXML
    ImageView spy10;
    @FXML
    ImageView spy11;
    ImageView[] spyImageList;

    public void initialize(){
        // map setup
        territoryList = new Polygon[]{territory0, territory1, territory2, territory3, territory4, territory5,
                territory6, territory7, territory8, territory9, territory10, territory11};
        myColor.setFill(Color.web(gameContext.colorList.get(gameContext.playerIDMap.get(gameContext.currentRoomID))));
        refreshColor();
        //clickOnTerritoryID = -1;
        clickList = new Runnable[]{this::handleTerritory0Button, this::handleTerritory1Button, this::handleTerritory2Button,
                this::handleTerritory3Button, this::handleTerritory4Button, this::handleTerritory5Button,
                this::handleTerritory6Button, this::handleTerritory7Button, this::handleTerritory8Button,
                this::handleTerritory9Button, this::handleTerritory10Button, this::handleTerritory11Button};

        // player info setup
        Text roomIDText = new Text(gameContext.currentRoomID.toString());
        roomIDText.setFont(Font.font("Palatino Linotype", FontWeight.NORMAL, 14));
        roomIDText.setStyle("-fx-fill: d4b91d;");
        roomIDTextFlow.getChildren().add(roomIDText);
        Text techLevelText = new Text(gameContext.playerInfo.getTechLevel().toString());
        techLevelText.setFont(Font.font("Palatino Linotype", FontWeight.NORMAL, 14));
        techLevelText.setStyle("-fx-fill: d4b91d;");
        techLevelTextFlow.getChildren().add(techLevelText);
        Text techPointText = new Text(gameContext.playerInfo.getResource().getTechPoint().toString());
        techPointText.setFont(Font.font("Palatino Linotype", FontWeight.NORMAL, 14));
        techPointText.setStyle("-fx-fill: d4b91d;");
        techPointTextFlow.getChildren().add(techPointText);
        Text foodPointText = new Text(gameContext.playerInfo.getResource().getFoodPoint().toString());
        foodPointText.setFont(Font.font("Palatino Linotype", FontWeight.NORMAL, 14));
        foodPointText.setStyle("-fx-fill: d4b91d;");
        foodPointTextFlow.getChildren().add(foodPointText);
        cloakTechCheckBox.setSelected(gameContext.playerInfo.getCanCloak());
        //cloakTechCheckBox.setStyle("-fx-color: green;");
        cloakTechCheckBox.setDisable(true);
        spyCheckBox.setSelected(gameContext.playerInfo.getHasSpy());
        //spyCheckBox.setStyle("-fx-color: green;");
        spyCheckBox.setDisable(true);

        // territory info setup
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
        Entry cloakLeftTurnEntry = new Entry("Cloaked Turns Left", null);
        territoryInfo.getItems().addAll(territoryNameEntry, ownerEntry, levelZeroEntry, levelOneEntry, levelTwoEntry, levelThreeEntry,
                levelFourEntry, levelFiveEntry, levelSixEntry, techProductionEntry, foodProductionEntry,
                territorySizeEntry, cloakLeftTurnEntry);
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        valueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));

        // spy icon setup
        spyImageList = new ImageView[]{spy0, spy1, spy2, spy3, spy4, spy5, spy6, spy7, spy8, spy9, spy10, spy11};
        for (int i = 0; i < spyImageList.length; i++) {
            spyImageList[i].setVisible(false);
        }
        if (gameContext.playerInfo.getHasSpy()) {
            spyImageList[gameContext.playerInfo.getSpyPosition()].setVisible(true);
        }
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

    protected void updateTableView(Territory territory) {
        List<Integer> levelList = new ArrayList<>(Collections.nCopies(7, 0));
        for (Unit u : territory.getTroop().getUnits()) {
            levelList.set(u.getLevel(), levelList.get(u.getLevel()) + 1);
        }
        String[] updateList = {territory.getName(), gameContext.playerList.get(territory.getOwner()),
                Integer.toString(levelList.get(0)), Integer.toString(levelList.get(1)), Integer.toString(levelList.get(2)),
                Integer.toString(levelList.get(3)), Integer.toString(levelList.get(4)), Integer.toString(levelList.get(5)),
                Integer.toString(levelList.get(6)), Integer.toString(territory.getTechProduction()),
                Integer.toString(territory.getFoodProduction()), Integer.toString(territory.getCost()),
                territory.getCloak() == -1 ? "Not Cloaked" : Integer.toString(territory.getCloak())};
        for (int i = 0; i < updateList.length; i++) {
            territoryInfo.getItems().get(i).setValue(updateList[i]);
        }
        territoryInfo.refresh();
    }

    protected void updateUnknownTable(String name) {
        ArrayList<String> updateList = new ArrayList<>(Collections.nCopies(13, "???"));
        territoryInfo.getItems().get(0).setValue(name);
        for (int i = 1; i < updateList.size(); i++) {
            territoryInfo.getItems().get(i).setValue(updateList.get(i));
        }
        territoryInfo.refresh();
    }

    protected void refreshColor() {
        for (int i = 0; i < territoryList.length; i++) {
            String color;
            //if (gameContext.territories.get(i).getOwner() == gameContext.playerIDMap.get(gameContext.currentRoomID) ||gameContext.playerInfo.getVisible().get(i)) {
            if (gameContext.playerInfo.getVisible().get(i)) {
                color = gameContext.colorList.get(gameContext.territories.get(i).getOwner());
            } else {
                color = gameContext.colorList.get(4);
            }
            territoryList[i].setFill(Color.web(color));
        }
    }

    @FXML
    public void handleTerritory0Button(){
        Territory territory = gameContext.territories.get(0);
        gameContext.finalClickedTerritoryID = 0;

        refreshColor();
        String newColor;
        if (gameContext.playerInfo.getVisible().get(0)) {
            newColor = gameContext.colorList.get(gameContext.territories.get(0).getOwner() + 5);
        } else {
            newColor = gameContext.colorList.get(9);
        }
        territory0.setFill(Color.web(newColor));

        if (gameContext.playerInfo.getVisited().get(0)) {
            updateTableView(territory);
        } else {
            updateUnknownTable(territory.getName());
        }
    }

    @FXML
    public void handleTerritory1Button(){
        Territory territory = gameContext.territories.get(1);
        gameContext.finalClickedTerritoryID = 1;

        refreshColor();
        String newColor;
        if (gameContext.playerInfo.getVisible().get(1)) {
            newColor = gameContext.colorList.get(gameContext.territories.get(1).getOwner() + 5);
        } else {
            newColor = gameContext.colorList.get(9);
        }
        territory1.setFill(Color.web(newColor));

        if (gameContext.playerInfo.getVisited().get(1)) {
            updateTableView(territory);
        } else {
            updateUnknownTable(territory.getName());
        }
    }

    @FXML
    public void handleTerritory2Button(){
        Territory territory = gameContext.territories.get(2);
        gameContext.finalClickedTerritoryID = 2;

        refreshColor();
        String newColor;
        if (gameContext.playerInfo.getVisible().get(2)) {
            newColor = gameContext.colorList.get(gameContext.territories.get(2).getOwner() + 5);
        } else {
            newColor = gameContext.colorList.get(9);
        }
        territory2.setFill(Color.web(newColor));

        if (gameContext.playerInfo.getVisited().get(2)) {
            updateTableView(territory);
        } else {
            updateUnknownTable(territory.getName());
        }
    }

    @FXML
    public void handleTerritory3Button(){
        Territory territory = gameContext.territories.get(3);
        gameContext.finalClickedTerritoryID = 3;

        refreshColor();
        String newColor;
        if (gameContext.playerInfo.getVisible().get(3)) {
            newColor = gameContext.colorList.get(gameContext.territories.get(3).getOwner() + 5);
        } else {
            newColor = gameContext.colorList.get(9);
        }
        territory3.setFill(Color.web(newColor));

        if (gameContext.playerInfo.getVisited().get(3)) {
            updateTableView(territory);
        } else {
            updateUnknownTable(territory.getName());
        }
    }

    @FXML
    public void handleTerritory4Button(){
        Territory territory = gameContext.territories.get(4);
        gameContext.finalClickedTerritoryID = 4;

        refreshColor();
        String newColor;
        if (gameContext.playerInfo.getVisible().get(4)) {
            newColor = gameContext.colorList.get(gameContext.territories.get(4).getOwner() + 5);
        } else {
            newColor = gameContext.colorList.get(9);
        }
        territory4.setFill(Color.web(newColor));

        if (gameContext.playerInfo.getVisited().get(4)) {
            updateTableView(territory);
        } else {
            updateUnknownTable(territory.getName());
        }
    }

    @FXML
    public void handleTerritory5Button(){
        Territory territory = gameContext.territories.get(5);
        gameContext.finalClickedTerritoryID = 5;

        refreshColor();
        String newColor;
        if (gameContext.playerInfo.getVisible().get(5)) {
            newColor = gameContext.colorList.get(gameContext.territories.get(5).getOwner() + 5);
        } else {
            newColor = gameContext.colorList.get(9);
        }
        territory5.setFill(Color.web(newColor));

        if (gameContext.playerInfo.getVisited().get(5)) {
            updateTableView(territory);
        } else {
            updateUnknownTable(territory.getName());
        }
    }

    @FXML
    public void handleTerritory6Button(){
        Territory territory = gameContext.territories.get(6);
        gameContext.finalClickedTerritoryID = 6;

        refreshColor();
        String newColor;
        if (gameContext.playerInfo.getVisible().get(6)) {
            newColor = gameContext.colorList.get(gameContext.territories.get(6).getOwner() + 5);
        } else {
            newColor = gameContext.colorList.get(9);
        }
        territory6.setFill(Color.web(newColor));

        if (gameContext.playerInfo.getVisited().get(6)) {
            updateTableView(territory);
        } else {
            updateUnknownTable(territory.getName());
        }
    }

    @FXML
    public void handleTerritory7Button(){
        Territory territory = gameContext.territories.get(7);
        gameContext.finalClickedTerritoryID = 7;

        refreshColor();
        String newColor;
        if (gameContext.playerInfo.getVisible().get(7)) {
            newColor = gameContext.colorList.get(gameContext.territories.get(7).getOwner() + 5);
        } else {
            newColor = gameContext.colorList.get(9);
        }
        territory7.setFill(Color.web(newColor));

        if (gameContext.playerInfo.getVisited().get(7)) {
            updateTableView(territory);
        } else {
            updateUnknownTable(territory.getName());
        }
    }

    @FXML
    public void handleTerritory8Button(){
        Territory territory = gameContext.territories.get(8);
        gameContext.finalClickedTerritoryID = 8;

        refreshColor();
        String newColor;
        if (gameContext.playerInfo.getVisible().get(8)) {
            newColor = gameContext.colorList.get(gameContext.territories.get(8).getOwner() + 5);
        } else {
            newColor = gameContext.colorList.get(9);
        }
        territory8.setFill(Color.web(newColor));

        if (gameContext.playerInfo.getVisited().get(8)) {
            updateTableView(territory);
        } else {
            updateUnknownTable(territory.getName());
        }
    }

    @FXML
    public void handleTerritory9Button(){
        Territory territory = gameContext.territories.get(9);
        gameContext.finalClickedTerritoryID = 9;

        refreshColor();
        String newColor;
        if (gameContext.playerInfo.getVisible().get(9)) {
            newColor = gameContext.colorList.get(gameContext.territories.get(9).getOwner() + 5);
        } else {
            newColor = gameContext.colorList.get(9);
        }
        territory9.setFill(Color.web(newColor));

        if (gameContext.playerInfo.getVisited().get(9)) {
            updateTableView(territory);
        } else {
            updateUnknownTable(territory.getName());
        }
    }

    @FXML
    public void handleTerritory10Button(){
        Territory territory = gameContext.territories.get(10);
        gameContext.finalClickedTerritoryID = 10;

        refreshColor();
        String newColor;
        if (gameContext.playerInfo.getVisible().get(10)) {
            newColor = gameContext.colorList.get(gameContext.territories.get(10).getOwner() + 5);
        } else {
            newColor = gameContext.colorList.get(9);
        }
        territory10.setFill(Color.web(newColor));

        if (gameContext.playerInfo.getVisited().get(10)) {
            updateTableView(territory);
        } else {
            updateUnknownTable(territory.getName());
        }
    }

    @FXML
    public void handleTerritory11Button() {
        Territory territory = gameContext.territories.get(11);
        gameContext.finalClickedTerritoryID = 11;

        refreshColor();
        String newColor;
        if (gameContext.playerInfo.getVisible().get(11)) {
            newColor = gameContext.colorList.get(gameContext.territories.get(11).getOwner() + 5);
        } else {
            newColor = gameContext.colorList.get(9);
        }
        territory11.setFill(Color.web(newColor));

        if (gameContext.playerInfo.getVisited().get(11)) {
            updateTableView(territory);
        } else {
            updateUnknownTable(territory.getName());
        }
    }

}

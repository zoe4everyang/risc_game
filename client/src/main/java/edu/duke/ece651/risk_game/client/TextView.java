package edu.duke.ece651.risk_game.client;

import edu.duke.ece651.risk_game.shared.InitResponse;
import edu.duke.ece651.risk_game.shared.Response;
import edu.duke.ece651.risk_game.shared.Territory;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TextView implements Viewer {
    PrintStream out;

    public TextView(PrintStream out) {
        this.out = out;
    }

    @Override
    public void initPrompt() {
        out.println("Welcome to RISC Game!");
    }

    @Override
    public void placePrompt(InitResponse initResponse, HashMap<Integer, String> territoryNameMap) {
        Integer id = initResponse.getPlayerID();
        StringBuilder territoryNames = new StringBuilder();
        for (Territory territory : initResponse.getTerritories()) {
            if (territory.getOwner() == id) {
                territoryNames.append(territoryNameMap.get(territory.getID())).append(" ");
            }
            territoryNames.append(territory.getName()).append("\n");
        }
        out.println("You have been connected to the server successfully!\n" +
                "You are Player" + id + ".\n" +
                "Your territories are: \n" +
                territoryNames.toString() + "\n" +
                "You have " + initResponse.getUnitAvailable() + " units available.\n" +
                "Please place your units on the map.");
    }

    @Override
    public void placeOneTerritoryPrompt(String territoryName) {
        out.println("Type in the number of units you want to place on " + territoryName + ":");
    }

    @Override
    public void losePrompt() {
        out.println("You lose!");
    }

    @Override
    public void resultPrompt(boolean failTheGame, Integer winner) {
        if (failTheGame) {
            out.println("Winner is Player" + winner + ".");
        } else {
            out.println("You win!");
        }
    }

    @Override
    public void displayTheWorld(Response response, HashMap<Integer, String> territoryNameMap) {
        HashMap<Integer, ArrayList<Territory>> territory_Map = new HashMap<>();
        for (Territory territory : response.getTerritories()) {
            if (territory_Map.containsKey(territory.getOwner())) {
                territory_Map.get(territory.getOwner()).add(territory);
            } else {
                ArrayList<Territory> temp = new ArrayList<>();
                temp.add(territory);
                territory_Map.put(territory.getOwner(), temp);
            }
        }
        StringBuilder s = new StringBuilder();
        for (Integer playerID : territory_Map.keySet()) {
            s.append("Player").append(playerID).append("\n");
            s.append("-".repeat(10)).append("\n");
            for (Territory t : territory_Map.get(playerID)) {
                s.append(printInfo(t, territoryNameMap)).append("\n");
            }
        }
        System.out.println(s.toString());
    }

    public String printInfo(Territory t, HashMap<Integer, String> territoryNameMap) {
        StringBuilder s = new StringBuilder();
        List<Integer> distances = t.getDistances();
        s.append(t.getUnits()).append(" units in ").append(t.getName()).append(" (next to:");
        for (int i = 0; i < distances.size(); i++) {
            if (distances.get(i) == 1) {
                s.append(" ").append(territoryNameMap.get(i));
            }
        }
        s.append(")");
        return s.toString();
    }
}
//    private ArrayList<Territory> toDisplay;
//    private HashMap<Integer,ArrayList<Territory>> territory_Map;
//    private int game_end;
//    private int lost_the_game;
//    private int initial_game;
//    private int player_id;
//    private String player_name;
//    private BufferedReader inputBuffer;
//    private ArrayList<Integer> Placement;
//
//    public HashMap<Integer,ArrayList<Territory>> get_territory_Map(){
//        return territory_Map;
//    }
//
//    public int getInitialGame(){
//        return this.initial_game;
//    }
//
//    public ArrayList<Integer> getPlacement(){
//        return this.Placement;
//    }
//
//
//

//
//    // initial defination
//    public TextView(ArrayList<Territory> toDisplay,int game_end,int fail_the_game, int unit_count, int player_id, String player_name) throws IOException{
//        ArrayList<Integer> owner_list = new ArrayList<Integer>();
//        HashMap<Integer,ArrayList<Territory>> territory_Map = new HashMap<Integer,ArrayList<Territory>>();
//        this.toDisplay = toDisplay;
//        if(toDisplay.size() > 10 || toDisplay.size() < 6){
//            throw new IllegalArgumentException("the num of territory must between 6 to 10");
//        }
//        if(unit_count != 0){
//            initial_the_Territory(unit_count);
//        }
//        for (Territory t:toDisplay){
//            if(!owner_list.contains(t.getOwner())){
//                owner_list.add(t.getOwner());
//                ArrayList<Territory> temp = new ArrayList<Territory>();
//                temp.add(t);
//                Integer owner = t.getOwner();
//                territory_Map.put(owner, temp);
//            }else{
//                Integer owner = t.getOwner();
//                ArrayList<Territory> temp = territory_Map.get(owner);
//                temp.add(t);
//                territory_Map.remove(owner);
//                territory_Map.put(owner, temp);
//            }
//        }
//        this.game_end = game_end;
//        this.lost_the_game = fail_the_game;
//        BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(System.in));
//        this.inputBuffer = inputBuffer;
//        this.Placement = new ArrayList<>();
//        this.player_id = player_id;
//        this.player_name = player_name;
//        if(unit_count == 0){
//            this.initial_game = 0;
//        }else{
//            this.initial_game = 1;
//        }
//    }
//
//    public void initial_the_Territory(int unit_count) throws IOException{
//        System.out.println("you can put unit "+ Integer.toString(unit_count));
//        ArrayList<Integer> Placement = new ArrayList<Integer>();
//        int count = unit_count;
//        int temp;
//        for (int i = 0; i < toDisplay.size(); i ++){
//            if(toDisplay.get(i).getOwner() != this.player_id){
//                Placement.add(-1);
//            }else{
//                while(true){
//                    temp = input_number();
//                    if(temp == -1){
//                        System.out.println("please input a number!");
//                    }else if(temp > count || temp <= 0){
//                        System.out.println("the num of unit must be less than the remain num of units");
//                    }else{
//                        Placement.add(temp);
//                        count = count - temp;
//                        break;
//                    }
//                }
//            }
//        }
//    }
//
//    private int input_number() throws IOException{
//        String line = readLine();
//        int i = Integer.valueOf(line).intValue();
//        return i;
//    }
//
//
//
//
//    private ArrayList<String> getConnectionTerritory(Territory t){
//        ArrayList<String> s = new ArrayList<String>();
//        List<Integer> distance = t.getDistances();
//        for (int i = 0; i< distance.size();i ++){
//            if(distance.get(i) == 1){
//                for(Territory terr : toDisplay){
//                    if(terr.getID() == i){
//                        s.add(terr.getName());
//                    }
//                }
//            }
//        }
//        return s;
//    }
//
//
//
//    private String readLine() throws IOException{
//        return inputBuffer.readLine();
//    }

//    @Override
//    public String readChoose() {
//        String text = "";
//        try {
//            text = inputBuffer.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if(text.length() != 1 || (text.charAt(0) != 'M' && text.charAt(0) != 'A' && text.charAt(0) != 'D')){
//            throw new IllegalArgumentException("you can only input 'M' 'A' AND 'D' to play the game!");
//        }
//        return text;
//    }

//    @Override


    // when we record the attack, I design it like ::
    //----------------------------------------------------------------
    // where do you want to attack from? (please the name of the territory)
    // how many unit do you want the use to attack? 
    // which territory do you want to attack at? 
//    private int record_Attack() throws IOException {
//        String attack_from;
//        String attack_to;
//        int attack_unit;
//        int test_pass = 1;
//        System.out.println("Where do you want to attack from? (please the name of the territory)");
//        attack_from = readLine();
//        System.out.println("How many unit do you want the use to attack? ");
//        attack_to = readLine();
//        System.out.println("Which territory do you want to attack? ");
//        attack_unit = Integer.valueOf(readLine());
//        test_pass = check_attack(attack_from, attack_to, attack_unit);
//        if(test_pass == 1){
//            AttackFrom.add(findTerritoryByName(attack_from));
//            AttackTo.add(findTerritoryByName(attack_to));
//            AttackNums.add(attack_unit);
//        }
//        return test_pass;
//    }


    // when we record the attack, I design it like ::
    //----------------------------------------------------------------
    // where do you want to move from ? (please the name of the territory)
    // how many unit do you want the use to move? 
    // which territory do you want to move to?
//    private int record_Move() throws NumberFormatException, IOException{
//        String move_from;
//        String move_to;
//        int move_unit;
//        int test_pass = 1;
//        System.out.println("Where do you want to move from ? (please the name of the territory)");
//        move_from = readLine();
//        System.out.println("How many unit do you want the use to move? ");
//        move_to = readLine();
//        System.out.println("Which territory do you want to move? ");
//        move_unit = Integer.valueOf(readLine());
//        test_pass = check_move(move_from,move_to,move_unit);
//        if(test_pass == 1){
//            MoveFrom.add(findTerritoryByName(move_from));
//            MoveTo.add(findTerritoryByName(move_to));
//            MoveNums.add(move_unit);
//        }
//        return test_pass;
//    }
//
//    private int check_attack(String attack_from, String attack_to, int attack_unit) throws NumberFormatException, IOException{
//        for(Territory t: toDisplay){
//            if (t.getName() == attack_from){
//                if(t.getOwner() != player_id){
//                    System.out.println("you can not use the unit from territory which does not belong to you!");
//                    return 0;
//                }else if (t.getUnits() <= attack_unit){
//                    System.out.println("you do not have enough units to make this attack!");
//                    return 0;
//                }
//                return 1;
//            }
//        }
//        System.out.println("please input the correct name of the territory to make the attack from!");
//        return 0;
//    }
//
//
//
//    private int check_move(String move_from, String move_to, int move_unit) throws NumberFormatException, IOException{
//        int status = 0;
//        for(Territory t: toDisplay){
//            if (t.getName() == move_from){
//                if(t.getOwner() != player_id){
//                    System.out.println("you can not use the unit from territory which does not belong to you!");
//                    return 0;
//                }else if (t.getUnits() <= move_unit){
//                    System.out.println("you do not have enough units to make this movement!");
//                    return 0;
//                }
//            }
//            status ++;
//        }
//        for(Territory t: toDisplay){
//            if (t.getName() == move_to){
//                if(t.getOwner() != player_id){
//                    System.out.println("you can not move your units to a territory which does not belong to you!");
//                    return 0;
//                }
//            }
//            status ++;
//        }
//        if(status != 2){
//            System.out.println("please input the correct name of territory!");
//            return 0;
//        }
//        return 1;
//    }
//
//    private int findTerritoryByName(String name){
//        for (Territory t:toDisplay){
//            if(t.getName().equals(name)){
//                return t.getID();
//            }
//        }
//        return -1;
//    }
//
//
//}

// Green player:
// -------------
// 10 units in Narnia (next to: Elantris, Midkemia)
// 12 units in Midkemia (next to: Narnia, Elantris, Scadrial, Oz)
// 8 units in Oz (next to: Midkemia, Scadrial, Mordor, Gondor)
// Blue player:
// ------------
// 6 units in Elantris (next to: Roshar, Scadrial, Midkemia, Narnia)
// 3 units in Roshar (next to: Hogwarts, Scadrial, Elantris)
// 5 units in Scadrial (next to: Elantris, Roshar, Hogwats, Mordor, Oz, Midkemia, Elantris)
// Red player:
// -----------
// 13 units in Gondor (next to: Oz, Mordor)
// 14 units in Mordor (next to: Hogwarts, Gondor, Oz, Scadrial)
// 3 units in Hogwarts (next to: Mordor, Scadrial, Roshar)
// You are the Green player, what would you like to do?
// (M)ove
// (A)ttack
// (D)one


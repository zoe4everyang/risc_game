package edu.duke.ece651.risk_game.client;
import edu.duke.ece651.risk_game.shared.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextView implements Viewer{

    private ArrayList<Territory> toDisplay;
    private HashMap<Integer,ArrayList<Territory>> territory_Map;
    private int game_end;
    private int lost_the_game;
    private int initial_game;
    private int player_id;
    private String player_name;
    private BufferedReader inputBuffer;
    private ArrayList<Integer> MoveFrom;
    private ArrayList<Integer> MoveTo;
    private ArrayList<Integer> MoveNums;
    private ArrayList<Integer> AttackFrom;
    private ArrayList<Integer> AttackTo;
    private ArrayList<Integer> AttackNums;
    private ArrayList<Integer> Placement;

    public HashMap<Integer,ArrayList<Territory>> get_territory_Map(){
        return territory_Map;
    }
    
    public int getInitialGame(){
        return this.initial_game;
    }

    public ArrayList<Integer> getPlacement(){
        return this.Placement;
    }

    public ArrayList<Integer> getMoveFrom(){
        return this.MoveFrom;
    }

    public ArrayList<Integer> getMoveTo(){
        return this.MoveTo;
    }

    public ArrayList<Integer> getMoveNums(){
        return this.MoveNums;
    }

    public ArrayList<Integer> getAttackFrom(){
        return this.AttackFrom;
    }

    public ArrayList<Integer> getAttackTo(){
        return this.AttackTo;
    }

    public ArrayList<Integer> getAttackNums(){
        return this.AttackNums;
    }

    // initial defination
    public TextView(ArrayList<Territory> toDisplay,int game_end,int fail_the_game, int unit_count, int player_id, String player_name) throws IOException{
        ArrayList<Integer> owner_list = new ArrayList<Integer>();
        HashMap<Integer,ArrayList<Territory>> territory_Map = new HashMap<Integer,ArrayList<Territory>>();
        this.toDisplay = toDisplay;
        if(toDisplay.size() > 10 || toDisplay.size() < 6){
            throw new IllegalArgumentException("the num of territory must between 6 to 10");
        }
        if(unit_count != 0){
            initial_the_Territory(unit_count);
        }
        for (Territory t:toDisplay){
            if(!owner_list.contains(t.getOwner())){
                owner_list.add(t.getOwner());
                ArrayList<Territory> temp = new ArrayList<Territory>();
                temp.add(t);
                Integer owner = t.getOwner();
                territory_Map.put(owner, temp);
            }else{
                Integer owner = t.getOwner();
                ArrayList<Territory> temp = territory_Map.get(owner);
                temp.add(t);
                territory_Map.remove(owner);
                territory_Map.put(owner, temp);
            }
        }
        this.game_end = game_end;
        this.lost_the_game = fail_the_game;
        BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(System.in));
        this.inputBuffer = inputBuffer;
        this.MoveFrom = new ArrayList<>();
        this.MoveTo = new ArrayList<>();
        this.MoveNums = new ArrayList<>();
        this.AttackFrom = new ArrayList<>();
        this.AttackTo = new ArrayList<>();
        this.AttackNums = new ArrayList<>();
        this.Placement = new ArrayList<>();
        this.player_id = player_id;
        this.player_name = player_name;
        if(unit_count == 0){
            this.initial_game = 0;
        }else{
            this.initial_game = 1;
        }
    }

    public void initial_the_Territory(int unit_count) throws IOException{
        System.out.println("you can put unit "+ Integer.toString(unit_count));
        ArrayList<Integer> Placement = new ArrayList<Integer>();
        int count = unit_count;
        int temp;
        for (int i = 0; i < toDisplay.size(); i ++){
            if(toDisplay.get(i).getOwner() != this.player_id){
                Placement.add(-1);
            }else{
                while(true){
                    temp = input_number();
                    if(temp == -1){
                        System.out.println("please input a number!");
                    }else if(temp > count || temp <= 0){
                        System.out.println("the num of unit must be less than the remain num of units");
                    }else{
                        Placement.add(temp);
                        count = count - temp;
                        break;
                    }
                }
            }
        }
    }

    private int input_number() throws IOException{
        String line = readLine();
        int i = Integer.valueOf(line).intValue();
        return i;
    }

    public void display(){
        String s = "";
        for (Integer i : territory_Map.keySet()) {
            s = s + "Player" + Integer.toString(i) + "\n";
            s = s + makeString(10);
            for (Territory t: territory_Map.get(i)){
                s = s + printInfor(t) + "\n";
            }
        }
        System.out.println(s);
    }

    public String  makeString(int num){
        String s = "";
        for (int i = 0; i < num; i++){
            s = s + "-";
        }
        s = s + "\n";
        return s;
    }

    public String printInfor(Territory t){
        String s = "";
        ArrayList<String> neibor_list = getConnectionTerritory(t);
        s = s + Integer.toString(t.getUnits()) + " units in " + t.getName() + " (next to:";
        for (String name :neibor_list){
            s = s + " " + name;
        }
        s = s + ")";
        return s;
    }

    private ArrayList<String> getConnectionTerritory(Territory t){
        ArrayList<String> s = new ArrayList<String>();
        List<Integer> distance = t.getDistances();
        for (int i = 0; i< distance.size();i ++){
            if(distance.get(i) == 1){
                for(Territory terr : toDisplay){
                    if(terr.getID() == i){
                        s.add(terr.getName());
                    }
                }
            }
        }
        return s;
    }

    @Override
    public void displayGamePrompt() {
        if(game_end == 1){
            System.out.println("the game is ended");
        }else if(lost_the_game == 1){
            System.out.println("you have lost in the game!");
        }else{
            System.out.println( player_name + ", what would you like to do?");
            System.out.println("(M)ove");
            System.out.println("(A)ttack");
            System.out.println("(D)one");
        }
    }

    private String readLine() throws IOException{
        return inputBuffer.readLine();
    }

    @Override
    public String readChoose() {
        String text = "";
        try {
            text = inputBuffer.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(text.length() != 1 || (text.charAt(0) != 'M' && text.charAt(0) != 'A' && text.charAt(0) != 'D')){
            throw new IllegalArgumentException("you can only input 'M' 'A' AND 'D' to play the game!");
        }
        return text;
    }

    @Override
    public int playGamePrompt() throws IOException {
        displayGamePrompt();
        char choose = readChoose().charAt(0);
        if(choose == 'A'){
            record_Attack();
            return 1;
        }else if(choose == 'M'){
            record_Move();
            return 1;
        }
        return 0;
    }

    // when we record the attack, I design it like ::
    //----------------------------------------------------------------
    // where do you want to attack from? (please the name of the territory)
    // how many unit do you want the use to attack? 
    // which territory do you want to attack at? 
    private int record_Attack() throws IOException {
        String attack_from;
        String attack_to;
        int attack_unit;
        int test_pass = 1;
        System.out.println("Where do you want to attack from? (please the name of the territory)");
        attack_from = readLine();
        System.out.println("How many unit do you want the use to attack? ");
        attack_to = readLine();
        System.out.println("Which territory do you want to attack? ");
        attack_unit = Integer.valueOf(readLine());
        test_pass = check_attack(attack_from, attack_to, attack_unit);
        if(test_pass == 1){
            AttackFrom.add(findTerritoryByName(attack_from));
            AttackTo.add(findTerritoryByName(attack_to));
            AttackNums.add(attack_unit);
        }
        return test_pass;
    }


    // when we record the attack, I design it like ::
    //----------------------------------------------------------------
    // where do you want to move from ? (please the name of the territory)
    // how many unit do you want the use to move? 
    // which territory do you want to move to?
    private int record_Move() throws NumberFormatException, IOException{
        String move_from;
        String move_to;
        int move_unit;
        int test_pass = 1;
        System.out.println("Where do you want to move from ? (please the name of the territory)");
        move_from = readLine();
        System.out.println("How many unit do you want the use to move? ");
        move_to = readLine();
        System.out.println("Which territory do you want to move? ");
        move_unit = Integer.valueOf(readLine());
        test_pass = check_move(move_from,move_to,move_unit);
        if(test_pass == 1){
            MoveFrom.add(findTerritoryByName(move_from));
            MoveTo.add(findTerritoryByName(move_to));
            MoveNums.add(move_unit);
        }
        return test_pass;
    }

    private int check_attack(String attack_from, String attack_to, int attack_unit) throws NumberFormatException, IOException{
        for(Territory t: toDisplay){
            if (t.getName() == attack_from){
                if(t.getOwner() != player_id){
                    System.out.println("you can not use the unit from territory which does not belong to you!");
                    return 0;
                }else if (t.getUnits() <= attack_unit){
                    System.out.println("you do not have enough units to make this attack!");
                    return 0;
                }
                return 1;
            }
        }
        System.out.println("please input the correct name of the territory to make the attack from!");
        return 0;
    }



    private int check_move(String move_from, String move_to, int move_unit) throws NumberFormatException, IOException{
        int status = 0;
        for(Territory t: toDisplay){
            if (t.getName() == move_from){
                if(t.getOwner() != player_id){
                    System.out.println("you can not use the unit from territory which does not belong to you!");
                    return 0;
                }else if (t.getUnits() <= move_unit){
                    System.out.println("you do not have enough units to make this movement!");
                    return 0;
                }
            }
            status ++;
        }
        for(Territory t: toDisplay){
            if (t.getName() == move_to){
                if(t.getOwner() != player_id){
                    System.out.println("you can not move your units to a territory which does not belong to you!");
                    return 0;
                }
            }
            status ++;
        }
        if(status != 2){
            System.out.println("please input the correct name of territory!");
            return 0;
        }
        return 1;
    }
    
    private int findTerritoryByName(String name){
        for (Territory t:toDisplay){
            if(t.getName().equals(name)){
                return t.getID();
            }
        }
        return -1;
    }

    @Override
    public void playOneTurn() throws IOException {
        display();
        while(true){
            if(playGamePrompt() == 0){
                break;
            }
        }
    }
}

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


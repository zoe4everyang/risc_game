package edu.duke.ece651.risk_game.client;
import edu.duke.ece651.risk_game.shared.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextView implements Viewer{

    private ArrayList<Territory> toDisplay;
    private HashMap<Integer,ArrayList<Territory>> territory_Map;
    private int game_end;
    private int lost_the_game;
    private BufferedReader inputBuffer;
    private ArrayList<Integer> MoveFrom;
    private ArrayList<Integer> MoveTo;
    private ArrayList<Integer> MoveNums;
    private ArrayList<Integer> AttackFrom;
    private ArrayList<Integer> AttackTo;
    private ArrayList<Integer> AttackNums;

    public HashMap<Integer,ArrayList<Territory>> get_territory_Map(){
        return territory_Map;
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
    public TextView(ArrayList<Territory> toDisplay,int game_end,int fail_the_game){
        ArrayList<Integer> owner_list = new ArrayList<Integer>();
        HashMap<Integer,ArrayList<Territory>> territory_Map = new HashMap<Integer,ArrayList<Territory>>();
        this.toDisplay = toDisplay;
        if(toDisplay.size() > 10 || toDisplay.size() < 6){
            throw new IllegalArgumentException("the num of territory must between 6 to 10");
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
        s = s + Integer.toString(t.getUnits()) + " units in " + t.getName() + " (next to:";
        for (int i :t.getDistances()){
            s = s + " territory" + Integer.toString(i);
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
            System.out.println("what would you like to do?");
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
    // how many unit do you want the use to attack? (please the name of the territory)
    // which territory do you want to attack? 
    private int record_Attack() throws IOException {
        String attack_from;
        String attack_to;
        int attack_unit;
        int test_pass = 1;
        System.out.println("Where do you want to attack from? (please the name of the territory)");
        attack_from = readLine();
        System.out.println("How many unit do you want the use to attack? (please the name of the territory)");
        attack_to = readLine();
        System.out.println("Which territory do you want to attack? ");
        attack_unit = Integer.valueOf(readLine());
        //add check method here to check if the attack is allowed
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
    // how many unit do you want the use to move? (please the name of the territory)
    // which territory do you want to move?
    private int record_Move() throws NumberFormatException, IOException{
        String move_from;
        String move_to;
        int move_unit;
        int test_pass = 1;
        System.out.println("Where do you want to move from ? (please the name of the territory)");
        move_from = readLine();
        System.out.println("How many unit do you want the use to move? (please the name of the territory)");
        move_to = readLine();
        System.out.println("Which territory do you want to move? ");
        move_unit = Integer.valueOf(readLine());
        //add check method here to check if the attack is allowed
        if(test_pass == 1){
            MoveFrom.add(findTerritoryByName(move_from));
            MoveTo.add(findTerritoryByName(move_to));
            MoveNums.add(move_unit);
        }
        return test_pass;
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



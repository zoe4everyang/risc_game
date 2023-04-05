package edu.duke.ece651.risk_game.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextView implements Viewer{
    private ArrayList<Territory> toDisplay;
    private HashMap<Integer,ArrayList<Territory>> territory_Map;

    // initial defination
    public TextView(ArrayList<Territory> toDisplay){
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
    }

    public String display(){
        String s = "";
        for (Integer i : territory_Map.keySet()) {
            s = s + "Player" + Integer.toString(i) + "\n";
            s = s + makeString(10);
            for (Territory t: territory_Map.get(i)){
                s = s + printInfor(t) + "\n";
            }
        }
        s = s + "\n"; 
        return s;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayGamePrompt'");
    }

    @Override
    public void displayPlacementPrompt() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayPlacementPrompt'");
    }

    @Override
    public void displayStartPrompt() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayStartPrompt'");
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

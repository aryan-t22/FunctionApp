package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// A worklist of Functions. The worklist itself cannot be reassigned to another list, but can be modified by the
// functionality provided within the class.
public class Worklist {
    private final List<Function> listFn;

    // EFFECTS: Constructs a new worklist
    public Worklist() {
        listFn = new ArrayList<>();
    }

    public List<Function> getListFn() {
        return listFn;
    }

    // EFFECTS: returns the size of the worklist.
    public int length() {
        return listFn.size();
    }

    // EFFECTS: checks if the worklist is empty or not
    public Boolean isEmpty() {
        return listFn.isEmpty();
    }

    // MODIFIES: this.
    // EFFECTS: inserts a function into the worklist.
    public void insertFunc(Function fn) {
        EventLog.getInstance().logEvent(new Event("Added " + fn.name("x") + " to the worklist."));
        listFn.add(fn);
    }

    // MODIFIES: this.
    // EFFECTS: removes the first instance of fn from the worklist. If the worklist has no functions, does nothing.
    public void removeFunc(Function fn) {
        if (!isEmpty()) {
            EventLog.getInstance().logEvent(new Event("Removed " + fn.name("x") + " to the worklist."));
            listFn.remove(fn);
        }
    }

    // EFFECTS: Produces the names of all the functions in the worklist as a single String. Produces "the worklist is
    // empty." if the worklist is empty
    public String names() {
        if (isEmpty()) {
            EventLog.getInstance().logEvent(new Event("Viewed the worklist when it was empty."));
            return "The worklist is empty.";
        } else {
            String result = "Here are the functions in your worklist: \n";
            for (Function fn : listFn) {
                result += "[" + fn.name("x") + "]" + ",\n";
            }
            result =  result.substring(0, result.length() - 2) + ".";
            EventLog.getInstance().logEvent(new Event("Viewed the worklist: \n" + result));
            return result;
        }
    }

    // EFFECTS: Produces true if one of the Functions in the worklist is a child node Function
    public Boolean hasBasicFn() {
        if (!isEmpty()) {
            for (Function fn : listFn) {
                if (fn.getFnn().getIsBasicFunc()) {
                    return true;
                }
            }
        }
        return false;
    }
    // For logging loading

//    // EFFECTS: Creates an event for is called when a worklist is loaded from the saved directory.
//    public void loading() {
//        EventLog.getInstance().logEvent(new Event("Beginning to load the saved worklist:"));
//    }
//
//    // EFFECTS: is called when a worklist is loaded from the saved directory.
//    public void loaded() {
//        EventLog.getInstance().logEvent(new Event("Finished loading the saved worklist."));
//    }

    // EFFECTS: creates a .JSON object for a Worklist of Functions
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("subintervals", Function.getSubintervals());
        json.put("precision", Function.getPrecision());
        json.put("functions", this.functionsToJson());
        EventLog.getInstance().logEvent(new Event("Saved the current worklist."));
        return json;
    }

    // EFFECTS: creates a .JSON array for a list of functions
    private JSONArray functionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Function f : listFn) {
            jsonArray.put(f.toJson());
        }
        return jsonArray;
    }
}

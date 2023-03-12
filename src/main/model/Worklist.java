package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
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
        listFn.add(fn);
    }

    // MODIFIES: this.
    // EFFECTS: removes the first instance of fn from the worklist. If the worklist has no functions, does nothing.
    public void removeFunc(Function fn) {
        if (!isEmpty()) {
            listFn.remove(fn);
        }
    }

    // EFFECTS: Produces the names of all the functions in the worklist as a single String. Produces "the worklist is
    // empty." if the worklist is empty
    public String names() {
        if (isEmpty()) {
            return "The worklist is empty.";
        } else {
            String result = "Here are the functions in your worklist: \n";
            for (Function fn : listFn) {
                result += "[" + fn.name("x") + "]" + ", ";
            }
            return result.substring(0, result.length() - 2) + ".";
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

    // EFFECTS: creates a .JSON object for a Worklist of Functions
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("functions", this.functionsToJson());
        return json;
    }

    // EFFECTS: creates a .JSON array for a list of functions
    public JSONArray functionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Function f : listFn) {
            jsonArray.put(f.toJson());
        }
        return jsonArray;
    }
}

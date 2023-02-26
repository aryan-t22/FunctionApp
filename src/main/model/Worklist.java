package model;

import java.util.ArrayList;
import java.util.List;

public class Worklist {
    private final List<Function> listFn;

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
}

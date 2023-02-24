package model;

import java.util.ArrayList;
import java.util.List;

public class Worklist {
    private List<Function> listFn;

    public Worklist() {
        listFn = new ArrayList<>();
    }

    // EFFECTS: inserts a function into the worklist.
    public void insertFunc(Function fn) {
        listFn.add(fn);
    }

    // EFFECTS: removes the fist instance of fn from the worklist. If the worklist has no functions, does nothing.
    public void removeFunc(Function fn) {
        if (!listFn.isEmpty()) {
            listFn.remove(fn);
        }
    }
}

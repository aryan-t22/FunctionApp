package model;

import java.util.ArrayList;
import java.util.List;

public class Worklist {
    private List<Function> listFn;
    private List<Function> listFourier;

    public Worklist() {
        listFn = new ArrayList<>();
        listFourier = new ArrayList<>();
    }

    public void insertFunc(Function fn) {
        listFn.add(fn);
    }

    public void removeFunc(Function fn) {
        if (!listFn.isEmpty()) {
            listFn.remove(fn);
        }
    }
}

package persistence;

import model.Worklist;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: Creates a .JSON writer, with target directory source
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens a file from source, throws a FileNotFoundException if the file is not found at the source
    public void open() throws FileNotFoundException {
        this.writer = new PrintWriter(this.destination);
    }

    // EFFECTS: writes to the file found at source, storing wl as a .JSON object
    public void write(Worklist wl) {
        JSONObject json = wl.toJson();
        this.saveToFile(json.toString(TAB));
    }

    // EFFECTS: closes the writer
    public void close() {
        this.writer.close();
    }

    // EFFECTS: saves the .JSON object of the worklist in the target directory source
    private void saveToFile(String json) {
        this.writer.print(json);
    }
}

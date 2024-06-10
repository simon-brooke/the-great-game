package java.cc.journeyman.the_great_game.game_objects;

import java.util.Collection;
import java.util.LinkedList;

/** Java implementation of a game object which is a container. */

public class JContainer extends JObject {
    /** The contents of this container. */
    private LinkedList<JObject> contents = new LinkedList<JObject>();

    /** Get the contents of this container. */
    public LinkedList<JObject> getContents() {
        return this.contents;
    }

    /**
     * Add this single item to this container.
     * 
     * @param item
     */
    public void addItem(JObject item) {
    }

    public void addItem(Object item) {
        if (item instanceof JObject) {
            this.addItem((JObject)item);
        } else {
            throw new IllegalArgumentException("All items added to a container must be game objects");
        }
    }

    public void addItems(Collection<Object> items) {
        for (Object item : items) {

            this.contents.add((JObject) item);

        }
    }
}

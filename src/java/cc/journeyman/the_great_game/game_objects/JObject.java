package java.cc.journeyman.the_great_game.game_objects;

import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bounding.BoundingVolume;
import com.jme3.scene.Spatial;
import com.jme3.scene.Node;
import java.lang.Math;
import java.security.SecureRandom;

/**
 * Highly experimental! Using Java beans instead of Clojure records
 * for underlying game objects, to allow type inheritance. It has the
 * additional advantage that Java objects are inherently mutable. This is the
 * root of the hierarchy, a basic object.
 * 
 * See [JavaBeans Spec]
 * (http://www.oracle.com/technetwork/java/javase/documentation/spec-136004.html).
 * 
 * If this works(!), it is likely that object persistence and (re-loading) will
 * happen in the Java layer rather than in the Clojure layer.
 * 
 * A game object model in jMonkeyEngine is loaded as a 'Spatial' which
 * is a subclass of 'Node', which has a 'BoundingVolume' i.v. which is
 * instantiated as either a 'BoundingSphere' or a 'BoundingBox'. We
 * can't directly extract length/width/height data from these... but we
 * ought not to tolerate majow inconsistency between the size we report
 * and the volume reported by the model.
 */
public class JObject {
    private static SecureRandom idSeed = new SecureRandom();

    /**
     * When we have an actual database we'll probably get the id from
     * the database...
     */
    private long id = idSeed.nextLong();

    private int weight = 0;

    private int length = 0;

    private int width = 0;

    private int height = 0;

    private Node model;

    private BoundingVolume getBoundingVolume() {
        BoundingVolume result = null;
        // if (model instanceof Spatial) {
        //     result = (Spatial) model.getWorldBound();
        // }

        return result;
    }

    private double getMaxExtent() {
        BoundingVolume v = this.getBoundingVolume();
        double result = -1;

        if (v instanceof BoundingBox) {
            BoundingBox vb = (BoundingBox) v;
            result = Math.max( Math.max( vb.getXExtent(), vb.getYExtent()),
                vb.getZExtent());
        } else if (v instanceof BoundingSphere ) {
            result = 2 * ((BoundingSphere) v).getRadius();
        }

        return result;
    }

    /**  */

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    /** Return the id of this instance, obviously. */
    public long getId() {
        return id;
    }
}

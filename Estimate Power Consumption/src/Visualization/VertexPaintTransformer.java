package Visualization;

import edu.uci.ics.jung.visualization.picking.PickedInfo;
import org.apache.commons.collections15.Transformer;

import java.awt.*;

/**
 * Created by Admin on 21/08/2016.
 */
public class VertexPaintTransformer implements Transformer<String,Paint> {

    private final PickedInfo<String> pi;

    VertexPaintTransformer ( PickedInfo<String> pi ) {
        super();
        if (pi == null)
            throw new IllegalArgumentException("PickedInfo instance must be non-null");
        this.pi = pi;
    }

    @Override
    public Paint transform(String i) {
        Color p = null;
        p = Color.RED;
        if (pi.isPicked(i)) {
            p = Color.gray;
        }
        return p;
    }
}
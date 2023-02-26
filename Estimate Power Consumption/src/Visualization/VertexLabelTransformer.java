package Visualization;

import edu.uci.ics.jung.visualization.picking.PickedInfo;
import org.apache.commons.collections15.Transformer;

/**
 * Created by Admin on 21/08/2016.
 */
public class VertexLabelTransformer implements Transformer<String,String> {
    private final PickedInfo<String> pi;

    public VertexLabelTransformer( PickedInfo<String> pi ){
        this.pi = pi;
    }

    @Override
    public String transform(String t) {
        if (pi.isPicked(t))
            return t.toString();
        else
            return t;
    }
}
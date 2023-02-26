package Visualization;

import org.apache.commons.collections15.Transformer;

/**
 * Created by Admin on 21/08/2016.
 */
public class EdgeLabelTransformer implements Transformer<Edge, String>
{
    @Override
    public String transform(Edge edge)
    {
        return edge.toString();
    }
}


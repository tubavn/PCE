package Visualization;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import org.apache.commons.collections15.functors.ConstantTransformer;

import javax.swing.*;
import java.awt.*;

public class Visualization {

    public Graph<String, String> g;
    public Visualization() {
    }

    public JPanel VisualizationGraph() {
        Layout<String, String> layout = new CircleLayout( this.g );
        layout.setSize( new Dimension( 450, 450 ) );
        VisualizationViewer<String, String> vv = new VisualizationViewer<String, String>( layout );

        vv.setBorder(BorderFactory.createLineBorder(Color.black));
        vv.setPreferredSize( new Dimension( 470, 470 ) );
        vv.getRenderContext().setVertexLabelRenderer( new DefaultVertexLabelRenderer( Color.black ) );
        vv.getRenderContext().setVertexLabelTransformer( new VertexLabelTransformer( vv.getPickedVertexState() ) );

        vv.getRenderContext().setEdgeDrawPaintTransformer( new ConstantTransformer( Color.BLACK ) );
        vv.getRenderContext().setEdgeStrokeTransformer( new ConstantTransformer( new BasicStroke( 1.0f ) ) );
        vv.getRenderContext().setEdgeLabelTransformer( new ToStringLabeller() );
        vv.getRenderContext().setLabelOffset(15);
        vv.getRenderContext().setVertexFillPaintTransformer( new VertexPaintTransformer( vv.getPickedVertexState() ) );

        DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        graphMouse.setMode( ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse( graphMouse );
        vv.setBackground( Color.white );
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.S );

        JPanel jPanel = new JPanel();
        jPanel.add( vv );
        return jPanel;
    }








}
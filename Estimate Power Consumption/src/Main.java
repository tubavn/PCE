import Automata.GeneralAutomata;
import Automata.PowerAutomata;
import Visualization.Visualization;
import com.sun.deploy.panel.JavaPanel;
import com.sun.deploy.util.SystemUtils;
import edu.uci.ics.jung.graph.Graph;
import jxl.read.biff.BiffException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static ParseCode currentCode;
private  static  JFrame frame;
    private  static  JTextField jTextField;
    public static void main(String[] args) throws IOException, BiffException {
        jTextField = new JTextField(200);
        jTextField.setText("C:\\Users\\Admin\\Desktop\\DeviceManager");
        jTextField.setVisible(true);
        jTextField.addActionListener( action );
       ShowGraph();
    }

    static  Action action = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String path=jTextField.getText();
            if(!path.isEmpty()) {
                currentCode = new ParseCode(path);
                PowerAutomata audioAutomata = new PowerAutomata("src/Data/Audio.txt").simplifyAutomata(currentCode.audioCommands);
                PowerAutomata GPSAutomata = new PowerAutomata("src/Data/GPS.txt").simplifyAutomata(currentCode.GPSCommands);
                PowerAutomata LCDAutomata = new PowerAutomata("src/Data/LCD.txt").simplifyAutomata(currentCode.LCDCommands);
                PowerAutomata cellularAutomata = new PowerAutomata("src/Data/Cellular.txt").simplifyAutomata(currentCode.cellularCommands);
                PowerAutomata wifiAutomata = new PowerAutomata("src/Data/wifi.txt").simplifyAutomata(currentCode.wifiCommands);
                GeneralAutomata ga = new GeneralAutomata(audioAutomata, GPSAutomata, LCDAutomata, cellularAutomata, wifiAutomata);

                JTabbedPane tabs = new JTabbedPane();
                tabs.add("General", AddPanel(ga));
                tabs.add("Audio", AddPanel(audioAutomata));
                tabs.add("LCD", AddPanel(LCDAutomata));
                tabs.add("Wifi", AddPanel(wifiAutomata));
                tabs.add("Cellular", AddPanel(cellularAutomata));
                tabs.add("GPS", AddPanel(GPSAutomata));
                frame.add( tabs );
                frame.pack();
            }else
            {
                frame.dispose();
                ShowGraph();
            }

        }
    };

    private static JPanel AddPanel(PowerAutomata pa) {
        JPanel tab = new JavaPanel();
        Visualization d = new Visualization();
        d.g = (Graph) pa.ToGrahp();
        JPanel panel = d.VisualizationGraph();
        tab.add( panel );
        return tab;
    }

    private static void ShowGraph() {
        frame = new JFrame("PSA Form Demo");
        frame.add(jTextField, BorderLayout.NORTH);

//        frame.setLocation(880, 65);
        frame.setLocation(400,30);
        frame.setPreferredSize(new Dimension(525, 670));
        frame.pack();
        frame.setVisible(true);
    }


}



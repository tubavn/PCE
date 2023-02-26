package Automata;

import Visualization.Edge;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Admin on 21/07/2016.
 */
public class PowerAutomata {
    public PowerState[] States;
    public String[] Alpha;
    public String[][] Transition;

    public PowerAutomata()
    {}
    public  PowerAutomata(String filePath){
        try {
            GenerateAutomataFromFile( filePath );
        } catch (IOException e) {
            e.printStackTrace();
        }

//        PrintState();
//        PrintAlpha();
//        PrintTransition();
    }
    private  void GenerateAutomataFromFile(String filePath) throws IOException {
        LineNumberReader lnr = new LineNumberReader( new FileReader( new File( filePath ) ) );
        lnr.skip( Long.MAX_VALUE );
        int lineCount = lnr.getLineNumber() + 1;

        BufferedReader br = new BufferedReader( new FileReader( filePath ) );
        String firstLine = br.readLine();
        String[] part = firstLine.split( "-" );
        //setup Alpha
        Alpha = new String[part.length - 1];
        for (int i = 1; i < part.length; i++) Alpha[i - 1] = part[i];
        //setup States length
        States = new PowerState[lineCount - 1];
        Transition =new String[States.length][Alpha.length];
        //setup Transition table and State value
        String line;
        int count=0;
        while ((line = br.readLine()) != null) {
            part = line.split( "-" );
            States[count]=new PowerState();
            States[count].name=part[0].split(" ")[0];
            States[count].coefficient_min= Double.parseDouble(part[0].split(" ")[1]);
            States[count].coefficient_max= Double.parseDouble(part[0].split(" ")[2]);
            //System.out.println(States[count].coefficient);
            for (int i = 1; i < part.length; i++) {
                Transition[count][i-1] = part[i];
            }
            count++;
        }
    }



    public Graph ToGrahp() {
        DirectedSparseGraph<String, Edge> g = new DirectedSparseGraph<String, Edge>();
        for (int i = 0; i < States.length; i++) {
            g.addVertex( States[i].name + "(" + States[i].coefficient_min+"-->"+States[i].coefficient_max+  ")");
        }
        if(Transition!=null) {
            for (int i = 0; i < Transition.length; i++) {
                for (int j = 0; j < Transition[i].length; j++)
                    if (!Transition[i][j].equals( "null" )) {
                        PowerState destination = States[getStateIdByName(Transition[i][j])];
                        g.addEdge( new Edge( Alpha[j] ), States[i].name + "(" + States[i].coefficient_min+ "-->"+States[i].coefficient_max+  ")", destination.name+"(" + destination.coefficient_min+ "-->"+destination.coefficient_max+  ")");
                        //System.out.println(  States[i].name +"--------"+   States[i].coefficient);
                    }
            }
        }
        return g;
    }

    public void PrintAlpha()
    {
        System.out.println("==========Alpha==========");
        for (int i = 0; i < Alpha.length ; i++) {
            System.out.println(Alpha[i]);
        }
    }
    public void PrintState()
    {
        System.out.println("==========State==========");
        for (int i = 0; i < States.length ; i++) {
            System.out.print("["+i+"]"+States[i].name);
            System.out.println();
        }
    }
    public void PrintTransition()
    {
        System.out.println("==========Transition==========");
        for (int i = 0; i < Transition.length ; i++) {
            for(int j=0;j<Transition[i].length;j++)
                System.out.print("["+i+"]["+j+"]"+Transition[i][j]+"\t");
            System.out.println();
        }
    }

    public int getStateIdByName(String name)
    {
        for (int i = 0; i < States.length; i++) {
            if(States[i].name.equals(name)) return  i;
        }
        return -1;
    }
    public int getAlphaIdByName(String name)
    {
        for (int i = 0; i < Alpha.length; i++) {
            if(Alpha[i].equals(name)) return  i;
        }
        return -1;
    }

    public  PowerAutomata simplifyAutomata(String[] alpha) {

        String[] tempState = {States[0].name};
        String[] newAlpha = alpha;
        //calculate state
        if(alpha!=null) {
            String[] oldState = null;
            while (oldState != tempState) {
                oldState = tempState;
                for (int i = 0; i < tempState.length; i++)
                    for (int j = 0; j < newAlpha.length; j++) {
                        int idState = getStateIdByName( tempState[i] );
                        int idAlpha = getAlphaIdByName( newAlpha[j] );
                        if ((idAlpha != -1) && (idState) != -1) {
                            String newState = Transition[idState][idAlpha];
                            if ((!newState.equals( "null" )) && (!CheckIfStringExists( tempState, newState )))
                                tempState = AddStringToArray( tempState, newState );
                        }
                    }
            }
        }
        //return new Automata
        PowerAutomata pa = new PowerAutomata();

        pa.States = new PowerState[tempState.length];
        for (int i = 0; i < tempState.length; i++) {
            pa.States[i] =States[getStateIdByName(tempState[i])];
           // pa.States[i]=new PowerState();
         //   pa.States[i].name = tempState[i];
        }
        if(alpha!=null) {
            pa.Alpha = newAlpha;
            pa.Transition = new String[pa.States.length][pa.Alpha.length];
            for (int i = 0; i < pa.States.length; i++)
                for (int j = 0; j < pa.Alpha.length; j++) {
                    int idState = getStateIdByName( pa.States[i].name );
                    int idAlpha = getAlphaIdByName( pa.Alpha[j] );
                    if ((idAlpha != -1) && (idState) != -1) {
                        pa.Transition[i][j] = Transition[idState][idAlpha];
                    } else pa.Transition[i][j] = "null";
                }
        }
        return pa;
    }

    private   String[] AddStringToArray(String[] arr,String input)
    {
        ArrayList<String> arrayList =new ArrayList<String>(  );
        for(int i=0;i<arr.length;i++) arrayList.add( arr[i] );
        arrayList.add(input );
        String[] newString=new String[arr.length+1];
        arrayList.toArray( newString );
        return newString;
    }
    private boolean CheckIfStringExists(String[] arr,String input)
    {
        for(int i=0;i<arr.length;i++)
            if(arr[i].equals( input )) return true;
        return false;
    }
}

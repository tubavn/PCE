package Automata;

/**
 * Created by Admin on 06/09/2016.
 */
public class GeneralAutomata extends PowerAutomata {
    private PowerAutomata audioAutomata;
    private PowerAutomata GPSAutomata;
    private PowerAutomata LCDAutomata;
    private PowerAutomata cellularAutomata;
    private PowerAutomata wifiAutomata;

    public  GeneralAutomata(PowerAutomata audio,PowerAutomata GPS,PowerAutomata LCD, PowerAutomata cellular,PowerAutomata wifi) {
        audioAutomata = audio;
        GPSAutomata = GPS;
        LCDAutomata = LCD;
        cellularAutomata = cellular;
        wifiAutomata = wifi;

        CombineState();
        CombineAlpha();
        CalculateTransition();
    }
    private  void CombineState()
    {
        int totalState =audioAutomata.States.length*GPSAutomata.States.length*LCDAutomata.States.length*cellularAutomata.States.length*wifiAutomata.States.length;
        States=new PowerState[totalState];
        int count =0;
        for(PowerState audio:audioAutomata.States)
            for(PowerState gps:GPSAutomata.States)
                for(PowerState LCD:LCDAutomata.States)
                    for(PowerState cellular:cellularAutomata.States)
                        for(PowerState wifi:wifiAutomata.States)
                        {
                            States[count]=new PowerState();
                            States[count].name="State"+count;
                            States[count].audio=audio.name;
                            States[count].GPS=gps.name;
                            States[count].LCD=LCD.name;
                            States[count].cellular=cellular.name;
                            States[count].wifi=wifi.name;
                            double coefficient_min = 3.42+ audio.coefficient_min+gps.coefficient_min+LCD.coefficient_min+cellular.coefficient_min+wifi.coefficient_min;
                            States[count].coefficient_min = Math.round(coefficient_min*100.0)/100.0;

                            double coefficient_max = 555.46 +  audio.coefficient_max+gps.coefficient_max+LCD.coefficient_max+cellular.coefficient_max+wifi.coefficient_max;
                            States[count].coefficient_max = Math.round(coefficient_max*100.0)/100.0;
                            count++;
                        }
    }

    private  void CombineAlpha()
    {
        int totalAlpha=0;
        if(audioAutomata.Alpha!=null) totalAlpha+=audioAutomata.Alpha.length;
        if(GPSAutomata.Alpha!=null) totalAlpha+=GPSAutomata.Alpha.length;
        if(LCDAutomata.Alpha!=null) totalAlpha+=LCDAutomata.Alpha.length;
        if(cellularAutomata.Alpha!=null) totalAlpha+=cellularAutomata.Alpha.length;
        if(wifiAutomata.Alpha!=null) totalAlpha+=wifiAutomata.Alpha.length;

        Alpha = new String[totalAlpha];
        int count =0;
        if(audioAutomata.Alpha!=null) for(String str:audioAutomata.Alpha)  Alpha[count++]=str;
        if(GPSAutomata.Alpha!=null) for(String str:GPSAutomata.Alpha)  Alpha[count++]=str;
        if(LCDAutomata.Alpha!=null) for(String str:LCDAutomata.Alpha)  Alpha[count++]=str;
        if (cellularAutomata.Alpha!=null) for(String str:cellularAutomata.Alpha)  Alpha[count++]=str;
        if(wifiAutomata.Alpha!=null) for(String str:wifiAutomata.Alpha)  Alpha[count++]=str;
    }

    private  void CalculateTransition()
    {
        Transition =new String[States.length][Alpha.length];
        for (int i = 0; i < Transition.length ; i++)
            for (int j = 0; j < Transition[i].length ; j++)
                Transition[i][j]="null";
        //Audio
        if(audioAutomata.Alpha!=null)
            for (int i = 0; i < audioAutomata.Transition.length ; i++)
                for (int j = 0; j < audioAutomata.Transition[i].length ; j++)
                    if(!audioAutomata.Transition[i][j].equals( "null" )) {
                        String state1 = audioAutomata.States[i].name;
                        String state2 = audioAutomata.Transition[i][j];
                        String alpha = audioAutomata.Alpha[j];
                        AddAudioState( state1, state2, alpha );
                    }
        //LCD
        if(LCDAutomata.Alpha!=null)
            for (int i = 0; i < LCDAutomata.Transition.length ; i++)
                for (int j = 0; j < LCDAutomata.Transition[i].length ; j++)
                    if(!LCDAutomata.Transition[i][j].equals( "null" )) {
                        String state1 = LCDAutomata.States[i].name;
                        String state2 = LCDAutomata.Transition[i][j];
                        String alpha = LCDAutomata.Alpha[j];
                        AddLCDState( state1, state2, alpha );
                    }
        //GPS
        if(GPSAutomata.Alpha!=null)
            for (int i = 0; i < GPSAutomata.Transition.length ; i++)
                for (int j = 0; j < GPSAutomata.Transition[i].length ; j++)
                    if(!GPSAutomata.Transition[i][j].equals( "null" )) {
                        String state1 = GPSAutomata.States[i].name;
                        String state2 = GPSAutomata.Transition[i][j];
                        String alpha = GPSAutomata.Alpha[j];
                        AddGPSState( state1, state2, alpha );
                    }
        //cellular
        if(cellularAutomata.Alpha!=null)
            for (int i = 0; i < cellularAutomata.Transition.length ; i++)
                for (int j = 0; j < cellularAutomata.Transition[i].length ; j++)
                    if(!cellularAutomata.Transition[i][j].equals( "null" )) {
                        String state1 = cellularAutomata.States[i].name;
                        String state2 = cellularAutomata.Transition[i][j];
                        String alpha = cellularAutomata.Alpha[j];
                        AddCellularState( state1, state2, alpha );
                    }
        //wifi
        if(wifiAutomata.Alpha!=null)
            for (int i = 0; i < wifiAutomata.Transition.length ; i++)
                for (int j = 0; j < wifiAutomata.Transition[i].length ; j++)
                    if(!wifiAutomata.Transition[i][j].equals( "null" )) {
                        String state1 = wifiAutomata.States[i].name;
                        String state2 = wifiAutomata.Transition[i][j];
                        String alpha = wifiAutomata.Alpha[j];
                        AddWifiState( state1, state2, alpha );
                    }
    }

    private  void AddAudioState(String state1,String state2,String alpha)
    {
        int idAlpha=getAlphaIdByName( alpha );
        for (int i = 0; i < States.length ; i++)
            for (int j = 0; j <  States.length ; j++)
                if((States[i].audio.equals( state1))&&(States[j].audio.equals( state2))&&CheckTheSameProperties( i,j,"audio" )) Transition[i][idAlpha]=States[j].name;
    }
    private  void AddLCDState(String state1,String state2,String alpha)
    {
        int idAlpha=getAlphaIdByName( alpha );
        for (int i = 0; i < States.length ; i++)
            for (int j = 0; j <  States.length ; j++)
                if((States[i].LCD.equals( state1))&&(States[j].LCD.equals( state2))&&CheckTheSameProperties( i,j,"LCD")) Transition[i][idAlpha]=States[j].name;
    }
    private  void AddGPSState(String state1,String state2,String alpha)
    {
        int idAlpha=getAlphaIdByName( alpha );
        for (int i = 0; i < States.length ; i++)
            for (int j = 0; j <  States.length ; j++)
                if((States[i].GPS.equals( state1))&&(States[j].GPS.equals( state2))&&CheckTheSameProperties( i,j,"GPS")) Transition[i][idAlpha]=States[j].name;
    }
    private  void AddCellularState(String state1,String state2,String alpha)
    {
        int idAlpha=getAlphaIdByName( alpha );
        for (int i = 0; i < States.length ; i++)
            for (int j = 0; j <  States.length ; j++)
                if((States[i].cellular.equals( state1))&&(States[j].cellular.equals( state2))&&CheckTheSameProperties( i,j,"cellular")) Transition[i][idAlpha]=States[j].name;
    }
    private  void AddWifiState(String state1,String state2,String alpha)
    {
        int idAlpha=getAlphaIdByName( alpha );
        for (int i = 0; i < States.length ; i++)
            for (int j = 0; j <  States.length ; j++)
                if((States[i].wifi.equals( state1))&&(States[j].wifi.equals( state2))&&CheckTheSameProperties( i,j,"wifi")) Transition[i][idAlpha]=States[j].name;
    }
    private  boolean CheckTheSameProperties(int i,int j,String option)
    {
        switch (option)
        {
            case "audio":
                if((States[i].LCD==States[j].LCD)&&(States[i].GPS==States[j].GPS)
                        &&(States[i].cellular==States[j].cellular)&&(States[i].wifi==States[j].wifi))  return true;
                break;
            case "LCD":
                if((States[i].audio==States[j].audio)&&(States[i].GPS==States[j].GPS)
                        &&(States[i].cellular==States[j].cellular)&&(States[i].wifi==States[j].wifi))  return true;
                break;
            case "GPS":
                if((States[i].LCD==States[j].LCD)&&(States[i].audio==States[j].audio)
                        &&(States[i].cellular==States[j].cellular)&&(States[i].wifi==States[j].wifi))  return true;
                break;
            case "cellular":
                if((States[i].LCD==States[j].LCD)&&(States[i].GPS==States[j].GPS)
                        &&(States[i].audio==States[j].audio)&&(States[i].wifi==States[j].wifi))  return true;
                break;
            case "wifi":
                if((States[i].LCD==States[j].LCD)&&(States[i].GPS==States[j].GPS)
                        &&(States[i].cellular==States[j].cellular)&&(States[i].audio==States[j].audio))  return true;
                break;
        }

        return false;
    }


}

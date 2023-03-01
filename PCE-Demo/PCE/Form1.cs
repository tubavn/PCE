using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PCE
{
    public partial class Form1 : Form
    {
        static NewAutomat automat;
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            button2.Enabled = true;
            richTextBox2.Text = "Start estimating...\r\n";
            SetupAutomata();
            Estimate();
        }




        private void Estimate()
        {
            // string[] input = {"Display:Open", "DoSomething:100", "3G:TurnOn", "DoSomething:300" };
            string[] input = richTextBox1.Text.Split(Convert.ToChar(10));
      
          
       
            int start = 0;
            int curent = 0;

            double minTotal = 0;
            double maxTotal = 0;
            foreach (string alpha in input)
            {
                if (alpha.Split(':')[0] == "DoSomething")
                {
                    int time = int.Parse(alpha.Split(':')[1]);
                    NewState currentState = automat.States[curent];
                    minTotal += time * (currentState.minCoefficient + 121.46);
                    maxTotal += time * (currentState.maxCoefficient + 121.46);
                    richTextBox2.Text+="State is not changed in " + time + " unit(s) time. Total power consumption: Min = " + minTotal + " | Max =  " + maxTotal+ "\r\n";
                }
                else
                {
                    curent = automat.ChangeState(start, alpha);
                   // MessageBox.Show("|" + start + "|" + curent);
                    if (curent != -1)
                    {
                        richTextBox2.Text+= PrintTrans(start, curent, minTotal, maxTotal)+"\r\n";
                        start = curent;
                    }
                    else
                    {
                        curent = start;
                        richTextBox2.Text +="Can't change to this State. Total power consumption: Min = " + minTotal + " | Max =  " + maxTotal+ "\r\n";
                    }
                }
            }


        }

        private string PrintTrans(int start, int curent, double minTotal, double maxTotal)
        {
            if (start != curent)
            {
                NewState startState = automat.States[start];
                NewState currentState = automat.States[curent];
                string output = "" + startState.Name + " => " + currentState.Name + ": (Audio:" + currentState.Audio.Name + " | Display: " + currentState.LCD.Name
                    + " | GPS: " + currentState.GPS.Name + " | 3G: " + currentState.Cellular.Name + " | Wifi: " + currentState.Wifi.Name
                    + ") - Total power consumption: Min = " + minTotal + " | Max =  " + maxTotal + "";
                return output;
            }
            return "null";
        }

        private void SetupAutomata()
        {
            Automata LCD = LCDAutomat();
            Automata GPS = GPSAutomat();
            Automata Audio = AudioAutomat();
            Automata Cellular = CellularAutomat();
            Automata Wifi = WifiAutomat();
            automat = new NewAutomat(LCD, Wifi, Cellular, Audio, GPS);
        }

        private static AutomataState[] GetStatesFromStringArray(string[] input)
        {
            AutomataState[] states = new AutomataState[input.Length];
            for (int i = 0; i < input.Length; i++)
            {
                states[i] = new AutomataState();
                states[i].Name = input[i].Split(':')[0].ToString();
                states[i].minCoefficient = double.Parse(input[i].Split(':')[1]);
                states[i].maxCoefficient = double.Parse(input[i].Split(':')[2]);
            }
            return states;
        }

        private Automata AudioAutomat()
        {
            string[] newStates = { "off:0:0", "on:384.62:384.62" };
            string[] newAlpha = { "Audio:Start", "Audio:Stop" };
            Automata automat = new Automata();
            automat.States = GetStatesFromStringArray(newStates);
            automat.Alphabets = newAlpha;
            automat.AddTrans(0, 0, 1);
            automat.AddTrans(1, 1, 0);
            return automat;
        }
        private Automata LCDAutomat()
        {
            string[] newStates = { "off:0:0", "on:2.40:612" };
            string[] newAlpha = { "Display:Lock", "Display:Open" };
            Automata automat = new Automata();
            automat.States = GetStatesFromStringArray(newStates);
            automat.Alphabets = newAlpha;
            automat.AddTrans(0, 1, 1);
            automat.AddTrans(1, 0, 0);
            return automat;
        }
        private Automata GPSAutomat()
        {
            string[] newStates = { "off:0:0", "sleep:173.55:173.55", "active:429.55:429.55" };
            string[] newAlpha = { "GPS:TurnOn", "GPS:TurnOff", "GPS:GetLocation", "GPS:Stop" };
            Automata automat = new Automata();
            automat.States = GetStatesFromStringArray(newStates);
            automat.Alphabets = newAlpha;
            automat.AddTrans(0, 0, 1);
            automat.AddTrans(1, 1, 0);
            automat.AddTrans(1, 2, 2);
            automat.AddTrans(2, 3, 1);
            automat.AddTrans(2, 1, 0);
            return automat;
        }
        private Automata CellularAutomat()
        {
            string[] newStates = { "off:0:0", "idle:10:10", "transmit:401:570" };
            string[] newAlpha = { "3G:TurnOn", "3G:TurnOff", "3G:Transfer", "3G:Stop" };
            Automata automat = new Automata();
            automat.States = GetStatesFromStringArray(newStates);
            automat.Alphabets = newAlpha;
            automat.AddTrans(0, 0, 1);
            automat.AddTrans(1, 1, 0);
            automat.AddTrans(1, 2, 2);
            automat.AddTrans(2, 3, 1);
            automat.AddTrans(2, 1, 0);
            return automat;
        }
        private Automata WifiAutomat()
        {
            string[] newStates = { "off:0:0", "low-power:20:20", "hight-power:710:758" };
            string[] newAlpha = { "Wifi:TurnOn", "Wifi:TurnOff", "Wifi:Transfer", "Wifi:Stop" };
            Automata automat = new Automata();
            automat.States = GetStatesFromStringArray(newStates);
            automat.Alphabets = newAlpha;
            automat.AddTrans(0, 0, 1);
            automat.AddTrans(1, 1, 0);
            automat.AddTrans(1, 2, 2);
            automat.AddTrans(2, 3, 1);
            automat.AddTrans(2, 1, 0);
            return automat;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            automat.ShowTransitTable();
        }
    }
}

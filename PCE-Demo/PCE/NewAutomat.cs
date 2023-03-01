using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PCE
{
    class NewAutomat
    {
        public NewState[] States;
        public string[] Alphabets;
        public Transit[] Trans;

        private Automata LCD;
        private Automata Wifi;
        private Automata Cellular;
        private Automata Audio;
        private Automata GPS;

        public NewAutomat(Automata lCD, Automata wifi, Automata cellular, Automata audio, Automata gPS)
        {
            LCD = lCD;
            Wifi = wifi;
            Cellular = cellular;
            Audio = audio;
            GPS = gPS;
            States = new NewState[LCD.States.Length * Wifi.States.Length * Cellular.States.Length * Audio.States.Length * GPS.States.Length];
            Alphabets = new string[LCD.Alphabets.Length + Wifi.Alphabets.Length + Cellular.Alphabets.Length + Audio.Alphabets.Length + GPS.Alphabets.Length];

            GetNewState();
            GetNewAlpha();
            GetNewTrans();
          //  ShowTransitTable();
        }

        public int ChangeState(int start, string alpha)
        {
            int alphaId = GetAlphaId(alpha);
           
            foreach (Transit t in Trans)
            {
                if (t != null)
                {
                    if ((t.IdState1 == start) && (t.IdAlphabet == alphaId))
                    {
                        return t.IdState2;
                    }
                }
            }
            return -1;
        }

        public void GetNewState()
        {
            int count = 0;
            foreach (AutomataState lcd in LCD.States)
                foreach (AutomataState wifi in Wifi.States)
                    foreach (AutomataState cellular in Cellular.States)
                        foreach (AutomataState audio in Audio.States)
                            foreach (AutomataState gps in GPS.States)
                            {
                                States[count] = new NewState();
                                States[count].Name = "State " + count;
                                States[count].FullName = "Audio: " + audio.Name + " | Display: " + lcd.Name + " | GPS: " + gps.Name + " | 3G: " + cellular.Name + " | Wifi: " + wifi.Name;
                                States[count].LCD = lcd;
                                States[count].Cellular = cellular;
                                States[count].Wifi = wifi;
                                States[count].GPS = gps;
                                States[count].Audio = audio;
                                States[count].minCoefficient = 0;
                                States[count].minCoefficient += lcd.minCoefficient + cellular.minCoefficient + wifi.minCoefficient + gps.minCoefficient + audio.minCoefficient;
                                States[count].maxCoefficient = 0;
                                States[count].maxCoefficient += lcd.maxCoefficient + cellular.maxCoefficient + wifi.maxCoefficient + gps.maxCoefficient + audio.maxCoefficient;
                                count++;
                            }
        }
        public void GetNewAlpha()
        {
            int count = 0;
            foreach (string lcd in LCD.Alphabets)
            {
                Alphabets[count++] = lcd;
            }

            foreach (string wifi in Wifi.Alphabets)
            {
                Alphabets[count++] = wifi;
            }
            foreach (string cellular in Cellular.Alphabets)
            {
                Alphabets[count++] = cellular;
            }
            foreach (string audio in Audio.Alphabets)
            {
                Alphabets[count++] = audio;
            }
            foreach (string gps in GPS.Alphabets)
            {
                Alphabets[count++] = gps;
            }
        }

        public void AddTrans(int state1, int alpha, int state2)
        {
            if (Trans == null) Trans = new Transit[States.Length * Alphabets.Length];
            int count = 0;
            while (Trans[count] != null) count++;
            Trans[count] = new Transit(state1, alpha, state2);
        }
        public void LCDNewTran()
        {
            foreach (Transit trans in LCD.Trans)
            {
                if (trans != null)
                {
                    AutomataState state1 = LCD.States[trans.IdState1];
                    AutomataState state2 = LCD.States[trans.IdState2];
                    string alpha = LCD.Alphabets[trans.IdAlphabet];

                    for (int i = 0; i < States.Length; i++)
                        for (int j = 0; j < States.Length; j++)
                            if ((i != j) && (States[i].LCD == state1) && (States[j].LCD == state2))
                            {
                                if ((States[i].Wifi == States[j].Wifi) && (States[i].Cellular == States[j].Cellular) && (States[i].Audio == States[j].Audio) && (States[i].GPS == States[j].GPS))
                                {
                                    AddTrans(i, GetAlphaId(alpha), j);
                                }
                            }
                }
                else break;
            }
        }
        public void WifiNewTran()
        {
            foreach (Transit trans in Wifi.Trans)
            {
                if (trans != null)
                {
                    AutomataState state1 = Wifi.States[trans.IdState1];
                    AutomataState state2 = Wifi.States[trans.IdState2];
                    string alpha = Wifi.Alphabets[trans.IdAlphabet];

                    for (int i = 0; i < States.Length; i++)
                        for (int j = 0; j < States.Length; j++)
                            if ((i != j) && (States[i].Wifi == state1) && (States[j].Wifi == state2))
                            {
                                if ((States[i].LCD == States[j].LCD) && (States[i].Cellular == States[j].Cellular) && (States[i].Audio == States[j].Audio) && (States[i].GPS == States[j].GPS))
                                {
                                    AddTrans(i, GetAlphaId(alpha), j);
                                }
                            }
                }
                else break;
            }
        }
        public void CelluarNewTran()
        {
            foreach (Transit trans in Cellular.Trans)
            {
                if (trans != null)
                {
                    AutomataState state1 = Cellular.States[trans.IdState1];
                    AutomataState state2 = Cellular.States[trans.IdState2];
                    string alpha = Cellular.Alphabets[trans.IdAlphabet];

                    for (int i = 0; i < States.Length; i++)
                        for (int j = 0; j < States.Length; j++)
                            if ((i != j) && (States[i].Cellular == state1) && (States[j].Cellular == state2))
                            {
                                if ((States[i].Wifi == States[j].Wifi) && (States[i].LCD == States[j].LCD) && (States[i].Audio == States[j].Audio) && (States[i].GPS == States[j].GPS))
                                {
                                    AddTrans(i, GetAlphaId(alpha), j);
                                }
                            }
                }
                else break;
            }
        }
        public void AudioNewTran()
        {
            foreach (Transit trans in Audio.Trans)
            {
                if (trans != null)
                {
                    AutomataState state1 = Audio.States[trans.IdState1];
                    AutomataState state2 = Audio.States[trans.IdState2];
                    string alpha = Audio.Alphabets[trans.IdAlphabet];

                    for (int i = 0; i < States.Length; i++)
                        for (int j = 0; j < States.Length; j++)
                            if ((i != j) && (States[i].Audio == state1) && (States[j].Audio == state2))
                            {
                                if ((States[i].Wifi == States[j].Wifi) && (States[i].Cellular == States[j].Cellular) && (States[i].LCD == States[j].LCD) && (States[i].GPS == States[j].GPS))
                                {
                                    AddTrans(i, GetAlphaId(alpha), j);
                                }
                            }
                }
                else break;
            }
        }
        public void GPSNewTran()
        {
            foreach (Transit trans in GPS.Trans)
            {
                if (trans != null)
                {
                    AutomataState state1 = GPS.States[trans.IdState1];
                    AutomataState state2 = GPS.States[trans.IdState2];
                    string alpha = GPS.Alphabets[trans.IdAlphabet];

                    for (int i = 0; i < States.Length; i++)
                        for (int j = 0; j < States.Length; j++)
                            if ((i != j) && (States[i].GPS == state1) && (States[j].GPS == state2))
                            {
                                if ((States[i].Wifi == States[j].Wifi) && (States[i].Cellular == States[j].Cellular) && (States[i].Audio == States[j].Audio) && (States[i].LCD == States[j].LCD))
                                {
                                    AddTrans(i, GetAlphaId(alpha), j);
                                }
                            }
                }
                else break;
            }
        }

        public void GetNewTrans()
        {
            LCDNewTran();
            WifiNewTran();
            CelluarNewTran();
            AudioNewTran();
            GPSNewTran();
        }

        public int GetAlphaId(string alpha)
        {
            for (int i = 0; i < Alphabets.Length; i++)
                if (Alphabets[i] == alpha) return i;
            return -1;
        }

        public void ShowTransitTable()
        {
            int sleep = 300;

            Microsoft.Office.Interop.Excel.Application oXL;
            Microsoft.Office.Interop.Excel._Workbook oWB;
            Microsoft.Office.Interop.Excel._Worksheet oSheet;
            //  Microsoft.Office.Interop.Excel.Range oRng;
            object misvalue = System.Reflection.Missing.Value;
            try
            {
                //Start Excel and get Application object.
                oXL = new Microsoft.Office.Interop.Excel.Application();
                oXL.Visible = true;

                Console.WriteLine("To be sure that your Exel application is read!");
                Console.ReadLine();
                ////Get a new workbook.
                oWB = (Microsoft.Office.Interop.Excel._Workbook)(oXL.Workbooks.Add(""));
                oSheet = (Microsoft.Office.Interop.Excel._Worksheet)oWB.ActiveSheet;


                //add row
                oSheet.Cells[1, 2] = "State/Input";
                int count = 1;
                foreach (NewState state in States)
                {
                    count++;
                    oSheet.Cells[count, 1] = state.FullName;
                    oSheet.Cells[count, 2] = state.Name + "(" + state.minCoefficient + "-"+state.maxCoefficient+")";
                    System.Threading.Thread.Sleep(sleep - 300);
                }

                //add header
                count = 1;
                foreach (string input in Alphabets)
                {
                    count++;
                    oSheet.Cells[1, count+1] = input;
                    System.Threading.Thread.Sleep(sleep - 300);
                }
                //add cell
                foreach (Transit tran in Trans)
                {   
                    if (tran == null) break;
                    oSheet.Cells[tran.IdState1 + 2, tran.IdAlphabet + 3] = States[tran.IdState2].Name;
                    System.Threading.Thread.Sleep(sleep-290);
                }

                

                //oSheet.Cells[x, y] = data;

                //
                //  oXL.Visible = false;
           //     oXL.UserControl = false;
                //  oWB.SaveAs(@"d:\test\test.xls", Microsoft.Office.Interop.Excel.XlFileFormat.xlWorkbookDefault, Type.Missing, Type.Missing,
                //      false, false, Microsoft.Office.Interop.Excel.XlSaveAsAccessMode.xlNoChange,
                //      Type.Missing, Type.Missing, Type.Missing, Type.Missing, Type.Missing);
                //  oWB.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Loi:" + e.Message);
            }
        }
    }
}

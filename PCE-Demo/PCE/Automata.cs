using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PCE
{
    class Automata
    {
        public AutomataState[] States;
        public string[] Alphabets;
        public Transit[] Trans;
     

        public Automata()
        {
        }


        public void AddTrans(int state1, int alpha, int state2)
        {
            if (Trans == null) Trans = new Transit[States.Length * Alphabets.Length];
            int count = 0;
            while (Trans[count] != null) count++;
            Trans[count] = new Transit(state1,alpha,state2);
        }

        public void ShowAllTrans()
        {
            int count = 0;
            while (Trans[count] != null)
            {
                System.Console.WriteLine(Trans[count].IdState1+"   "+Trans[count].IdAlphabet+"   "+Trans[count].IdState2+"   ");
                count++;
            }
        }
        public void ShowStates()
        {
            Microsoft.Office.Interop.Excel.Application oXL;
            Microsoft.Office.Interop.Excel._Workbook oWB;
            Microsoft.Office.Interop.Excel._Worksheet oSheet;
            object misvalue = System.Reflection.Missing.Value;
            try
            {
                //Start Excel and get Application object.
                oXL = new Microsoft.Office.Interop.Excel.Application();
                oXL.Visible = true;

                //Get a new workbook.
                oWB = (Microsoft.Office.Interop.Excel._Workbook)(oXL.Workbooks.Add(""));
                oSheet = (Microsoft.Office.Interop.Excel._Worksheet)oWB.ActiveSheet;

                //Add table headers going cell by cell.

                int count = 0;
                foreach (AutomataState state in States)
                {
                    count++;
                    oSheet.Cells[count, 1] = state.Name;
                    System.Threading.Thread.Sleep(300);
                }

                //oSheet.Cells[x, y] = data;

                //
                //  oXL.Visible = false;
                oXL.UserControl = false;
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

                //Get a new workbook.
                oWB = (Microsoft.Office.Interop.Excel._Workbook)(oXL.Workbooks.Add(""));
                oSheet = (Microsoft.Office.Interop.Excel._Worksheet)oWB.ActiveSheet;


                //add row
                oSheet.Cells[1, 1] = "State/Input";
                int count = 1;
                foreach (AutomataState state in States)
                {
                    count++;
                    oSheet.Cells[count, 1] = state.Name;
                    System.Threading.Thread.Sleep(sleep);
                }

                //add header
                count = 1;
                foreach (string input in Alphabets)
                {
                    count++;
                    oSheet.Cells[1, count] = input;
                    System.Threading.Thread.Sleep(sleep);
                }
                //add cell
                foreach (Transit tran in Trans)
                {
                    if (tran == null) break;

                    oSheet.Cells[tran.IdState1+2, tran.IdAlphabet+2] = States[tran.IdState2];
                    System.Threading.Thread.Sleep(sleep);
                }


                //oSheet.Cells[x, y] = data;

                //
                //  oXL.Visible = false;
                oXL.UserControl = false;
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

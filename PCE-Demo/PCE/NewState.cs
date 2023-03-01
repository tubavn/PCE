using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PCE
{
    class NewState
    {
        public string Name;
        public string FullName;
        public AutomataState LCD;
        public AutomataState Wifi;
        public AutomataState Cellular;
        public AutomataState Audio;
        public AutomataState GPS;
        public double minCoefficient;
        public double maxCoefficient;
    }
}

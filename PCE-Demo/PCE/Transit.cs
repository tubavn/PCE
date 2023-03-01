using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PCE
{
    class Transit
    {
        public int IdState1;
        public int IdState2;
        public int IdAlphabet;

        public Transit(int idState1, int idAlphabet, int idState2)
        {
            IdState1 = idState1;
            IdState2 = idState2;
            IdAlphabet = idAlphabet;
        }
    }
}

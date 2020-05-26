using System;
using System.Collections.Generic;

namespace db.Db
{
    public partial class AcTrigger
    {
        public int TrgUsrId { get; set; }
        public int TrgTrtId { get; set; }
        public string TrgValue { get; set; }
        public byte TrgActivity { get; set; }

        public AcTriggerType TrgTrt { get; set; }
        public AcUser TrgUsr { get; set; }
    }
}

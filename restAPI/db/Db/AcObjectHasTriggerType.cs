using System;
using System.Collections.Generic;

namespace db.Db
{
    public partial class AcObjectHasTriggerType
    {
        public int OhtTrtId { get; set; }
        public int OhtObjId { get; set; }
        public byte OhtActivity { get; set; }

        public AcObject OhtObj { get; set; }
        public AcTriggerType OhtTrt { get; set; }
    }
}

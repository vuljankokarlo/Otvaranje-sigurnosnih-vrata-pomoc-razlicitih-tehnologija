using System;
using System.Collections.Generic;

namespace db.Db
{
    public partial class AcEventLog
    {
        public int EvlId { get; set; }
        public DateTime EvlDate { get; set; }
        public string EvlTrgValue { get; set; }
        public int EvlEvsId { get; set; }
        public int? EvlObjId { get; set; }
        public int? EvlUsrId { get; set; }
        public int EvlTrtId { get; set; }

        public AcEventStatus EvlEvs { get; set; }
        public AcObject EvlObj { get; set; }
        public AcTriggerType EvlTrt { get; set; }
        public AcUser EvlUsr { get; set; }
    }
}

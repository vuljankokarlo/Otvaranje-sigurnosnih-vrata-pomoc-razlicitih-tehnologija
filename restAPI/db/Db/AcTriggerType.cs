using System;
using System.Collections.Generic;

namespace db.Db
{
    public partial class AcTriggerType
    {
        public AcTriggerType()
        {
            AcEventLog = new HashSet<AcEventLog>();
            AcObjectHasTriggerType = new HashSet<AcObjectHasTriggerType>();
            AcTrigger = new HashSet<AcTrigger>();
        }

        public int TrtId { get; set; }
        public string TrtName { get; set; }

        public ICollection<AcEventLog> AcEventLog { get; set; }
        public ICollection<AcObjectHasTriggerType> AcObjectHasTriggerType { get; set; }
        public ICollection<AcTrigger> AcTrigger { get; set; }
    }
}

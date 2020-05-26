using System;
using System.Collections.Generic;

namespace db.Db
{
    public partial class AcEventStatus
    {
        public AcEventStatus()
        {
            AcEventLog = new HashSet<AcEventLog>();
        }

        public int EvsId { get; set; }
        public string EvsName { get; set; }
        public string EvsDescription { get; set; }

        public ICollection<AcEventLog> AcEventLog { get; set; }
    }
}

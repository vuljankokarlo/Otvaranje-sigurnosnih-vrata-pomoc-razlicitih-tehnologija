using System;
using System.Collections.Generic;

namespace db.Db
{
    public partial class AcDay
    {
        public AcDay()
        {
            AcSchedule = new HashSet<AcSchedule>();
        }

        public int DayId { get; set; }
        public string DayName { get; set; }

        public ICollection<AcSchedule> AcSchedule { get; set; }
    }
}

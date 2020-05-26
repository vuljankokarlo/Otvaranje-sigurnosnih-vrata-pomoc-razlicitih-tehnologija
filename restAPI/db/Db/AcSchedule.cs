using System;
using System.Collections.Generic;

namespace db.Db
{
    public partial class AcSchedule
    {
        public int SchProId { get; set; }
        public int SchDayId { get; set; }
        public TimeSpan SchTimeFrom { get; set; }
        public TimeSpan SchTimeTo { get; set; }

        public AcDay SchDay { get; set; }
        public AcProfil SchPro { get; set; }
    }
}

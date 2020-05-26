using System;
using System.Collections.Generic;

namespace db.Db
{
    public partial class AcProfil
    {
        public AcProfil()
        {
            AcAccess = new HashSet<AcAccess>();
            AcDate = new HashSet<AcDate>();
            AcSchedule = new HashSet<AcSchedule>();
        }

        public int ProId { get; set; }
        public string ProName { get; set; }
        public byte ProActivity { get; set; }

        public ICollection<AcAccess> AcAccess { get; set; }
        public ICollection<AcDate> AcDate { get; set; }
        public ICollection<AcSchedule> AcSchedule { get; set; }
    }
}

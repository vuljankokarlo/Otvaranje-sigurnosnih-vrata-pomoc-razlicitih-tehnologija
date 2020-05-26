using System;
using System.Collections.Generic;

namespace db.Db
{
    public partial class AcSystemLog
    {
        public int SysId { get; set; }
        public int SysUsrId { get; set; }
        public string SysAction { get; set; }
        public string SysText { get; set; }
    }
}

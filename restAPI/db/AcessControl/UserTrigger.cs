using System;
using System.Collections.Generic;
using System.Text;

namespace db.AcessControl
{
    public class UserTrigger
    {
        public int? UsrId { get; set; }
        public int TrgtId { get; set; }
        public byte? TrgActivity { get; set; }
        public byte? UsrActivity { get; set; }
    }
}

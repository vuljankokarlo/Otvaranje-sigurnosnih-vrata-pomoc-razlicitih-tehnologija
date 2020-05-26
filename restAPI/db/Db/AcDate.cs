using System;
using System.Collections.Generic;

namespace db.Db
{
    public partial class AcDate
    {
        public int DatId { get; set; }
        public DateTime DatDateFrom { get; set; }
        public DateTime DatDateTo { get; set; }
        public byte DatEnabled { get; set; }
        public int DatProId { get; set; }

        public AcProfil DatPro { get; set; }
    }
}

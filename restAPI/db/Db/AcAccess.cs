using System;
using System.Collections.Generic;

namespace db.Db
{
    public partial class AcAccess
    {
        public int AcsId { get; set; }
        public DateTime AcsValidFrom { get; set; }
        public DateTime AcsValidTo { get; set; }
        public int? AcsOpeningCounter { get; set; }
        public int? AcsUsrId { get; set; }
        public int? AcsProId { get; set; }
        public int? AcsObjId { get; set; }

        public AcObject AcsObj { get; set; }
        public AcProfil AcsPro { get; set; }
        public AcUser AcsUsr { get; set; }
    }
}

using System;
using System.Collections.Generic;

namespace db.Db
{
    public partial class AcObjectType
    {
        public AcObjectType()
        {
            AcObject = new HashSet<AcObject>();
        }

        public int ObtId { get; set; }
        public string ObtName { get; set; }
        public byte? ObtIn { get; set; }
        public byte? ObtOut { get; set; }

        public ICollection<AcObject> AcObject { get; set; }
    }
}

using System;
using System.Collections.Generic;

namespace db.Db
{
    public partial class AcObject
    {
        public AcObject()
        {
            AcAccess = new HashSet<AcAccess>();
            AcEventLog = new HashSet<AcEventLog>();
            AcObjectHasTriggerType = new HashSet<AcObjectHasTriggerType>();
        }

        public int ObjId { get; set; }
        public string ObjName { get; set; }
        public byte? ObjOpen { get; set; }
        public byte? ObjAuto { get; set; }
        public byte ObjActivity { get; set; }
        public string ObjGps { get; set; }
        public string ObjAction { get; set; }
        public int ObjObtTypeId { get; set; }

        public AcObjectType ObjObtType { get; set; }
        public ICollection<AcAccess> AcAccess { get; set; }
        public ICollection<AcEventLog> AcEventLog { get; set; }
        public ICollection<AcObjectHasTriggerType> AcObjectHasTriggerType { get; set; }
    }
}

using System;
using System.Collections.Generic;

namespace db.Db
{
    public partial class AcUser
    {
        public AcUser()
        {
            AcAccess = new HashSet<AcAccess>();
            AcEventLog = new HashSet<AcEventLog>();
            AcTrigger = new HashSet<AcTrigger>();
        }

        public int UsrId { get; set; }
        public string UsrName { get; set; }
        public string UsrSurname { get; set; }
        public string UsrEmail { get; set; }
        public byte UsrActivity { get; set; }
        public byte[] UsrPasswordSalt { get; set; }
        public byte[] UsrCryptedPassword { get; set; }
        public int? UsrRolId { get; set; }

        public AcRole UsrRol { get; set; }
        public ICollection<AcAccess> AcAccess { get; set; }
        public ICollection<AcEventLog> AcEventLog { get; set; }
        public ICollection<AcTrigger> AcTrigger { get; set; }
    }
}

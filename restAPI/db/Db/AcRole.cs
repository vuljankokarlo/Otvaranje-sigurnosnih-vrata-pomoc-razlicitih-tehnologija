using System;
using System.Collections.Generic;

namespace db.Db
{
    public partial class AcRole
    {
        public AcRole()
        {
            AcUser = new HashSet<AcUser>();
        }

        public int RolId { get; set; }
        public string RolName { get; set; }
        public string RolCompany { get; set; }

        public ICollection<AcUser> AcUser { get; set; }
    }
}

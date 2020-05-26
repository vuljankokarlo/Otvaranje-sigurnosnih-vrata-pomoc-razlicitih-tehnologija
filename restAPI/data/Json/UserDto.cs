using System;
using System.Collections.Generic;
using System.Text;

namespace data.Json
{
    public class UserDto
    {
        public int UsrId { get; set; }
        public string UsrName { get; set; }
        public string UsrSurname { get; set; }
        public string UsrEmail { get; set; }
        public byte UsrActivity { get; set; }
        public int UsrRolId { get; set; }
        public bool GenPassword { get; set; }
        public string LoginPassword { get; set; }
        public string PhoneNumber { get; set; }
        public DateTime guestValidFrom { get; set; }
        public DateTime guestValidTo { get; set; }
        public int guestObjId { get; set; } 
    }
}

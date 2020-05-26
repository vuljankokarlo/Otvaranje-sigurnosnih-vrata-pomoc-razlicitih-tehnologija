using System;
using System.Collections.Generic;
using System.Text;

namespace data.Json
{
    public class AccessDto
    {
        public int AcsId { get; set; }
        public DateTime ValidFrom { get; set; }
        public DateTime ValidTo { get; set; }
        public int? Counter { get; set; }
        public int? UsrId { get; set; }
        public int? ProId { get; set; }
        public int? ObjId { get; set; }
    }
}

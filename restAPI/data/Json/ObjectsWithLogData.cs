using System;
using System.Collections.Generic;
using System.Text;

namespace data.Json
{
    public class ObjectsWithLogData
    {
        public int? ObjId { get; set; }
        public string ObjName { get; set; }
        public byte? ObjOpen { get; set; }
        public byte? ObjAuto { get; set; }
        public byte ObjActivity { get; set; }
        public string ObjGps { get; set; }
        public string ObjAction { get; set; }
        public int? ObjObtTypeId { get; set; }
        public int? EventLogId { get; set; }
        public DateTime? Date { get; set; }
        public string TriggerValue { get; set; }
        public string UserName { get; set; }
        public string UserSurname { get; set; }
        public string TriggerName { get; set; }
        public string ObjectName { get; set; }
        public string EventStatusName { get; set; }
    }
}

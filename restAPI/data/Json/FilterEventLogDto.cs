using System;
using System.Collections.Generic;
using System.Text;

namespace data.Json
{
    public class FilterEventLogDto
    {
        public int EventLogId { get; set; }
        public DateTime Date { get; set; }
        public string TriggerValue { get; set; }
        public string UserName { get; set; }
        public string UserSurname { get; set; }
        public string TriggerName { get; set; }
        public string ObjectName { get; set; }
        public string EventStatusName { get; set; }
        public int? ObjectId { get; set; }
    }
}

using db.Db;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace restAPI.Services
{
   public interface ILoggerService
    {
        void InsertEventLog(string value, int trtId, int? objId, int evsId);
        void InsertEventLog(string value, int trtId, int? objId, int evsId, int? usrId);
    }

    public class LoggerService : ILoggerService
    {
        //private static LoggerService _logger;
        //private LoggerService() { }
        private mydbContext _context;

        public LoggerService(mydbContext context)
        {
            _context = context;
        }


       /* public static LoggerService Instance
        {
            get
            {
                if (_logger == null)
                {
                    _logger = new LoggerService();

                }

                return _logger;
            }
        }*/

        public void InsertEventLog(string value, int trtId, int? objId, int evsId)
        {
            AcEventLog eventLog = new AcEventLog
            {
                EvlDate = DateTime.Now,
                EvlTrgValue = value,
                EvlTrtId = trtId,
                EvlObjId = objId,
                EvlEvsId = evsId
            };
            _context.AcEventLog.Add(eventLog);
            _context.SaveChanges();
            
        }
        public void InsertEventLog(string value, int trtId, int? objId, int evsId, int? usrId)
        {

            AcEventLog eventLog = new AcEventLog
            {
                EvlDate = DateTime.Now,
                EvlTrgValue = value,
                EvlTrtId = trtId,
                EvlObjId = objId,
                EvlEvsId = evsId,
                EvlUsrId = usrId
        };
            _context.AcEventLog.Add(eventLog);
            _context.SaveChanges();
            
        }

    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using data.Json;
using db.Db;

namespace restAPI.Services
{
    public interface IEventLogService
    {
        List<FilterEventLogDto> getEventLogs(int? id);
        //List<ObjectsWithLogData> getObjects();
    }

    public class EventLogService : IEventLogService
    {
        private mydbContext _context;

        public EventLogService(mydbContext context)
        {
            _context = context;
        }
        //public List<ObjectsWithLogData> getObjects()
        //{
            
            
                //List<ObjectsWithLogData> Allobjects = (
                //    from evl in _context.AcEventLog
                //    join evs in _context.AcEventStatus on evl.EvlEvsId equals evs.EvsId
                //    join obj1 in _context.AcObject on evl.EvlObjId equals obj1.ObjId into AcObject
                //    from obj in AcObject.DefaultIfEmpty()
                //    join usr1 in _context.AcUser on evl.EvlUsrId equals usr1.UsrId into AcUser
                //    from usr in AcUser.DefaultIfEmpty()
                //    join trt in _context.AcTriggerType on evl.EvlTrtId equals trt.TrtId
                //    where req.ObjId == evl.evlObjId
                //    select new ObjectsWithLogData
                //    {
                //        EventLogId = evl.EvlId,
                //        Date = evl.EvlDate,
                //        TriggerValue = evl.EvlTrgValue,
                //        UserName = usr.UsrName,
                //        UserSurname = usr.UsrSurname,
                //        TriggerName = trt.TrtName,
                //        ObjectName = obj.ObjName,
                //        EventStatusName = evs.EvsName
                //    }
                //).OrderByDescending(x => x.Date).ToList();
            
            

            //return Allobjects;
        //}

        public List<FilterEventLogDto> getEventLogs(int? id)
        {
            List <FilterEventLogDto> eventLogs = (
                from evl in _context.AcEventLog
                join evs in _context.AcEventStatus on evl.EvlEvsId equals evs.EvsId
                join obj1 in _context.AcObject on evl.EvlObjId equals obj1.ObjId into AcObject from obj in AcObject.DefaultIfEmpty()
                join usr1 in _context.AcUser on evl.EvlUsrId equals usr1.UsrId into AcUser from usr in AcUser.DefaultIfEmpty() 
                join trt in _context.AcTriggerType on evl.EvlTrtId equals trt.TrtId
                where id == null || evl.EvlUsrId==id 
                select new FilterEventLogDto
                {
                    EventLogId = evl.EvlId,
                    Date = evl.EvlDate,
                    TriggerValue = evl.EvlTrgValue,
                    UserName = usr.UsrName,
                    UserSurname = usr.UsrSurname,
                    TriggerName = trt.TrtName,
                    ObjectName = obj.ObjName,
                    EventStatusName = evs.EvsName
                }
            ).OrderByDescending(x => x.Date).ToList();

            return eventLogs;
        }

    }
}

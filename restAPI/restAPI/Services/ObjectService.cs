using data.Json;
using db.Db;
using restAPI.Helpers;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;

namespace restAPI.Services
{
    public interface IObjectService
    {
        List<AcObject> getObjects(string type, string objectName);
        List<ObjectsWithLogData> getObjectsLastOpened();
    }

    public class ObjectService : IObjectService
    {
        private mydbContext _context;

        public ObjectService(mydbContext context)
        {
            _context = context;
        }

        public List<AcObject> getObjects(string type, string objectName)
        {
            List<AcObject> sObj = (
                from obj in _context.AcObject
                join ohs in _context.AcObjectHasTriggerType on obj.ObjId equals ohs.OhtObjId
                join trgt in _context.AcTriggerType on ohs.OhtTrtId equals trgt.TrtId
                where trgt.TrtName == type && obj.ObjName == objectName
                select obj
            ).ToList();

            return sObj;
        }

        public List<ObjectsWithLogData> getObjectsLastOpened()
        {
            List<FilterEventLogDto> lastOpened = (from evl in _context.AcEventLog
                             join evs in _context.AcEventStatus on evl.EvlEvsId equals evs.EvsId
                             join obj1 in _context.AcObject on evl.EvlObjId equals obj1.ObjId into AcObject
                             from obj in AcObject.DefaultIfEmpty()
                             join usr1 in _context.AcUser on evl.EvlUsrId equals usr1.UsrId into AcUser
                             from usr in AcUser.DefaultIfEmpty()
                             join trt in _context.AcTriggerType on evl.EvlTrtId equals trt.TrtId
                             where evl.EvlObjId != null && (from log2 in _context.AcEventLog
                                    where log2.EvlObjId == evl.EvlObjId && log2.EvlEvsId == 10
                                    orderby log2.EvlDate descending
                                    select log2.EvlId
                                    ).First().Equals(evl.EvlId)
                             select new FilterEventLogDto
                             {
                                 EventLogId = evl.EvlId,
                                 Date = evl.EvlDate,
                                 TriggerValue = evl.EvlTrgValue,
                                 UserName = usr.UsrName,
                                 UserSurname = usr.UsrSurname,
                                 TriggerName = trt.TrtName,
                                 ObjectName = obj.ObjName,
                                 EventStatusName = evs.EvsName,
                                 ObjectId = evl.EvlObjId
                             }).ToList();
            
           List<ObjectsWithLogData> lstt = (from obj in _context.AcObject
                join lst in lastOpened on obj.ObjId equals lst.ObjectId into lstl
                from v in lstl.DefaultIfEmpty()
                select new ObjectsWithLogData
                {
                    ObjId = obj.ObjId,
                    ObjName = obj.ObjName,
                    ObjOpen = obj.ObjOpen,
                    ObjAuto = obj.ObjAuto,
                    ObjActivity = obj.ObjActivity,
                    ObjGps = obj.ObjGps,
                    ObjAction = obj.ObjAction,
                    ObjObtTypeId = obj.ObjObtTypeId,
                    Date = v.Date,
                    TriggerValue = v.TriggerValue,
                    UserName = v.UserName,
                    UserSurname = v.UserSurname,
                    TriggerName = v.TriggerName,
                    EventStatusName = v.EventStatusName
                }).ToList();
            return lstt;
        }
    }
}

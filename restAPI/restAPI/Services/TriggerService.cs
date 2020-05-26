using db.Db;
using restAPI.Helpers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace restAPI.Services
{
    public interface ITriggerService
    {
        void Create(int userId, string triggerType, string triggerValue, byte trgActivity);
        void Create(int userId, int triggerTypeId, string triggerValue, byte trgActivity);
        void Update(int userId, int triggerTypeId, string triggerValue, byte trgActivity);
        List<AcTrigger> GetByValue(string triggerValue);
        List<AcTrigger> GetByUser(int id);
        void Delete(int id);
    }

    public class TriggerService : ITriggerService
    {
        private mydbContext _context;

        public TriggerService(mydbContext context)
        {
            _context = context;
        }

        public List<AcTrigger> GetByValue(string triggerValue)
        {
            List<AcTrigger> trgs = (
                from trg in _context.AcTrigger
                join trgt in _context.AcTriggerType on trg.TrgTrtId equals trgt.TrtId
                where trg.TrgValue == triggerValue
                select trg
            ).ToList();
            return trgs;
        }

        public List<AcTrigger> GetByUser(int id)
        {
            List<AcTrigger> trgs = (
                from trg in _context.AcTrigger
                join trgt in _context.AcTriggerType on trg.TrgTrtId equals trgt.TrtId
                where trg.TrgUsrId == id
                select trg
            ).ToList();
            return trgs;
        }

        public void Create(int userId, string triggerType, string triggerValue, byte trgActivity)
        {
            _context.AcTrigger.Add(new AcTrigger{
                TrgUsrId = userId,
                TrgTrtId = (from trgt in _context.AcTriggerType where trgt.TrtName == triggerType select trgt.TrtId).Single(),
                TrgValue = triggerValue,
                TrgActivity = trgActivity
            });
            _context.SaveChanges();
        }

        public void Create(int userId, int triggerTypeId, string triggerValue, byte trgActivity)
        {
            AcTrigger trg = new AcTrigger
            {
                TrgUsrId = userId,
                TrgTrtId = triggerTypeId,
                TrgValue = triggerValue,
                TrgActivity = trgActivity
            };
            _context.AcTrigger.Add(trg);
            _context.SaveChanges();
        }

        public void Update(int userId, int triggerTypeId, string triggerValue, byte trgActivity)
        {
            AcTrigger trg = new AcTrigger
            {
                TrgUsrId = userId,
                TrgTrtId = triggerTypeId,
                TrgValue = triggerValue,
                TrgActivity = trgActivity
            };
            _context.AcTrigger.Update(trg);
            _context.SaveChanges();
        }

        public void Delete(int id)
        {
            var trg = _context.AcTrigger.Find(id);
            if (trg != null)
            {
                _context.AcTrigger.Remove(trg);
                _context.SaveChanges();
            }
        }
    }
}

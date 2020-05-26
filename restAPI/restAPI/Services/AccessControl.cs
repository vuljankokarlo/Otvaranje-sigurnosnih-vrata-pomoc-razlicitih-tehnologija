using db.Db;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using restAPI.Controllers;
using db.AcessControl;
using restAPI.Helpers;
using data.Json;

namespace restAPI.Services
{
    public interface IAccessControl
    {
        List<string> objectIO(data.Json.TriggerAccessDto inputs);
        List<string> closeAll(List<TriggerAccessDto> inputs);
    }

    public class AccessControl : IAccessControl
    {
        private mydbContext _context;
        private IUserService _userService;
        private IAccessService _accessService;
        private IObjectService _objectService;
        private ILoggerService _logger;

        public AccessControl(mydbContext context, IUserService userService, IAccessService accessService, IObjectService objectService, ILoggerService logger)
        {
            _context = context;
            _userService = userService;
            _accessService = accessService;
            _objectService = objectService;
            _logger = logger;
        }

        public List<string> closeAll(List<TriggerAccessDto> inputs)
        {
            List<string> objectIOs = new List<string>();
            for (int i = 0; i < inputs.Count; i++)
            {

                UserTrigger usTrg = _userService.getUserByTriggerType(inputs[i].Value, inputs[i].TriggerTypeName);
                List<AcObject> objs = _objectService.getObjects(inputs[i].TriggerTypeName, inputs[i].ObjectName);
                foreach (AcObject obj in objs)
                {
                    _logger.InsertEventLog(inputs[i].Value, usTrg.TrgtId, obj.ObjId, 11, usTrg.UsrId);
                    objectIOs.Add(obj.ObjAction);
                }
            }

            return objectIOs;
        }
        /// <summary>
        /// main logic for access control, and store results in event log. If access is valid then open object.
        /// </summary>
        public List<string> objectIO(data.Json.TriggerAccessDto inputs)
        {
            List<string> objectIOs = new List<string>();
            UserTrigger usTrg = _userService.getUserByTriggerType(inputs.Value, inputs.TriggerTypeName);
            List<AcObject> objs = _objectService.getObjects(inputs.TriggerTypeName, inputs.ObjectName);

            if (usTrg == null)
            {
                throw new AppException("Trigger type not found.");
            }
            else if(usTrg.TrgActivity == null)
            {
                _logger.InsertEventLog(inputs.Value, usTrg.TrgtId, null, 1);
                throw new AppException("Unknown phone number.");
            }
            else if (usTrg.TrgActivity == 0)
            {
                _logger.InsertEventLog(inputs.Value, usTrg.TrgtId, null, 3);
                throw new AppException("Trigger not active.");
            }
            else if (usTrg.UsrActivity == 0)
            {
                _logger.InsertEventLog(inputs.Value, usTrg.TrgtId, null, 4);
                throw new AppException("User not found.");
            }
            else if (objs.Count == 0)
            {
                _logger.InsertEventLog(inputs.Value, usTrg.TrgtId, null, 5);
                throw new AppException("Object not found.");
            }

            foreach(AcObject obj in objs)
            {
                if(obj.ObjActivity == 0)
                {
                    _logger.InsertEventLog(inputs.Value, usTrg.TrgtId, obj.ObjId, 6, usTrg.UsrId);
                    continue;
                }
                List<AcAccess> acs = _accessService.checkAccess(usTrg.UsrId, obj.ObjId);

                if (acs.Count != 0)
                {
                    _logger.InsertEventLog(inputs.Value, usTrg.TrgtId, obj.ObjId, 10, usTrg.UsrId);
                    objectIOs.Add(obj.ObjAction);
                }else
                {
                    _logger.InsertEventLog(inputs.Value, usTrg.TrgtId, obj.ObjId, 7, usTrg.UsrId);
                }
            }
            if(objectIOs.Count == 0)
            {
                throw new AppException("User has no access.");
            }
            return objectIOs;
        }
    }
}

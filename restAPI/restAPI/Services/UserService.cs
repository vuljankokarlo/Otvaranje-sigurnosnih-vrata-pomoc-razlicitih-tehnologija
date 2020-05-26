using db.AcessControl;
using db.Db;
using restAPI.Helpers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace restAPI.Services
{
    public interface IUserService
    {
        AcUser Authenticate(string username, string password);
        IEnumerable<AcUser> GetAll();
        string GetRole(int id);
        AcUser GetById(int id);
        AcUser Create(AcUser user, string password);
        void Update(AcUser user, string password = null);
        void Delete(int id);
        UserTrigger getUserByTriggerType(string value, string type);
    }

    public class UserService : IUserService
    {
        private mydbContext _context;

        public UserService(mydbContext context)
        {
            _context = context;
        }

        public AcUser Authenticate(string email, string password)
        {
            if (string.IsNullOrEmpty(email) || string.IsNullOrEmpty(password))
                return null;
            var userWithRole = (
                from usr in _context.AcUser
                join rol in _context.AcRole on usr.UsrRolId equals rol.RolId
                where usr.UsrEmail == email
                select new
                {
                    User = usr,
                    RolName = rol
                }
            ).SingleOrDefault();
            AcUser user = userWithRole.User;
            user.UsrRol = userWithRole.RolName;

            // check if username exists
            if (user == null)
                return null;

            // check if password is correct
            if (!VerifyPasswordHash(password, user.UsrCryptedPassword, user.UsrPasswordSalt))
                return null;

            // authentication successful
            return user;
        }

        public IEnumerable<AcUser> GetAll()
        {
            return _context.AcUser;
        }

        public AcUser GetById(int id)
        {
            return _context.AcUser.Find(id);
        }

        public string GetRole(int id)
        {
            return (from us in _context.AcUser
                    join rl in _context.AcRole on us.UsrRolId equals rl.RolId
                    select rl.RolName).SingleOrDefault();
        }

        public AcUser Create(AcUser user, string password)
        {
            // validation
            if (string.IsNullOrWhiteSpace(password) && string.IsNullOrWhiteSpace(user.UsrEmail))
                throw new AppException("Password and Email are required");

            Console.WriteLine(user.UsrEmail);

            var order = _context.AcUser.FirstOrDefault(x => x.UsrEmail == user.UsrEmail);
            if(order != null)
            {
                throw new AppException("Email already taken");
            }
            
            if (user.UsrRolId==0)
            {
                user.UsrRolId = (from rol in _context.AcRole where rol.RolName == "Gost" select rol.RolId).SingleOrDefault();
            }

            byte[] passwordHash, passwordSalt;
            CreatePasswordHash(password, out passwordHash, out passwordSalt);
            

            user.UsrCryptedPassword = passwordHash;
            user.UsrPasswordSalt = passwordSalt;

            user.UsrActivity = 1;

            _context.AcUser.Add(user);
            _context.SaveChanges();

            return user;
        }

        public void Update(AcUser userParam, string password = null)
        {
            var user = _context.AcUser.Find(userParam.UsrId);

            if (user == null)
                throw new AppException("User not found");

            if (userParam.UsrEmail != user.UsrEmail)
            {
                // username has changed so check if the new username is already taken
                //if (_context.AcUser.Any(x => x.UsrEmail == userParam.UsrEmail))
                if ((from usr in _context.AcUser where usr.UsrEmail == userParam.UsrEmail select usr).SingleOrDefault() != null)
                    throw new AppException("Username " + userParam.UsrEmail + " is already taken");
            }

            // update user properties
            user.UsrName = userParam.UsrName;
            user.UsrSurname = userParam.UsrSurname;
            user.UsrEmail = userParam.UsrEmail;

            // update password if it was entered
            if (!string.IsNullOrWhiteSpace(password))
            {
                byte[] passwordHash, passwordSalt;
                CreatePasswordHash(password, out passwordHash, out passwordSalt);

                user.UsrCryptedPassword = passwordHash;
                user.UsrPasswordSalt = passwordSalt;
            }

            _context.AcUser.Update(user);
            _context.SaveChanges();
        }

        public void Delete(int id)
        {
            var user = _context.AcUser.Find(id);
            if (user != null)
            {
                _context.AcUser.Remove(user);
                _context.SaveChanges();
            }
        }

        // private helper methods

        private static void CreatePasswordHash(string password, out byte[] passwordHash, out byte[] passwordSalt)
        {
            if (password == null) throw new ArgumentNullException("password");
            if (string.IsNullOrWhiteSpace(password)) throw new ArgumentException("Value cannot be empty or whitespace only string.", "password");

            using (var hmac = new System.Security.Cryptography.HMACSHA256())
            { 
                passwordSalt = hmac.Key;
                passwordHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));
            }
        }

        private static bool VerifyPasswordHash(string password, byte[] storedHash, byte[] storedSalt)
        {
            if (password == null) throw new ArgumentNullException("password");
            if (string.IsNullOrWhiteSpace(password)) throw new ArgumentException("Value cannot be empty or whitespace only string.", "password");
            if (storedHash.Length != 32) throw new ArgumentException("Invalid length of password hash (64 bytes expected).", "passwordHash");
            if (storedSalt.Length != 64) throw new ArgumentException("Invalid length of password salt (128 bytes expected).", "passwordHash");

            using (var hmac = new System.Security.Cryptography.HMACSHA256(storedSalt))
            {
                var computedHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));
                for (int i = 0; i < computedHash.Length; i++)
                {
                    if (computedHash[i] != storedHash[i]) return false;
                }
            }

            return true;
        }

        public UserTrigger getUserByTriggerType(string value, string type)
        {
            UserTrigger userTrigger = (
                from trgt in _context.AcTriggerType
                join trg in _context.AcTrigger on new { Key1 = trgt.TrtId, Key2= value } equals new { Key1 = trg.TrgTrtId, Key2 = trg.TrgValue } into AcTrigger
                from q in AcTrigger.DefaultIfEmpty() join us in _context.AcUser on q.TrgUsrId equals us.UsrId into AcUser
                from l in AcUser.DefaultIfEmpty()
                where trgt.TrtName == type
                select new UserTrigger
                {
                    TrgtId = trgt.TrtId,
                    UsrId = q.TrgUsrId,
                    TrgActivity = q.TrgActivity,
                    UsrActivity = l.UsrActivity
                }
            ).SingleOrDefault();
            return userTrigger;
        }
    }
}

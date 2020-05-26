using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using db.Db;
using Microsoft.AspNetCore.Authorization;

namespace restAPI.Controllers
{
    [Route("api/[controller]")]
    [Authorize]
    [ApiController]
    public class RolesController : ControllerBase
    {
        private readonly mydbContext _context;

        public RolesController(mydbContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Get all roles
        /// </summary>
        /// <returns></returns>
        // GET: api/AcRoles
        [AllowAnonymous]
        [HttpGet]
        public IEnumerable<AcRole> GetAcRole()
        {
            return _context.AcRole;
        }

        /// <summary>
        /// Get role by id
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        // GET: api/AcRoles/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetAcRole([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var acRole = await _context.AcRole.FindAsync(id);

            if (acRole == null)
            {
                return NotFound();
            }

            return Ok(acRole);
        }

        /// <summary>
        /// Update role
        /// </summary>
        /// <param name="id"></param>
        /// <param name="acRole"></param>
        /// <returns></returns>
        // PUT: api/AcRoles/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutAcRole([FromRoute] int id, [FromBody] AcRole acRole)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != acRole.RolId)
            {
                return BadRequest();
            }

            _context.Entry(acRole).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!AcRoleExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        /// <summary>
        /// Create role
        /// </summary>
        /// <param name="acRole"></param>
        /// <returns></returns>
        // POST: api/AcRoles
        [HttpPost]
        public async Task<IActionResult> PostAcRole([FromBody] AcRole acRole)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.AcRole.Add(acRole);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetAcRole", new { id = acRole.RolId }, acRole);
        }

        /// <summary>
        /// Delete role
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        // DELETE: api/AcRoles/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteAcRole([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var acRole = await _context.AcRole.FindAsync(id);
            if (acRole == null)
            {
                return NotFound();
            }

            _context.AcRole.Remove(acRole);
            await _context.SaveChangesAsync();

            return Ok(acRole);
        }

        private bool AcRoleExists(int id)
        {
            return _context.AcRole.Any(e => e.RolId == id);
        }
    }
}
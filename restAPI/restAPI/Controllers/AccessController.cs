using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AutoMapper;
using data.Json;
using db.Db;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using restAPI.Helpers;
using restAPI.Services;

namespace restAPI.Controllers
{
    [Route("api/[controller]")]
    [Authorize]
    [ApiController]
    public class AccessController : ControllerBase
    {
        private readonly mydbContext _context;
        private IAccessService _accessService;
        private IMapper _mapper;
        private IUserService _userService;
        private readonly AppSettings _appSettings;
        private IMailService _mailService;

        public AccessController(
            IMapper mapper,
            mydbContext context,
            IAccessService accessService,
            IUserService userService,
            IOptions<AppSettings> appSettings,
            IMailService mailService)
        {
            _context = context;
            _mapper = mapper;
            _userService = userService;
            _accessService = accessService;
            _appSettings = appSettings.Value;
            _mailService = mailService;
        }

        /// <summary>
        /// Get accesses for user.
        /// </summary>
        /// <param name="id"></param>
        /// <response code="400">return error message if there was an exception</response>  
        [HttpGet]
        public IActionResult Get(int id)
        {
            return Ok(_accessService.GetByUser(id));
        }

        /// <summary>
        /// Create Access for user.
        /// </summary>
        /// <param name="trgDto"></param>
        /// <response code="400">return error message if there was an exception</response>  
        [HttpPost]
        public IActionResult Create([FromBody]AccessDto trgDto)
        {
            try
            {
                // if admin
                AcAccess acs = _accessService.Create(trgDto);
                return Ok(acs.AcsId);
            }
            catch (AppException ex)
            {
                // return error message if there was an exception
                return BadRequest(new { message = ex.Message });
            }
        }

        /// <summary>
        /// Update Access
        /// </summary>
        /// <param name="acs"></param>
        /// <returns></returns>
        [HttpPost("update")]
        public IActionResult Update([FromBody]AccessDto acs)
        {
            try
            {
                // save 
                AcAccess acsNew = _accessService.Update(acs);
                return Ok(acsNew.AcsId);
            }
            catch (AppException ex)
            {
                // return error message if there was an exception
                return BadRequest(new { message = ex.Message });
            }
        }

        /// <summary>
        /// Deletes a specific Access.
        /// </summary>
        /// <param name="id"></param>  
        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            try
            {
                _accessService.Delete(id);
                return Ok();
            }
            catch (AppException ex)
            {
                // return error message if there was an exception
                return BadRequest(new { message = ex.Message });
            }
        }
    }
}
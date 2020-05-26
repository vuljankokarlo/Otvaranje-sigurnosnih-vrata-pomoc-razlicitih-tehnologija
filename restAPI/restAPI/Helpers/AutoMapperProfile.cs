using AutoMapper;
using db.Db;
using data.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace restAPI.Helpers
{
    public class AutoMapperProfile : Profile
    {
        public AutoMapperProfile()
        {
            CreateMap<AcUser, UserDto>();
            CreateMap<UserDto, AcUser>();

        }
    }
}

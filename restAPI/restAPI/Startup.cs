using System.Text;
using System.Threading.Tasks;
using AutoMapper;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Newtonsoft.Json;
using Microsoft.IdentityModel.Tokens;
using restAPI.Helpers;
using restAPI.Services;
using db.Db;
using Microsoft.EntityFrameworkCore;
using System.Security.Claims;
using Swashbuckle.AspNetCore.Swagger;
using System.Reflection;
using System.IO;
using System;
using System.Collections.Generic;

namespace restAPI
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddAutoMapper();

            var connection = "server=192.168.0.1;port=3306;user=root;password=test123;database=mydb";
            services.AddDbContext<mydbContext>(options => options.UseMySQL(connection));
            services.AddMvc(options =>
            {
                options.RespectBrowserAcceptHeader = true; // false by default
            }).AddJsonOptions(options => {
                options.SerializerSettings.ReferenceLoopHandling = ReferenceLoopHandling.Ignore;
            }).SetCompatibilityVersion(CompatibilityVersion.Version_2_1);

            services.AddCors(options =>
            {
                options.AddPolicy("AllowAll",
                    builder =>
                    {
                        builder
                        .AllowAnyOrigin()
                        .AllowAnyMethod()
                        .AllowAnyHeader()
                        .AllowCredentials();
                    });
            });

            services.AddOptions();
            var appSettingsSection = Configuration.GetSection("AppSettings");
            services.Configure<AppSettings>(appSettingsSection);

            var appSettings = appSettingsSection.Get<AppSettings>();
            var key = Encoding.ASCII.GetBytes(appSettings.Secret);
            services.AddAuthentication(x =>
            {
                x.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
                x.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
            })
            .AddJwtBearer(x =>
            {
                x.Events = new JwtBearerEvents
                {
                    OnTokenValidated = context =>
                    {
                        var userService = context.HttpContext.RequestServices.GetRequiredService<IUserService>();
                        var userId = int.Parse(context.Principal.Identity.Name);
                        var user = userService.GetById(userId);
                        if (user == null)
                        {
                            // return unauthorized if user no longer exists
                            context.Fail("Unauthorized");
                        }
                        ClaimsIdentity identity = context.Principal.Identity as ClaimsIdentity;
                        identity.AddClaim(new Claim("current_user_id", context.Principal.Identity.Name));
                        return Task.CompletedTask;
                    }
                };
                x.RequireHttpsMetadata = false;
                x.SaveToken = true;
                x.TokenValidationParameters = new TokenValidationParameters
                {
                    ValidateIssuerSigningKey = true,
                    IssuerSigningKey = new SymmetricSecurityKey(key),
                    ValidateIssuer = false,
                    ValidateAudience = false
                };
            });
            services.AddScoped<IAccessControl, AccessControl>();
            services.AddScoped<IEventLogService, EventLogService>();
            services.AddScoped<IUserService, UserService>();
            services.AddScoped<IObjectService, ObjectService>();
            services.AddScoped<IAccessService, AccessService>();
            services.AddScoped<ILoggerService, LoggerService>();
            services.AddScoped<ITriggerService, TriggerService>();
            services.AddScoped<IMailService, MailService>();

            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new Info {
                    Version = "v1",
                    Title = "TDCEapi",
                    Description = "API for TDCE Ramp",
                    TermsOfService = "None",
                    License = new License
                    {
                        Name = "Mobilisis",
                        Url = "https://mobilisis.hr"
                    }
                });
                var xmlFile = $"{Assembly.GetExecutingAssembly().GetName().Name}.XML";
                var xmlPath = Path.Combine(AppContext.BaseDirectory, xmlFile);
                c.IncludeXmlComments(xmlPath);
            });
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            app.UseSwagger();
            app.UseSwaggerUI(c =>
            {
                c.SwaggerEndpoint("/swagger/v1/swagger.json", "TDCEapi");
            });

            //app.UseHsts();
            //app.UseHttpsRedirection();
            app.UseCors();
            app.UseAuthentication();
            app.UseMvc();
        }
    }
}

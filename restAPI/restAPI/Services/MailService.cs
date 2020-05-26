using Microsoft.Extensions.Options;
using Newtonsoft.Json;
using restAPI.Helpers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Mail;
using System.Text;
using System.Threading.Tasks;

namespace restAPI.Services
{
    public interface IMailService
    {
        int Send(string reciever, string body, string subject);
        int SendSMS(string number, string body);
    }

    public class MailService : IMailService
    {
        private readonly AppSettings _appSettings;

        public MailService(IOptions<AppSettings> appSettings)
        {
            _appSettings = appSettings.Value;
        }

        public int Send(string reciever, string body, string subject)
        {
            SmtpClient client = new SmtpClient(_appSettings.MailHost, _appSettings.MailPort);
            client.EnableSsl = true;
            client.UseDefaultCredentials = false;
            client.Credentials = new NetworkCredential(_appSettings.MailUsername, _appSettings.MailPassword);

            MailMessage mailMessage = new MailMessage();
            mailMessage.From = new MailAddress(_appSettings.MailUsername);
            mailMessage.To.Add(reciever);
            mailMessage.Body = body;
            mailMessage.Subject = subject;
            client.Send(mailMessage);
            return 1;
        }

        public int SendSMS(string number, string body)
        {
            var client = new HttpClient();
            client.PostAsync("http://192.168.0.1:1880/api/gotAccess", new StringContent(JsonConvert.SerializeObject(new
            {
                phoneNumber = number,
                msg = body
            }),
            Encoding.UTF8, "application/json"));
            return 1;
        }
    }
}

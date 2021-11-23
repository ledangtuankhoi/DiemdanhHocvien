using DiemdanhHocvien.DataAccess;
using DiemdanhHocvien.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;

namespace DiemdanhHocvien.Controllers.api
{
    public class AccountController : ApiController
    {
        private AuthenticationDB db = new AuthenticationDB();

        // POST: api/Class
        [HttpPost]
        public string PostUser([FromBody] User value)
        {
            if (!db.Users.Any(x=>x.Username.Equals(value.Username)))
            {
                //return "";
                User user = new User();
                user.Username = value.Username;
                user.FirstName = value.FirstName;
                user.LastName = value.LastName;
                user.Email = value.Email;
                user.Password = value.Password;

                try
                { 
                    db.Users.Add(user);
                    db.SaveChanges();
                    return JsonConvert.SerializeObject("Register Success");
                }
                catch (Exception ex)
                {
                    return JsonConvert.SerializeObject(ex.Message);
                     
                }
            }
            else
            {
                return JsonConvert.SerializeObject("user existing in database") ;
            }
             
        }
    }
}

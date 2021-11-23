using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using DiemdanhHocvien.DataAccess;
using DiemdanhHocvien.Models;
using Newtonsoft.Json;

namespace DiemdanhHocvien.Controllers.api
{
    public class LoginController : ApiController
    {
        private AuthenticationDB db = new AuthenticationDB();

        // POST: api/Class
        [HttpPost]
        public string login([FromBody] User value)
        {
            if (!db.Users.Any(x => x.Username.Equals(value.Username)))
            {
                User user = db.Users.Where(x => x.Username.Equals(value.Username)).First();
                if (user.Password.Equals(value.Password))
                {
                    return JsonConvert.SerializeObject(user);
                }
                else
                {
                    return JsonConvert.SerializeObject("Pass flase");
                }
            }
            else
            {
                return JsonConvert.SerializeObject("user not existing in database");

            } 

        }
    }
}

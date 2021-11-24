﻿using System;
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

        // POST: api/login
        [HttpPost]
        public string login([FromBody]LoginView value)
        {
            //return JsonConvert.SerializeObject(value);

            if (db.Users.Any(x => x.Username.Equals(value.UserName)))
            {
                User user = db.Users.Where(x => x.Username.Equals(value.UserName)).First();
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

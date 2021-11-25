using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using DiemdanhHocvien.DataAccess;
using DiemdanhHocvien.Models;
using Newtonsoft.Json;

namespace DiemdanhHocvien.Controllers.api
{
    public class LoginController : ApiController
    {
        private AuthenticationDB db = new AuthenticationDB();

        // POST: api/login
        [ResponseType(typeof(User))]
        public IHttpActionResult login([FromBody]LoginView value)
        {
            //return JsonConvert.SerializeObject(value);

            if (db.Users.Any(x => x.Username.Equals(value.UserName)))
            {
                User user = db.Users.Where(x => x.Username.Equals(value.UserName)).First();
                if (user.Password.Equals(value.Password))
                {
                    //User u = db.Users.Where(x => x.UserId == user.UserId).Select(x => new { x.UserId }).FirstOrDefault();
                    CustomSerializeModel userModel = new Models.CustomSerializeModel()
                    {
                        UserId = user.UserId,
                        FirstName = user.FirstName,
                        LastName = user.LastName,
                        Email = user.Email,
                        RoleName = user.Roles.Select(r => r.RoleName).ToList()
                    };
                    return Content(HttpStatusCode.OK, userModel);
                }
                else
                {
                    return Content(HttpStatusCode.BadRequest, "Pass flase");
                }
            }
            else
            {
                return Content(HttpStatusCode.BadRequest, "user not existing in database");

            } 

        }
    }
}

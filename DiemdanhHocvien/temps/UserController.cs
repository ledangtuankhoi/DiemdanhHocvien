using DiemdanhHocvien.CustomAuthentication;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace DiemdanhHocvien.Controllers
{
    [CustomAuthorize(Roles = "User")]
    public class UserController : Controller
    {
        
        // GET: User
        public ActionResult Index()
        {
            return View();
        }
    }
}
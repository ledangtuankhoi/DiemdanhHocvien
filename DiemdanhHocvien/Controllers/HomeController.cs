using System.Collections.Generic;
using System.Linq;
using System.Web.Mvc;
using DiemdanhHocvien.CustomAuthentication;
using DiemdanhHocvien.DataAccess;

namespace DiemdanhHocvien.Controllers
{
    public class HomeController : Controller
    {
        private AuthenticationDB db = new AuthenticationDB();

        // GET: Home
        public ActionResult Index()
        {
            //User curent
            var identity = ((CustomPrincipal)System.Web.HttpContext.Current.User);
            //role of User
            string Roles = identity.Roles.FirstOrDefault();

            if (Roles == "teacher")
            {

                return RedirectToAction("indexTeacher", "Attendence");
            }
             

            return View();
        }
    }
}
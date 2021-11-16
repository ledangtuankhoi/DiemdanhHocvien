using System.Web.Mvc;

namespace DiemdanhHocvien.Controllers
{
    public class ErrorController : Controller
    {
        // GET: Error
        public ActionResult AccessDenied()
        {
            return View();
        }
    }
}
using DiemdanhHocvien.CustomAuthentication;
using DiemdanhHocvien.DataAccess;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web.Mvc;

namespace DiemdanhHocvien.Controllers
{
    [Authorize]
    public class UserController : Controller
    {
        private AuthenticationDB db = new AuthenticationDB();

        [CustomAuthorize(Roles = "superadmin,user,admin,")]
        // GET: User
        public ActionResult Index()
        {
            return View(db.Users.ToList());
        }

        // GET: User/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            User user = db.Users.Find(id);
            if (user == null)
            {
                return HttpNotFound();
            }
            return View(user);
        }

        [CustomAuthorize(Roles = "superadmin,user,admin,")]
        // GET: User/Create
        public ActionResult Create()
        {
            return View();
        }

        [CustomAuthorize(Roles = "superadmin,user,admin,")]
        // POST: User/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "UserId,Username,FirstName,LastName,Email,Password,IsActive,ActivationCode")] User user)
        {
            if (ModelState.IsValid)
            {
                db.Users.Add(user);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(user);
        }

        // GET: User/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            User user = db.Users.Find(id);
            if (user == null)
            {
                return HttpNotFound();
            }
            return View(user);
        }

        // POST: User/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "UserId,FirstName,LastName,Email,Password ")] User user)
        {
            if (ModelState.IsValid)
            {
                User a = db.Users.Find(user.UserId);
                if(a != null)
                {
                    a.Username = user.Username;
                    a.FirstName = user.FirstName;
                    a.LastName = user.LastName;
                    a.Email = user.Email;
                    a.Password = user.Password;

                    db.Entry(a).State = EntityState.Modified;
                    db.SaveChanges();
                    return RedirectToAction("Details", new { id = user.UserId });
                }
            }
            return View(user);
        }

        [CustomAuthorize(Roles = "superadmin,user,admin,")]
        // GET: User/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            User user = db.Users.Find(id);
            if (user == null)
            {
                return HttpNotFound();
            }
            return View(user);
        }

        [CustomAuthorize(Roles = "superadmin,user,admin,")]
        // POST: User/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            User user = db.Users.Find(id);
            db.Users.Remove(user);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}

using DiemdanhHocvien.CustomAuthentication;
using DiemdanhHocvien.DataAccess;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web.Mvc;
using System.Web.UI.WebControls;


namespace DiemdanhHocvien.Controllers
{
    [CustomAuthorize(Roles = "superadmin,admin,user,leader,teacher")]

    public class ClassController : Controller
    {

        private AuthenticationDB db = new AuthenticationDB();

        // GET: addStudent/5
        public ActionResult addStudent(int id)
        {
            var lstStudent = db.students.ToList();
            List<string> lstnameClass = new List<string>();

            foreach (var item in lstStudent)
            {
                var nameClass = db.classes.Where(x => x.id == item.classId).Select(x => x.className).FirstOrDefault();
                if (nameClass != null)
                {

                    lstnameClass.Add(nameClass);
                }
                else
                {
                    lstnameClass.Add("null");
                }
            }
            ViewBag.lstnameClass = lstnameClass;
            ViewBag.classId = id;

            return View(lstStudent);
        }
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult addStudent(List<int> lstStudent, int classId)
        {
            if (ModelState.IsValid)
            {
                //db.students.Add(student);

                foreach (var item in lstStudent)
                {
                    db.students.Find(item).classId = classId;
                    db.SaveChanges();
                }

                ViewBag.quantity = lstStudent.Count;
                return RedirectToAction("Index", "class");
            }

            return View();
        }

        // GET: Class
        public ActionResult Index()
        {
            var lstClass = db.classes.ToList();
            List<int> soluong = new List<int>();
            foreach (var item in lstClass)
            {
                var countStud = db.students.Where(x => x.classId == item.id).Count();
                soluong.Add(countStud);
            }
            //lstClass.AddRange);
            ViewBag.soluong = soluong;
            return View(db.classes.ToList());
        }

        // GET: Class/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Class @class = db.classes.Find(id);
            if (@class == null)
            {
                return HttpNotFound();
            }

            //tim hoc vien thuoc hop
            List<Student> studentsOfClass = new List<Student>();
            foreach (var item in db.students.ToList())
            {
                if (item.classId == id)
                {
                    studentsOfClass.Add(item);
                }
            }
            ViewBag.studentsOfClass = studentsOfClass;
            return View(@class);
        }

        // GET: Class/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: Class/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "id,className,codeName,startDate,endDate,dayOfWeek,userId")] Class @class, List<int> dayOfWeek)
        {
            if (ModelState.IsValid && dayOfWeek != null)
            {
                List<string> l2 = dayOfWeek.ConvertAll<string>(x => x.ToString());

                @class.dayOfWeek = string.Join(",", l2);
                db.classes.Add(@class);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(@class);
        }

        // GET: Class/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Class @class = db.classes.Find(id);
            if (@class == null)
            {
                return HttpNotFound();
            }
            return View(@class);
        }

        // POST: Class/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "id,className,codeName,startDate,endDate ,userId")] Class @class, List<int> dayOfWeek)
        {
            if (ModelState.IsValid)
            {
                List<string> l2 = dayOfWeek.ConvertAll<string>(x => x.ToString());

                @class.dayOfWeek = string.Join(",", l2);
                db.Entry(@class).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(@class);
        }

        // GET: Class/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Class @class = db.classes.Find(id);
            if (@class == null)
            {
                return HttpNotFound();
            }
            return View(@class);
        }

        // POST: Class/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Class @class = db.classes.Find(id);
            db.classes.Remove(@class);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        [HttpPost]
        public ActionResult DeleteStudent(int id)
        {
            db.students.Find(id).classId = 0;
            db.SaveChanges();
            return new HttpStatusCodeResult(System.Net.HttpStatusCode.OK);

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

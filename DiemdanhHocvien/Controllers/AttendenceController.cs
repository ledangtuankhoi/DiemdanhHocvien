using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using DiemdanhHocvien.CustomAuthentication;
using DiemdanhHocvien.DataAccess;

namespace DiemdanhHocvien.Controllers
{
    public class AttendenceController : Controller
    {
        private AuthenticationDB db = new AuthenticationDB();

        // GET: Attendence/indexTeacher
        public ActionResult indexTeacher()
        {
            var identity = ((CustomPrincipal)System.Web.HttpContext.Current.User);

            //find id class with id user
            int classId = db.classes.Where(x => x.userId == identity.UserId).Select(x => x.id).FirstOrDefault();

            //qualyti student have in class
            List<int> soluong = new List<int>();
            //List  all student with id class
            var lstClass = db.classes.Where(x => x.userId == identity.UserId).ToList();
            foreach (var item in lstClass)
            {
                var countStud = db.students.Where(x => x.classId == item.id).Count();
                soluong.Add(countStud);
            }
            //lstClass.AddRange);
            ViewBag.soluong = soluong;
            return View(lstClass);
        }

        // GET: Attendence/attendenceTeacher
        public ActionResult attendenceTeacher(int id)
        {
            var lstStudent = db.students.Where(x => x.classId == id).ToList();
            //List<string> lstnameClass = new List<string>();


            return View(lstStudent);
            //return 
        }
        [HttpPost]
        [ValidateAntiForgeryToken]
        // POST: Attendence/attendenceTeacher
        public ActionResult attendenceTeacher(List<int> lstStudentTrue, List<string> description)
        {
            //List<string> lstDes = new List<string>( ) ;
            foreach (var item in description)
            {

                var i = item.Split('#');
                int idStud = int.Parse(i[0]);
                //minium time next attendent student
                int minTimeAtte = 10;
                var atteStud = db.attendences.Where(x => x.studentId == idStud).FirstOrDefault();
                if (atteStud != null && atteStud.id > 0)
                {
                    if (atteStud.createTime.Date.Day == DateTime.Now.Date.Day || (atteStud.time - DateTime.Now).TotalMinutes > minTimeAtte)
                    {
                        atteStud.order += 1;
                        atteStud.time = DateTime.Now;

                        db.Entry(atteStud).State = EntityState.Modified;
                        db.SaveChanges();
                    }
                }
                else
                {

                    if (lstStudentTrue.Any(x => x == idStud))
                    {
                        Attendence attendence = new Attendence();
                        attendence.order = 1;
                        attendence.description = i[1];
                        attendence.studentId = idStud;

                        attendence.time = DateTime.Now;
                        attendence.createTime = DateTime.Now;
                        db.attendences.Add(attendence);
                        db.SaveChanges();
                    }
                    else
                    {
                        Attendence attendence = new Attendence();
                        attendence.order = 0;
                        attendence.description = i[1];
                        attendence.studentId = idStud;

                        attendence.time = DateTime.Now;
                        attendence.createTime = DateTime.Now;
                        db.attendences.Add(attendence);
                        db.SaveChanges();
                    }
                }
            }

            return View();

        }


        // GET: Attendence
        public ActionResult Index()
        {
            return View(db.attendences.ToList());
        }

        // GET: Attendence/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Attendence attendence = db.attendences.Find(id);
            if (attendence == null)
            {
                return HttpNotFound();
            }
            return View(attendence);
        }

        // GET: Attendence/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: Attendence/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "id,order,time,description")] Attendence attendence)
        {
            if (ModelState.IsValid)
            {
                db.attendences.Add(attendence);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(attendence);
        }

        // GET: Attendence/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Attendence attendence = db.attendences.Find(id);
            if (attendence == null)
            {
                return HttpNotFound();
            }
            return View(attendence);
        }

        // POST: Attendence/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "id,order,time,description")] Attendence attendence)
        {
            if (ModelState.IsValid)
            {
                db.Entry(attendence).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(attendence);
        }

        // GET: Attendence/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Attendence attendence = db.attendences.Find(id);
            if (attendence == null)
            {
                return HttpNotFound();
            }
            return View(attendence);
        }

        // POST: Attendence/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Attendence attendence = db.attendences.Find(id);
            db.attendences.Remove(attendence);
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

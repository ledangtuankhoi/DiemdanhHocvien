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
    [Authorize]
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
        public ActionResult attendenceTeacher(int id, string msg)
        {
            //List student in class
            var lstStudent = db.students.Where(x => x.classId == id).ToList();

            List<Attendence> lstAtten = new List<Attendence>();
            foreach (var item in lstStudent)
            {
                var i = db.attendences.Where(x => x.studentId == item.id).OrderByDescending(x=>x.createTime).FirstOrDefault();
                if(i == null || i.createTime.Date.Day != DateTime.Now.Date.Day)
                {
                    Attendence atte = new Attendence();
                    atte.createTime = DateTime.Now;
                    atte.time= DateTime.Now;
                    atte.description = "";
                    atte.order = 0;
                    atte.studentId = item.id;

                    lstAtten.Add(atte);
                    db.attendences.Add(atte);
                    db.SaveChanges();
                }
                else
                {
                    lstAtten.Add(i);
                }
            }
            ViewBag.classId = id;
            ViewBag.error = msg;
            ViewBag.lstStud = lstStudent;
            return View(lstAtten);
            //return 
        }
        [HttpPost]
        [ValidateAntiForgeryToken]
        // POST: Attendence/attendenceTeacher
        public ActionResult attendenceTeacher(List<int> lstStudentTrue, List<string> description)
        {
            //var lstStudent = db.students.Where(x => x.classId == id).ToList();
            List<int> lstStudeId = new List<int>();
            List<Attendence> lstAtten = new List<Attendence>();
            foreach (var item in description)
            {
                var i = item.Split('#');
                //i[0] id of student
                //i[1] description of student
                int idStud = int.Parse(i[0]);

                //description or student
                string desAtten = item.Substring(item.IndexOf("#")+1);

                lstStudeId.Add(idStud);

                //minium time next attendent student
                int minTimeAtte =0; // minutes

                var atteStud = db.attendences.Where(x => x.studentId == idStud).OrderByDescending(x => x.createTime).FirstOrDefault();
                if (atteStud != null && atteStud.createTime.Date.Day == DateTime.Now.Date.Day && (DateTime.Now - atteStud.time).TotalMinutes > minTimeAtte)
                { 
                    atteStud.description = desAtten;
                    // kiểm tra xem nếu có trong list điểm danh thì tăng order lên
                    if(lstStudentTrue != null && lstStudentTrue.Contains(idStud))
                    {
                        atteStud.order += 1;
                        atteStud.time = DateTime.Now;
                    } 
                    db.Entry(atteStud).State = EntityState.Modified;
                    db.SaveChanges();
                }
                else
                {
                    ViewBag.error = "sau " + minTimeAtte + " phut ban moi co the diem danh lai";
                    if (atteStud == null)
                    {
                        ViewBag.error = "diem danh hocj sinh"+atteStud.studentId+"khong thanh cong";
                    }
                    
                }
                    ////find attendence procese
                    bool datetrue = atteStud.createTime.Date.Day == DateTime.Now.Date.Day;
                lstAtten.Add(db.attendences.Where(x => x.studentId == idStud && datetrue == true).FirstOrDefault());
            }
            List<Student> lstStudent = new List<Student>();
            foreach (var item in lstStudeId)
            {
                lstStudent.Add(db.students.Find(item));
            }

            //ViewBag.lstStud = lstStudent;
            //return View(lstAtten);
            string msg = ViewBag.error;
            return RedirectToAction("attendenceTeacher",new { msg });

        }

        [CustomAuthorize(Roles = "superadmin,admin,user,leader")]
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
        [CustomAuthorize(Roles = "superadmin,admin,user,leader")]

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

        [CustomAuthorize(Roles = "superadmin,admin,user,leader")]
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

        [CustomAuthorize(Roles = "superadmin,admin,user,leader")]
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

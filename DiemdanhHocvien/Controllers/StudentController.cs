using DiemdanhHocvien.CustomAuthentication;
using DiemdanhHocvien.DataAccess;
using DiemdanhHocvien.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using System.Web.Security;

namespace DiemdanhHocvien.Controllers
{
    [CustomAuthorize(Roles = "superadmin,admin,user,leader,teacher")]
    public class StudentController : Controller
    {
        private AuthenticationDB db = new AuthenticationDB();



        // GET: Student
        public ActionResult Index()
        {
            List<Student> listStudent = new List<Student>();

            HttpCookie authCookie = Request.Cookies["Cookie1"];
            if (authCookie != null)
            {
                FormsAuthenticationTicket authTicket = FormsAuthentication.Decrypt(authCookie.Value);

                var serializeModel = JsonConvert.DeserializeObject<CustomSerializeModel>(authTicket.UserData);
                CustomPrincipal principal = new CustomPrincipal(authTicket.Name);
                principal.UserId = serializeModel.UserId;
                
                //id is iduser
                var u = db.Users.Find(principal.UserId);
                string role = u.Roles.FirstOrDefault().RoleName;
                //set authen 
                if (role == "teacher" && role != null)
                {
                    var listClass = db.classes.Where(x => x.userId == u.UserId).ToList();
                    foreach (var item in listClass)
                    {
                        List<Student> students = db.students.Where(x => x.classId == item.id).ToList();
                        listStudent.AddRange(students);
                    }
                    //list studetn not class or class null empty
                    foreach (var item in db.students.ToList())
                    {
                        if (db.classes.Find(item.classId) == null)
                        {
                            listStudent.Add(item);
                        }
                    }

                }
                else if (role == "admin" || role == "superadmin" || role == "admin" || role == "leader")
                {
                    listStudent.AddRange(db.students.ToList());
                }
                else
                {
                    listStudent = null;
                }
                //return listStudent;
            }


            return View(listStudent);
        }

        // GET: Student/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Student student = db.students.Find(id);
            if (student == null)
            {
                return HttpNotFound();
            }

            // sinh vien hoc lớp nào
            var classOfStud = db.classes.Where(x => x.id == student.classId).FirstOrDefault();
            ViewBag.classOfStud = classOfStud;
            // giáo viên dạy lớp
            ViewBag.teacherOfStud = db.Teachers.Where(x => x.id == classOfStud.userId).FirstOrDefault();
            // số ngày điểm danh
            var AtteOfStud = db.attendences.Where(x => x.studentId == student.id).ToList();
            ViewBag.countDateAtteOfStud = AtteOfStud.Count();
            // số ngày vắng
            ViewBag.countOrderFalse = AtteOfStud.Where(x => x.order == 0).Count();
            // chi tiết vắng
            //ngày - lý do - lớp 
            ViewBag.orderFalse = AtteOfStud.Where(x => x.order == 0).Select(s => new { s.time, s.description, s.order });
            // số ngày điểm danh ngoài giờ
            var startDate = classOfStud.startDate.Date;
            var endDate = classOfStud.endDate.Date;
            List<int> dayOfWeek = classOfStud.dayOfWeek.Split(',').Select(Int32.Parse).ToList();
            ViewBag.dayOfWeek = dayOfWeek;
            List<Attendence> attenDateOT = new List<Attendence>();
            List<Attendence> attenDateTrue = new List<Attendence>();
            List<Attendence> attenDateFalse = new List<Attendence>();

            for (DateTime date = startDate; date <= endDate; date = date.AddDays(1))
            {
                var a = AtteOfStud.Where(x => x.createTime.Date == date.Date && x.studentId == id).FirstOrDefault();
                if (a != null)
                {

                    if (dayOfWeek.Contains(((int)date.DayOfWeek)) == true)
                    {
                        if(a.order > 0)
                        {
                            attenDateTrue.Add(a);
                        }
                        else
                        {
                            attenDateFalse.Add(a);
                        }
                    }
                    else
                    {
                        attenDateOT.Add(a);
                    }
                }
            }
            

            // những ngày điểm danh 
            ViewBag.attenDateTrue = attenDateTrue;
            // những ngày KHONG điểm danh 
            ViewBag.attenDateFalse = attenDateFalse;
            // số ngày điểm danh ngoài giờ
            ViewBag.attenDateOT = attenDateOT;
            // chi tiết vắng
            //ngày - lý do - lớp 

            return View(student);
        }

        // GET: Student/Create
        public ActionResult Create()
        {
            ViewBag.parents = db.parents.ToList();

            return View();
        }

        // POST: Student/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "id,lastName,firstName,holyName,BOD,numPhone,email,parentId,classId")] Student student)
        {
            if (ModelState.IsValid)
            {
                db.students.Add(student);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(student);
        }

        // GET: Student/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Student student = db.students.Find(id);
            if (student == null)
            {
                return HttpNotFound();
            }

            ViewBag.parents = db.parents.ToList();

            student.BOD.GetDateTimeFormats();
            return View(student);
        }

        // POST: Student/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "id,lastName,firstName,holyName,BOD,numPhone,email,parentId,classId")] Student student)
        {
            if (ModelState.IsValid)
            {
                db.Entry(student).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(student);
        }

        // GET: Student/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Student student = db.students.Find(id);
            if (student == null)
            {
                return HttpNotFound();
            }
            return View(student);
        }

        // POST: Student/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Student student = db.students.Find(id);
            db.students.Remove(student);
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

using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using DiemdanhHocvien.DataAccess;

namespace DiemdanhHocvien.Controllers.api
{
    public class StudentsInClassController : ApiController
    {
        private AuthenticationDB db = new AuthenticationDB();
        public class AtteStudent
        {
            //info student
            private int id { get; set; }
            public string lastName { get; set; }
            public string firstName { get; set; }
            public string holyName { get; set; }
            public DateTime BOD { get; set; }
            public string numPhone { get; set; }
            public string email { get; set; }
            public int parentId { get; set; }
            public int classId { get; set; }
            //info attendance
            public int order { get; set; }
            public string description { get; set; }
            public int studentId { get; set; }
            public DateTime createTime { get; set; }
            public DateTime time { get; set; }
            public bool isAttendance { get; set; }


        }

        // GET: api/StudentsInClass/5
        public List<AtteStudent> GetstudentsInClass(int id)
        {
            //id is idclass
            //init list studen for attendance
            List<AtteStudent> listAtteStudents = new List<AtteStudent>();

            //List student in class
            var lstStudent = db.students.Where(x => x.classId == id).ToList();
            foreach (var item in lstStudent)
            {
                var i = db.attendences.Where(x => x.studentId == item.id).OrderByDescending(x => x.createTime).FirstOrDefault();
                if (i == null || i.createTime.Date.Day != DateTime.Now.Date.Day)
                {
                    //create att in database
                    Attendence atte = new Attendence();
                    atte.createTime = DateTime.Now;
                    atte.time = DateTime.Now;
                    atte.description = "";
                    atte.order = 0;
                    atte.studentId = item.id;
                    //lstAtten.Add(atte);
                    db.attendences.Add(atte);
                    db.SaveChanges();

                    //create att in list attstudent
                    //info attendance
                    AtteStudent atteStudent = new AtteStudent();
                    atteStudent.order = atte.order;
                    atteStudent.description = atte.description;
                    atteStudent.studentId = atte.studentId;
                    atteStudent.time = atte.time;
                    atteStudent.createTime = atte.createTime;
                    atteStudent.isAttendance = false;

                    //info student
                    atteStudent.lastName = item.lastName;
                    atteStudent.firstName = item.firstName;
                    atteStudent.holyName = item.holyName;
                    atteStudent.BOD = item.BOD;
                    atteStudent.email = item.email;
                    atteStudent.numPhone = item.numPhone;
                    atteStudent.parentId = item.parentId;
                    atteStudent.classId = item.classId;
                    //add object to list
                    listAtteStudents.Add(atteStudent);


                }
                else
                {
                    //create att in list attstudent
                    //info attendance
                    AtteStudent atteStudent = new AtteStudent();
                    atteStudent.order = i.order;
                    atteStudent.description = i.description;
                    atteStudent.studentId = i.studentId;
                    atteStudent.time = i.time;
                    atteStudent.createTime = i.createTime;
                    atteStudent.isAttendance = false;

                    //info student
                    atteStudent.lastName = item.lastName;
                    atteStudent.firstName = item.firstName;
                    atteStudent.holyName = item.holyName;
                    atteStudent.BOD = item.BOD;
                    atteStudent.email = item.email;
                    atteStudent.numPhone = item.numPhone;
                    atteStudent.parentId = item.parentId;
                    atteStudent.classId = item.classId;
                    //add object to list
                    listAtteStudents.Add(atteStudent);
                }


            }


            return listAtteStudents;
        }


        // POST: api/StudentsInClass
        [ResponseType(typeof(Student))]
        public IHttpActionResult PostStudent(List<AtteStudent> atteStudents)
        {
            foreach (var item in atteStudents)
            { 
                Attendence attendence = db.attendences.Where(x => x.studentId == item.studentId).OrderByDescending(x => x.createTime).FirstOrDefault();
                //when attendance true
                if (attendence != null && item.isAttendance == true
                    && attendence.createTime.Date.Day == DateTime.Now.Date.Day)
                {
                    attendence.order += 1;
                    attendence.time = DateTime.Now;
                    attendence.description = item.description;
                    db.Entry(attendence).State = EntityState.Modified;
                    db.SaveChanges();
                }
                else if(attendence != null 
                    && attendence.createTime.Date.Day == DateTime.Now.Date.Day)
                {
                    attendence.time = DateTime.Now;
                    attendence.description = item.description;
                    db.Entry(attendence).State = EntityState.Modified;
                    db.SaveChanges();
                }
            }

            return Ok("ok");
        }

        //// GET: api/StudentsInClass/5
        //[ResponseType(typeof(Student))]
        //public IHttpActionResult GetStudent(int id)
        //{
        //    Student student = db.students.Find(id);
        //    if (student == null)
        //    {
        //        return NotFound();
        //    }

        //    return Ok(student);
        //}

        // PUT: api/StudentsInClass/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutStudent(int id, Student student)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != student.id)
            {
                return BadRequest();
            }

            db.Entry(student).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!StudentExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }


        // DELETE: api/StudentsInClass/5
        [ResponseType(typeof(Student))]
        public IHttpActionResult DeleteStudent(int id)
        {
            Student student = db.students.Find(id);
            if (student == null)
            {
                return NotFound();
            }

            db.students.Remove(student);
            db.SaveChanges();

            return Ok(student);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool StudentExists(int id)
        {
            return db.students.Count(e => e.id == id) > 0;
        }
    }
}
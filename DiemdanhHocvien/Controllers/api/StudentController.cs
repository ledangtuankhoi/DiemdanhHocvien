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

    public class StudentController : ApiController
    {
        private AuthenticationDB db = new AuthenticationDB();

        // GET: api/Student
        [HttpGet]
        [ActionName("Getstudents")]
        public List<Student> Getstudents(int id)
        {
            //id is iduser
            var u = db.Users.Find(id);
            string role = u.Roles.FirstOrDefault().RoleName;
             List<Student> listStudent = new List<Student>();
            if (role == "teacher" && role != null)
            {
                var listClass = db.classes.Where(x => x.userId == id).ToList();
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
            return listStudent;
            //return db.students.Where(x => x.Equals(lisdfa)).ToList();

        }

        // GET: api/Student/5
        [ResponseType(typeof(Student))]
        [HttpGet]
        [ActionName("GetStudent")]
        public IHttpActionResult GetStudent(int id)
        {
            Student student = db.students.Find(id);
            if (student == null)
            {
                return NotFound();
            }

            return Ok(student);
        }

        // PUT: api/Student/5
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

        // POST: api/Student
        [ResponseType(typeof(Student))]
        public IHttpActionResult PostStudent(Student student)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.students.Add(student);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = student.id }, student);
        }

        // DELETE: api/Student/5
        [HttpDelete]
        [ActionName("DeleteStudent")]
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
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
        
        //get studtnt in class
        // GET: api/StudentsInClass/5
        public List<Student> GetstudentsInClass(int id)
        {
            //id is idclass
            //init list studen for attendance
            List<Student> listStudentsInClass = new List<Student>();
            listStudentsInClass.AddRange(db.students.Where(x => x.classId == id).ToList());
            return listStudentsInClass;
        }

        // add student to class
        // POST: api/StudentsInClass
        [ResponseType(typeof(Student))]
        public IHttpActionResult PostStudent(List<Student> Students)
        { 
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
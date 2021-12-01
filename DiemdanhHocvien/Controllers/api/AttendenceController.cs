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
    public class AttendenceController : ApiController
    {
        private AuthenticationDB db = new AuthenticationDB();

        // GET: Attendence/indexTeacher
        //public IQueryable<Attendence> indexTeacher()
        //{
        //    var identity = ((CustomPrincipal)System.Web.HttpContext.Current.User);

        //    //find id class with id user
        //    int classId = db.classes.Where(x => x.userId == identity.UserId).Select(x => x.id).FirstOrDefault();

        //    //qualyti student have in class
        //    List<int> soluong = new List<int>();
        //    //List  all student with id class
        //    var lstClass = db.classes.Where(x => x.userId == identity.UserId).ToList();
        //    foreach (var item in lstClass)
        //    {
        //        var countStud = db.students.Where(x => x.classId == item.id).Count();
        //        soluong.Add(countStud);
        //    }
        //    //lstClass.AddRange);
        //    ViewBag.soluong = soluong;
        //    return View(lstClass);
        //}

        //// GET: api/Attendence
        //public IQueryable<Attendence> Getattendences()
        //{
        //    return db.attendences;
        //}

        //// GET: api/Attendence/5
        //[ResponseType(typeof(Attendence))]
        //public IHttpActionResult GetAttendence(int id)
        //{
        //    Attendence attendence = db.attendences.Find(id);
        //    if (attendence == null)
        //    {
        //        return NotFound();
        //    }

        //    return Ok(attendence);
        //}

        // PUT: api/Attendence/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutAttendence(int id, Attendence attendence)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != attendence.id)
            {
                return BadRequest();
            }

            db.Entry(attendence).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!AttendenceExists(id))
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

        // POST: api/Attendence
        [ResponseType(typeof(Attendence))]
        public IHttpActionResult PostAttendence(Attendence attendence)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.attendences.Add(attendence);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = attendence.id }, attendence);
        }

        // DELETE: api/Attendence/5
        [ResponseType(typeof(Attendence))]
        public IHttpActionResult DeleteAttendence(int id)
        {
            Attendence attendence = db.attendences.Find(id);
            if (attendence == null)
            {
                return NotFound();
            }

            db.attendences.Remove(attendence);
            db.SaveChanges();

            return Ok(attendence);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool AttendenceExists(int id)
        {
            return db.attendences.Count(e => e.id == id) > 0;
        }
    }
}
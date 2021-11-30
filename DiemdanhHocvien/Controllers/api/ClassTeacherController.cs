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
    public class ClassTeacherController : ApiController
    {
        private AuthenticationDB db = new AuthenticationDB();

        // GET: api/ClassTeacher/5
        public List<Class> Getclasses(int id)
        {
            //id of teacher
            List<Class> classes = db.classes.Where(x => x.userId == id).ToList();
            return classes;
        }

        //// GET: api/ClassTeacher/5
        //[ResponseType(typeof(Class))]
        //public IHttpActionResult GetClass(int id)
        //{
        //    Class @class = db.classes.Find(id);
        //    if (@class == null)
        //    {
        //        return NotFound();
        //    }

        //    return Ok(@class);
        //}

        // PUT: api/ClassTeacher/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutClass(int id, Class @class)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != @class.id)
            {
                return BadRequest();
            }

            db.Entry(@class).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ClassExists(id))
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

        // POST: api/ClassTeacher
        [ResponseType(typeof(Class))]
        public IHttpActionResult PostClass(Class @class)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.classes.Add(@class);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = @class.id }, @class);
        }

        // DELETE: api/ClassTeacher/5
        [ResponseType(typeof(Class))]
        public IHttpActionResult DeleteClass(int id)
        {
            Class @class = db.classes.Find(id);
            if (@class == null)
            {
                return NotFound();
            }

            db.classes.Remove(@class);
            db.SaveChanges();

            return Ok(@class);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool ClassExists(int id)
        {
            return db.classes.Count(e => e.id == id) > 0;
        }
    }
}
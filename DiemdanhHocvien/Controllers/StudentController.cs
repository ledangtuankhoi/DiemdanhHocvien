﻿using System;
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
    [CustomAuthorize(Roles = "superadmin,admin,user,leader,teacher")]
    public class StudentController : Controller
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
                if (nameClass!=null)
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
        public ActionResult addStudent(List<int> lstStudent,int classId)
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
                return RedirectToAction("Index","class");
            }

            return View( );
        }

        // GET: Student
        public ActionResult Index()
        {
            return View(db.students.ToList());
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
            return View(student);
        }

        // GET: Student/Create
        public ActionResult Create()
        {
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

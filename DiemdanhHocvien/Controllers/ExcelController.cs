using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using OfficeOpenXml;
using OfficeOpenXml.Style;
using OfficeOpenXml.Table; 
using DiemdanhHocvien.CustomAuthentication;
using DiemdanhHocvien.DataAccess;
using System.Data.Entity;  

namespace DiemdanhHocvien.Controllers
{
    public class ExcelController : Controller
    {
        private AuthenticationDB db = new AuthenticationDB();

        public ActionResult CreateExcelForClass(int id)
        {
            var studentOfClass = db.students.Where(x => x.classId == id).ToList();
            List<Attendence> attenOfClass = new List<Attendence>();
            var classCurrently = db.classes.Find(id);
            //var AtteOfStud = db.attendences.Where(x => x.studentId == student.id).ToList();

            //tìm điểm danh thuộc lớp này
            //tìm các ngày lớp đã đăng ký
            for (DateTime date = classCurrently.startDate; date <= classCurrently.endDate; date = date.AddDays(1))
            {
 
                //tìm điểm danh của sinh viên thuộc lớp này
                foreach (var item in studentOfClass)
                {
                    //tìm cái thằng này với ngày này và thuộc id học sinh của lớp này
                    var i = db.attendences.Where(x => x.time == date && x.studentId == item.id).FirstOrDefault();
                    if (i != null)
                    {
                        attenOfClass.Add(i);
                    }
                }
            }
                //if (a != null)
                //{

                //    if (dayOfWeek.Contains(((int)date.DayOfWeek)) == true)
                //    {
                //        if (a.order > 0)
                //        {
                //            attenDateTrue.Add(a);
                //        }
                //        else
                //        {
                //            attenDateFalse.Add(a);
                //        }
                //    }
                //    else
                //    {
                //        attenDateOT.Add(a);
                //    }
                //}
            


            return View( );
        }

        // GET: Excel
        public ActionResult Index()
        {
            return View();
        }
         
    }
}
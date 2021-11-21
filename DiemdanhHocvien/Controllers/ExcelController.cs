using DiemdanhHocvien.DataAccess;
using OfficeOpenXml;
using OfficeOpenXml.Style;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.IO;
using System.Linq;
using System.Web.Mvc;

namespace DiemdanhHocvien.Controllers
{
    public class ExcelController : Controller
    {
        private AuthenticationDB db = new AuthenticationDB();

        public void CreateExcelForClass(int id)
        {
            var studentOfClass = db.students.Where(x => x.classId == id).ToList();
            List<Attendence> attenOfClass = new List<Attendence>();
            var classCurrently = db.classes.Find(id);

            foreach (var item in studentOfClass)
            {
                var a = db.attendences.Where(x => x.studentId == item.id).ToList();
                attenOfClass.AddRange(a);

            }

            ExcelPackage excel = new ExcelPackage();
            var workSheet = excel.Workbook.Worksheets.Add("Sheet1");
            workSheet.TabColor = System.Drawing.Color.Black;
            workSheet.DefaultRowHeight = 12;
            //Header of table  
            //  
            workSheet.Row(1).Height = 20;
            workSheet.Row(1).Style.HorizontalAlignment = ExcelHorizontalAlignment.Center;
            workSheet.Row(1).Style.VerticalAlignment = ExcelVerticalAlignment.Center;
            workSheet.Row(2).Style.HorizontalAlignment = ExcelHorizontalAlignment.Center;
            workSheet.Row(2).Style.VerticalAlignment = ExcelVerticalAlignment.Center;
            workSheet.Row(1).Style.Font.Bold = true;

            //workSheet.Cells["A1:A2"].Value = "S.No";
            workSheet.Cells[1, 1, 2, 1].Merge = true;
            workSheet.Cells[1, 1, 2, 1].Value = "S.No";

            //workSheet.Cells["B1:B2"].Value = "S.No";
            workSheet.Cells[1, 2, 2, 2].Merge = true;
            workSheet.Cells[1, 2, 2, 2].Value = "S.No";

            //workSheet.Cells["C1:C2"].Merge = true;
            workSheet.Cells[1, 3, 2, 3].Merge = true;
            workSheet.Cells[1, 3, 2, 3].Value = "S.No";

            //workSheet.Cells["D1:D2"].Merge = true;
            workSheet.Cells[1, 4, 2, 4].Merge = true;
            workSheet.Cells[1, 4, 2, 4].Value = "S.No";

            //workSheet.Cells["D1:D2"].Merge = true;
            workSheet.Cells[1, 5, 2, 5].Merge = true;
            workSheet.Cells[1, 5, 2, 5].Value = "S.No";

            //
            int indexCol = 6;
            int firstDay = 0;
            List<int> dayOfWeek = classCurrently.dayOfWeek.Split(',').Select(Int32.Parse).ToList();
            // tim ngay dau tien da dang ky
            for (DateTime date = classCurrently.startDate; date <= classCurrently.endDate; date = date.AddDays(1))
            {
                if (dayOfWeek.Contains(((int)date.DayOfWeek)) == true)
                {
                    firstDay = (int)date.DayOfWeek;
                    break;
                }

            }

            // chạy từ ngày bắt đầu đến ngày kết thúc của lớp học 
            for (DateTime date = classCurrently.startDate; date <= classCurrently.endDate; date = date.AddDays(1))
            {
                // gộp các dòng để đánh dấu 1 tuần
                if ((int)date.Date.DayOfWeek == firstDay)
                {
                    workSheet.Cells[1, indexCol, 1, indexCol + dayOfWeek.Count()-1].Merge = true;
                     workSheet.Cells[1, indexCol, 1, indexCol + dayOfWeek.Count()-1].Value = date.Date.Day + "  =>  " + date.Date.AddDays(6).ToString("dd-MM-yyyy");
                }

                //lọc ra ngày trong tuần đúng với thứ của tuần đã đăng ký
                if (dayOfWeek.Contains(((int)date.DayOfWeek)) == true)
                {

                    //nếu ngày trong tuần bằng 0 thì là chủ nhật
                    if ((int)date.DayOfWeek == 0)
                    {
                        workSheet.Cells[2, indexCol].Value = "CN";
                        workSheet.Cells[3, indexCol].Value = date.Date.Day;

                    }
                    else
                    {
                        workSheet.Cells[2, indexCol].Value = (int)date.DayOfWeek + 1;
                        workSheet.Cells[3, indexCol].Value = date.Date.Day;
                    }
                    indexCol++;
                }

            }

            //Body of table  
            //  
            int recordIndex = 4;
            foreach (var item in studentOfClass)
            {

                workSheet.Cells[recordIndex, 1].Value = (recordIndex - 1).ToString();
                workSheet.Cells[recordIndex, 2].Value = item.holyName;
                workSheet.Cells[recordIndex, 3].Value = item.firstName;
                workSheet.Cells[recordIndex, 4].Value = item.lastName;
                workSheet.Cells[recordIndex, 5].Value = item.id;

                //ngày điểm danh 
                
                for (DateTime date = classCurrently.startDate; date <= classCurrently.endDate; date = date.AddDays(1))
                {
                    //lọc ra ngày trong tuần đúng với thứ của tuần đã đăng ký
                    if (dayOfWeek.Contains(((int)date.DayOfWeek)) == true)
                    {
                        var b = db.attendences.Where(x => DbFunctions.TruncateTime(x.time) == date.Date && x.studentId == item.id).FirstOrDefault();
                        if (b != null)
                        {

                            if (b.order > 0)
                            {
                                workSheet.Cells[recordIndex, indexCol].Value = "V- "+date.Day;
                                indexCol++;

                            }
                            else
                            {
                                workSheet.Cells[recordIndex, indexCol].Value = "- " + date.Day;
                                indexCol++;
                            }
                        }
                        else
                        {
                            indexCol++;
                        }
 
                    }
                    

                }
                recordIndex++;
            }
            workSheet.Column(1).AutoFit();
            workSheet.Column(2).AutoFit();
            workSheet.Column(3).AutoFit();
            workSheet.Column(4).AutoFit();
            string excelName = "studentsRecord";
            using (var memoryStream = new MemoryStream())
            {
                Response.ContentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                Response.AddHeader("content-disposition", "attachment; filename=" + excelName + ".xlsx");
                excel.SaveAs(memoryStream);
                memoryStream.WriteTo(Response.OutputStream);
                Response.Flush();
                Response.End();
            }
        }
    }
}
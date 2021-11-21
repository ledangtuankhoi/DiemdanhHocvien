using DiemdanhHocvien.DataAccess;
using OfficeOpenXml;
using OfficeOpenXml.Style;
using System;
using System.Collections.Generic;
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

            int i = 5;
            int firstDay = 0;
            for (DateTime date = classCurrently.startDate; date <= classCurrently.endDate; date = date.AddDays(1))
            {
                List<int> dayOfWeek = classCurrently.dayOfWeek.Split(',').Select(Int32.Parse).ToList();
                if (dayOfWeek.Contains(((int)date.DayOfWeek)) == true)
                {
                    firstDay = (int)date.DayOfWeek;
                    break;
                }
                    
            }

            // chạy từ ngày bắt đầu đến ngày kết thúc của lớp học 
            for (DateTime date = classCurrently.startDate; date <= classCurrently.endDate; date = date.AddDays(1))
            {
                //tạo list cho các ngày trong tuần đã đang ký
                List<int> dayOfWeek = classCurrently.dayOfWeek.Split(',').Select(Int32.Parse).ToList();

                //
                //if (i == 5)
                //{
                //    workSheet.Cells[1, i, 1, i + dayOfWeek.Count()-1].Merge = true;
                //    workSheet.Cells[1, i, 1, i + dayOfWeek.Count()-1].Value = date.Date.ToString("dd-MM-yyyy");
                //}
                if ((int)date.Date.DayOfWeek == firstDay)
                {
                    workSheet.Cells[1, i + 1, 1, i + dayOfWeek.Count()].Merge = true;
                    workSheet.Cells[1, i + 1, 1, i + dayOfWeek.Count()].Value = date.Date.ToString("dd-MM-yyyy");
                }

                //lọc ra ngày trong tuần đúng với thứ của tuần đã đăng ký
                if (dayOfWeek.Contains(((int)date.DayOfWeek)) == true)
                {
                    
                    //nếu ngày trong tuần bằng 0 thì là chủ nhật
                    if ((int)date.DayOfWeek == 0)
                    {
                        workSheet.Cells[2, i].Value = "CN";

                    }
                    else
                    {
                        workSheet.Cells[2, i].Value = (int)date.DayOfWeek + 1;
                    }
                    i++;
                }

            }

            //Body of table  
            //  
            int recordIndex = 2;
            foreach (var item in attenOfClass)
            {
                workSheet.Cells[recordIndex, 1].Value = (recordIndex - 1).ToString();
                workSheet.Cells[recordIndex, 2].Value = item.studentId;
                workSheet.Cells[recordIndex, 3].Value = item.time.Date.ToString("dd-MM-yyyy");
                workSheet.Cells[recordIndex, 4].Value = item.studentId;
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
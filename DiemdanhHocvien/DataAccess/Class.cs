using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace DiemdanhHocvien.DataAccess
{
    public class Class
    {
        [Key]
        public int id { get; set; }
        public string className { get; set; }
        public string codeName { get; set; }
        public DateTime startDate{ get; set; }
        public DateTime endDate { get; set; }
        public string numPhoneMom { get; set; }
        public string dayOfWeek { get; set; }
        public int userId { get; set; } 

    }
}
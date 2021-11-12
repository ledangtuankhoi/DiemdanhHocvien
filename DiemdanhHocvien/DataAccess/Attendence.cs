using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace DiemdanhHocvien.DataAccess
{
    public class Attendence
    {
        [Key]
        public int id { get; set; }
        public int order { get; set; }
        public DateTime time { get; set; }
        public string description { get; set; }
    }
}
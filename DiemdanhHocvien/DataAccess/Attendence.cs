using System;
using System.ComponentModel.DataAnnotations;

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
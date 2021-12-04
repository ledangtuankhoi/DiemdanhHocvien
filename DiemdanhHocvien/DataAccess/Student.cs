using System;
using System.ComponentModel.DataAnnotations;

namespace DiemdanhHocvien.DataAccess
{
    public class Student
    {
        [Key]
        public int id { get; set; }
        [Required]
        public string lastName { get; set; }
        [Required]
        public string firstName { get; set; }
        [Required]
        public string holyName { get; set; }
        [Required]
        public DateTime BOD { get; set; }
        public string numPhone { get; set; }
        public string email { get; set; }
        public int parentId { get; set; }
        public int classId { get; set; }
    }
}
using System.ComponentModel.DataAnnotations;

namespace DiemdanhHocvien.DataAccess
{
    public class Parent
    {
        [Key]
        public int id { get; set; }
        public string dadName { get; set; }
        public string monName { get; set; }
        public string numPhoneDad { get; set; }
        public string numPhoneMom { get; set; }
        public string emailDad { get; set; }
        public string emailMon { get; set; }
        public string address { get; set; }

    }
}
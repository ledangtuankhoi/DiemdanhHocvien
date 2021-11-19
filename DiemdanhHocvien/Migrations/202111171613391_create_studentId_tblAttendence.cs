namespace DiemdanhHocvien.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class create_studentId_tblAttendence : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Attendences", "studentId", c => c.Int(nullable: false));
            AddColumn("dbo.Attendences", "createTime", c => c.DateTime(nullable: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Attendences", "createTime");
            DropColumn("dbo.Attendences", "studentId");
        }
    }
}

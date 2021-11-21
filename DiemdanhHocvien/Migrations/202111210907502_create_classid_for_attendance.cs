namespace DiemdanhHocvien.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class create_classid_for_attendance : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Attendences", "classId", c => c.Int(nullable: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Attendences", "classId");
        }
    }
}

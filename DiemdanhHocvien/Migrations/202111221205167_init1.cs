namespace DiemdanhHocvien.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class init1 : DbMigration
    {
        public override void Up()
        {
            DropColumn("dbo.Attendences", "classId");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Attendences", "classId", c => c.Int(nullable: false));
        }
    }
}

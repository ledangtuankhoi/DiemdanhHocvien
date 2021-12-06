namespace DiemdanhHocvien.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class student : DbMigration
    {
        public override void Up()
        {

            DropTable("dbo.Students");
            CreateTable(
                "dbo.Students",
                c => new
                {
                    id = c.Int(nullable: false, identity: true),
                    lastName = c.String(nullable: false),
                    firstName = c.String(nullable: false),
                    holyName = c.String(nullable: false),
                    BOD = c.DateTime(nullable: false),
                    numPhone = c.String(),
                    email = c.String(),
                    parentId = c.Int(),
                    classId = c.Int(),
                })
                .PrimaryKey(t => t.id);
        }
        
        public override void Down()
        {
            DropTable("dbo.Students");

        }
    }
}

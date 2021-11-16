namespace DiemdanhHocvien.Migrations
{
    using System.Data.Entity.Migrations;

    public partial class init : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Attendences",
                c => new
                {
                    id = c.Int(nullable: false, identity: true),
                    order = c.Int(nullable: false),
                    time = c.DateTime(nullable: false),
                    description = c.String(),
                })
                .PrimaryKey(t => t.id);

            CreateTable(
                "dbo.Classes",
                c => new
                {
                    id = c.Int(nullable: false, identity: true),
                    className = c.String(),
                    codeName = c.String(),
                    startDate = c.DateTime(nullable: false),
                    endDate = c.DateTime(nullable: false),
                    numPhoneMom = c.String(),
                    dayOfWeek = c.Int(nullable: false),
                    userId = c.Int(nullable: false),
                })
                .PrimaryKey(t => t.id);

            CreateTable(
                "dbo.Parents",
                c => new
                {
                    id = c.Int(nullable: false, identity: true),
                    dadName = c.String(),
                    monName = c.String(),
                    numPhoneDad = c.String(),
                    numPhoneMom = c.String(),
                    emailDad = c.String(),
                    emailMon = c.String(),
                    address = c.String(),
                })
                .PrimaryKey(t => t.id);

            CreateTable(
                "dbo.Roles",
                c => new
                {
                    RoleId = c.Int(nullable: false, identity: true),
                    RoleName = c.String(),
                })
                .PrimaryKey(t => t.RoleId);

            CreateTable(
                "dbo.Users",
                c => new
                {
                    UserId = c.Int(nullable: false, identity: true),
                    Username = c.String(),
                    FirstName = c.String(),
                    LastName = c.String(),
                    Email = c.String(),
                    Password = c.String(),
                    IsActive = c.Boolean(nullable: false),
                    ActivationCode = c.Guid(nullable: false),
                })
                .PrimaryKey(t => t.UserId);

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
                    parentId = c.Int(nullable: false),
                    classId = c.Int(nullable: false),
                })
                .PrimaryKey(t => t.id);

            CreateTable(
                "dbo.Teachers",
                c => new
                {
                    id = c.Int(nullable: false, identity: true),
                    lastName = c.String(nullable: false),
                    firstName = c.String(nullable: false),
                    holyName = c.String(nullable: false),
                    BOD = c.DateTime(nullable: false),
                    numPhone = c.String(),
                    email = c.String(),
                    rules = c.Int(nullable: false),
                })
                .PrimaryKey(t => t.id);

            CreateTable(
                "dbo.UserRoles",
                c => new
                {
                    UserId = c.Int(nullable: false),
                    RoleId = c.Int(nullable: false),
                })
                .PrimaryKey(t => new { t.UserId, t.RoleId })
                .ForeignKey("dbo.Users", t => t.UserId, cascadeDelete: true)
                .ForeignKey("dbo.Roles", t => t.RoleId, cascadeDelete: true)
                .Index(t => t.UserId)
                .Index(t => t.RoleId);

        }

        public override void Down()
        {
            DropForeignKey("dbo.UserRoles", "RoleId", "dbo.Roles");
            DropForeignKey("dbo.UserRoles", "UserId", "dbo.Users");
            DropIndex("dbo.UserRoles", new[] { "RoleId" });
            DropIndex("dbo.UserRoles", new[] { "UserId" });
            DropTable("dbo.UserRoles");
            DropTable("dbo.Teachers");
            DropTable("dbo.Students");
            DropTable("dbo.Users");
            DropTable("dbo.Roles");
            DropTable("dbo.Parents");
            DropTable("dbo.Classes");
            DropTable("dbo.Attendences");
        }
    }
}

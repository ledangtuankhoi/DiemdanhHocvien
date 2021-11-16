namespace DiemdanhHocvien.Migrations
{
    using System.Data.Entity.Migrations;

    public partial class tbl_class_dayofwweb_to_string : DbMigration
    {
        public override void Up()
        {
            AlterColumn("dbo.Classes", "dayOfWeek", c => c.String());
        }

        public override void Down()
        {
            AlterColumn("dbo.Classes", "dayOfWeek", c => c.Int(nullable: false));
        }
    }
}

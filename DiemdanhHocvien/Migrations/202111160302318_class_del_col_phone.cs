namespace DiemdanhHocvien.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class class_del_col_phone : DbMigration
    {
        public override void Up()
        {
            DropColumn("dbo.Classes", "numPhoneMom");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Classes", "numPhoneMom", c => c.String());
        }
    }
}

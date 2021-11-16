namespace DiemdanhHocvien.Migrations
{
    using DiemdanhHocvien.DataAccess;
    using System.Collections.Generic;
    using System.Data.Entity.Migrations;

    internal sealed class Configuration : DbMigrationsConfiguration<DiemdanhHocvien.DataAccess.AuthenticationDB>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
        }

        protected override void Seed(DiemdanhHocvien.DataAccess.AuthenticationDB context)
        {

            //  This method will be called after migrating to the latest version.

            //  You can use the DbSet<T>.AddOrUpdate() helper extension method 
            //  to avoid creating duplicate seed data. E.g.
            //
            //    context.People.AddOrUpdate(
            //      p => p.FullName,
            //      new Person { FullName = "Andrew Peters" },
            //      new Person { FullName = "Brice Lambson" },
            //      new Person { FullName = "Rowan Miller" }
            //    );
            //



            context.Users.AddOrUpdate(x => x.UserId,

                new DataAccess.User
                {
                    UserId = 1,
                    FirstName = "Super",
                    LastName = "admin",
                    Username = "superadmin",
                    Password = "1234",
                    Email = "ledangtuankhoi2@gmail.com",
                    IsActive = true,
                    Roles = new List<Role> { new Role { RoleId = 1, RoleName = "superadmin" } }
                },
                new DataAccess.User
                {
                    UserId = 2,
                    FirstName = "Admin",
                    LastName = "0",
                    Username = "admin",
                    Password = "1234",
                    Email = "ledangtuankhoi2@gmail.com",
                    IsActive = true,
                    Roles = new List<Role> { new Role { RoleId = 2, RoleName = "admin" } }
                },
                new DataAccess.User
                {
                    UserId = 3,
                    FirstName = "User",
                    LastName = "0",
                    Username = "user",
                    Password = "1234",
                    Email = "ledangtuankhoi2@gmail.com",
                    IsActive = true,
                    Roles = new List<Role> { new Role { RoleId = 3, RoleName = "user" } }
                },
                new DataAccess.User
                {
                    UserId = 4,
                    FirstName = "Leader",
                    LastName = "admi0",
                    Username = "leader",
                    Password = "1234",
                    Email = "ledangtuankhoi2@gmail.com",
                    IsActive = true,
                    Roles = new List<Role> { new Role { RoleId = 3, RoleName = "leader" } }
                },
                new DataAccess.User
                {
                    UserId = 5,
                    FirstName = "Teacher",
                    LastName = "admi0",
                    Username = "teacher",
                    Password = "1234",
                    Email = "ledangtuankhoi2@gmail.com",
                    IsActive = true,
                    Roles = new List<Role> { new Role { RoleId = 4, RoleName = "teacher" } }
                }
                );
            context.SaveChanges();

        }
    }
}

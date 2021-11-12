namespace DiemdanhHocvien.Migrations
{
    using DiemdanhHocvien.DataAccess;
    using System;
    using System.Collections.Generic;
    using System.Data.Entity;
    using System.Data.Entity.Migrations;
    using System.Linq;

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
                    FirstName = "Super",
                    LastName = "admin",
                    Username = "superadmin",
                    Password = "1234",
                    IsActive = true,
                    Roles = new List<Role> { new Role { RoleId = 1, RoleName = " superadmin" } }
                },
                new DataAccess.User
                {
                    FirstName = "Admin",
                    LastName = "0",
                    Username = "admin",
                    Password = "1234",
                    IsActive = true,
                    Roles = new List<Role> { new Role { RoleId = 2, RoleName = "admin" } }
                },
                new DataAccess.User
                {
                    FirstName = "User",
                    LastName = "0",
                    Username = "user",
                    Password = "1234",
                    IsActive = true,
                    Roles = new List<Role> { new Role { RoleId = 3, RoleName = "user" } }
                },
                new DataAccess.User
                {
                    FirstName = "Leader",
                    LastName = "admi0",
                    Username = "leader",
                    Password = "1234",
                    IsActive = true,
                    Roles = new List<Role> { new Role { RoleId = 3, RoleName = "leader" } }
                },
                new DataAccess.User
                {
                    FirstName = "Teacher",
                    LastName = "admi0",
                    Username = "teacher",
                    Password = "1234",
                    IsActive = true,
                    Roles = new List<Role> { new Role { RoleId = 4, RoleName = "teacher" } }
                }



                );
            
        }
    }
}

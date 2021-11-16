using System.Data.Entity;

namespace DiemdanhHocvien.DataAccess
{
    public class AuthenticationDB : DbContext
    {
        public AuthenticationDB()
            : base("DiemDanhHocVienConnection")
        {

        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<User>()
                .HasMany(u => u.Roles)
                .WithMany(r => r.Users)
                .Map(m =>
                {
                    m.ToTable("UserRoles");
                    m.MapLeftKey("UserId");
                    m.MapRightKey("RoleId");
                });
        }

        public DbSet<User> Users { get; set; }
        public DbSet<Role> Roles { get; set; }
        public DbSet<Class> classes { get; set; }
        public DbSet<Attendence> attendences { get; set; }
        public DbSet<Parent> parents { get; set; }
        public DbSet<Student> students { get; set; }
        public DbSet<Teacher> Teachers { get; set; }

    }
}
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace db.Db
{
    public partial class mydbContext : DbContext
    {
        public mydbContext()
        {
        }

        public mydbContext(DbContextOptions<mydbContext> options)
            : base(options)
        {
        }

        public virtual DbSet<AcAccess> AcAccess { get; set; }
        public virtual DbSet<AcDate> AcDate { get; set; }
        public virtual DbSet<AcDay> AcDay { get; set; }
        public virtual DbSet<AcEventLog> AcEventLog { get; set; }
        public virtual DbSet<AcEventStatus> AcEventStatus { get; set; }
        public virtual DbSet<AcObject> AcObject { get; set; }
        public virtual DbSet<AcObjectHasTriggerType> AcObjectHasTriggerType { get; set; }
        public virtual DbSet<AcObjectType> AcObjectType { get; set; }
        public virtual DbSet<AcProfil> AcProfil { get; set; }
        public virtual DbSet<AcRole> AcRole { get; set; }
        public virtual DbSet<AcSchedule> AcSchedule { get; set; }
        public virtual DbSet<AcSystemLog> AcSystemLog { get; set; }
        public virtual DbSet<AcTrigger> AcTrigger { get; set; }
        public virtual DbSet<AcTriggerType> AcTriggerType { get; set; }
        public virtual DbSet<AcUser> AcUser { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. See http://go.microsoft.com/fwlink/?LinkId=723263 for guidance on storing connection strings.
                optionsBuilder.UseMySQL("server=192.168.0.1;port=3306;user=root;password=test123;database=mydb");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<AcAccess>(entity =>
            {
                entity.HasKey(e => new { e.AcsId, e.AcsValidFrom, e.AcsValidTo });

                entity.ToTable("ac_access", "mydb");

                entity.HasIndex(e => e.AcsObjId)
                    .HasName("fk_access_object1_idx");

                entity.HasIndex(e => e.AcsProId)
                    .HasName("fk_access_profiles1_idx");

                entity.HasIndex(e => e.AcsUsrId)
                    .HasName("fk_access_user1_idx");

                entity.Property(e => e.AcsId)
                    .HasColumnName("acs_id")
                    .HasColumnType("int(11)")
                    .ValueGeneratedOnAdd();

                entity.Property(e => e.AcsValidFrom).HasColumnName("acs_valid_from");

                entity.Property(e => e.AcsValidTo).HasColumnName("acs_valid_to");

                entity.Property(e => e.AcsObjId)
                    .HasColumnName("acs_obj_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.AcsOpeningCounter)
                    .HasColumnName("acs_opening_counter")
                    .HasColumnType("int(11)");

                entity.Property(e => e.AcsProId)
                    .HasColumnName("acs_pro_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.AcsUsrId)
                    .HasColumnName("acs_usr_id")
                    .HasColumnType("int(11)");

                entity.HasOne(d => d.AcsObj)
                    .WithMany(p => p.AcAccess)
                    .HasForeignKey(d => d.AcsObjId)
                    .HasConstraintName("fk_access_object1");

                entity.HasOne(d => d.AcsPro)
                    .WithMany(p => p.AcAccess)
                    .HasForeignKey(d => d.AcsProId)
                    .HasConstraintName("fk_access_profiles1");

                entity.HasOne(d => d.AcsUsr)
                    .WithMany(p => p.AcAccess)
                    .HasForeignKey(d => d.AcsUsrId)
                    .HasConstraintName("fk_access_user1");
            });

            modelBuilder.Entity<AcDate>(entity =>
            {
                entity.HasKey(e => e.DatId);

                entity.ToTable("ac_date", "mydb");

                entity.HasIndex(e => e.DatProId)
                    .HasName("fk_date_profil1_idx");

                entity.Property(e => e.DatId)
                    .HasColumnName("dat_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.DatDateFrom).HasColumnName("dat_date_from");

                entity.Property(e => e.DatDateTo).HasColumnName("dat_date_to");

                entity.Property(e => e.DatEnabled)
                    .HasColumnName("dat_enabled")
                    .HasColumnType("tinyint(4)");

                entity.Property(e => e.DatProId)
                    .HasColumnName("dat_pro_id")
                    .HasColumnType("int(11)");

                entity.HasOne(d => d.DatPro)
                    .WithMany(p => p.AcDate)
                    .HasForeignKey(d => d.DatProId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("fk_date_profil1");
            });

            modelBuilder.Entity<AcDay>(entity =>
            {
                entity.HasKey(e => e.DayId);

                entity.ToTable("ac_day", "mydb");

                entity.Property(e => e.DayId)
                    .HasColumnName("day_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.DayName)
                    .IsRequired()
                    .HasColumnName("day_name")
                    .HasMaxLength(45)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<AcEventLog>(entity =>
            {
                entity.HasKey(e => e.EvlId);

                entity.ToTable("ac_event_log", "mydb");

                entity.HasIndex(e => e.EvlEvsId)
                    .HasName("fk_event_log_event_type1_idx");

                entity.HasIndex(e => e.EvlObjId)
                    .HasName("fk_event_log_object1_idx");

                entity.HasIndex(e => e.EvlTrtId)
                    .HasName("fk_event_log_trigger_type1_idx");

                entity.HasIndex(e => e.EvlUsrId)
                    .HasName("fk_event_log_user1_idx");

                entity.Property(e => e.EvlId)
                    .HasColumnName("evl_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.EvlDate).HasColumnName("evl_date");

                entity.Property(e => e.EvlEvsId)
                    .HasColumnName("evl_evs_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.EvlObjId)
                    .HasColumnName("evl_obj_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.EvlTrgValue)
                    .IsRequired()
                    .HasColumnName("evl_trg_value")
                    .HasMaxLength(100)
                    .IsUnicode(false);

                entity.Property(e => e.EvlTrtId)
                    .HasColumnName("evl_trt_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.EvlUsrId)
                    .HasColumnName("evl_usr_id")
                    .HasColumnType("int(11)");

                entity.HasOne(d => d.EvlEvs)
                    .WithMany(p => p.AcEventLog)
                    .HasForeignKey(d => d.EvlEvsId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("fk_event_log_event_type1");

                entity.HasOne(d => d.EvlObj)
                    .WithMany(p => p.AcEventLog)
                    .HasForeignKey(d => d.EvlObjId)
                    .HasConstraintName("fk_event_log_object1");

                entity.HasOne(d => d.EvlTrt)
                    .WithMany(p => p.AcEventLog)
                    .HasForeignKey(d => d.EvlTrtId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("fk_event_log_trigger_type1");

                entity.HasOne(d => d.EvlUsr)
                    .WithMany(p => p.AcEventLog)
                    .HasForeignKey(d => d.EvlUsrId)
                    .HasConstraintName("fk_event_log_user1");
            });

            modelBuilder.Entity<AcEventStatus>(entity =>
            {
                entity.HasKey(e => e.EvsId);

                entity.ToTable("ac_event_status", "mydb");

                entity.Property(e => e.EvsId)
                    .HasColumnName("evs_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.EvsDescription)
                    .HasColumnName("evs_description")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.EvsName)
                    .IsRequired()
                    .HasColumnName("evs_name")
                    .HasMaxLength(45)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<AcObject>(entity =>
            {
                entity.HasKey(e => e.ObjId);

                entity.ToTable("ac_object", "mydb");

                entity.HasIndex(e => e.ObjObtTypeId)
                    .HasName("fk_objects_object_type1_idx");

                entity.Property(e => e.ObjId)
                    .HasColumnName("obj_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.ObjAction)
                    .IsRequired()
                    .HasColumnName("obj_action")
                    .HasMaxLength(1000)
                    .IsUnicode(false);

                entity.Property(e => e.ObjActivity)
                    .HasColumnName("obj_activity")
                    .HasColumnType("tinyint(4)");

                entity.Property(e => e.ObjAuto)
                    .HasColumnName("obj_auto")
                    .HasColumnType("tinyint(4)");

                entity.Property(e => e.ObjGps)
                    .IsRequired()
                    .HasColumnName("obj_GPS")
                    .HasMaxLength(100)
                    .IsUnicode(false);

                entity.Property(e => e.ObjName)
                    .IsRequired()
                    .HasColumnName("obj_name")
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.ObjObtTypeId)
                    .HasColumnName("obj_obt_type_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.ObjOpen)
                    .HasColumnName("obj_open")
                    .HasColumnType("tinyint(4)");

                entity.HasOne(d => d.ObjObtType)
                    .WithMany(p => p.AcObject)
                    .HasForeignKey(d => d.ObjObtTypeId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("fk_objects_object_type1");
            });

            modelBuilder.Entity<AcObjectHasTriggerType>(entity =>
            {
                entity.HasKey(e => new { e.OhtTrtId, e.OhtObjId });

                entity.ToTable("ac_object_has_trigger_type", "mydb");

                entity.HasIndex(e => e.OhtObjId)
                    .HasName("fk_trigger_type_has_object_object1_idx");

                entity.HasIndex(e => e.OhtTrtId)
                    .HasName("fk_trigger_type_has_object_trigger_type1_idx");

                entity.Property(e => e.OhtTrtId)
                    .HasColumnName("oht_trt_id")
                    .HasColumnType("int(11)")
                    .HasDefaultValueSql("0");

                entity.Property(e => e.OhtObjId)
                    .HasColumnName("oht_obj_id")
                    .HasColumnType("int(11)")
                    .HasDefaultValueSql("0");

                entity.Property(e => e.OhtActivity)
                    .HasColumnName("oht_activity")
                    .HasColumnType("tinyint(4)");

                entity.HasOne(d => d.OhtObj)
                    .WithMany(p => p.AcObjectHasTriggerType)
                    .HasForeignKey(d => d.OhtObjId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("fk_trigger_type_has_object_object1");

                entity.HasOne(d => d.OhtTrt)
                    .WithMany(p => p.AcObjectHasTriggerType)
                    .HasForeignKey(d => d.OhtTrtId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("fk_trigger_type_has_object_trigger_type1");
            });

            modelBuilder.Entity<AcObjectType>(entity =>
            {
                entity.HasKey(e => e.ObtId);

                entity.ToTable("ac_object_type", "mydb");

                entity.Property(e => e.ObtId)
                    .HasColumnName("obt_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.ObtIn)
                    .HasColumnName("obt_in")
                    .HasColumnType("tinyint(4)");

                entity.Property(e => e.ObtName)
                    .IsRequired()
                    .HasColumnName("obt_name")
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.ObtOut)
                    .HasColumnName("obt_out")
                    .HasColumnType("tinyint(4)");
            });

            modelBuilder.Entity<AcProfil>(entity =>
            {
                entity.HasKey(e => e.ProId);

                entity.ToTable("ac_profil", "mydb");

                entity.Property(e => e.ProId)
                    .HasColumnName("pro_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.ProActivity)
                    .HasColumnName("pro_activity")
                    .HasColumnType("tinyint(4)");

                entity.Property(e => e.ProName)
                    .IsRequired()
                    .HasColumnName("pro_name")
                    .HasMaxLength(45)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<AcRole>(entity =>
            {
                entity.HasKey(e => e.RolId);

                entity.ToTable("ac_role", "mydb");

                entity.Property(e => e.RolId)
                    .HasColumnName("rol_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.RolCompany)
                    .HasColumnName("rol_company")
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.RolName)
                    .IsRequired()
                    .HasColumnName("rol_name")
                    .HasMaxLength(45)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<AcSchedule>(entity =>
            {
                entity.HasKey(e => new { e.SchProId, e.SchDayId });

                entity.ToTable("ac_schedule", "mydb");

                entity.HasIndex(e => e.SchDayId)
                    .HasName("fk_schedule_day1_idx");

                entity.HasIndex(e => e.SchProId)
                    .HasName("fk_schedule_profil1_idx");

                entity.Property(e => e.SchProId)
                    .HasColumnName("sch_pro_id")
                    .HasColumnType("int(11)")
                    .HasDefaultValueSql("0");

                entity.Property(e => e.SchDayId)
                    .HasColumnName("sch_day_id")
                    .HasColumnType("int(11)")
                    .HasDefaultValueSql("0");

                entity.Property(e => e.SchTimeFrom).HasColumnName("sch_time_from");

                entity.Property(e => e.SchTimeTo).HasColumnName("sch_time_to");

                entity.HasOne(d => d.SchDay)
                    .WithMany(p => p.AcSchedule)
                    .HasForeignKey(d => d.SchDayId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("fk_schedule_day1");

                entity.HasOne(d => d.SchPro)
                    .WithMany(p => p.AcSchedule)
                    .HasForeignKey(d => d.SchProId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("fk_schedule_profil1");
            });

            modelBuilder.Entity<AcSystemLog>(entity =>
            {
                entity.HasKey(e => e.SysId);

                entity.ToTable("ac_system_log", "mydb");

                entity.Property(e => e.SysId)
                    .HasColumnName("sys_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.SysAction)
                    .IsRequired()
                    .HasColumnName("sys_action")
                    .HasMaxLength(2000)
                    .IsUnicode(false);

                entity.Property(e => e.SysText)
                    .HasColumnName("sys_text")
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.SysUsrId)
                    .HasColumnName("sys_usr_id")
                    .HasColumnType("int(11)");
            });

            modelBuilder.Entity<AcTrigger>(entity =>
            {
                entity.HasKey(e => new { e.TrgUsrId, e.TrgTrtId });

                entity.ToTable("ac_trigger", "mydb");

                entity.HasIndex(e => e.TrgTrtId)
                    .HasName("fk_user_has_category_category1_idx");

                entity.HasIndex(e => e.TrgUsrId)
                    .HasName("fk_user_has_category_user1_idx");

                entity.Property(e => e.TrgUsrId)
                    .HasColumnName("trg_usr_id")
                    .HasColumnType("int(11)")
                    .HasDefaultValueSql("0");

                entity.Property(e => e.TrgTrtId)
                    .HasColumnName("trg_trt_id")
                    .HasColumnType("int(11)")
                    .HasDefaultValueSql("0");

                entity.Property(e => e.TrgActivity)
                    .HasColumnName("trg_activity")
                    .HasColumnType("tinyint(4)");

                entity.Property(e => e.TrgValue)
                    .IsRequired()
                    .HasColumnName("trg_value")
                    .HasMaxLength(1000)
                    .IsUnicode(false);

                entity.HasOne(d => d.TrgTrt)
                    .WithMany(p => p.AcTrigger)
                    .HasForeignKey(d => d.TrgTrtId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("fk_user_has_category_category1");

                entity.HasOne(d => d.TrgUsr)
                    .WithMany(p => p.AcTrigger)
                    .HasForeignKey(d => d.TrgUsrId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("fk_user_has_category_user1");
            });

            modelBuilder.Entity<AcTriggerType>(entity =>
            {
                entity.HasKey(e => e.TrtId);

                entity.ToTable("ac_trigger_type", "mydb");

                entity.Property(e => e.TrtId)
                    .HasColumnName("trt_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.TrtName)
                    .IsRequired()
                    .HasColumnName("trt_name")
                    .HasMaxLength(45)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<AcUser>(entity =>
            {
                entity.HasKey(e => e.UsrId);

                entity.ToTable("ac_user", "mydb");

                entity.HasIndex(e => e.UsrRolId)
                    .HasName("fk_user_user_type_idx");

                entity.Property(e => e.UsrId)
                    .HasColumnName("usr_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.UsrActivity)
                    .HasColumnName("usr_activity")
                    .HasColumnType("tinyint(4)");

                entity.Property(e => e.UsrCryptedPassword)
                    .HasColumnName("usr_crypted_password")
                    .HasMaxLength(100)
                    .IsUnicode(false);

                entity.Property(e => e.UsrEmail)
                    .HasColumnName("usr_email")
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.UsrName)
                    .HasColumnName("usr_name")
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.UsrPasswordSalt)
                    .IsRequired()
                    .HasColumnName("usr_password")
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.UsrRolId)
                    .HasColumnName("usr_rol_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.UsrSurname)
                    .HasColumnName("usr_surname")
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.HasOne(d => d.UsrRol)
                    .WithMany(p => p.AcUser)
                    .HasForeignKey(d => d.UsrRolId)
                    .HasConstraintName("fk_user_user_type");
            });
        }
    }
}

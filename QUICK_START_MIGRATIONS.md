# Quick Start: Running Flyway Migrations

## 🚀 Quick Steps (Copy & Paste)

### Step 1: Start PostgreSQL
```powershell
cd E:\Projetos-code\systemL
docker-compose up -d
```

### Step 2: Run Your Application
```powershell
mvn clean spring-boot:run
```

### Step 3: Verify Migrations Applied
```powershell
# The app will automatically run V1__init.sql migration
# Check the logs for: "Executing SQL migration with checksum"
```

## 📁 What Was Set Up

✓ Migration folder: `src/main/resources/db/migration/`
✓ First migration file: `V1__init.sql`
✓ Flyway configured in pom.xml
✓ Flyway enabled in application-dev.properties

## 📝 To Add More Migrations

1. Create new file: `src/main/resources/db/migration/V2__your_description.sql`
2. Write SQL (example):
   ```sql
   ALTER TABLE users ADD COLUMN age INTEGER;
   ```
3. Run the app again - migration applies automatically!

## 🔗 Useful Commands

```powershell
# Start database
docker-compose up -d

# Stop database
docker-compose down

# View database tables (if psql installed)
psql -h localhost -U postgres -d postgres -c "\dt"

# Build without running
mvn clean package

# Run app
mvn clean spring-boot:run

# Run with specific profile
mvn clean spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

## ⚠️ Remember

- Always increment version numbers (V1, V2, V3, not V1, V1, V2)
- Never edit old migration files - create new ones instead
- Use `IF NOT EXISTS` clauses for safety
- Migrations run in order automatically when app starts

---

For detailed information, see `FLYWAY_GUIDE.md`


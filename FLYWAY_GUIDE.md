# Flyway Migration Guide for systemL

## What is Flyway?
Flyway is a version control system for your database. It automatically applies SQL migrations in order, ensuring your database schema stays synchronized with your application.

## Your Current Setup ✓

Your project already has Flyway configured:
- **pom.xml**: Flyway dependencies and Maven plugin are set up
- **application-dev.properties**: Flyway is enabled and configured
- **Migration folder**: Created at `src/main/resources/db/migration/`

## How Flyway Works

### Migration File Naming Convention
Flyway uses a specific naming pattern: `V{version}__{description}.sql`

Examples:
- `V1__init.sql` - Initial schema
- `V2__add_users_table.sql` - Add users table
- `V3__add_email_column_to_users.sql` - Add email column
- `V10__add_timestamps.sql` - Add timestamps

**Rules:**
- `V` = Version (required prefix)
- Numbers = Version number (must be in ascending order)
- `__` = Double underscore (separator)
- Description = Descriptive name (snake_case recommended)
- Extension = `.sql`

## Step-by-Step Migration Process

### 1. Start Your Database
```powershell
docker-compose up -d
```

### 2. Create Migration Files
Place SQL files in: `src/main/resources/db/migration/`

**Example - V1__init.sql** (Already created for you):
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 3. Run Your Application
```powershell
mvn flyway:migrate -Dflyway.url=jdbc:postgresql://localhost:5432/postgres -Dflyway.user=postgres -Dflyway.password=123456
```

**What happens automatically:**
- Spring Boot starts
- Flyway checks the database
- Any pending migrations (V1, V2, etc.) are applied in order
- Your schema is updated!

### 4. Verify the Migration
Connect to your database:
```powershell
# Using PostgreSQL CLI (if installed)
psql -h localhost -U postgres -d postgres
# Password: 123456

# Then in psql:
\dt  -- List all tables
SELECT * FROM flyway_schema_history;  -- See migration history
```

## Creating New Migrations

When you need to modify your database:

1. **Create a new file** with the next version number:
   ```
   V2__add_phone_to_users.sql
   ```

2. **Write your SQL**:
   ```sql
   ALTER TABLE users ADD COLUMN phone VARCHAR(20);
   ```

3. **Commit to git** (optional but recommended):
   ```powershell
   git add src/main/resources/db/migration/V2__add_phone_to_users.sql
   git commit -m "Add phone column to users table"
   ```

4. **Run your application** - migrations apply automatically!

## Important Rules & Best Practices

### ✓ DO:
- Keep migrations small and focused
- Use descriptive names for migrations
- Always increment version numbers
- Make migrations idempotent where possible (use `IF NOT EXISTS`, `IF NOT EXISTS`, etc.)
- Keep SQL simple and database-agnostic when possible
- Test migrations locally before deploying

### ✗ DON'T:
- Never modify existing migration files (V1, V2, etc.)
- Never skip version numbers
- Don't manually alter the database - use migrations instead
- Don't use lowercase in version numbers (V01 not v1)

## Troubleshooting

### Migration fails to apply?
Check these things:
1. Database is running: `docker ps`
2. Check database credentials in `application-dev.properties`
3. Look at Spring Boot logs for error details
4. Check if `flyway_schema_history` table exists

### Need to "undo" a migration?
Unfortunately, Flyway doesn't support rolling back migrations by default. Your options:
1. Create a new migration (V3__undo_previous_changes.sql) with reverse SQL
2. Or use Flyway Enterprise edition for rollback support

### Delete database and start fresh?
```powershell
docker-compose down -v  # -v removes volumes
docker-compose up -d    # Recreates database
mvn clean spring-boot:run  # Runs migrations from scratch
```

## Common Migration Examples

### Create a table:
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL
);
```

### Add a column:
```sql
ALTER TABLE users ADD COLUMN phone VARCHAR(20);
```

### Create an index:
```sql
CREATE INDEX idx_users_email ON users(email);
```

### Add a constraint:
```sql
ALTER TABLE users ADD CONSTRAINT uk_email UNIQUE(email);
```

### Insert data:
```sql
INSERT INTO users (email) VALUES ('admin@example.com');
```

## Summary

1. ✓ Flyway is already configured in your project
2. ✓ Migration folder is created: `src/main/resources/db/migration/`
3. ✓ First migration file (V1__init.sql) is ready
4. Start database: `docker-compose up -d`
5. Run app: `mvn clean spring-boot:run`
6. Migrations apply automatically! 🎉

For future migrations, just create new V{n}__description.sql files and run your app again.


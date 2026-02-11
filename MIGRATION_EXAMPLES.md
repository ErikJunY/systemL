# Example Migrations for Your systemL Project

This file shows example migrations you might want to create based on your current structure.

## V1__init.sql (✓ Already Created)
```sql
-- Initial database schema setup for systeml
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
```

---

## V2__add_phone_to_users.sql (Example - Create if needed)
```sql
-- Add phone number column to users table
ALTER TABLE users ADD COLUMN phone VARCHAR(20);
```

To create this, save the above SQL to: `src/main/resources/db/migration/V2__add_phone_to_users.sql`

---

## V3__add_address_columns.sql (Example - Create if needed)
```sql
-- Add address columns to users table
ALTER TABLE users ADD COLUMN address VARCHAR(500);
ALTER TABLE users ADD COLUMN city VARCHAR(100);
ALTER TABLE users ADD COLUMN state VARCHAR(50);
ALTER TABLE users ADD COLUMN postal_code VARCHAR(20);
ALTER TABLE users ADD COLUMN country VARCHAR(100);
```

To create this, save the above SQL to: `src/main/resources/db/migration/V3__add_address_columns.sql`

---

## V4__add_status_column.sql (Example - Create if needed)
```sql
-- Add status column to users table
ALTER TABLE users ADD COLUMN status VARCHAR(50) DEFAULT 'ACTIVE';
CREATE INDEX idx_users_status ON users(status);
```

To create this, save the above SQL to: `src/main/resources/db/migration/V4__add_status_column.sql`

---

## How to Use These Examples

1. **Don't copy V1** - it's already created
2. **Copy any V2, V3, V4** you need by creating new files
3. **Rename and modify** as needed for your use case
4. **Run your app** - migrations apply automatically in order

Example:
- You want to add phone and address to users
- Create `V2__add_phone_to_users.sql` (copy the V2 example above)
- Create `V3__add_address_columns.sql` (copy the V3 example above)
- Run: `mvn clean spring-boot:run`
- Both migrations apply automatically! ✓

---

## Tips for Writing Good Migrations

✓ One concern per migration (e.g., "add phone" not "add phone and address")
✓ Use `IF NOT EXISTS` when adding tables/columns
✓ Use `IF EXISTS` when dropping
✓ Be explicit about constraints and indexes
✓ Add comments explaining the change

## Template for New Migrations

```sql
-- Brief description of what this migration does

-- Statement 1
ALTER TABLE table_name ADD COLUMN column_name VARCHAR(100);

-- Statement 2 - Create index for performance
CREATE INDEX idx_table_column ON table_name(column_name);
```

---

## Questions?

See `FLYWAY_GUIDE.md` for complete documentation.


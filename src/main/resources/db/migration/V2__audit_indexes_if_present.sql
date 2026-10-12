-- Safe transition migration: add a useful audit lookup index when the legacy
-- audit_events table already exists. The conditional keeps fresh installations
-- compatible with the transitional Hibernate schema bootstrap.
DO $$
BEGIN
    IF to_regclass('public.audit_events') IS NOT NULL THEN
        CREATE INDEX IF NOT EXISTS idx_audit_events_shop_created
            ON audit_events (shop_id, created_at);
    END IF;
END $$;

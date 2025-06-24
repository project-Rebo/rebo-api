INSERT INTO store (
    id,
    name,
    csv_id,
    road_address,
    latitude,
    longitude,
    category_large,
    category_middle,
    category_small,
    created_at,
    updated_at,
    is_deleted
  )
VALUES (
    'id:bigint',
    'name:character varying',
    'csv_id:character varying',
    'road_address:character varying',
    latitude:numeric,
    longitude:numeric,
    'category_large:character varying',
    'category_middle:character varying',
    'category_small:character varying',
    'created_at:timestamp without time zone',
    'updated_at:timestamp without time zone',
    is_deleted:boolean
  );
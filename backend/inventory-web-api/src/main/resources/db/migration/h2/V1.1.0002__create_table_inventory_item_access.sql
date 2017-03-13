--
-- File: V1.1.0001__create_table_inventory_item_access.sql
-- Purpose: Create the initial INVENTORY_ITEM_ACCESS table
-- Database compliance: H2
--
--
-- insert into inventory_item_access (inventory_item_ref_id, date_accessed, accessed_by) values ('1', CURRENT_TIMESTAMP(), 'sb');
-- 
-- Query to determine how many times all inventory items were viewed in last hour
--   select count(*) from inventory_item_access
--   where date_accessed > TIMESTAMPADD('MINUTE', -60, CURRENT_TIMESTAMP())
--
-- Query to determine how many times each inventory item was viewed in last hour
--   select inventory_item_ref_id, count(*) from inventory_item_access
--   where date_accessed > TIMESTAMPADD('MINUTE', -60, CURRENT_TIMESTAMP())
--   group by inventory_item_ref_id



CREATE TABLE inventory_item_access
(
  id bigint NOT NULL DEFAULT nextval('HIBERNATE_SEQUENCE'),
  inventory_item_ref_id VARCHAR(255) NOT NULL,
  date_accessed TIMESTAMP NOT NULL,
  accessed_by VARCHAR(64) NOT NULL,
  CONSTRAINT inventory_item_access_pkey PRIMARY KEY (id)
);


CREATE INDEX inventory_item_access_ii_ref_id_idx
ON inventory_item_access
( inventory_item_ref_id );

CREATE INDEX inventory_item_access_date_accessed_idx
ON inventory_item_access
( date_accessed );

CREATE INDEX inventory_item_access_accessed_by_idx
ON inventory_item_access
( accessed_by );

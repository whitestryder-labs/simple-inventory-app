--
-- File: V1.1.0001__create_table_inventory_item.sql
-- Purpose: Create the initial INVENTORY_ITEM table
-- Database compliance: H2
--
--

CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 INCREMENT BY 1;


CREATE TABLE inventory_item
(
  id bigint NOT NULL DEFAULT nextval('HIBERNATE_SEQUENCE'),
  description VARCHAR(255),
  external_reference_id VARCHAR(255) NOT NULL,
  name VARCHAR(64) NOT NULL,
  price INTEGER NOT NULL,
  quantity_in_stock INTEGER NOT NULL,
  CONSTRAINT inventory_item_pkey PRIMARY KEY (id),
  CONSTRAINT uk_ii_name UNIQUE (name),
  CONSTRAINT uk_ii_ext_ref_id UNIQUE (external_reference_id)
);

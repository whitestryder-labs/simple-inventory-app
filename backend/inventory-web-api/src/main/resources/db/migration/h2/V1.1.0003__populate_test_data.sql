--
-- File: V1.1.0003__populate_test_data.sql
-- Purpose: Insert test data (inventory items) for demo purposes
-- Database compliance: H2
--



insert into inventory_item (external_reference_id, name, description, price, quantity_in_stock)
values (RANDOM_UUID(), 'Soccer Match Ball', 'Addidas Official Match Ball', 150, 8);

insert into inventory_item (external_reference_id, name, description, price, quantity_in_stock)
values (RANDOM_UUID(), 'Barcelona Home Jersey', 'Fans love this Barca jersey', 99, 10);

insert into inventory_item (external_reference_id, name, description, price, quantity_in_stock)
values (RANDOM_UUID(), 'Nike soccer boots', 'CR7 Christiano Ronaldo signature boot', 235, 20);

insert into inventory_item (external_reference_id, name, description, price, quantity_in_stock)
values (RANDOM_UUID(), 'Addidas Copa soccer boots', 'The most popular soccer boot of all time', 160, 20);

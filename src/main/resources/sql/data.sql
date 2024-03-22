-- productos
INSERT INTO products (NAME, price) VALUES('Panasonic Pantalla LCD', 259990);
INSERT INTO products (name, price) VALUES('Sony Camara digital DSC-W320B', 123490);
INSERT INTO products (name, price) VALUES('Apple iPod shuffle', 1499990);
INSERT INTO products (name, price) VALUES('Sony Notebook Z110', 37990);
INSERT INTO products (name, price) VALUES('Hewlett Packard Multifuncional F2280', 69990);
INSERT INTO products (name, price) VALUES('Bianchi Bicicleta Aro 26', 69990);
INSERT INTO products (name, price) VALUES('Mica Comoda 5 Cajones', 299990);

-- clientes
INSERT INTO clients (id, name, address, email) VALUES(1,'John', 'Vlissides', 'john.vlissides@gmail.com');
INSERT INTO clients (id, name, address, email) VALUES(2,'Dr. James', 'Gosling', 'james.gosling@gmail.com');
INSERT INTO clients (id ,name, address, email) VALUES(3,'Magma', 'Lee', 'magma.lee@gmail.com');
INSERT INTO clients (id, name, address, email) VALUES(4,'Tornado', 'Roe', 'tornado.roe@gmail.com');
INSERT INTO clients (id, name, address, email) VALUES(5,'Jade', 'Doe', 'jane.doe@gmail.com');

-- facturas
INSERT INTO invoices (description, client_id, total, create_at) VALUES('Factura Bicicleta', 1, 25000, NOW());
INSERT INTO invoices (description, client_id, total, create_at) VALUES('Factura Gorra', 2, 30000, NOW());

-- lines de cada factura
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(5, 6000, 1, 1);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(10, 5000, 1, 2);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(12, 70000, 2, 3);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(21, 89000, 2, 4);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(5, 6000, 1, 1);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(10, 5000, 1, 2);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(12, 70000, 2, 3);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(21, 89000, 2, 4);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(5, 6000, 1, 1);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(10, 5000, 1, 2);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(12, 70000, 2, 3);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(21, 89000, 2, 4);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(5, 6000, 1, 1);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(10, 5000, 1, 2);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(12, 70000, 2, 3);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(21, 89000, 2, 4);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(5, 6000, 1, 1);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(10, 5000, 1, 2);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(12, 70000, 2, 3);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(21, 89000, 2, 4);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(5, 6000, 1, 1);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(10, 5000, 1, 2);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(12, 70000, 2, 3);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(21, 89000, 2, 4);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(5, 6000, 1, 1);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(10, 5000, 1, 2);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(12, 70000, 2, 3);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(21, 89000, 2, 4);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(5, 6000, 1, 1);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(10, 5000, 1, 2);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(12, 70000, 2, 3);
INSERT INTO items (units, sub_total, invoice_id, product_id) VALUES(21, 89000, 2, 4);
-- Drop tables if exists (productos first due to foreign key dependency)
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS categorias;

-- Create categorias table first (parent)
CREATE TABLE categorias (
                            id SERIAL PRIMARY KEY,
                            nombre VARCHAR(255) NOT NULL
);

-- Create productos table with foreign key (child)
CREATE TABLE productos (
                           id SERIAL PRIMARY KEY,
                           nombre VARCHAR(255) NOT NULL,
                           descripcion TEXT,
                           precio NUMERIC(10, 2) NOT NULL,
                           stock INTEGER DEFAULT 0,
                           categoria_id INTEGER NOT NULL,
                           CONSTRAINT fk_producto_categoria 
                               FOREIGN KEY (categoria_id) REFERENCES categorias(id)
                               ON DELETE RESTRICT
                               ON UPDATE CASCADE
);

-- Insert sample categories
INSERT INTO categorias (nombre)
VALUES
    ('Laptops'),     -- id: 1
    ('Monitores'),   -- id: 2  
    ('Accesorios'),  -- id: 3
    ('Periféricos'); -- id: 4

-- Insert products with category relationships
INSERT INTO productos (nombre, descripcion, precio, stock, categoria_id)
VALUES
    ('Laptop Dell XPS 13', 'Portátil de alta gama con procesador i7', 1299.99, 10, 1),  -- Laptops
    ('Monitor LG 27"', 'Monitor UltraHD con tecnología IPS', 349.99, 15, 2),            -- Monitores
    ('Teclado Mecánico', 'Teclado mecánico con retroiluminación RGB', 89.99, 20, 4),    -- Periféricos
    ('Mouse Inalámbrico', 'Mouse ergonómico inalámbrico', 29.99, 30, 4);                -- Periféricos
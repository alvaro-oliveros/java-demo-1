-- Drop tables if exists
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS categorias;

-- Create categorias table
CREATE TABLE categorias (
                            id SERIAL PRIMARY KEY,
                            nombre VARCHAR(255) NOT NULL
);

-- Create productos table
CREATE TABLE productos (
                           id SERIAL PRIMARY KEY,
                           nombre VARCHAR(255) NOT NULL,
                           descripcion TEXT,
                           precio NUMERIC(10, 2) NOT NULL,
                           stock INTEGER DEFAULT 0
);

-- Insert sample categories
INSERT INTO categorias (nombre)
VALUES
    ('Laptops'),
    ('Monitores'),
    ('Accesorios'),
    ('Periféricos');

-- Insert some sample data
INSERT INTO productos (nombre, descripcion, precio, stock)
VALUES
    ('Laptop Dell XPS 13', 'Portátil de alta gama con procesador i7', 1299.99, 10),
    ('Monitor LG 27"', 'Monitor UltraHD con tecnología IPS', 349.99, 15),
    ('Teclado Mecánico', 'Teclado mecánico con retroiluminación RGB', 89.99, 20),
    ('Mouse Inalámbrico', 'Mouse ergonómico inalámbrico', 29.99, 30);
package com.demo.productmanagement.repository;

import com.demo.productmanagement.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Row mapper for Product with category information
    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setNombre(rs.getString("nombre"));
            product.setDescripcion(rs.getString("descripcion"));
            product.setPrecio(rs.getBigDecimal("precio"));
            product.setStock(rs.getInt("stock"));
            product.setCategoriaId(rs.getLong("categoria_id"));
            // Check if category name is available in the result set
            try {
                product.setCategoriaNombre(rs.getString("categoria_nombre"));
            } catch (SQLException e) {
                // Category name not available in this query
                product.setCategoriaNombre(null);
            }
            return product;
        };
    }

    // Find all products with category names
    public List<Product> findAll() {
        String sql = "SELECT p.*, c.nombre as categoria_nombre " +
                     "FROM productos p " +
                     "LEFT JOIN categorias c ON p.categoria_id = c.id " +
                     "ORDER BY p.id";
        return jdbcTemplate.query(sql, productRowMapper());
    }

    // Find product by ID with category name
    public Optional<Product> findById(Long id) {
        String sql = "SELECT p.*, c.nombre as categoria_nombre " +
                     "FROM productos p " +
                     "LEFT JOIN categorias c ON p.categoria_id = c.id " +
                     "WHERE p.id = ?";
        try {
            Product product = jdbcTemplate.queryForObject(sql, new Object[]{id}, productRowMapper());
            return Optional.ofNullable(product);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Save new product
    public Product save(Product product) {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, stock, categoria_id) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getNombre());
            ps.setString(2, product.getDescripcion());
            ps.setBigDecimal(3, product.getPrecio());
            ps.setInt(4, product.getStock());
            ps.setLong(5, product.getCategoriaId());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.containsKey("id")) {
            product.setId(((Number) keys.get("id")).longValue());
        }
        return product;
    }

    // Update product
    public boolean update(Product product) {
        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, stock = ?, categoria_id = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql,
                product.getNombre(),
                product.getDescripcion(),
                product.getPrecio(),
                product.getStock(),
                product.getCategoriaId(),
                product.getId());
        return rowsAffected > 0;
    }

    // Delete product
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }
}
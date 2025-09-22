package com.demo.productmanagement.repository;

import com.demo.productmanagement.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Category> categoryRowMapper() {
        return (rs, rowNum) -> {
            Category category = new Category();
            category.setId(rs.getLong("id"));
            category.setNombre(rs.getString("nombre"));
            return category;
        };
    }

    public List<Category> findAll() {
        String sql = "SELECT * FROM categorias ORDER BY id";
        return jdbcTemplate.query(sql, categoryRowMapper());
    }

    public Optional<Category> findById(Long id) {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        try {
            Category category = jdbcTemplate.queryForObject(sql, new Object[]{id}, categoryRowMapper());
            return Optional.ofNullable(category);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Category save(Category category) {
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getNombre());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.containsKey("id")) {
            category.setId(((Number) keys.get("id")).longValue());
        }
        return category;
    }

    public boolean update(Category category) {
        String sql = "UPDATE categorias SET nombre = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, category.getNombre(), category.getId());
        return rowsAffected > 0;
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM categorias WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }
}

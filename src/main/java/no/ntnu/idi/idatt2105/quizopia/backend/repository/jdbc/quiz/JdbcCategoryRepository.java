package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.quiz;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz.CategoryRepository;

@Repository
public class JdbcCategoryRepository implements CategoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getCategoryById(Long categoryId) {
        String sql = "SELECT name FROM category WHERE category_id = ?";
        try {
            String category = jdbcTemplate.queryForObject(
                sql,
                new Object[]{categoryId},
                (rs, rowNum) -> rs.getString("name")
            );
            return category;
        } catch (Exception e) {
            return null; 
        }
    }
}
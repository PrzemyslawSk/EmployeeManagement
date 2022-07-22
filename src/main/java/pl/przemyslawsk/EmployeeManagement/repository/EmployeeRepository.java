package pl.przemyslawsk.EmployeeManagement.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.przemyslawsk.EmployeeManagement.model.Employee;

import java.util.List;

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Employee> getAll() {
        return jdbcTemplate.query("SELECT id, name, surname, date_of_employment FROM employees",
                BeanPropertyRowMapper.newInstance(Employee.class));
    }

    public Employee getById(long id) {
        return jdbcTemplate.queryForObject("SELECT id, name, surname, date_of_employment FROM employees " +
                "WHERE id = ?", BeanPropertyRowMapper.newInstance(Employee.class), id);
    }

    public int save(List<Employee> employees) {
        employees.forEach(employee -> jdbcTemplate
                .update("INSERT INTO employees(name, surname, date_of_employment) VALUES(?, ?, ?)",
                        employee.getName(), employee.getSurname(), new java.util.Date()
                ));

        return 1;
    }

    public int update(Employee employee) {
        return jdbcTemplate.update("UPDATE employees SET name=?, surname=? WHERE id=?",
                employee.getName(), employee.getSurname(), employee.getId());
    }

    public int delete(long id) {
        return jdbcTemplate.update("DELETE FROM employees WHERE id=?", id);
    }

}

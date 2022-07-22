package pl.przemyslawsk.EmployeeManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.przemyslawsk.EmployeeManagement.model.Employee;
import pl.przemyslawsk.EmployeeManagement.repository.EmployeeRepository;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(
                employeeRepository.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {
        try{
            Employee employee = employeeRepository.getById(id);
            return new ResponseEntity<>(
                    employee, HttpStatus.OK);
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }

    }

    @PostMapping("")
    public ResponseEntity<List<Employee>> addEmployee(@RequestBody List<Employee> employees) {
        employeeRepository.save(employees);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable("id") long id, @RequestBody Employee updatedEmployee) {
        try {
            Employee employee = employeeRepository.getById(id);

            if(employee != null) {
                employee.setName(updatedEmployee.getName());
                employee.setSurname(updatedEmployee.getSurname());
                employeeRepository.update(employee);

                return ResponseEntity.status(HttpStatus.OK)
                        .build();
            }
            else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .build();
            }
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable("id") long id) {
        employeeRepository.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}

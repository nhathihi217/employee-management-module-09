package com.example.employeemanagement.employee;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employees;
    public EmployeeService(EmployeeRepository employees) { this.employees = employees; }

    @Transactional(readOnly = true)
    public List<Employee> findAll() { return employees.findAll(); }

    public Employee create(EmployeeRequest request) {
        return employees.save(new Employee(request.name().trim(), request.email().trim()));
    }

    public Employee update(Long id, EmployeeRequest request) {
        Employee employee = employees.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return employees.save(new EmployeeWithId(employee, request).toEmployee());
    }

    public void delete(Long id) {
        if (!employees.existsById(id)) throw new EmployeeNotFoundException(id);
        employees.deleteById(id);
    }

    private record EmployeeWithId(Employee current, EmployeeRequest request) {
        Employee toEmployee() {
            // Updating a managed entity preserves its database identity.
            current.setName(request.name().trim());
            current.setEmail(request.email().trim());
            return current;
        }
    }
}

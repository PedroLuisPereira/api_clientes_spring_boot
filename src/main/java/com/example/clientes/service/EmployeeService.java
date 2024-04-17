package com.example.clientes.service;

import com.example.clientes.domain.Employee;

import java.util.List;


public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee getEmployeeById(long id);

    Employee saveEmployee(Employee employee);

    Employee updateEmployee(long id, Employee updatedEmployee);

    void deleteEmployee(long id);
}
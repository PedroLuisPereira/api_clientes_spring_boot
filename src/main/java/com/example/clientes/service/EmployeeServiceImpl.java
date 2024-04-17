package com.example.clientes.service;

import com.example.clientes.domain.Employee;
import com.example.clientes.exceptions.BadRequestException;
import com.example.clientes.exceptions.ResourceNotFoundException;
import com.example.clientes.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String REGISTRO_NO_ENCONTRADO = "Registro no econtrado";

    private static final String EMAIL_YA_EXISTE = "Email ya existe";

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(REGISTRO_NO_ENCONTRADO));
    }

    @Override
    public Employee saveEmployee(Employee employee) {

        //validaciones

        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if(savedEmployee.isPresent()){
            throw new ResourceNotFoundException(EMAIL_YA_EXISTE);
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(long id, Employee updatedEmployee) {

        //validaciones

        employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(REGISTRO_NO_ENCONTRADO));

        Optional<Employee> oldEmployee = employeeRepository.findByEmail(updatedEmployee.getEmail());
        if(oldEmployee.isPresent() && id != oldEmployee.get().getId() ){
            throw new BadRequestException(EMAIL_YA_EXISTE);
        }

        Employee employee = Employee.builder()
                .id(id)
                .firstName(updatedEmployee.getFirstName())
                .lastName(updatedEmployee.getLastName())
                .email(updatedEmployee.getEmail())
                .build();

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }
}
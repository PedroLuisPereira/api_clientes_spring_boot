package com.example.clientes.service;


import static org.assertj.core.api.Assertions.assertThat;

import com.example.clientes.domain.Employee;
import com.example.clientes.exceptions.BadRequestException;
import com.example.clientes.exceptions.ResourceNotFoundException;
import com.example.clientes.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTests {

    private static final String REGISTRO_NO_ENCONTRADO = "Registro no econtrado";

    private static final String EMAIL_YA_EXISTE = "Email ya existe";

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .id(1L)
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();
    }

    // JUnit test for getAllEmployees method
    @DisplayName("JUnit test for getAllEmployees method")
    @Test
    void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
        // given - precondition or setup

        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tony")
                .lastName("Stark")
                .email("tony@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        // when -  action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        Assertions.assertNotNull(employeeList);
        Assertions.assertEquals(2, employeeList.size());
    }

    // JUnit test for getAllEmployees method
    @DisplayName("JUnit test for getAllEmployees method (negative scenario)")
    @Test
    void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        // given - precondition or setup
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when -  action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isEmpty();
    }


    // JUnit test for getEmployeeById method
    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // when
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId());

        // then
        assertThat(savedEmployee).isNotNull();
        Assertions.assertEquals("ramesh@gmail.com", savedEmployee.getEmail());

    }

    @Test
    void givenEmployeeId_whenGetEmployeeById_thenReturnException() {

        Mockito.when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(anyLong());
        });

        Assertions.assertEquals(REGISTRO_NO_ENCONTRADO, thrown.getMessage());
        Mockito.verify(employeeRepository, times(1)).findById(anyLong());
    }

    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

        // given - precondition or setup
        Mockito.when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);

        // when -  action or the behaviour that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        Assertions.assertEquals("ramesh@gmail.com", savedEmployee.getEmail());
    }

    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method which throws exception")
    @Test
    void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        // given - precondition or setup
        Mockito.when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));

        // when -  action or the behaviour that we are going test
        BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then
        Assertions.assertEquals(EMAIL_YA_EXISTE, thrown.getMessage());
        Mockito.verify(employeeRepository, times(1)).findByEmail(employee.getEmail());

    }


    // JUnit test for updateEmployee method
    @DisplayName("JUnit test for updateEmployee method")
    @Test
    void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        // given - precondition or setup
        Employee updateEmployee = Employee.builder()
                .id(1L)
                .firstName("Juan")
                .lastName("Lopez")
                .email("ramesh@gmail.com")
                .build();

        Mockito.when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        Mockito.when(employeeRepository.save(any())).thenReturn(updateEmployee);

        // when -  action or the behaviour that we are going test
        updateEmployee = employeeService.updateEmployee(1L, updateEmployee);

        // then - verify the output
        assertThat(updateEmployee).isNotNull();
        Assertions.assertEquals("ramesh@gmail.com", updateEmployee.getEmail());

    }

    // JUnit test for updateEmployee method
    @DisplayName("JUnit test for updateEmployee method")
    @Test
    void givenEmployeeObject_whenUpdateEmployee_thenReturnExceptionById() {

        // given - precondition or setup
        Employee updateEmployee = Employee.builder()
                .id(1L)
                .firstName("Juan")
                .lastName("Lopez")
                .email("ramesh@gmail.com")
                .build();

        Mockito.when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(1L, updateEmployee);
        });

        // then
        Assertions.assertEquals(REGISTRO_NO_ENCONTRADO, thrown.getMessage());
        Mockito.verify(employeeRepository, times(1)).findById(anyLong());

    }

    // JUnit test for updateEmployee method
    @DisplayName("JUnit test for updateEmployee method")
    @Test
    void givenEmployeeObject_whenUpdateEmployee_thenReturnExceptionByEmail() {

        // given - precondition or setup
        Employee updateEmployee = Employee.builder()
                .id(1L)
                .firstName("Juan")
                .lastName("Lopez")
                .email("ramesh@gmail.com")
                .build();

        Employee oterEmployee = Employee.builder()
                .id(10L)
                .firstName("Maria")
                .lastName("Lopez")
                .email("ramesh@gmail.com")
                .build();

        Mockito.when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.of(oterEmployee));

        // when -  action or the behaviour that we are going test
        BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () -> {
            employeeService.updateEmployee(1L, updateEmployee);
        });

        // then
        Assertions.assertEquals(EMAIL_YA_EXISTE, thrown.getMessage());
        Mockito.verify(employeeRepository, times(1)).findById(anyLong());

    }

    // JUnit test for deleteEmployee method
    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        // given - precondition or setup
        long employeeId = 1L;

        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when -  action or the behaviour that we are going test
        employeeService.deleteEmployee(employeeId);

        // then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}
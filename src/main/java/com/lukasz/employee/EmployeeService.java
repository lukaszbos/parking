package com.lukasz.employee;

import com.lukasz.parking.Parking;
import com.lukasz.parking.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private ParkingRepository parkingRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ParkingRepository parkingRepository) {
        this.employeeRepository = employeeRepository;
        this.parkingRepository = parkingRepository;
    }

    List<Employee> getEmployee(Integer parkingId) {
        if (isIdSent(parkingId)) {
            return getEmployeesWorkingOnParking(parkingId);
        } else
            return getAllEmployees();
    }

    private boolean isIdSent(Integer parkingId) {
        return parkingId != null;
    }

    private List<Employee> getEmployeesWorkingOnParking(Integer parkingId) {
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findByParking_ParkingId(parkingId).forEach(employees::add);
        return employees;
    }

    private List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
        return employees;
    }

    Employee getEmployeeById(Integer employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    void addEmployee(Employee employee) {
        assembleTheEmployee(employee);
        employeeRepository.save(employee);
    }

    private void assembleTheEmployee(Employee employee) {
        Parking parkingOfEmployee = assembleParking(employee);
        employee.setParking(parkingOfEmployee);
    }

    private Parking assembleParking(Employee employee) {
        Integer parkingIdOfEmployee = employee.getParking().getParkingId();
        String parkingNameOfEmployee = employee.getParking().getName();

        if (isThisParkingInMyRepo(parkingIdOfEmployee))
            return getParkingById(parkingIdOfEmployee);
        else
            return new Parking(parkingIdOfEmployee, parkingNameOfEmployee);
    }

    private boolean isThisParkingInMyRepo(Integer parkingIdFromEmployee) {
        return parkingRepository.existsById(parkingIdFromEmployee);
    }

    private Parking getParkingById(Integer parkingId) {
        return parkingRepository.findById(parkingId).get();
    }

    void updateEmployee(Employee employee) {
        assembleTheEmployee(employee);
        employeeRepository.save(employee);
    }

    void deleteEmployee(Integer employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
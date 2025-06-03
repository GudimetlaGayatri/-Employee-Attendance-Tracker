// AttendanceController.java
package com.example.attendance.controller;

import com.example.attendance.entity.*;
import com.example.attendance.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/{employeeId}")
    public Attendance markAttendance(@PathVariable Long employeeId, @RequestParam String status) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(LocalDate.now());
        attendance.setStatus(status);
        return attendanceRepository.save(attendance);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Attendance> getEmployeeAttendance(@PathVariable Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        return attendanceRepository.findByEmployee(employee);
    }

    @GetMapping("/department-summary")
    public List<Object[]> getDepartmentSummary() {
        return attendanceRepository.getDepartmentAttendanceSummary();
    }
}


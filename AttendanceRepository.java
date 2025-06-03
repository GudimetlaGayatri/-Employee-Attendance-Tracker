// AttendanceRepository.java
package com.example.attendance.repository;

import com.example.attendance.entity.*;
import org.springframework.data.jpa.repository.*;
import java.time.LocalDate;
import java.util.*;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployee(Employee employee);
    List<Attendance> findByEmployeeAndDate(Employee employee, LocalDate date);
    @Query("SELECT a.employee.department, COUNT(a) FROM Attendance a WHERE a.status='Present' GROUP BY a.employee.department")
    List<Object[]> getDepartmentAttendanceSummary();
}


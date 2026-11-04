package com.dropsense.DropsenseProject.repository;

import com.dropsense.DropsenseProject.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String>
{
    List<Student> findByFullNameContainingIgnoreCase(String fullName);
}

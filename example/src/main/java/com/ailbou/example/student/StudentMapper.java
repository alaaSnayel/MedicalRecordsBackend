package com.ailbou.example.student;

import org.springframework.stereotype.Service;

import com.ailbou.example.school.School;

@Service
public class StudentMapper {
  
  public Student toStudent(StudentDto dto) {
    if (dto == null) {
      throw new NullPointerException("StudentDto should not be null");
    }

    var student = new Student();

    student.setFirstname(dto.firstname());
    student.setLastname(dto.lastname());
    student.setEmail(dto.email());

    var school = new School();
    school.setId(dto.schoolId());

    student.setSchool(school);

    return student;
  }
  
  public StudentResponseDto toStudentResponseDto(Student student){
    return new StudentResponseDto(student.getFirstname(), student.getLastname(), student.getEmail());
  }
  
}

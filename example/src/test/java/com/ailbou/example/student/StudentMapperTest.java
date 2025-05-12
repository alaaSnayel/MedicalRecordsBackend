package com.ailbou.example.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentMapperTest {

  private StudentMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new StudentMapper();
  }

  @Test
  public void shouldMapStudentDtoToStudent() {
    StudentDto dto = new StudentDto(
      "john",
      "Doe",
      "alaanayel@gmail.com",
      1
    );

    Student student = mapper.toStudent(dto);

    assertEquals(dto.firstname(), student.getFirstname());
    assertEquals(dto.lastname(), student.getLastname());
    assertEquals(dto.email(), student.getEmail());
    assertNotNull(student.getSchool());
    assertEquals(dto.schoolId(), student.getSchool().getId());
  }

  @Test
  public void should_throw_nullPointerException_student_when_studentDto_is_null() {

    var exp = assertThrows(NullPointerException.class, () -> mapper.toStudent(null));

    assertEquals("StudentDto should not be null", exp.getMessage());
  }

  @Test
  public void shouldMapStudentToStudentResponseDto() {
    // Given
    Student student = new Student(
      "jane",
      "ali",
      "alaa1@gmail.com",
      20
    );

    // When
    StudentResponseDto response = mapper.toStudentResponseDto(student);

    // Then
    assertEquals(response.firstname(), student.getFirstname());
    assertEquals(response.firstname(), student.getFirstname());
    assertEquals(response.email(), student.getEmail());
  }

}

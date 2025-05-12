package com.ailbou.example.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StudentServiceTest {
  // which service we want to test
  @InjectMocks
  private StudentService studentService;

  // declare the dependency
  @Mock
  StudentRepository repository;

  @Mock
  StudentMapper studentMapper;

  @BeforeEach
  void setUp() {
   MockitoAnnotations.openMocks(this);
  }

  @Test
  public void should_successfully_save_student() {
    // given
    StudentDto dto = new StudentDto("John", "Doe", "john@gmail.com", 1);
    Student student = new Student("John", "Doe", "john@gmail.com", 26);
    Student savedStudent = new Student("John", "Doe", "john@gmail.com", 26);

    savedStudent.setId(1);

    // mok the calls
    when(studentMapper.toStudent(dto)).thenReturn(student);
    when(repository.save(student)).thenReturn(savedStudent);
    when(studentMapper.toStudentResponseDto(savedStudent))
      .thenReturn(new StudentResponseDto("John", "Doe","john@gmail.com"));

    // when
    StudentResponseDto responseDto = studentService.saveStudent(dto);

    // then
    assertEquals(dto.firstname(), responseDto.firstname());
    assertEquals(dto.lastname(), responseDto.lastname());
    assertEquals(dto.email(), responseDto.email());

    verify(studentMapper, times(1)).toStudent(dto);
    verify(repository, times(1)).save(student);
    verify(studentMapper, times(1)).toStudentResponseDto(savedStudent);
  }

  @Test
  public void should_return_all_students(){
    // given
    List<Student> students = new ArrayList<>();
    students.add( new Student("John", "Doe", "john@gmail.com", 26));

    // Mock the calls
    when(repository.findAll()).thenReturn(students);
    when(studentMapper.toStudentResponseDto(any(Student.class)))
      .thenReturn(new StudentResponseDto("John", "Doe","john@gmail.com"));

    // when
    List<StudentResponseDto> responseDtos = studentService.findAllStudent();

    // then
    assertEquals(students.size(), responseDtos.size());
    verify(repository, times(1)).findAll();
  }

  @Test
  public void should_return_student_by_id() {
    // given
    Integer studentId = 1;
    Student student = new Student("John", "Doe", "john@gmail.com", 26);

    // Mock the calls
    when(repository.findById(studentId)).thenReturn(java.util.Optional.of(student));
    when(studentMapper.toStudentResponseDto(student))
      .thenReturn(new StudentResponseDto("John", "Doe","john@gmail.com"));

    // when
    StudentResponseDto dto = studentService.findStudentById(studentId);

    // then
    assertEquals(dto.firstname(), student.getFirstname());
    assertEquals(dto.lastname(), student.getLastname());
    assertEquals(dto.email(), student.getEmail());

    verify(repository, times(1)).findById(studentId);

  }

  @Test
  public void should_find_student_by_name() {
    // given
    String studentName = "John";
    
    List<Student> students = new ArrayList<>();
    students.add( new Student("John", "Doe", "john@gmail.com", 26));

    // Mock the calls
    when(repository.findAllByFirstnameContaining(studentName)).thenReturn(students);
    when(studentMapper.toStudentResponseDto(any(Student.class)))
      .thenReturn(new StudentResponseDto("John", "Doe","john@gmail.com"));

    // when
    var responseDtos = studentService.findStudentByName(studentName);

    // then
    assertEquals(students.size(), responseDtos.size());
    verify(repository, times(1)).findAllByFirstnameContaining(studentName);
  }
}

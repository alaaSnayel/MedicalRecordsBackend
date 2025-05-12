package com.ailbou.example.school;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {
  private final SchoolRepository schoolRepository;
  private final SchoolMapper schoolMapper;

  public SchoolService(SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
    this.schoolRepository = schoolRepository;
    this.schoolMapper = schoolMapper;
  }

  public SchoolDto create(SchoolDto dto) {

    var school = schoolMapper.toSchool(dto);
    schoolRepository.save(school);
    return dto;
  }

  public List<SchoolDto> findAll() {
    return schoolRepository.findAll().stream().map(schoolMapper::toSchoolDto).collect(Collectors.toList());
  }
}

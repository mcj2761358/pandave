package com.minutch.fox.biz.dance;

import com.minutch.fox.entity.dance.Student;
import com.minutch.fox.param.dance.StudentQueryParam;

import java.util.List;

public interface StudentService {
    List<Student> getAll();

    Student getById(Long id);

    boolean save(Student student);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

    List<Student> queryStudent(StudentQueryParam param);

    int queryStudentCount(StudentQueryParam param);
}

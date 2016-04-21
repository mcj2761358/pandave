package com.minutch.pandave.biz.dance;

import com.minutch.pandave.entity.dance.Student;
import com.minutch.pandave.param.dance.StudentQueryParam;

import java.util.List;

public interface StudentService {
    public List<Student> getAll();

    public Student getById(Long id);

    public boolean save(Student student);

    public boolean deleteById(Long id);

    public int deleteByIds(Long[] ids);

    public List<Student> queryStudent(StudentQueryParam param);

    public int queryStudentCount(StudentQueryParam param);
}

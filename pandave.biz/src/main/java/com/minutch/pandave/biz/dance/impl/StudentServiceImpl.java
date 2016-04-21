package com.minutch.pandave.biz.dance.impl;

import java.util.List;

import com.minutch.pandave.biz.base.BaseServiceImpl;
import com.minutch.pandave.param.dance.StudentQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.pandave.biz.dance.StudentService;
import com.minutch.pandave.dao.dance.StudentDao;
import com.minutch.pandave.entity.dance.Student;


@Service
public class StudentServiceImpl extends BaseServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    public List<Student> getAll() {
        return super.getAll(studentDao);
    }

    public Student getById(Long id) {
        return super.getById(studentDao, id);
    }

    public boolean save(Student student) {
        return super.save(studentDao, student);
    }

    public boolean deleteById(Long id) {
        return super.deleteById(studentDao, id);
    }

    public int deleteByIds(Long[] ids) {
        return super.deleteByIds(studentDao, ids);
    }

    @Override
    public List<Student> queryStudent(StudentQueryParam param) {
        return studentDao.queryStudent(param,param.getStart(),param.getEnd());
    }

    @Override
    public int queryStudentCount(StudentQueryParam param) {
        return studentDao.queryStudentCount(param);
    }
}

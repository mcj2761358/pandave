package com.minutch.fox.biz.dance.impl;

import java.util.List;

import com.minutch.fox.biz.base.BaseServiceImpl;
import com.minutch.fox.param.dance.StudentQueryParam;
import com.minutch.fox.biz.dance.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.fox.dao.dance.StudentDao;
import com.minutch.fox.entity.dance.Student;


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

package com.minutch.fox.dao.dance;

import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.dance.Student;
import com.minutch.fox.param.dance.StudentQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface StudentDao extends BaseDao<Student> {

    List<Student> queryStudent(@Param("param") StudentQueryParam param, @Param("start") int start, @Param("limit") int limit);

    int queryStudentCount(@Param("param") StudentQueryParam param);
}

package com.minutch.fox.web.dance;

import com.minutch.fox.biz.dance.StudentService;
import com.minutch.fox.entity.dance.Student;
import com.minutch.fox.param.Result;
import com.minutch.fox.param.dance.StudentQueryParam;
import com.minutch.fox.result.PageResultVO;
import com.minutch.fox.result.dance.StudentVO;
import com.minutch.fox.utils.FoxBeanUtils;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Minutch on 16/3/19.
 */
@Controller
@RequestMapping("student")
@Slf4j
public class StudentController  extends BaseController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("queryStudent/employee/auth")
    @ResponseBody
    public Result<?> queryStudent(@RequestBody StudentQueryParam param){

        int totalNum = studentService.queryStudentCount(param);
        PageResultVO<StudentVO> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageSize(param.getPageSize());
        pageResultVO.setCurPage(param.getCurPage());
        pageResultVO.setTotalSize(totalNum);

        if (totalNum > 0) {
            List<Student> studentList = studentService.queryStudent(param);
            pageResultVO.setDataList(FoxBeanUtils.copyList(studentList, StudentVO.class));
        } else {
            pageResultVO.setDataList(new ArrayList<StudentVO>());
        }

        return Result.wrapSuccessfulResult(pageResultVO);
    }
}

package com.minutch.fox.web.dance;

import com.minutch.fox.biz.dance.EmployeeService;
import com.minutch.fox.entity.dance.Employee;
import com.minutch.fox.param.Result;
import com.minutch.fox.param.dance.StudentQueryParam;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Minutch on 16/3/19.
 */
@Controller
@RequestMapping("dance")
@Slf4j
public class DanceController  extends BaseController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping()
    public String system(){


        return "dance/login";
    }

    @RequestMapping("mainpage/employee/auth")
    public String mainpage(Model model){

        Long employeeId = getEmployeeId();
        Employee employee = employeeService.getById(employeeId);
        if (employee == null) {
            throw new RuntimeException("不存在的用户ID！");
        }

        model.addAttribute("name",employee.getName());
        model.addAttribute("mobilephone",employee.getMobilePhone());

        return "dance/mainpage";
    }

    @RequestMapping("queryAll")
    @ResponseBody
    public Result<?> queryAll(@RequestBody StudentQueryParam param){



        return Result.wrapSuccessfulResult("A");
    }
}

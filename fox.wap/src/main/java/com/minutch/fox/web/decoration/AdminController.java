package com.minutch.fox.web.decoration;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minutch.fox.biz.decoration.EmployeeService;
import com.minutch.fox.biz.decoration.StoreService;
import com.minutch.fox.constants.dance.EmployeeConstants;
import com.minutch.fox.entity.decoration.Employee;
import com.minutch.fox.entity.decoration.Store;
import com.minutch.fox.param.Result;
import com.minutch.fox.param.decoration.StoreParam;
import com.minutch.fox.param.decoration.StoreQueryParam;
import com.minutch.fox.result.PageResultVO;
import com.minutch.fox.result.decoration.StoreVO;
import com.minutch.fox.utils.ConvertInterface;
import com.minutch.fox.utils.DataCheckUtils;
import com.minutch.fox.utils.FoxBeanUtils;
import com.minutch.fox.utils.ListUtils;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Minutch on 17/9/4.
 */
@Controller
@RequestMapping("admin")
@Slf4j
public class AdminController extends BaseController{

    @Autowired
    private StoreService storeService;
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("queryStoreList")
    @ResponseBody
    public Result<?> queryStoreList(@RequestBody StoreQueryParam param) {

        if (!storeService.isAdmin(getStoreId())) {
            log.error("storeId不是admin");
            return Result.wrapErrorResult("", "无权访问");
        }

        int totalNum = storeService.queryStoreCount(param);
        PageResultVO<StoreVO> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageSize(param.getPageSize());
        pageResultVO.setCurPage(param.getCurPage());
        pageResultVO.setTotalSize(totalNum);
        if (totalNum > 0) {
            List<Store> storeList = storeService.queryStore(param);
            List<StoreVO> voList = FoxBeanUtils.copyList(storeList, StoreVO.class);
            pageResultVO.setDataList(voList);

            //处理主账号权限
            if (ListUtils.isNotBlank(voList)) {
                List<Long> storeIdList = new ArrayList<>();
                for (StoreVO vo : voList) {
                    storeIdList.add(vo.getId());
                }

                //查询主账号权限
                List<Employee> employeeList = employeeService.queryMainEmps(storeIdList);
                Map<Long, Employee> employeeMap = ListUtils.convertToMapValue(employeeList, new ConvertInterface<Long, Employee>() {
                    @Override
                    public Long convert(Employee param) {
                        return param.getStoreId();
                    }
                });

                //将权限放到store中
                for (StoreVO vo : voList) {
                    Long storeId = vo.getId();
                    Employee employee = employeeMap.get(storeId);
                    if (employee == null) {
                        continue;
                    }
                    String permissionRoleJson = employee.getPermissionRule();
                    if (StringUtils.isBlank(permissionRoleJson)) {
                        continue;
                    }
                    Map<String, String> permissionMap =
                            new Gson().fromJson(employee.getPermissionRule(),
                                    new TypeToken<Map<String, String>>(){}.getType());

                    List<String> permissionRoleList = new ArrayList<>();
                    if (employeeMap!=null && employeeMap.size()>0) {
                        for (String key:permissionMap.keySet()) {
                            String value = permissionMap.get(key);
                            if ("Y".equals(value)) {
                                permissionRoleList.add(key);
                            }
                        }
                    }
                    vo.setPermissionRoleList(permissionRoleList);
                }
            }

        } else {
            pageResultVO.setDataList(new ArrayList<StoreVO>());
        }
        return Result.wrapSuccessfulResult(pageResultVO);
    }

    @RequestMapping("save")
    @ResponseBody
    public Result<?> save(@RequestBody StoreParam param) {

        if (!storeService.isAdmin(getStoreId())) {
            log.error("storeId不是admin");
            return Result.wrapErrorResult("", "无权修改");
        }

        //判断信息是否正确
        if (StringUtils.isBlank(param.getStoreName())) {
            log.error("商户名称不能为空.");
            return Result.wrapErrorResult("","商户姓名不能为空.");
        }
        if (StringUtils.isBlank(param.getMobilePhone())) {
            log.error("商户手机号码不能为空.");
            return Result.wrapErrorResult("","商户手机号码不能为空.");
        }
        if (!DataCheckUtils.checkMobile(param.getMobilePhone())) {
            log.error("商户手机号码格式不正确.");
            return Result.wrapErrorResult("","商户手机号码格式不正确.");
        }

        //拼装授权信息
        HashMap permissionMap = new HashMap();;
        if (ListUtils.isNotBlank(param.getPermissionRoleList())) {
            for (String permission:param.getPermissionRoleList()) {
                permissionMap.put(permission, "Y");
            }
        }

        Store store;
        Employee employee = null;
        if (param.getId() == null) {
            //判断当前手机号是否已经被注册
            store = storeService.queryByMobilePhone(param.getMobilePhone());
            if (store != null) {
                log.error("商户[" + param.getMobilePhone() + "]已存在.");
                return Result.wrapErrorResult("", "商户户[" + param.getMobilePhone() + "]已存在，请到[商户管理]查询此商户信息.");
            }

            employee = new Employee();
            employee.setEmpMobile(param.getMobilePhone());
            employee.setDefaultBizValue(getEmpId());
            employee.setEmpName(param.getStoreName());
            if (StringUtils.isBlank(param.getPassword().trim())) {
                employee.setEmpPassword("123456");
            } else {
                employee.setEmpPassword(param.getPassword().trim());
            }
            employee.setBeMain("Y");
        } else {
            //查询主账号
            employee = employeeService.queryMainEmp(param.getId());
        }

        store = new Store();
        BeanUtils.copyProperties(param, store);
        store.setDefaultBizValue(getEmpId());
        storeService.save(store);
        if (employee != null) {
            employee.setPermissionRule(new Gson().toJson(permissionMap));
            employee.setStoreId(store.getId());
            employeeService.save(employee);
        }

        return Result.wrapSuccessfulResult(null);
    }



    @RequestMapping("storeRegister")
    @ResponseBody
    public Result<?> storeRegister(@RequestBody StoreParam param, HttpServletRequest request) {

        //判断信息是否正确
        if (StringUtils.isBlank(param.getStoreName())) {
            log.error("商户名称不能为空.");
            return Result.wrapErrorResult("","商户姓名不能为空.");
        }
        if (StringUtils.isBlank(param.getMobilePhone())) {
            log.error("商户手机号码不能为空.");
            return Result.wrapErrorResult("","商户手机号码不能为空.");
        }
        if (!DataCheckUtils.checkMobile(param.getMobilePhone())) {
            log.error("商户手机号码格式不正确.");
            return Result.wrapErrorResult("","商户手机号码格式不正确.");
        }
        if (StringUtils.isBlank(param.getPassword())) {
            log.error("请设置登录密码.");
            return Result.wrapErrorResult("","请设置登录密码.");
        }

        Store store;
        Employee employee = null;
        if (param.getId() == null) {
            //判断当前手机号是否已经被注册
            store = storeService.queryByMobilePhone(param.getMobilePhone());
            if (store != null) {
                log.error("商户[" + param.getMobilePhone() + "]已存在.");
                return Result.wrapErrorResult("", "商户户[" + param.getMobilePhone() + "]已存在，请到[商户管理]查询此商户信息.");
            }

            employee = new Employee();
            employee.setEmpMobile(param.getMobilePhone());
            employee.setDefaultBizValue(getEmpId());
            employee.setEmpName(param.getStoreName());
            if (StringUtils.isBlank(param.getPassword().trim())) {
                employee.setEmpPassword("123456");
            } else {
                employee.setEmpPassword(param.getPassword().trim());
            }
            employee.setBeMain("Y");
        }

        store = new Store();
        store.setStoreName(param.getStoreName());
        store.setMobilePhone(param.getMobilePhone());
        store.setDefaultBizValue(getEmpId());
        storeService.save(store);
        if (employee != null) {
            employee.setStoreId(store.getId());
            employeeService.save(employee);
        }


        request.getSession().setAttribute(EmployeeConstants.ATTRIBUTE_STORE_ID, employee.getStoreId());
        request.getSession().setAttribute(EmployeeConstants.ATTRIBUTE_EMPLOYEE_ID, employee.getId());
        request.getSession().setAttribute(EmployeeConstants.ATTRIBUTE_EMPLOYEE_NAME, employee.getEmpName());
        request.getSession().setAttribute(EmployeeConstants.ATTRIBUTE_STORE_NAME, store.getStoreName());
        return Result.wrapSuccessfulResult(null);
    }


//    @RequestMapping("deleteById")
//    @ResponseBody
//    public Result<?> deleteById(Long storeId) {
//
//        if (storeId == null) {
//            log.error("商户id不能为空！");
//            return Result.wrapErrorResult("","商户ID不能为空!");
//        }
//        storeService.deleteById(storeId);
//        log.info("delete customer["+storeId+"]");
//        return Result.wrapSuccessfulResult(null);
//    }

    @RequestMapping("storeList")
    public String orderList(Model model) {

        if (!storeService.isAdmin(getStoreId())) {

            log.error("当前商家["+getStoreId()+"]无权查看该页面");
            model.addAttribute("errorMsg", "您不能查看人家隐私哦！");
            return "decoration/error500";
        }
        return "decoration/storeList";
    }

}

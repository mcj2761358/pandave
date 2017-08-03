package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.StoreService;
import com.minutch.fox.constants.dance.EmployeeConstants;
import com.minutch.fox.entity.decoration.Store;
import com.minutch.fox.param.Result;
import com.minutch.fox.param.decoration.LoginParam;
import com.minutch.fox.param.decoration.ModifyPasswordParam;
import com.minutch.fox.utils.DataCheckUtils;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Minutch on 17/7/28.
 */
@Controller
@RequestMapping("store")
@Slf4j
public class StoreController extends BaseController {

    @Autowired
    private StoreService storeService;

    @RequestMapping("login")
    @ResponseBody
    public Result<?> login(@RequestBody LoginParam param, HttpServletRequest request) {

        //检查手机号
        String mobilePhone = param.getMobilePhone();
        String password = param.getPassword();

        if (StringUtils.isBlank(mobilePhone)) {
            log.error("手机号不能为空.");
            return Result.wrapErrorResult("", "手机号不能为空.");
        }
        if (!DataCheckUtils.checkMobile(mobilePhone)) {
            log.error("手机号码[" + mobilePhone + "]格式不正确.");
            return Result.wrapErrorResult("", "手机号格式不正确.");
        }
        if (StringUtils.isBlank(password)) {
            log.error("密码不能为空.");
            return Result.wrapErrorResult("", "密码不能为空.");
        }

        Store store = storeService.queryByMobilePhone(mobilePhone);
        if (store == null) {
            log.error("该手机号[" + mobilePhone + "]暂未注册，请联系管理员.");
            return Result.wrapErrorResult("", "手机号[" + mobilePhone + "]暂未注册，请联系管理员.");
        }

        //检查密码
        if (!password.equals(store.getPassword())) {
            log.error("密码[" + password + "]与系统登记的密码[" + store.getPassword() + "]不一致.");
            return Result.wrapErrorResult("", "密码错误,请新输入密码.");
        }

        request.getSession().setAttribute(EmployeeConstants.ATTRIBUTE_STORE_ID, store.getId());
        request.getSession().setAttribute("storeName", store.getStoreName());
//        ServletContext application = request.getSession().getServletContext();
//        application.setAttribute("storeName",store.getStoreName());
        return Result.wrapSuccessfulResult(null);
    }


    @RequestMapping("logout")
    @ResponseBody
    public Result<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        log.info("logout:" + getStoreId() + "退出成功！");
        return Result.wrapSuccessfulResult(null);
    }

    @RequestMapping("modifyPassword")
    @ResponseBody
    public Result<?> modifyPassword(@RequestBody ModifyPasswordParam param) {

        //检查密码
        if (StringUtils.isBlank(param.getNewPassword())) {
            log.error("密码不能为空.");
            return Result.wrapErrorResult("", "新密码不能为空.");
        }

        Long storeId = getStoreId();
        if (storeId == null) {
            log.error("暂未登录.");
            return Result.wrapErrorResult("", "请先登录.");
        }

        storeService.updatePassword(storeId, param.getNewPassword());

        return Result.wrapSuccessfulResult(null);
    }


}
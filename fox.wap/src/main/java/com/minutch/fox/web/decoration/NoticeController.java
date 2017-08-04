package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.NoticeService;
import com.minutch.fox.entity.decoration.Notice;
import com.minutch.fox.param.Result;
import com.minutch.fox.result.decoration.NoticeVO;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by Minutch on 17/8/4.
 */
@Controller
@RequestMapping("notice")
@Slf4j
public class NoticeController extends BaseController {

    //缓存已经读取过通知的用户
    private static Map<Long, Set<Long>> noticedStoreMap = new HashMap<>();
    //缓存已经查询过的通知
    private static Notice notice;
    @Autowired
    private NoticeService noticeService;

    @RequestMapping("queryNewNotice")
    @ResponseBody
    public Result<?> queryNewNotice() {

        if (notice == null) {
            notice = noticeService.queryNewNotice();
        }

        Long storeId = getStoreId();

        if (notice != null) {

            NoticeVO vo = new NoticeVO();
            BeanUtils.copyProperties(notice, vo);

            Long noticeId = notice.getId();
            Set<Long> storeIdList = noticedStoreMap.get(noticeId);
            if (storeIdList == null) {
                return Result.wrapSuccessfulResult(vo);

            } else {
                if (!storeIdList.contains(storeId)) {
                    return Result.wrapSuccessfulResult(vo);
                }
            }
        }

        return Result.wrapSuccessfulResult(null);
    }

    @RequestMapping("clearNewNotice")
    @ResponseBody
    public Result<?> clearNewNotice() {

        notice = null;
        noticedStoreMap = new HashMap<>();
        return Result.wrapSuccessfulResult(null);
    }


    @RequestMapping("closeNewNotice")
    @ResponseBody
    public Result<?> closeNewNotice(Long noticeId) {

        if (noticeId == null) {
            return Result.wrapSuccessfulResult(null);
        }

        Long storeId = getStoreId();
        if (storeId == null) {
            return Result.wrapSuccessfulResult(null);
        }

        Set<Long> storeIdList = noticedStoreMap.get(noticeId);
        if (storeIdList == null) {
            storeIdList = new HashSet<>();
            noticedStoreMap.put(noticeId, storeIdList);
            storeIdList.add(storeId);
            return Result.wrapSuccessfulResult(null);
        } else {
            storeIdList.add(storeId);
            return Result.wrapSuccessfulResult(null);

        }
    }
}

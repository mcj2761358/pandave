package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.NoticeService;
import com.minutch.fox.entity.decoration.Notice;
import com.minutch.fox.param.Result;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Minutch on 17/8/4.
 */
@Controller
@RequestMapping("notice")
@Slf4j
public class NoticeController extends BaseController{

    //缓存已经读取过通知的用户
    private static Map<Long, List<Long>> noticedStoreMap = new HashMap<>();
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

        if (notice != null) {
            Long noticeId = notice.getId();
            List<Long> storeIdList = noticedStoreMap.get(noticeId);
            if (storeIdList == null) {
                storeIdList = new ArrayList<>();
                noticedStoreMap.put(noticeId, storeIdList);
            }
        }

        return null;
    }
}

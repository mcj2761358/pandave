package com.minutch.fox.web.topic;

import com.minutch.fox.biz.topic.TopicService;
import com.minutch.fox.param.Result;
import com.minutch.fox.param.topic.TopicQueryParam;
import com.minutch.fox.result.PageResultVO;
import com.minutch.fox.view.topic.TopicAnswerView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Minutch on 16/4/22.
 */
@Controller
@RequestMapping("topic")
@Slf4j
public class TopicController {



    @Autowired
    private TopicService topicService;

    @RequestMapping("queryTopicAnswer")
    @ResponseBody
    public Result<?> queryTopicAnswer(@RequestBody TopicQueryParam param){

        int totalNum = topicService.queryTopicAnswerCount(param);
        PageResultVO<TopicAnswerView> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageSize(param.getPageSize());
        pageResultVO.setCurPage(param.getCurPage());
        pageResultVO.setTotalSize(totalNum);

        if (totalNum > 0) {
            List<TopicAnswerView> topicAnswerList = topicService.queryTopicAnswer(param);
            makeMainPageTopicAnswer(topicAnswerList);
            pageResultVO.setDataList(topicAnswerList);
        } else {
            pageResultVO.setDataList(new ArrayList<TopicAnswerView>());
        }

        return Result.wrapSuccessfulResult(pageResultVO);
    }

    @RequestMapping("queryAnswerDetail/{answerId}")
    public String queryAnswerDetail(@PathVariable("answerId") Long answerId,Model model) {
        TopicAnswerView topicAnswerView = topicService.queryAnswerDetail(answerId);
        model.addAttribute("answerDetail",topicAnswerView);
        return "topic/answerDetail";
    }


    private static final int contentLength = 100;
    /**
     * 修改首页话题回复显示内容
     * @param topicAnswerViewList
     */
    private void makeMainPageTopicAnswer(List<TopicAnswerView> topicAnswerViewList) {

        if (topicAnswerViewList==null || topicAnswerViewList.size()==0) {
            log.warn("topicAnswerList is null.");
            return;
        }
        for (TopicAnswerView answerView: topicAnswerViewList) {
            String content = answerView.getAnswerContent();
            if (StringUtils.isBlank(content)) {
                continue;
            }
            if (content.getBytes().length<=3*contentLength) {
                answerView.setAnswerContentBrief(content);
                continue;
            }
            int sumChineseChar = 0;
            StringBuffer brief = new StringBuffer();
            for (int i=0;i<content.length();i++) {
                char contentItem = content.charAt(i);
                boolean isChineseChar = isChineseChar(contentItem);
                if (isChineseChar) {
                    sumChineseChar ++;
                }
                brief.append(String.valueOf(contentItem));
                if (sumChineseChar == contentLength) {
                    brief.append("......<a href='javascript:readAllContent("+answerView.getAnswerId()+")' >阅读全文</a>");
                    break;
                }
            }
            answerView.setAnswerContentBrief(brief.toString());
        }
    }


    private boolean isChineseChar(char item) {
        int length = String.valueOf(item).getBytes().length;

        return length == 3;
    }
}


































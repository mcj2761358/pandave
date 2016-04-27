package com.minutch.pandave.web.topic;

import com.minutch.pandave.biz.topic.TopicService;
import com.minutch.pandave.entity.topic.Topic;
import com.minutch.pandave.entity.topic.TopicAnswer;
import com.minutch.pandave.param.Result;
import com.minutch.pandave.param.topic.TopicQueryParam;
import com.minutch.pandave.result.PageResultVO;
import com.minutch.pandave.result.topic.TopicVO;
import com.minutch.pandave.utils.PandaveBeanUtils;
import com.minutch.pandave.view.topic.TopicAnswerView;
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
}

$(function () {

    //左侧导航栏事件
    $('.nav-bar-link').click(function () {
        $('.nav-bar-link').removeClass('active');
        $(this).addClass('active');
    })

    //内容导航栏
    $('.main-nav').click(function () {
        $('.main-nav').removeClass('main-nav-active');
        $(this).addClass('main-nav-active');
    })

    //查询话题数据
    queryTopicAnswer(0);

    //分页功能
    //createPage(2, 4, 100);
})

var contentMap = {};

function readAllContent(answerBreiefId){
    console.log(answerBreiefId)
    var className = '.'+answerBreiefId;
    var valueContent = $(className).text();
    //console.log(valueContent);

    var briefClassName = '.brief'+answerBreiefId;
    $(briefClassName).html('');
    $(briefClassName).append(contentMap[answerBreiefId]);

}

function createPage(pageSize, total) {
    //$(".pagination").jBootstrapPage({
    //    pageSize: pageSize,
    //    total: total,
    //    maxPageButton: buttons,
    //    onPageClicked: function (obj, pageIndex) {
    //        console.log(pageIndex);
    //        queryTopicAnswer(pageIndex);
    //    }
    //});

    $('.pagination').pagination({
        items: total,
        itemsOnPage: pageSize,
        displayedPages: 1,
        edges:1,
        cssStyle: 'light-theme',
        prevText: '上一页',
        nextText: '下一页',
        onPageClick: function(pageNumber,event) {
            console.log(pageNumber)
            queryTopicAnswer(pageNumber);
        }
    });
}

var pageSize = 1;
function queryTopicAnswer(pageIndex) {
    var param = {}
    param.pageSize = pageSize
    param.curPage = pageIndex + 1;
    //param.keyword = $('#keyword').val()
    $.ajax({
        url: "/pandave/topic/queryTopicAnswer",
        datatype: 'json',
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(param),
        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    var resultData = result.data;

                    //清空content数据
                    $('.contentDiv').html('');
                    //加载新内容
                    var topicList = resultData.dataList;
                    if (topicList != null & topicList != undefined & topicList.length > 0) {
                        for (var index = 0; index < topicList.length; index++) {
                            //组装话题数据表格
                            var topic = topicList[index];

                            var topicId = topic.topicId;
                            var topicTitle = topic.topicTitle;
                            var topicContent = topic.topicContent;
                            var answerId = topic.answerId;
                            var answerModifyTime = topic.answerModifyTime;
                            var answerAuthorName = topic.answerAuthorName;
                            var answerAuthorSign = topic.answerAuthorSign;
                            var answerContent = topic.answerContent;
                            var answerContentBrief = topic.answerContentBrief;
                            var answerVote = topic.answerVote;
                            var answerVote = topic.answerVote;

                            var topicDataHtml =
                                '<article class="post col-sm-120">' +
                                '<header class="post-title">' +
                                '<a target="_blank" href="topic/queryAnswerDetail/'+answerId+'">'+
                                 topicTitle +
                                '</a>'+
                                '</header>' +
                                ' <div class="post-author">' +
                                '   <span>' + answerAuthorName + ' ⋅ ' + answerModifyTime + ' ⋅ 点赞数:' + answerVote + '</span>' +
                                '</div>' +
                                '<p class="brief'+answerId+'">' +
                                answerContentBrief +
                                '</p>' +
                                '</article>';
                            contentMap[answerId] = answerContent;
                            $('.contentDiv').append(topicDataHtml);
                        }
                    }

                    if (pageIndex == 0) {
                        createPage(pageSize, resultData.totalSize);
                    }

                }
            }
        }
    });
}




























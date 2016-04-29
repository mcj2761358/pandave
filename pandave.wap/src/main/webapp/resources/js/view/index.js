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
    //createPage(10, 10, 100);
})

function createPage(pageSize, buttons, total) {
    $(".pagination").jBootstrapPage({
        pageSize: pageSize,
        total: total,
        maxPageButton: buttons,
        onPageClicked: function (obj, pageIndex) {
            console.log(obj);
            console.log(pageIndex);
            queryTopicAnswer(pageIndex);
        }
    });
}

var pageSize = 10;
var bottonsSize = 3;
function queryTopicAnswer(pageIndex) {
    var param = {}
    param.pageSize = 10
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
                console.log(result);
                //请求数据成功
                if (result.success) {
                    var resultData = result.data;

                    //清空content数据
                    $('.contentDiv').html('');
                    //加载新内容
                    var topicList = resultData.dataList;
                    console.log(topicList);
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

                            var topicDataHtml =
                                '<article class="post col-sm-120">' +
                                '<header class="post-title">' +
                                '<a target="_blank" href="topic/queryAnswerDetail/'+answerId+'">'+
                                 topicTitle +
                                '</a>'+
                                '</header>' +
                                ' <div class="post-author">' +
                                '   <span>' + answerAuthorName + ' ⋅ ' + answerModifyTime + '</span>' +
                                '</div>' +
                                '<p>' +
                                answerContentBrief +
                                '</p>' +
                                '</article>';

                            $('.contentDiv').append(topicDataHtml);
                        }
                    }

                    if (pageIndex == 0) {
                        createPage(pageSize, bottonsSize, resultData.totalSize);
                    }

                }
            }
        }
    });
}




























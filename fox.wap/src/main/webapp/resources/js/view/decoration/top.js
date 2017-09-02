$(function () {

    //请求公告数据
    //queryNotice();
})


function queryNotice() {

    $.ajax({
        url: "/fox/notice/queryNewNotice",
        type: "GET",
        success: function (result) {
            if (result != null) {

                console.log(result);
                //请求数据成功
                if (result.success) {
                    notice = result.data;
                    if (notice != null) {

                        var noticeMsg =
                            '<div>' +
                            '<div class="alert alert-success alert-dismissable">' +
                            '<button onclick="closeNotice('+notice.id+')" type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>' +
                            '<h4>' +
                            '【'+notice.noticeTypeName+'】：'+notice.noticeMessage +
                            '</h4>' +
                            '</div>' +
                            '</div>';


                        $('#topDiv').append(noticeMsg);
                    }
                } else {

                }
            }
        }
    });
}


function closeNotice(noticeId) {

    $.ajax({
        url: "/fox/notice/closeNewNotice?noticeId="+noticeId,
        type: "GET",
        success: function (result) {
            if (result != null) {
               console.log(result)
            }
        }
    });
}
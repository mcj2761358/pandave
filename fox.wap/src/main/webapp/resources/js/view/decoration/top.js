$(function () {

    //请求公告数据
    queryNotice();
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
                    data = result.data;
                    if (data != null) {

                        var noticeMsg =
                            '<div>' +
                            '<div class="alert alert-success alert-dismissable">' +
                            '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>' +
                            '<h4>' +
                            '【'+data.noticeTypeName+'】：'+data.noticeMessage +
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
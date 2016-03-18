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


    //param = {};
    //param.keyword = "Minutch";
//        $.ajax({
//            url: "/pandave/system/queryAll",
//            datatype: 'json',
//            type: "POST",
//            contentType: 'application/json',
//            data: JSON.stringify(param),
//            success: function (data) {
//                if (data != null) {
//                    console.log(data);
//                }
//            }
//        });


    //分页功能
    createPage(10, 10, 100);

})


function createPage(pageSize, buttons, total) {
    $(".pagination").jBootstrapPage({
        pageSize: pageSize,
        total: total,
        maxPageButton: buttons,
        onPageClicked: function (obj, pageIndex) {
            console.log(obj);
            console.log(pageIndex);
        }
    });
}
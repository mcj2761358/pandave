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

    //绑定搜索按钮
    $('#search').click(function () {
        queryStudent(0);
    })

    //加载学员数据
    queryStudent(0);


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
            queryStudent(pageIndex);
        }
    });
}

var pageSize = 10;
var bottonsSize = 10;
function queryStudent(pageIndex) {
    var param = {}
    param.pageSize = 10
    param.curPage = pageIndex+1;
    param.keyword = $('#keyword').val()
    $.ajax({
        url: "/pandave/student/queryStudent/employee/auth",
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

                    //清空studentBody的数据
                    $('.studentBody').html('');
                    //加载新内容
                    var studentList = resultData.dataList;
                    console.log(studentList);
                    if (studentList!=null & studentList!=undefined & studentList.length>0) {
                        for (var index=0; index<studentList.length;index++) {

                            var num = index+1;

                            //计算总序号
                            var seqNum = pageIndex*pageSize + num;
                            //计算样式该行
                            var trClass = '';
                            if (num == 2) {
                                trClass = 'info';
                            } else if (num == 4) {
                                trClass = 'success';
                            } else if (num == 6) {
                                trClass = 'warning';
                            } else if (num == 8) {
                                trClass = 'self-primary';
                            } else if (num == 10) {
                                trClass = 'danger';
                            }

                            //组装学员数据表格
                            var student = studentList[index];
                            var id = student.id;
                            var name = student.name;
                            var courseTerm = student.courseTerm;
                            var courseName = student.courseName;
                            var totalCourse = student.totalCourse;
                            var usedCourse = student.usedCourse;
                            var lastSignTime = student.lastSignTime;
                            if (lastSignTime == null) {
                                lastSignTime = '从未打卡'
                            }

                            var studentDataHtml =
                                ' <tr class="'+trClass+'">' +
                                '   <th scope="row">'+seqNum+'</th>' +
                                '   <td>'+name+'</td>' +
                                '   <td>'+courseTerm+'</td>' +
                                '   <td>'+courseName+'</td>' +
                                '   <td>'+totalCourse+'</td>' +
                                '   <td>'+usedCourse+'</td>' +
                                '   <td>'+lastSignTime+'</td>' +
                                '   <td>' +
                                '       <a id="'+id+'" class="btn btn-info signBtn">打卡</a>' +
                                '   </td>' +
                                '</tr>';

                            $('.studentBody').append(studentDataHtml);

                        }
                    }

                    if (pageIndex == 0) {
                        createPage(pageSize, bottonsSize, resultData.totalSize);
                    }

                } else {

                    if (result.errorCode = -1000) {
                        window.location.href = '/pandave/dance'
                    } else {
                        alert(result.errorMsg);
                    }
                }
            }
        }
    });
}


























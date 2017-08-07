/**
 * Created by Minutch on 16/12/17.
 */
$(function () {

    //绑定日期控件
    $('.queryTime').datetimepicker({
        language:  'zh-CN',
        minView: "month",
        autoclose: 1,
    });

    queryOrderList(0);
});


function getFormattedDate(date) {
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear().toString().slice(2);
    return day + '-' + month + '-' + year;
}

function createPage(pageSize, total) {

    $('.pagination').pagination({
        items: total,
        itemsOnPage: pageSize,
        displayedPages: 2,
        edges: 1,
        cssStyle: 'light-theme',
        prevText: '上一页',
        nextText: '下一页',
        onPageClick: function (pageNumber, event) {
            queryOrderList(pageNumber - 1)
        }
    });
}


var pageSize = 10;
function queryOrderList(pageIndex) {

    var contextPath = $('#rcContextPath').val();

    var param = {};
    param.pageSize = pageSize;
    param.curPage = pageIndex + 1;
    param.keyword = $('#keyword').val();
    param.queryTime = $('#queryTime').val();
    param.beFinish = $('#beFinish').val();

    $.ajax({
        url: contextPath + "/decoration/order/queryList",
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
                    var orderList = resultData.dataList;
                    if (orderList != null & orderList != undefined & orderList.length > 0) {
                        for (var index = 0; index < orderList.length; index++) {
                            //组装话题数据表格
                            var order = orderList[index];

                            var orderId = order.id;
                            var cusId = order.cusId;
                            var cusName = order.cusName;
                            var gmtCreate = order.gmtCreatePos;
                            var mobilePhone = order.mobilePhone;
                            var houseName = order.houseName;
                            var goodsName = order.goodsName;
                            var goodsModel = order.goodsModel;
                            var goodsNum = order.goodsNum;
                            var orderAmount = order.orderAmount;
                            var returnAmount = order.returnAmount;
                            var beFinish = order.beFinish;

                            if (beFinish == null || beFinish == undefined || beFinish=='N') {
                                beFinish = '<span style="color: red">未结清</span>';
                            }
                            if (beFinish == 'Y') {
                                beFinish = '<span style="color: #32CD32">已结清</span>';
                            }

                            var customerDetailUrl = contextPath + '/decoration/customerDetail?cusId=' + cusId;
                            var orderDataHtml = '<tr>' +
                                '<td><a href="' + customerDetailUrl + '" target="_blank">' + cusName + '</a></td>' +
                                '<td class="center">' + gmtCreate + '</td>' +
                                '<td class="center">' + mobilePhone + '</td>' +
                                '<td class="center">' + houseName + '</td>' +
                                '<td class="center">' + goodsName + '</td>' +
                                '<td class="center">' + goodsModel + '</td>' +
                                '<td class="center">' + goodsNum + '</td>' +
                                '<td class="center">' + orderAmount + '</td>' +
                                '<td class="center">' + beFinish + '</td>' +
                                '<td class="center">' +
                                '<a href="' + customerDetailUrl + '"  target="_blank" class="btn btn-info btn-sm">' +
                                '<i class="glyphicon glyphicon-edit icon-white"></i>编辑' +
                                '</a>' +
                                '</td>' +
                                '</tr>';
                            $('.contentDiv').append(orderDataHtml);
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

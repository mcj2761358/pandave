/**
 * Created by Minutch on 16/12/17.
 */
$(function () {
    queryOrderList(0);
});

function createPage(pageSize, total) {

    $('.pagination').pagination({
        items: total,
        itemsOnPage: pageSize,
        displayedPages: 2,
        edges:1,
        cssStyle: 'light-theme',
        prevText: '上一页',
        nextText: '下一页',
        onPageClick: function(pageNumber,event) {
            queryOrderList(pageNumber-1)
        }
    });
}




var pageSize = 10;
function queryOrderList(pageIndex) {

    var contextPath = $('#rcContextPath').val();

    var param = {};
    param.pageSize = pageSize;
    param.remindStatus = 0;
    param.timeName = $('#timeName').val();
    param.curPage = pageIndex + 1;
    param.keyword = $('#keyword').val();

    $.ajax({
        url: contextPath +"/decoration/order/queryList",
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
                            var gmtCreate = order.gmtCreate;
                            var mobilePhone = order.mobilePhone;
                            var houseName = order.houseName;
                            var goodsName = order.goodsName;
                            var goodsModel = order.goodsModel;
                            var goodsNum = order.goodsNum;
                            var orderAmount = order.orderAmount;
                            var returnAmount = order.returnAmount;
                            var remindTime = order.remindTime;
                            var beFinish = order.beFinish;

                            if (beFinish == null || beFinish== undefined) {
                                beFinish = '<span style="color: red">未结清</span>';
                            }
                            if (beFinish == 'Y') {
                                beFinish = '<span style="color: #32CD32">已结清</span>';
                            }
                            if (remindTime == null || remindTime == undefined) {
                                remindTime = '';
                            }


                            var customerDetailUrl = contextPath + '/decoration/customerDetail?cusId='+cusId;
                            var orderDataHtml = '<tr>'+
                                '<td><a href="'+customerDetailUrl+'" target="_blank">'+cusName+'</a></td>' +
                                '<td class="center">'+remindTime+'</td>'+
                                '<td class="center">'+mobilePhone+'</td>'+
                                '<td class="center">'+houseName+'</td>'+
                                '<td class="center">'+goodsName+'</td>'+
                                '<td class="center">'+goodsModel+'</td>'+
                                '<td class="center">'+goodsNum+'</td>'+
                                '<td class="center">'+orderAmount+'</td>'+
                                '<td class="center">'+
                                '<a class="btn btn-danger btn-sm" onClick="sureHandleRemindOrder('+orderId+')" href="#">' +
                                '<i class="glyphicon glyphicon-edit icon-white"></i>处理'+
                                '</a>'+
                                '</td>'+
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




//删除客户信息按按钮事件
function sureHandleRemindOrder(orderId) {
    showChooseModel("确认处理该订单吗?", handleRemindOrder, orderId);
}
//处理真正删除事件
function handleRemindOrder (orderId) {
    var contextPath = $('#rcContextPath').val();
    $.ajax({
        url: contextPath +"/decoration/order/handleRemindById?orderId=" + orderId,
        type: "GET",

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    var resultData = result.data;
                    console.log(resultData);
                    window.location.href = contextPath+"/decoration/remindOrderList";
                } else{
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });
}

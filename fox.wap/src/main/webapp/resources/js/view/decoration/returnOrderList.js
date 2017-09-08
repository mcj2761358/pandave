/**
 * Created by Minutch on 16/12/17.
 */
$(function () {
    queryReturnOrderList(0);
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
            queryReturnOrderList(pageNumber-1)
        }
    });
}




var pageSize = 10;
function queryReturnOrderList(pageIndex) {

    var contextPath = $('#rcContextPath').val();

    var param = {};
    param.pageSize = pageSize;
    param.curPage = pageIndex + 1;

    $.ajax({
        url: contextPath +"/returnOrder/queryList",
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
                    var returnOrderList = resultData.dataList;
                    if (returnOrderList != null & returnOrderList != undefined & returnOrderList.length > 0) {
                        for (var index = 0; index < returnOrderList.length; index++) {
                            //组装话题数据表格
                            var returnOrder = returnOrderList[index];

                            var cusId = returnOrder.cusId;
                            var mobilePhone = returnOrder.mobilePhone;
                            var orderSn = returnOrder.orderSn;
                            var cusName = returnOrder.cusName;
                            var gmtCreate = returnOrder.gmtCreatePos;

                            var goodsName = returnOrder.goodsName;
                            var goodsModel = returnOrder.goodsModel;
                            var goodsNum = returnOrder.goodsNum;
                            var orderAmount = returnOrder.orderAmount;
                            var beNew = returnOrder.beNew;
                            if (beNew == true) {
                                cusName += '<span style="color: red">(新)</span>';
                            }

                            var customerDetailUrl = contextPath + '/decoration/customerDetail?cusId='+cusId+'&orderSn='+orderSn;
                            var orderDataHtml = '<tr>'+
                                '<td><a href="'+customerDetailUrl+'" target="_blank">'+orderSn+'</a></td>' +
                                '<td class="center">'+cusName+'</td>'+
                                //'<td class="center">'+mobilePhone+'</td>'+
                                '<td class="center">'+goodsName+goodsModel+'</td>'+
                                '<td class="center">'+goodsNum+'</td>'+
                                '<td class="center">'+orderAmount+'</td>'+
                                '<td class="center">'+gmtCreate+'</td>'+
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


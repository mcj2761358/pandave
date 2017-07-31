/**
 * Created by Minutch on 16/12/17.
 */
$(function () {

    //绑定创建订单按钮事件
    $('#saveOrderBtn').click(function () {
        saveOrder();
    });

    //弹出创建订单Modal
    $('#orderModalBtn').click(function () {
        $('#orderModal').modal(true);
    });

    queryOrderList(0);
});


function saveOrder() {

    var cusId = $('#cusId').val();
    var orderId = $('#editOrderId').val();
    var goodsName = $('#goodsName').val();
    var goodsNum = $('#goodsNum').val();
    var goodsPrice = $('#goodsPrice').val();
    var orderAmount = $('#orderAmount').val();
    var remark = $('#remark').val();

    //数据检查
    if (goodsName == null || goodsName == '') {
        showAlertModel('请填写商品名称');
        return false;
    }
    if (isNaN(goodsNum) || goodsNum == '') {
        showAlertModel('商品数量必须为数字.');
        return false;
    }
    if (goodsNum.indexOf(".") > -1) {
        showAlertModel('商品数量必须为整数.');
        return false;
    }


    if (isNaN(goodsPrice) || goodsPrice == '') {
        showAlertModel('商品单价必须为数字.');
        return false;
    }
    if (isNaN(orderAmount) || orderAmount == '') {
        showAlertModel('订单金额必须为数字.');
        return false;
    }

    var param = {};
    param.id = orderId;
    param.cusId = cusId;
    param.goodsName = goodsName;
    param.goodsNum = goodsNum;
    param.goodsPrice = goodsPrice;
    param.orderAmount = orderAmount;
    param.remark = remark;

    var contextPath = $('#rcContextPath').val();

    $.ajax({
        url: contextPath + "/decoration/order/save",
        datatype: 'json',
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(param),

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    clearOrderModal();
                    queryOrderList(0);
                    $('#orderModal').modal('hide');
                } else {
                    alert(result.errorMsg);
                }
            }
        }
    });
}

//删除信息按按钮事件
function deleteOrder(orderId) {
    showChooseModel("确认要删除该订单吗?", handleDeleteOrder, orderId);
}
//处理真正删除事件
function handleDeleteOrder(orderId) {
    var contextPath = $('#rcContextPath').val();
    $.ajax({
        url: contextPath + "/decoration/order/deleteById?orderId=" + orderId,
        type: "GET",

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    $('.D_'+ orderId).remove();
                } else {
                    alert(result.errorMsg);
                }
            }
        }
    });
}

function clearOrderModal() {
    $('#goodsName').val('');
    $('#goodsNum').val('');
    $('#goodsPrice').val('');
    $('#orderAmount').val('');
    $('#remark').val('');
}



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
    param.curPage = pageIndex + 1;
    param.keyword = $('#keyword').val();
    param.cusId = $('#cusId').val();

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
                    $('.orderContentDiv').html('');
                    //加载新内容
                    var orderList = resultData.dataList;
                    if (orderList != null & orderList != undefined & orderList.length > 0) {
                        for (var index = 0; index < orderList.length; index++) {
                            //组装话题数据表格
                            var order = orderList[index];

                            var orderId = order.id;
                            var goodsName = order.goodsName;
                            var goodsNum = order.goodsNum;
                            var goodsPrice = order.goodsPrice;
                            var orderAmount = order.orderAmount;
                            var remark = order.remark;
                            var gmtCreate = order.gmtCreate;
                            var beFinish = order.beFinish;

                            if (remark == null || remark== undefined) {
                                remark = '';
                            }
                            if (beFinish == null || beFinish== undefined) {
                                beFinish = '<span style="color: red">未结清</span>';
                            }
                            if (beFinish == 'Y') {
                                beFinish = '<span style="color: #32CD32">已结清</span>';
                            }

                            var orderDataHtml =
                                ' <tr class="D_'+orderId+'">' +
                                '   <td class="goodsName">'+goodsName+'</td>' +
                                '   <td class="center goodsNum">'+goodsNum+'</td>' +
                                '   <td class="center goodsPrice">'+goodsPrice+'</td>' +
                                '   <td class="center orderAmount">'+orderAmount+'</td>' +
                                '   <td class="center gmtCreate">'+gmtCreate+'</td>' +
                                '   <td class="center remark">'+remark+'</td>' +
                                '   <td class="center beFinish">'+beFinish+'</td>' +
                                '   <td class="center">' +
                                '       <a class="btn btn-info btn-sm" onclick="editOrder('+orderId+')">' +
                                '           <i class="glyphicon glyphicon-edit icon-white"></i>编辑' +
                                '       </a>' +
                                '       <a class="btn btn-primary btn-sm" onclick="finishOrder('+orderId+')">' +
                                '           <i class="glyphicon glyphicon-wrench icon-white"></i>还款' +
                                '       </a>' +
                                '       <a class="btn btn-danger btn-sm" onclick="deleteOrder('+orderId+')">' +
                                '           <i class="glyphicon glyphicon-trash icon-white"></i>删除' +
                                '       </a>' +
                                '   </td>' +
                                '</tr>';

                            $('.orderContentDiv').append(orderDataHtml);
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




//新建订单
function createOrder() {
    clearOrderModal();
    $('#orderModal').modal(true);
}


//结清订单
function finishOrder(orderId) {

    var classOrder = '.D_' + orderId;
    var beFinish =  $(classOrder +' .beFinish').html();
    if (beFinish == '已结清'){
        showAlertModel('该订单已经结清.');
        return;
    }

    showChooseModel("确认该订单客户已经结款了吗?", handleFinishOrder, orderId);
}
//处理真正结算事件
function handleFinishOrder(orderId) {

    var contextPath = $('#rcContextPath').val();
    $.ajax({
        url: contextPath + "/decoration/order/finishById?orderId=" + orderId,
        type: "GET",

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    var classOrder = '.D_' + orderId;
                    $(classOrder +' .beFinish').html('已结清');
                } else {
                    alert(result.errorMsg);
                }
            }
        }
    });
}


//编辑订单
function editOrder(orderId) {
    var classOrder = '.D_' + orderId;
    var goodsName = $(classOrder +' .goodsName').html();
    var goodsNum = $(classOrder +' .goodsNum').html();
    var goodsPrice = $(classOrder +' .goodsPrice').html();
    var orderAmount = $(classOrder +' .orderAmount').html();
    var remark = $(classOrder +' .remark').html();

    $('#editOrderId').val(orderId);
    $('#goodsName').val(goodsName);
    $('#goodsNum').val(goodsNum);
    $('#goodsPrice').val(goodsPrice);
    $('#orderAmount').val(orderAmount);
    $('#remark').val(remark);
    $('#orderModal').modal(true);

}


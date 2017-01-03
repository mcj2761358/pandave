/**
 * Created by Minutch on 16/12/17.
 */
$(function () {

    //绑定创建订单按钮事件
    $('#saveOrderBtn').click(function () {
        createOrder();
    });

    //弹出创建订单Modal
    $('#orderModalBtn').click(function () {
        $('#orderModal').modal(true);
    });
});


function createOrder(orderId) {

    var cusId = $('#cusId').val();
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
                    var resultData = result.data;
                    console.log(resultData);

                    var orderId = resultData.id;
                    var goodsName = resultData.goodsName;
                    var goodsNum = resultData.goodsNum;
                    var goodsPrice = resultData.goodsPrice;
                    var orderAmount = resultData.orderAmount;
                    var remark = resultData.remark;

                    var orderRow = '  <tr class="D_'+orderId+'">' +
                        '<td class="goodsName">'+goodsName+'</td>' +
                        '<td class="center goodsNum">'+goodsNum+'</td>' +
                        '   <td class="center goodsPrice">'+goodsPrice+'</td>' +
                        '   <td class="center orderAmount">'+orderAmount+'</td>' +
                        '   <td class="center">'+remark+'</td>' +
                        '   <td class="center">' +
                        '       <a class="btn btn-danger btn-sm"  onclick="deleteOrder('+orderId+')">' +
                        '            <i class="glyphicon glyphicon-trash icon-white"></i>删除' +
                        '       </a>' +
                        '   </td>' +
                        '</tr>';

                    $('.orderContentDiv').append(orderRow);

                    clearOrderModal();
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
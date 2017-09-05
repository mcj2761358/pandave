/**
 * Created by Minutch on 16/12/17.
 */

var searchMap = {};
var jsonData = [];

$(function () {

    //绑定创建订单按钮事件
    $('#saveOrderBtn').click(function () {
        saveOrder();
    });

    //绑定创建订单按钮事件
    $('#saveReturnOrderBtn').click(function () {
        saveReturnOrder();
    });

    ////弹出创建订单Modal
    //$('#orderModalBtn').click(function () {
    //    $('#orderModal').modal(true);
    //});

    //绑定日期控件
    $('.remindTime').datetimepicker({
        language: 'zh-CN',
        minView: "month",
        autoclose: 1
    });


    //绑定日期控件
    $('.createTimeCon').datetimepicker({
        language: 'zh-CN',
        minView: "month",
        autoclose: 1
    });

});

function changeEmpName(headerId) {

    var empName = $('#orderSaleOp_'+headerId).val();


    var param = {};
    param.headerId = headerId;
    param.empName = empName;
    var contextPath = $('#rcContextPath').val();

    $.ajax({
        url: contextPath + "/decoration/order/updateEmpName",
        datatype: 'json',
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(param),

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                   console.log('保存开单人成功.');
                } else {
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });
}


function initGoodsData(goodsList) {
    if (goodsList==null || goodsList==undefined) {
        return;
    }

   for (var i=0; i<goodsList.length; i++) {
       var goods = goodsList[i];
       jsonData.push(goods.key);
       searchMap[goods.key] = goods.value;
   }
}


function bindSearch(headerId) {

    var mainSearchId = '#goodsSearch'+headerId;

    var goodsModelId = '#fillGoodsModel' + headerId;
    var goodsPriceId = '#fillGoodsPrice' + headerId;
    var goodsId = '#fillGoodsId' + headerId;

    var mainResultId = '#searchResult'+headerId;
    var uniCompleteClass = 'autocomplete'+headerId;
    var autoCompleteClass = 'autocomplete autocomplete'+headerId;
    var autoCompleteDom = $('<ul class="'+autoCompleteClass+'"></ul>')

    //Bug:汉字不能查询的问题和空值的时候要出现值
    //var jsonData = ["张三", "李四", "1科比", "罗晋", "张娜", "张芳", "123", "allen lverson", "bob dylan", "bob1", "bob2", "1nba"];
    var selectedItem = null; //定义全局变量
    var old_searchtext = null;
    var $autoComplete = autoCompleteDom.hide().insertAfter(mainSearchId);

    //定义函数获取鼠标显示颜色
    var setSelectedItem = function (item, eventKeyCode) {
        selectedItem = item;
        if (selectedItem == null || $('ul[class="'+autoCompleteClass+'"]').find("li").length <= 0) {
            //没有选择值则移除ul
            $('ul[class="'+autoCompleteClass+'"]').empty().hide();
            return false;
        }

        if (selectedItem < 0) { //向上移出界
            $autoComplete.find("li").removeClass("selected");

            var searchKey = $(mainResultId).val();
            var searchObject = JSON.parse(searchMap[searchKey]);
            $(goodsModelId).val(searchObject.goodsModel);
            $(goodsPriceId).val(searchObject.goodsPrice);
            $(goodsId).val(searchObject.id);

            $(mainSearchId).val(searchObject.goodsName);
            console.log('get1:' + searchKey)
            selectedItem = null;
            return false;
        }

        if (selectedItem >= $autoComplete.find("li").length) {  //向下移出界
            $autoComplete.find("li").removeClass("selected");

            var searchKey = $(mainResultId).val();
            var searchObject = JSON.parse(searchMap[searchKey]);
            $(goodsModelId).val(searchObject.goodsModel);
            $(goodsPriceId).val(searchObject.goodsPrice);
            $(goodsId).val(searchObject.id);
            $(mainSearchId).val(searchObject.goodsName);

            console.log('get2:' + searchKey)
            selectedItem = null;
            return false;
        }

        //移动上下键获取文本值
        $(mainSearchId).val();

        var searchKey = $autoComplete.find("li").removeClass("selected").eq(selectedItem).addClass("selected").text();
        var searchObject = JSON.parse(searchMap[searchKey]);
        $(goodsModelId).val(searchObject.goodsModel);
        $(goodsPriceId).val(searchObject.goodsPrice);
        $(goodsId).val(searchObject.id);
        $(mainSearchId).val(searchObject.goodsName);
        console.log('get3:' + searchKey)
        $autoComplete.show();
    };

    //按回车键获取值，但不能获取==>这里改为直接提交
    var populateSearchField = function () {

        var searchKey = $autoComplete.find("li").eq(selectedItem).text();
        var searchObject = JSON.parse(searchMap[searchKey]);
        $(goodsModelId).val(searchObject.goodsModel);
        $(goodsPriceId).val(searchObject.goodsPrice);
        $(goodsId).val(searchObject.id);
        $(mainSearchId).val(searchObject.goodsName);

        console.log('get4:' + $autoComplete.find("li").eq(selectedItem).text())
        //移除ul
        $('ul[class="'+autoCompleteClass+'"]').empty().hide();
    };

    var searchLabel = $("#search_title").remove().text();
    $("#form1").submit(function () {
        if ($(mainSearchId).val() == searchLabel) {
            $(mainSearchId).val('');
        }
    });

    $(mainSearchId).addClass("placeholder")
        .val(searchLabel)
        .focus(function () {
            if (this.value == searchLabel) {
                $(this).removeClass("placeholder").val('');
            }
        })
        .blur(function () {
            if (this.value == "") {
                $(this).addClass("placeholder").val(searchLabel);
            }
        })
        .attr(uniCompleteClass, "off")                  //关闭search_text文本框（即浏览器）自带的自动完成功能
        .keydown(function () {
            old_searchtext = $(this).val();           //按下时获取文本值
        })
        .keyup(function (event) {
            var InComeDescription = $(this).val();    //按键释放时触发
            //没有值直接返回,提高性能
            if (InComeDescription == null || InComeDescription.length <= 0) {
                return false;
            }

            //代码为40及以下的为特殊键（回车、方向、退出键等），为8backspace键,32空格键,13 回车
            if (event.keyCode > 40 || event.keyCode == 8 || event.keyCode == 32) {
                selectedItem = null; //每次输入新的数据时清空，以使下拉列表显示正常
                //有数据(可加个判断InComeDescritption不为空才取值)
                $searchtextvalue = $(mainResultId).val(InComeDescription);

                if (jsonData.length) {
                    //var objData = jQuery.parseJSON(jsonData);
                    var objData = jsonData;
                    //先把以前的记录清空，不然下拉列表会重复
                    $('ul[class="'+autoCompleteClass+'"]').empty();
                    $.each(objData, function (index, term) {
                        if (term.indexOf(InComeDescription) > -1) {
                            $('<li></li>').text(term)
                                .appendTo($autoComplete)
                                .mouseover(function () {
                                    setSelectedItem(index, null);
                                })
                                .click(function () {//实现选择值到文本框
                                    console.log('get5:' + $autoComplete.find("li").eq(selectedItem).text())

                                    var searchKey = term;
                                    var searchObject = JSON.parse(searchMap[searchKey]);
                                    $(goodsModelId).val(searchObject.goodsModel);
                                    $(goodsPriceId).val(searchObject.goodsPrice);
                                    $(goodsId).val(searchObject.id);
                                    $(mainSearchId).val(searchObject.goodsName);
                                    $('ul[class="'+autoCompleteClass+'"]').hide();
                                });
                        }
                    });

                    $autoComplete.show();//按百度默认不选择第一行
                    // setSelectedItem(0);//默认选中第一行,不然这时不显示
                } else {
                    //没有数据就不显示
                    setSelectedItem(null, null);
                }
            } else if (event.keyCode == 38) {
                //用户按了上方向键
                if (selectedItem == null) {
                    return false;
                } //不选
                setSelectedItem(selectedItem - 1, 38);

            } else if (event.keyCode == 40) {
                //用户按了向下方向键
                if (selectedItem == null) {
                    selectedItem = -1;
                } //选择第一行
                setSelectedItem(selectedItem + 1, 40);
            }
        })
        .keypress(function (event) {
            if (event.keyCode == 13 && selectedItem != null) {
                populateSearchField();
                event.preventDefault();
            }
        })
        //失去焦点关闭下拉列表,因为blur先于click事件调用，所以这时在延迟以使当click事件执行时能执行
        .blur(function (event) {
            setTimeout(function () {
                setSelectedItem(null)
            }, 250);
        });
}


function saveOrder() {

    var cusId = $('#cusId').val();
    var orderId = $('#editOrderId').val();
    var headerId = $('#editHeaderId').val();
    var goodsName = $('#goodsName').val();
    var goodsModel = $('#goodsModel').val();
    var goodsNum = $('#goodsNum').val();
    var goodsPrice = $('#goodsPrice').val();
    //var inGoodsPrice = $('#inGoodsPrice').val();
    var orderAmount = $('#orderAmount').val();
    var remindTime = $('#remindTime').val();
    var createTime = $('#createTime').val();
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
    param.headerId = headerId;
    param.goodsName = goodsName;
    param.goodsModel = goodsModel;
    param.goodsNum = goodsNum;
    param.goodsPrice = goodsPrice;
    //param.inGoodsPrice = inGoodsPrice;
    param.orderAmount = orderAmount;
    param.remindTime = remindTime;
    param.createTime = createTime;
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


                    var orderSn = $('#fromOrderSn').val();
                    var param = "?cusId=" + cusId;
                    if (orderSn != null && orderSn != '') {
                        param += "&orderSn="+orderSn;
                    }
                    window.location.href = contextPath + "/decoration/customerDetail" + param;

                } else {
                    alert(result.errorMsg);
                }
            }
        }
    });
}


function addOrder(orderId) {
    $('.quick_order_' + orderId).css('display', 'block');
}

function simpleCreateOrder(headerId) {

    var cusId = $('#cusId').val();

    var classHeader = '.quick_order_' + headerId;

    var goodsName = $(classHeader + ' .goodsName').val();
    var goodsModel = $(classHeader + ' .goodsModel').val();
    var goodsNum = $(classHeader + ' .goodsNum').val();
    var goodsPrice = $(classHeader + ' .goodsPrice').val();
    var orderAmount = $(classHeader + ' .orderAmount').val();
    var goodsId = $(classHeader + ' .goodsId').val();

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
    param.headerId = headerId;
    param.cusId = cusId;
    param.goodsName = goodsName;
    param.goodsModel = goodsModel;
    param.goodsNum = goodsNum;
    param.goodsPrice = goodsPrice;
    param.orderAmount = orderAmount;
    param.goodsId = goodsId;

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
                    //clearSimpleOrderModal();
                    //queryOrderList(0);

                    var orderSn = $('#fromOrderSn').val();
                    var param = "?cusId=" + cusId;
                    if (orderSn != null && orderSn != '') {
                        param += "&orderSn="+orderSn;
                    }
                    window.location.href = contextPath + "/decoration/customerDetail" + param;

                    //window.location.href = contextPath + "/decoration/customerDetail?cusId=" + cusId;
                } else {
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });
}


/**
 * 自动计算订单金额
 * @returns {boolean}
 */
function exeOrderAmount(headerId) {

    var classHeader = '.quick_order_' + headerId;

    var goodsNum = $(classHeader + ' .goodsNum').val();
    var goodsPrice = $(classHeader + ' .goodsPrice').val();
    var orderAmount = $(classHeader + ' .orderAmount').val();


    if (isNaN(goodsNum) || goodsNum == '') {
        goodsNum = 0;
    }
    else if (goodsNum.indexOf(".") > -1) {
        goodsNum = 0;
    }

    if (isNaN(goodsPrice) || goodsPrice == '') {
        goodsPrice = 0;
    }

    $(classHeader + ' .orderAmount').val(goodsNum * goodsPrice)

}


function exeFoxOrderAmount() {
    var goodsNum = $('#goodsNum').val();
    var goodsPrice = $('#goodsPrice').val();
    var orderAmount = $('#orderAmount').val();

    if (isNaN(goodsNum) || goodsNum == '') {
        //showAlertModel('商品数量必须为数字.');
        return false;
    }
    if (goodsNum.indexOf(".") > -1) {
        //showAlertModel('商品数量必须为整数.');
        return false;
    }

    if (isNaN(goodsPrice) || goodsPrice == '') {
        //showAlertModel('商品单价必须为数字.');
        return false;
    }

    //if (isNaN(orderAmount) || orderAmount == '') {
    $('#orderAmount').val(goodsNum * goodsPrice)
    //}
}


/**
 * 计算总金额
 */
function exeTotalAmount(headerId) {

    var totalAmount = $('.totalAmount_' + headerId).val();
    var preAmount = $('.preAmount_' + headerId).val();

    if (isNaN(totalAmount) || totalAmount == '') {
        preAmount = 0;
    }

    if (isNaN(preAmount) || preAmount == '') {
        preAmount = 0;
    }

    $('.leftAmount_' + headerId).html(totalAmount - preAmount);

    var allTotalAmount = 0;
    $('input.totalAmount').each(function () {
        var thisTotalAmount = parseFloat($(this).val());
        if (isNaN(thisTotalAmount) || thisTotalAmount == '') {
            thisTotalAmount = 0;
        }
        allTotalAmount += thisTotalAmount;
    });

    var allPreAmount = 0;
    $('input.preAmount').each(function () {
        var thisPreAmount = parseFloat($(this).val());
        if (isNaN(thisPreAmount) || thisPreAmount == '') {
            thisPreAmount = 0;
        }
        allPreAmount += thisPreAmount;
    });

    var allLeftAmount = allTotalAmount - allPreAmount;
    $('#allTotalAmount').html(allTotalAmount + ' 元')
    $('#allPreAmount').html(allPreAmount + ' 元')
    $('#allLeftAmount').html(allLeftAmount + ' 元')

    saveTotalAmount(headerId);
}


function saveTotalAmount(headerId) {

    var totalAmount = $('.totalAmount_' + headerId).val();
    var preAmount = $('.preAmount_' + headerId).val();


    if (isNaN(totalAmount) || totalAmount == '') {
        totalAmount = 0;
    }
    if (isNaN(preAmount) || preAmount == '') {
        preAmount = 0;
    }

    var param = {};
    param.headerId = headerId;
    param.totalAmount = totalAmount;
    param.preAmount = preAmount;
    var contextPath = $('#rcContextPath').val();

    $.ajax({
        url: contextPath + "/decoration/order/saveOrderTotalAmount",
        datatype: 'json',
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(param),

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    $('#saveTotalAmountBtn').popover('show');
                } else {
                    showAlertModel(result.errorMsg);
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
                    $('.D_' + orderId).remove();
                } else {
                    alert(result.errorMsg);
                }
            }
        }
    });
}


//退货按钮事件
function returnOrder(orderId) {
    var classOrder = '.D_' + orderId;
    var goodsName = $(classOrder + ' .goodsName').attr('value');
    var goodsModel = $(classOrder + ' .goodsModel').html();
    var goodsNum = $(classOrder + ' .goodsNum').html();
    var goodsPrice = $(classOrder + ' .goodsPrice').html();
    var orderAmount = $(classOrder + ' .orderAmount').html();

    $('#editReturnOrderId').val(orderId);
    $('#returnGoodsName').val(goodsName);
    $('#returnGoodsModel').val(goodsModel);
    $('#returnGoodsPrice').val(goodsPrice);
    $('#showReturnOrderAmount').val(orderAmount);

    $('#returnGoodsNum').val('');
    $('#returnOrderAmount').val('');

    $('#returnOrderModal').modal(true);
}



function saveReturnOrder() {

    var cusId = $('#cusId').val();
    var orderId = $('#editReturnOrderId').val();
    var goodsNum = $('#returnGoodsNum').val();
    var orderAmount = $('#returnOrderAmount').val();

    if (isNaN(goodsNum) || goodsNum == '') {
        showAlertModel('商品数量必须为数字.');
        return false;
    }
    if (goodsNum.indexOf(".") > -1) {
        showAlertModel('商品数量必须为整数.');
        return false;
    }
    if (isNaN(orderAmount) || orderAmount == '') {
        showAlertModel('订单金额必须为数字.');
        return false;
    }

    var param = {};
    param.orderId = orderId;
    param.goodsNum = goodsNum;
    param.orderAmount = orderAmount;

    var contextPath = $('#rcContextPath').val();

    $.ajax({
        url: contextPath + "/decoration/order/handleReturnOrder",
        datatype: 'json',
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(param),

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {

                    var orderSn = $('#fromOrderSn').val();
                    var param = "?cusId=" + cusId;
                    if (orderSn != null && orderSn != '') {
                        param += "&orderSn="+orderSn;
                    }
                    window.location.href = contextPath + "/decoration/customerDetail" + param;

                } else {
                    alert(result.errorMsg);
                }
            }
        }
    });
}



function clearSimpleOrderModal() {
    $('#cGoodsName').val('');
    $('#cGoodsModel').val('');
    $('#cGoodsNum').val('');
    //$('#inGoodsPrice').val('');
    $('#cGoodsPrice').val('');
    $('#cOrderAmount').val('');
}

function clearOrderModal() {
    $('#goodsName').val('');
    $('#goodsNum').val('');
    $('#goodsPrice').val('');
    //$('#inGoodsPrice').val('');
    $('#goodsModel').val('');
    $('#orderAmount').val('');
    $('#remark').val('');
}


//结清订单
function finishOrder(orderId) {

    var classOrder = '.D_' + orderId;
    var beFinish = $(classOrder + ' .beFinish').html();
    if (beFinish == '已结清') {
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
                    $(classOrder + ' .beFinish').html('已结清');
                } else {
                    alert(result.errorMsg);
                }
            }
        }
    });
}


//编辑订单
function editOrder(orderId, headerId) {
    var classOrder = '.D_' + orderId;
    var goodsName = $(classOrder + ' .goodsName').attr('value');
    var goodsModel = $(classOrder + ' .goodsModel').html();
    var goodsNum = $(classOrder + ' .goodsNum').html();
    var goodsPrice = $(classOrder + ' .goodsPrice').html();
    //var inGoodsPrice = $(classOrder + ' .inGoodsPrice').val();
    var remindTime = $(classOrder + ' .remindTime').val();
    var createTime = $(classOrder + ' .createTime').val();
    var orderAmount = $(classOrder + ' .orderAmount').html();
    var remark = $(classOrder + ' .remark').html();


    $('#editOrderId').val(orderId);
    $('#editHeaderId').val(headerId);
    $('#goodsName').val(goodsName);
    $('#goodsModel').val(goodsModel);
    $('#goodsNum').val(goodsNum);
    $('#goodsPrice').val(goodsPrice);
    //$('#inGoodsPrice').val(inGoodsPrice);
    $('#remindTime').val(remindTime);
    $('#createTime').val(createTime);
    $('#orderAmount').val(orderAmount);
    $('#remark').val(remark);
    $('#orderModal').modal(true);

}


//导出订单
function exportOrder() {

    //获取所有选中的checkbox
    var chkValue = [];
    $('input[name="order"]:checked').each(function () {

        var val = $(this).val();
        chkValue.push(val);
    });

    if (chkValue.length == 0) {
        showAlertModel('请勾选要打印的订单.');
        return;
    }

    var cusId = $('#cusId').val();
    var contextPath = $('#rcContextPath').val();
    window.location.href = contextPath + '/export/orderList?cusId=' + cusId + '&orderIds=' + chkValue;
}










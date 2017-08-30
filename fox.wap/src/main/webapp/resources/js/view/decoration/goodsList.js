/**
 * Created by Minutch on 16/12/17.
 */
$(function () {

    //绑定创建订单按钮事件
    $('#saveGoodsBtn').click(function () {
        saveGoods();
    });

    //弹出创建订单Modal
    $('#goodsModalBtn').click(function () {
        clearGoodsInfo();
        $('#goodsModal').modal(true);
    });

    queryGoodsList(0);

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
            queryGoodsList(pageNumber-1)
        }
    });
}

/**
 * 保存商品信息
 */
function simpleSaveGoods() {

    var goodsId = $('#goodsId').val();
    var goodsName = $('#goodsName').val();
    var goodsModel = $('#goodsModel').val();
    var goodsPrice = $('#goodsPrice').val();
    var inGoodsPrice = $('#inGoodsPrice').val();
    var whId =$('#whId option:selected').val();
    var whName =$('#whId option:selected').text();

    if (goodsName == '') {
        showAlertModel("请填写商品名称.");
        $('#goodsName').focus();
        return;
    }

    if (goodsModel == '') {
        showAlertModel("请填写商品型号.");
        $('#goodsModel').focus();
        return;
    }

    if (isNaN(goodsPrice) || goodsPrice == '') {
        goodsPrice = 0;
    }
    if (isNaN(inGoodsPrice) || inGoodsPrice == '') {
        inGoodsPrice = 0;
    }
    if (isNaN(stockNum) || stockNum == '') {
        stockNum = 0;
    }

    var param = {};
    param.id = goodsId;
    param.goodsName = goodsName;
    param.goodsModel = goodsModel;
    param.goodsPrice = goodsPrice;
    param.inGoodsPrice = inGoodsPrice;
    param.stockNum = stockNum;
    param.whId = whId;
    param.whName = whName;


    var contextPath = $('#rcContextPath').val();

    $.ajax({
        url: contextPath +"/goods/save",
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
                    clearSimpleGoodsInfo();
                    queryGoodsList(0);
                } else{
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });
}



function saveGoods() {
    var goodsId = $('#editGoodsId').val();
    var goodsName = $('#editGoodsName').val();
    var goodsModel = $('#editGoodsModel').val();
    var goodsPrice = $('#editGoodsPrice').val();
    var inGoodsPrice = $('#editInGoodsPrice').val();
    var stockNum = $('#editStockNum').val();

    var whId =$('#editWhId option:selected').val();
    var whName =$('#editWhId option:selected').text();
    var remark = $('#editRemark').val();

    if (goodsName == '') {
        showAlertModel("请填写商品名称.");
        $('#editGoodsName').focus();
        return;
    }

    if (goodsModel == '') {
        showAlertModel("请填写商品型号.");
        $('#edifGoodsModel').focus();
        return;
    }

    if (isNaN(goodsPrice) || goodsPrice == '') {
        goodsPrice = 0;
    }
    if (isNaN(inGoodsPrice) || inGoodsPrice == '') {
        inGoodsPrice = 0;
    }
    if (isNaN(stockNum) || stockNum == '') {
        stockNum = 0;
    }

    var param = {};
    param.id = goodsId;
    param.goodsName = goodsName;
    param.goodsModel = goodsModel;
    param.goodsPrice = goodsPrice;
    param.inGoodsPrice = inGoodsPrice;
    param.stockNum = stockNum;
    param.whId = whId;
    param.whName = whName;
    param.remark = remark;

    var contextPath = $('#rcContextPath').val();

    $.ajax({
        url: contextPath +"/goods/save",
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
                    clearGoodsInfo();
                    queryGoodsList(0);
                    $('#goodsModal').modal('hide');
                } else{
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });
}


function editGoodsList() {
    var currentName = $('.editGoodsOption').text();
    if (currentName == '编辑商品') {
        $('.optionBtn').show();
        $('.editGoodsOption').text('编辑完成');
    } else if (currentName == '编辑完成') {
        $('.optionBtn').hide();
        $('.editGoodsOption').text('编辑商品');
    }
}


function showOptionBtn() {
    var currentName = $('.editGoodsOption').text();
    if (currentName == '编辑商品') {
        $('.optionBtn').hide();
    } else if (currentName == '编辑完成') {
        $('.optionBtn').show();
    }
}



var pageSize = 10;
function queryGoodsList(pageIndex) {

    var contextPath = $('#rcContextPath').val();

    var param = {};
    param.pageSize = pageSize;
    param.curPage = pageIndex + 1;
    param.keyword = $('#keyword').val();

    $.ajax({
        url: contextPath +"/goods/queryList",
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
                    var goodsList = resultData.dataList;
                    if (goodsList != null & goodsList != undefined & goodsList.length > 0) {
                        for (var index = 0; index < goodsList.length; index++) {
                            //组装话题数据表格
                            var goods = goodsList[index];

                            var goodsId = goods.id;
                            var goodsName = goods.goodsName;
                            var goodsModel = goods.goodsModel;
                            var goodsPrice = goods.goodsPrice;
                            var inGoodsPrice = goods.inGoodsPrice;
                            var stockNum = goods.stockNum;
                            var whName = goods.whName;
                            var gmtCreate = goods.gmtCreatePos;

                            if (whName == null) {
                                whName = '';
                            }

                            var goodsDataHtml =
                                '<tr class="goodsInfo_'+goodsId+'">' +
                                '<td class="goodsName">'+goodsName+'</td>' +
                                '<td class="goodsModel">'+goodsModel+'</td>' +
                                '<td class="inGoodsPrice">'+inGoodsPrice+'</td>' +
                                '<td class="goodsPrice">'+goodsPrice+'</td>' +
                                '<td class="stockNum">'+stockNum+'</td>' +
                                '<td class="whName">'+whName+'</td>' +
                                '<td class="gmtCreate">'+gmtCreate+'</td>' +
                                '<td class="center">' +
                                '<input class="goodsId" value="'+goodsId+'" hidden/>'+
                                '<input class="inGoodsPriceHidden" value="'+inGoodsPrice+'" hidden/>'+
                                '<a class="optionBtn btn btn-info btn-sm" onclick="editGoods('+goodsId+')">' +
                                '<i class="glyphicon glyphicon-edit icon-white"></i>编辑' +
                                '</a> ' +
                                '<a class="optionBtn btn btn-danger btn-sm" onClick="deleteGoods('+goodsId+',\''+goodsName+'\',\''+goodsModel+'\')" href="#">' +
                                '<i class="glyphicon glyphicon-trash icon-white"></i>删除' +
                                '</a>' +
                                '</td>' +
                                '</tr>';
                            $('.contentDiv').append(goodsDataHtml);
                        }
                    }

                    if (pageIndex == 0) {
                        createPage(pageSize, resultData.totalSize);
                    }
                    showOptionBtn();
                }
            }
        }
    });
}

function editGoods(goodsId) {

    var classGoods =  '.goodsInfo_'+goodsId;
    var goodsId = $(classGoods + ' .goodsId').val();
    var goodsName = $(classGoods + ' .goodsName').html();
    var goodsModel = $(classGoods + ' .goodsModel').html();
    var goodsPrice = $(classGoods + ' .goodsPrice').html();
    var inGoodsPrice = $(classGoods + ' .inGoodsPriceHidden').val();
    var stockNum = $(classGoods + ' .stockNum').html();

    $('#editGoodsId').val(goodsId);
    $('#editGoodsName').val(goodsName);
    $('#editGoodsModel').val(goodsModel);
    $('#editGoodsPrice').val(goodsPrice);
    $('#editInGoodsPrice').val(inGoodsPrice);
    $('#editStockNum').val(stockNum);


    $('#goodsModal').modal(true);
}


function clearSimpleGoodsInfo() {
    $('#goodsId').val('');
    $('#goodsName').val('');
    $('#goodsModel').val('');
    $('#goodsPrice').val('');
    $('#inGoodsPrice').val('');
    $('#stockNum').val('');

    $('.beNew').html('(新)');
}

function clearGoodsInfo() {
    $('#editGoodsId').val('');
    $('#editGoodsName').val('');
    $('#editGoodsModel').val('');
    $('#editGoodsPrice').val('');
    $('#editInGoodsPrice').val('');
    $('#editStockNum').val('');
    $('#editRemark').val('');
}


//删除客户信息按按钮事件
function deleteGoods(goodsId,goodsName,goodsModel) {
    showChooseModel("确认要删除商品【"+goodsName+" "+goodsModel+"】吗?", handleDeleteGoods, goodsId);
}
//处理真正删除事件
function handleDeleteGoods (goodsId) {
    var contextPath = $('#rcContextPath').val();
    $.ajax({
        url: contextPath +"/goods/deleteById?goodsId=" + goodsId,
        type: "GET",

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    var resultData = result.data;
                    console.log(resultData);
                    queryGoodsList(0);
                } else{
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });
}

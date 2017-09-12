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
        edges: 1,
        cssStyle: 'light-theme',
        prevText: '上一页',
        nextText: '下一页',
        onPageClick: function (pageNumber, event) {
            queryGoodsList(pageNumber - 1)
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
    var stockNum = $('#stockNum').val();
    var stockWarn = $('#stockWarn').val();
    var whId = $('#whId option:selected').val();
    var whName = $('#whId option:selected').text();

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
    if (isNaN(stockWarn) || stockWarn == '') {
        stockWarn = 0;
    }

    var param = {};
    param.id = goodsId;
    param.goodsName = goodsName;
    param.goodsModel = goodsModel;
    param.goodsPrice = goodsPrice;
    param.inGoodsPrice = inGoodsPrice;
    param.stockNum = stockNum;
    param.stockWarn = stockWarn;
    param.whId = whId;
    param.whName = whName;


    var contextPath = $('#rcContextPath').val();

    $.ajax({
        url: contextPath + "/goods/save",
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
                } else {
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
    var stockWarn = $('#editStockWarn').val();

    var whId = $('#editWhId option:selected').val();
    var whName = $('#editWhId option:selected').text();
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
        inGoodsPrice = '';
    }
    if (isNaN(stockNum) || stockNum == '') {
        stockNum = 0;
    }
    if (isNaN(stockWarn) || stockWarn == '') {
        stockWarn = 0;
    }

    var currentPage = $('.current').text();
    var pageIndex = 0;
    try {
        pageIndex = parseInt(currentPage)-1 ;
    } catch (e) {
        console.log('error current page['+currentPage+']')
    }

    var param = {};
    param.id = goodsId;
    param.goodsName = goodsName;
    param.goodsModel = goodsModel;
    param.goodsPrice = goodsPrice;
    param.inGoodsPrice = inGoodsPrice;
    param.stockWarn = stockWarn;
    param.stockNum = stockNum;
    param.whId = whId;
    param.whName = whName;
    param.remark = remark;

    var contextPath = $('#rcContextPath').val();

    $.ajax({
        url: contextPath + "/goods/save",
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
                    queryGoodsList(pageIndex);
                    $('#goodsModal').modal('hide');
                } else {
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
        url: contextPath + "/goods/queryList",
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
                            var stockWarn = goods.stockWarn;
                            var whName = goods.whName;
                            var gmtCreate = goods.gmtCreatePos;

                            if (whName == null) {
                                whName = '';
                            }

                            var beNew = goods.beNew;

                            var showGoodsName = goodsName;
                            if (beNew == true) {
                                showGoodsName += '<span style="color: red">(新)</span>';
                            }
                            if (inGoodsPrice == null) {
                                inGoodsPrice = '';
                            }

                            var goodsDataHtml =
                                '<tr class="goodsInfo_' + goodsId + '">' +
                                '<td class="goodsName" id="stockShow_' + goodsId + '" value="' + goodsName + '"><span class="showStockList" onclick="showStockList(' + goodsId + ',\'' + goodsName + '\',\'' + goodsModel + '\')">' + showGoodsName + '</span></td>' +
                                '<td class="goodsModel">' + goodsModel + '</td>' +
                                '<td class="inGoodsPrice">' + inGoodsPrice + '</td>' +
                                '<td class="goodsPrice">' + goodsPrice + '</td>' +
                                '<td class="stockNum">' + stockNum + '</td>' +
                                '<td class="stockWarn">' + stockWarn + '</td>' +
                                '<td class="whName">' + whName + '</td>' +
                                '<td class="gmtCreate">' + gmtCreate + '</td>' +
                                '<td class="center">' +
                                '<input class="goodsId" value="' + goodsId + '" hidden/>' +
                                '<input class="inGoodsPriceHidden" value="' + inGoodsPrice + '" hidden/>' +
                                '<a class="optionBtn btn btn-info btn-sm" onclick="editGoods(' + goodsId + ')">' +
                                '<i class="glyphicon glyphicon-edit icon-white"></i>编辑' +
                                '</a> ' +
                                '<a class="optionBtn btn btn-danger btn-sm" onClick="deleteGoods(' + goodsId + ',\'' + goodsName + '\',\'' + goodsModel + '\')" href="#">' +
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

function showStockList(goodsId, goodsName, goodsModel) {

    $('#listContent_'+goodsId).remove();

    $('<div id="listContent_'+goodsId+'" style="display:none;"></div>').appendTo("body");


    //查询库存列表
    var contextPath = $('#rcContextPath').val();
    $.ajax({
        url: contextPath + "/stockDetail/stockList?goodsId=" + goodsId,
        type: "GET",
        async : false,
        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    var resultData = result.data;
                    console.log(resultData);
                    //清空content数据
                    $('#listContent_'+goodsId).html('');


                    var contentData = '<ul class="list-group">';

                    //加载新内容
                    var detailList = resultData;
                    if (detailList != null & detailList != undefined & detailList.length > 0) {
                        for (var index = 0; index < detailList.length; index++) {
                            //组装话题数据表格
                            var detail = detailList[index];

                            var gmtCreate = detail.gmtCreatePos;
                            var objType = detail.objType;
                            var objName = detail.objName;
                            var objId = detail.objId;
                            var stockNum = detail.stockNum;
                            var showNum = '' + stockNum;
                            if (stockNum > 0) {
                                showNum = '<span style="color:red;">+' + showNum + '</span>';
                            } else{
                                showNum = '<span style="color:green;">' + showNum + '</span>';
                            }

                            //制作超链接
                            if ("order" == objType || "orderReturn" == objType) {

                                var hrefVal = contextPath + '/decoration/customerDetail?orderId='+objId;
                                objName = '<a href="'+hrefVal+'" target="_blank">' + objName +'</a>';
                            }

                            var showIndex = (index+1);
                            if (showIndex <= 9) {
                                showIndex = ' ' + showIndex;
                            }

                            var itemData = showIndex + '. ' + gmtCreate + ' ' + objName + ' 库存 ' + showNum;

                            contentData += '<li class="list-group-item">'+itemData+'</li>';
                        }

                        if (detailList.length > 20) {
                            contentData += '<li class="list-group-item">只显示最近20条记录</li>';
                        }

                    } else {
                        contentData += '<li class="list-group-item">暂无数据.</li>';
                    }

                    contentData += '</ul>';
                    $('#listContent_'+goodsId).html(contentData);



                    var listContent =  $('#listContent_'+goodsId).html();;
                    //弹出消息框
                    var listSettings = {
                        content: listContent,
                        title: '商品【' + goodsName + '-' + goodsModel + '】 库存明细',
                        padding: false
                    };

                    var settings = {
                        trigger: 'click',
                        title: '',
                        content: '',
                        width: 320,
                        multi: false,
                        closeable: false,
                        style: '',
                        delay: 300,
                        padding: true
                    };
                    $('#stockShow_' + goodsId).webuiPopover('destroy').webuiPopover($.extend({}, settings, listSettings));


                } else {
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });

}

function editGoods(goodsId) {

    var classGoods = '.goodsInfo_' + goodsId;
    var goodsId = $(classGoods + ' .goodsId').val();
    var goodsName = $(classGoods + ' .goodsName').attr('value');
    var goodsModel = $(classGoods + ' .goodsModel').html();
    var goodsPrice = $(classGoods + ' .goodsPrice').html();
    var inGoodsPrice = $(classGoods + ' .inGoodsPriceHidden').val();
    var stockNum = $(classGoods + ' .stockNum').html();
    var stockWarn = $(classGoods + ' .stockWarn').html();

    $('#editGoodsId').val(goodsId);
    $('#editGoodsName').val(goodsName);
    $('#editGoodsModel').val(goodsModel);
    $('#editGoodsPrice').val(goodsPrice);
    $('#editInGoodsPrice').val(inGoodsPrice);
    $('#editStockNum').val(stockNum);
    $('#editStockWarn').val(stockWarn);


    $('#goodsModal').modal(true);
}


function clearSimpleGoodsInfo() {
    $('#goodsId').val('');
    $('#goodsName').val('');
    $('#goodsModel').val('');
    $('#goodsPrice').val('');
    $('#inGoodsPrice').val('');
    $('#stockNum').val('');
    $('#stockWarn').val('');

    $('.beNew').html('(新)');
}

function clearGoodsInfo() {
    $('#editGoodsId').val('');
    $('#editGoodsName').val('');
    $('#editGoodsModel').val('');
    $('#editGoodsPrice').val('');
    $('#editInGoodsPrice').val('');
    $('#editStockNum').val('');
    $('#editStockWarn').val('');
    $('#editRemark').val('');
}


//删除客户信息按按钮事件
function deleteGoods(goodsId, goodsName, goodsModel) {
    showChooseModel("确认要删除商品【" + goodsName + " " + goodsModel + "】吗?", handleDeleteGoods, goodsId);
}
//处理真正删除事件
function handleDeleteGoods(goodsId) {
    var contextPath = $('#rcContextPath').val();
    $.ajax({
        url: contextPath + "/goods/deleteById?goodsId=" + goodsId,
        type: "GET",

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    var resultData = result.data;
                    console.log(resultData);
                    queryGoodsList(0);
                } else {
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });
}


//{
//    placement:'auto',//值: auto,top,right,bottom,left,top-right,top-left,bottom-right,bottom-left
//    width:'auto',//可以设置数字
//    height:'auto',//可以设置数字
//    trigger:'click',//值:click,hover
//    style:'',//值:'',inverse
//    delay:300,//延迟时间, 悬浮属性才执行
//    cache:true,//如果缓存设置为false,将重建
//    multi:false,//在页面允许其他弹出层
//    arrow:true,//是否显示箭头
//    title:'',//标题,如果标题设置为空字符串时,标题栏会自动隐藏
//    content:'',//内容,内容可以是函数
//    closeable:false,//显示关闭按钮
//    padding:true,//内容填充
//    type:'html',//内容类型, 值:'html','iframe','async'
//    url:''//如果不是空的,插件将通过url加载内容
//}
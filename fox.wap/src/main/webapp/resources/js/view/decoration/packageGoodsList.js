/**
 * Created by Minutch on 16/12/17.
 */

var searchMap = {};
var jsonData = [];

$(function () {

    //绑定创建订单按钮事件
    $('#saveGoodsBtn').click(function () {
        saveGoods();
    });

    //绑定创建订单按钮事件
    $('#saveSubGoodsBtn').click(function () {
        saveSubGoods();
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
        showAlertModel("请填写套餐名称.");
        $('#goodsName').focus();
        return;
    }

    if (goodsModel == '') {
        showAlertModel("请填写套餐型号.");
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

    param.stockWarn = 0;
    param.stockNum = 0;
    //param.stockNum = stockNum;
    //param.stockWarn = stockWarn;
    //param.whId = whId;
    //param.whName = whName;
    param.goodsType = 'package'

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



var pageSize = 10;
function queryGoodsList(pageIndex) {

    var contextPath = $('#rcContextPath').val();

    var param = {};
    param.pageSize = pageSize;
    param.curPage = pageIndex + 1;
    param.goodsType = 'package';
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
                                //'<td class="goodsName" id="stockShow_' + goodsId + '" value="' + goodsName + '"><span class="showStockList" onclick="showStockList(' + goodsId + ',\'' + goodsName + '\',\'' + goodsModel + '\')">' + showGoodsName + '</span></td>' +
                                '<td class="goodsName" id="stockShow_' + goodsId + '" value="' + goodsName + '">' + showGoodsName + '</td>' +
                                '<td class="goodsModel">' + goodsModel + '</td>' +
                                '<td class="inGoodsPrice">' + inGoodsPrice + '</td>' +
                                '<td class="goodsPrice">' + goodsPrice + '</td>' +
                                //'<td class="stockNum">' + stockNum + '</td>' +
                                //'<td class="stockWarn">' + stockWarn + '</td>' +
                                //'<td class="whName">' + whName + '</td>' +
                                '<td class="gmtCreate">' + gmtCreate + '</td>' +
                                '<td class="center">' +
                                '<input class="goodsId" value="' + goodsId + '" hidden/>' +
                                '<input class="inGoodsPriceHidden" value="' + inGoodsPrice + '" hidden/>' +
                                '<a class="optionBtn btn btn-info btn-sm" onclick="editGoods(' + goodsId + ')">' +
                                '<i class="glyphicon glyphicon-edit icon-white"></i>编辑子商品' +
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
                    //showOptionBtn();
                }
            }
        }
    });
}

function editGoods(goodsId) {

    var contextPath = $('#rcContextPath').val();

    var classGoods = '.goodsInfo_' + goodsId;
    var goodsId = $(classGoods + ' .goodsId').val();
    var goodsName = $(classGoods + ' .goodsName').attr('value');
    var goodsModel = $(classGoods + ' .goodsModel').html();
    var goodsPrice = $(classGoods + ' .goodsPrice').html();
    var inGoodsPrice = $(classGoods + ' .inGoodsPriceHidden').val();

    $('#editGoodsId').val(goodsId);
    $('#editGoodsName').val(goodsName);
    $('#editGoodsModel').val(goodsModel);
    $('#editGoodsPrice').val(goodsPrice);
    $('#editInGoodsPrice').val(inGoodsPrice);

    $.ajax({
        url: contextPath + "/goods/querySubGoodsList?goodsId="+goodsId,
        datatype: 'json',
        type: "GET",

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    var resultData = result.data;

                    //清空content数据
                    $('.subGoodsDiv').html('');
                    //加载新内容
                    var goodsList = resultData;
                    if (goodsList != null & goodsList != undefined & goodsList.length > 0) {
                        for (var index = 0; index < goodsList.length; index++) {
                            //组装话题数据表格
                            var goods = goodsList[index];

                            var id = goods.id;
                            var goodsName = goods.goodsName;
                            var goodsModel = goods.goodsModel;
                            var goodsPrice = goods.goodsPrice;
                            var subNum = goods.subNum;

                            var goodsDataHtml = '<tr>' +
                                '<td>'+goodsName+'</td>' +
                                '<td>'+goodsModel+'</td>' +
                                '<td>'+goodsPrice+'</td>' +
                                '<td>'+subNum+'</td>' +
                                '<td>' +
                                '<a class="btn btn-danger btn-sm" onclick="deleteSubGoods('+id+')">' +
                                '<i class="glyphicon glyphicon-trash icon-white"></i>删除' +
                                '</a>' +
                                '</td>' +
                                '</tr>';
                            $('.subGoodsDiv').append(goodsDataHtml);
                        }
                    }
                }
            }
        }
    });


    $('#goodsModal').modal(true);
}



function saveSubGoods() {
    var goodsName = $('#subGoodsName').val();
    var goodsModel = $('#subGoodsModel').val();
    var subNum = $('#subNum').val();
    var goodsId = $('#editGoodsId').val();

    if (goodsName == '') {
        showAlertModel("请填写子商品名称.");
        return;
    }

    if (goodsModel == '') {
        showAlertModel("请填写子商品型号.");
        return;
    }

    if (isNaN(subNum) || goodsPrice == '') {
        showAlertModel("请填写子商品数量.");
    }

    var param = {};
    param.goodsId = goodsId;
    param.goodsName = goodsName;
    param.goodsModel = goodsModel;
    param.subNum = subNum;

    var contextPath = $('#rcContextPath').val();

    $.ajax({
        url: contextPath + "/goods/saveSubGoods",
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
                    //clearGoodsInfo();
                    //queryGoodsList(pageIndex);
                    //$('#goodsModal').modal('hide');

                    var id = resultData.id;
                    var goodsName = resultData.goodsName;
                    var goodsModel = resultData.goodsModel;
                    var subNum = resultData.subNum;

                    var goodsDataHtml = '<tr>' +
                        '<td>'+goodsName+'</td>' +
                        '<td>'+goodsModel+'</td>' +
                        '<td>'+subNum+'</td>' +
                        '<td>' +
                        '<a class="btn btn-danger btn-sm" onclick="deleteSubGoods('+id+')">' +
                        '<i class="glyphicon glyphicon-trash icon-white"></i>删除' +
                        '</a>' +
                        '</td>' +
                        '</tr>';
                    $('.subGoodsDiv').append(goodsDataHtml);
                    clearSubGoodsInfo();
                } else {
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });
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

function clearSubGoodsInfo() {

    $('#subGoodsName').val('');
    $('#subGoodsModel').val('');
    $('#subNum').val('');
}

//删除客户信息按按钮事件
function deleteGoods(goodsId, goodsName, goodsModel) {
    showChooseModel("确认要删除商品【" + goodsName + " " + goodsModel + "】吗?", handleDeleteGoods, goodsId);
}

//删除客户信息按按钮事件
function deleteSubGoods(id) {
    showChooseModel("确认要删除子商品吗?", handleDeleteGoods, id);
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



//处理真正删除事件
function handleDeleteSubGoods(goodsId) {
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

    var mainSearchId = '#subGoodsName'+headerId;

    var goodsModelId = '#subGoodsModel' + headerId;

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
        $(mainSearchId).val(searchObject.goodsName);
        console.log('get3:' + searchKey)
        $autoComplete.show();
    };

    //按回车键获取值，但不能获取==>这里改为直接提交
    var populateSearchField = function () {

        var searchKey = $autoComplete.find("li").eq(selectedItem).text();
        var searchObject = JSON.parse(searchMap[searchKey]);
        $(goodsModelId).val(searchObject.goodsModel);
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


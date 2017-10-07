/**
 * Created by Minutch on 16/12/17.
 */
$(function () {

    queryUserList(0);

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
            queryUserList(pageNumber-1)
        }
    });
}



function simpleCreateCustomer() {

    var cusId = $('#cusId').val();
    var cusName = $('#cusName').val();
    var mobilePhone = $('#mobilePhone').val();
    var houseName = $('#houseName').val();
    var address = $('#address').val();

    //数据检查
    if (cusName==null || cusName=='') {
        showAlertModel('请填写客户姓名');
        return false;
    }
    if (mobilePhone==null || mobilePhone=='') {
        showAlertModel('请填写手机号码');
        return false;
    }
    if (houseName==null || houseName=='') {
        showAlertModel('请填写小区名称');
        return false;
    }
    var param = {};
    param.id = cusId;
    param.cusName = cusName;
    param.mobilePhone = mobilePhone;
    param.houseName = houseName;
    param.address = address;

    var contextPath = $('#rcContextPath').val();

    $.ajax({
        url: contextPath +"/decoration/customer/save",
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
                    window.location.href = contextPath+"/decoration/userList";
                } else{
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });
}



function clearCustomerInfo() {
    $('#cusId').val('');
    $('#cusName').val('');
    $('#mobilePhone').val('');
    $('#houseName').val('');
    $('#address').val('');
}


var pageSize = 10;
function queryUserList(pageIndex) {

    var contextPath = $('#rcContextPath').val();

    var param = {};
    param.pageSize = pageSize;
    param.curPage = pageIndex + 1;
    param.keyword = $('#keyword').val();

    $.ajax({
        url: contextPath +"/decoration/customer/queryList",
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
                    var customerList = resultData.dataList;
                    if (customerList != null & customerList != undefined & customerList.length > 0) {
                        for (var index = 0; index < customerList.length; index++) {
                            //组装话题数据表格
                            var customer = customerList[index];

                            var cusId = customer.id;
                            var cusName = customer.cusName;
                            var mobilePhone = customer.mobilePhone;
                            var houseName = customer.houseName;
                            var address = customer.address;
                            var remark = customer.remark;
                            var gmtCreate = customer.gmtCreatePos;
                            var beNew = customer.beNew;

                            if (remark==null) {
                                remark = '';
                            }
                            if (beNew == true) {
                                cusName += '<span style="color: red">(新)</span>';
                            }

                            var customerDetailUrl = contextPath + '/decoration/customerDetail?cusId='+cusId;
                            var cusDataHtml =
                                '<tr>' +
                                '<td><a href="'+customerDetailUrl+'" target="_self">'+cusName+'</a></td>' +
                                '<td class="center">'+mobilePhone+'</td>' +
                                '<td class="center">'+houseName+'</td>' +
                                '<td class="center">'+address+'</td>' +
                                '<td class="center">'+remark+'</td>' +
                                '<td class="center">' +
                                '<a class="btn btn-info btn-sm" href="'+contextPath+'/decoration/createCustomer?cusId='+cusId+'">' +
                                '<i class="glyphicon glyphicon-edit icon-white"></i>编辑' +
                                '</a> ' +
                                '<a class="btn btn-danger btn-sm" onClick="deleteCustomer('+cusId+')" href="#">' +
                                '<i class="glyphicon glyphicon-trash icon-white"></i>删除' +
                                '</a>' +
                                '</td>' +
                                '</tr>';
                            $('.contentDiv').append(cusDataHtml);
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
function deleteCustomer(cusId) {
    showChooseModel("确认要删除该客户吗?", handleDeleteCustomer, cusId);
}
//处理真正删除事件
function handleDeleteCustomer (cusId) {
    var contextPath = $('#rcContextPath').val();
    $.ajax({
        url: contextPath +"/decoration/customer/deleteById?cusId=" + cusId,
        type: "GET",

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    var resultData = result.data;
                    console.log(resultData);
                    window.location.href = contextPath+"/decoration/userList";
                } else{
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });
}











function getFun(id) {
    return "string" == typeof id ? document.getElementById(id) : id;
}
var Bind = function (object, fun) {
    return function () {
        return fun.apply(object, arguments);
    }
}
function AutoComplete(obj, autoObj, arr) {
    this.obj = getFun(obj);        //输入框
    this.autoObj = getFun(autoObj);//DIV的根节点
    this.value_arr = arr;        //不要包含重复值
    this.index = -1;          //当前选中的DIV的索引
    this.search_value = "";   //保存当前搜索的字符
}
AutoComplete.prototype = {
    //初始化DIV的位置
    init: function () {
        //this.autoObj.style.left = this.obj.offsetLeft + 420 + "px";
//                this.autoObj.style.top = this.obj.offsetTop + this.obj.offsetHeight + "px";
        this.autoObj.style.width = this.obj.offsetWidth - 2 + "px";//减去边框的长度2px
    },
    //删除自动完成需要的所有DIV
    deleteDIV: function () {
        while (this.autoObj.hasChildNodes()) {
            this.autoObj.removeChild(this.autoObj.firstChild);
        }
        this.autoObj.className = "auto_hidden";
    },
    //设置值
    setValue: function (_this) {
        return function () {
            _this.obj.value = this.seq;
            _this.autoObj.className = "auto_hidden";
        }
    },
    //模拟鼠标移动至DIV时，DIV高亮
    autoOnmouseover: function (_this, _div_index) {
        return function () {
            _this.index = _div_index;
            var length = _this.autoObj.children.length;
            for (var j = 0; j < length; j++) {
                if (j != _this.index) {
                    _this.autoObj.childNodes[j].className = 'auto_onmouseout';
                } else {
                    _this.autoObj.childNodes[j].className = 'auto_onmouseover';
                }
            }
        }
    },
    //更改classname
    changeClassname: function (length) {
        for (var i = 0; i < length; i++) {
            if (i != this.index) {
                this.autoObj.childNodes[i].className = 'auto_onmouseout';
            } else {
                this.autoObj.childNodes[i].className = 'auto_onmouseover';
                this.obj.value = this.autoObj.childNodes[i].seq;
            }
        }
    }
    ,
    //响应键盘
    pressKey: function (event) {
        var length = this.autoObj.children.length;
        //光标键"↓"
        if (event.keyCode == 40) {
            ++this.index;
            if (this.index > length) {
                this.index = 0;
            } else if (this.index == length) {
                this.obj.value = this.search_value;
            }
            this.changeClassname(length);
        }
        //光标键"↑"
        else if (event.keyCode == 38) {
            this.index--;
            if (this.index < -1) {
                this.index = length - 1;
            } else if (this.index == -1) {
                this.obj.value = this.search_value;
            }
            this.changeClassname(length);
        }
        //回车键
        else if (event.keyCode == 13) {
            this.autoObj.className = "auto_hidden";
            this.index = -1;
        } else {
            this.index = -1;
        }
    },
    //程序入口
    start: function (event) {
        if (event.keyCode != 13 && event.keyCode != 38 && event.keyCode != 40) {
            this.init();
            this.deleteDIV();
            this.search_value = this.obj.value;
            var valueArr = this.value_arr;
            valueArr.sort();
            if (this.obj.value.replace(/(^\s*)|(\s*$)/g, '') == "") {
                return;
            }//值为空，退出
            try {
                var reg = new RegExp("(" + this.obj.value + ")", "i");
            }
            catch (e) {
                return;
            }
            var div_index = 0;//记录创建的DIV的索引
            for (var i = 0; i < valueArr.length; i++) {
                if (reg.test(valueArr[i])) {
                    var div = document.createElement("div");
                    div.className = "auto_onmouseout";
                    div.seq = valueArr[i];
                    div.onclick = this.setValue(this);
                    div.onmouseover = this.autoOnmouseover(this, div_index);
                    div.innerHTML = valueArr[i].replace(reg, "<strong>$1</strong>");//搜索到的字符粗体显示
                    this.autoObj.appendChild(div);
                    this.autoObj.className = "auto_show";
                    div_index++;
                }
            }
        }
        this.pressKey(event);
        window.onresize = Bind(this, function () {
            this.init();
        });
    }
}
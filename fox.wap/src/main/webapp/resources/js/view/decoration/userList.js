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
                            var gmtCreate = customer.gmtCreate;

                            var customerDetailUrl = contextPath + '/decoration/customerDetail?cusId='+cusId;
                            var cusDataHtml =
                                '<tr>' +
                                '<td><a href="'+customerDetailUrl+'" target="_blank">'+cusName+'</a></td>' +
                                '<td class="center">'+gmtCreate+'</td>' +
                                '<td class="center">'+mobilePhone+'</td>' +
                                '<td class="center">'+houseName+'</td>' +
                                '<td class="center">'+address+'</td>' +
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
                    alert(result.errorMsg);
                }
            }
        }
    });
}

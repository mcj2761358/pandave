/**
 * Created by Minutch on 16/12/17.
 */
$(function () {

    //绑定创建订单按钮事件
    $('#saveStoreBtn').click(function () {
        saveStore();
    });

    //弹出创建订单Modal
    $('#storeModalBtn').click(function () {
        clearStoreInfo();
        $('#storeModal').modal(true);
    });


    //绑定日期控件
    $('.endTimeCon').datetimepicker({
        language:  'zh-CN',
        minView: "month",
        autoclose: 1,
    });

    queryStoreList(0);

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
            queryStoreList(pageNumber - 1)
        }
    });
}

function saveStore() {
    var storeId = $('#editStoreId').val();
    var storeName = $('#editStoreName').val();
    var mobilePhone = $('#editMobilePhone').val();
    var endTime = $('#editEndTime').val();
    var storeLevel = $('#editStoreLevel option:selected').val();
    var storeLevelName = $('#editStoreLevel option:selected').text();
    var remark = $('#editRemark').val();

    var permissionRoleList = $('#editPermissionRole').selectpicker('val');
    console.log(permissionRoleList);

    if (storeName == '') {
        showAlertModel("请填写商户名称.");
        $('#editStoreName').focus();
        return;
    }

    if (mobilePhone == '') {
        showAlertModel("请填写商户手机号码.");
        $('#editMobilePhone').focus();
        return;
    }

    var param = {};
    param.id = storeId;
    param.storeName = storeName;
    param.mobilePhone = mobilePhone;
    param.endTime = endTime;
    param.storeLevel = storeLevel;
    param.permissionRoleList = permissionRoleList;
    param.remark = remark;

    var contextPath = $('#rcContextPath').val();

    $.ajax({
        url: contextPath + "/admin/save",
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
                    clearStoreInfo();
                    queryStoreList(0);
                    $('#storeModal').modal('hide');
                } else {
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });
}


var storePermissionMap = {};
var pageSize = 10;
function queryStoreList(pageIndex) {

    var contextPath = $('#rcContextPath').val();

    var param = {};
    param.pageSize = pageSize;
    param.curPage = pageIndex + 1;
    param.keyword = $('#keyword').val();

    $.ajax({
        url: contextPath + "/admin/queryStoreList",
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
                    var storeList = resultData.dataList;
                    if (storeList != null & storeList != undefined & storeList.length > 0) {
                        for (var index = 0; index < storeList.length; index++) {
                            //组装话题数据表格
                            var store = storeList[index];

                            var storeId = store.id;
                            var storeName = store.storeName;
                            var mobilePhone = store.mobilePhone;
                            var endTime = store.endTimePos;
                            var storeLevel = store.storeLevel;
                            var storeLevelName = store.storeLevelName;
                            var gmtCreate = store.gmtCreatePos;
                            var remark = store.remark;
                            var permissionRole = JSON.stringify(store.permissionRoleList);
                            storePermissionMap[storeId] = permissionRole;

                            var beNew = store.beNew;

                            var showStoreName = storeName;
                            if (beNew == true) {
                                showStoreName += '<span style="color: red">(新)</span>';
                            }

                            if (endTime == null) {
                                endTime = '';
                            }

                            if (remark==null){
                                remark = '';
                            }


                            var storeDataHtml =
                                '<tr class="storeInfo_' + storeId + '">' +
                                '<td class="storeName" value="' + storeName + '">'+showStoreName+'</td>' +
                                '<td class="gmtCreate">' + gmtCreate + '</td>' +
                                '<td class="mobilePhone">' + mobilePhone + '</td>' +
                                '<td class="storeLevelName">' + storeLevelName + '</td>' +
                                '<td class="endTime">' + endTime + '</td>' +
                                '<td class="remark">' + remark + '</td>' +
                                '<td class="center">' +
                                '<input class="storeId" value="' + storeId + '" hidden/>' +
                                '<input class="storeLevel" value="' + storeLevel + '" hidden/>' +
                                '<a class="optionBtn btn btn-info btn-sm" onclick="editStore(' + storeId + ')">' +
                                '<i class="glyphicon glyphicon-edit icon-white"></i>编辑' +
                                '</a> ' +
                                //'<a class="optionBtn btn btn-danger btn-sm" onClick="deleteStore(' + storeId + ',\'' + storeName + ')" href="#">' +
                                //'<i class="glyphicon glyphicon-trash icon-white"></i>删除' +
                                //'</a>' +
                                '</td>' +
                                '</tr>';
                            $('.contentDiv').append(storeDataHtml);
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


function editStore(storeId) {

    var classStore = '.storeInfo_' + storeId;
    var storeId = $(classStore + ' .storeId').val();
    var storeName = $(classStore + ' .storeName').attr('value');
    var mobilePhone = $(classStore + ' .mobilePhone').html();
    var endTime = $(classStore + ' .endTime').html();
    var storeLevel = $(classStore + ' .storeLevel').val();
    var permissionRole = $(classStore + ' .permissionRole').val();
    var storeLevelName = $(classStore + ' .storeLevelName').html();
    var remark = $(classStore + ' .remark').html();

    $('#editStoreId').val(storeId);
    $('#editStoreName').val(storeName);
    $('#editMobilePhone').val(mobilePhone);
    $('#editEndTime').val(endTime);
    $('#editStoreLevel').val(storeLevel);
    $('#editRemark').val(remark);

    $('#editPermissionRole').selectpicker('val', JSON.parse(storePermissionMap[storeId]));

    $('#storeModal').modal(true);
}



function clearStoreInfo() {

    $('#editStoreId').val('');
    $('#editStoreName').val('');
    $('#editMobilePhone').val('');
    $('#editEndTime').val('');
    $('#editStoreLevel').val('');
    $('#editPermissionRole').selectpicker('val', []);
    $('#editRemark').val('');

}


//删除客户信息按按钮事件
function deleteStore(storeId, storeName) {
    showChooseModel("确认要删除商户【" + storeName + "】吗?", handleDeleteStore, storeId);
}
//处理真正删除事件
function handleDeleteStore(storeId) {
    var contextPath = $('#rcContextPath').val();
    $.ajax({
        url: contextPath + "/admin/deleteByStoreId?storeId=" + storeId,
        type: "GET",

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    var resultData = result.data;
                    console.log(resultData);
                    queryStoreList(0);
                } else {
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });
}

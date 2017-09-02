/**
 * Created by Minutch on 16/12/17.
 */
$(function () {

    //绑定创建订单按钮事件
    $('#saveEmployeeBtn').click(function () {
        saveEmployee();
    });

    //弹出创建订单Modal
    $('#employeeModalBtn').click(function () {
        clearEmployeeInfo();
        $('#employeeModal').modal(true);
    });


    //绑定日期控件
    $('.birthTime').datetimepicker({
        language: 'zh-CN',
        minView: "month",
        autoclose: 1
    });

    queryEmployeeList(0);

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
            queryEmployeeList(pageNumber-1)
        }
    });
}


function saveEmployee() {

    var empId = $('#editEmpId').val();
    var empName = $('#editEmpName').val();
    var empMobile = $('#editEmpMobile').val();
    var cardno = $('#editCardno').val();
    var sex = $('#editSex').val();
    var address = $('#editAddress').val();
    var birthday = $('#editBirthday').val();

    if (empName == '') {
        showAlertModel("请填写员工名称.");
        $('#empName').focus();
        return;
    }

    if (empMobile == '') {
        showAlertModel("请填写员工手机号码.");
        $('#editEmpMobile').focus();
        return;
    }


    var param = {};
    param.id = empId;
    param.empName = empName;
    param.empMobile = empMobile;
    param.cardno = cardno;
    param.sex = sex;
    param.address = address;
    param.birthday = birthday;

    var contextPath = $('#rcContextPath').val();

    $.ajax({
        url: contextPath +"/employee/save",
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
                    clearEmployeeInfo();
                    queryEmployeeList(0);
                    $('#employeeModal').modal('hide');
                } else{
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });
}


//function editEmployeeList() {
//    var currentName = $('.editGoodsOption').text();
//    if (currentName == '编辑商品') {
//        $('.optionBtn').show();
//        $('.editGoodsOption').text('编辑完成');
//    } else if (currentName == '编辑完成') {
//        $('.optionBtn').hide();
//        $('.editGoodsOption').text('编辑商品');
//    }
//}


//function showOptionBtn() {
//    var currentName = $('.editGoodsOption').text();
//    if (currentName == '编辑商品') {
//        $('.optionBtn').hide();
//    } else if (currentName == '编辑完成') {
//        $('.optionBtn').show();
//    }
//}



var pageSize = 10;
function queryEmployeeList(pageIndex) {

    var contextPath = $('#rcContextPath').val();

    var param = {};
    param.pageSize = pageSize;
    param.curPage = pageIndex + 1;

    $.ajax({
        url: contextPath +"/employee/queryList",
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
                    var empList = resultData.dataList;
                    if (empList != null & empList != undefined & empList.length > 0) {
                        for (var index = 0; index < empList.length; index++) {
                            //组装话题数据表格
                            var emp = empList[index];

                            var empId = emp.id;
                            var empName = emp.empName;
                            var empMobile = emp.empMobile;
                            var cardno = emp.cardno;
                            var sex = emp.sex;
                            var address = emp.address;
                            var birthday = emp.birthdayPos;

                            if (cardno == null) {
                                cardno = '';
                            }

                            if (sex == null) {
                                sex = '';
                            }

                            if (address == null) {
                                address = '';
                            }

                            if (birthday == null) {
                                birthday = '';
                            }

                            var empDataHtml =
                                '<tr class="empInfo_'+empId+'">' +
                                '<td class="empName">'+empName+'</td>' +
                                '<td class="empMobile">'+empMobile+'</td>' +
                                '<td class="cardno">'+cardno+'</td>' +
                                '<td class="sex">'+sex+'</td>' +
                                '<td class="address">'+address+'</td>' +
                                '<td class="birthday">'+birthday+'</td>' +
                                '<td class="center">' +
                                '<input class="empId" value="'+empId+'" hidden/>'+
                                '<a class="optionBtn btn btn-info btn-sm" onclick="editEmployee('+empId+')">' +
                                '<i class="glyphicon glyphicon-edit icon-white"></i>编辑' +
                                '</a> ' +
                                '<a class="optionBtn btn btn-danger btn-sm" onClick="deleteEmployee('+empId+',\''+empName+'\',)" href="#">' +
                                '<i class="glyphicon glyphicon-trash icon-white"></i>删除' +
                                '</a>' +
                                '</td>' +
                                '</tr>';
                            $('.contentDiv').append(empDataHtml);
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

function editEmployee(empId) {

    var classEmployee =  '.empInfo_'+empId;
    var empId = $(classEmployee + ' .empId').val();
    var empName = $(classEmployee + ' .empName').text();
    var empMobile = $(classEmployee + ' .empMobile').text();
    var cardno = $(classEmployee + ' .cardno').text();
    var sex = $(classEmployee + ' .sex').text();
    var address = $(classEmployee + ' .address').text();
    var birthday = $(classEmployee + ' .birthday').text();

    $('#editEmpId').val(empId);
    $('#editEmpName').val(empName);
    $('#editEmpMobile').val(empMobile);
    $('#editCardno').val(cardno);
    $('#editSex').val(sex);
    $('#editAddress').val(address);
    $('#editBirthday').val(birthday);

    $('#employeeModal').modal(true);
}



function clearEmployeeInfo() {
    $('#editEmpId').val('');
    $('#editEmpName').val('');
    $('#editEmpMobile').val('');
    $('#editCardno').val('');
    $('#editSex').val('');
    $('#editAddress').val('');
    $('#editBirthday').val('');
}


//删除客户信息按按钮事件
function deleteEmployee(empId,empName) {
    showChooseModel("确认要删除员工【"+empName +"】吗?", handleDeleteEmp, empId);
}
//处理真正删除事件
function handleDeleteEmp (empId) {
    var contextPath = $('#rcContextPath').val();
    $.ajax({
        url: contextPath +"/employee/deleteById?empId=" + goodsId,
        type: "GET",

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    var resultData = result.data;
                    console.log(resultData);
                    queryEmployeeList(0);
                } else{
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });
}

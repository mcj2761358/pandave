/**
 * Created by Minutch on 16/12/17.
 */
$(function () {

    //绑定创建客户按钮事件
    $('#saveCustomerBtn').click(function () {
        createCustomer();
    });
});



function createCustomer() {

    var cusId = $('#cusId').val();
    var cusName = $('#cusName').val();
    var mobilePhone = $('#mobilePhone').val();
    var houseName = $('#houseName').val();
    var address = $('#address').val();
    var remark = $('#remark').val();

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
    if (address==null || address=='') {
        showAlertModel('请填写详细地址');
        return false;
    }

    var param = {};
    param.id = cusId;
    param.cusName = cusName;
    param.mobilePhone = mobilePhone;
    param.houseName = houseName;
    param.address = address;
    param.remark = remark;

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


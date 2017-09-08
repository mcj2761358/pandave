$(function () {
    //绑定登录事件
    $('#registerBtn').click(function () {

        var storeName = $('#storeName').val();
        var mobilePhone = $('#mobilePhone').val();
        var password = $('#password').val();


        if (storeName == '') {
            showAlertModel("请填写店铺名称.");
            $('#storeName').focus();
            return;
        }

        if (mobilePhone == '') {
            showAlertModel("请填写手机号码.");
            $('#mobilePhone').focus();
            return;
        }

        var param = {};
        param.storeName = storeName;
        param.mobilePhone = mobilePhone;
        param.password = password;

        var contextPath = $('#rcContextPath').val();

        $.ajax({
            url: contextPath + "/admin/storeRegister",
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
                        window.location.href = '/fox/decoration/userList';
                    } else {
                        showAlertModel(result.errorMsg);
                    }
                }
            }
        });

    })
})
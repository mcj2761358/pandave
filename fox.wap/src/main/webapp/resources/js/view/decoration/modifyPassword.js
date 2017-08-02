$(function () {
    //绑定登录事件
    $('#modifyBtn').click(function () {

        var param = {}
        param.newPassword = $('#newPassword').val();

        $.ajax({
            url: "/fox/store/modifyPassword",
            datatype: 'json',
            type: "POST",
            contentType: 'application/json',
            data: JSON.stringify(param),
            success: function (result) {
                if (result != null) {
                    console.log(result);
                    //请求数据成功
                    if (result.success) {
                        window.location.href = '/fox/decoration/userList'
                    } else {
                        showAlertModel(result.errorMsg);
                    }
                }
            }
        });
    })
})
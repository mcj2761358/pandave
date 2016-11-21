$(function () {
    //绑定登录事件
    $('#loginBtn').click(function () {

        var param = {}
        param.mobilephone = $('#mobilephone').val();
        param.password = $('#password').val();

        $.ajax({
            url: "/fox/employee/login",
            datatype: 'json',
            type: "POST",
            contentType: 'application/json',
            data: JSON.stringify(param),
            success: function (result) {
                if (result != null) {
                    console.log(result);
                    //请求数据成功
                    if (result.success) {
                        window.location.href = '/fox/dance/mainpage/employee/auth'
                    } else {
                        alert(result.errorMsg);
                    }
                }
            }
        });
    })
})
/**
 * Created by Minutch on 16/12/22.
 */
var callbackFun;
var callbackParam;
$(function () {
    $('#sureBtn').click(function () {
        callbackFun(callbackParam);
    });
});

function showChooseModel(content, callback, param) {
    $('#chooseModalContent').empty();
    $('#chooseModalContent').append(content);
    $('#chooseModal').modal(true);
    callbackFun = callback;
    callbackParam = param;
}

function showAlertModel(content) {
    $('#alertModalContent').empty();
    $('#alertModalContent').append(content);
    $('#alertModal').modal(true);
}

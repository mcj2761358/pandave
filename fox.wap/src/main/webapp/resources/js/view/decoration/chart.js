/**
 * Created by Minutch on 16/12/17.
 */
//chart with points
$(function () {

    $("<div id='tooltip'></div>").css({
        position: "absolute",
        display: "none",
        border: "1px solid #fdd",
        padding: "2px",
        "background-color": "#fee",
        opacity: 0.80
    }).appendTo("body");

    reportNearlyDaysOrderAmount();

    reportHotGoodsByAmount();

    reportHotGoodsByNum();


});


/**
 * 近10天销售额
 */
function reportNearlyDaysOrderAmount() {

    var contextPath = $('#rcContextPath').val();

    $.ajax({
        url: contextPath + "/chart/reportNearlyDaysOrderAmount",
        datatype: 'json',
        async: true,
        type: "GET",

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    var resultData = result.data;
                    console.log(resultData);


                    var data = [];
                    //加载新内容
                    var detailList = resultData;
                    if (detailList != null & detailList != undefined & detailList.length > 0) {
                        for (var index = 0; index < detailList.length; index++) {
                            var detail = detailList[index];
                            var amountView = [];
                            amountView.push(detail.gmtCreate);
                            amountView.push(detail.totalAmount);
                            data.push(amountView);
                        }
                    }

                    $.plot("#reportNearlyDaysOrderAmount", [
                        {
                            data: data,
                            label: "近10天销售额"

                        }, //lines: {show: true}
                    ], {
                        series: {
                            bars: {
                                show: true,
                                barWidth: 0.6,
                                align: "center"
                            },
                            //points: {show: true}
                        },
                        xaxis: {
                            mode: "categories",
                            tickLength: 0
                        },
                        colors: ["#2a5caa"],

                        grid: {
                            hoverable: true,
                            clickable: true
                        }
                    });

                    $("#reportNearlyDaysOrderAmount").bind("plothover", function (event, pos, item) {

                        if (item) {
                            var x = item.datapoint[1];

                            $("#tooltip").html("￥ " + x + " 元")
                                .css({top: item.pageY + 5, left: item.pageX + 5})
                                .fadeIn(200);
                        } else {
                            $("#tooltip").hide();
                        }

                    });

                } else {
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });
}

/**
 * 近90天高销售额商品
 */
function reportHotGoodsByAmount() {

    var contextPath = $('#rcContextPath').val();

    $.ajax({
        url: contextPath + "/chart/reportHotGoodsByAmount",
        datatype: 'json',
        async: true,
        type: "GET",

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    var resultData = result.data;
                    console.log(resultData);

                    var treeData = [];
                    var pieData = [];
                    //加载新内容
                    var detailList = resultData;
                    if (detailList != null & detailList != undefined & detailList.length > 0) {
                        for (var index = 0; index < detailList.length; index++) {
                            var detail = detailList[index];
                            var treeView = [];
                            treeView.push(detail.goodsName);
                            treeView.push(detail.goodsAmount);
                            treeData.push(treeView);

                            var pieView = {};
                            pieView['label'] = detail.goodsName;
                            pieView['data'] = detail.goodsAmount;
                            pieData.push(pieView);
                        }
                    }


                    /****************************处理树状图***************************/
                    $.plot("#reportHotGoodsByAmount", [
                        {
                            data: treeData,
                            label: "高销售额商品Top10"

                        }, //lines: {show: true}
                    ], {
                        series: {
                            bars: {
                                show: true,
                                barWidth: 0.6,
                                align: "center"
                            },
                            //points: {show: true}
                        },
                        xaxis: {
                            mode: "categories",
                            tickLength: 0
                        },
                        colors: ["#b54334"],

                        grid: {
                            hoverable: true,
                            clickable: true
                        }
                    });

                    $("#reportHotGoodsByAmount").bind("plothover", function (event, pos, item) {

                        if (item) {
                            var x = item.datapoint[1];

                            $("#tooltip").html("近90天销售 " + x + " 元")
                                .css({top: item.pageY + 5, left: item.pageX + 5})
                                .fadeIn(200);
                        } else {
                            $("#tooltip").hide();
                        }

                    });


                    /****************************处理饼状图***************************/
                    $.plot($("#reportHotGoodsByAmountPie"), pieData,
                        {
                            series: {
                                pie: {
                                    show: true
                                }
                            },
                            grid: {
                                hoverable: true,
                                clickable: true
                            },
                            legend: {
                                show: false
                            }
                        });

                    $("#reportHotGoodsByAmountPie").bind("plothover",  function (event, pos, item) {
                        if (item) {
                            percent = parseFloat(item.series.percent).toFixed(2);
                            var dataDetail = item.datapoint[1][0][1];

                            $("#tooltip").html(item.series.label + ' 销售额:' + dataDetail + '元 占比:(' + percent + '%)')
                                .css({top: pos.pageY + 5, left: pos.pageX + 5})
                                .fadeIn(200);
                        } else {
                            $("#tooltip").hide();
                        }
                    });
                };

            } else {
                showAlertModel(result.errorMsg);
            }
        }
    });
}



/**
 * 近90天高销量商品
 */
function reportHotGoodsByNum() {

    var contextPath = $('#rcContextPath').val();

    $.ajax({
        url: contextPath + "/chart/reportHotGoodsByNum",
        datatype: 'json',
        async: true,
        type: "GET",

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    var resultData = result.data;
                    console.log(resultData);

                    var treeData = [];
                    var pieData = [];
                    //加载新内容
                    var detailList = resultData;
                    if (detailList != null & detailList != undefined & detailList.length > 0) {
                        for (var index = 0; index < detailList.length; index++) {
                            var detail = detailList[index];
                            var treeView = [];
                            treeView.push(detail.goodsName);
                            treeView.push(detail.goodsNum);
                            treeData.push(treeView);

                            var pieView = {};
                            pieView['label'] = detail.goodsName;
                            pieView['data'] = detail.goodsNum;
                            pieData.push(pieView);
                        }
                    }


                    /****************************处理树状图***************************/
                    $.plot("#reportHotGoodsByAmount", [
                        {
                            data: treeData,
                            label: "高销量商品Top10"

                        }, //lines: {show: true}
                    ], {
                        series: {
                            bars: {
                                show: true,
                                barWidth: 0.6,
                                align: "center"
                            },
                            //points: {show: true}
                        },
                        xaxis: {
                            mode: "categories",
                            tickLength: 0
                        },
                        colors: ["#b54334"],

                        grid: {
                            hoverable: true,
                            clickable: true
                        }
                    });

                    $("#reportHotGoodsByNum").bind("plothover", function (event, pos, item) {

                        if (item) {
                            var x = item.datapoint[1];

                            $("#tooltip").html("近90天销量 " + x + " 件")
                                .css({top: item.pageY + 5, left: item.pageX + 5})
                                .fadeIn(200);
                        } else {
                            $("#tooltip").hide();
                        }

                    });


                    /****************************处理饼状图***************************/
                    $.plot($("#reportHotGoodsByNum"), pieData,
                        {
                            series: {
                                pie: {
                                    show: true
                                }
                            },
                            grid: {
                                hoverable: true,
                                clickable: true
                            },
                            legend: {
                                show: false
                            }
                        });

                    $("#reportHotGoodsByAmountPie").bind("plothover",  function (event, pos, item) {
                        if (item) {
                            percent = parseFloat(item.series.percent).toFixed(2);
                            var dataDetail = item.datapoint[1][0][1];

                            $("#tooltip").html(item.series.label + ' 销量:' + dataDetail + '件 占比:(' + percent + '%)')
                                .css({top: pos.pageY + 5, left: pos.pageX + 5})
                                .fadeIn(200);
                        } else {
                            $("#tooltip").hide();
                        }
                    });
                };

            } else {
                showAlertModel(result.errorMsg);
            }
        }
    });
}









/**
 * 近90天高销量商品
 */
function reportHotGoodsByNum() {

    var contextPath = $('#rcContextPath').val();

    $.ajax({
        url: contextPath + "/chart/reportHotGoodsByNum",
        datatype: 'json',
        async: true,
        type: "GET",

        success: function (result) {
            if (result != null) {
                //请求数据成功
                if (result.success) {
                    var resultData = result.data;
                    console.log(resultData);

                    var treeData = [];
                    var pieData = [];
                    //加载新内容
                    var detailList = resultData;
                    if (detailList != null & detailList != undefined & detailList.length > 0) {
                        for (var index = 0; index < detailList.length; index++) {
                            var detail = detailList[index];
                            var treeView = [];
                            treeView.push(detail.goodsName);
                            treeView.push(detail.goodsNum);
                            treeData.push(treeView);

                            var pieView = {};
                            pieView['label'] = detail.goodsName;
                            pieView['data'] = detail.goodsNum;
                            pieData.push(pieView);
                        }
                    }

                    /****************************处理树状图***************************/
                    $.plot("#reportHotGoodsByNum", [
                        {data: treeData,label: "高销量商品Top10"}, //lines: {show: true}
                    ], {
                        series: {
                            bars: {
                                show: true,
                                barWidth: 0.6,
                                align: "center"
                            },
                            //points: {show: true}
                        },
                        xaxis: {
                            mode: "categories",
                            tickLength: 0
                        },
                        colors: ["#faa755"],

                        grid: {
                            hoverable: true,
                            clickable: true
                        }
                    });

                    $("#reportHotGoodsByNum").bind("plothover", function (event, pos, item) {

                        if (item) {
                            var x = item.datapoint[1];

                            $("#tooltip").html("近90天销售 " + x + " 件")
                                .css({top: item.pageY + 5, left: item.pageX + 5})
                                .fadeIn(200);
                        } else {
                            $("#tooltip").hide();
                        }

                    });


                    /****************************处理饼状图***************************/
                    $.plot($("#reportHotGoodsByNumPie"), pieData,
                        {
                            series: {
                                pie: {
                                    show: true
                                }
                            },
                            grid: {
                                hoverable: true,
                                clickable: true
                            },
                            legend: {
                                show: false
                            }
                        });

                    $("#reportHotGoodsByNumPie").bind("plothover",  function (event, pos, item) {
                        if (item) {
                            percent = parseFloat(item.series.percent).toFixed(2);
                            var dataDetail = item.datapoint[1][0][1];

                            $("#tooltip").html(item.series.label + ' 销量:' + dataDetail + '件 占比:(' + percent + '%)')
                                .css({top: pos.pageY + 5, left: pos.pageX + 5})
                                .fadeIn(200);
                        } else {
                            $("#tooltip").hide();
                        }
                    });
                } else {
                    showAlertModel(result.errorMsg);
                }
            }
        }
    });
}






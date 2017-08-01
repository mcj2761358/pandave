//chart with points
$(function () {

    var data = [
        ["一月", 200],
        ["二月", 315],
        ["三月", 400],
        ["四月", 130],
        ["五月", 170],
        ["六月", 292],
        ["七月", 90],
        ["八月", 190],
        ["九月", 230],
        ["十月", 220],
        ["十一月",190],
        ["十二月", 310],
    ]

    $.plot("#sincos", [
        { data: data, label: "2016年度报表"},
    ], {
        series: {
            bars: {
                show: true,
                barWidth: 0.6,
                align: "center"
            }
        },
        xaxis: {
            mode: "categories",
            tickLength: 0
        },
        colors: ["#539F2E"]
    });
});

//畅销商品占比
var data = [
    {label: "冰箱", data: 120000},
    {label: "空调", data: 150000},
    {label: "洗衣机", data: 80000},
    {label: "油烟机", data: 200000},
    {label: "扫地机器人", data: 160000},
    {label: "实木床", data: 91000}
];

if ($("#piechart").length) {
    $.plot($("#piechart"), data,
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

    function pieHover(event, pos, obj) {
        if (!obj)
            return;
        percent = parseFloat(obj.series.percent).toFixed(2);
        $("#hover").html('<span style="font-weight: bold; color: ' + obj.series.color + '">' + obj.series.label + ' (' + percent + '%)</span>');
    }

    $("#piechart").bind("plothover", pieHover);
}

//donut chart
if ($("#donutchart").length) {
    $.plot($("#donutchart"), data,
        {
            series: {
                pie: {
                    innerRadius: 0.5,
                    show: true
                }
            },
            legend: {
                show: false
            }
        });
}


// we use an inline data source in the example, usually data would
// be fetched from a server
var data = [], totalPoints = 300;

function getRandomData() {
    if (data.length > 0)
        data = data.slice(1);

    // do a random walk
    while (data.length < totalPoints) {
        var prev = data.length > 0 ? data[data.length - 1] : 50;
        var y = prev + Math.random() * 10 - 5;
        if (y < 0)
            y = 0;
        if (y > 100)
            y = 100;
        data.push(y);
    }

    // zip the generated y values with the x values
    var res = [];
    for (var i = 0; i < data.length; ++i)
        res.push([i, data[i]])
    return res;
}

// setup control widget
var updateInterval = 30;
$("#updateInterval").val(updateInterval).change(function () {
    var v = $(this).val();
    if (v && !isNaN(+v)) {
        updateInterval = +v;
        if (updateInterval < 1)
            updateInterval = 1;
        if (updateInterval > 2000)
            updateInterval = 2000;
        $(this).val("" + updateInterval);
    }
});

//realtime chart
if ($("#realtimechart").length) {
    var options = {
        series: {shadowSize: 1}, // drawing is faster without shadows
        yaxis: {min: 0, max: 100},
        xaxis: {show: false}
    };
    var plot = $.plot($("#realtimechart"), [getRandomData()], options);

    function update() {
        plot.setData([getRandomData()]);
        // since the axes don't change, we don't need to call plot.setupGrid()
        plot.draw();

        setTimeout(update, updateInterval);
    }

    update();
}
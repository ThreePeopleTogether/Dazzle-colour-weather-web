
var hourChart = echarts.init(document.querySelector('.hour_temperature'));

var fifteenDaysChart = echarts.init(document.querySelector('.fifteenDays_temperature'));

var forecastAirQuality = echarts.init(document.querySelector('.forecast_air_quality'));

$(document).ready(function() {
    init();
})

function init() {

    //获取城市ID
    var cityAddress = "101010100";  //默认为北京

    //获取Location对象的search属性值
    var searchStr = location.search;

    //由于searchStr属性值包括“?”，所以除去该字符
    searchStr = searchStr.substr(1);

    //将searchStr字符串分割成数组，数组中的每一个元素为一个参数和参数值
    var searchs = searchStr.split("&");

    //获得第一个参数和值
    var address = searchs[0].split("=");

    if (address[0] == "cityAddress") {
        cityAddress = address[1];
    }

    var cityNameData = document.getElementById('city');
    //获取城市名称
    var addressName = searchs[1].split("=");

    var nameValue = decodeURIComponent(addressName[1]); //将中文编码解析成中文

    cityNameData.innerHTML = nameValue;

    try {
        RealtimeData(cityAddress);
        Hour(cityAddress);
        fifteen(cityAddress);
        lifePoint(cityAddress);
        airFut(cityAddress);
        airQuality(cityAddress);
    }catch(error) {
        alert(error.message);
    }
}

//当日气温部分
function RealtimeData(cityAddress) {
    $.ajax({
        url: "http://mzkt.xyz/weather/GetNow?",
        alert: true,
        type: "post",
        data: {
            "cityID": cityAddress
        },
        async: false,
        dataType: "json",
        success: function (result) {
            //加载数据
            var dataTemp = JSON.stringify(result);
            var iconPath = "icon/";
            iconPath = iconPath + result.icon + ".png";
            $("#weatherIcon").attr("src", iconPath);
            dataTemp = $("#hourTime").text(result.obsTime);
            dataTemp = $("#nowTemperature").text(result.temp + "℃");
            dataTemp = $("#weatherName").text(result.text);
            dataTemp = $("#cloudCover").text(result.cloud + "%");
            dataTemp = $("#windDirection").text(result.windDir);
            dataTemp = $("#humidity").text(result.humidity + "%");
            dataTemp = $("#visibility").text(result.vis + "km");
            dataTemp = $("#windSpeed").text(result.windSpeed + "km/h");
            dataTemp = $("#temperatureBody").text(result.feelsLike + "℃");
            dataTemp = $("#airPressure").text(result.pressure + "hPa");
            dataTemp = $("#precipitation").text(result.precip + "mm");
        },
        error: function () {
            throw new Error("数据加载失败！请检查网络链接是否正确。");
        }
    })
}

//逐小时预报部分
function Hour(cityAddress) {
    $.ajax({
        url: "http://mzkt.xyz/weather/GetDayByHour?",
        alert: true,
        type: "post",
        data: {
            "cityID": cityAddress
        },
        async: false,
        dataType: "json",
        success: function (result) {
            var xData = [];
            var temperatureData = [];
            for (var i = 1; i < result.length; i++) {
                xData.push(result[i - 1].fxTime);
            }
            for (var j = 0; j < result.length; j++) {
                temperatureData.push(result[j].temp);
            }
            var hourOption = {
                tooltip: {
                    trigger: 'axis'
                },

                legend: { //图例
                    data: ['气温']
                },

                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                label: {
                    show: true,
                    position: 'top'
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: xData
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    name: '气温',
                    type: 'line',
                    stack: '总量',
                    data: temperatureData
                },]
            };
            hourChart.setOption(hourOption); //加载图表
            window.addEventListener('resize', function () {
                hourChart.resize();
            });

        },
        error: function () {
            throw new Error("数据加载失败！请检查网络链接是否正确。");
        }
    })
}

//15日气温部分
function fifteen(cityAddress) {
    $.ajax({
        url: "http://mzkt.xyz/weather/GetFifteen?", //数据地址
        alert: true,
        type: "post",
        data: {
            "cityID": cityAddress
        },
        async: false,
        dataType: "json",
        success: function (result) {
            var xData = [];
            var maxData = [];
            var minData = [];
            for (var i = 1; i < result.length; i++) {
                xData.push(result[i - 1].fxDate);
            }
            for (var j = 0; j < result.length; j++) {
                maxData.push(result[j].tempMax);
            }
            for (var k = 0; k < result.length; k++) {
                minData.push(result[k].tempMin);
            }
            var fifteenDaysOption = {
                tooltip: {
                    trigger: 'axis'
                },
                legend: { //图例
                    data: ['最高气温', '最低气温']
                },
                toolbox: {
                    show: true,
                    feature: {
                        dataZoom: {
                            yAxisIndex: 'none'
                        },
                        dataView: {
                            readOnly: false
                        },
                        magicType: {
                            type: ['line', 'bar']
                        },
                        restore: {},
                        saveAsImage: {}
                    }
                },
                label: {
                    show: true,
                    position: 'top'
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: xData, //X轴数据
                    axisLabel: {
                        interval: 0
                    }, //显示X轴所有数据
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} °C'
                    }
                },
                series: [{
                    name: '最高气温',
                    type: 'line',
                    data: maxData, //最高气温数据
                    markPoint: {
                        data: [{
                            type: 'max',
                            name: '最大值'
                        },
                            {
                                type: 'min',
                                name: '最小值'
                            }
                        ]
                    },
                    markLine: {
                        data: [{
                            type: 'average',
                            name: '平均值'
                        }]
                    }
                },
                    {
                        name: '最低气温',
                        type: 'line',
                        data: minData, //最低气温数据
                        markPoint: {
                            data: [{
                                name: '周最低',
                                value: -2,
                                xAxis: 1,
                                yAxis: -1.5
                            }]
                        },
                        markLine: {
                            data: [{
                                type: 'average',
                                name: '平均值'
                            },
                                [{
                                    symbol: 'none',
                                    x: '90%',
                                    yAxis: 'max'
                                }, {
                                    symbol: 'circle',
                                    label: {
                                        position: 'start',
                                        formatter: '最大值'
                                    },
                                    type: 'max',
                                    name: '最高点'
                                }]
                            ]
                        }
                    }
                ]
            }
            fifteenDaysChart.setOption(fifteenDaysOption);
            window.addEventListener('resize', function () {
                fifteenDaysChart.resize();
            });
        },
        error: function () {
            throw new Error("数据加载失败！请检查网络链接是否正确。");
        }
    });
}

//生活指数部分
function lifePoint(cityAddress) {
    $.ajax({
        url: "http://mzkt.xyz/weather/GetIndices?",
        alert: true,
        type: "post",
        data: {
            "cityID": cityAddress
        },
        async: false,
        dataType: "json",
        success: function (result) {
            var pointData = JSON.stringify(result);
            for (var i = 0; i < result.length; i++) {
                pointData = $("#wearPoint").text(result[3].category);
                pointData = $("#exercisePoint").text(result[1].category);
                pointData = $("#coldPoint").text(result[4].category);
                pointData = $("#carPoint").text(result[2].category);
            }
        },
        error: function () {
            throw new Error("数据加载失败！请检查网络链接是否正确。");
        }
    })
}

//空气质量预报部分
function airFut(cityAddress) {
    $.ajax({
        url: "http://mzkt.xyz/weather/GetAirFut?", //数据地址
        alert: true,
        type: "post",
        data: {
            "cityID": cityAddress
        },
        async: false,
        dataType: "json",
        success: function (result) {
            var xData = [];
            var aqiData = [];
            for (var i = 1; i < result.length; i++) {
                xData.push(result[i - 1].fxDate);
            }
            for (var j = 0; j < result.length; j++) {
                aqiData.push(result[j].aqi);
            }
            var forecastAirQualityOption = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        label: {
                            backgroundColor: '#6a7985'
                        }
                    }
                },
                legend: {
                    data: ['空气质量数值']
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                color: {
                    type: 'linear',
                    x: 0.5,
                    y: 0.5,
                    r: 0.5,
                    colorStops: [{
                        offset: 0,
                        color: '#6495ed' // 0% 处的颜色
                    }, {
                        offset: 1,
                        color: '#e5f0fe' // 100% 处的颜色
                    }],
                    global: false // 缺省为 false
                },
                xAxis: [{
                    type: 'category',
                    boundaryGap: false,
                    data: xData,
                    axisLabel: {
                        interval: 0
                    }, //显示X轴所有数据
                }],
                yAxis: [{
                    type: 'value'
                }],
                series: [{
                    name: '空气质量数值',
                    type: 'line',
                    stack: '总量',
                    areaStyle: {},
                    label: {
                        show: true,
                        position: 'top'
                    },
                    emphasis: {
                        focus: 'series'
                    },
                    data: aqiData
                },]
            };
            forecastAirQuality.setOption(forecastAirQualityOption);
            window.addEventListener('resize', function () {
                forecastAirQuality.resize();
            });

        },
        error: function () {
            throw new Error("数据加载失败！请检查网络链接是否正确。");
        }
    })
}

//空气质量部分
function airQuality(cityAddress) {
    $.ajax({
        url: "http://mzkt.xyz/weather/GetAirNow?", //数据地址
        alert: true,
        type: "post",
        data: {
            "cityID": cityAddress
        },
        async: false,
        dataType: "json",
        success: function (result) {
            var airData = JSON.stringify(result);
            airData = $("#aqiData").text(result.aqi);
            airData = $("#PM10Data").text(result.pm10);
            airData = $("#PM2p5Data").text(result.pm2p5);
            airData = $("#No2Data").text(result.no2);
            airData = $("#So2Data").text(result.so2);
            airData = $("#O3Data").text(result.o3);
            airData = $("#CoData").text(result.co);
        },
        error: function () {
            throw new Error("数据加载失败！请检查网络链接是否正确。");
        }
    })
}
<!doctype html>
<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
    <% pageContext.setAttribute("APP_PATH", request.getContextPath()); %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>轨迹回放</title>
    <link rel="stylesheet" href="${APP_PATH}/static/css/demo-center.css"/>
    <link rel="stylesheet" href="${APP_PATH}/static/css/bootstrap.css"/>
    <link rel="stylesheet" href="${APP_PATH}/static/css/layui.css" media="all">
    <script src="${APP_PATH}/static/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${APP_PATH}/static/jquery.min.js"></script>
    <script type="text/javascript" src="${APP_PATH}/static/jquery-3.2.1.min.js"></script>
    <style>
        html, body {
            height: 100%;
            width: 100%;
        }

        .layui-row {
            height: 10%;
            width: 100%;
        }

        #container {
            height: 90%;
            width: 100%;
        }

        .input-card .btn {
            margin-right: 1.2rem;
            width: 9rem;
        }

        .input-card .btn:last-child {
            margin-right: 0;
        }
    </style>
</head>
<body>

<div class="layui-row" id="header">
    <div class="layui-col-xs6">
        <div class="layui-form">
            <div class="layui-form-item">
                <div class="layui-inline ">
                    <label class="layui-form-label ">请选择日期</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" id="test11" placeholder="yyyy年MM月dd日">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="layui-col-xs6 ">
        <form class="layui-form">
            <div class="layui-form-item">
                <label class="layui-form-label">请选择车辆</label>
                <div class="layui-col-xs3 layui-input-block">
                    <select class="form-control" lay-filter="plate" id="add_role_name">
                        <option value="">请选择车辆</option>
                    </select>
                </div>
                <button type="button" class="btn btn-primary" id="button">查询</button>
            </div>
        </form>
    </div>
</div>
</form>
<div id="container"></div>
<div class="input-card">
    <h4>轨迹回放控制</h4>
    <div class="input-item">
        <input type="button" class="btn" value="开始动画" id="start" onclick="startAnimation()"/>
        <input type="button" class="btn" value="暂停动画" id="pause" onclick="pauseAnimation()"/>
    </div>
    <div class="input-item">
        <input type="button" class="btn" value="继续动画" id="resume" onclick="resumeAnimation()"/>
        <input type="button" class="btn" value="查看超速" id="stop" onclick="stopAnimation()"/>
    </div>
</div>
<script type="text/javascript"
        src="https://webapi.amap.com/maps?v=1.4.14&key=b4e288825a07a480ca57cd9113411feb"></script>
<script>
    var form, layer, laydate,plateNum,date1,marker;
    layui.use(['form', 'layer', 'laydate'], function () {
        form = layui.form;
        layer = layui.layer
        laydate = layui.laydate;
    })
    function selectplateName(value) {
        $.ajax({
            url: "${APP_PATH}/plateNum",
            type: "POST",
            data: {"value": value},
            success: function (data) {
                $.each(data.extend.list, function (index, item) {
                    var option = $("<option></option>").append(item.platenum).attr("value", item.platenum);
                    option.appendTo("#header select");
                })
                form.render("select");
            }
        })
    }
    layui.use('form', function () {
        var form = layui.form;
        form.on('select(plate)', function (data) {
            plateNum = data.value;
        });
    });
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        //自定义格式
        laydate.render({
            elem: '#test11'
            , format: 'yyyy年MM月dd日'
            , done: function (value, date, endDate) {
                date1 = value;
                selectplateName(value);    // 调用查询方法
            }
        });
    })
    var map = new AMap.Map("container", {
        resizeEnable: true,
        center: [116.397428, 39.90923],
        zoom: 17
    });

    var lngLat=[],linearry=[];
    $("button").click(function () {
        map.clearMap();
        $.post(
            "${APP_PATH}/Longiandlati",
            {time: date1, plateNum: plateNum},
            function (data) {
                linearry=[];
                $.each(data.extend.list.list, function (index, item) {
                    linearry.push([item.lon,item.lat])
                    lngLat.push(new AMap.LngLat(item.lon, item.lat));
                })
                marker = new AMap.Marker({
                    map: map,
                    position:linearry[0],
                    icon: "https://webapi.amap.com/images/car.png",
                    offset: new AMap.Pixel(-26, -13),
                    autoRotation: true,
                    angle:-90,
                });
                var polyline = new AMap.Polyline({
                    map: map,
                    path: linearry,
                    showDir:true,
                    strokeColor: "#28F",  //线颜色
                    // strokeOpacity: 1,     //线透明度
                    strokeWeight: 6,      //线宽
                    // strokeStyle: "solid"  //线样式
                });
                polyline.show();
                map.add(polyline);
                var passedPolyline = new AMap.Polyline({
                    map: map,
                    //   path: linearry,
                    strokeColor: "#AF5",  //线颜色
                    // strokeOpacity: 1,     //线透明度
                    strokeWeight: 6,      //线宽
                    // strokeStyle: "solid"  //线样式
                });
                marker.on('moving', function (e) {
                    passedPolyline.setPath(e.passedPath);
                });
                map.setFitView();
            }
        )
    });
    function startAnimation () {
        marker.moveAlong(linearry, 300);
    }
    function pauseAnimation () {
        marker.pauseMove();
    }
    function resumeAnimation () {
        marker.resumeMove();
    }
    function stopAnimation () {



        $.post("test.php", { name: "John", time: "2pm" },
            function(data){
                alert("Data Loaded: " + data);
            });




    }
</script>
<script type="text/javascript" src="https://a.amap.com/jsapi_demos/static/demo-center/data/food.js?v=1.0"></script>
</body>
</html>

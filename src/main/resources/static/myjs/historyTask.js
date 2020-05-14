layui.use(['form', 'layer','jquery','table'], function () {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
    layer = layui.layer;
    //第一个实例
    table.render({
        elem: '#historyTask_datagrid'
        , url: '/workFlow/queryHistoryTask' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'id', title: '任务ID'}
            , {field: 'processInstanceId', title: '流程实例ID'}
            , {field: 'name', title: '任务名称'}
            , {field: 'assignee', title: '办理人'}
            , {field: 'startTime', title: '开始时间',templet : "<div>{{layui.util.toDateString(d.startTime, 'yyyy年MM月dd日 HH:mm:ss')}}</div>"}
            , {field: 'endTime', title: '结束时间',templet : "<div>{{layui.util.toDateString(d.endTime, 'yyyy年MM月dd日 HH:mm:ss')}}</div>"}
        ]]

    });


});
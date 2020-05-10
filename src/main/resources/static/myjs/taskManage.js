var tableIns;
layui.use(['form', 'layer','jquery','table'], function () {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
    layer = layui.layer;
    //待办任务列表
    tableIns = table.render({
        elem: '#taskList',
        url : '/workFlow/loadCurrentUserTask',
        cellMinWidth : 95,
        page : true,
        height : "full-220",
        limits : [10,15,20,25],
        defaultToolbar: ['filter'],
        limit : 10,
        id : "taskListTable",
        cols : [[
            {field: 'id', title: '任务ID', minWidth:100, align:"center"},
            {field: 'name', title: '待办任务名称', minWidth:100, align:"center"},
            {field: 'createTime', title: '创建时间', minWidth:100, align:"center"},
            {field: 'assignee', title: '办理人',  align:'center'},
            {title: '操作', minWidth:175, templet:'#taskListBar',fixed:"right",align:"center"}
        ]]
    });
});
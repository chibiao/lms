var tableIns;
var taskRecord;
var leaveRecord;
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
    //列表操作
    table.on('tool(taskList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
            taskRecord = data;
        if(layEvent === 'toDoTask'){ //办理任务
            $.ajax({
                data:{},
                type:"get",
                url:"/workFlow/toDoTask/"+data.id,
                dataType:"json",
                success:function (result) {
                    if (result.code == "0000") {
                        leaveRecord = result.data;
                        //表单初始赋值
                        form.val('completeTask', {
                            "id": result.data.id ,// "name": "value"
                            "leaveTitle": result.data.leaveTitle,
                            "leaveStatus": result.data.leaveStatus,
                            "leaveDays": result.data.leaveDays,
                            "leaveBeginTime": result.data.leaveBeginTime,
                            "leaveReason": result.data.leaveReason
                        });
                        task=layer.open({
                            type: 1,
                            content: $("#task"), //这里content是一个普通的String
                            area: ['600px', '500px']
                        });
                        //待办任务列表
                        table.render({
                            elem: '#commentList',
                            url : '/workFlow/loadAllCommentByTaskId?taskId='+data.id,
                            cellMinWidth : 95,
                            height : "full-320",
                            id : "commentListTable",
                            cols : [[
                                {field: 'time', title: '批注时间', minWidth:100, align:"center"},
                                {field: 'userId', title: '批注人', minWidth:100, align:"center"},
                                {field: 'message', title: '批注内容', minWidth:100, align:"center"}
                            ]]
                        });
                        form.render();
                    }
                }
            });
        }else if (layEvent === 'viewProcessByTaskId') {
            viewProcessImage=layer.open({
                title : "修改待办任务",
                type: 1,
                content: $("#taskImage"), //这里content是一个普通的String
                area: ['500px', '500px']
            });
            $("#taskImage").attr("src","/workFlow/viewProcessImage?taskId="+data.id);
        }
    });

    //监控按钮事件
    $(".dotask").click(function(obj){
        var outcome=this.value;
        $.post("/workFlow/doTask",{
            taskId:taskRecord.id,
            outcome:outcome,
            id:leaveRecord.id,
            comment:$("#comment").val()
        },function(result){
            if(result.code == "0000"){
                if (result.data){
                    layer.msg("办理成功");
                    // 刷新表格
                    tableIns.reload();
                    //关闭当前frame
                    layer.close(task);
                } else {
                    layer.msg(result.message);
                }
            }else {
                layer.msg(result.message);
            }
        })
    })

});
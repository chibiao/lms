var tableIns;
layui.use(['form', 'layer','jquery','table','laydate'], function () {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
    layer = layui.layer;
    var laydate = layui.laydate;
    //执行一个laydate实例
    laydate.render({
        elem: '#leaveBeginTime' //指定元素
        ,type: 'date'
        ,format:'yyyy-MM-dd'
        ,trigger: 'click'//呼出事件改成click 解决闪退问题
        ,min: 0
        ,max: 30
    });
    //执行一个laydate实例
    laydate.render({
        elem: '#L_leaveBeginTime' //指定元素
        ,type: 'date'
        ,format:'yyyy-MM-dd'
        ,trigger: 'click'//呼出事件改成click 解决闪退问题
    });
    //执行一个laydate实例
    laydate.render({
        elem: '#L_leaveEndTime' //指定元素
        ,type: 'date'
        ,format:'yyyy-MM-dd'
        ,trigger: 'click'//呼出事件改成click 解决闪退问题
    });
    //第一个实例
    tableIns = table.render({
        elem: '#leaveRecord_datagrid'
        , url: '/leaveRecord/selectLeaveRecord' //数据接口
        , page: true
        , toolbar: '#tableToolBar'
        , defaultToolbar: ['filter']
        , cols: [[ //表头
            {field: 'leaveTitle', title: '请假单标题',align:"center"}
            , {field: 'leaveReason', title: '请假原因',align:"center"}
            , {field: 'leaveDays', title: '请假天数',align:"center"}
            , {field: 'leaveBeginTime', title: '请假开始时间',align:"center"}
            , {field: 'leaveEndTime', title: '请假结束时间',align:"center"}
            , {field: 'leaveStatus', title: '状态',templet:function(d){
                    var html="";
                    if(d.leaveStatus==0){
                        html="<font color=red>未提交</font>"
                    }else if(d.leaveStatus==1){
                        html="<font color=yellow>审批中</font>"
                    }else if(d.leaveStatus==2){
                        html="<font color=blue>审批完成</font>"
                    }else if(d.leaveStatus==3){
                        html="<font color=gray>已放弃</font>"
                    }else{
                        html="<font color=red>未知状态</font>"
                    }
                    return html;
                },align:"center"}
            ,{title: '操作', minWidth:280, templet:'#leaveRecordListBar',fixed:"right",align:"center"}
        ]]
    });

    //监听头工具栏
    table.on('toolbar(leaveRecord)', function (obj) {
        switch(obj.event){
            case 'add':
                $("#addLeaveRecord")[0].reset();
                addLeaveRecord=layer.open({
                    type: 1,
                    content: $("#addLeaveRecord"), //这里content是一个普通的String
                    area: ['700px', '400px']
                });
                break;
        }
    });
    //列表操作
    table.on('tool(leaveRecord)', function(obj){
        var data = obj.data;
        var id = obj.data.id;
        switch(obj.event){
            case 'edit':
                //表单初始赋值
                form.val('addLeaveRecord', {
                    "id": data.id ,// "name": "value"
                    "leaveTitle": data.leaveTitle,
                    "leaveStatus": data.leaveStatus,
                    "leaveDays": data.leaveDays,
                    "leaveBeginTime": data.leaveBeginTime,
                    "leaveReason": data.leaveReason
                });
                updateLeaveRecord=layer.open({
                    type: 1,
                    content: $("#addLeaveRecord"), //这里content是一个普通的String
                    area: ['700px', '400px']
                });
                break;
            case 'del':
                layer.confirm('确定删【'+data.leaveTitle+'】请假单吗？',{icon:3, title:'提示信息'},function(index){
                    $.ajax({
                        data:{
                            _method:'DELETE'
                        },
                        type:"post",
                        url:"/leaveRecord/deleteLeaveRecord/"+id,
                        dataType:"json",
                        success:function (result) {
                            if(result.data){
                                layer.msg("提交成功");
                                //刷新table
                                tableIns.reload();
                                //关闭提示框
                                layer.close(index);
                            }else{
                                layer.msg(result.message);
                            }
                        }
                    });
                });
                break;
            case 'startProcess':
                layer.confirm('确定要提交【'+data.leaveTitle+'】请假单吗？',{icon:3, title:'提示信息'},function(index){
                    $.post("/workFlow/startProcess",{
                        id : data.id  //将需要删除的id作为参数传入
                    },function(result){
                        if(result.code == "0000"){
                            if (result.data){
                                layer.msg("提交成功");
                                //刷新table
                                tableIns.reload();
                                //关闭提示框
                                layer.close(index);
                            } else {
                                layer.msg(result.message);
                            }
                        }else {
                            layer.msg(result.message);
                        }
                    })
                });
                break;
            case 'viewSpProcess':
                //表单初始赋值
                form.val('seeLeaveRecord', {
                    "id": data.id ,// "name": "value"
                    "leaveTitle": data.leaveTitle,
                    "leaveStatus": data.leaveStatus,
                    "leaveDays": data.leaveDays,
                    "leaveBeginTime": data.leaveBeginTime,
                    "leaveReason": data.leaveReason
                });
                //待办任务列表
                table.render({
                    elem: '#commentList',
                    url : '/workFlow/loadCommentByLeaveRecordId?id='+data.id,
                    cellMinWidth : 95,
                    height : "full-320",
                    id : "commentListTable",
                    cols : [[
                        {field: 'time', title: '批注时间', minWidth:100, align:"center"},
                        {field: 'userId', title: '批注人', minWidth:100, align:"center"},
                        {field: 'message', title: '批注内容', minWidth:100, align:"center"}
                    ]]
                });
                seeLeaveRecord=layer.open({
                    type: 1,
                    content: $("#seeLeaveRecord"), //这里content是一个普通的String
                    area: ['700px', '400px']
                });
                break;
        }
    });

    //监听提交
    form.on('submit(add)', function(data) {
        var id = data.field.id;
        if (id!=null && id!=''){
            // 修改
            $.ajax({
                data:data.field,
                type:"put",
                url:"/leaveRecord/updateLeaveRecord",
                dataType:"json",
                success:function (result) {
                    if(result.code == "0000"){
                        if (result.data){
                            layer.msg("修改成功");
                            //关闭当前frame
                            layer.close(updateLeaveRecord);
                            table.reload('leaveRecord_datagrid');
                        } else {
                            layer.msg(result.message);
                        }
                    }else {
                        layer.msg(result.message);
                    }
                }
            });
        } else {
            // 添加
            $.ajax({
                data:data.field,
                type:"post",
                url:"/leaveRecord/addLeaveRecord",
                dataType:"json",
                success:function (result) {
                    if(result.code == "0000"){
                        if (result.data){
                            layer.msg("添加成功");
                            //关闭当前frame
                            layer.close(addLeaveRecord);
                            table.reload('leaveRecord_datagrid');
                        } else {
                            layer.msg(result.message);
                        }
                    }else {
                        layer.msg(result.message);
                    }
                }
            });
        }
        return false;
    });

    //点击搜索条件
    $('#search').on('click', function () {
        // 搜索条件
        var leaveTitle = $('#L_leaveTitle').val();
        var leaveReason = $('#L_leaveReason').val();
        var leaveBeginTime = $('#L_leaveBeginTime').val();
        var leaveEndTime = $('#L_leaveEndTime').val();
        table.reload('leaveRecord_datagrid', {
            method: 'get'
            , where: {
                'leaveTitle':leaveTitle,
                'leaveReason':leaveReason,
                'leaveBeginTime':leaveBeginTime,
                'leaveEndTime':leaveEndTime
            }
            , page: {
                curr: 1
            }
        });
    });
});
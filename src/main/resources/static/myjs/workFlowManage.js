var tableDeploymentIns;
var tableprocessDefinitionIns;
layui.use(['form', 'layer','jquery','table','upload'], function () {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
    layer = layui.layer;
    var upload=layui.upload;
    //流程部署列表
    tableDeploymentIns = table.render({
        elem: '#deploymentList',
        url : '/workFlow/loadAllDeployment',
        cellMinWidth : 95,
        toolbar: '#deploymentTableToolBar',
        page : true,
        defaultToolbar: ['filter'],
        id : "deploymentListTable",
        cols : [[
            {field: 'id', title: '部署ID', minWidth:100, align:"center"},
            {field: 'name', title: '部署名称', minWidth:100, align:"center"},
            {field: 'deploymentTime', title: '部署时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#deploymentListBar',fixed:"right",align:"center"}
        ]]
    });
    //流程定义列表
    tableprocessDefinitionIns = table.render({
        elem: '#processDefinitionList',
        url : '/workFlow/loadAllProcessDefinition',
        cellMinWidth : 95,
        page : true,
        id : "processDefinitionListTable",
        cols : [[
            {field: 'id', title: '流程定义ID', minWidth:100, align:"center"},
            {field: 'name', title: '流程定义名称', minWidth:100, align:"center"},
            {field: 'key', title: '流程定义KEY', minWidth:100, align:"center"},
            {field: 'version', title: '流程定义版本', minWidth:100, align:"center"},
            {field: 'deploymentId', title: '部署ID', minWidth:100, align:"center"},
            {field: 'resourceName', title: '资源文件名[bpmn]', minWidth:100, align:"center"},
            {field: 'diagramResourceName', title: '资源文件名[png]', minWidth:100, align:"center"}
        ]]
    });

    //查询
    $(".search_btn").on("click",function(){
        var params=$("#searchForm").serialize();
        table.reload('deploymentListTable', {
            url: '/workFlow/loadAllDeployment?'+params
        });
        table.reload('processDefinitionListTable', {
            url: '/workFlow/loadAllProcessDefinition?'+params
        });
    });

    //列表操作
    table.on('tool(deploymentList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'del'){ //删除
            layer.confirm('确定删【'+data.name+'】流程吗？',{icon:3, title:'提示信息'},function(index){
                $.post("/workFlow/deleteWorkFlow",{
                    deploymentId : data.id  //将需要删除的id作为参数传入
                },function(result){
                    if(result.code == "0000"){
                        if (result.data){
                            layer.msg("删除成功");
                            //刷新table
                            tableDeploymentIns.reload();
                            tableprocessDefinitionIns.reload();
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
        }else if(layEvent==="viewProcessImage"){
            viewProcessImage=layer.open({
                title : "查看["+data.name+"]流程图",
                type: 1,
                content: $("#workFlowImage"), //这里content是一个普通的String
                area: ['500px', '500px']
            });
            $("#workFlowImage").attr("src","/workFlow/viewProcessImage?deploymentId="+data.id);
        }
    });

    //监听头工具栏事件
    table.on('toolbar(deploymentList)', function(obj){
        switch(obj.event){
            case 'add':
                frm=layer.open({
                    type: 1,
                    content: $("#frm"), //这里content是一个普通的String
                    area: ['500px', '300px']
                });
                break;
        }
    });
    //选完文件后不自动上传
    upload.render({
        elem: '#uploadProcess'
        ,url: '/workFlow/addWorkFlow'
        ,auto: false
        ,accept:'file'//选择上传文件
        ,acceptMime:'application/zip'//打开文件选择框默认显示压缩文件
        ,exts: 'zip'//校验类型
        ,field:'mf'
        ,data:{
            deploymentName:function(){
                return $('#deploymentName').val();
            }
        }
        ,bindAction: '#addWorkFlow'
        ,done: function(result){
            if(result.code == "0000"){
                if (result.data){
                    layer.msg("添加成功");
                    //关闭当前frame
                    layer.close(frm);
                    table.reload('deploymentListTable');
                    table.reload('processDefinitionListTable');
                } else {
                    layer.msg(result.message);
                }
            }else {
                layer.msg(result.message);
            }
        }
        ,error:function(){
            layer.msg('部署失败');
        }
    });
});
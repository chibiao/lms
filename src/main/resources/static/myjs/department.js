layui.use(['form', 'layer','jquery','table'], function () {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
    layer = layui.layer;
    //第一个实例
    table.render({
        elem: '#department_datagrid'
        , url: '/department/departmentList' //数据接口
        , toolbar:'#toolbar'
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'deptNo', title: '院系编号'}
            , {field: 'deptName', title: '院系名称'}
            , {field: 'deptDesc', title: '院系描述'}
        ]]

    });

    //监听头工具栏
    table.on('toolbar(department)', function (obj) {
        switch(obj.event){
            case 'add':
                addDepartment=layer.open({
                    type: 1,
                    content: $("#addDepartment"), //这里content是一个普通的String
                    area: ['500px', '300px']
                });
                break;
        }
    });

    //监听行工具事件
    table.on('tool(department)', function (obj) {
        //获取每行的数据
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                var deptNo=obj.data.deptNo;
                $.ajax({
                    data:{
                        _method:'DELETE'
                    },
                    type:"post",
                    url:"/department/deleteDepartment/"+deptNo,
                    dataType:"json",
                    success:function (result) {
                        if(result.data){
                            layer.msg("删除成功");
                            table.reload('department_datagrid'); //只重载数据
                        }else{
                            layer.msg(result.msg);
                        }
                    }
                });
            });
        }
    });

    //监听提交
    form.on('submit(add)', function(data) {
        $.ajax({
            data:data.field,
            type:"post",
            url:"/department/addDepartment",
            dataType:"json",
            success:function (result) {
                if(result.code == "0000"){
                    if (result.data){
                        layer.msg("添加成功");
                        //关闭当前frame
                        layer.close(addDepartment);
                        table.reload('department_datagrid');
                    } else {
                        layer.msg(result.message);
                    }
                }else {
                    layer.msg(result.message);
                }
            }
        });
        return false;
    });

});
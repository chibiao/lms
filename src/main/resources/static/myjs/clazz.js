layui.use(['form', 'layer','jquery','table','laydate'], function () {
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;
    var form = layui.form,
    layer = layui.layer;
    //第一个实例
    table.render({
        elem: '#clazz_datagrid'
        , url: '/clazz/clazzList' //数据接口
        , toolbar:'#toolbar'
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'department.deptName', title: '院系名称'
                ,templet: function(d){
                    return d.department.deptName;
                }
            },
            {field: 'specialty.specialtyName', title: '专业名称'
                ,templet: function(d){
                    return d.specialty.specialtyName;
                }
            }
            ,{field: 'clazzNo', title: '班级编号'}
            , {field: 'clazzYear', title: '年级'}
            , {field: 'clazzName', title: '班级名称'}
            , {field: 'clazzDesc', title: '班级描述'}
        ]]

    });
    //执行一个laydate实例
    laydate.render({
        elem: '#L_clazzYear' //指定元素
        ,type: 'year'
    });

    //监听头工具栏
    table.on('toolbar(clazz)', function (obj) {
        switch(obj.event){
            case 'add':
                addClazz=layer.open({
                    type: 1,
                    content: $("#addClazz"), //这里content是一个普通的String
                    area: ['500px', '500px']
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
            url:"/clazz/addClazz",
            dataType:"json",
            success:function (result) {
                if(result.code == "0000"){
                    if (result.data){
                        layer.msg("添加成功");
                        //关闭当前frame
                        layer.close(addClazz);
                        table.reload('clazz_datagrid');
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

    // 渲染院系下拉框数据
    $.ajax({
        data:{},
        type:"get",
        url:"/department/allDepartment",
        dataType:"json",
        success:function (result) {
            if (result.code == "0000") {
                if (result.data.length!=0){
                    if (result.data.length!=0){
                        $.each(result.data,function(index,item){
                            $("#L_deptNo").append("<option value='" + item.deptNo + "'>" + item.deptName + "</option>");
                        });
                    }
                    form.render();
                }
                form.render();
            }
        }
    });

    // 监听select选择事件
    form.on('select(deptNoSelect)', function(data){
        var deptNo=data.value;
        $.ajax({
            type : "GET",
            url : "/specialty/specialtyByDeptNo/"+deptNo,
            success:function (result) {
                $("#L_specialtyNo").empty();
                if (result.code == "0000") {
                    if (result.data.length!=0){
                        $.each(result.data,function(index,item){
                            $("#L_specialtyNo").append("<option value='" + item.specialtyNo + "'>" + item.specialtyName + "</option>");
                        });
                    }
                    form.render();
                }
            },error:function(){
                alert("获取数据失败","error");
            }
        });
    });

});
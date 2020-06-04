layui.use(['form', 'layer','jquery','table'], function () {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
        layer = layui.layer;
    //第一个实例
    table.render({
        elem: '#specialty_datagrid'
        , url: '/specialty/specialtyList' //数据接口
        , toolbar:'#toolbar'
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'department.deptName', title: '院系名称'
                ,templet: function(d){
                    return d.department.deptName;
                }
            }
            ,{field: 'specialtyNo', title: '专业编号'}
            , {field: 'specialtyName', title: '专业名称'}
            , {field: 'specialtyDesc', title: '专业描述'}
        ]]
    });

    //监听头工具栏
    table.on('toolbar(specialty)', function (obj) {
        switch(obj.event){
            case 'add':
                addSpecialty=layer.open({
                    type: 1,
                    content: $("#addSpecialty"), //这里content是一个普通的String
                    area: ['500px', '500px']
                });
                break;
        }
    });
    // 渲染下拉框数据
    $.ajax({
        data:{},
        type:"get",
        url:"/department/allDepartment",
        dataType:"json",
        success:function (result) {
            if (result.code == "0000"){
                if (result.data.length!=0){
                    $.each(result.data,function(index,item){
                        $("#L_deptNo").append("<option value='" + item.deptNo + "'>" + item.deptName + "</option>");
                    });
                }
                form.render();
            }
        }
    });

    //监听提交
    form.on('submit(add)', function(data) {
        $.ajax({
            data:data.field,
            type:"post",
            url:"/specialty/addSpecialty",
            dataType:"json",
            success:function (result) {
                if(result.data){
                    layer.msg("添加成功");
                    //关闭当前frame
                    layer.close(addSpecialty);
                    table.reload('specialty_datagrid');
                }else {
                    layer.msg(result.msg);
                }
            }
        });
        return false;
    });

});
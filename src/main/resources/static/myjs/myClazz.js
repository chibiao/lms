layui.use(['form', 'layer','jquery','table'], function () {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
    layer = layui.layer;
    //第一个实例
    table.render({
        elem: '#myClazz_datagrid'
        , url: '/teacher/selectMyClazz' //数据接口
        , toolbar:'#toolbar'
        , cols: [[ //表头
            {field: 'clazzNo', title: '班级编号'}
            , {field: 'clazzName', title: '班级名称'}
            , {field: 'clazzDesc', title: '班级描述'}
        ]]

    });

    //监听头工具栏
    table.on('toolbar(myClazz)', function (obj) {
        switch(obj.event){
            case 'add':
                addMyClazz=layer.open({
                    type: 1,
                    content: $("#addMyClazz"), //这里content是一个普通的String
                    area: ['500px', '300px']
                });
                break;
        }
    });
    //监听提交
    form.on('submit(add)', function(data) {
        $.ajax({
            data:data.field,
            type:"post",
            url:"/teacher/addMyClazz",
            dataType:"json",
            success:function (result) {
                if(result.code == "0000"){
                    if (result.data){
                        layer.msg("添加成功");
                        //关闭当前frame
                        layer.close(addMyClazz);
                        table.reload('myClazz_datagrid');
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
        url:"/teacher/specialtyByTeacherNo",
        dataType:"json",
        success:function (result) {
            if (result.code == "0000") {
                if (result.data.length!=0){
                    if (result.data.length!=0){
                        $("#L_specialtyNo").append("<option value=''></option>");
                        $.each(result.data,function(index,item){
                            $("#L_specialtyNo").append("<option value='" + item.specialtyNo + "'>" + item.specialtyName + "</option>");
                        });
                    }
                    form.render();
                }
                form.render();
            }
        }
    });

    // 监听专业变化
    form.on('select(specialtyNoSelect)', function(data){
        if (data.value == ""){
            $("#L_clazzNo").empty();
            $("#L_clazzNo").append("<option value=''></option>");
            form.render()
        }else {
            var specialtyNo=data.value;
            $.ajax({
                type : "GET",
                url : "/clazz/clazzBySpecialtyNo/"+specialtyNo,
                success:function (result) {
                    $("#L_clazzNo").empty();
                    $("#L_clazzNo").append("<option value=''></option>");
                    if (result.code == "0000") {
                        if (result.data.length!=0){
                            $.each(result.data,function(index,item){
                                $("#L_clazzNo").append("<option value='" + item.clazzNo + "'>" + item.clazzName + "</option>");
                            });
                        }
                        form.render();
                    }
                },error:function(){
                    alert("获取数据失败","error");
                }
            });
        }
    });
});
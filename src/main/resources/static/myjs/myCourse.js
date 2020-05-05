layui.use(['form', 'layer','jquery','table'], function () {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
    layer = layui.layer;
    //第一个实例
    table.render({
        elem: '#myCourse_datagrid'
        , url: '/teacher/selectMyCourse' //数据接口
        , toolbar:'#toolbar'
        , cols: [[ //表头
            {field: 'courseNo', title: '课程编号'}
            , {field: 'courseName', title: '课程名称'}
            , {field: 'courseDesc', title: '课程描述'}
        ]]

    });

    //监听头工具栏
    table.on('toolbar(myCourse)', function (obj) {
        switch(obj.event){
            case 'add':
                addMyCourse=layer.open({
                    type: 1,
                    content: $("#addMyCourse"), //这里content是一个普通的String
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
            url:"/teacher/addMyCourse",
            dataType:"json",
            success:function (result) {
                if(result.code == "0000"){
                    if (result.data){
                        layer.msg("添加成功");
                        //关闭当前frame
                        layer.close(addMyCourse);
                        table.reload('myCourse_datagrid');
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
        url:"/teacher/selectCourseByTeacherDept",
        dataType:"json",
        success:function (result) {
            if (result.code == "0000") {
                if (result.data.length!=0){
                    if (result.data.length!=0){
                        $.each(result.data,function(index,item){
                            $("#L_courseName").append("<option value=''></option>");
                            $("#L_courseName").append("<option value='" + item.courseNo + "'>" + item.courseName + "</option>");
                        });
                    }
                    form.render();
                }
                form.render();
            }
        }
    });

    // 监听select选择事件
    form.on('select(courseSelect)', function(data){
        console.log("aaa");
        if (data.value == ""){
            $('#L_courseNo').val('');
            form.render();
        }else {
            $('#L_courseNo').val(data.value);
            form.render();
        }
    });

});
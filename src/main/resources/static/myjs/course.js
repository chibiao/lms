layui.use(['form', 'layer','jquery','table'], function () {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
    layer = layui.layer;
    //第一个实例
    table.render({
        elem: '#course_datagrid'
        , url: '/course/courseList' //数据接口
        , toolbar:'#toolbar'
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'courseNo', title: '课程编号'}
            , {field: 'courseName', title: '课程名称'}
            , {field: 'courseDesc', title: '课程描述'}
            , {field: 'department.deptName', title: '所属院系'
                ,templet: function(d){
                    return d.department.deptName;
                }}
        ]]

    });

    //监听头工具栏
    table.on('toolbar(course)', function (obj) {
        switch(obj.event){
            case 'add':
                addCourse=layer.open({
                    type: 1,
                    content: $("#addCourse"), //这里content是一个普通的String
                    area: ['500px', '500px']
                });
                break;
        }
    });

    //监听提交
    form.on('submit(add)', function(data) {
        $.ajax({
            data:data.field,
            type:"post",
            url:"/course/addCourse",
            dataType:"json",
            success:function (result) {
                if(result.code == "0000"){
                    if (result.data){
                        layer.msg("添加成功");
                        //关闭当前frame
                        layer.close(addCourse);
                        table.reload('course_datagrid');
                        $("#addCourse")[0].reset();
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

});
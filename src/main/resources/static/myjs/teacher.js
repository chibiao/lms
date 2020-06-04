layui.use(['form', 'layer','jquery','table','laydate'], function () {
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;
    var form = layui.form,
    layer = layui.layer;
    //第一个实例
    table.render({
        elem: '#teacher_datagrid'
        , url: '/teacher/teacherList' //数据接口
        , toolbar:'#toolbar'
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'teacherNo', title: '教师编号'}
            , {field: 'teacherName', title: '教师名称'}
            , {field: 'teacherEmail', title: '邮箱'}
            , {field: 'teacherPhone', title: '电话'}
            , {field: 'teacherSex', title: '性别',templet: '#sexSwitch'}
            ,{field: 'teacherType', title: '教师类型'
                ,templet: function(d){
                    if (d.teacherType == 0){
                        return "教师";
                    } else if (d.teacherType == 1){
                        return "辅导员"
                    }
                }
            }
            , {field: 'department.deptName', title: '所属院系'
                ,templet: function(d){
                    return d.department.deptName;
                }
            }
            , {fixed: 'right', title: '操作',toolbar: '#bar',width:220}
        ]]

    });

    //监听头工具栏
    table.on('toolbar(teacher)', function (obj) {
        switch(obj.event){
            case 'add':
                $("#add").css("display","block");
                $("#update").css("display","none");
                $("#L_teacherNo").attr("disabled",false);
                $("#L_teacherName").attr("disabled",false);
                addTeacher=layer.open({
                    type: 1,
                    content: $("#addTeacher"), //这里content是一个普通的String
                    area: ['600px', '500px']
                });
                $('#addTeacher')[0].reset();
                form.render();
                break;
        }
    });
    //监听行工具事件
    table.on('tool(teacher)', function (obj) {
        //获取每行的数据
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                var teacherNo=obj.data.teacherNo;
                $.ajax({
                    data:{
                        _method:'DELETE'
                    },
                    type:"post",
                    url:"/teacher/deleteTeacher/"+teacherNo,
                    dataType:"json",
                    success:function (result) {
                        if(result.data){
                            layer.msg("删除成功");
                            table.reload('teacher_datagrid'); //只重载数据
                        }else{
                            layer.msg(result.message);
                        }
                    }
                });
            });
        }else if (obj.event === 'update') {
            updateTeacher=layer.open({
                type: 1,
                content: $("#addTeacher"), //这里content是一个普通的String
                area: ['600px', '500px']
            });
            $("#L_teacherNo").attr("disabled",true);
            $("#L_teacherName").attr("disabled",true);
            $("#add").css("display","none");
            $("#update").css("display","block");
            form.val('addTeacher', {
                "teacherNo": data.teacherNo,
                "teacherName": data.teacherName,
                "teacherEmail":data.teacherEmail,
                "teacherPhone":data.teacherPhone,
                "teacherSex":data.teacherSex,
                "teacherType":data.teacherType,
                "department.deptNo":data.department.deptNo
            });
            form.render();
        }
    });

    //点击搜索条件
    $('#search').on('click', function () {
        // 搜索条件
        var teacherNo = $('#teacherNo').val();
        var teacherName = $('#teacherName').val();
        var teacherType = $('#teacherType').val();
        table.reload('teacher_datagrid', {
            method: 'get'
            , where: {
                'teacherNo': teacherNo,
                'teacherName': teacherName,
                'teacherType': teacherType
            }
            , page: {
                curr: 1
            }
        });
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
            url:"/teacher/addTeacher",
            dataType:"json",
            success:function (result) {
                if(result.code == "0000"){
                    if (result.data){
                        layer.msg("添加成功");
                        //关闭当前frame
                        layer.close(addTeacher);
                        table.reload('teacher_datagrid');
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

    //监听提交
    form.on('submit(update)', function(data) {
        $.ajax({
            data:data.field,
            type:"put",
            url:"/teacher/updateTeacher",
            dataType:"json",
            success:function (result) {
                if(result.code == "0000"){
                    if (result.data){
                        layer.msg("修改成功");
                        //关闭当前frame
                        layer.close(updateTeacher);
                        table.reload('teacher_datagrid');
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

    // 校验规则
    form.verify({
        numberMsg: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(value!=""){  //值不是空的时候再去走验证
                if(!/(^$)|^\d+$/.test(value)){
                    return '只能填写数字';
                }
            }
        }
    });
});
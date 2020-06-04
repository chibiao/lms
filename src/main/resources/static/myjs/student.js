layui.use(['form', 'layer','jquery','table','laydate','upload'], function () {
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;
    var upload = layui.upload;
    var form = layui.form,
    layer = layui.layer;
    //第一个实例
    table.render({
        elem: '#student_datagrid'
        , url: '/student/studentList' //数据接口
        , toolbar:'#toolbar'
        , page: true //开启分页
        , cols: [[ //表头
           {field: 'studentId', title: '学号'}
            , {field: 'studentName', title: '姓名'}
            , {field: 'studentPhone', title: '手机号'}
            , {field: 'studentEmail', title: '邮箱'}
            , {field: 'studentSex', title: '性别',templet: '#sexSwitch'}
            , {field: 'studentBirthday', title: '生日'
                ,templet : "<div>{{layui.util.toDateString(d.studentBirthday, 'yyyy-MM-dd')}}</div>"}
            , {field: 'studentAge', title: '年龄'}
            , {fixed: 'right', title: '操作',toolbar: '#bar',width:220}
        ]]

    });
    //执行一个laydate实例
    laydate.render({
        elem: '#L_studentBirthday' //指定元素
        ,trigger: 'click'//呼出事件改成click
    });

    //监听头工具栏
    table.on('toolbar(student)', function (obj) {
        switch(obj.event){
            case 'add':
                addStudent=layer.open({
                    type: 1,
                    content: $("#addStudent"), //这里content是一个普通的String
                    area: ['1000px', '500px']
                });
                $("#add").css("display","block");
                $("#update").css("display","none");
                $("#L_specialtyNo").empty();
                $("#L_clazzNo").empty();
                $("#L_specialtyNo").append("<option value=''></option>");
                $("#L_clazzNo").append("<option value=''></option>");
                $("#L_studentId").removeAttr("disabled");
                $('#addStudent')[0].reset();
                form.render();
                break;
            case 'downloadTml':
                addStudent=layer.open({
                    type: 1,
                    content: $("#downloadStudentTml"), //这里content是一个普通的String
                    area: ['400px', '300px']
                });
                // window.open("/student/downloadExcelTml");
        }
    });

    //点击搜索条件
    $('#search').on('click', function () {
        // 搜索条件
        var studentId = $('#studentId').val();
        var studentName = $('#studentName').val();
        var deptNo = $('#deptNo').val();
        var specialtyNo = $('#specialtyNo').val();
        var clazzNo = $('#clazzNo').val();
        table.reload('student_datagrid', {
            method: 'get'
            , where: {
                'studentId': studentId,
                'studentName': studentName,
                'department.deptNo': deptNo,
                'specialty.specialtyNo': specialtyNo,
                'clazz.clazzNo': clazzNo
            }
            , page: {
                curr: 1
            }
        });
    });

    //监听行工具事件
    table.on('tool(student)', function (obj) {
        //获取每行的数据
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                var studentId=obj.data.studentId;
                $.ajax({
                    data:{
                        _method:'DELETE'
                    },
                    type:"post",
                    url:"/student/deleteStudent/"+studentId,
                    dataType:"json",
                    success:function (result) {
                        if(result.data){
                            layer.msg("删除成功");
                            table.reload('student_datagrid'); //只重载数据
                        }else{
                            layer.msg(result.message);
                        }
                    }
                });
            });
        }
        if (obj.event === 'reset') {
            layer.confirm('是否要重置该学生的登录密码?', function (index) {
                var studentId=obj.data.studentId;
                $.ajax({
                    data:{
                        _method:'PUT'
                    },
                    type:"post",
                    url:"/student/resetStudentPassword/"+studentId,
                    dataType:"json",
                    success:function (result) {
                        if(result.data){
                            layer.msg("重置成功");
                            table.reload('student_datagrid'); //只重载数据
                        }else{
                            layer.msg(result.message);
                        }
                    }
                });
            });
        }
        if (obj.event === 'update') {
            updateStudent=layer.open({
                type: 1,
                content: $("#addStudent"), //这里content是一个普通的String
                area: ['1000px', '500px']
            });
            $("#add").css("display","none");
            $("#update").css("display","block");
            $("#L_studentId").attr("disabled",true);
            $.ajax({
                type : "GET",
                url : "/specialty/specialtyByDeptNo/"+data.department.deptNo,
                success:function (result) {
                    // 删除子元素
                    $("#L_specialtyNo").empty();
                    if (result.code == "0000") {
                        if (result.data.length!=0){
                            $.each(result.data,function(index,item){
                                $("#L_specialtyNo").append("<option value='" + item.specialtyNo + "'>" + item.specialtyName + "</option>");
                            });
                            $.ajax({
                                type : "GET",
                                url : "/clazz/clazzBySpecialtyNo/"+data.specialty.specialtyNo,
                                success:function (result) {
                                    $("#L_clazzNo").empty();
                                    if (result.code == "0000") {
                                        if (result.data.length!=0){
                                            $.each(result.data,function(index,item){
                                                $("#L_clazzNo").append("<option value='" + item.clazzNo + "'>" + item.clazzName + "</option>");
                                            });
                                        }
                                        form.render();
                                        form.val('addStudent', {
                                            "studentId": data.studentId,
                                            "studentName": data.studentName,
                                            "studentEmail":data.studentEmail,
                                            "studentPhone":data.studentPhone,
                                            "studentSex":data.studentSex,
                                            "studentAge":data.studentAge,
                                            "studentBirthday":data.studentBirthday,
                                            "department.deptNo":data.department.deptNo,
                                            "specialty.specialtyNo":data.specialty.specialtyNo,
                                            "clazz.clazzNo":data.clazz.clazzNo
                                        });
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }

    });

    //监听提交
    form.on('submit(add)', function(data) {
        $.ajax({
            data:data.field,
            type:"post",
            url:"/student/addStudent",
            dataType:"json",
            success:function (result) {
                if(result.code == "0000"){
                    if (result.data){
                        layer.msg("添加成功");
                        //关闭当前frame
                        layer.close(addStudent);
                        table.reload('student_datagrid');
                        $("#addStudent")[0].reset();
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
            url:"/student/updateStudent",
            dataType:"json",
            success:function (result) {
                if(result.code == "0000"){
                    if (result.data){
                        layer.msg("修改成功");
                        //关闭当前frame
                        layer.close(updateStudent);
                        table.reload('student_datagrid');
                        $("#addStudent")[0].reset();
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
                            $("#T_deptNo").append("<option value='" + item.deptNo + "'>" + item.deptName + "</option>");
                            $("#deptNo").append("<option value='" + item.deptNo + "'>" + item.deptName + "</option>");
                        });
                    }
                    form.render();
                }
                form.render();
            }
        }
    });

    //监听提交
    form.on('submit(downloadTml)', function(data) {
        window.open("/student/downloadExcelTml?deptNo="+data.field.deptNo+"&specialtyNo="+data.field.specialtyNo+"&clazzNo="+data.field.clazzNo);
    });
    // 上传学生excel
    upload.render({ //允许上传的文件后缀
        elem: '#uploadTml'
        ,url: '/student/uploadExcelTml' //改成您自己的上传接口
        ,accept: 'file' //普通文件
        ,exts: 'xlsx|xls' //只允许上传excel
        ,done: function(res){
            layer.msg('上传成功');
            table.reload('student_datagrid');
        }
    });

    // 监听select选择事件
    form.on('select(deptNoSelect)', function(data){
        if (data.value == ""){
            // 删除子元素
            $("#L_specialtyNo").empty();
            $("#L_clazzNo").empty();
            $("#L_specialtyNo").append("<option value=''></option>");
            $("#L_clazzNo").append("<option value=''></option>");
            form.render()
        }else {
            var deptNo=data.value;
            $.ajax({
                type : "GET",
                url : "/specialty/specialtyByDeptNo/"+deptNo,
                success:function (result) {
                    // 删除子元素
                    $("#L_specialtyNo").empty();
                    $("#L_specialtyNo").append("<option value=''></option>");
                    $("#L_clazzNo").empty();
                    $("#L_clazzNo").append("<option value=''></option>");
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
        }
    });

    // 监听select选择事件
    form.on('select(deptNoFormSelect)', function(data){
        if (data.value == ""){
            // 删除子元素
            $("#specialtyNo").empty();
            $("#clazzNo").empty();
            $("#specialtyNo").append("<option value=''>请选择专业</option>");
            $("#clazzNo").append("<option value=''>请选择班级</option>");
            form.render()
        }else {
            var deptNo=data.value;
            $.ajax({
                type : "GET",
                url : "/specialty/specialtyByDeptNo/"+deptNo,
                success:function (result) {
                    // 删除子元素
                    $("#specialtyNo").empty();
                    $("#specialtyNo").append("<option value=''>请选择专业</option>");
                    $("#clazzNo").empty();
                    $("#clazzNo").append("<option value=''>请选择班级</option>");
                    if (result.code == "0000") {
                        if (result.data.length!=0){
                            $.each(result.data,function(index,item){
                                $("#specialtyNo").append("<option value='" + item.specialtyNo + "'>" + item.specialtyName + "</option>");
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

    // 监听专业变化
    form.on('select(specialtyNoFormSelect)', function(data){
        if (data.value == ""){
            $("#clazzNo").empty();
            $("#clazzNo").append("<option value=''>请选择班级</option>");
            form.render()
        }else {
            var specialtyNo=data.value;
            $.ajax({
                type : "GET",
                url : "/clazz/clazzBySpecialtyNo/"+specialtyNo,
                success:function (result) {
                    $("#clazzNo").empty();
                    $("#clazzNo").append("<option value=''>请选择班级</option>");
                    if (result.code == "0000") {
                        if (result.data.length!=0){
                            $.each(result.data,function(index,item){
                                $("#clazzNo").append("<option value='" + item.clazzNo + "'>" + item.clazzName + "</option>");
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


    // 下载模版
    // 监听select选择事件
    form.on('select(deptNoTmlSelect)', function(data){
        if (data.value == ""){
            // 删除子元素
            $("#T_specialtyNo").empty();
            $("#T_clazzNo").empty();
            $("#T_specialtyNo").append("<option value=''>请选择专业</option>");
            $("#T_clazzNo").append("<option value=''>请选择班级</option>");
            form.render()
        }else {
            var deptNo=data.value;
            $.ajax({
                type : "GET",
                url : "/specialty/specialtyByDeptNo/"+deptNo,
                success:function (result) {
                    // 删除子元素
                    $("#T_specialtyNo").empty();
                    $("#T_clazzNo").empty();
                    $("#T_specialtyNo").append("<option value=''>请选择专业</option>");
                    $("#T_clazzNo").append("<option value=''>请选择班级</option>");
                    if (result.code == "0000") {
                        if (result.data.length!=0){
                            $.each(result.data,function(index,item){
                                $("#T_specialtyNo").append("<option value='" + item.specialtyNo + "'>" + item.specialtyName + "</option>");
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

    // 监听专业变化
    form.on('select(specialtyNoTmlSelect)', function(data){
        if (data.value == ""){
            $("#T_clazzNo").empty();
            $("#T_clazzNo").append("<option value=''></option>");
            form.render()
        }else {
            var specialtyNo=data.value;
            $.ajax({
                type : "GET",
                url : "/clazz/clazzBySpecialtyNo/"+specialtyNo,
                success:function (result) {
                    $("#T_clazzNo").empty();
                    $("#T_clazzNo").append("<option value=''>请选择班级</option>");
                    if (result.code == "0000") {
                        if (result.data.length!=0){
                            $.each(result.data,function(index,item){
                                $("#T_clazzNo").append("<option value='" + item.clazzNo + "'>" + item.clazzName + "</option>");
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
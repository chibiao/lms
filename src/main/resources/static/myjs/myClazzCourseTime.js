layui.use(['form', 'layer','jquery','table','laydate'], function () {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
    layer = layui.layer;
    var laydate = layui.laydate;
    var courseBeginTime = '';
    var courseEndTime = '';
    var courseBeginDay = '';
    var courseEndDay = '';
    //执行一个laydate实例
    laydate.render({
        elem: '#L_courseTime' //指定元素
        ,type: 'time'
        ,range: '~'
        ,format:'HH:mm:ss'
        ,trigger: 'click'//呼出事件改成click 解决闪退问题
        ,done: function(value, date, endDate){
            var values;
            values = value.split("~");
            courseBeginTime = values[0];
            courseEndTime = values[1];
        }
    });
    //执行一个laydate实例
    laydate.render({
        elem: '#L_courseDay' //指定元素
        ,type: 'date'
        ,range: '~'
        ,format:'yyyy-MM-dd'
        ,trigger: 'click'//呼出事件改成click 解决闪退问题
        ,done: function(value, date, endDate){
            var values;
            values = value.split("~");
            courseBeginDay = values[0];
            courseEndDay = values[1];
        }
    });
    //第一个实例
    table.render({
        elem: '#myClazzCourseTime_datagrid'
        , url: '/teacher/selectClazzCourseTimeByTeacherNo' //数据接口
        , toolbar:'#toolbar'
        , cols: [[ //表头
            {field: 'clazz.clazzName', title: '班级名称'
                ,templet: function(d){
                    return d.clazz.clazzName;
                }
            }
            , {field: 'course.courseName', title: '课程名称'
                ,templet: function(d){
                    return d.course.courseName;
                }
            }
            , {field: 'courseBeginDay', title: '上课开始日期'}
            , {field: 'courseEndDay', title: '上课结束日期'}
            , {field: 'courseBeginTime', title: '上课开始时间'}
            , {field: 'courseEndTime', title: '上课结束时间'}
            , {field: 'courseWeek', title: '上课周'
                ,templet: function(d){
                    if (d.courseWeek  == 0){
                        return '星期日';
                    }
                    if (d.courseWeek  == 1){
                        return '星期一';
                    }
                    if (d.courseWeek  == 2){
                        return '星期二';
                    }
                    if (d.courseWeek  == 3){
                        return '星期三';
                    }
                    if (d.courseWeek  == 4){
                        return '星期四';
                    }
                    if (d.courseWeek  == 5){
                        return '星期五';
                    }
                    if (d.courseWeek  == 6){
                        return '星期六';
                    }
                }
            }
        ]]

    });

    //监听头工具栏
    table.on('toolbar(myClazzCourseTime)', function (obj) {
        switch(obj.event){
            case 'add':
                addMyClazzCourseTime=layer.open({
                    type: 1,
                    content: $("#addMyClazzCourseTime"), //这里content是一个普通的String
                    area: ['500px', '500px']
                });
                break;
        }
    });
    //监听提交
    form.on('submit(add)', function(data) {
        $.ajax({
            data:{
                'clazz.clazzNo':data.field.clazzNo,
                'course.courseNo':data.field.courseNo,
                'courseBeginTime': courseBeginTime,
                'courseEndTime':courseEndTime,
                'courseWeek':data.field.courseWeek,
                'courseBeginDay':courseBeginDay,
                'courseEndDay':courseEndDay
            },
            type:"post",
            url:"/teacher/addClazzCourseTime",
            dataType:"json",
            success:function (result) {
                if(result.code == "0000"){
                    if (result.data){
                        layer.msg("添加成功");
                        //关闭当前frame
                        layer.close(addMyClazzCourseTime);
                        table.reload('myClazzCourseTime_datagrid');
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

    // 渲染院系下拉框数据 查询教师教授的班级
    $.ajax({
        data:{},
        type:"get",
        url:"/teacher/selectMyClazz",
        dataType:"json",
        success:function (result) {
            if (result.code == 0) {
                if (result.data.length!=0){
                    $("#L_clazzNo").append("<option value=''></option>");
                    $.each(result.data,function(index,item){
                        $("#L_clazzNo").append("<option value='" + item.clazzNo + "'>" + item.clazzName + "</option>");
                    });
                    form.render();
                }
                form.render();
            }
        }
    });

    // 渲染院系下拉框数据 查询教师教授的班级
    $.ajax({
        data:{},
        type:"get",
        url:"/teacher/selectMyCourse",
        dataType:"json",
        success:function (result) {
            if (result.code == 0) {
                if (result.data.length!=0){
                    $("#L_courseNo").append("<option value=''></option>");
                    $.each(result.data,function(index,item){
                        $("#L_courseNo").append("<option value='" + item.courseNo + "'>" + item.courseName + "</option>");
                    });
                    form.render();
                }
                form.render();
            }
        }
    });
});
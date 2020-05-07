layui.use(['form', 'layer','jquery','table','laydate'], function () {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
    layer = layui.layer;
    var laydate = layui.laydate;
    var courseBeginDay = '';
    var courseEndDay = '';
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
    //执行一个laydate实例
    laydate.render({
        elem: '#L_searchDay' //指定元素
        ,type: 'date'
        ,format:'yyyy-MM-dd'
        ,trigger: 'click'//呼出事件改成click 解决闪退问题
    });
    //第一个实例
    table.render({
        elem: '#clazzCourseTime_datagrid'
        , url: '/student/selectClazzCourseTimeByClazzNo' //数据接口
        , page: true
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
            , {field: 'teacher.teacherName', title: '任课老师'
                ,templet: function(d){
                    return d.teacher.teacherName;
                }
            }
        ]]
    });
    //点击搜索条件
    $('#search').on('click', function () {
        // 搜索条件
        var courseWeek = $('#L_courseWeek').val();
        var searchDay = $('#L_searchDay').val();
        table.reload('clazzCourseTime_datagrid', {
            method: 'get'
            , where: {
                'courseWeek': courseWeek,
                'searchDay': searchDay,
                'courseBeginDay':courseBeginDay,
                'courseEndDay':courseEndDay
            }
            , page: {
                curr: 1
            }
        });
    });
});
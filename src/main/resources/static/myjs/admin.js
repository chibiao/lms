layui.use(['form', 'layer','jquery','table'], function () {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
    layer = layui.layer;
    //第一个实例
    table.render({
        elem: '#admin_datagrid'
        , url: '/admin/adminList' //数据接口
        , toolbar:'#toolbar'
        , page: true //开启分页
        , cols: [[ //表头
             {field: 'adminAccount', title: '管理员账号'}
            , {field: 'adminPassword', title: '管理员密码'}
        ]]

    });

    //监听头工具栏
    table.on('toolbar(admin)', function (obj) {
        switch(obj.event){
            case 'add':
                addAdmin=layer.open({
                    type: 1,
                    content: $("#addAdmin"), //这里content是一个普通的String
                    area: ['350px', '200px']
                });
                break;
        }
    });

    //监听提交
    form.on('submit(add)', function(data) {
        $.ajax({
            data:data.field,
            type:"post",
            url:"/admin/addAdmin",
            dataType:"json",
            success:function (result) {
                if(result.code == "0000"){
                    if (result.data){
                        layer.msg("添加成功");
                        //关闭当前frame
                        layer.close(addAdmin);
                        table.reload('admin_datagrid');
                        $("#addAdmin")[0].reset();
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
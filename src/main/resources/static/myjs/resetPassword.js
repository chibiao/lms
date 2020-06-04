layui.use(['form', 'layer','jquery','table'], function () {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
    layer = layui.layer;
    //监听提交
    form.on('submit(add)', function(data) {
        console.log("aaa");
        var oldPassword = data.field.oldPassword;
        var newPassword = data.field.newPassword;
        var newPassword2 = data.field.newPassword2;
        if (newPassword != newPassword2){
            layer.msg("两次输入的密码不一致");
            return;
        }
        $.ajax({
            data:data.field,
            type:"post",
            url:"/updatePassword",
            dataType:"json",
            success:function (result) {
                if(result.code == "0000"){
                    if (result.data){
                        layer.msg("修改成功");
                        //关闭当前frame
                        location.href="/logout";
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

    //自定义验证规则
    form.verify({
       pass: [
            /^[\S]{6,12}$/
            ,'密码必须6到12位，且不能出现空格'
        ]
    });

});
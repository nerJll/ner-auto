<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>租户表编辑--${site.name}</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="description" content="${site.description}"/>
    <meta name="keywords" content="${site.keywords}"/>
    <meta name="author" content="${site.author}"/>
    <link rel="icon" href="${site.logo}">
    <link rel="stylesheet" href="${base}/static/layui/css/modules/laydate/default/laydate.css" media="all" />
    <link rel="stylesheet" href="${base}/static/layui/css/layui.css" media="all" />
    <style type="text/css">
        .layui-form-item .layui-inline{ width:33.333%; float:left; margin-right:0; }
        @media(max-width:1240px){
            .layui-form-item .layui-inline{ width:100%; float:none; }
        }
        .layui-form-item .role-box {
            position: relative;
        }
        .layui-form-item .role-box .jq-role-inline {
            height: 100%;
            overflow: auto;
        }

    </style>
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
    <input value="${baseTenant.id}" name="id" type="hidden">
    <div class="layui-form-item">
        <label class="layui-form-label">名称</label>
        <div class="layui-input-block">
                <input  type="text" class="layui-input" value = "${baseTenant.name}" name="name" lay-verify="required" placeholder="请输入名称">

        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">到期时间</label>
        <div class="layui-input-block">
                <input type="text" name="limitDate" id="limitDate" <#if (baseTenant.limitDate)??>value="${baseTenant.limitDate?string('yyyy-MM-dd')}"</#if>  lay-verify="date" placeholder="请选择到期时间" autocomplete="off" class="layui-input">

        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">管理员</label>
        <div class="layui-input-block">
            <select name="managerId" lay-search="">
                <option value="">请选择一个管理员</option>
                <#list ms as m>
                    <option <#if m.id == baseTenant.managerId>selected</#if>
                            value="${m.id}">${m.nickName}</option>
                </#list>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="addBaseTenant">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/static/layui/lay/modules/laydate.js"></script>
<script type="text/javascript" src="${base}/static/layui/layui.js"></script>
<script>
    layui.use(['form','jquery','layer','laydate'],function(){
        var form      = layui.form,
                $     = layui.jquery,
                layer = layui.layer;
                          //初始赋值
                          laydate.render({
                            elem: '#limitDate'
<#if (baseTenant.limitDate)??>
                            ,value: '${baseTenant.limitDate?string("yyyy-MM-dd")}'
</#if>
                          });


        form.on("submit(addBaseTenant)",function(data){
                               if(null === data.field.limitDate || "" ===data.field.limitDate){
                                delete data.field["limitDate"];
                            }else{
                                data.field.limitDate = new Date(data.field.limitDate);
                            }
            var loadIndex = layer.load(2, {
                shade: [0.3, '#333']
            });
            //给角色赋值
            $.post("${base}/admin/baseTenant/edit",data.field,function(res){
                layer.close(loadIndex);
                if(res.success){
                    parent.layer.msg("租户表编辑成功！",{time:1000},function(){
                        parent.layer.close(parent.editIndex);
                        //刷新父页面
                        parent.location.reload();
                    });
                }else{
                    layer.msg(res.message);
                }
            });
            return false;
        });

    });
</script>
</body>
</html>
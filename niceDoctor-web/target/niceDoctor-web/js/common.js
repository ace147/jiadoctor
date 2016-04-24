$(function(){
 
    var navContainer = $("#navContainer");

    mccmsa.getHtmlFromRemoteTemplate({
        templateName: "nav.hbs",
        container: navContainer,
        callback: function(){
            $.Huifold("#Huifold .item h4","#Huifold .item .info","fast",1,"click");
        }
    });

    // 点击二级菜单加载内容
    navContainer.on('click', '.ajaxLoad', function (event) {
        var self = $(this);

        // 加载内容
        var rightSrc = self.attr("href");
        if (rightSrc != '#') {
            mccmsa.getHtmlFromRemoteTemplate({
                templateName: rightSrc + ".hbs",
                container: $("#mainContainer")
            });
        } else {
            alert("略略略略略...");
        }

        // 后添加选中样式
        $(".arrow-r").remove();
        $("li", ".info").removeClass("current");
        self.parent().addClass("current").append('<i class="arrow-r"></i>');

        // 移动端（宽度小于767px）响应式布局时，选择后收起菜单
        if($(window).width()<768){
            $(".Hui-aside").slideToggle();
        }

        return false;
    });

    // 移动端（宽度小于767px）响应式布局头部菜单按钮
    $(".Hui-nav-toggle").click(function(){
        $(".Hui-aside").slideToggle();
    });

    var resizeID;
    /*左侧菜单响应式*/
    $(window).resize(function(){
        clearTimeout(resizeID);
        resizeID = setTimeout(function(){
            if($(window).width()>=768){
                $(".Hui-aside").show()
            }
        }, 300);
    });
});

// 公共方法，定义在mccmsa命名空间下
window.mccmsa = {
    ajax: function(options) {
        var defaultOptions = {
            url: "",
            method: "POST",
            data: {},
            dataType: "json"
        };
 
        var options = $.extend(defaultOptions, options);

        var loadingLayerIndex;
        return $.ajax({
            // TODO url需按实际情况修改路径
            url: "" + options.url,
            method: options.method,
            data: options.data,
            dataType: options.dataType,
            beforeSend: function(){
                loadingLayerIndex = layer.load({
                    shade: [0.8, '#393D49']
                });
            },
            success: function(data, xhr){
                if(data.status == 200 || data.status == 300){
                    if(options.success && $.isFunction(options.success)){
                        options.success(data);
                    }
                }else {
					alert(data.message)
                    // TODO 业务错误处理...
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log(textStatus + "[" + jqXHR.status + "]: " + errorThrown);

                // TODO 请求错误处理...
            },
            complete: function() {
                layer.close(loadingLayerIndex);
            }
        });
    },

    /*
     * 编译服务器端请求的模板并生成Html
     *
     * opt: {
     *   templateName: 模板文件名
     *   container: 模板容器
     *   callback: 模板添加成功后回调
     * }
     * */
    getHtmlFromRemoteTemplate: function(opt){
        var templatePath = "templates/";

        $.ajax({
            url: templatePath + opt.templateName,
            method: "GET",
            success: function(tmpl){
                var template = Handlebars.compile(tmpl);
                opt.container && opt.container.html(template(opt.data || {}));

                if(opt.callback && $.isFunction(opt.callback)){
                    opt.callback();
                }
            },
            error: function(jqXHR, textStatus, errorThrown){
                console.log(textStatus + "[" + jqXHR.status + "]: " + errorThrown);

                // TODO 请求错误处理...
            }
        });
    },

    /*
     * 编译页面上的模板并生成Html
     *
     * opt: {
     *   templateId: 模板Id
     *   container: 模板容器
     *   callback: 模板添加成功后回调
     * }
     * */
    getHtmlFromLocalTemplate: function(opt){
        if(opt.templateId && $("#" + opt.templateId).length > 0){

            var template = Handlebars.compile($("#" + opt.templateId).html());
            opt.container && opt.container.html(template(opt.data));

            if(opt.callback && $.isFunction(opt.callback)){
                opt.callback();
            }
        }

    }

};

// 相加
Handlebars.registerHelper ("mccmsaAdd", function (value1, value2) {
    return value1 + value2;
}); 

// 粗体
Handlebars.registerHelper ("mccmsaBold", function (value) {
    return new Handlebars.SafeString("<b>" + value + "</b>");
});

// 相等则显示
Handlebars.registerHelper ("mccmsaShowIfEqual", function (value, value2, options) {
    if(value == value2){
        return options.fn(this);
    }

    return options.inverse(this);
});

// 与if使用错误则继续
Handlebars.registerHelper ("error", function (value , options) {
    if(value == ""){
        return options.fn(this);
    }
});
/* Jquery plugin START */
/*
* 表单验证，通过给需要验证的输入控件（input、select等）添加类实现验证规则设定
*
* 类名格式：mccms-validate-验证规则[?参数]
* 如：
* 非空 mccms-validate-required
* 最大长度10字符 mccms-validate-maxlength?10
* 输入值大于1小于10范围内 mccms-validate-range?[1,10]
* */
$.fn.mcFormValide = function(options) {
    var defaultOptions = {};

    options = $.extend(defaultOptions, options);

    return this.each(function(){
        var self = $(this);

        // 需要验证的空间元素，包含mccms-validate-*类的空间元素
        var inputEles = self.find('[class*="mccms-validate-"]');

        var rules = {};
        for(var i = 0; i < inputEles.length; i++){
            var inputEle = inputEles.eq(i);
            var inputClassList = inputEles[i].classList;

            var rule = {};
            var regExpPrefix = /^mccms-validate-/;  // 前缀
            var regExpSuffix = /\?\S+$/;  // 后缀
            for(var j = 0; j < inputClassList.length; j++){
                var clazz = inputClassList[j];

                var ruleName = clazz.replace(regExpPrefix, '').replace(regExpSuffix, "");

                if(regExpPrefix.test(clazz)){
                    // 规则带参数
                    if(regExpSuffix.test(clazz)){
                        rule[ruleName] = clazz.match(regExpSuffix)[0].replace("?", "");  // 把”？“去掉
                    }
                    // 不带参数直接给true
                    else{
                        rule[ruleName] = true;
                    }
                }
            }

            rules[inputEle.attr('name')] = rule;
        }

        self.validate({
            rules: rules
        });
    });
};
/* Jquery plugin END */
var httpUrl = "";
//"http://192.168.31.109:8081/mccms-web/";
/*alex*/
/*-- nav --*/
function navMenu(){
	mccmsa.ajax({
		url:httpUrl+"permission/menu",
		method:"GET",
		success:function(json){
			var data=[];
			var temp=[];
			for (var i in json.result){
				if(json.result[i].permissionId==0){
					json.result[i].child=[];
					data.push(json.result[i]);
				}else{
					temp.push(json.result[i])
				}
			}
			for(var j in temp){
				for(var a in data){
					if(temp[j].permissionId==data[a].id){
						data[a].child.push(temp[j])
					}
				}
			}
			var json={};
			json.data=data;
			mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
				templateId: "navMenu",
				container: $("#navContainer"),
				data:json,
				callback: function(){
					$.Huifold("#Huifold .item h4","#Huifold .item .info","fast",1,"click");
				},
			});
			var styleIcon=["basic-setting","ui-style","column-setting ","content-manager","interaction-manager","system-data","system-config"]
			$(".item-icon").each(function(){
					var random = parseInt(Math.random()*styleIcon.length)
					$(this).addClass(styleIcon[random]);
					styleIcon.splice(random,1);
			})
		}
	})
}


/*--用户管理--*/ 
//列表
function userManage(pageNo){
        //给栏目表录入数据
		if(!pageNo){pageNo=1}		//分页页数
        mccmsa.ajax({
            url:httpUrl+"user/getUserAll",
            method:"GET",
			data:{
				pageNo:pageNo,
				pageSize:8
			},
            success:function(json){
				Handlebars.registerHelper("addOne",function(index,options){
				   return parseInt(index)+1;
				});
                mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                    templateId: "navMain",
                    container: $("#pathContainer")
                });

                mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                    templateId: "getColumnTableData",
                    container: $("#ColumnTableData"),
                    data:json
                });
				pageHtml(json);		//生成分页
                $("#userManage").show();
                $("#onClickDataTable").hide();
				
                var scrollbarWidth = $('.table-maxHeight')[0].offsetWidth - $('.table-maxHeight')[0].scrollWidth;
                $("table:eq(0)").css("paddingRight",scrollbarWidth);    //获取滚动条宽度并赋值到表头padding-right，对齐
            }
        })
}
//查看
function checkData(obj){//查询数据内容框     
        mccmsa.ajax({
                url:httpUrl+"user/getUser/"+$(obj).parents("tr").find("td:first").attr("userId"), //需要添加参数，看接口需要
                method:"GET",
                success:function(data){
                    mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                        templateId: "navCheck",
                        container: $("#pathContainer")
                    });
                    mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                        templateId: "getCheckData",
                        container: $("#onClickDataTable"),
                        data:data
                    });
                    $("#userManage").hide();
                    $("#onClickDataTable").show();
                }
        })
}
//编辑
function editData(obj){//编辑数据内容框
	var id = $(obj).parents("tr").find("td:first").attr("userId");
    mccmsa.ajax({
        url:httpUrl+"user/editUser/"+$(obj).parents("tr").find("td:first").attr("userId"), //需要添加参数，看接口需要
        method:"GET",
        success:function(json){
            mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                templateId: "navEdit",
                container: $("#pathContainer")
            });
            mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                templateId: "getEditData",
                container: $("#onClickDataTable"),
                data:json
            });
            $("#userManage").hide();
            $("#onClickDataTable").show();
			$("#select option[value='"+json.result.user.status+"']").attr("selected","selected");
			$("#role input[type=checkbox]:eq("+parseInt(json.result.user.roles[0].id-1)+")").prop("checked", true);
			$("#onClickDataTable tr:first").attr({
				"id":id
			});
        }
    })
}
function postEditData(obj){
	mccmsa.ajax({
        url:httpUrl+"user/updateUserInfo/", //需要添加参数，看接口需要
        method:"POST",
		data:{
			"id":$("#onClickDataTable tr:first").attr("id"),
			"username":$("#username").val(),
			"nickName":$("#nickName").val(),
			"mobilePhone":$("#mobilePhone").val(),
			"roleId":$("#role input[type=checkbox]:checked").val(),
			"status":$("#select option:selected").val()
		},
        success:function(json){
			alert("提交成功")
        } 
    })
}


//密码
function setPassword(obj){//修改密码内容框
		var id = $(obj).parents("tr").find("td:first").attr("userId");
		var userName = $(obj).parents("tr").find("td:eq(1)").text();
		mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
			templateId: "navSetPassword",
			container: $("#pathContainer")
		});
		mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
			templateId: "getSetPassword",
			container: $("#onClickDataTable"),
		});
		$("#onClickDataTable tr:first").attr({
			"id":id,
			"userName":userName
		});
		$("#userManage").hide();
		$("#onClickDataTable").show();
}
function postPassword(obj){//提交修改密码内容框
		mccmsa.ajax({
			url:httpUrl+"user/updateUserSafe", //需要添加参数，看接口需要
			method:"POST",
			data:{
				"id":$("#onClickDataTable tr:first").attr("id"),
				"username":$("#onClickDataTable tr:first").attr("userName"),
				"newPassword":$("#userPassword").val(),
				"authPassword":$("#newPassword").val()
			},
			success:function(json){
				$("#pathContainer input").click();
			}
		})
}


//新增
function newData(obj){//新增数据
		mccmsa.ajax({
			url:httpUrl+"role/getRoleAll", //需要添加参数，看接口需要
			method:"GET",
			success:function(json){
				console.log(json)
				mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
					templateId: "navNewData",
					container: $("#pathContainer")
				});
				mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
					templateId: "getNewData",
					container: $("#onClickDataTable"),
					data:json
				});

				$("#userManage").hide();
				$("#onClickDataTable").show();
			}
		})
		
}
function postNewData(obj){
	mccmsa.ajax({
        url:httpUrl+"user/addUser", //需要添加参数，看接口需要
        method:"POST",
		data:{
			"username":$("#newUserName").val(),
			"password":$("#newPassWord").val(),
			"nickName":$("#newName").val(),
			"mobilePhone":$("#newPhone").val(),
			"roleId":$("#role input:checked").val(),
			"static":$("#select option:selected").val()
		},
        success:function(json){
            mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                templateId: "navNewData",
                container: $("#pathContainer")
            });
            mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                templateId: "getNewData",
                container: $("#onClickDataTable"),
                data:json
            });

            $("#userManage").hide();
            $("#onClickDataTable").show();
        }
    })
}
function modalUser(obj){
	$("#myModal").attr("userId",$(obj).parents("tr").find("td:first").attr("userId"));
}
function deleteUser(){
	mccmsa.ajax({
		url:httpUrl+"user/delUser/"+$("#myModal").attr("userId"), //需要添加参数，看接口需要
		method:"POST",
		data:{
			"_method":"delete"
		},
		success:function(json){
			$("#myModal .close").click();
			userManage();
		}
	})
}


/*--角色管理--*/
//列表
function roleManagement(){
    //给栏目表录入数据
    mccmsa.ajax({
        url:httpUrl+"role/getRoleAll",
        method:"GET",
        success:function(json){
            mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                templateId: "navMain",
                container: $("#pathContainer")
            });

            mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                templateId: "getColumnTableData",
                container: $("#ColumnTableData"),
                data:json
            });

            $("#roleManagement").show();
            $("#onClickDataTable").hide();
			
			pageHtml(json);		//生成分页
			
            var scrollbarWidth = $('.table-maxHeight')[0].offsetWidth - $('.table-maxHeight')[0].scrollWidth;
            $("table:eq(0)").css("paddingRight",scrollbarWidth);    //获取滚动条宽度并赋值到表头padding-right，对齐

            $(".col-7").each(function(){
                    if($(this).text().length>80){
                        var text =  $(this).text().substring(0,80)+"...";
                        $(this).text(text);
                    }
            })

        }
    })
}
//查看
function checkDataRole(obj){
	mccmsa.ajax({
		url:httpUrl+"role/getRole/"+$(obj).parents("tr").attr("getId"), //需要添加参数，看接口需要
		method:"GET",
		success:function(json){
			mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                templateId: "navCheck",
                container: $("#pathContainer")
            });

            mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                templateId: "getCheckDataRole",
                container: $("#onClickDataTable"),
                data:json
            });
			$("#roleManagement").hide();
            $("#onClickDataTable").show();
			
		}
	})
}
//编辑
function editDataRole(obj){
	var roleId = $(obj).parents("tr").attr("getId");
	var roleName = $(obj).parents("tr").attr("name");
	mccmsa.ajax({
		url:httpUrl+"role/getRole/"+roleId, //需要添加参数，看接口需要
		method:"GET",
		success:function(json){
			var list=[];
			for(var i=0;i<json.result.permissions.length;i++){
				list.push(json.result.permissions[i].id);
			}
			editDataRoleAll(list,json,roleId,roleName);
		}
	})
}
function editDataRoleAll(list,da,roleId,roleName){
	 //给栏目表录入数据
    mccmsa.ajax({
        url:httpUrl+"permission/getPermissionAll",
        method:"GET",
        success:function(json){
			
            mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                templateId: "navEdit",
                container: $("#pathContainer")
            });

            mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                templateId: "getEditDataRole",
                container: $("#onClickDataTable"),
                data:da
            });
			for(var i=0;i<json.result.menu.length;i++){
				for(var j=0;j<list.length;j++){
					if(json.result.menu[i].id==list[j]){
						json.result.menu[i].checked=true;
					}
				}
			}
			for(var i=0;i<json.result.front.length;i++){
				for(var j=0;j<list.length;j++){
					if(json.result.front[i].id==list[j]){
						json.result.front[i].checked=true;
					}
				}
			}
			for(var i=0;i<json.result.opt.length;i++){
				for(var j=0;j<list.length;j++){
					if(json.result.opt[i].id==list[j]){
						json.result.opt[i].checked=true;
					}
				}
			}
			
			$("#roleManagement").hide();
            $("#onClickDataTable").show();
			
            var scrollbarWidth = $('.table-maxHeight')[0].offsetWidth - $('.table-maxHeight')[0].scrollWidth;
            $("table:eq(0)").css("paddingRight",scrollbarWidth);    //获取滚动条宽度并赋值到表头padding-right，对齐
			
			var setting = {
				check: {
					enable: true
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: zTreeOnClick
				}
			};
			
			json.result.menu[json.result.menu.length]={
                "id": 0,
                "permissionType": "0",
                "description": "MCCMS",
				"checked":true,
				open:true
            };
			json.result.front[json.result.front.length]={
                "id": 0,
                "permissionType": "0",
                "description": "MCCMS",
				"checked":true,
				open:true
            };
			$.fn.zTree.init($("#roleMenuTree"), setting, json.result.menu.reverse());
			$.fn.zTree.init($("#roleFrontTree"), setting, json.result.front.reverse());
			
			var data = '';
			for(var i=0;i<json.result.opt.length;i++){
				if(json.result.opt[i].checked){
					data += '<div class="text-l check-box"><input type="checkbox" checked="checked" roleId="'+json.result.opt[i].id+'" id="checkbox-'+i+'"><label for="checkbox-'+i+'">'+json.result.opt[i].description+'</label></div>'
				}else{
					data += '<div class="text-l check-box"><input type="checkbox" roleId="'+json.result.opt[i].id+'" id="checkbox-'+i+'"><label for="checkbox-'+i+'">'+json.result.opt[i].description+'</label></div>'
				}
			}
			$("#roleOptTree").html(data);
			$("#roleId").attr({
				"roleId":roleId,
				"roleName":roleName
			})
			
        }
    })
} 
function postRoleEditData(){
	var list = [];
	var treeFront= $.fn.zTree.getZTreeObj("roleFrontTree");
	var nodesFront = treeFront.getCheckedNodes(true);
	
	
	var treeMenu= $.fn.zTree.getZTreeObj("roleMenuTree");
	var nodesMenu = treeMenu.getCheckedNodes(true);

	for(var i=0;i<nodesFront.length;i++){
		list.push(nodesFront[i].id); //获取每个节点的id
	}
	for(var i=0;i<nodesMenu.length;i++){
		list.push(nodesMenu[i].id); //获取每个节点的id
	}
	
	
	$("#roleOptTree input").each(function(){
		if($(this).is(':checked')){
			list.push($(this).attr("roleid"));
		}
	})
	mccmsa.ajax({
		url:httpUrl+"role/updateRole",
		method:"POST",
		data:{
			id:$("#roleId").attr("roleId"),
			name:$("#roleId").attr("rolename"),
			description:$("#roleDescription").val(),
			permissionss:list.toString()
		},
		success:function(json){
			roleManagement();
		}
	})
	
}
//新增
function roleNewData(){
	var roleId = $("#ColumnTableData tr:first").attr("getId");
	var roleName = $("#ColumnTableData tr:first").attr("name");
	mccmsa.ajax({
		url:httpUrl+"permission/getPermissionAll",
		method:"GET",
		success:function(json){
			 mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                templateId: "navEdit",
                container: $("#pathContainer")
            });

            mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                templateId: "getNewDataRole",
                container: $("#onClickDataTable"),
                data:json
            });
			
			$("#roleManagement").hide();
            $("#onClickDataTable").show();
			
			
			var setting = {
				check: {
					enable: true
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: zTreeOnClick
				}
			};
			
			json.result.menu[json.result.menu.length]={
                "id": 0,
                "permissionType": "0",
                "description": "MCCMS",
				open:true
            };
			json.result.front[json.result.front.length]={
                "id": 0,
                "permissionType": "0",
                "description": "MCCMS",
				open:true
            };
			$.fn.zTree.init($("#roleMenuTree"), setting, json.result.menu.reverse());
			$.fn.zTree.init($("#roleFrontTree"), setting, json.result.front.reverse());
			
			var data = '';
			for(var i=0;i<json.result.opt.length;i++){
				data += '<div class="text-l check-box"><input type="checkbox" roleId="'+json.result.opt[i].id+'" id="checkbox-'+i+'"><label for="checkbox-'+i+'">'+json.result.opt[i].description+'</label></div>'
			}
			$("#roleOptTree").html(data);
			$("#roleId").attr({
				"roleId":roleId,
				"roleName":roleName
			})
		}
	})
	
}
function postRoleNewData(){
	var list = [];
	var treeFront= $.fn.zTree.getZTreeObj("roleFrontTree");
	var nodesFront = treeFront.transformToArray(treeFront.getCheckedNodes(true));
	var treeMenu= $.fn.zTree.getZTreeObj("roleMenuTree");
	var nodesMenu = treeMenu.transformToArray(treeMenu.getCheckedNodes(true));
	
	for(var i=0;i<nodesFront.length;i++){
		list.push(nodesFront[i].id); //获取每个节点的id
	}
	for(var i=0;i<nodesMenu.length;i++){
		list.push(nodesMenu[i].id); //获取每个节点的id
	}
	
	mccmsa.ajax({
		url:httpUrl+"role/addRole",
		method:"POST",
		data:{
			name:$("#roleDescription").val(),
			description:$("#roleDescription").val(),
			permissionss:list.toString()
		},
		success:function(json){
			roleManagement();
		}
	})
}
//删除
function delRoleMode(obj){
	$("#myModal").attr("getId",$(obj).parents("tr").attr("getid"))
}

function postRoleDelData(){
	mccmsa.ajax({
		url:httpUrl+"role/delRole/"+$("#myModal").attr("getId"),
		method:"POST",
		data:{
			"_method":"delete"
		},
		success:function(json){
			roleManagement();
			$("#myModal .close").click();
		}
	})
}

/*--权限管理--*/
//列表
var id;
function permissionManage(){
    //给栏目表录入数据
    mccmsa.ajax({
        url:httpUrl+"permission/getPermissionAll",
        method:"GET",
        success:function(json){
            mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                templateId: "navMain",
                container: $("#pathContainer")
            });

            mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                templateId: "getColumnTableData",
                container: $("#ColumnTableData"),
                data:json
            });

            $("#permissionManage").show();
            $("#onClickDataTable").hide();
			
            var scrollbarWidth = $('.table-maxHeight')[0].offsetWidth - $('.table-maxHeight')[0].scrollWidth;
            $("table:eq(0)").css("paddingRight",scrollbarWidth);    //获取滚动条宽度并赋值到表头padding-right，对齐
			
			var setting = {                    
				data: {
					simpleData: {
						enable: true,
						idKey: "id"
					},
				},
				callback: {
					onClick: zTreeOnClick
				}
			};
			
			json.result.menu[json.result.menu.length]={
                "id": 0,
                "permissionType": "0",
                "description": "MCCMS",
				open:true
            };
			json.result.front[json.result.front.length]={
                "id": 0,
                "permissionType": "0",
                "description": "MCCMS",
				open:true
            };
			$.fn.zTree.init($("#treeMenu"), setting, json.result.menu.reverse());
			$.fn.zTree.init($("#treeFront"), setting, json.result.front.reverse());
        }
    })
}
//新增
function permissionNewData(obj){//新增数据
		$(".treeFunction").remove();
		mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
			templateId: "navNewData",
			container: $("#pathContainer")
		});
		mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
			templateId: "getNewData",
			container: $("#onClickDataTable"),
		});
		
		$("#parentsZTree .modal-body .modal-treeMenu").html(treeMenu);
		$("#parentsZTree .modal-body .modal-treeFront").html(treeFront);
		
		$("#permissionManage").hide();
		$("#onClickDataTable").show();
}
function newChooseTree(obj){
		if($("#select option:selected").val()==0){
			$("#parentsZTree .modal-body .modal-treeMenu").show();
			$("#parentsZTree .modal-body .modal-treeFront").hide();
		}else if($("#select option:selected").val()==1){
			$("#parentsZTree .modal-body .modal-treeMenu").hide();
			$("#parentsZTree .modal-body .modal-treeFront").show();
		}
}
function postNewPermissionData(){
		mccmsa.ajax({
			url:httpUrl+"permission/addPermission",
			method:"POST",
			data:{
				name:$("#permissionName").val(),
				description:$("#permissionDescription").val(),
				permissionId:id,
				uri:$("#permissionUri").val(),
				permissionType:$("#onClickDataTable #select option:selected").val()
			},
			success:function(json){
				alert(json.message);
				permissionManage();
			}
		})
}
//编辑
function getEditPermissionData(obj){
		var parText = $(obj).parents("ul").prev().find("span:eq(1)").text();
		mccmsa.ajax({
			url:httpUrl+"permission/getPermission/"+id,
			method:"GET",
			success:function(json){
				$(".treeFunction").remove();
				mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
					templateId: "navEdit",
					container: $("#pathContainer")
				});
				mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
					templateId: "getEditData",
					container: $("#onClickDataTable"),
					data:json
				});
				$("#permFirstTr").attr("nodeId",id);
				id = json.result.permissionId;
				$("#parPermission").val(parText);
				$("#select option:eq("+json.result.permissionType+")").attr("selected","selected");
				
				$("#parentsZTree .modal-body .modal-treeMenu").html(treeMenu);
				$("#parentsZTree .modal-body .modal-treeFront").html(treeFront);
				
				$("#permissionManage").hide();
				$("#onClickDataTable").show();
			}
		})
}
function postEditPermissionData(){ 
		mccmsa.ajax({
			url:httpUrl+"permission/updatePermission",
			method:"POST",
			data:{
				id:$("#permFirstTr").attr("nodeId"),
				name:$("#permissionName").val(),
				description:$("#permissionDescription").val(),
				permissionId:id,
				uri:$("#permissionUri").val(),
				permissionType:$("#onClickDataTable #select option:selected").val()
			},
			success:function(json){
				alert(json.message);
				permissionManage();
			}
		})
}
//删除
function postDelPermissionData(){
		mccmsa.ajax({
			url:httpUrl+"permission/delPermission/"+id,
			method:"POST",
			data:{
				"_method":"delete"
			},
			success:function(json){
				alert(json.message);
				permissionManage();
				$(".close").click();
			}
		})
}

function zTreeOnClick(event, treeId, treeNode) {
	id=treeNode.id;
};
function dblClickExpand(treeId, treeNode) {
	return treeNode.level > 0;
}



/*--栏目配置-栏目管理--*/
//列表
function columnManage(pageNo,id){
        //给栏目表录入数据
		if(!pageNo){pageNo=1}		//分页页数
		if(!id){id=0}
        mccmsa.ajax({
            url:httpUrl+"column/getColumnsSubList/"+id,
            method:"GET",
			data:{
				pageNo:pageNo,
				pageSize:8
			},
            success:function(json){
				Handlebars.registerHelper("addOne",function(index,options){
				   return parseInt(index)+1;
				});
                mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
					templateId: "navMain",
					container: $("#pathContainer")
				});

                mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
                    templateId: "getColumnTableData",
                    container: $("#ColumnTableData"),
                    data:json
                });
				pageHtml(json);		//生成分页
                $("#columnManage,.zTreeDemoBackground").show();
                $("#onClickDataTable").hide();
				
				columnZtree();
				$(".zTreeDemoBackground").append(treeDemo)
				
                var scrollbarWidth = $('.table-maxHeight')[0].offsetWidth - $('.table-maxHeight')[0].scrollWidth;
                $("table:eq(0)").css("paddingRight",scrollbarWidth);    //获取滚动条宽度并赋值到表头padding-right，对齐
			}
        })
}
function columnZtree(){		//初始化zTree
	var setting = {
		view: {
			dblClickExpand: dblClickExpand
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback:{
			onClick:columnZtreeClick
		}
	};
	var zNodes = [];
	mccmsa.ajax({
		url:httpUrl+"column/getColumnsInfo",
		method:"GET",
		success:function(json){
			var zeroJson = {
				"id": 0,
				"permissionType": "0",
				"description": "MCCMS",
				open:true
			}
			for(var i=0;i<json.result.length;i++){
				var data = {};
				data.id = json.result[i][0];
				data.permissionId = json.result[i][1];
				data.description = json.result[i][2];
				zNodes.push(data)
			}
			zNodes.push(zeroJson)
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		}
	})
}
function columnZtreeClick(event, treeId, treeNode){	//节点点击回调事件
	if(!$("#treeDemo").parents(".modal-scrollable").length){
		if(treeNode.children){
			columnManage(1,treeNode.id)
		}else{
			columnEdit(false,treeNode)
		}
	}
}
//编辑
function columnEdit(obj,treeNode){
		var id,parentName;
		if(!obj){
			id=treeNode.id;
			parentName=treeNode.getParentNode().description;
		}else{
			id=$(obj).parents("tr").attr("columnid");
			parentName=$.fn.zTree.getZTreeObj("treeDemo").getNodeByParam("id", id, null).getParentNode().description;
		}
		mccmsa.ajax({
            url:httpUrl+"column/getColumnTemplate",
            method:"GET",
            success:function(json){
				var html = "";
				for(var i=0;i<json.result.length;i++){
					html+='<option value="'+json.result[i].id+'">'+json.result[i].name+'</option>'
				}
				mccmsa.ajax({
					url:httpUrl+"column/getColumnById/"+id,
					method:"GET",
					success:function(json){
						mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
							templateId: "navEdit",
							container: $("#pathContainer")
						});

						mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
							templateId: "getEditDataColumn",
							container: $("#onClickDataTable"),
							data:json
						});
						$("#postColumnTemplate").html(html);
						
						$("#columnManage,.zTreeDemoBackground").hide();
						$("#onClickDataTable").show();
						
						$("#isShowInNav option[value='"+json.result.isShowInNav+"']").attr("selected","selected");
						$("#columnState option[value='"+json.result.state+"']").attr("selected","selected");
						$("#postColumnTemplate option[value='"+json.result.template+"']").attr("selected","selected");
						
						$("#sureParents").val(parentName);
						
						$("#parentsZTree .modal-body .modal-treeMenu").html(treeDemo);
						
						//实例化编辑器
						//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
						editor = new UE.ui.Editor();
						editor.render("editor");
						//判断ueditor 编辑器是否创建成功
						editor.addListener("ready", function() {
							// editor准备好之后才可以使用
							if(json.result.content!=null){
								editor.setContent(json.result.content);
							}
						});
						$("#onClickDataTable>tr:last").after('<tr><td class="col-1 table-bgc-f5"></td><td class="text-l col-11"> <input class="btn btn-primary radius" onClick="columnPostEdit()" type="submit" value="提交"></td></tr>')
					}
				})
            }
        })
}
function columnPostEdit(){
		mccmsa.ajax({
            url:httpUrl+"column/editColumn/",
            method:"POST",
			data:{
				"id":$("#postColumnId").attr("columnId"),
				"name":$("#postColumnName").val(),
				"content":editor.getContent(),
				"shortName":$("#postColumnShortName").val(),
				"isShowInNav":$("#isShowInNav option:selected").val(),
				"columnKey":$("#postColumnColumnKey").val(),
				"serial":$("#postColumnSerial").val(),
				"template":$("#postColumnTemplate option:selected").val(),
				"link":$("#postColumnLink").val(),
				"parent":$.fn.zTree.getZTreeObj("treeDemo").getNodeByParam("description", $("#sureParents").val(), null).id,
				"state":$("#columnState option:selected").val()
			},
            success:function(json){
				columnManage();
            }
        })
}
//新增数据
function columnNew(){
		mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
			templateId: "navNew",
			container: $("#pathContainer")
		});

		mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
			templateId: "getNewDataColumn",
			container: $("#onClickDataTable")
		});
		$("#parentsZTree .modal-body .modal-treeMenu").html(treeDemo);
		$("#columnManage,.zTreeDemoBackground").hide();
		$("#onClickDataTable").show();
		
		mccmsa.ajax({
            url:httpUrl+"column/getColumnTemplate",
            method:"GET",
            success:function(json){
				var html = "";
				for(var i=0;i<json.result.length;i++){
					html+='<option value="'+json.result[i].id+'">'+json.result[i].name+'</option>'
				}
				$("#postColumnTemplate").html(html);
				//实例化编辑器
				//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
				editor = new UE.ui.Editor();
				editor.render("editor");
				//判断ueditor 编辑器是否创建成功
				$("#onClickDataTable>tr:last").after('<tr><td class="col-1 table-bgc-f5"></td><td class="text-l col-11"> <input class="btn btn-primary radius" onClick="columnPostNew()" type="submit" value="提交"></td></tr>')
			}
		})
}
function columnPostNew(){
		mccmsa.ajax({
            url:httpUrl+"column/newColumn/",
            method:"POST",
			data:{
				"id":$("#postColumnId").attr("columnId"),
				"name":$("#postColumnName").val(),
				"content":editor.getContent(),
				"shortName":$("#postColumnShortName").val(),
				"isShowInNav":$("#isShowInNav option:selected").val(),
				"columnKey":$("#postColumnColumnKey").val(),
				"serial":$("#postColumnSerial").val(),
				"template":$("#postColumnTemplate option:selected").val(),
				"link":$("#postColumnLink").val(),
				"parent":$.fn.zTree.getZTreeObj("treeDemo").getNodeByParam("description", $("#sureParents").val(), null).id,
				"state":$("#columnState option:selected").val()
			},
            success:function(json){
				columnManage();
            }
        })
}
//删除数据
function postDelColumnData(obj){
	
		mccmsa.ajax({
			url:httpUrl+"column/deleteColumn/"+$("#myModal").attr("columnId"),
			method:"POST",
			data:{
				"_method":"delete"
			},
			success:function(json){
				$("#myModal .close").click();
				columnManage();
			}
		})
}
function columnDel(obj){
	$("#myModal").attr("columnId",$(obj).parents("tr").attr("columnid"))
}



/*--栏目配置-内容管理--*/
//列表

var jsonContent;
var columnName;
var columnId;
var editor;
function Content(obj){
	//给栏目表录入数据
	columnName=$(obj).parents("tr").find("td:eq(1)").text();
	columnId=$(obj).parents("tr").attr("columnid");
	mccmsa.ajax({
		url:httpUrl+"news/getNewsSubList/"+columnId,
		method:"GET",
		success:function(json){
			Handlebars.registerHelper("addOne",function(index,options){
			   return parseInt(index)+1;
			});
			mccmsa.getHtmlFromRemoteTemplate({
                templateName:"contentManage.hbs",
                container: $("#mainContainer")
            });
			jsonContent=json;
		}
	})
}
function contentManage(json){
	mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
		templateId: "navMain",
		container: $("#pathContainer")
	});

	mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
		templateId: "getColumnTableData",
		container: $("#ColumnTableData"),
		data:json
	});
	$("#contentManage").show();
	$("#htmlCheckContent,#onClickDataTable").hide();
	pageHtml(json);		//生成分页
}
//查看
function contentCheck(obj){
	mccmsa.ajax({
		url:httpUrl+"news/getNewsById/"+$(obj).parents("tr").attr("newid"),
		method:"GET",
		success:function(json){
			mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
				templateId: "navCheck",
				container: $("#pathContainer")
			});

			mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
				templateId: "getCheckDataContent",
				container: $("#onClickDataTable"),
				data:json
			});
			
			$("#onClickDataTable").show();
			$("#htmlCheckContent,#contentManage").hide();
			
			//实例化编辑器
			//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
			editor = new UE.ui.Editor();
			editor.render("editor");
			//判断ueditor 编辑器是否创建成功
			editor.addListener("ready", function() {
				// editor准备好之后才可以使用
				editor.setContent(json.result.contents);
			});
			$("#onClickDataTable>tr:last").after('<tr><td class="col-1 table-bgc-f5"></td><td class="text-l col-11"> <input class="btn btn-primary radius"  onClick="contentEditPost()" type="submit" value="提交"></td></tr>')
		}
	})
	
}
//编辑
function contentEdit(obj){
	mccmsa.ajax({
		url:httpUrl+"news/getNewsById/"+$(obj).parents("tr").attr("newid"),
		method:"GET",
		success:function(json){
			mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
				templateId: "navEdit",
				container: $("#pathContainer")
			});

			mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
				templateId: "getEditDataContent",
				container: $("#onClickDataTable"),
				data:json
			});
			
			$("#onClickDataTable tr:first").attr("newId",$(obj).parents("tr").attr("newid"));
			
			$("#newColumnContent").val(columnName);
			$("#postContentTitle").val(json.result.title);
			$("#postContentURL").val(json.result.otherURL);
			$("#postContentKey").val(json.result.priKey);
			$("#postContentShortMeta").text(json.result.shortMeta);
			$("#postContentAutor").val(json.result.autor);
			$("#postContentSource").val(json.result.source);
			
			if(json.result.photoFileName!=null){
				$("#preview").attr("src",json.result.photoFileName);
				$("#preview").parent().find(".shadeFile").html(json.result.photoFileName.substring(json.result.photoFileName.lastIndexOf("/")+1))
			}
			
			if(json.result.fileName!=null){
				$("#file").attr("src",json.result.fileName)
				$("#file").parent().find(".shadeFile").html(json.result.fileName.substring(json.result.fileName.lastIndexOf("/")+1))
			}
			
			$(".select option[value="+json.result.state+"]").attr("selected","selected");
			
			
			var jsonObj=[
				$("#postContentIsTop"),json.result.isTop,
				$("#postContentIsCommend"),json.result.isCommend,
				$("#postContentisOnlyContent"),json.result.isOnlyContent,
				$("#postContentIsShowedOnIndex"),json.result.isShowedOnIndex
			];
			checkedObj(jsonObj);
			
			$("#onClickDataTable").show();
			$("#htmlCheckContent,#contentManage").hide();
			//实例化编辑器
			//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
			editor = new UE.ui.Editor();
			editor.render("editor");
			//判断ueditor 编辑器是否创建成功
			editor.addListener("ready", function() {
				// editor准备好之后才可以使用
				editor.setContent(json.result.contents);
			});
			$("#onClickDataTable>tr:last").after('<tr><td class="col-1 table-bgc-f5"></td><td class="text-l col-11"> <input class="btn btn-primary radius"  onClick="contentEditPost()" type="submit" value="提交"></td></tr>')
		}
	})
}
function contentEditPost(){
	mccmsa.ajax({
		url: httpUrl+"news/editNews",
		method:"POST",
		data:{
			id:$("#onClickDataTable tr:first").attr("newId"),
			columnId:columnId,
			title:$("#postContentTitle").val(),
			priKey:$("#postContentKey").val(),
			shortMeta:$("#postContentShortMeta").val(),
			autor:$("#postContentAutor").val(),
			source:$("#postContentSource").val(),
			contents:editor.getContent(),
			otherURL:$("#postContentURL").val(),
			isTop:$("#postContentIsTop:checked").length?1:0,
			isCommend:$("#postContentIsCommend:checked").length?1:0,
			isOnlyContent:$("#postContentisOnlyContent:checked").length?1:0,
			isShowedOnIndex:$("#postContentIsShowedOnIndex:checked").length?1:0,
			newsType:0,
			fileName:$("#file").attr("src"),
			photoFileName:$("#preview").attr("src"),
			state:$(".select option:selected").val()
		},
		success:function(json){
			alert(json.message)
			$(".breadcrumb input").click();
		}
	})
	
}
function checkedObj(obj){
	for(var i=0;i<obj.length;i++){
		if(obj[i+1]==1){
			obj[i].attr("checked","checked");
		}
		i++;
	}
}
//新增
function contentNew(obj){
	//给栏目表录入数据
	mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
		templateId: "navNew",
		container: $("#pathContainer")
	});

	mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
		templateId: "getNewDataContent",
		container: $("#onClickDataTable")
	});
	
	$("#newColumnContent").val(columnName);
	
	$("#onClickDataTable").show();
	$("#htmlCheckContent,#contentManage").hide();
	
	var scrollbarWidth = $('.table-maxHeight')[0].offsetWidth - $('.table-maxHeight')[0].scrollWidth;
	$("table:eq(0)").css("paddingRight",scrollbarWidth);    //获取滚动条宽度并赋值到表头padding-right，对齐
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    editor = new UE.ui.Editor();
    editor.render("editor");
	$("#onClickDataTable>tr:last").after('<tr><td class="col-1 table-bgc-f5"></td><td class="text-l col-11"> <input class="btn btn-primary radius"  onClick="contentNewPost()" type="submit" value="提交"></td></tr>');
}

function uploadPhoto(obj){ 
	if($("#preview").parent().find("img").length==2){
		$(".loadImg").show();
	}else{
		$("#preview").after("<img class='loadImg' src='img/loadFile.gif'>")
	}
	var val = $("#doc").val();
	$.ajaxFileUpload({
		url: httpUrl+"base/imageUpload", //用于文件上传的服务器端请求地址
		secureuri: false, //一般设置为false
		fileElementId: ['doc'], //文件上传空间的id属性  <input type="file" id="file" name="file" />
		dataType: 'json', //返回值类型 一般设置为json

		success: function (data, status){  //服务器成功响应处理函数
			alert(data.message);
			$("#preview").parent().find("span").text(val);
			$("#preview").attr({
				"src":httpUrl.substring(0,httpUrl.length-1)+data.result
			});
			$(".loadImg").hide();
		},
		error: function (data, status, e){//服务器响应失败处理函数
			alert(data.message)
		}
	})
}
function uploadFile(){
	if($("#file").parent().find("img").length){
		$(".loadImg").show();
	}else{
		$("#file").before("<img class='loadImg' src='img/loadFile.gif'>")
	}
	var val = $("#file").val();
	$.ajaxFileUpload({
		url: httpUrl+"base/fileUpload", //用于文件上传的服务器端请求地址
		secureuri: false, //一般设置为false
		fileElementId: ['file'], //文件上传空间的id属性  <input type="file" id="file" name="file" />
		dataType: 'json', //返回值类型 一般设置为json

		success: function (data, status){  //服务器成功响应处理函数
			alert(data.message);
			$("#file").parent().find("span").text(val);
			$("#file").attr({
				"src":httpUrl+data.result
			});
			$(".loadImg").hide();
		},
		error: function (data, status, e){//服务器响应失败处理函数
			alert(data.message)
		}
	})
}
	
	

function contentNewPost(){
	mccmsa.ajax({
		url: httpUrl+"news/newNews",
		method:"POST",
		data:{
			columnId:columnId,
			title:$("#postContentTitle").val(),
			priKey:$("#postContentKey").val(),
			shortMeta:$("#postContentShortMeta").val(),
			autor:$("#postContentAutor").val(),
			source:$("#postContentSource").val(),
			contents:editor.getContent(),
			otherURL:$("#postContentURL").val(),
			isTop:$("#postContentIsTop:checked").length?1:0,
			isCommend:$("#postContentIsCommend:checked").length?1:0,
			isOnlyContent:$("#postContentisOnlyContent:checked").length?1:0,
			isShowedOnIndex:$("#postContentIsShowedOnIndex:checked").length?1:0,
			newsType:0,
			fileName:$("#file").attr("src"),
			photoFileName:$("#preview").attr("src"),
			state:$(".select option:selected").val()
		},
		success:function(json){
			alert(json.message)
			updateContent();
		}
	})
}
//删除
function postDelContentData(obj){
		mccmsa.ajax({
			url:httpUrl+"/news/deleteNews/"+$("#myModal").attr("columnId"),
			method:"POST",
			data:{
				"_method":"delete"
			},
			success:function(json){
				$("#myModal .close").click();
				updateContent();
			}
		})
}
function updateContent(){
	mccmsa.ajax({
		url:httpUrl+"news/getNewsSubList/"+columnId,
		method:"GET",
		success:function(json){
			contentManage(json);
		}
	})
}
function columnConDel(obj){
	$("#myModal").attr("columnId",$(obj).parents("tr").attr("newid"))
}




/*--栏目配置-模板类型管理--*/
//列表
function templateManage(){
	//给栏目表录入数据
	mccmsa.ajax({
		url:httpUrl+"column/getColumnTemplate",
		method:"GET",
		success:function(json){
			Handlebars.registerHelper("addOne",function(index,options){
			   return parseInt(index)+1;
			});
			mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
				templateId: "navMain",
				container: $("#pathContainer")
			});

			mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
				templateId: "getColumnTableData",
				container: $("#ColumnTableData"),
				data:json
			});
			//pageHtml(json);		//生成分页
			
			$("#templateManage").show();
			$("#onClickDataTable").hide();
			
			var scrollbarWidth = $('.table-maxHeight')[0].offsetWidth - $('.table-maxHeight')[0].scrollWidth;
			$("table:eq(0)").css("paddingRight",scrollbarWidth);    //获取滚动条宽度并赋值到表头padding-right，对齐
		}
	})
}

function templateNewData(){
	//给栏目表录入数据
	mccmsa.ajax({
		url:httpUrl+"column/getColumnTemplate",
		method:"GET",
		success:function(json){
			Handlebars.registerHelper("addOne",function(index,options){
			   return parseInt(index)+1;
			});
			mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
				templateId: "navMain",
				container: $("#pathContainer")
			});

			mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
				templateId: "getNewDataTemplate",
				container: $("#onClickDataTable"),
				data:json
			});
			//pageHtml(json);		//生成分页
			$("#onClickDataTable").show();
			$("#templateManage").hide();
		}
	})
}

/*--基本配置-公共信息管理--*/
function commonManage(){
		mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
			templateId: "navMain",
			container: $("#pathContainer")
		});
     
}

/*--基本配置-站点管理--*/
function siteManage(){
		mccmsa.ajax({
			url:httpUrl+"site/getWebSite",
			method:"GET",
			success:function(json){
				mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
					templateId: "navMain",
					container: $("#pathContainer")
				});
				mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
					templateId: "mainTableSite",
					container: $("#onClickDataTable"),
					data:json.result
				});
			}
		})
		
     
}

/*
 * ajax分页
 */
function pageHtml(json){
	var data="";
	if(json.result.pageCount<=6){
		for(var i=1;i<json.result.pageCount+1;i++){
			data+='<a class="pageClick" pageNo='+i+'>'+i+'</a>';
		}
	}else if(json.result.pageNo>json.result.pageCount-3){
		data+='<a class="pageClick" pageNo="1">1</a>... ...';
		for(var i=json.result.pageCount-3;i<=json.result.pageCount;i++){
			data+='<a class="pageClick" pageNo='+i+'>'+i+'</a>';
		}
	}else if(json.result.pageNo<4){
		for(var i=1;i<6;i++){
			data+='<a class="pageClick" pageNo='+i+'>'+i+'</a>';
		}
		data+='...<a class="pageClick" pageNo='+json.result.pageCount+'>'+json.result.pageCount+'</a>';
	}else{
		data+='<a class="pageClick" pageNo="1">1</a>...';
		data+='<a class="pageClick" pageNo='+(json.result.pageNo-2)+'>'+(json.result.pageNo-2)+'</a>';
		data+='<a class="pageClick" pageNo='+(json.result.pageNo-1)+'>'+(json.result.pageNo-1)+'</a>';
		data+='<a class="pageClick" pageNo='+json.result.pageNo+'>'+json.result.pageNo+'</a>';
		data+='<a class="pageClick" pageNo='+(json.result.pageNo+1)+'>'+(json.result.pageNo+1)+'</a>';
		data+='<a class="pageClick" pageNo='+(json.result.pageNo+2)+'>'+(json.result.pageNo+2)+'</a>';
		data+='...<a class="pageClick" pageNo='+json.result.pageCount+'>'+json.result.pageCount+'</a>';
	}
	$("#layPage").html('<table class="pager" pageNo="'+json.result.pageNo+'"><tbody><tr><td width="60%"><span class="btn btn-small" id="pageIndex">首页</span><span class="btn btn-small" id="pagePrev">上一页</span>'+data+'<span class="btn btn-small" id="pageNext">下一页</span><span class="btn btn-small" id="pageLast">尾页</span></td></tr></tbody></table>');
	$("#layPage a[pageNo="+json.result.pageNo+"]").addClass("laypage_curr").removeClass("pageClick");
	
};
$("#mainContainer").on("click","#pageIndex",function(){
	eval($(this).parents(".Hui-article").attr("id")+'(1)');
});
$("#mainContainer").on("click","#pageLast",function(){
	eval($(this).parents(".Hui-article").attr("id")+'('+$("#layPage a").length+')');
});
$("#mainContainer").on("click","#pagePrev",function(){
	eval($(this).parents(".Hui-article").attr("id")+'('+(parseInt($("#layPage .pager").attr("pageNo"))-parseInt(1))+')');
});
$("#mainContainer").on("click","#pageNext",function(){ 
	eval($(this).parents(".Hui-article").attr("id")+'('+(parseInt($("#layPage .pager").attr("pageNo"))+parseInt(1))+')');
});
$("#mainContainer").on("click",".pageClick",function(){
	eval($(this).parents(".Hui-article").attr("id")+'('+parseInt($(this).text())+')');
});


$(function(){
	//zTree树节点点击时显示编辑，删除
	$("#mainContainer").on("click","#permissionManage .treeTip a",function(event){
		$(".treeFunction").remove();
		var html = "<div class='treeFunction'><span class='treeEdit' onClick='getEditPermissionData(this)'>编辑</span><span class='treeDel' data-toggle='modal' href='#myModal' onclick='modalUser(this)'>删除</span></div>"
		$(this).append(html)
	});
	//权限，当权限类型选为功能时，隐藏父级权限选择
	$("#mainContainer").on("change","#onClickDataTable #select",function(event){
		if($(this).val()==2){
			$("#permFirstTr").hide();
		}else{
			$("#permFirstTr").show();
		}
	});
	
});

//公用方法
function sureParents(obj){
		var text = $("#parentsZTree .curSelectedNode span:eq(1)").text();
		$("#sureParents").val(text);
		$(".modal-header a").click();
}


/*alex end*/






















/*--MCGT--*/
function fieldSure(){
    mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
        templateId: "navMain",
        container: $("#pathContainer")
    });

    mccmsa.getHtmlFromLocalTemplate({   //将数据打包到模版丢进容器
        templateId: "getColumnTableData",
        container: $("#ColumnTableData")
    });
    $("#userManage").show();
    $("#onClickDataTable").hide();

    var text="";
    $(".field input").each(function(){
        if($(this).val()){
            text+=$(this).val()+"-"
        }
    });
    text += $("#field-type").val();
    $("#ColumnTableData #fieldTr input:last").val(text);
    $("#ColumnTableData #fieldTr p:last").after('<p><input type="text" class="input-text input-col-3 mb-10 bgTextColoe" disabled="disabled"></p>');
    $("#myModal .close").click();
    fieldClear();
}
function fieldClear(){
    $(".field input").val("");
}
function McgtPost(){
    var data0={};
    for(var i=0,j=0;i<$("#fieldTr input").length;i++){
        var text = $("#fieldTr input:eq("+i+")").val();
        if(text){
            text = text.split("-");
            data0['mcColumns['+j+'].name']=text[0];
            data0['mcColumns['+j+'].length']=text[1];
            data0['mcColumns['+j+'].notNull']=text[2];
            data0['mcColumns['+j+'].defaultValue']=text[3];
            data0['mcColumns['+j+'].comment']=text[4];
            data0['mcColumns['+j+'].type']=text[5];
            j++;
        }
    }
    data0['projectRootPath']=$("#projectRootPathInput").val().substring(0,$("#projectRootPathInput").val().lastIndexOf("\\"));
    data0['moduleName']=$("#mcgtFieldNameDesc").val();
    data0['moduleNameDesc']=$("#mcgtFieldName").val();
    data0['name']=$("#mcgtTableName").val();
    data0['comment']=$("#mcgtTableNameDesc").val();

    mccmsa.ajax({
        url:"mcgenerate/generate",
        method:"POST",
        dataType:"json",
        data:data0,
        success:function(json){
            alert("提交成功");
        }
    })
}

/* Gary START */
/* Template START */
function Template(){
    var pathContainerEle = $("#pathContainer");
    var listContainerEle = $("#listContainer");
    var operationContainerEle = $("#operationContainer");

    mccmsa.getHtmlFromLocalTemplate({
        templateId: "pathMain",
        container: pathContainerEle
    });

    getListData();

    // 当前位置 新增、返回按钮
    pathContainerEle.on("click", ".path-btn", function(){
        var self = $(this);

        // 新增
        if(self.attr("operation") == "new"){
            // 更新当前位置
            mccmsa.getHtmlFromLocalTemplate({
                templateId: "pathNew",
                container: pathContainerEle
            });
            mccmsa.getHtmlFromLocalTemplate({
                templateId: "newTemplate",
                container: operationContainerEle
            });

            listContainerEle.hide();
            operationContainerEle.show();
        }
        // 返回
        else if(self.attr("operation") == "back"){
            backToList();
        }
    });

    // 列表 查看/编辑按钮
    listContainerEle.on("click", ".operation-btn", function(){
        var self = $(this);

        // 查看
        if(self.attr("operation") == "check"){
            mccmsa.ajax({
                url: "jsonData/check",
                data: {}, // 传id？
                method:"GET",
                success:function(data){
                    // 更新当前位置
                    mccmsa.getHtmlFromLocalTemplate({
                        templateId: "pathCheck",
                        container: pathContainerEle
                    });
                    mccmsa.getHtmlFromLocalTemplate({
                        templateId: "editTemplate",
                        container: operationContainerEle,
                        data: data.result
                    });

                    listContainerEle.hide();
                    operationContainerEle.show();
                }
            })
        }
    });

    // 新增/查看/修改提交按钮
    operationContainerEle.on("click", ".operation-submit-btn", function(){
        var self = $(this);

        // 新增提交
        if(self.attr("operation") == "new"){
            mccmsa.ajax({
                url: "jsonData/new", //需要添加参数，看接口需要
                method: "GET",
                data: $("newForm").serialize,
                success:function(json){
                    mccmsa.getHtmlFromLocalTemplate({
                        templateId: "getNewData",
                        container: $("#onClickDataTable"),
                        data:json
                    });

                    getListData();
                    backToList();
                }
            })
        }

        if(self.attr("operation") == "edit"){

        }
    });
}

// 取列表数据，显示列表
function getListData(){
    mccmsa.ajax({
        url: "jsonData/apiUrl",
        method:"GET",
        data:{},
        success:function(data){
            mccmsa.getHtmlFromLocalTemplate({
                templateId: "listTemplate",
                container: $("#listContentContainer"),
                data: data
            });

            pageHtml(data);		//生成分页

            var tableMaxHeightELe = $('.table-maxHeight')[0];
            var scrollbarWidth = tableMaxHeightELe.offsetWidth - tableMaxHeightELe.scrollWidth;
            $("table:eq(0)").css("paddingRight",scrollbarWidth);    //获取滚动条宽度并赋值到表头padding-right，对齐
        }
    });
}

function backToList(){
    mccmsa.getHtmlFromLocalTemplate({
        templateId: "pathMain",
        container: $("#pathContainer")
    });

    $("#listContainer").show();
    $("#operationContainer").hide();
}

/* Template END */
/* Gary END */
/*jerry  start*/
function site() { 
    //添加自定义模块
    $(".select").change(function() {
        var option_val = $(".select option:selected").val(); //获取选中元素的值
        $("#" + option_val).parents(".row").fadeIn(); //显示自定义模块
    });
    //删除自定义模块
    $(".close-icon").on("click", function() {
        $(this).parents(".row").fadeOut();
    })
    mccmsa.ajax({
        url: "jsonData/siteInfo", //需要添加参数，看接口需要
        method: "GET",
        success: function(data) {
            mccmsa.getHtmlFromLocalTemplate({ //将数据打包到模版丢进容器
                templateId: "siteinfo",
                container: $("#form"),
                data: data
            });
        }
    })
}

/*加载互动管理 START*/
function friendly_link() {
    mccmsa.ajax({
        url: 'jsonData/friendly_link',
        method: "GET",
        success: function(data) {
            mccmsa.getHtmlFromLocalTemplate({ //加载导航栏
                templateId: "nav_location",
                container: $("#pathContainer")
            });
            mccmsa.getHtmlFromLocalTemplate({ //加载页面数据
                templateId: "getColumnTableData",
                container: $("#ColumnTableData"),
                data: data
            });
            $("#friendly_link").show();
            $("#onClickDataTable").hide();
            var scrollbarWidth = $('.table-maxHeight')[0].offsetWidth - $('.table-maxHeight')[0].scrollWidth;
            $("table:eq(0)").css("paddingRight", scrollbarWidth); //获取滚动条宽度并赋值到表头padding-right，对齐            
        }
    })
    laypage({
        cont: 'layPage', //容器。值支持id名、原生dom对象，jquery对象,
        pages: 10, //总页数
        skin: 'molv', //皮肤
        first: '首页', //若不显示，设置false即可
        last: '尾页', //若不显示，设置false即可
        prev: '<', //若不显示，设置false即可
        next: '>' //若不显示，设置false即可
    });
}
//新增链接
function new_link() {
    mccmsa.ajax({
        url: 'jsonData/new_friendly_link',
        method: "GET",
        success: function(data) {
            mccmsa.getHtmlFromLocalTemplate({ //加载导航栏
                templateId: "nav_location_return",
                container: $("#pathContainer")
            });
            mccmsa.getHtmlFromLocalTemplate({
                templateId: "new_link",
                container: $("#onClickDataTable"),
                data: data
            });
            $("#onClickDataTable").show();
            $("#friendly_link").hide();
        }
    })
}
//查看链接
function check_link(){
        mccmsa.ajax({
        url: 'jsonData/check_link',
        method: "GET",
        success: function(data) {
            mccmsa.getHtmlFromLocalTemplate({ //加载导航栏
                templateId: "nav_location_return",
                container: $("#pathContainer")
            });
            $("#true_location").text("查看链接");
            mccmsa.getHtmlFromLocalTemplate({
                templateId: "check_link",
                container: $("#onClickDataTable"),
                data: data
            });
            $("#onClickDataTable").show();
            $("#friendly_link").hide();
        }
    })
}

/*加载互动管理 END*/

/*jerry  finshed
/*jerry  start*/
 
function site(){
	$("#submitBtn").click(function(event) {
		event.preventDefault();

		var site = {
			name:$("#name").val(),
			link:$("#link").val(),
			title:$("#title").val(),
			index_name:$("#index_name").val(),
			port:$("#port").val(),
			company:$("#company").val(),
			copyright:$("#copyright").val(),
			icp:$("#icp").val()
		};
		alert(site);
		mccmsa.ajax({
			url: 'http://192.168.31.249:8180/galaxy_pcserver_crm/users/test',
			method: 'GET',
			data:site,
			success:function(data){
				var msg = data.msg;
				alert(msg)
			},
			error:(function() {
				alert("1111")
			})
		})

		
	});


}


/*jerry  finshed*/

function Test(){
    var submitBtn = $("#submitBtn");
 
    init();

    function init(){
        mccmsa.getHtmlFromLocalTemplate({
            templateId: "testLocalTemplate",
            container: $("#pathContainer"),
            data: {
                "path1": "基本配置",
                "path2": "站点配置",
                "array": [
                    {
                        "name": "tina"
                    },
                    {
                        "name": "alex"
                    }
                ]
            }
        });

        submitBtn.on("click", function(){
            alert("submit!");

            return false;
        });
    }

}

    /*单选框控制*/
function iCheck(){
        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });
    }
    /*单选框控制*/
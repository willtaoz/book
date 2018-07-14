<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>首页</title>
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-table.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
  	<!-- 查询区域 -->
  	<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
	  <div class="panel panel-default">
	    <div class="panel-heading" role="tab" id="headingOne">
	      <h4 class="panel-title">
	        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
	        	条件查询
	        </a>
	      </h4>
	    </div>
	    <div id="collapseOne" class="panel-collapse collapse " role="tabpanel" aria-labelledby="headingOne">
	      <div class="panel-body">
	       	<form class="form-inline" role="form">
			  <div class="form-group">
			    <label  for="bookname">书名:</label>
			    <input type="text" class="form-control" 
			    id="bookname" name="bookname" placeholder="输入书名">
			  </div>
		  	  <div class="form-group">
			    <label  for="caid">类别:</label>
			    <select  class="form-control" 
			    id="caid" name="caid" ></select>
			  </div>
		  	<button type="button" onclick="queryByConditions()" class="btn btn-default">查询</button>
		  	<button type="button" onclick="export2Excel()" class="btn btn-success">导出到EXCEL</button>
			</form>
	      </div>
	    </div>
	  </div>
  </div>
  	
  	<!-- 查询区域 end-->
   	<table id="tb"></table>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-table.js"></script>
     <script src="js/bootstrap-table-zh-CN.js"></script>
     <script>
     	$(function(){
     		//初始化下拉菜单
     		queryAllCas();
     		
     		queryData();
     	});
     	function queryByConditions(){
     		//让表格重新请求数据
     		tb.bootstrapTable("refresh");
     	}
     	/*
     		分页:
     				客户端分页--按需显示--假分页
     				服务器端分页--按需提取--真分页
     	*/
     	var start="";
     	var tb="";//表格
     	function queryData(){
     		tb = $("#tb").bootstrapTable({
     			onClickCell:function(field,e,f,xx){
     				alert("您点击:"+e+"了！！");
     			},
     			url:"book1",
     			pagination:true,//是否分页
     			pageSize:3,//每页显示记录数
     			sidePagination:"server",//服务器端分页
     			queryParamsType:"undefined",//limit参数是limit和offset undefined参数是:pageNumber和pageSize
     			queryParams:function(params){
     				console.log(params);
     				start = (params.pageNumber-1)*params.pageSize
     				var msg={
     					pageNumber:params.pageNumber,
     					pageSize:params.pageSize,
     					name:$("#bookname").val(),
     					caid:$("#caid").val()
     				};
     				return msg;
     			},
     			columns: [{
     		        field: 'id',
     		        title: '编号',
     		      	formatter:function(value,row,index){
    		        	return start+index+1;
    		        }
     		    }, {
     		        field: 'name',
     		        title: '书名'
     		    }, {
     		        field: 'price',
     		        title: '价格'
     		    },{
     		        field: 'author',
     		        title: '作者'
     		    },{
     		        field: 'status',
     		        title: '状态',
     		        formatter:function(value,row,index){
     		        	if("1"==value){
     		        		return "上架";
     		        	}else{
     		        		return "下架";
     		        	}
     		        }
     		    }, {
     		        field: 'publishdate',
     		        title: '出版日期'
     		    },{
     		        field: 'ca.name',
     		        title: '所在类别'
     		    },{
     		        title: '操作',
     		        formatter:function(value,row,index){
     		        	var info="<a id='showinfo' "+
     		        	" href='javascript:void(0)'>显示详情</a> | "+
     		        	"<a id='del' "+
     		        	" href='javascript:void(0)'>删除</a>"
     		        	return info;
     		        },
     		        events:etevents
     		    }]
     			
     		});
     	}
     	
     	window.etevents={
     			'click #showinfo':function(event,value,row,index){
     				alert("您要更新:"+row.name+"吗？");
     				//在这可以发送异步请求 控制 模态框的显示等等。。
     				
     				
     			}
     	}
      	function queryAllCas(){
    		$.ajax({
    			type:"post",
    			url:"book",
    			data:"method=queryAllCas",
    			dataType:"json",
    			success:function(msg){//msg-->List<Category>
    			//[{id:xx,name:xxx},{}]
    				console.log(msg);
    				var ops="<option value='-1'>==请选择==</option>";
    				$.each(msg,function(i,ca){
    					ops+="<option value='"+ca.id+"'>"+ca.name+"</option>";
    				});
    				$("#caid").html(ops);
    				$("#categoryid").html(ops);
    			}
    			
    		});
    	}
     </script>
  </body>
</html>



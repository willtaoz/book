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
	<table class="table table-stripted table-bordered table-hover
	table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>名字</th>
				<th>作者</th>
				<th>价格</th>
				<th>出版日期</th>
				<th>所在类别</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="book" ></tbody>
	</table>
	<div style="text-align:center">
		<button id='first' type='button' class='btn btn-primary'>首页</button>
		<button id='pre' type='button' class='btn btn-success'>上一页</button>
		<button id='next' type='button' class='btn btn-info'>下一页</button>
		<button id='end' type='button' class='btn btn-danger'>尾页</button>
	</div>
	<!-- 模态框 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="myModalLabel">书籍详情</h4>
	      </div>
	      <div class="modal-body">
	           	 <form action="book" method="POST" class="form-horizontal"
   	  role="form" enctype="multipart/form-data">
	 
		  <div class="form-group">
		    <label for="name" class="col-sm-2 control-label">书籍名字:</label>
		    <div class="col-sm-10">
		      <input type="text" 
		      class="form-control" id="name" name="name"
		       placeholder="请输入名字">
		    </div>
		  </div>
		  <!--作者  -->
		   <div class="form-group">
		    <label for="author" class="col-sm-2 control-label">作者:</label>
		    <div class="col-sm-10">
		      <input type="text" 
		      class="form-control" id="author" name="author"
		       placeholder="作者">
		    </div>
		  </div>
		  <!-- 价格 -->
		   <div class="form-group">
		    <label for="price" class="col-sm-2 control-label">价格:</label>
		    <div class="col-sm-10">
		      <input type="number" 
		      class="form-control" id="price" name="price">
		    </div>
		  </div>
		  <!-- 所在类别 -->
		   <div class="form-group">
		    <label for="categoryid" class="col-sm-2 control-label">所在类别:</label>
		    <div class="col-sm-10">
		      <select class="form-control" id="categoryid"
		       name="categoryid"></select>
		    </div>
		  </div>
		  <!-- 出版日期 -->
		   <div class="form-group">
		    <label for="publishdate" class="col-sm-2 control-label">出版日期:</label>
		    <div class="col-sm-10">
		      <input type="date" class="form-control"
		       id="publishdate" name="publishdate">
		    </div>
		  </div>
		  <!-- 状态 -->
		   <div class="form-group">
		    <label for="status" class="col-sm-2 control-label">状态:</label>
		    <div class="col-sm-10">
		      <select 
		      class="form-control" id="status" name="status">
		      	<option value="1">上架</option>
		      	<option value="0">下架</option>
		      </select>
		    </div>
		  </div>
		  <!-- 图片 -->
		  <div class="form-group">
		  	请选择:<input type="file" name="pic" id="pic"/>
		   	<button type='button' class="btn btn-primary"
		   	 onclick="addpic()">添加图片</button>
		    <div class="col-sm-10" >
		    	<div class="row"  id="pics">
		    	
		    	</div>
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
		      <button type="submit" class="btn btn-default">提交</button>
		    </div>
		  </div>
	</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary">Save changes</button>
	      </div>
	    </div>
	  </div>
	</div>
	<!-- modal end -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/ajaxfileupload.js"></script>
    <script>
    	function export2Excel(){
    		 window.location.href="book?method=export2Excel"+conds;
    	}
    	var pre,next,pageCount;
    	var conds="";//条件
    	var bookid="";//书籍id
    	$(function(){
    		//初始化下拉菜单
    		queryAllCas();
    		//
    		queryData(1,3,conds);
    		///~~~~~~添加分页事件
    		$("#first").on("click",function(){
    			queryData(1,3,conds);
    		});
    		$("#pre").on("click",function(){
    			queryData(pre,3,conds);
    		});
    		$("#next").on("click",function(){
    			queryData(next,3,conds);
    		});
    		$("#end").on("click",function(){
    			queryData(pageCount,3,conds);
    		});
    	});
    	function queryData(cp,ps,tj){
    		$.ajax({
    			type:"post",
    			url:"book",
    			data:"method=querySome&currentPage="+cp+"&pageSize="+ps+tj,
    			dataType:"json",
    			success:function(msg){//msg-->Page<Book>
    				console.log(msg);
    				pre = msg.pre;
    				next = msg.next;
    				pageCount = msg.pageCount;
    				var trs="";
    				$.each(msg.rows,function(i,book){
    					trs+="<tr><td>"+(msg.start+i+1)+"</td><td>"+book.name
    					+"</td><td>"+book.author+"</td><td>"+book.price
    					+"</td><td>"+book.publishdate
    					+"</td><td>"+book.ca.name
        				+"</td><td>"+(book.status=="0"?"下架":"上架") 
    				+"</td><td><button type='button' onclick=\"queryBookInfo('"+book.id+"')\" class='btn btn-primary'>显示详情</button></td></tr>";
    				});
    				$("#book").html(trs);
    			}
    		});
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
    	
    	//根据条件查询
    	function queryByConditions(){
    		//重置条件
    		conds="";
    		//组装条件
    		var name  = $("#bookname").val();
    		var caid = $("#caid").val();
    		if(name!=null && ""!=name){
    			conds+="&name="+name
    		}
    		if(caid!=null && caid!="-1"){
    			conds+="&caid="+caid;
    		}
    		//调用queryData方法 将条件传入到后台
    		queryData(1,3,conds);
    	}
    function queryBookInfo(id){
    	bookid=id;
    	$.post("book",//url
    			"method=queryBookById&id="+id,//参数
    			showbookinfo,//回掉
    			"json");
    }
    function showbookinfo(msg){//msg--->book
    	console.log(msg);
    	$("#name").val(msg.name);
    	$("#author").val(msg.author);
    	$("#price").val(msg.price);
    	$("#publishdate").val(msg.publishdate.substr(0,10));
    	$("#status").val(msg.status);
    	$("#categoryid").val(msg.categoryid);
    	var pics="";
    	$.each(msg.bps,function(i,bp){
    		pics+="<div class='col-sm-6 col-md-4'>"+
            "<div class='thumbnail'>"+
            
              "<img src='<%=path%>"+bp.savepath+"'>"+
              "<div class='caption'>"+
              	"<button type='button' onclick=\"setfm('"+bp.id+"')\" class='btn btn-primary' "+(bp.fm=="1"?"disabled":"")+">设置为封面</button>"+
              	"<button type='button' onclick=\"delpic('"+bp.id+"')\" class='btn btn-danger'>删除</button>"+
              "</div>"+
            "</div>"+
          "</div>";
	
    	});
    	   $("#pics").html(pics);	
    	
    	
    	$("#myModal").modal("show");
    }
    //删除图片
    function delpic(id){
    	$.post("book",
    			"method=delpic&id="+id,
    			function(msg){//{flag:"success"}
    				if(msg.flag=="success"){
    					//重新查询书籍信息
    					queryBookInfo(bookid);
    				}
    			},"json");
    }
  	//异步的文件上传功能
    function addpic(){
    	$.ajaxFileUpload({
    		type:"post",
    		url:"addpic",
    		fileElementId:"pic",//上传文件控件的id
    		data:{
    			"bookid":bookid
    		},
    		dataType:"json",
    		success:function(msg){
    			if(msg.flag=="success"){
    				queryBookInfo(bookid);
    			}
    		}
    	})
    }
  	function setfm(id){//id====bookpic.id
  		$.post("book",
    			"method=setfm&id="+id,
    			function(msg){//{flag:"success"}
    				if(msg.flag=="success"){
    					//重新查询书籍信息
    					queryBookInfo(bookid);
    				}
    			},"json");
  	}
    </script>
    
    
    
    
  </body>
  
  
  
  
  
</html>

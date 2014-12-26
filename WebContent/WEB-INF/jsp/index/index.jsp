<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<jsp:include page="/common/taglib.jsp" />
<html xml:lang="zh-CN" xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
	<title>智能温度控制系统</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="${basePath}static/css/manage.css" media="screen" rel="stylesheet" type="text/css" />
	<script src="${basePath}static/common/jquery-1.7.2.min.js" type="text/javascript" ></script>
	<script src="${basePath}static/common/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
	<script src="${basePath}static/common/Highcharts-4.0.3/js/highcharts.js" type="text/javascript" ></script>
</head>
<body>
	<div class="manage_container">
		<div class="manage_head">
			<div class="manage_logo"><a href="${basePath}">智能温度控制系统</a></div>
			<div id="nav"></div>
		</div>
		<div class="main">
			<div>
				<div style="float: left;">
					<form action="${basePath}" method="get" id="queryForm">
						<span style="font-size: 14px;font-weight: bold;">查询：</span>
						<input type="text" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" readonly="readonly" name="startDate" value="${startDate}"/>
						<span>至</span>
						<input type="text" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" readonly="readonly" name="endDate" value="${endDate}"/>
						<input type="submit" value="查询"/>
						<input type="button" value="清空" id="clearForm"/>
					</form>
				</div>
				<div style="float: right;">
					<input type="button" value="关闭继电器" id="openRelay"/>
					<input type="button" value="打开继电器" id="closeRelay"/>
				</div>
			</div>
			<div class="table_box">
				<table width="100%" align="center">
					<thead>
						<tr height="30" style="line-height: 30px;background-color: #040;">
							<th style="color: #fff;">温度</th>
							<th style="color: #fff;">湿度</th>
							<th style="color: #fff;">接收时间</th>
						</tr>
					</thead>
					<c:forEach var="data" items="${list}" varStatus="">
						<tr height="30" style="line-height: 30px">
							<td width="33%" <c:if test="${data.temperature > 35}">style="color:red;font-weight:bolder"</c:if>>${data.temperature}<c:if test="${data.temperature > 35}">(温度过高)</c:if></td>
							<td width="33%">${data.humidity}</td>
							<td width="34%">${data.receiveTime}</td>
						</tr>
					</c:forEach>
				</table>
				<div style="height: 30px;background: #f2f2f2"></div>
				<div id="container"></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function () {
		$("#openRelay").click(function(){
			$.post("${basePath}openRelay", function(){
				alert("继电器打开成功！");
			})
		});
		
		$("#closeRelay").click(function(){
			$.post("${basePath}closeRelay", function(){
				alert("继电器关闭成功！");
			})
		});
		
		$("#clearForm").click(function(){
			$("#queryForm").find("input:text").each(function(){
				$(this).val("");
			});
		});
		
	    $('#container').highcharts({
	        title: {
	            text: '智能温度控制系统',
	            x: -20 //center
	        },
	        subtitle: {
	            text: '温度变化折线图',
	            x: -20
	        },
	        xAxis: {
	            categories: [
	     					<c:forEach var="data" items="${list2}" varStatus="status">
	     						<c:if test='${status.last}'>'${data.receiveTime}'</c:if>
	     						<c:if test='${!status.last}'>'${data.receiveTime}',</c:if>
	     					</c:forEach>
	     					]
	        },
	        yAxis: {
	            title: {
	                text: '温度 (°C)'
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip: {
	            valueSuffix: '°C'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        series: [{
	            name: '温度',
	            data: [
    					<c:forEach var="data" items="${list2}" varStatus="status2">
    						<c:if test='${status2.last}'>${data.temperature}</c:if>
    						<c:if test='${!status2.last}'>${data.temperature},</c:if>
    					</c:forEach>
	                  ]
	        }]
	    });
	});
</script>
</html>

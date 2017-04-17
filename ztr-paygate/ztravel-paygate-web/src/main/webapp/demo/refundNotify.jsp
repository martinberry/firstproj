<%@page import="java.util.HashMap" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%
response.setCharacterEncoding("UTF-8");
request.setCharacterEncoding("UTF-8");
//进行支付结果的处理
Map<String,String[]> map = request.getParameterMap();
Map<String,String> values = new HashMap<String,String>();
for(String name : map.keySet()){
	String[] vs = map.get(name);
	if(vs.length > 0){
		values.put(name, vs[0]);
	} else {
		values.put(name, "");
	}
}
System.out.println("BG-REFUND-NOTIFY 网关返回信息 ::: "+values);
out.println("T");
%>
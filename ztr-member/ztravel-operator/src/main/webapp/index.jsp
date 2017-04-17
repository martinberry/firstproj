<%@page import="com.ztravel.common.util.WebEnv"%>
<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%
	String url = WebEnv.get("server.path.operaServer") + "/demo/view";
	response.sendRedirect(url);
%>
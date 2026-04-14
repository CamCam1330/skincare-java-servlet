<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title><c:out value="${title != null ? title : 'MyPham'}"/></title>

  <link rel="stylesheet" href="${ctx}/assets/css/bootstrap.min.css">
  <link rel="stylesheet" href="${ctx}/assets/css/app.css">
  <link rel="stylesheet" href="${ctx}/assets/css/site.css">

  <script defer src="${ctx}/assets/js/bootstrap.bundle.min.js"></script>
</head>
<body>

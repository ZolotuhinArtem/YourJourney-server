<%-- 
    Document   : test_upload
    Created on : Nov 19, 2017, 8:20:17 PM
    Author     : rtmss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<body>
    <h1>${message}</h1>
    <form method="POST" enctype="multipart/form-data" action="${s:mvcUrl('TUC#uploadPost').build()}">
        File to upload: <input type="file" name="image"><br/> 
        <input type="submit" value="Upload"> Press here to upload the file!
    </form>
</body>
</html>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="java.io.File" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %><%--
  Created by IntelliJ IDEA.
  User: whiterabbit.nsk
  Date: 08.10.2018
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String destination = ".../src/images";
    String destinationRealPath = application.getRealPath(destination);
    DiskFileItemFactory factory = new DiskFileItemFactory();
    factory.setSizeThreshold(1024);
    factory.setRepository(new File(destinationRealPath));
    ServletFileUpload uploader = new ServletFileUpload(factory);
    try {
        List items = uploader.parseRequest(request);
        Iterator iterator = items.iterator();
        while (iterator.hasNext()) {
            FileItem item = (FileItem) iterator.next();
            if (!item.isFormField()) {
                File file = new File(destinationRealPath, item.getName());
                item.write(file);
            } else {
                System.out.println(item.getName() + ": " + item.getFieldName());
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        /*request.getRequestDispatcher("/controller").forward(request, response);*/
    }
%>
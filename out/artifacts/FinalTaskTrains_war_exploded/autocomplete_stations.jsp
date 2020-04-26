<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="db.DBManager" %>
<%@ page import="db.entity.Station" %>
<%
    String query = request.getParameter("q");
    DBManager dbManager = DBManager.getInstance();
    List<Station> stations = dbManager.findAllStation();

    response.setHeader("Content-Type", "text/html");
    int max=1;
    for(int i=0;i<stations.size();i++) {
        if(stations.get(i).getName().toUpperCase().startsWith(query.toUpperCase())) {
            out.print(stations.get(i).getName()+"\n");
            if(max>=10)
                break;
            max++;
        }
    }
%>

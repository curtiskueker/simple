<%@ page import="org.curtis.model.Thing" %>
<%@ page import="java.util.List" %>
<%
    List<Thing> things = (List<Thing>)request.getAttribute("things");
    String errorMessage = (String)request.getAttribute("errorMessage");
%>
<html>
<body>
<table>
    <tr>
        <td><b>List Page</b></td>
    </tr>
    <%
        if(things != null) {
            for(Thing thing : things) {
    %>
    <tr>
        <td><%= thing.getName() %></td>
    </tr>
    <%
            }
        }
    %>
    <%
        if(errorMessage != null) {
    %>
    <tr>
        <td><%= errorMessage %></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>

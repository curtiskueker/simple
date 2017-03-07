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
        <td colspan="3"><b>List Page</b></td>
    </tr>
    <%
        if(errorMessage != null) {
    %>
    <tr>
        <td colspan="3"><%= errorMessage %></td>
    </tr>
    <%
        }
    %>
    <tr>
        <td colspan="3"><a href="/thing/new">New Thing</a></td>
    </tr>
    <%
        if(things != null) {
            for(Thing thing : things) {
    %>
    <tr>
        <td><%= thing.getName() %></td>
        <td><%= thing.getDescription() %></td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>

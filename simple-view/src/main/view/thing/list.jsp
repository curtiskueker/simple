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
        <td colspan="4"><b>List Page</b></td>
    </tr>
    <%
        if(errorMessage != null) {
    %>
    <tr>
        <td colspan="4"><%= errorMessage %></td>
    </tr>
    <%
        }
    %>
    <tr>
        <td colspan="4"><a href="/thing/update">New Thing</a></td>
    </tr>
    <%
        if(things != null) {
            for(Thing thing : things) {
    %>
    <tr>
        <td><%= thing.getName() %></td>
        <td><%= thing.getDescription() %></td>
        <td><a href="/thing/update?thingId=<%= thing.getId() %>">Update</a></td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>

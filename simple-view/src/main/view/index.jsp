<%@ page import="org.curtis.model.Thing" %>
<%@ page import="org.curtis.database.DBSessionFactory" %>
<%
    Thing thing = DBSessionFactory.getInstance().getTransaction().getObjectById(Thing.class, 1);
%>
<html>
<body>
<table>
    <tr>
        <td><b>Main Page</b></td>
    </tr>
    <tr>
        <td><%= thing.getName() %></td>
    </tr>
</table>
</body>
</html>
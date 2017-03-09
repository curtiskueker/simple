<%@ page import="org.curtis.model.Thing" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Thing thing = (Thing)request.getAttribute("thing");
    String errorMessage = (String)request.getAttribute("errorMessage");
%>
<html>
<head>
    <title>New Thing</title>
</head>
<body>
<table>
    <tr>
        <%
            if(thing.isPersisted()) {
        %>
        <td colspan="2"><b>Update Thing</b></td>
        <%
            } else {
        %>
        <td colspan="2"><b>New Thing</b></td>
        <%

            }
        %>
    </tr>
    <%
        if(errorMessage != null) {
    %>
    <tr>
        <td colspan="2"><%= errorMessage %></td>
    </tr>
    <%
        }
    %>
    <form method="post" action="/thing/update/<%= thing.getId() %>">
        <%
            if(thing.isPersisted()) {
        %>
        <tr>
            <td align="right">
                ID
            </td>
            <td align="left">
                <%= thing.getId() %>
            </td>
            <%
                }
            %>
        </tr>
        <tr>
            <td align="right">
                Name
            </td>
            <td align="left">
                <input type="text" name="name" value="<%= thing.getName() %>">
            </td>
        </tr>
        <tr>
            <td align="right">
                Description
            </td>
            <td align="left">
                <input type="text" name="description" value="<%= thing.getDescription() %>">
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td align="left">
                <%
                    if(thing.isPersisted()) {
                %>
                <input type="submit" value="Update Thing">
                <%
                } else {
                %>
                <input type="submit" value="Add Thing">
                <%

                    }
                %>
            </td>
        </tr>
        <input type="hidden" name="updateThing" value="true">
    </form>
</table>
</body>
</html>

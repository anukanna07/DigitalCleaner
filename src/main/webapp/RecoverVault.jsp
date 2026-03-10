<%@ page import="java.io.File" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>

<html>
<head>
<title>Recover Vault</title>

<style>
body{
    font-family:Arial;
    padding:30px;
    background:#111827;
    color:white;
}

table{
    width:100%;
    border-collapse:collapse;
}

th,td{
    padding:12px;
    border:1px solid #444;
    text-align:center;
}

.download-btn{
    background:#10b981;
    padding:6px 12px;
    border-radius:6px;
    color:white;
    text-decoration:none;
}

.delete-btn{
    background:#ef4444;
    border:none;
    padding:6px 12px;
    border-radius:6px;
    color:white;
    cursor:pointer;
}
</style>

</head>

<body>

<h2>Recover Vault</h2>

<table>

<tr>
<th>File Name</th>
<th>Size (KB)</th>
<th>Download</th>
<th>Delete Permanently</th>
</tr>

<%
String vaultPath = application.getRealPath("/RecoverVault");

File vault = new File(vaultPath);

File[] files = vault.listFiles();

if(files != null){
for(File f : files){
%>

<tr>

<td><%= f.getName() %></td>

<td><%= f.length()/1024 %></td>

<td>
<a class="download-btn"
   href="RecoverVault/<%= f.getName() %>"
   download>
Download
</a>
</td>

<td>
<form action="DeletePermanentServlet" method="post">
<input type="hidden" name="fileName" value="<%= f.getName() %>">

<button type="submit" class="delete-btn">
Delete Permanently
</button>

</form>
</td>

</tr>

<%
}
}
%>

</table>

</body>
</html>

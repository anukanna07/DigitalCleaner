<%@ page import="utils.FileInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="utils.RiskIndicator" %>

<%
List<FileInfo> files = (List<FileInfo>) request.getAttribute("files");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Duster Smart Results</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">

<style>

/* ===== BODY WITH FUTURISTIC BACKGROUND ===== */
body{
    font-family:'Poppins',sans-serif;
    margin:0;
    padding:30px;
    color:white;
    position:relative;
    overflow-x:hidden;
}

/* Background Image */
body::before{
    content:"";
    position:fixed;
    top:0;
    left:0;
    width:100%;
    height:100%;
    background:url("images/particle-bg.jpg") no-repeat center center;
    background-size:cover;
    z-index:-2;
}

/* Dark Overlay */
body::after{
    content:"";
    position:fixed;
    top:0;
    left:0;
    width:100%;
    height:100%;
    background:rgba(0,0,0,0.65);
    z-index:-1;
}

/* ===== HEADER ===== */
.header{
    display:flex;
    justify-content:space-between;
    align-items:center;
    margin-bottom:30px;
}

.grid{
    display:grid;
    grid-template-columns:repeat(auto-fill, minmax(250px, 1fr));
    gap:20px;
}

/* ===== GLASS CARD ===== */
.card{
    background:rgba(15,25,45,0.75);
    padding:15px;
    border-radius:15px;
    transition:0.3s;
    backdrop-filter:blur(8px);
    border:1px solid rgba(0,255,255,0.2);
    box-shadow:0 0 15px rgba(0,255,255,0.15);
}

.card:hover{
    transform:translateY(-6px);
    box-shadow:0 0 25px rgba(0,255,255,0.3);
}

.badge{
    display:inline-block;
    padding:4px 10px;
    border-radius:15px;
    font-size:12px;
    margin-top:8px;
}

.duplicate{ background:#dc2626; }
.old{ background:#f59e0b; }
.blurry{ background:#8b5cf6; }

.delete-btn{
    background:linear-gradient(45deg,#ff4b2b,#ff416c);
    border:none;
    padding:12px 25px;
    border-radius:25px;
    color:white;
    cursor:pointer;
    box-shadow:0 0 10px rgba(255,65,108,0.5);
}

.logout{
    text-decoration:none;
    color:white;
    border:1px solid rgba(255,255,255,0.6);
    padding:8px 15px;
    border-radius:20px;
    backdrop-filter:blur(6px);
}

/* ===== MODAL ===== */

.modal{
    display:none;
    position:fixed;
    top:0;
    left:0;
    width:100%;
    height:100%;
    background:rgba(0,0,0,0.75);
    justify-content:center;
    align-items:center;
}

.modal-content{
    background:#111827;
    padding:30px;
    border-radius:15px;
    text-align:center;
    width:350px;
    border:1px solid rgba(0,255,255,0.2);
}

.modal-buttons{
    margin-top:20px;
}

.confirm-btn{
    background:linear-gradient(45deg,#ff4b2b,#ff416c);
    border:none;
    padding:10px 20px;
    border-radius:20px;
    color:white;
    margin-right:10px;
    cursor:pointer;
}

.cancel-btn{
    background:gray;
    border:none;
    padding:10px 20px;
    border-radius:20px;
    color:white;
    cursor:pointer;
}

</style>
</head>

<body>

<div class="header">
    <h2>Duster Smart Scan Results</h2>
    <a href="LogoutServlet" class="logout">Logout</a>
</div>

<form action="CleanServlet" method="post">

<h3>Total Screenshots: <%= files != null ? files.size() : 0 %></h3>

<div class="grid">

<%
if(files != null){
for(FileInfo info : files){

String riskLevel = RiskIndicator.getRiskLevel(info.getFile().getName());

String badgeClass = "";
String badgeText = "";

if(info.isDuplicate()){
    badgeClass = "duplicate";
    badgeText = "Duplicate";
}
else if(info.isOldFile()){
    badgeClass = "old";
    badgeText = "Old File";
}
else if(info.isBlurry()){
    badgeClass = "blurry";
    badgeText = "Low Quality";
}
else{
    badgeText = "Risk: " + riskLevel;
}
%>

<div class="card">

<input type="checkbox"
       name="selectedFiles"
       value="<%= info.getFile().getName() %>"
       data-size="<%= info.getFile().length()/1024 %>"
       <%= (info.isDuplicate() || info.isOldFile()) ? "checked" : "" %> >

<p><strong><%= info.getFile().getName() %></strong></p>
<p><%= info.getFile().length()/1024 %> KB</p>

<% if(!badgeText.equals("")){ %>
<span class="badge <%= badgeClass %>"><%= badgeText %></span>
<% } %>

</div>
<%
}
}
%>

</div>

<br><br>

<button type="button" class="delete-btn" onclick="openModal()">
    Clean Selected Files
</button>

</form>

<!-- ===== MODAL ===== -->

<div id="confirmModal" class="modal">
    <div class="modal-content">
        <h3>Confirm Deletion</h3>
        <p id="fileCount"></p>
        <p id="spaceCount"></p>

        <div class="modal-buttons">
            <button onclick="submitForm()" class="confirm-btn">
                Confirm Delete
            </button>
            <button onclick="closeModal()" class="cancel-btn">
                Cancel
            </button>
        </div>
    </div>
</div>

<script>
function openModal(){

    const checkboxes = document.querySelectorAll('input[name="selectedFiles"]:checked');
    let totalSize = 0;

    checkboxes.forEach(cb => {
        totalSize += parseInt(cb.dataset.size);
    });

    if(checkboxes.length === 0){
        alert("Please select at least one file.");
        return;
    }

    document.getElementById("fileCount").innerText =
        "You are about to delete " + checkboxes.length + " files.";

    document.getElementById("spaceCount").innerText =
        "Total space to be freed: " + totalSize + " KB.";

    document.getElementById("confirmModal").style.display = "flex";
}

function closeModal(){
    document.getElementById("confirmModal").style.display = "none";
}

function submitForm(){
    document.forms[0].submit();
}
</script>

</body>
</html>
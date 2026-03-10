<%@ page contentType="text/html;charset=UTF-8" %>
<%
if(session == null || session.getAttribute("loggedIn") == null){
    response.sendRedirect("login.html");
    return;
}

String username = (String) session.getAttribute("username");

Integer totalDeleted = (Integer) session.getAttribute("totalDeleted");
Long totalSaved = (Long) session.getAttribute("totalSaved");

if(totalDeleted == null) totalDeleted = 0;
if(totalSaved == null) totalSaved = 0L;

/* Dynamic Optimization Percentage */
int percent = totalDeleted > 0 ? Math.min(100, totalDeleted * 5) : 20;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Duster - Dashboard</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap" rel="stylesheet">

<style>
*{
margin:0;
padding:0;
box-sizing:border-box;
font-family:'Poppins',sans-serif;
}

body{
background:
linear-gradient(rgba(10,15,30,0.75), rgba(10,15,30,0.75)),
url("images/bg.jpg");
background-size:cover;
background-position:center;
background-attachment:fixed;
color:white;
display:flex;
justify-content:center;
align-items:center;
height:100vh;
overflow:hidden;
animation:fadeIn 0.8s ease;
}

@keyframes fadeIn{
from{opacity:0;}
to{opacity:1;}
}

@keyframes floatCard{
0%{transform:translateY(0px);}
50%{transform:translateY(-12px);}
100%{transform:translateY(0px);}
}

.card{
background:rgba(255,255,255,0.08);
padding:45px;
border-radius:25px;
width:480px;
backdrop-filter:blur(12px);
border:1px solid rgba(255,255,255,0.15);
box-shadow:0 0 35px rgba(37,117,252,0.4);
animation:floatCard 5s ease-in-out infinite;
transition:0.4s;
}

h2{
text-align:center;
margin-bottom:8px;
}

.subtitle{
text-align:center;
font-size:14px;
opacity:0.8;
margin-bottom:25px;
}

.stats{
display:flex;
justify-content:space-between;
margin-bottom:25px;
}

.stat-box{
background:rgba(0,0,0,0.3);
padding:18px;
border-radius:15px;
width:48%;
text-align:center;
}

.stat-box h3{
font-size:22px;
color:#00ffcc;
margin-bottom:5px;
}

.stat-box p{
font-size:13px;
opacity:0.8;
}

hr{
border:0.5px solid rgba(255,255,255,0.2);
margin:20px 0;
}

.progress-container{
background:rgba(255,255,255,0.2);
border-radius:20px;
height:12px;
margin-top:10px;
overflow:hidden;
}

.progress-bar{
height:12px;
width:0%;
background:linear-gradient(90deg,#00ffcc,#2575fc);
border-radius:20px;
animation:loadBar 2s forwards;
}

@keyframes loadBar{
to{width:<%= percent %>%;}
}

.status{
margin-top:15px;
font-size:14px;
}

.activity{
margin-top:20px;
font-size:14px;
}

.activity ul{
margin-top:8px;
padding-left:18px;
}

.btn{
display:inline-block;
margin:15px 10px 0 10px;
padding:12px 26px;
border-radius:30px;
text-decoration:none;
color:white;
background:linear-gradient(45deg,#6a11cb,#2575fc);
transition:0.3s;
}

.btn:hover{
transform:scale(1.05);
box-shadow:0 0 20px rgba(37,117,252,0.7);
}
</style>
</head>

<body>

<div class="card">

<h2>Welcome, <%= username %> 👋</h2>
<p class="subtitle">Your system performance overview 🚀</p>

<div class="stats">
    <div class="stat-box">
        <h3><%= totalDeleted %></h3>
        <p>Files Deleted</p>
    </div>
    <div class="stat-box">
        <h3><%= totalSaved %> KB</h3>
        <p>Space Saved</p>
    </div>
</div>

<hr>

<div>
    <strong>System Status:</strong> 🟢 Optimized
    <div class="progress-container">
        <div class="progress-bar"></div>
    </div>
    <p style="font-size:12px; margin-top:5px;"><%= percent %>% Optimized</p>
</div>

<div class="activity">
    <strong>Recent Activity</strong>
    <ul>
        <li>System scanned successfully</li>
        <li>Junk files cleaned</li>
        <li>Performance improved</li>
    </ul>
</div>

<div style="text-align:center;">
<a href="dashboard.html" class="btn">Start Scan</a>
<a href="LogoutServlet" class="btn">Logout</a>
</div>

</div>

</body>
</html>
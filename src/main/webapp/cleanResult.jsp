<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cleanup Complete</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap" rel="stylesheet">

<style>

body{
    margin:0;
    font-family:'Poppins',sans-serif;
    color:white;
    display:flex;
    justify-content:center;
    align-items:center;
    height:100vh;
    overflow:hidden;
    background:url("images/particle-bg.jpg") no-repeat center center;
    background-size:cover;
}

@keyframes gradientMove{
    0%{background-position:0% 50%;}
    50%{background-position:100% 50%;}
    100%{background-position:0% 50%;}
}

.container{
    text-align:center;
    background:rgba(255,255,255,0.08);
    padding:50px;
    border-radius:20px;
    backdrop-filter:blur(12px);
    box-shadow:0 0 40px rgba(0,255,255,0.2);
    animation:fadeIn 1.2s ease;
}

@keyframes fadeIn{
    from{opacity:0; transform:translateY(30px);}
    to{opacity:1; transform:translateY(0);}
}

.success-icon{
    font-size:70px;
    color:#00ffcc;
    animation:pop 0.8s ease;
}

@keyframes pop{
    0%{transform:scale(0);}
    70%{transform:scale(1.2);}
    100%{transform:scale(1);}
}

h1{
    margin:15px 0;
    font-weight:700;
}

.stats{
    display:flex;
    justify-content:center;
    gap:30px;
    margin-top:30px;
}

.stat-card{
    background:rgba(0,0,0,0.3);
    padding:20px 30px;
    border-radius:15px;
    min-width:150px;
}

.stat-card h2{
    margin:0;
    font-size:28px;
    color:#00ffcc;
}

.stat-card p{
    margin:5px 0 0;
    font-size:14px;
    opacity:0.8;
}

.progress-bar{
    margin-top:30px;
    height:8px;
    background:rgba(255,255,255,0.2);
    border-radius:10px;
    overflow:hidden;
}

.progress-fill{
    height:100%;
    width:0%;
    background:linear-gradient(90deg,#00ffcc,#00aaff);
    animation:loadBar 2s forwards;
}

@keyframes loadBar{
    to{width:100%;}
}

.back-btn{
    margin-top:40px;
    padding:12px 30px;
    border-radius:30px;
    border:none;
    cursor:pointer;
    font-size:16px;
    color:white;
    background:linear-gradient(45deg,#00ffcc,#00aaff);
    transition:0.3s;
}

.back-btn:hover{
    transform:scale(1.05);
    box-shadow:0 0 15px rgba(0,255,255,0.5);
}

</style>
</head>

<body>

<div class="container">

<div class="success-icon">Progress</div>

<h1>Cleanup Completed Successfully!</h1>

<div class="stats">
    <div class="stat-card">
        <h2><%= request.getAttribute("deletedCount") %></h2>
        <p>Files Deleted</p>
    </div>

    <div class="stat-card">
        <h2><%= request.getAttribute("spaceSaved") %> KB</h2>
        <p>Space Recovered</p>
    </div>
</div>

<div class="progress-bar">
    <div class="progress-fill"></div>
</div>

<button class="back-btn" onclick="window.location.href='userDashboard.jsp' ">
    Back 
</button>

</div>

</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Conditional Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 50px;
        }
        .success {
            color: #27ae60;
            font-size: 24px;
        }
        .error {
            color: #e74c3c;
            font-size: 24px;
        }
    </style>
</head>
<body>
    <h1>Conditional Example</h1>
    <#if flag>
    <div class="success">
        <p>Flag is true</p>
    </div>
    <#else>
    <div class="error">
        <p>Flag is false</p>
    </div>
    </#if>
    <p><a href="/">Back to Home</a></p>
</body>
</html>

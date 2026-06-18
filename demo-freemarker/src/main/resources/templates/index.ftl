<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Freemarker Demo</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 50px;
        }
        h1 {
            color: #2c3e50;
        }
        .container {
            margin-top: 20px;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 8px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>${message}</h1>
        <p>This is a Freemarker template example</p>
        <p><a href="/user/cunyu1943">Go to User Page</a></p>
        <p><a href="/list">Go to List Page</a></p>
        <p><a href="/conditional/true">Go to Conditional Page (true)</a></p>
        <p><a href="/conditional/false">Go to Conditional Page (false)</a></p>
    </div>
</body>
</html>

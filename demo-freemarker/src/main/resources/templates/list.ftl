<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>List Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 0 auto;
            padding: 50px;
        }
        h1 {
            color: #27ae60;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            padding: 10px;
            margin: 5px 0;
            background-color: #ecf0f1;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <h1>Technology List</h1>
    <ul>
        <#list items as item>
        <li>${item}</li>
        </#list>
    </ul>
    <p><a href="/">Back to Home</a></p>
</body>
</html>

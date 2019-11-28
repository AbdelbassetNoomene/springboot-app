<!doctype html>
<html lang="fr-FR">
<head class="v_scrollbar">
    <meta charset="UTF-8"/>
    <title>List Users</title>
<style>

    .image-logo img{
        height: 45px;
        background-image:url("spring-boot-logo.png"); ;
    }
</style>
</head>
<body>
<div class="container">
    <div class="main">
        <div class="image-logo">
            <img src="spring-boot-logo.png" class="rounded float-right" />
        </div>
        <div class="panel panel-primary">
            <div class="panel-heading">List Users</div>
            <div class="panel-body">
                <div class="form-table">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Lastname</th>
                            <th>Email</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list users as user>
                            <tr>
                                <td>${user.id}</td>
                                <td>${user.firstname}</td>
                                <td>${user.lastname}</td>
                                <td>${user.email}</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

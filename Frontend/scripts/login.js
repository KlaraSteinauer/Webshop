
$(document).ready(function () {
    class UserLogin {
        constructor(username, password) {
            this.username = username;
            this.password = password
        }
    }

    function setLoginData() {
        let userData = {
            "username": $('#floatingInput').val(),
            "password": $('#floatingPassword').val()
        }

        return new UserLogin(userData.username, userData.password)
    }

    $('#loginButton').click(function (e) {
        e.preventDefault();
        let user = setLoginData();

        $.ajax({
            url: 'http://localhost:8080/login',
            method: 'POST',
            data: JSON.stringify(user),
            contentType: 'application/json',
            dataType: 'text',
            success: (response) => {
                const accessToken = response;
                localStorage.setItem('accessToken', accessToken);
                console.log(localStorage.getItem("accessToken"))
                isAdmin();
            },
            error: (err) => {
                console.error(err, "Login fehlgeschlagen!");
            }
        });
    });

    function isAdmin() {
        $.ajax({
            url: 'http://localhost:8080/isAdmin',
            method: 'GET',
            headers: 
            { 
                "Authorization": localStorage.getItem("accessToken")
            },
            contentType: 'application/json',
            success: function () {
                if (isAdmin) {
                    $('#admin-link').attr('href', '/admin.html');
                    $('#admin-link').attr('style', 'display: block');
                    location.href = "admin.html"

                } else {
                    console.log(localStorage.getItem("accessToken"))
                    location.href = "home.html"
                }
            },
            error: (err) => {
                console.log(localStorage.getItem("accessToken"))
                console.error(err, "Adminrechte benötigt!")
            }

        })
    }


})
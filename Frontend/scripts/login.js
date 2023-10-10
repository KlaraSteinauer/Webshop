$(document).ready(function () {

    class UserLogin {
        constructor(username, password) {
            this.username = username;
            this.password = password
        }
    }

    function setLoginData() {
        let userData = {
            "username": $('#userName').val(),
            "password": $('#userPassword').val()
        }

        return new UserLogin(userData.username, userData.password)
    }

    enableButtons();

    //TODO implement page relaod 
    $('#showLogout').click(function (e) {
        localStorage.removeItem('accessToken');
    })

    function enableButtons() {
        if (localStorage.getItem("accessToken") != null) {
            $('#showLogout').attr('style', 'display: block');
            $('#showLogin').attr('style', 'display: none');
            $('#showLoginIcon').attr('style', 'display: none');
        } else {
            $('#showLogout').attr('style', 'display: none');
            $('#showLogin').attr('style', 'display: block');
            $('#showLoginIcon').attr('style', 'display: block');
        }
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
            success: function (response) {
                if (response) {
                    location.href = "admin.html"
                    $('#admin-link').attr('href', '/admin.html');
                    $('#admin-link').attr('style', 'display: block');
                } else {
                    location.href = "home.html"
                }
            },
            error: (err) => {
                console.log("Anmeldung fehlgeschlagen!")
                location.href = "home.html"
            }
        })
    }
})
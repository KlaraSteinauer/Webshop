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

    function logout() {
        localStorage.removeItem('accessToken');
    }

    function enableButtons(){
        $('#showLogout').attr('style', 'display: block');
        $('#showLogin').attr('style', 'display: none');
        $('#showLoginIcon').attr('style', 'display: none');   
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
                    enableButtons();
                } else {
                    location.href = "home.html"
                    enableButtons();
                }
            },
            error: (err) => {
                console.log("Anmeldung fehlgeschlagen!")
                location.href = "home.html"
            }
        })
    }
})

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
            dataType: 'json',
            success: (response) => {
                const accessToken = response.accessToken;
                localStorage.setItem('accessToken', accessToken);
            },
            error: (err) => {
                console.error(err, "Login fehlgeschlagen!");
            }
        });
    });

    $.ajax({
        url: 'http://localhost:8080/isAdmin',
        method: 'GET',
        data: JSON.stringify(accessToken),
        contentType: 'application/json',
        success: function () {
            if (isAdmin) {
                $('#admin-link').attr('href', 'admin.html');
                $('#admin-link').attr('href', 'checkout.html');
                $('#admin-link').attr('href', 'help.html');
                $('#admin-link').attr('href', 'home.html');
                $('#admin-link').attr('href', 'impressum.html');
                $('#admin-link').attr('href', 'login.html');
                $('#admin-link').attr('href', 'products.html');
                $('#admin-link').attr('href', 'registration.html');
            } else {
                $('#user-link').attr('href', 'checkout.html');
                $('#user-link').attr('href', 'help.html');
                $('#user-link').attr('href', 'home.html');
                $('#user-link').attr('href', 'impressum.html');
                $('#user-link').attr('href', 'login.html');
                $('#user-link').attr('href', 'products.html');
                $('#user-link').attr('href', 'registration.html');
            }
        },
        error: (err) => {
            console.error(err, "Adminrechte benötigt!")
        }

    })

})


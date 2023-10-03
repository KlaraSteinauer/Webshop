
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
                location.href = "home.html"
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
                $('#admin-link').attr('href', '/admin.html');
                $('#admin-link').attr('style', 'display: block');

            }
        },
        error: (err) => {
            console.error(err, "Adminrechte benötigt!")
        }

    })

})


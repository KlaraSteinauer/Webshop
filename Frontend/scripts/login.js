
$(document).ready(function () {
    class UserLogin {
        constructor(username, password) {
            this.username = username;
            this.password = password
        }
    }

    function setLoginData (){
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
                if (accessToken.role === 'ADMIN') {
                    window.location.href = 'src/admin.html';
                } else if (accessToken.role === 'CUSTOMER') {
                    window.location.href = 'src/home.html';
                }
            },
            error: (err) => {
                console.error(err, "Login fehlgeschlagen!");
            }
        });
    });
    
})


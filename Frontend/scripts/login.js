
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
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            success: (response) => {
                const accessToken = response.accessToken;
                localSotrage.getItem(accessToken),
                    window.location.href = '/admin';
            },
            error: (err) => {
                console.error("Login fehlgeschlagen!")
            }
        })
    })
})
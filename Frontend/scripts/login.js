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

    const userNameInput = $("#userName");
    const userPasswordInput = $("#userPassword");
    const loginButton = $("#loginButton");

    userNameInput.on("input", toggleButtonState);
    userPasswordInput.on("input", toggleButtonState);

    function toggleButtonState() {
        if (userNameInput.val() !== "" && userPasswordInput.val() !== "") {
            loginButton.removeClass("disabled");
        } else {
            loginButton.addClass("disabled");
        }
    }

    $('#showLogout').click(function (e) {
        sessionStorage.removeItem('accessToken');
        sessionStorage.removeItem('currentUser');
        sessionStorage.removeItem('cartItems');
        window.location.href = 'home.html';
        //window.location.reload()
    })

    function enableButtons() {
        if (sessionStorage.getItem("accessToken") != null) {
            $('#showLogout').attr('style', 'display: block');
            $('#showLogin').attr('style', 'display: none');
            $('#showLoginIcon').attr('style', 'display: none');
            $('#showRegistration').attr('style', 'display: none');
        } else {
            $('#showLogout').attr('style', 'display: none');
            $('#showLogin').attr('style', 'display: block');
            $('#showLoginIcon').attr('style', 'display: block');
            $('#showRegistration').attr('style', 'display: block');
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
                sessionStorage.setItem('accessToken', accessToken);
                sessionStorage.setItem('currentUser', user.username);
                isAdmin();
            },
            error: (err) => {
                $('#alertModal').modal('show');
                $('#alertModalText').text("Login fehlgeschlagen. Bitte überprüfen Sie nochmals ihre Logindaten!")
            }
        });
    });

    $('#currentUser').text(sessionStorage.getItem("currentUser"))
    $('#itemsInShoppingCardL').text(sessionStorage.getItem("cartItems"));
    $('#itemsInShoppingCardS').text(sessionStorage.getItem("cartItems"));

    function isAdmin() {
        $.ajax({
            url: 'http://localhost:8080/isAdmin',
            method: 'GET',
            headers:
            {
                "Authorization": sessionStorage.getItem("accessToken")
            },
            contentType: 'application/json',
            success: function (response) {
                if (response) {
                    location.href = "admin.html"
                } else {
                    location.href = "home.html"
                }
            },
            error: (err) => {
                alert("isAdmin fehlgeschlagen!")
                location.href = "home.html"
            }
        })
    }
})
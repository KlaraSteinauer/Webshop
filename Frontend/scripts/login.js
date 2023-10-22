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
        localStorage.removeItem('accessToken');
        localStorage.removeItem('currentUser');
        localStorage.removeItem('cartItems');
        window.location.href = 'home.html';
        //window.location.reload()
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
                localStorage.setItem('currentUser', user.username);
                isAdmin();
            },
            error: (err) => {
                $('#alertModal').modal('show');
                $('#alertModalText').text("Login fehlgeschlagen. Bitte überprüfen Sie nochmals ihre Logindaten!")
            }
        });
    });

    $('#currentUser').text(localStorage.getItem("currentUser"))
    $('#itemsInShoppingCardL').text(localStorage.getItem("cartItems"));
    $('#itemsInShoppingCardS').text(localStorage.getItem("cartItems"));

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
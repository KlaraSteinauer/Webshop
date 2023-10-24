$(document).ready(function () {
    //logic to get the userId from currentUser out of the token
    const tokenBearer = sessionStorage.getItem("accessToken") || '';
    let token = '';

    if (tokenBearer.startsWith('Bearer ')) {
        token = tokenBearer.substring(7); // Remove "Bearer " from the beginning
    }

    if (token.length > 0) {
        try {
            jwt = JSON.parse(atob(token.split('.')[1]));
            currentUserRole = jwt.role;
        } catch (e) {
            console.log('error: ' + e);
        }
    }

    function showAdminButtons() {
        if (currentUserRole === "ADMIN") {
            $('#showAdminPage').attr('style', 'display: block');
        } else {
            $('#showAdminPage').attr('style', 'display: none');
        }
    }

    showAdminButtons()

})
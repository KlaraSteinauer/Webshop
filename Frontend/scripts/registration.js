$(document).ready(function () {
    //User 
    class User {
        constructor(userName, userPassword, eMail, gender, firstName, lastName, address) {
            this.userName = userName;
            this.userPassword = userPassword;
            this.eMail = eMail;
            this.gender = gender;
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
        }
    }

    //Address
    class Address {
        constructor(street, number, zip, city, country) {
            this.street = street;
            this.number = number;
            this.zip = zip;
            this.city = city;
            this.country = country;
        }
    }

    //TODO find out why email request is null in backend??
    function createNewUser() {
        let registrationData = {
            "gender": $('#Anrede').val(),
            "firstName": $('#Firstname').val(),
            "lastName": $('#Lastname').val(),
            "userEmail": $('#Email').val(),
            "userName": $('#Username').val(),
            "userPassword": $('#Password').val(),
        };

        let addressData = {
            "street": $('#Street').val(),
            "number": $('#Number').val(),
            "zip": $('#PLZ').val(),
            "city": $('#City').val(),
            "country": $('#Country').val(),
        };

        let userAddress = new Address(addressData.street, addressData.number, addressData.zip, addressData.city, addressData.country)
        return new User (registrationData.userName, registrationData.userPassword, registrationData.userEmail, registrationData.gender, registrationData.firstName, registrationData.lastName, userAddress)
    }

    $("#registration").on("click", function (event) {
        event.preventDefault();

        let newUser = createNewUser();
        console.log(newUser)

        $.ajax({
            url: 'http://localhost:8080/user/add',
            method: "POST",
            contentType: 'application/json',
            data: JSON.stringify(newUser),
            success: (response) => {
                const accessToken = response.accessToken;
                localStorage.setItem('accessToken', accessToken); 
                if (accessToken.role === 'ADMIN') {
                    window.location.href = 'src/admin.html';
                } else if (accessToken.role === 'CUSTOMER') {
                    window.location.href = 'src/home.html';
                }
            },
            error: function (error) {
                console.log("Error: " + JSON.stringify(error));
            }
        });
    });
});






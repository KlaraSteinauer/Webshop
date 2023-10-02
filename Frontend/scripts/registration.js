$(document).ready(function () {
    //User 
    class User {
        constructor(userName, userPassword, eMail, gender, firstName, lastName) {
            this.userName = userName;
            this.userPassword = userPassword;
            this.eMail = eMail;
            this.gender = gender;
            this.firstName = firstName;
            this.lastName = lastName;
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

    let addressData = {
        "street": $('#Street').val(),
        "number": $('#Number').val(),
        "plz": $('#PLZ').val(),
        "city": $('#City').val(),
        "country": $('#Country').val(),
    };

    function createNewUser() {
        let registrationData = {
            "gender": $('#Anrede').val(),
            "firstName": $('#Firstname').val(),
            "lastName": $('#Lastname').val(),
            "eMail": $('#Email').val(),
            "userName": $('#Username').val(),
            "userPassword": $('#Password').val(),
        };
        return new User (registrationData.userName, registrationData.userPassword, registrationData.eMail, registrationData.gender, registrationData.firstName, registrationData.lastName)
    }

    $("#registration").on("click", function (event) {
        event.preventDefault();

        let newUser = createNewUser();

        $.ajax({
            url: 'http://localhost:8080/user/add',
            method: "POST",
            contentType: 'application/json',
            data: JSON.stringify(newUser),
            success: function (response) {
                console.log("User wurde erfolgreich hinzugefügt!");
            },
            error: function (error) {
                console.log("Error: " + JSON.stringify(error));
            }
        });
    });
});






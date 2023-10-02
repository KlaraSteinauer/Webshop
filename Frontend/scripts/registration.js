$(document).ready(function () {

    //User Role {ANONYMOUS(1), CUSTOMER(2), ADMIN(3);}
    class User {
        constructor(userName, userPassword, eMail, gender, firstname, lastname, address) {
            this.userName = userName;
            this.userPassword = userPassword;
            this.eMail = eMail;
            this.gender = gender;
            this.firstname = firstname;
            this.lastname = lastname;
            this.address = address
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

    function createNewUser() {
        let registrationData = {
            "userName": $('#Username').val(),
            "userPassword": $('#Password').val(),
            "eMail": $('#Email').val(),
            "role": $('#username').val(),
            "gender": $('#Anrede option:selected').val(),
            "firstname": $('#Firstname').val(),
            "lastname": $('#Lastname').val(),
        }

        let registrationAddress = {
            "street": $('#Street').val(),
            "number": $('#Number').val(),
            "zip": $('#PLZ').val(),
            "city": $('#City').val(),
            "country": $('#Country').val(),
        }

        let address = new Address(registrationAddress.street, registrationAddress.number, registrationAddress.zip, registrationAddress.city, registrationAddress.country)
        let user = new User(registrationData.userName, registrationData.userPassword, registrationData.eMail, registrationData.gender, registrationData.firstname, registrationData.lastname, address)

        return user;
    }

    $("#registration").on("click", _e => {
        let newUser = createNewUser();
        $.ajax({
            url: 'http://localhost:8080/user/add',
            method: "POST",
            contentType: 'application/json',
            data: JSON.stringify(newUser),
            success: console.log("User wurde erfolgreich hinzugefügt!"),
            error: function (error) {
                console.log("Error: " + error);
            }
        });
    });



})
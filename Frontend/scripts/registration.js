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

    function validateForm() {
        let registrationData = {
            "gender": $('#Anrede').val(),
            "firstName": $('#Firstname').val(),
            "lastName": $('#Lastname').val(),
            "userEmail": $('#Email').val(),
            "userName": $('#Username').val(),
            "userPassword": $('#Password').val(),
            "repeatPassword": $('#RepeatPassword').val()
        };
        if (!isValidRegistrationData(registrationData)) {
            alert("Registration data is not valid.");
            return false;
        } 

        let addressData = {
            "street": $('#Street').val(),
            "number": $('#Number').val(),
            "zip": $('#PLZ').val(),
            "city": $('#City').val(),
            "country": $('#Country').val(),
        };

        if (!isValidAddressData(addressData)) {
            alert("Address data is not valid.");
            return false;
        } 

        return true
    }

    function isValidRegistrationData(data) {
        const namePattern = /^[A-Za-zÖÄÜöäü]+$/;
        if (!data.gender) {
            $('#Anrede').addClass('invalid-input-value');
            $('#Anrede').siblings('.invalid-input').show();
        } else {
            $('#Anrede').removeClass('invalid-input-value');
            $('#Anrede').siblings('.invalid-input').hide();
        }

        if (!data.firstName.match(namePattern)) {
            $('#Firstname').addClass('invalid-input-value');
            $('#Firstname').siblings('.invalid-input').show();
        } else {
            $('#Firstname').removeClass('invalid-input-value');
            $('#Firstname').siblings('.invalid-input').hide();
        }

        if (!data.lastName.match(namePattern)) {
            $('#Lastname').addClass('invalid-input-value');
            $('#Lastname').siblings('.invalid-input').show();
        } else {
            $('#Lastname').removeClass('invalid-input-value');
            $('#Lastname').siblings('.invalid-input').hide();
        }

        if (!data.userName) {
            $('#Username').addClass('invalid-input-value');
            $('#Username').siblings('.invalid-input').show();
        } else {
            $('#Username').removeClass('invalid-input-value');
            $('#Username').siblings('.invalid-input').hide();
        }

        if (!data.userPassword) {
            $('#Password').addClass('invalid-input-value');
            $('#Password').siblings('.invalid-input').show();
        } else {
            $('#Password').removeClass('invalid-input-value');
            $('#Password').siblings('.invalid-input').hide();
        }

        if (!data.repeatPassword) {
            $('#RepeatPassword').addClass('invalid-input-value');
            $('#RepeatPassword').siblings('.invalid-input').show();
        } else {
            $('#RepeatPassword').removeClass('invalid-input-value');
            $('#RepeatPassword').siblings('.invalid-input').hide();
        }

        if (data.userPassword !== data.repeatPassword) {
            alert("Password and Repeat Password do not match.");
        }

        const emailPattern = /^[a-zA-Z0-9._-ÖÄÜöäü]+@[a-zA-Z0-9.-ÖÄÜöäü]+\.[a-zA-Z]{2,4}$/;
        if (!emailPattern.test(data.userEmail)) {
            $('#Email').addClass('invalid-input-value');
            $('#Email').siblings('.invalid-input').show();
        } else {
            $('#Email').removeClass('invalid-input-value');
            $('#Email').siblings('.invalid-input').hide();
        }

        return true;
    }

    function isValidAddressData(data) {
        const streetPattern = /^[A-Za-zÖÄÜöäü]+$/;
        if (!data.street.match(streetPattern)) {
            $('#Street').addClass('invalid-input-value');
            $('#Street').siblings('.invalid-input').show();
        } else {
            $('#Street').removeClass('invalid-input-value');
            $('#Street').siblings('.invalid-input').hide();
        }

        const numberPattern = /\d+/;
        if (!data.number.match(numberPattern)) {
            $('#Number').addClass('invalid-input-value');
            $('#Number').siblings('.invalid-input').show();
        } else {
            $('#Number').removeClass('invalid-input-value');
            $('#Number').siblings('.invalid-input').hide();
        }

        const zipPattern = /^\d{4,}$/;
        if (!data.zip.match(zipPattern)) {
            $('#PLZ').addClass('invalid-input-value');
            $('#PLZ').siblings('.invalid-input').show();
        } else {
            $('#PLZ').removeClass('invalid-input-value');
            $('#PLZ').siblings('.invalid-input').hide();
        }

        const cityPattern = /^[A-Za-zÖÄÜöäü]+$/;
        if (!data.city.match(cityPattern)) {
            $('#City').addClass('invalid-input-value');
            $('#City').siblings('.invalid-input').show();
        } else {
            $('#City').removeClass('invalid-input-value');
            $('#City').siblings('.invalid-input').hide();
        }

        const countryPattern = /^[A-Za-zÖÄÜöäü]+$/;
        if (!data.country.match(countryPattern)) {
            $('#Country').addClass('invalid-input-value');
            $('#Country').siblings('.invalid-input').show();
        } else {
            $('#Country').removeClass('invalid-input-value');
            $('#Country').siblings('.invalid-input').hide();
        }

        return true;
    }

    function createNewUser() {
        let registrationData = {
            "gender": $('#Anrede').val(),
            "firstName": $('#Firstname').val(),
            "lastName": $('#Lastname').val(),
            "userEmail": $('#Email').val(),
            "userName": $('#Username').val(),
            "userPassword": $('#Password').val(),
            "repeatPassword": $('#RepeatPassword').val()
        };
    
        let addressData = {
            "street": $('#Street').val(),
            "number": $('#Number').val(),
            "zip": $('#PLZ').val(),
            "city": $('#City').val(),
            "country": $('#Country').val(),
        }

        let userAddress = new Address(addressData.street, addressData.number, addressData.zip, addressData.city, addressData.country)
        return new User(registrationData.userName, registrationData.userPassword, registrationData.userEmail, registrationData.gender, registrationData.firstName, registrationData.lastName, userAddress)
    }

    $("#registration").on("click", function (event) {
        event.preventDefault();

        if (validateForm()) {
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
                    alert("Registrierung erfolgreich!")
                },
                error: function (error) {
                    console.log("Error: " + JSON.stringify(error));
                }
            });
        } else {
            alert("Registrierung fehlgeschlagen!")
        }

    });

    $("#reset").on("click", function (event) {
        event.preventDefault();
        $(this).closest('form').find("input[type=text], textarea").val("");
        $(this).closest('form').find("select").val(function () {
            return $(this).find("option[value='default']").text();
        });                
    });
});






$(document).ready(function () {

    //User 
    class User {
        constructor(userName, userPassword, userEmail, gender, firstName, lastName, address) {
            this.userName = userName;
            this.userPassword = userPassword;
            this.userEmail = userEmail;
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
            "repeatPassword": $('#RepeatPassword').val(),
            "acceptedAGB": $('#acceptedAGB')
        };
        let addressData = {
            "street": $('#Street').val(),
            "number": $('#Number').val(),
            "zip": $('#PLZ').val(),
            "city": $('#City').val(),
            "country": $('#Country').val(),
        };
        isValidAddressData(addressData);
        isValidRegistrationData(registrationData);

        if (!isValidAddressData(addressData) || !isValidRegistrationData(registrationData)) {
            $('#alertModal').modal('show');
            $('#alertModalText').text("Validierung fehlgeschlagen! Bitte Eingabedaten nochmals überprüfen.")
            return false;
        }
        if (
            !addressData.street ||
            !addressData.number ||
            !addressData.zip ||
            !addressData.city ||
            !addressData.country
        ) {
            $('#alertModal').modal('show');
            $('#alertModalText').text("Bitte füllen Sie alle erforderlichen Felder aus.");
            return false;
        }
        if (
            !registrationData.gender ||
            !registrationData.firstName ||
            !registrationData.lastName ||
            !registrationData.userName ||
            !registrationData.userPassword ||
            !registrationData.repeatPassword ||
            !registrationData.userEmail ||
            !registrationData.acceptedAGB
        ) {
            $('#alertModal').modal('show');
            $('#alertModalText').text("Bitte füllen Sie alle erforderlichen Felder aus.");
            return false;
        }
        return true;
    }

    function isValidRegistrationData(data) {
        let isValid = true;
        const namePattern = /^[A-Za-zÖÄÜöäü]+$/;
        if (!data.gender === null || data.gender === 'default') {
            $('#Anrede').addClass('invalid-input-value');
            $('#Anrede').siblings('.invalid-input').show();
            isValid = false;
        } else {
            $('#Anrede').removeClass('invalid-input-value');
            $('#Anrede').siblings('.invalid-input').hide();
        }
        if (!data.firstName.match(namePattern)) {
            $('#Firstname').addClass('invalid-input-value');
            $('#Firstname').siblings('.invalid-input').show();
            isValid = false;
        } else {
            $('#Firstname').removeClass('invalid-input-value');
            $('#Firstname').siblings('.invalid-input').hide();
        }
        if (!data.lastName.match(namePattern)) {
            $('#Lastname').addClass('invalid-input-value');
            $('#Lastname').siblings('.invalid-input').show();
            isValid = false;
        } else {
            $('#Lastname').removeClass('invalid-input-value');
            $('#Lastname').siblings('.invalid-input').hide();
        }
        if (!data.userName) {
            $('#Username').addClass('invalid-input-value');
            $('#Username').siblings('.invalid-input').show();
            isValid = false;
        } else {
            $('#Username').removeClass('invalid-input-value');
            $('#Username').siblings('.invalid-input').hide();
        }
        if (!data.userPassword) {
            $('#Password').addClass('invalid-input-value');
            $('#Password').siblings('.invalid-input').show();
            isValid = false;
        } else {
            $('#Password').removeClass('invalid-input-value');
            $('#Password').siblings('.invalid-input').hide();
        }
        if (!data.repeatPassword) {
            $('#RepeatPassword').addClass('invalid-input-value');
            $('#RepeatPassword').siblings('.invalid-input').show();
            isValid = false;
        } else {
            $('#RepeatPassword').removeClass('invalid-input-value');
            $('#RepeatPassword').siblings('.invalid-input').hide();
        }
        if (data.userPassword !== data.repeatPassword) {
            $('#alertModal').modal('show');
            $('#alertModalText').text("Password and Repeat Password do not match.")
            isValid = false;
        }
        const emailPattern = /^[a-zA-Z0-9._-ÖÄÜöäü]+@[a-zA-Z0-9.-ÖÄÜöäü]+\.[a-zA-Z]{2,4}$/;
        if (!data.userEmail.match(emailPattern)) {
            $('#Email').addClass('invalid-input-value');
            $('#Email').siblings('.invalid-input').show();
            isValid = false;
        } else {
            $('#Email').removeClass('invalid-input-value');
            $('#Email').siblings('.invalid-input').hide();
        }
        if (!data.acceptedAGB.is(':checked')) {
            $('#acceptedAGB').addClass('invalid-input-value');
            $('#acceptedAGB').siblings('.invalid-input').show();
            isValid = false;
        } else {
            $('#acceptedAGB').removeClass('invalid-input-value');
            $('#acceptedAGB').siblings('.invalid-input').hide();
        }

        return isValid;
    }

    function isValidAddressData(data) {
        let isValid = true;
        const streetPattern = /^[A-Za-zÖÄÜöäü\s]+$/;
        if (!data.street.match(streetPattern)) {
            $('#Street').addClass('invalid-input-value');
            $('#Street').siblings('.invalid-input').show();
            isValid = false;
        } else {
            $('#Street').removeClass('invalid-input-value');
            $('#Street').siblings('.invalid-input').hide();
        }
        const numberPattern = /\d+/;
        if (!data.number.match(numberPattern)) {
            $('#Number').addClass('invalid-input-value');
            $('#Number').siblings('.invalid-input').show();
            isValid = false;
        } else {
            $('#Number').removeClass('invalid-input-value');
            $('#Number').siblings('.invalid-input').hide();
        }
        const zipPattern = /^\d{4,6}$/;
        if (!data.zip.match(zipPattern)) {
            $('#PLZ').addClass('invalid-input-value');
            $('#PLZ').siblings('.invalid-input').show();
            isValid = false;
        } else {
            $('#PLZ').removeClass('invalid-input-value');
            $('#PLZ').siblings('.invalid-input').hide();
        }
        const cityPattern = /^[A-Za-zÖÄÜöäü]+$/;
        if (!data.city.match(cityPattern)) {
            $('#City').addClass('invalid-input-value');
            $('#City').siblings('.invalid-input').show();
            isValid = false;
        } else {
            $('#City').removeClass('invalid-input-value');
            $('#City').siblings('.invalid-input').hide();
        }
        const countryPattern = /^[A-Za-zÖÄÜöäü]+$/;
        if (!data.country.match(countryPattern)) {
            $('#Country').addClass('invalid-input-value');
            $('#Country').siblings('.invalid-input').show();
            isValid = false;
        } else {
            $('#Country').removeClass('invalid-input-value');
            $('#Country').siblings('.invalid-input').hide();
        }
        return isValid;
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
            $.ajax({
                url: 'http://localhost:8080/users/registration',
                method: "POST",
                contentType: 'application/json',
                data: JSON.stringify(newUser),
                success: function () {
                    $('#successModal').modal('show');
                    $('#successModalText').text("Registrierung erfolgreich durchgeführt.")
                    setTimeout(function () {
                        window.location.href = 'login.html';
                    }, 2000);
                },
                error: function (error) {
                    $('#alertModal').modal('show');
                    $('#alertModalText').text("Username ist bereits vergeben!");
                }
            });
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






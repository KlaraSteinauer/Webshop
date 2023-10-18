
$(document).ready(function () {

    /*TODO:
    - get user details from the local Storage
    - autofill the user details to the sender fields    
    */

    //------------------------------------------------------------------------------------------------------------------------
    //--------------------------------Produktliste für das Shoppingcart erstellen --------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------

    class Product {
        constructor(name, description, price, quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.description = description
        }
    }

    class PromotionCode {
        constructor(name, description, price) {
            this.name = name;
            this.price = price;
            this.description = description
        }
    }

    //promo für shopping cart
    let newPromotion = new PromotionCode("Promo", "Beschreibung des Codes", 8)

    function shoppingCartItem(item) {
        let newItem = $("<li>", {
            class: "list-group-item d-flex justify-content-between lh-sm"
        });
        let contentDiv = $("<div>", {
            id: "list-group-product-item-content",
            html: `<span class="my-0">${item.name}</span><br><small class="text-muted">${item.description}</small>`
        });
        let priceSpan = $("<span>", {
            class: "text-muted",
            html: `€ ${item.price}`
        })
        let actionDiv = $("<div>");
        let deleteSpan = $("<span>", {
            class: "deleteItem",
            html: `
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                    <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z" />
                </svg>
            `,
        });

        contentDiv.appendTo(newItem);
        actionDiv.appendTo(newItem);
        deleteSpan.appendTo(actionDiv);
        priceSpan.appendTo(newItem);
        $('#shoppingCartList').append(newItem);
    }

    function shoppingCartPromotion(item) {
        let newItem = $("<li>", {
            class: "list-group-item d-flex justify-content-between bg-light"
        });
        let contentDiv = $("<div>", {
            class: "text-success",
            html: `<span class="my-0">${item.name}</span><br><small>${item.description}</small>`
        });
        let priceSpan = $("<span>", {
            class: "text-success",
            html: `€ - ${item.price}`
        })

        contentDiv.appendTo(newItem);
        priceSpan.appendTo(newItem);
        $('#shoppingCartList').append(newItem);
    }

    function shoppingCartSum(summe) {
        let newItem = $("<li>", {
            class: "list-group-item d-flex justify-content-between"
        });
        let contentDiv = $("<div>", {
            id: "list-group-product-item-content",
            html: `<span>Total (EUR)</span>`
        });
        let priceSpan = $("<strong>", {
            html: `€ ${summe}`
        })

        contentDiv.appendTo(newItem);
        priceSpan.appendTo(newItem);
        $('#shoppingCartList').append(newItem);
    }

    function summe() {
        let summe = 0;
        shoppingCartList.forEach(item => {
            shoppingCartItem(item)
            summe += item.price;
        })
        return summe - newPromotion.price
    };

    /*function itemsInCart() {
        let itemsInCart = 0;
        shoppingCartList.forEach(item => {
            itemsInCart += item.quantity;
        })
        return itemsInCart
    }*/

    let itemsInCart = 0;

    $('#btn-promo').on("click", function () {
        shoppingCartPromotion(newPromotion)
    })

    $.ajax({
        url: `http://localhost:8080/findAllProductsInShoppingCart/`,
        method: 'GET',
        headers:
        {
            "Authorization": localStorage.getItem("accessToken")
        },
        success: function () {
            shoppingCartSum(summe());
            $('#amountItems').text(itemsInCart().toString())
        },
        error: function () {
            console.log("Error: ShoppingCart konnte nicht geladen werden");
        }
    });



    //------------------------------------------------------------------------------------------------------------------------
    //--------------------------------Userdaten für Bestellung (automatisch) laden -------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------

    class User {
        constructor(userName, eMail, firstName, lastName, address) {
            this.userName = userName;
            this.eMail = eMail;
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
        }
    }

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
        let userData = {
            "firstName": $('#firstName').val(),
            "lastName": $('#lastName').val(),
            "eMail": $('#email').val(),
            "userName": $('#username').val(),
        };
        let addressData = {
            "street": $('#address').val(),
            "number": $('#address2').val(),
            "zip": $('#zip').val(),
            "city": $('#city').val(),
            "country": $('#country selected:option').val(),
        };
        isValidAddressData(addressData);
        isValidUserData(userData);

        if (!isValidAddressData(addressData) || !isValidUserData(userData)) {
            alert("Validierung fehlgeschlagen! Bitte Eingabedaten nochmals überprüfen.");
            return false;
        }
        if (
            !addressData.street ||
            !addressData.number ||
            !addressData.zip ||
            !addressData.city ||
            !addressData.country
        ) {
            alert("Bitte füllen Sie alle erforderlichen Felder aus.");
            return false;
        }
        if (
            !userData.firstName ||
            !userData.lastName ||
            !userData.userName ||
            !userData.userEmail
        ) {
            alert("Bitte füllen Sie alle erforderlichen Felder aus.");
            return false;
        }
        return true;
    }

    function isValidUserData(data) {
        const namePattern = /^[A-Za-zÖÄÜöäü]+$/;
        if (!data.firstName.match(namePattern)) {
            $('#firstName').siblings('.invalid-feedback').show();
        } else {
            $('#firstName').siblings('.invalid-feedback').hide();
        }
        if (!data.lastName.match(namePattern)) {
            $('#lastName').siblings('.invalid-feedback').show();
        } else {
            $('#lastName').siblings('.invalid-feedback').hide();
        }
        if (!data.userName) {
            $('#username').siblings('.invalid-feedback').show();
        } else {
            $('#username').siblings('.invalid-feedback').hide();
        }
        const emailPattern = /^[a-zA-Z0-9._-ÖÄÜöäü]+@[a-zA-Z0-9.-ÖÄÜöäü]+\.[a-zA-Z]{2,4}$/;
        if (!data.userEmail.match(emailPattern)) {
            $('#email').siblings('.invalid-feedback').show();
        } else {
            $('#email').siblings('.invalid-feedback').hide();
        }
        return true;
    }

    function isValidAddressData(data) {
        const streetPattern = /^[A-Za-zÖÄÜöäü\s]+$/;
        if (!data.street.match(streetPattern)) {
            $('#address').siblings('.invalid-feedback').show();
        } else {
            $('#address').siblings('.invalid-feedback').hide();
        }
        const numberPattern = /\d+/;
        if (!data.number.match(numberPattern)) {
            $('#address2').siblings('.invalid-feedback').show();
        } else {
            $('#address2').siblings('.invalid-feedback').hide();
        }
        const zipPattern = /^\d{4,6}$/;
        if (!data.zip.match(zipPattern)) {
            $('#zip').siblings('.invalid-feedback').show();
        } else {
            $('#zip').siblings('.invalid-feedback').hide();
        }
        const cityPattern = /^[A-Za-zÖÄÜöäü]+$/;
        if (!data.city.match(cityPattern)) {
            $('#city').siblings('.invalid-feedback').show();
        } else {
            $('#city').siblings('.invalid-feedback').hide();
        }
        const countryPattern = /^[A-Za-zÖÄÜöäü]+$/;
        if (!data.country.match(countryPattern)) {
            $('#country').siblings('.invalid-feedback').show();
        } else {
            $('#country').siblings('.invalid-feedback').hide();
        }
        return true;
    }

    const userName = localStorage.getItem("currentUser");

    $.ajax({
        url: `http://localhost:8080/user/get/${userName}`,
        method: 'GET',
        headers:
        {
            "Authorization": localStorage.getItem("accessToken")
        },
        success: function (user) {
            $('#username').val(user.userName),
                $('#email').val(user.eMail),
                $('#firstName').val(user.firstname),
                $('#lastName').val(user.lastname)
            $('#address').val(user.address.street),
                $('#address2').val(user.address.number),
                $('#zip').val(user.address.zip),
                $('#city').val(user.address.city),
                $('#country option:selected').val(user.address.country);
        },
        error: function (error) {
            console.log("Error: " + error);
        }
    });

    $('#finishOrder').on("click", function () {
        validateForm()
    })

})
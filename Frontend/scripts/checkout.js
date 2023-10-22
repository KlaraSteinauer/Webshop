
$(document).ready(function () {

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

    let shoppingCartList = [];
    loadShoppingCart()

    //promo für shopping cart
    let newPromotion = new PromotionCode("Promo", "Beschreibung des Codes", 8)

    function shoppingCartItem(item) {
        let newItem = $("<li>", {
            class: "list-group-item d-flex justify-content-between lh-sm", 'data-id': item.id
        });
        let contentDiv = $("<div>", {
            id: "list-group-product-item-content",
            html: ` <span class="deleteItem">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
                            <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                        </svg>
                    </span>
                    <span class="my-0">${item.name} [ ${item.quantity} Stk.]</span><br><small class="text-muted">${item.description}</small>`
        });
        let priceSpan = $("<span>", {
            class: "text-muted",
            html: `€ ${item.price}`
        })
        contentDiv.appendTo(newItem);
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

    //logic that loads the cartitems to the list and set total amount + total itemsInShoppingCart
    function loadShoppingCart() {
        let summeCart = 0;
        let itemsInCart = 0;
        $.ajax({
            url: `http://localhost:8080/carts`,
            method: 'GET',
            headers:
            {
                "Authorization": localStorage.getItem("accessToken")
            },
            success: function (products) {
                products.forEach(item => {
                    shoppingCartItem(item);
                    summeCart += item.price;
                    itemsInCart += item.quantity;
                });
                shoppingCartSum(summeCart);
                $('#amountItems').text(itemsInCart)
                localStorage.setItem("cartItems", itemsInCart)
            },
            error: function () {
                console.log("Error: ShoppingCart konnte nicht geladen werden");
            }
        });
    }

    //TODO promotion richtig von gesamtsumme abziehen - button nach 1x klicken deaktivieren
    $('#btn-promo').on("click", function () {
        shoppingCartPromotion(newPromotion);
        return summeCart = (summeCart - newPromotion.price)
    })

    //logic to delete one item from the shopping cart
    $('#shoppingCartList').on('click', '.deleteItem', function () {
        let item = $(this).closest('.list-group-item');
        let productId = item.data('id');

        $.ajax({
            url: `http://localhost:8080/carts/${productId}`,
            method: 'DELETE',
            headers:
            {
                "Authorization": localStorage.getItem("accessToken")
            },
            success: function () {
                item.remove();
                location.reload();
            },
            error: function () {
                console.log("Error: ShoppingCart konnte nicht geladen werden");
            }
        });
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

    //TODO check the validation
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

    let currentUserId;

    //logic to get the userId from currentUser out of the token
    const tokenBearer = localStorage.getItem("accessToken") || '';
    let token = '';

    if (tokenBearer.startsWith('Bearer ')) {
        token = tokenBearer.substring(7); // Remove "Bearer " from the beginning
    }

    if (token.length > 0) {
        try {
            jwt = JSON.parse(atob(token.split('.')[1]));
            currentUserId = jwt.id;
        } catch (e) {
            console.log('error: ' + e);
        }
    }

    //TODO get the address from backend
    $.ajax({
        url: `http://localhost:8080/users/me`,
        method: 'GET',
        headers:
        {
            "Authorization": localStorage.getItem("accessToken")
        },
        success: function (user) {
            $('#username').val(user.userName),
                $('#email').val(user.userEmail),
                $('#firstName').val(user.firstName),
                $('#lastName').val(user.lastName)
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
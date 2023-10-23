$(document).ready(function () {

    let pageLoaded = true;
    // Hide all forms and lists initially
    $('.adminManagementForm, .managementList').hide();

    $('#productManagement').click(function () {
        toggleFormAndList('#productManagementForm', '#productManagementList');
    });

    $('#userManagement').click(function () {
        toggleFormAndList('#userManagementForm', '#userManagementList');
    });

    $('#orderManagement').click(function () {
        toggleFormAndList('#orderManagementForm', '#orderManagementList');
    });

    function toggleFormAndList(formSelector, listSelector) {
        if (pageLoaded) {
            $(formSelector).show();
            $(listSelector).show();
            pageLoaded = false;
        } else {
            $('.adminManagementForm, .managementList').hide();
            $(formSelector).show();
            $(listSelector).show();
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------
    //--------------------------------Product Klasse + Constructor + Logic-------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------

    class Product {
        constructor(name, description, imageFile, price, quantity, category) {
            this.name = name;
            this.category = category;
            this.imageFile = imageFile;
            this.price = price;
            this.quantity = quantity;
            this.description = description
        }
    }

    let productList = [];

    function newProductItem(product) {
        let newItem = $("<li>", {
            class: "list-group-item d-flex justify-content-between align-items-center",
            id: "list-group-product-item",
            "data-item-id": product.id, // Add data attribute for item id
        });
        let contentDiv = $("<div>", {
            id: "list-group-product-item-content",
            html: `<span class="badge bg-primary rounded-pill">${product.quantity}</span> ${product.name}`,
        });
        let actionDiv = $("<div>");
        let updateSpan = $("<span>", {
            class: "updateItem",
            html: `
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                        <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
                        <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z" />
                    </svg>
                `,
        });
        let deleteSpan = $("<span>", {
            class: "deleteItem",
            html: `
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                        <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z" />
                    </svg>
                `,
        });
        contentDiv.appendTo(newItem);
        updateSpan.appendTo(actionDiv);
        deleteSpan.appendTo(actionDiv);
        actionDiv.appendTo(newItem);
        $('#list-group-product').append(newItem);
    }

    function validateProductForm() {
        let productData = {
            "name": $('#product-name-val').val(),
            "description": $('#product-description-val').val(),
            "image": $('#product-img-val').val(),
            "price": $('#product-price-val').val(),
            "quantity": $('#product-amount-val').val(),
            "category": $('#product-category').val()
        }
        isValidProduct(productData);
        if (!isValidProduct(productData)) {
            $('#alertModal').modal('show');
            $('#alertModalText').text("Validierung fehlgeschlagen! Bitte Eingabedaten nochmals überprüfen.")
            return false;
        }
        if (
            !productData.name ||
            !productData.description ||
            !productData.image ||
            !productData.price ||
            !productData.quantity ||
            !productData.category
        ) {
            $('#alertModal').modal('show');
            $('#alertModalText').text("Bitte füllen Sie alle erforderlichen Felder aus.")
            return false;
        }
        return true;
    }

    function isValidProduct(data) {
        if (!data.name.match(/^[\w\s-]+$/)) {
            $('#product-name').closest('.input-group').addClass('invalid-input-value');
        } else {
            $('#product-name').closest('.input-group').removeClass('invalid-input-value');
        }
        if (!data.description.match(/^(?!default$).+$/)) {
            $('#product-description-val').closest('.input-group').addClass('invalid-input-value');
        } else {
            $('#product-description-val').closest('.input-group').removeClass('invalid-input-value');
        }
        if (!data.image.match(/^.*\.(jpg|jpeg|png|gif)$/)) {
            $('#product-img-val').closest('.input-group').addClass('invalid-input-value');
        } else {
            $('#product-img-val').closest('.input-group').removeClass('invalid-input-value');
        }
        if (!data.price.match(/^\d+(\.\d{2})?$/)) {
            $('#product-price-val').closest('.input-group').addClass('invalid-input-value');
        } else {
            $('#product-price-val').closest('.input-group').removeClass('invalid-input-value');
        }
        if (!data.quantity.match(/^\d+$/)) {
            $('#product-amount-val').closest('.input-group').addClass('invalid-input-value');
        } else {
            $('#product-amount-val').closest('.input-group').removeClass('invalid-input-value');
        }
        if (data.category === "default") {
            $('#product-category').addClass('invalid-input-value');
        } else {
            $('#product-category').removeClass('invalid-input-value');
        }
        return true;
    }

    // Function to load product list from server
    function loadProductList() {
        $.ajax({
            url: 'http://localhost:8080/products',
            method: 'GET',
            success: (products) => {
                productList = products;
                $('#list-group-product').empty();
                products.forEach(product => {
                    newProductItem(product);
                });

            },
            error: function (error) {
                console.log("Error: " + error);
            }
        });
    }

    //event to load product list from server
    $("#productManagement").on("click", _e => {
        loadProductList();
    })

    //event to add a product from the list
    $('#addProduct').click(function (e) {
        e.preventDefault();
        if (validateProductForm()) {
            var imageFile = $('#product-img-val')[0].files[0];
            var formData = new FormData();
            var product = {
                name: $('#product-name-val').val(),
                description: $('#product-description-val').val(),
                price: $('#product-price-val').val(),
                quantity: $('#product-amount-val').val(),
                category: $('#product-category option:selected').val()
            };
            formData.append("product", JSON.stringify(product));
            formData.append("productImage", imageFile);

            $.ajax({
                url: "http://localhost:8080/products",
                type: "POST",
                headers: {
                    "Authorization": sessionStorage.getItem("accessToken")
                },
                data: formData,
                processData: false, // To prevent jQuery from processing data
                contentType: false, // To prevent jQuery from setting content type
                success: function () {
                    $('#successModal').modal('show');
                    $('#successModalText').text("Produkt wurde erfolgreich hinzugefügt!");
                },
                error: function (errorThrown) {
                    $('#alertModal').modal('show');
                    $('#alertModalText').text("Produkt konnte nicht hinzugefügt werden. Fehler: " + errorThrown);
                }
            });
        } else {
            $('#alertModal').modal('show');
            $('#alertModalText').text("Validierung fehlgeschlagen! Bitte Eingabedaten nochmals überprüfen.");
        }
    });

    //event to delete a product from the list
    $('#list-group-product').on("click", '.deleteItem', function () {
        let newItem = $(this).closest('li');
        let id = newItem.data('item-id');

        $.ajax({
            url: `http://localhost:8080/products/${id}`,
            method: 'DELETE',
            headers:
            {
                "Authorization": sessionStorage.getItem("accessToken")
            },
            contentType: "application/json",
            success: function () {
                newItem.remove();
                $('#alertModal').modal('show');
                $('#alertModalText').text("Produkt wurde erfolgreich gelöscht.")
                loadProductList();
            },
            error: function (error) {
                console.log("Error: " + error);
            }
        });
    });

    //event to update product from the list
    $('#list-group-product').on("click", '.updateItem', function () {
        let newItem = $(this).closest('li');
        let id = newItem.data('item-id');
        $.ajax({
            url: `http://localhost:8080/products/${id}`,
            method: 'GET',
            headers:
            {
                "Authorization": sessionStorage.getItem("accessToken")
            },
            success: function (product) {
                $('#product-name-val').val(product.name);
                $('#product-description-val').val(product.description);
                $('#product-img-val').val(product.image);
                $('#product-price-val').val(product.price);
                $('#product-amount-val').val(product.quantity);
                $('#product-category').val(product.category);
            },
            error: function (error) {
                console.log("Error: " + error);
            }
        });

        $('#saveProduct').on("click", _e => {
            if (validateProductForm()) {
                var imageFile = $('#product-img-val').prop('files')[0];
                var formData = new FormData();
                var product = {
                    name: $('#product-name-val').val(),
                    description: $('#product-description-val').val(),
                    price: $('#product-price-val').val(),
                    quantity: $('#product-amount-val').val(),
                    category: $('#product-category option:selected').val()
                };
                formData.append("product", JSON.stringify(product));
                formData.append("productImage", imageFile);
                fetch(`http://localhost:8080/products/${id}`, {
                    method: "PUT",
                    headers: {
                        "Authorization": sessionStorage.getItem("accessToken")
                    },
                    body: formData,
                    success: function () {
                        loadProductList()
                    },
                    error: (error) => {
                        console.error('Error:', error);
                        $('#alertModal').modal('show');
                        $('#alertModalText').text("Error adding product!")
                    }
                })
            } else {
                $('#alertModal').modal('show');
                $('#alertModalText').text("Produkt konnte nicht hinzugefügt werden.")
            }
        });
    }
    );

    //------------------------------------------------------------------------------------------------------------------------
    //--------------------------------User Klasse + Constructor + Logic-------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------

    class User {
        constructor(userName, userPassword, userEmail, gender, firstName, lastName) {
            this.userName = userName;
            this.userPassword = userPassword;
            this.userEmail = userEmail;
            this.gender = gender;
            this.firstName = firstName;
            this.lastName = lastName
        }
    }

    let userList = [];

    function newUserItem(user) {
        let newItem = $("<li>", {
            class: "list-group-item d-flex justify-content-between align-items-center",
            id: "list-group-product-item",
            "data-item-id": user.userId, // Add data attribute for item id
        });
        let contentDiv = $("<div>", {
            id: "list-group-product-item-content",
            html: `<span class="badge bg-primary rounded-pill">${user.userId}</span> ${user.userName}`,
        });
        let actionDiv = $("<div>");
        let updateSpan = $("<span>", {
            class: "updateItem",
            html: `
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                        <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
                        <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z" />
                    </svg>
                `,
        });
        let deleteSpan = $("<span>", {
            class: "deleteItem",
            html: `
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                        <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z" />
                    </svg>
                `,
        });
        contentDiv.appendTo(newItem);
        updateSpan.appendTo(actionDiv);
        deleteSpan.appendTo(actionDiv);
        actionDiv.appendTo(newItem);
        $('#list-group-user').append(newItem);
    }

    function validateUserForm() {
        let userData = {
            "userName": $('#userName').val(),
            "userPassword": $('#userPassword').val(),
            "userEmail": $('#eMail').val(),
            "gender": $('#gender').val(),
            "firstName": $('#firstName').val(),
            "lastName": $('#lastName').val()
        };
        isValidUser(userData);
        if (!isValidUser(userData)) {
            $('#alertModal').modal('show');
            $('#alertModalText').text("Validierung fehlgeschlagen! Bitte Eingabedaten nochmals überprüfen.")
            return false;
        }
        if (
            !userData.userName ||
            !userData.userPassword ||
            !userData.userEmail ||
            !userData.gender ||
            !userData.firstName ||
            !userData.lastName
        ) {
            $('#alertModal').modal('show');
            $('#alertModalText').text("Bitte füllen Sie alle erforderlichen Felder aus.")
            return false;
        }
        return true;
    }

    function isValidUser(data) {
        if (!data.userName) {
            $('#userName').closest('.form-floating').addClass('invalid-input-value');
        } else {
            $('#userName').closest('.form-floating').removeClass('invalid-input-value');
        }
        if (!data.userPassword) {
            $('#userPassword').closest('.form-floating').addClass('invalid-input-value');
        } else {
            $('#userPassword').closest('.form-floating').removeClass('invalid-input-value');
        }
        if (!data.userEmail.match(/^[a-zA-Z0-9._-ÖÄÜöäü]+@[a-zA-Z0-9.-ÖÄÜöäü]+\.[a-zA-Z]{2,4}$/)) {
            $('#eMail').closest('.form-floating').addClass('invalid-input-value');
        } else {
            $('#eMail').closest('.form-floating').removeClass('invalid-input-value');
        }
        if (!data.lastName.match(/^[A-Za-zÖÄÜöäü]+$/)) {
            $('#lastName').closest('.form-floating').addClass('invalid-input-value');
        } else {
            $('#lastName').closest('.form-floating').removeClass('invalid-input-value');
        }
        if (!data.firstName.match(/^[A-Za-zÖÄÜöäü]+$/)) {
            $('#firstName').closest('.form-floating').addClass('invalid-input-value');
        } else {
            $('#firstName').closest('.form-floating').removeClass('invalid-input-value');
        }
        if (data.gender === "default") {
            $('#gender').addClass('invalid-input-value');
        } else {
            $('#gender').removeClass('invalid-input-value');
        }
        return true;
    }

    function createUser() {
        let userValues = {
            userName: $('#userName').val(),
            userPassword: $('#userPassword').val(),
            userEmail: $('#eMail').val(),
            gender: $('#gender option:selected').val(),
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
        };
        return new User(userValues.userName, userValues.userPassword, userValues.userEmail, userValues.gender, userValues.firstName, userValues.lastName);
    }

    function updateUser(id) {
        let userValues = {
            userId: id,
            userName: $('#userName').val(),
            userPassword: $('#userPassword').val(),
            userEmail: $('#eMail').val(),
            gender: $('#gender option:selected').val(),
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
        };
        return new User(userValues.userName, userValues.userPassword, userValues.userEmail, userValues.gender, userValues.firstName, userValues.lastName);
    }

    // Function to load user list from server 
    function loadUserList() {
        $.ajax({
            url: 'http://localhost:8080/users',
            method: 'GET',
            headers:
            {
                "Authorization": sessionStorage.getItem("accessToken")
            },
            success: (users) => {
                userList = users;
                $('#list-group-user').empty();
                users.forEach(user => {
                    newUserItem(user);
                });
            },
            error: function (error) {
                console.log("Error: " + error);
            }
        });
    }

    //event to load user list from server
    $("#userManagement").on("click", _e => {
        loadUserList();
    })

    //event to add a new user to the list
    $("#addUser").on("click", _e => {
        if (validateUserForm()) {
            let user = createUser();
            $.ajax({
                url: 'http://localhost:8080/users/registration',
                method: "POST",
                headers:
                {
                    "Authorization": sessionStorage.getItem("accessToken")
                },
                contentType: 'application/json',
                data: JSON.stringify(user),
                success: function () {
                    $('#successModal').modal('show');
                    $('#successModalText').text("User erfolgreich hinzugefügt.")
                    loadUserList()
                },
                error: function (error) {
                    console.log("Error: " + error);
                }
            });
        } else {
            $('#alertModal').modal('show');
            $('#alertModalText').text("User konnte nicht hinzugefügt werden!")
        }
    });

    //event to delete a user from the list
    $('#list-group-user').on("click", '.deleteItem', function () {
        let newItem = $(this).closest('li');
        let id = newItem.data('item-id');

        $.ajax({
            url: `http://localhost:8080/users/${id}`,
            method: 'DELETE',
            contentType: "application/json",
            headers:
            {
                "Authorization": sessionStorage.getItem("accessToken")
            },
            success: function () {
                newItem.remove();
                $('#alertModal').modal('show');
                $('#alertModalText').text("User erfolgreich gelöscht")
                loadUserList();
            },
            error: function (error) {
                console.log("Error: " + error);
            }
        });
    });

    //event to update user from the list 
    $('#list-group-user').on("click", '.updateItem', function () {
        let newItem = $(this).closest('li');
        let id = newItem.data('item-id');
        $.ajax({
            url: `http://localhost:8080/users/${id}`,
            method: 'GET',
            headers:
            {
                "Authorization": sessionStorage.getItem("accessToken")
            },
            success: function (user) {
                $('#userName').val(user.userName),
                    $('#userPassword').val(user.userPassword),
                    $('#eMail').val(user.userEmail),
                    $('#gender').val(user.gender),
                    $('#firstName').val(user.firstName),
                    $('#lastName').val(user.lastName);
                if (user.isActive) {
                    $('#checkActive').prop('checked', true);
                    $('#checkDeactive').prop('checked', false);
                } else {
                    $('#checkDeactive').prop('checked', true);
                    $('#checkActive').prop('checked', false);
                }
            },
            error: function (error) {
                console.log("Error: " + error);
            }
        });

        $('#saveUser').on("click", _e => {
            if (validateUserForm()) {
                let user = updateUser(id);
                $.ajax({
                    url: `http://localhost:8080/users/${id}`,
                    method: "PUT",
                    headers:
                    {
                        "Authorization": sessionStorage.getItem("accessToken")
                    },
                    contentType: 'application/json',
                    data: JSON.stringify(user),
                    success: function () {
                        loadUserList()
                    },
                    error: function (error) {
                        console.log("Error: " + error);
                    }
                });

                if ($('#checkActive').is(':checked')) {
                    $.ajax({
                        url: `http://localhost:8080/users/activate/${id}`,
                        method: "PUT",
                        headers:
                        {
                            "Authorization": sessionStorage.getItem("accessToken")
                        },
                        contentType: 'application/json',
                        success: function () {
                            loadUserList()
                        },
                        error: function (error) {
                            console.log("Error: " + error);
                        }
                    });
                } else if ($('#checkDeactive').is(':checked')) {
                    $.ajax({
                        url: `http://localhost:8080/users/deactivate/${id}`,
                        method: "PUT",
                        headers:
                        {
                            "Authorization": sessionStorage.getItem("accessToken")
                        },
                        contentType: 'application/json',
                        success: function () {
                            loadUserList()
                        },
                        error: function (error) {
                            console.log("Error: " + error);
                        }
                    });
                }
            };
        });
    });

    // Function to toggle the activate checkbox
    function toggleCheckbox() {
        if ($('#checkActive').is(':checked')) {
            $('#checkDeactive').prop('checked', false);
        } else if ($('#checkDeactive').is(':checked')) {
            $('#checkActive').prop('checked', false);
        }
    }

    // Attach the change event handler to both checkboxes
    $('#checkActive, #checkDeactive').on('change', function () {
        toggleCheckbox();
    });


});

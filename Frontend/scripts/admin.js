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

    //--------------------------------Product Klasse + Constructor + Logic-------------------------------------------------------
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

    /*function createProduct() {
        let productValues = {
            name: $('#product-name-val').val(),
            description: $('#product-description-val').val(),
            image: $('#product-img-val').val(),
            price: $('#product-price-val').val(),
            quantity: $('#product-amount-val').val(),
            category: $('#product-category option:selected').val(),
        };
        return new Product(productValues.name, productValues.description, productValues.image, productValues.price, productValues.quantity, productValues.category);
    }*/

    function updateProduct(id) {
        let productValues = {
            id: id,
            name: $('#product-name-val').val(),
            description: $('#product-description-val').val(),
            image: $('#product-img-val').val(),
            price: $('#product-price-val').val(),
            quantity: $('#product-amount-val').val(),
            category: $('#product-category option:selected').val(),
        };
        return new Product(productValues.name, productValues.description, productValues.image, productValues.price, productValues.quantity, productValues.category);
    }

    // Function to load product list from server
    function loadProductList() {
        $.ajax({
            url: 'http://localhost:8080/product/all',
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


    $('#addProduct').click(function (e) {
        e.preventDefault();
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
        fetch("http://localhost:8080/product/file", {
            method: "POST",
            // No 'Content-Type' header—fetch sets it automatically due to FormData
            headers: {
                "Authorization": localStorage.getItem("accessToken")
            },
            body: formData,
            success: function (response) {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                alert("Product added successfully!");
                return response.json();
            },
            error: (error) => {
                console.error('Error:', error);
                alert("Error adding product!");
            }
        })
    })

    //event to add a new product to the list
    /*$("#addProduct").on("click", _e => {
        let product = createProduct();
     
        $.ajax({
            url: 'http://localhost:8080/product',
            method: "POST",
            contentType: 'application/json',
            headers:
            {
                "Authorization": localStorage.getItem("accessToken")
            },
            data: JSON.stringify(product),
            success: $.ajax({
                url: 'http://localhost:8080/product/all',
                method: 'GET',
                success: function () {
                    loadProductList()
                },
                error: function (error) {
                    console.log("Error: " + error);
                }
            }),
            error: function (error) {
                console.log("Error: " + error);
            }
        });
    });*/

    //event to delete a product from the list
    $('#list-group-product').on("click", '.deleteItem', function () {
        let newItem = $(this).closest('li');
        let id = newItem.data('item-id');
        console.log(id)

        $.ajax({
            url: `http://localhost:8080/product/${id}`,
            method: 'DELETE',
            headers:
            {
                "Authorization": localStorage.getItem("accessToken")
            },
            contentType: "application/json",
            success: function () {
                newItem.remove();
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
            url: `http://localhost:8080/product/findById/${id}`,
            method: 'GET',
            success: function (product) {
                $('#product-name-val').val(product.name);
                $('#product-description-val').val(product.description);
                $('#product-img-val').val(product.imageUrl);
                $('#product-price-val').val(product.price);
                $('#product-amount-val').val(product.quantity);
                $('#product-category option:selected').val(product.category);
            },
            error: function (error) {
                console.log("Error: " + error);
            }
        });

        $('#saveProduct').on("click", _e => {
            let product = updateProduct(id);
            $.ajax({
                url: `http://localhost:8080/product/${id}`,
                method: "PUT",
                headers:
                {
                    "Authorization": localStorage.getItem("accessToken")
                },
                contentType: 'application/json',
                data: JSON.stringify(product),
                success: function () {
                    loadProductList()
                },
                error: function (error) {
                    console.log("Error: " + error);
                }
            });
        });
    }
    );

    //--------------------------------User Klasse + Constructor + Logic-------------------------------------------------------
    class User {
        constructor(userName, userPassword, eMail, gender, firstname, lastname) {
            this.userName = userName;
            this.userPassword = userPassword;
            this.eMail = eMail;
            this.gender = gender;
            this.firstname = firstname;
            this.lastname = lastname
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

    function createUser() {
        let userValues = {
            userName: $('#userName').val(),
            userPassword: $('#userPassword').val(),
            eMail: $('#eMail').val(),
            gender: $('#gender option:selected').val(),
            firstname: $('#firstname').val(),
            lastname: $('#lastname').val(),
        };
        return new User(userValues.userName, userValues.userPassword, userValues.eMail, userValues.gender, userValues.firstname, userValues.lastname);
    }

    function updateUser(id) {
        let userValues = {
            userId: id,
            userName: $('#userName').val(),
            userPassword: $('#userPassword').val(),
            eMail: $('#eMail').val(),
            gender: $('#gender option:selected').val(),
            firstname: $('#firstname').val(),
            lastname: $('#lastname').val(),
        };
        return new User(userValues.userName, userValues.userPassword, userValues.eMail, userValues.gender, userValues.firstname, userValues.lastname);
    }

    // Function to load user list from server 
    function loadUserList() {
        $.ajax({
            url: 'http://localhost:8080/user/all',
            method: 'GET',
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
        let user = createUser();

        $.ajax({
            url: 'http://localhost:8080/user',
            method: "POST",
            contentType: 'application/json',
            data: JSON.stringify(user),
            success: function () {
                loadUserList()
            },
            error: function (error) {
                console.log("Error: " + error);
            }

        });
    });

    //event to delete a user from the list
    $('#list-group-user').on("click", '.deleteItem', function () {
        let newItem = $(this).closest('li');
        let id = newItem.data('item-id');
        console.log(id)

        $.ajax({
            url: `http://localhost:8080/user/delete/${id}`,
            method: 'DELETE',
            contentType: "application/json",
            success: function () {
                newItem.remove();
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
            url: `http://localhost:8080/user/get/${id}`,
            method: 'GET',
            success: function (user) {
                $('#userName').val(user.userName),
                    $('#userPassword').val(user.userPassword),
                    $('#eMail').val(user.eMail),
                    $('#gender option:selected').val(user.gender),
                    $('#firstname').val(user.firstname),
                    $('#lastname').val(user.lastname)
            },
            error: function (error) {
                console.log("Error: " + error);
            }
        });

        $('#saveUser').on("click", _e => {
            let user = updateUser(id);
            $.ajax({
                url: `http://localhost:8080/user/${id}`,
                method: "PUT",
                contentType: 'application/json',
                data: JSON.stringify(user),
                success: function () {
                    loadUserList()
                },
                error: function (error) {
                    console.log("Error: " + error);
                }
            });
        });
    });
});

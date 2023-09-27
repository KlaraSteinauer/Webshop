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

    //Product Klasse + Constructor + Logic
    class Product {
        constructor(id, name, description, imageUrl, price, quantity, category) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.imageUrl = imageUrl;
            this.price = price;
            this.quantity = quantity;
            this.description = description
        }
    }

    function toggleFormAndList(formSelector, listSelector) {
        if (pageLoaded) {
            $('.adminManagementForm, .managementList').hide();
            $(formSelector).show();
            $(listSelector).show();
            pageLoaded = false;
        } else {
            $('.adminManagementForm, .managementList').hide();
            $(formSelector).show();
            $(listSelector).show();
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

    function createProduct() {
        let productValues = {
            Id: $('#product-id-val').val(),
            Name: $('#product-name-val').val(),
            Description: $('#product-description-val').val(),
            ImageUrl: $('#product-img-val').val(),
            Price: $('#product-price-val').val(),
            Quantity: $('#product-amount-val').val(),
            Category: $('#product-category-val').val(),
        };
        return new Product(productValues.Id, productValues.Name, productValues.Description, productValues.ImageUrl, productValues.Price, productValues.Quantity, productValues.Category);
    }

    /*function updateProduct(Product) {
          productList.forEach(product => {
             if (product.Id === Product.Id) {
                 let id = (product.Id === Product.Id);
                 let name = (product.Name = Product.Name);
                 let description = (product.Description = Product.Description);
                 let imageUrl = (product.ImageUrl = Product.ImageUrl);
                 let price = (product.Price = Product.Price);
                 let quantity = (product.Quantity = Product.Quantity);
                 let category = (product.Category = Product.Category);
                 return productList.push(Product(id, name, description, imageUrl, price, quantity, category))
             } else {
                 console.error("Produkt konnte nicht aktualisiert werden!")
             }
         });
     }*/

    //function to load product list from db
    $("#search").on("click", _e => {
        $.ajax({
            url: 'http://localhost:8080/product',
            method: 'GET',
            success: function (products) {
                productList = products;
                $('#list-group-product').empty();
                products.forEach(product => {
                    newProductItem(product);
                });
            },
            error: function (error) {
                console.log(error);
            }
        });
    })

    //function to add a new product to the list
    $("#addProduct").on("click", _e => {
        let product = createProduct();
        console.log(product)

        $.ajax({
            url: "http://localhost:8080/product/create",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(product),
            success: function () {
                productList.push(product);
            },
            error: error => {
                console.log(error);
            }
        });
    });

    //function to delete a product from the list
    $('#list-group-product').on("click", '.deleteItem', function () {
        let newItem = $(this).closest('li');
        let itemId = newItem.data('item-id');

        $.ajax({
            url: `/product/${itemId}`,
            method: 'DELETE',
            success: function () {
                newItem.remove();
            },
            error: function (error) {
                console.log(error);
            }
        });
    });

    //function to update product from the list
    /* $('#list-group-product').on("click", function () {
         let itemId = updateItem.data('item-id');
 
         $.ajax({
             url: `/product/${itemId}`,
             method: 'PUT',
             success: updateProduct(itemId),
             error: function (error) {
                 console.log(error);
             }
         });
     });*/

});

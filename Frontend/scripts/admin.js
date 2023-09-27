$(document).ready(function () {
    $('#orderManagementForm').hide();
    $('#userManagementForm').hide();
    $('#productManagementForm').hide();

    $('#orderManagementList').hide();
    $('#userManagementList').hide();
    $('#productManagementList').hide();

    $('#productManagement').click(function () {
        $('#orderManagementForm').hide();
        $('#orderManagementList').hide();
        $('#userManagementForm').hide();
        $('#userManagementList').hide();
        $('#productManagementForm').show();
        $('#productManagementList').show();
    })

    $('#userManagement').click(function () {
        $('#orderManagementForm').hide();
        $('#orderManagementList').hide();
        $('#userManagementForm').show();
        $('#userManagementList').show();
        $('#productManagementForm').hide();
        $('#productManagementList').hide();
    })

    $('#orderManagement').click(function () {
        $('#orderManagementForm').show();
        $('#orderManagementList').show();
        $('#userManagementForm').hide();
        $('#userManagementList').hide();
        $('#productManagementForm').hide();
        $('#productManagementList').hide();
    })

    //OOP - Product Klasse + Constructor
    class Product {
        constructor(productId, productName, productCategory, productPicture, productPrice, productAmount, productDescription) {
            this.productId = productId;
            this.productName = productName;
            this.productCategory = productCategory;
            this.productPicture = productPicture;
            this.productPrice = productPrice;
            this.productAmount = productAmount;
            this.productDescription = productDescription
        }
    }

    let productData = {
        Id: $('#product-id-val').val(),
        Name: $('#product-name-val').val(),
        Description: $('#product-category').val(),
        ImageUrl: $('#product-img-val').val(),
        Price: $('#product-price-val').val(),
        Quantity: $('#product-amount-val').val(),
        Category: $('#product-description-val').val(),
    };

    $('#addProduct').click(function (e) {
        e.preventDefault();
        const product = new Product(productData.Id, productData.Name, productData.Category, productData.ImageUrl, productData.Price, productData.Quantity, productData.Description)
        // Create a new li element with some text
        let newItem = $('<li class="list-group-item d-flex justify-content-between align-items-center" id="list-group-product-item">' +
            '<div id="list-group-product-item-content"><span class="badge bg-primary rounded-pill"> ' + product.productAmount + ' </span> ' +
            product.productName +
            ' </div>' +
            '<div>' +
            '<span>' +
            ' <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">' +
            '<path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />' +
            '<path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z" />' +
            '</svg>' +
            '</span>' +
            '<span class="deleteItem">' +
            ' <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">' +
            '<path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z" /> ' +
            '</svg>' +
            '</span>' +
            '</div>' +
            '</li>');
        // Append the new li element to the ul with id 'myList'
        $('#list-group-product').append(newItem)
    });

    $.ajax({
        url: 'http://localhost:8080/product',
        method: 'POST',
        data: JSON.stringify(productData),
        contentType: 'application/json',
        dataType: 'json',
        //Handle the successful response here
        success: function (data) {
            console.log(data);
        },
        error: function (error) {
            console.error(error);
        }
    });

    /*
    $('.deleteItem').click(function (e) {
        //TODO implement delete Function as soon as Data from List and DB is loaded in FrontEnd
    })

    $.ajax({
        url: '/product/{id}',  
        method: 'DELETE',
        success: function (data) {
            // Handle the successful response here
            console.log(data);
        },
        error: function (xhr, status, error) {
            // Handle errors here
            console.error(error);
        }
    });


    $('#saveProduct').click(function (e) {
        e.preventDefault();
        //TODO implement save/adjust Function as soon as Data from List and DB is loaded in FrontEnd
    })

    $.ajax({
        url: '/product/{id}',  
        method: 'GET',
        dataType: 'json',     // The expected data type in response
        success: function (data) {
            // Handle the successful response here
            console.log(data);
        },
        error: function (xhr, status, error) {
            // Handle errors here
            console.error(error);
        }
    });
*/

});


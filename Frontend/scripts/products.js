$(document).ready(function () {

    //------------------------------------------------------------------------------------------------------------------------
    //--------------------------------Produktliste im HTML erstellen ---------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------

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

    function createCardElement(product) {
        const card = $('<div>', { class: 'card', 'data-id': product.id });
        const img = $('<img>', { src: `/images/${product.imageUrl}`, class: 'card-img-top', alt: `${product.name}` });
        const cardBody = $('<div>', { class: 'card-body' });
        const cardContent = $('<div>', { class: 'card-content' });
        const cardTitle = $('<h5>', { class: 'card-title', text: `${product.name}` });
        const cardText = $('<p>', { class: 'card-text', text: `${product.description}` });
        const cardQuantity = $('<p>', { class: 'card-text-quantity', text: `Anzahl aktuell verfügbarer Produkte: ${product.quantity}` });
        const btnCenter = $('<div>', { class: 'btn-center' });
        const addButton = $('<button>', { class: 'btn btn-primary btn-addToShoppingcart', text: 'Add to cart' });
        cardContent.append(cardTitle, cardText, cardQuantity);
        btnCenter.append(addButton);
        cardBody.append(cardContent, btnCenter);
        card.append(img, cardBody);
        return card;
    }

    function addProductToList(product) {
        const cardElement = createCardElement(product);
        const productCategory = product.category;
        const categoryList = document.getElementById(`section-${product.category}`);

        if (productCategory) {
            $(categoryList).append(cardElement);
        } else {
            console.error("Produkt konnte nicht gefunden werden")
        }
    }

    //function to load product list from db
    $.ajax({
        url: 'http://localhost:8080/product/all',
        method: 'GET',
        success: function (products) {
            products.forEach(product => {
                addProductToList(product);
            });

        },
        error: function (error) {
            console.log(error);
        }
    });

    //------------------------------------------------------------------------------------------------------------------------
    //--------------------------------Logic to save products to the local storage --------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------


    $('#productContainer').on('click', '.btn-addToShoppingcart', function () {
        // You can access the product information for the clicked card using the card's HTML structure.
        const card = $(this).closest('.card');
        const productId = $(this).closest('.card').data('id');
        const cardTitle = card.find('.card-title').text();

        // Example: Display the product information in an alert
        alert(`${cardTitle} zum Warenkorb hinzugefügt`);

        $.ajax({
            url: `http://localhost:8080/cart/${productId}`,
            method: 'POST',
            headers:
            {
                "Authorization": localStorage.getItem("accessToken")
            },
            success: function (cartItems) {
                localStorage.setItem("cartItems", cartItems)
            },
            error: function () {
                console.log("Error: ShoppingCart konnte nicht geladen werden");
            }
        });
    });
})
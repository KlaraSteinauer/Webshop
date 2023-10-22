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
        const btnCenter = $('<div>', { class: 'btn-center' });
        const addButton = $('<button>', { class: 'btn btn-primary btn-addToShoppingcart', text: 'Add to cart' });
        cardContent.append(cardTitle, cardText);
        btnCenter.append(addButton);
        cardBody.append(cardContent, btnCenter);
        card.append(img, cardBody);
        return card;
    }

    function addProductToList(product) {
        const cardElement = createCardElement(product);
        const productCategory = product.category;
        const categoryList = $(`#section-${product.category}`);

        if (productCategory) {
            $(categoryList).append(cardElement);
        } else {
            console.error("Produkt konnte nicht gefunden werden")
        }
    }

    //function to load product list from db
    $.ajax({
        url: 'http://localhost:8080/products',
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
    //--------------------------------Logic to add products to shoppingcart------ --------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------

    //event to add product to the shoppingcart
    $('#productContainer').on('click', '.btn-addToShoppingcart', function () {
        const card = $(this).closest('.card');
        const productId = $(this).closest('.card').data('id');
        const cardTitle = card.find('.card-title').text();
        let itemsSelected = 0;

        $.ajax({
            url: `http://localhost:8080/carts/${productId}`,
            method: 'POST',
            headers:
            {
                "Authorization": localStorage.getItem("accessToken")
            },
            success: function () {
                $.ajax({
                    url: `http://localhost:8080/carts`,
                    method: 'GET',
                    headers:
                    {
                        "Authorization": localStorage.getItem("accessToken")
                    },
                    success: function (products) {
                        products.forEach(item => {
                            itemsSelected += item.quantity;
                        });
                        localStorage.setItem("cartItems", itemsSelected);
                        $('#successModal').modal('show');
                        $('#successModalText').text(`${cardTitle} zum Warenkorb hinzugefügt.`);
                        setTimeout(function() {
                            location.reload();
                        }, 3000)
                    },
                    error: function () {
                        $('#alertModal').modal('show');
                        $('#alertModalText').text(`ShoppingCart konnte nicht geladen werden`)
                    }
                });
            },
            error: function () {
                $('#alertModal').modal('show');
                $('#alertModalText').text(`${cardTitle} konnte nicht zum Warenkorb hinzugefügt werden. Um Produkte hinzuzufügen, bitten wir Sie, sich zuerst anzumelden.`);
            }
        });
    });

    //function to display detail modal for product
    $('#productContainer').on('click', '.card-content', function () {
        const card = $(this).closest('.card');
        const productId = card.data('id');
        $('#productDetailModal').modal('show');

        $.ajax({
            url: 'http://localhost:8080/products',
            method: 'GET',
            success: function (products) {
                products.forEach(product => {
                    if (product.id === productId) {
                        $('#productDetailModal').data('id', product.id);
                        $('#productDetailModalLabel').text(`${product.name}`)
                        $('#productDetailCategory').text(`Produktkategorie: ${product.category}`)
                        $('#productDetailimageUrl').attr('src', `/images/${product.imageUrl}`)
                        $('#productDetailPrice').text(`Preis pro Stück: ${product.price}`)
                        $('#productDetailQuantity').text(`Aktuell sind ${product.quantity} verfügbar.`)
                        $('#productDetailDescription').text(`Produktbeschreibung: ${product.description}`)
                    }
                });
            },
            error: function (error) {
                console.log(error);
            }
        });
    });

    $('#productDetailModal').on('click', '.btn-modal-addToShoppingcart', function () {
        const modal = $(this).closest('.modal');
        const productId = modal.data('id');
        const modalTitle = modal.find('.modal-title').text();
        let itemsSelected = 0;

        $.ajax({
            url: `http://localhost:8080/carts/${productId}`,
            method: 'POST',
            headers:
            {
                "Authorization": localStorage.getItem("accessToken")
            },
            success: function () {
                $('#successModal').modal('show');
                $('#successModalText').text(`${modalTitle} zum Warenkorb hinzugefügt. Um Produkte hinzuzufügen, bitten wir Sie, sich zuerst anzumelden.`);
                $.ajax({
                    url: `http://localhost:8080/carts`,
                    method: 'GET',
                    headers:
                    {
                        "Authorization": localStorage.getItem("accessToken")
                    },
                    success: function (products) {
                        products.forEach(item => {
                            itemsSelected += item.quantity;
                        });
                        localStorage.setItem("cartItems", itemsSelected),
                        setTimeout(function() {
                            location.reload();
                        }, 3000)
                    },
                    error: function () {
                        $('#alertModal').modal('show');
                        $('#alertModalText').text(`ShoppingCart konnte nicht geladen werden`);
                    }
                });
            },
            error: function () {
                $('#alertModal').modal('show');
                $('#alertModalText').text(`${modalTitle} konnte nicht zum Warenkorb hinzugefügt werden. Um Produkte hinzuzufügen, bitten wir Sie, sich zuerst anzumelden.`);
            }
        });
    });
    
})
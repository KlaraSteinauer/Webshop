$(document).ready(function () {

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
        const card = $('<div>', { class: 'card' });
        const img = $('<img>', { src: `/images/${product.imageUrl}`, class: 'card-img-top', alt: `${product.name}` });
        const cardBody = $('<div>', { class: 'card-body' });
        const cardContent = $('<div>', { class: 'card-content' });
        const cardTitle = $('<h5>', { class: 'card-title', text: `${product.name}` });
        const cardText = $('<p>', { class: 'card-text', text: `${product.description}` });
        const btnCenter = $('<div>', { class: 'btn-center' });
        const addButton = $('<button>', { class: 'btn btn-primary', id: "btn-addToShoppingcart", text: 'Add to cart' });

        cardContent.append(cardTitle, cardText);
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
})
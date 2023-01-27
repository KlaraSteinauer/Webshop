$("#createProductButton").on("click", e => {
    const product = {
        "name": "Frontend",
        "description": "Ein Produkt",
        "price": 20.99,
        "quantity": 3,
        "type": "frontend",
    }

    $.ajax({
        url: "http://localhost:8080/products",
        type: "POST",
        cors: true,
        contentType: "application/json",
        data: JSON.stringify(product),
        success: console.log,
        error: console.error()
    });
});

$("#getProductsButton").on("click", function (e){
    $ajax({
        url: "http://localhost:8080/products",
        type: "GET",
        cors: true,
        success: function (products) { addProductsToPage(products) },
        error: function (error) { console.error(error) }
    })
});
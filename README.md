# Webshop
Application for a Webshop build with Spring Boot in Backend and Bootstrap in Frontend.

PRODUCT
• There should be a page where all products are visible:
    ordered by categories
    ordered in a list
    visible by their details page
• The products should have:
    title
    description
    a product image
    price
    category
    quantity
• You should be able to filter the products by categories
• All products have a details page
• Administrator could create/delete/update Products

USER
• A user can login with a username/email and password
• A anonymous user could register with firstname/lastname/e-mail/username/password
• You can either be an anonymous user, normal user or an administrator
• All Users can see all products
• Registered users can put all products into their shopping card
• Registered Users can remove products from their shopping card

ADMINISTRATOR
• Can add, edit, and delete products
• Can edit users - change user date, user passwords, user status - active, deactive
• Can view all orders - ordered, paid, shipped - and change the status of the order - cancelled, ordered, shipped -
• See a list of all users

SHOPPINGCART / ORDER
• Users can order their shopping card - if this is done the order will be saved in the DB
• Users can only order if the products are available
• Is an order placed the quantity of the product will be adjusted
• Orders can have the status “ordered”, “shipped” and “canceled”
• Orders have at least user/product/order status
• Administrators can see and update all orders
• Users can only see their order

Authoriszation should be done with JWT.
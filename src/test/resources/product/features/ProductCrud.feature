Feature: Create, Read, Update, and Delete Products

  Scenario: Create a Product
    Given someone is interacting with the application
    When they create a product with:
      | name  | description                     | priceInBrl | imageUrl |
      | Pizza | The best pizza you'll ever eat! | 50.00      |          |
    Then they get the product "Pizza"
    And the product has:
      | name  | description                     | priceInBrl | imageUrl |
      | Pizza | The best pizza you'll ever eat! | 50.00      |          |
    And the product is hidden from consumers

  Scenario: Change Fields in a Product
    Given someone is interacting with the application
    And they create a product with:
      | name  | description                     | priceInBrl | imageUrl |
      | Pizza | The best pizza you'll ever eat! | 50.00      |          |
    When they update the product "Pizza" with:
      | name                           | description                     | priceInBrl | imageUrl |
      | Pizza Chicken and Cream-Cheese | The best pizza you'll ever eat! | 49.99      |          |
    Then they get the product "Pizza"
    And the product has:
      | name                           | description                     | priceInBrl | imageUrl |
      | Pizza Chicken and Cream-Cheese | The best pizza you'll ever eat! | 49.99      |          |
    And the product is hidden from consumers

  Scenario: Show a Product to Customers
    Given someone is interacting with the application
    And they create a product with:
      | name  | description                     | priceInBrl | imageUrl |
      | Pizza | The best pizza you'll ever eat! | 50.00      |          |
    When they show the product "Pizza" to customers
    Then they get the product "Pizza"
    And the product is shown to consumers

  Scenario: Hide a Product from Customers
    Given someone is interacting with the application
    And they create a product with:
      | name  | description                     | priceInBrl | imageUrl |
      | Pizza | The best pizza you'll ever eat! | 50.00      |          |
    When they hide the product "Pizza" from customers
    Then they get the product "Pizza"
    And the product is hidden from consumers

  Scenario: Archive a Product
    Given someone is interacting with the application
    And they create a product with:
      | name  | description                     | priceInBrl | imageUrl |
      | Pizza | The best pizza you'll ever eat! | 50.00      |          |
    When they archive the product "Pizza"
    And they get the product "Pizza"
    Then the product is archived

  Scenario: Find All Products to Show to Customers
    Given someone is interacting with the application
    And they create the following products:
      | name             | description                          | priceInBrl | imageUrl |
      | Pizza            | The best pizza you'll ever eat!      | 50.00      |          |
      | Hamburguer       | The best hamburguer you'll ever eat! | 25.00      |          |
      | Brazilian Pastel | Choose this.                         | 21.50      |          |
    And they show the product "Brazilian Pastel" to customers
    And they archive the product "Hamburguer"
    When they fetch all products to show customers
    Then the following products are returned:
      | name             | description  | priceInBrl | imageUrl |
      | Brazilian Pastel | Choose this. | 21.50      |          |

  Scenario: Find All Products
    Given someone is interacting with the application
    And they create the following products:
      | name             | description                          | priceInBrl | imageUrl |
      | Pizza            | The best pizza you'll ever eat!      | 50.00      |          |
      | Hamburguer       | The best hamburguer you'll ever eat! | 25.00      |          |
      | Brazilian Pastel | Choose this.                         | 21.50      |          |
    When they fetch all products
    Then the following products are returned:
      | name             | description                          | priceInBrl | imageUrl |
      | Pizza            | The best pizza you'll ever eat!      | 50.00      |          |
      | Hamburguer       | The best hamburguer you'll ever eat! | 25.00      |          |
      | Brazilian Pastel | Choose this.                         | 21.50      |          |

  Scenario: Fail When Product Searched for Does Not Exist
    Given someone is interacting with the application
    And they create a product with:
      | name  | description                     | priceInBrl | imageUrl |
      | Pizza | The best pizza you'll ever eat! | 50.00      |          |
    When they get the product "Hamburger"
    Then they receive an error of product not found
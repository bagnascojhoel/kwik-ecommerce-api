package br.com.bagnascojhoel.kwik_ecommerce.product.application.behavior;

import br.com.bagnascojhoel.kwik_ecommerce.product.application.ProductApplicationService;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.Product;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductId;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductNotFoundException;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.SaveProductCommand;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public final class ProductCrudSteps {

    private final ProductApplicationService productApplicationService;

    private ClientState clientState;

    @Before
    public void before() {
        this.clientState = new ClientState();
    }

    @Given("someone is interacting with the application")
    public void someoneIsInteractingWithTheApplication() {
    }

    @Given("they fetch the product by its id")
    public void theyFetchTheProductByItsId() {
        var productId = this.clientState.productId;

        Product product;
        try {
            product = this.productApplicationService.getProductById(productId);
            this.clientState.product = product;
        } catch (Throwable throwable) {
            this.clientState.error = throwable;
        }
    }

    @Given("they show the {string} product")
    public void theyShowTheXProduct(@NonNull final String productName) {
        var productId = this.clientState.getCreatedProductIdByName(productName);

        this.productApplicationService.showProduct(productId);
    }

    @Given("they archive the {string} product")
    public void theyArchiveTheXProduct(@NonNull final String productName) {
        var productId = this.clientState.getCreatedProductIdByName(productName);

        this.productApplicationService.archiveProduct(productId);
    }

    @When("they create the following products:")
    @When("they create a product with:")
    public void theyCreateProduct(final List<SaveProductCommand> commands) {
        for (SaveProductCommand command : commands) {
            this.clientState.productId = this.productApplicationService.saveProduct(command);
            this.clientState.createdProducts.put(command.getName(), this.clientState.productId);
        }
    }

    @When("they update the product {string} with:")
    public void theyUpdateAProductWith(final String initialProductName, final SaveProductCommand command) {
        var productId = this.clientState.getCreatedProductIdByName(initialProductName);
        var productWithCorrectId = command.toBuilder().id(productId).build();
        this.clientState.productId = this.productApplicationService.saveProduct(productWithCorrectId);
    }

    @When("they archive the product by its id")
    public void theyArchiveTheProductByItsId() {
        this.productApplicationService.archiveProduct(this.clientState.productId);
    }

    @When("they show the product to customers")
    public void theyShowTheProductToCustomer() {
        this.productApplicationService.showProduct(this.clientState.productId);
    }

    @When("they hide the product from customers")
    public void theyHideTheProductFromCustomers() {
        this.productApplicationService.hideProduct(this.clientState.productId);
    }

    @When("they fetch all products to show customers")
    public void theyFetchAllProductsToShowToCustomers() {
        this.clientState.fetchedProducts = this.productApplicationService.findAllProductsToShowCustomers();
    }

    @When("they fetch all products")
    public void theyFetchAllProducts() {
        this.clientState.fetchedProducts = this.productApplicationService.findAllProducts();
    }

    @When("they get the product {string}")
    public void theyGetTheProduct(final String productName) {
        ProductId productId = ProductId.generate();
        try {
            productId = this.clientState.getCreatedProductIdByName(productName);
        } catch (IllegalStateException ignored) {
        }

        try {
            this.clientState.product = this.productApplicationService.getProductById(productId);
        } catch (Throwable throwable) {
            this.clientState.error = throwable;
        }
    }

    @Then("the product is shown to consumers")
    public void theProductShouldBeShownToCustomers() {
        var product = this.clientState.product;
        assertThat(product).isNotNull();
        assertThat(product.canBeShownToCustomer()).isTrue();
    }

    @Then("the product is hidden from consumers")
    public void theProductShouldBeNotSellable() {
        var product = this.clientState.product;
        assertThat(product).isNotNull();
        assertThat(product.canBeShownToCustomer()).isFalse();
    }

    @Then("the product is archived")
    public void theProductIsArchived() {
        var product = this.clientState.product;
        assertThat(product.isArchived()).isTrue();
    }

    @Then("the product has:")
    public void theProductHas(final Product product) {
        assertThat(this.clientState.product).isEqualTo(product);
    }

    @Then("the following products are returned:")
    public void theFollowingProductsAreReturned(final List<Product> expectedProducts) {
        assertThat(this.clientState.fetchedProducts)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "state")
                .containsAll(expectedProducts);
    }

    @Then("they receive an error of product not found")
    public void theyReceiveAnErrorOfProductNotFound() {
        assertThat(this.clientState.product).isNull();
        assertThat(this.clientState.error)
                .usingRecursiveComparison()
                .isEqualTo(new ProductNotFoundException());
    }

    @DataTableType
    public Product product(final Map<String, String> source) {
        return Product.builder()
                .id(this.clientState.productId != null ? this.clientState.productId : ProductId.generate())
                .name(source.get("name"))
                .description(source.get("description"))
                .priceInBrl(new BigDecimal(source.get("priceInBrl")))
                .imageUrl(source.get("imageUrl"))
                .build();
    }

    @DataTableType
    public SaveProductCommand saveProductCommand(final Map<String, String> source) {
        return SaveProductCommand.builder()
                .id(this.clientState.productId != null ? this.clientState.productId : ProductId.generate())
                .name(source.get("name"))
                .description(source.get("description"))
                .priceInBrl(new BigDecimal(source.get("priceInBrl")))
                .imageUrl(source.get("imageUrl"))
                .build();
    }

    @NoArgsConstructor
    public static final class ClientState {
        private final Map<String, ProductId> createdProducts = new LinkedHashMap<>();
        private ProductId productId;
        private Product product;
        private List<Product> fetchedProducts;
        private Throwable error;

        public ProductId getCreatedProductIdByName(final String productName) {
            return Optional.ofNullable(this.createdProducts.get(productName))
                    .orElseThrow(() -> new IllegalStateException("product " + productName + " with name does not exist"));
        }
    }

}

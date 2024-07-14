package br.com.bagnascojhoel.kwik_ecommerce.product.application.behavior;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.bagnascojhoel.kwik_ecommerce.product.application.ProductApplicationService;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.Product;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductId;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductNotFoundException;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductState;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.SaveProductCommand;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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

  @Given("they show the product {string} to customers")
  public void theyShowTheXProduct(@NonNull final String productName) {
    var productId = this.clientState.getCreatedProductIdByName(productName);

    this.productApplicationService.saveProduct(productId, SaveProductCommand.builder()
        .productState(ProductState.SHOWN)
        .build());
  }

  @Given("they archive the product {string}")
  public void theyArchiveTheXProduct(@NonNull final String productName) {
    var productId = this.clientState.getCreatedProductIdByName(productName);

    this.productApplicationService.saveProduct(productId, SaveProductCommand.builder()
        .productState(ProductState.ARCHIVED)
        .build());
  }

  @Given("they hide the product {string} from customers")
  public void theyHideTheXProduct(@NonNull final String productName) {
    var productId = this.clientState.getCreatedProductIdByName(productName);

    this.productApplicationService.saveProduct(productId, SaveProductCommand.builder()
        .productState(ProductState.HIDDEN)
        .build());
  }

  @When("they create the following products:")
  @When("they create a product with:")
  public void theyCreateProduct(final List<SaveProductCommand> commands) {
    for (SaveProductCommand command : commands) {
      this.clientState.product = this.productApplicationService.saveProduct(null, command);
      this.clientState.createdProducts.put(command.getName(), this.clientState.product.getId());
    }
  }

  @When("they update the product {string} with:")
  public void theyUpdateAProductWith(
      final String initialProductName,
      final SaveProductCommand command
  ) {
    var productId = this.clientState.getCreatedProductIdByName(initialProductName);
    this.clientState.product = this.productApplicationService.saveProduct(
        productId,
        command);
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
    assertThat(this.clientState.error)
        .usingRecursiveComparison()
        .isEqualTo(new ProductNotFoundException());
  }

  @DataTableType
  public Product product(final Map<String, String> source) {
    return Product.builder()
        .id(this.clientState.product != null ? this.clientState.product.getId()
            : ProductId.generate())
        .name(source.get("name"))
        .description(source.get("description"))
        .priceInBrl(new BigDecimal(source.get("priceInBrl")))
        .imageUrl(source.get("imageUrl"))
        .build();
  }

  @DataTableType
  public SaveProductCommand saveProductCommand(final Map<String, String> source) {
    return SaveProductCommand.builder()
        .name(source.get("name"))
        .description(source.get("description"))
        .priceInBrl(new BigDecimal(source.get("priceInBrl")))
        .imageUrl(source.get("imageUrl"))
        .build();
  }

  @NoArgsConstructor
  public static final class ClientState {

    private final Map<String, ProductId> createdProducts = new LinkedHashMap<>();
    private Product product;
    private ProductId productId;
    private List<Product> fetchedProducts;
    private Throwable error;

    public ProductId getCreatedProductIdByName(final String productName) {
      return Optional.ofNullable(this.createdProducts.get(productName))
          .orElseThrow(() -> new IllegalStateException(
              "product with name '" + productName + "' does not exist"));
    }
  }

}

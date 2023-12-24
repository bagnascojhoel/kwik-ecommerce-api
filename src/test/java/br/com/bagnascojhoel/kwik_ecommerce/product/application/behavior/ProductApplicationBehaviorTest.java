package br.com.bagnascojhoel.kwik_ecommerce.product.application.behavior;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = "classpath:/product/features",
        glue = "br.com.bagnascojhoel.kwik_ecommerce.product.application.behavior",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class ProductApplicationBehaviorTest {
}

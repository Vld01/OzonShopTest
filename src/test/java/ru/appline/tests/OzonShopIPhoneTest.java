package ru.appline.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.Test;
import ru.appline.base.BaseTests;

public class RenAllureTest extends BaseTests {

    @Epic("Тест для Озон")
    @Feature(value = "Тест на покупку iPhone")
    @Test
    public void depositInRenTest1(){
        app.getStartPage()
            .selectProductSearch("iphone")
            .clickProductSearch(100_000)
            .fillCheckbox("Высокий рейтинг", "true")
            .fillCheckbox("NFC", "true")
            .fillCheckbox(8)
            .checkProductsInBasket(8)
            .deleteAllProducts();
    }
}

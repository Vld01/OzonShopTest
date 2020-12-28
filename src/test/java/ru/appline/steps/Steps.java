package ru.appline.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import ru.appline.framework.managers.PageManager;


public class Steps {
    /**
     * Менеджер страниц
     * @see PageManager#getPageManager()
     */
    private PageManager app = PageManager.getPageManager();

    @Когда("^Загружаем стартовую страницу$")
    public void getInitialPage(){
        app.getStartPage();
    }

    @Когда("^Выполняем поиск продукта '(.*)'$")
    public void selectProductSearch(String nameProduct) {app.getStartPage().selectProductSearch(nameProduct);}

    @Когда("^Ограничение цены до '(\\d+)'$")
    public void clickProductSearch(Integer upLimit) {app.getSearchPage().clickProductSearch(upLimit);}

    @Когда("^Заполняем поле значением$")
    public void fillCheckbox(DataTable dataTable) {
        dataTable.cells().forEach(
                raw -> {
                    app.getSearchPage().fillCheckbox(raw.get(0), raw.get(1));
                });
    }

    @Когда("^Добавляем в корзину первые '(\\d+)' четных товара$")
    public void fillCheckbox(Integer countProduct) {app.getSearchPage().fillCheckbox(countProduct); }

    @Тогда("^Проверяем наличие '(\\d+)' товаров в корзине$")
    public void checkProductsInBasket(Integer countProduct) { app.getBasketPage().checkProductsInBasket(countProduct);}

    @Тогда("^Добавлеем в отчет Аллюра текстовый файл$")
    public void addTextFile() { app.getBasketPage().addTextFile();}

    @Тогда("^Удаляем все товары из корзины$")
    public void deleteAllProducts() { app.getBasketPage().deleteAllProducts();}
}

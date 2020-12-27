package ru.appline.framework.managers;

import ru.appline.framework.pages.BasePage;
import ru.appline.framework.pages.BasketPage;
import ru.appline.framework.pages.SearchPage;
import ru.appline.framework.pages.StartPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для управления страницами
 */
public class PageManager {

    private static List<BasePage> listPage = new ArrayList<>();

    /**
     * Менеджер страниц
     */
    private static PageManager pageManager;

    /**
     * Стартовая страница
     */
    static StartPage startPage;

    /**
     * Страница поиска товара
     */
    static SearchPage searchPage;

    /**
     * Страница корзины
     */
    static BasketPage basketPage;

    /**
     * Конструктор специально сделал приватным (синглтон)
     * @see PageManager#getPageManager()
     */
    private PageManager() {
    }

    /**
     * Ленивая инициализация ManagerPages
     *
     * @return ManagerPages
     */
    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    /**
     * Ленивая инициализация {@link ru.appline.framework.pages.StartPage}
     *
     * @return StartPage
     */
    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
            listPage.add(startPage);
        }
        return startPage;
    }

    /**
     * Ленивая инициализация {@link ru.appline.framework.pages.SearchPage}
     *
     * @return SearchPage
     */
    public SearchPage getSearchPage() {
        if (searchPage == null) {
            searchPage = new SearchPage();
            listPage.add(searchPage);
        }
        return searchPage;
    }


    /**
     * Ленивая инициализация {@link ru.appline.framework.pages.BasketPage}
     *
     * @return BasketPage
     */
    public BasketPage getBasketPage() {
        if (basketPage == null) {
            basketPage = new BasketPage();
            listPage.add(basketPage);
        }
        return basketPage;
    }

    /**
     *  Метод обнуления страниц в сессии
     *
     */
    public static void cleanListPage() {
         for (BasePage page: listPage) {
             if (page != null) {
                 page = null;
             }
         }
    }
}

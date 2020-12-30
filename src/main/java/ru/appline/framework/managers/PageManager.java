package ru.appline.framework.managers;

import ru.appline.framework.pages.BasePage;
import ru.appline.framework.pages.BasketPage;
import ru.appline.framework.pages.SearchPage;
import ru.appline.framework.pages.StartPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Класс для управления страницами
 */
public class PageManager {

    private static HashMap<String, BasePage> mapPage = new HashMap<>();

    /**
     * Менеджер страниц
     */
    private static PageManager pageManager;

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
        if (mapPage.isEmpty() | mapPage.get("StartPage") == null) {
            mapPage.put("StartPage", new StartPage());
        }
        return (StartPage) mapPage.get("StartPage");
    }

    /**
     * Ленивая инициализация {@link ru.appline.framework.pages.SearchPage}
     *
     * @return SearchPage
     */
    public SearchPage getSearchPage() {
        if (mapPage.isEmpty() | mapPage.get("SearchPage") == null) {
            mapPage.put("SearchPage", new SearchPage());
        }
        return (SearchPage) mapPage.get("SearchPage");
    }


    /**
     * Ленивая инициализация {@link ru.appline.framework.pages.BasketPage}
     *
     * @return BasketPage
     */
    public BasketPage getBasketPage() {
        if (mapPage.isEmpty() | mapPage.get("BasketPage") == null) {
            mapPage.put("BasketPage", new BasketPage());
        }
        return (BasketPage) mapPage.get("BasketPage");
    }

    /**
     *  Метод обнуления страниц в сессии
     *
     */
    public static void cleanMapPage() {
        mapPage.clear();
    }
}

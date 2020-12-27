package ru.appline.base;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import ru.appline.framework.managers.InitManager;
import ru.appline.framework.managers.PageManager;

import static ru.appline.framework.managers.DriverManager.getDriver;
import static ru.appline.framework.managers.InitManager.props;
import static ru.appline.framework.utils.PropConst.APP_URL;

public class BaseTests {

    /**
     * Менеджер страниц
     * @see PageManager#getPageManager()
     */
    protected PageManager app = PageManager.getPageManager();

    @BeforeClass
    public static void beforeAll() {
        InitManager.initFramework();
    }

    @Before
    public void beforeEach(){
        getDriver().get(props.getProperty(APP_URL));
    }

    @AfterClass
    public static void afterAll() {
        InitManager.quitFramework();
    }

}

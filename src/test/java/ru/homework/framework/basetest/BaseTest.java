package ru.homework.framework.basetest;

import org.junit.AfterClass;
import org.junit.Before;
import ru.homework.framework.managers.ManagerPages;

import static ru.homework.framework.managers.InitManager.*;

public class BaseTest {
    protected ManagerPages app = ManagerPages.getManagerPages();

    @Before
    public void before() {
        initFramework();
    }

    @AfterClass
    public static void afterClass() {
        quitFramework();
    }

}

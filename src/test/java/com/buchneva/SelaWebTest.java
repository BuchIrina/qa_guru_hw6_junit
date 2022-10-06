package com.buchneva;

import com.buchneva.data.ItemHeaderMenu;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SelaWebTest {
    SelenideElement
            popup = $(".choice-city-popup__body");

    static Stream<Arguments> mainMenuShouldHaveSubmenuWithTextsTest() {
        return Stream.of(
                Arguments.of(ItemHeaderMenu.ЖЕНЩИНЫ, List.of("Новинки", "Верхняя одежда", "Комплекты")),
                Arguments.of(ItemHeaderMenu.ДЕВОЧКИ, List.of("Super tech outwear", "Новинки", "Верхняя одежда"))
        );
    }

    @BeforeEach
    void openSelaSite() {
        open("https://www.sela.ru/");
        if (popup.isDisplayed()) {
            $(".js-choice-city-close-popup").click();
        }
    }

    @ValueSource(strings = {"куртка", "брюки"})
    @ParameterizedTest(name = "Проверка работы поля Поиск по запросу {0}")
    void checkingSearchTest(String item) {
        $("#header_user_menu_search_link").click();
        $(".digi-search-form__input").setValue(item).pressEnter();
        $(".digi-product__main").shouldHave(text(item));
    }

    @CsvSource({
            "куртка, Найдено в категориях",
            "брюки классические, Найдено в категориях",
            "отвертка, По вашему запросу ничего не найдено"
    })
    @ParameterizedTest(name = "Проверка корректности вывода результата поиска по запросу {0}")
    void searchWithDifferentQueryTypeTest(String item, String textOfSearchResult) {
        $("#header_user_menu_search_link").click();
        $(".digi-search-form__input").setValue(item).pressEnter();
        $(".digi-main-scroll-wrapper").shouldHave(text(textOfSearchResult));
    }

    @EnumSource(ItemHeaderMenu.class)
    @ParameterizedTest(name = "Видимость эллементов основного меню {0}")
    void mainMenuIsVisibleTest(ItemHeaderMenu itemHeaderMenu) {
        $$(".header__aside-menu").find(text(itemHeaderMenu.name())).shouldBe(visible);
    }

    @MethodSource
    @ParameterizedTest(name = "Проверка что каждый элемент основного меню содержит пункты подменю {0}")
    void mainMenuShouldHaveSubmenuWithTextsTest(ItemHeaderMenu itemHeaderMenu, List<String> list) {
        $$(".header__main-menu-item.js-header-sub-menu-open").find(text(itemHeaderMenu.name())).click();
        $$("li.header__sub-menu-item a")
                .shouldHave(CollectionCondition.containExactTextsCaseSensitive(list));
    }


}

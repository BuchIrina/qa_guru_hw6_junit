package com.buchneva;

import com.buchneva.data.Locale;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class WebTest {

    static Stream<Arguments> selenideSiteButtonText() {
        return Stream.of(
                Arguments.of(Locale.EN, List.of("Quick start", "Docs", "FAQ", "Blog", "Javadoc", "Users", "Quotes")),
                Arguments.of(Locale.RU, List.of("С чего начать?", "Док", "ЧАВО", "Блог", "Javadoc", "Пользователи", "Отзывы"))
        );
    }

    @ValueSource(strings = {"Selenide", "JUnit"})
    @ParameterizedTest(name = "Checking the numbers of search results in Yandex on request {0}")
    void yandexSearchCommonTest(String testData) {
        open("https://ya.ru/");
        Configuration.holdBrowserOpen = true;
        $("#text").setValue(testData);
        $("button[type = 'submit']").click();
        $$("li.serp-item")
                .shouldHave(CollectionCondition.size(10))
                .first()
                .shouldHave(text(testData));
    }

    @CsvSource({
            "Selenide, Selenide - это фреймворк для автоматизированного тестирования",
            "JUnit, A programmer-oriented testing framework for Java"
    })
    @ParameterizedTest(name = "Checking the numbers of search results in Yandex on request {0}")
    void yandexSearchCommonTestDifferentExpectedText(String searchQuery, String expectedText) {
        open("https://ya.ru/");
        Configuration.holdBrowserOpen = true;
        $("#text").setValue(searchQuery);
        $("button[type = 'submit']").click();
        $$("li.serp-item")
                .shouldHave(CollectionCondition.size(10))
                .first()
                .shouldHave(text(expectedText));
    }

    @MethodSource
    @ParameterizedTest(name = "Checking button names for locale {0}")
    void selenideSiteButtonText(Locale locale, List<String> buttonTexts) {
        open("https://selenide.org/");
        $$("#languages a").find(text(locale.name())).click();
        $$(".main-menu-pages a").filter(visible)
                .shouldHave(CollectionCondition.texts(buttonTexts));
    }

    @EnumSource(Locale.class)
    @ParameterizedTest(name = "Checking button names for locale {0}")
    void checkLocaleTest(Locale locale){
        open("https://selenide.org/");
        $$("#languages a").find(text(locale.name())).shouldBe(visible);
    }
}

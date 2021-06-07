package ru.learning.addressbook.bdd;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//класс-запускатель для сценариев
@CucumberOptions(features = "classpath:bdd", plugin = {"pretty", "html:build/cucumber-report"})
//features - где именно находятся файлы с описанием сценариев
//plugin - формат вывода на консоль, директория отчёта
public class GroupTests extends AbstractTestNGCucumberTests {
}

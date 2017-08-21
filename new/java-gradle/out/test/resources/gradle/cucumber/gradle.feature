Feature: Gradle-Cucumber integration

  Scenario: Поиск наушников
    Given Зайти на "https://yandex.ru"
    When нажата ссылка "Маркет"
    And нажата ссылка "Электроника"
    And выбрано из списка "Наушники и Bluetooth-гарнитуры"
    And установлена цена "от" на значение "5000"
    And отмечен чекбокс "Beats"
    * выполнено ожидание в течение 10 секунд
    Then отображается "12" элементов
    Then Запомнить первый элемент в списке "товар"
    Given выполнить поиск по элементу "товар"
    * выполнено ожидание в течение 5 секунд
    And нажата кнопка "Найти"

    Then Наименование товара соотвествует запомненному значению "товар"

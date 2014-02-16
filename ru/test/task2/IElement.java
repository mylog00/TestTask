package ru.test.task2;

/**
 * Элемент
 */
public interface IElement {

    /** @return уникальный идентификатор элемента */
    long getId();

    /**
     * быстрая операция, гарантированно возвращает присвоенное ранее значение,
     * при этом не совершает никаких посторонних операций
     * @return текущий присвоенный номер
     */
    int getNumber();

    /**
     * Присваивает номер для элемента.

     * <p>Важно, что метод является очень трудоемким
     * (например, может делать запрос на update в базу или синхронно отправлять данные на внешний сервис),
     * по этой причине метод лучше вызывать как можно меньшее число раз.</p>
     *
     * @param number
     */
    void setupNumber(int number);

}

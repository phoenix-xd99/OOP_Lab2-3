package edu.java.lab2;

/**
 * Собственное исключение, сигнализирующее о некорректном
 * значении цены: цена не является числом или меньше нуля.
 *
 * @author Белякова С.Е.
 * @version 1.0
 */
public class InvalidPriceException extends Exception {

    /**
     * Конструктор с сообщением об ошибке.
     *
     * @param message текст сообщения об ошибке
     */
    public InvalidPriceException(String message) {
        super(message);
    }
}
package edu.java.lab2;

/**
 * Собственное исключение, сигнализирующее о том, что
 * обязательное поле формы осталось незаполненным.
 *
 * @author Белякова С.Е.
 * @version 1.0
 */
public class EmptyFieldException extends Exception {

    /**
     * Конструктор с сообщением об ошибке.
     *
     * @param message текст сообщения об ошибке
     */
    public EmptyFieldException(String message) {
        super(message);
    }
}
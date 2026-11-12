package edu.java.lab2;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Загрузка каталога коньков из XML-файла с использованием DOM.
 *
 * @author Белякова С.Е.
 * @version 6.0
 */
public class FileLoader {

    /**
     * Запрашивает у пользователя XML-файл и загружает его данные в таблицу.
     *
     * @param parent окно-владелец диалогов
     * @param model  модель таблицы (очищается перед загрузкой)
     * @return путь к загруженному файлу или null
     *         (пользователь отказался или произошла ошибка)
     */
    public static String load(Frame parent, DefaultTableModel model) {
        FileDialog fd = new FileDialog(parent, "Открытие каталога", FileDialog.LOAD);
        fd.setFile("*.xml");
        fd.setVisible(true);
        if (fd.getFile() == null) return null; // нажата «Отмена»

        String fileName = fd.getDirectory() + fd.getFile();

        try {
            // Создание парсера и чтение документа из файла
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new File(fileName));
            // Нормализация документа
            doc.getDocumentElement().normalize();

            if (!"skatelist".equals(doc.getDocumentElement().getNodeName())) {
                JOptionPane.showMessageDialog(parent,
                        "Файл не содержит каталог коньков (корневой элемент должен быть skatelist)",
                        "Неверный формат", JOptionPane.WARNING_MESSAGE);
                return null;
            }

            // Документ разобран успешно: очищаем таблицу и заполняем её
            model.setRowCount(0);
            NodeList list = doc.getElementsByTagName("skate");
            for (int i = 0; i < list.getLength(); i++) {
                NamedNodeMap attrs = list.item(i).getAttributes();
                model.addRow(new Object[] {
                        attr(attrs, "brand"), attr(attrs, "model"), attr(attrs, "size"),
                        attr(attrs, "level"), attr(attrs, "price") });
            }
            return fileName;

        } catch (ParserConfigurationException e) {
            error(parent, "Ошибка создания XML-парсера", e);
        } catch (SAXException e) {
            error(parent, "Файл не является корректным XML-документом", e);
        } catch (IOException e) {
            error(parent, "Ошибка чтения файла", e);
        }
        return null;
    }

    /** Значение атрибута или пустая строка, если атрибута нет. */
    private static String attr(NamedNodeMap attrs, String name) {
        Node n = attrs.getNamedItem(name);
        return (n == null) ? "" : n.getNodeValue();
    }

    private static void error(Frame parent, String msg, Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(parent, msg, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}

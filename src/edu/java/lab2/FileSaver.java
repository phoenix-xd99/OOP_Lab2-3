package edu.java.lab2;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Сохранение каталога коньков в XML-файл с использованием DOM.
 *
 * @author Белякова С.Е.
 * @version 6.0
 */
public class FileSaver {

    /**
     * Сохраняет данные таблицы в XML-файл.
     * Если currentFile равен null, пользователю показывается диалог выбора файла.
     *
     * @param parent      окно-владелец диалогов
     * @param model       модель таблицы с данными
     * @param currentFile путь к текущему файлу или null
     * @return путь к файлу, в который сохранены данные, или null
     *         (пользователь отказался или произошла ошибка)
     */
    public static String save(Frame parent, DefaultTableModel model, String currentFile) {
        String fileName = currentFile;

        if (fileName == null) {
            FileDialog fd = new FileDialog(parent, "Сохранение каталога", FileDialog.SAVE);
            fd.setFile("*.xml");
            fd.setVisible(true);
            if (fd.getFile() == null) return null; // нажата «Отмена»
            fileName = fd.getDirectory() + fd.getFile();
            if (!fileName.toLowerCase().endsWith(".xml")) fileName += ".xml";
        }

        try {
            writeXml(model, new File(fileName));
            JOptionPane.showMessageDialog(parent, "Каталог сохранён в файл:\n" + fileName);
            return fileName;
        } catch (ParserConfigurationException e) {
            error(parent, "Ошибка создания XML-парсера", e);
        } catch (TransformerException e) {
            error(parent, "Ошибка работы XML-преобразователя", e);
        } catch (IOException e) {
            error(parent, "Ошибка записи в файл", e);
        }
        return null;
    }

    /**
     * Строит DOM-документ по данным таблицы и записывает его в файл.
     *
     * @param model модель таблицы
     * @param file  файл для записи
     * @throws ParserConfigurationException ошибка создания парсера
     * @throws TransformerException         ошибка преобразователя
     * @throws IOException                  ошибка ввода-вывода
     */
    private static void writeXml(DefaultTableModel model, File file)
            throws ParserConfigurationException, TransformerException, IOException {

        // Создание парсера и пустого документа
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.newDocument();

        // Корневой элемент
        Element root = doc.createElement("skatelist");
        doc.appendChild(root);

        // Дочерние элементы skate, атрибуты берутся из ячеек таблицы
        for (int i = 0; i < model.getRowCount(); i++) {
            Element skate = doc.createElement("skate");
            root.appendChild(skate);
            skate.setAttribute("brand", String.valueOf(model.getValueAt(i, 0)));
            skate.setAttribute("model", String.valueOf(model.getValueAt(i, 1)));
            skate.setAttribute("size",  String.valueOf(model.getValueAt(i, 2)));
            skate.setAttribute("level", String.valueOf(model.getValueAt(i, 3)));
            skate.setAttribute("price", String.valueOf(model.getValueAt(i, 4)));
        }

        // Запись дерева в файл без таблицы стилей
        Transformer trans = TransformerFactory.newInstance().newTransformer();
        trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
        FileOutputStream out = new FileOutputStream(file);
        try {
            trans.transform(new DOMSource(doc), new StreamResult(out));
        } finally {
            out.close();
        }
    }

    private static void error(Frame parent, String msg, Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(parent, msg, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}

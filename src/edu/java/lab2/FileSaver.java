package edu.java.lab2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

/**
 * Класс для сохранения данных таблицы в текстовый файл.
 * Использует FileDialog для выбора файла и BufferedWriter для записи.
 *
 * @author Белякова С.Е.
 * @version 1.1
 */
public class FileSaver {

    /**
     * Сохранение содержимого таблицы в файл.
     * Если передан известный путь — сохраняет без диалога.
     * Иначе открывает диалог выбора файла.
     *
     * @param parent      родительское окно
     * @param tableModel  модель таблицы
     * @param knownFile   известный путь к файлу (может быть null)
     * @return путь к сохранённому файлу или null при отмене
     */
    public static String save(Frame parent, DefaultTableModel tableModel, String knownFile) {
        String fileName = knownFile;

        if (fileName == null) {
            FileDialog dialog = new FileDialog(parent, "Сохранение каталога", FileDialog.SAVE);
            dialog.setFile("skates.txt");
            dialog.setVisible(true);

            if (dialog.getFile() == null) return null;
            fileName = dialog.getDirectory() + dialog.getFile();
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    line.append(tableModel.getValueAt(i, j));
                    if (j < tableModel.getColumnCount() - 1) line.append("|");
                }
                writer.write(line.toString());
                writer.newLine();
            }
            writer.close();

            JOptionPane.showMessageDialog(parent,
                    "Данные успешно сохранены в файл:\n" + fileName);
            return fileName;

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent,
                    "Ошибка записи в файл: " + ex.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}

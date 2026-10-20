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
 * @version 1.0
 */
public class FileSaver {

    /**
     * Сохранение содержимого таблицы в файл.
     *
     * @param parent    родительское окно для диалога
     * @param tableModel модель таблицы с данными
     */
    public static void save(Frame parent, DefaultTableModel tableModel) {
        FileDialog dialog = new FileDialog(parent, "Сохранение каталога", FileDialog.SAVE);
        dialog.setFile("*.txt");
        dialog.setVisible(true);

        String fileName = dialog.getDirectory() + dialog.getFile();
        if (dialog.getFile() == null) return;

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
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent,
                    "Ошибка записи в файл: " + ex.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
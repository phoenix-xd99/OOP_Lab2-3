package edu.java.lab2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

/**
 * Класс для загрузки данных из текстового файла в таблицу.
 * Использует FileDialog для выбора файла и BufferedReader для чтения.
 *
 * @author Белякова С.Е.
 * @version 1.0
 */
public class FileLoader {

    /**
     * Загрузка данных из файла в таблицу.
     *
     * @param parent     родительское окно для диалога
     * @param tableModel модель таблицы, куда загружаются данные
     */
    public static void load(Frame parent, DefaultTableModel tableModel) {
        FileDialog dialog = new FileDialog(parent, "Открытие каталога", FileDialog.LOAD);
        dialog.setFile("*.txt");
        dialog.setVisible(true);

        String fileName = dialog.getDirectory() + dialog.getFile();
        if (dialog.getFile() == null) return;

        try {
            // Очищаем таблицу перед загрузкой
            tableModel.setRowCount(0);

            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    tableModel.addRow(parts);
                }
            }

            reader.close();

            JOptionPane.showMessageDialog(parent,
                    "Данные успешно загружены из файла:\n" + fileName);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(parent,
                    "Файл не найден: " + fileName,
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent,
                    "Ошибка чтения из файла: " + ex.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
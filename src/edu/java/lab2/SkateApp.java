package edu.java.lab2;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Класс, реализующий экранную форму «Каталог коньков».
 * Содержит обработчики событий для кнопок, меню,
 * поля поиска, выпадающих списков и таблицы.
 *
 * @author Белякова С.Е.
 * @version 2.0
 */
public class SkateApp extends JFrame {

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem newMenuIt, openMenuIt, saveMenuIt, exitMenuIt;

    private JToolBar toolBar;
    private Box box1, box2, box3;

    private JButton buttonSave, buttonOpen, buttonAdd,
                    buttonEdit, buttonDelete, buttonProfile, buttonSearch;

    private JLabel searchLabel;
    private JComboBox<String> searchCombo;
    private JTextField searchField;

    private JLabel sortingLabel;
    private JComboBox<String> sortingCombo;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextArea textArea;

    /**
     * Конструктор. Создаёт компоненты, размещает их и подключает слушателей.
     */
    public SkateApp() {
        super("Каталог коньков");

        setSize(1200, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        String[] searchItems = {"Ключевому слову", "Бренду", "Модели",
                                "Размеру", "Уровню", "Цене"};
        String[] sortItems   = {"Бренду", "Модели", "Размеру",
                                "Уровню", "Цене"};
        Object[] headers = {"Бренд", "Модель", "Размер", "Уровень", "Цена"};
        Object[][] data = new Object[0][5];

        searchLabel  = new JLabel("Поиск по:");
        searchCombo  = new JComboBox<>(searchItems);
        searchField  = new JTextField("Введите данные", 15);
        buttonSearch = new JButton("Поиск");

        sortingLabel = new JLabel("Сортировка по");
        sortingCombo = new JComboBox<>(sortItems);

        tableModel = new DefaultTableModel(data, headers);
        table      = new JTable(tableModel);

        textArea = new JTextArea(20, 30);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        menuBar    = new JMenuBar();
        fileMenu   = new JMenu("Файл");
        newMenuIt  = new JMenuItem("Новое");
        openMenuIt = new JMenuItem("Открыть");
        saveMenuIt = new JMenuItem("Сохранить");
        exitMenuIt = new JMenuItem("Выйти");

        // Кнопки: иконка + текст
        buttonSave    = new JButton("Сохранить",  new ImageIcon("./16x16/29.png"));
        buttonOpen    = new JButton("Открыть",    new ImageIcon("./16x16/46.png"));
        buttonProfile = new JButton("Профиль",    new ImageIcon("./16x16/11.png"));
        buttonAdd     = new JButton("Добавить",   new ImageIcon("./16x16/20.png"));
        buttonDelete  = new JButton("Удалить",    new ImageIcon("./16x16/17.png"));
        buttonEdit    = new JButton("Изменить",   new ImageIcon("./16x16/36.png"));

        buttonSave.setToolTipText("Сохранить каталог коньков");
        buttonOpen.setToolTipText("Открыть каталог коньков");
        buttonAdd.setToolTipText("Добавить модель");
        buttonEdit.setToolTipText("Редактировать модель");
        buttonDelete.setToolTipText("Удалить модель");
        buttonProfile.setToolTipText("Профиль пользователя");

        createMenu();
        createToolBar();

        getContentPane().add(toolBar, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        getContentPane().add(new JScrollPane(textArea), BorderLayout.EAST);

        // ===== Подключение слушателей событий (лаба №3) =====
        ActionListener actionHandler = new ActionHandler();
        MouseListener mouseHandler = new MouseHandler();
        ListSelectionListener listHandler = new ListHandler();

        newMenuIt.addActionListener(actionHandler);
        openMenuIt.addActionListener(actionHandler);
        saveMenuIt.addActionListener(actionHandler);
        exitMenuIt.addActionListener(actionHandler);

        buttonSave.addActionListener(actionHandler);
        buttonOpen.addActionListener(actionHandler);
        buttonAdd.addActionListener(actionHandler);
        buttonEdit.addActionListener(actionHandler);
        buttonDelete.addActionListener(actionHandler);
        buttonProfile.addActionListener(actionHandler);
        buttonSearch.addActionListener(actionHandler);

        searchCombo.addActionListener(actionHandler);
        sortingCombo.addActionListener(actionHandler);

        searchField.addMouseListener(mouseHandler);

        table.getSelectionModel().addListSelectionListener(listHandler);
    }

    /**
     * Создание строки меню «Файл».
     */
    private void createMenu() {
        fileMenu.add(newMenuIt);
        fileMenu.add(openMenuIt);
        fileMenu.add(saveMenuIt);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuIt);

        menuBar.add(fileMenu);
        menuBar.add(Box.createHorizontalGlue());
        setJMenuBar(menuBar);
    }

    /**
     * Создание панели инструментов из трёх горизонтальных блоков.
     */
    private void createToolBar() {
        toolBar = new JToolBar("Инструментальная панель");

        box1 = Box.createHorizontalBox();
        box1.add(buttonSave);
        box1.add(Box.createHorizontalStrut(3));
        box1.add(buttonOpen);
        box1.add(Box.createHorizontalStrut(3));
        box1.add(buttonProfile);
        box1.add(Box.createHorizontalStrut(3));
        box1.add(new JSeparator(SwingConstants.VERTICAL));
        box1.add(Box.createHorizontalStrut(3));
        box1.add(buttonAdd);
        box1.add(Box.createHorizontalStrut(3));
        box1.add(buttonDelete);
        box1.add(Box.createHorizontalStrut(3));
        box1.add(buttonEdit);

        box2 = Box.createHorizontalBox();
        box2.add(searchLabel);
        box2.add(Box.createHorizontalStrut(6));
        box2.add(searchCombo);
        box2.add(Box.createHorizontalStrut(6));
        box2.add(searchField);
        box2.add(Box.createHorizontalStrut(6));
        box2.add(buttonSearch);

        box3 = Box.createHorizontalBox();
        box3.add(sortingLabel);
        box3.add(Box.createHorizontalStrut(6));
        box3.add(sortingCombo);

        toolBar.add(box1, BorderLayout.WEST);
        toolBar.add(Box.createHorizontalStrut(12));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(Box.createHorizontalStrut(12));
        toolBar.add(box2);
        toolBar.add(Box.createHorizontalStrut(12));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(Box.createHorizontalStrut(12));
        toolBar.add(box3, BorderLayout.EAST);

        toolBar.setFloatable(false);
    }

    /**
     * Обработчик действий для кнопок и пунктов меню.
     */
    private class ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();

            // ===== Меню «Файл» =====
            if (src == newMenuIt) {
                tableModel.setRowCount(0);
                textArea.setText("");
                JOptionPane.showMessageDialog(SkateApp.this,
                        "Создан новый каталог");
            } else if (src == openMenuIt) {
                JOptionPane.showMessageDialog(SkateApp.this,
                        "Открытие каталога из файла");
            } else if (src == saveMenuIt) {
                JOptionPane.showMessageDialog(SkateApp.this,
                        "Сохранение каталога в файл");
            } else if (src == exitMenuIt) {
                dispose();
            }

            // ===== Кнопки панели инструментов =====
            else if (src == buttonSave) {
                JOptionPane.showMessageDialog(SkateApp.this,
                        "Сохранение каталога коньков");
            } else if (src == buttonOpen) {
                JOptionPane.showMessageDialog(SkateApp.this,
                        "Открытие каталога коньков");
            } else if (src == buttonProfile) {
                JOptionPane.showMessageDialog(SkateApp.this,
                        "Профиль пользователя:\nБелякова С.Е.\nГруппа 5315");
            } else if (src == buttonAdd) {
                addRow();
            } else if (src == buttonDelete) {
                deleteRow();
            } else if (src == buttonEdit) {
                editRow();
            } else if (src == buttonSearch) {
                String criterion = (String) searchCombo.getSelectedItem();
                String value = searchField.getText();
                JOptionPane.showMessageDialog(SkateApp.this,
                        "Поиск по: " + criterion + "\nЗначение: " + value);
            }

            // ===== Выпадающие списки =====
            else if (src == searchCombo) {
                searchField.setText("Введите данные");
            } else if (src == sortingCombo) {
                JOptionPane.showMessageDialog(SkateApp.this,
                        "Сортировка по: " + sortingCombo.getSelectedItem());
            }
        }
    }

    /**
     * Добавление новой строки через диалоговое окно.
     */
    private void addRow() {
        JTextField brandF = new JTextField();
        JTextField modelF = new JTextField();
        JTextField sizeF  = new JTextField();
        JTextField levelF = new JTextField();
        JTextField priceF = new JTextField();

        Object[] message = {
                "Бренд:",   brandF,
                "Модель:",  modelF,
                "Размер:",  sizeF,
                "Уровень:", levelF,
                "Цена:",    priceF
        };

        int option = JOptionPane.showConfirmDialog(SkateApp.this, message,
                "Добавить модель коньков", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            tableModel.addRow(new Object[]{
                    brandF.getText(),
                    modelF.getText(),
                    sizeF.getText(),
                    levelF.getText(),
                    priceF.getText()
            });
        }
    }

    /**
     * Удаление выбранной строки с подтверждением.
     */
    private void deleteRow() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(SkateApp.this,
                    "Сначала выберите строку в таблице");
            return;
        }

        int option = JOptionPane.showConfirmDialog(SkateApp.this,
                "Удалить выбранную модель?", "Подтверждение удаления",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            tableModel.removeRow(row);
            textArea.setText("");
        }
    }

    /**
     * Редактирование выбранной строки через диалоговое окно.
     */
    private void editRow() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(SkateApp.this,
                    "Сначала выберите строку в таблице");
            return;
        }

        JTextField brandF = new JTextField((String) tableModel.getValueAt(row, 0));
        JTextField modelF = new JTextField((String) tableModel.getValueAt(row, 1));
        JTextField sizeF  = new JTextField((String) tableModel.getValueAt(row, 2));
        JTextField levelF = new JTextField((String) tableModel.getValueAt(row, 3));
        JTextField priceF = new JTextField((String) tableModel.getValueAt(row, 4));

        Object[] message = {
                "Бренд:",   brandF,
                "Модель:",  modelF,
                "Размер:",  sizeF,
                "Уровень:", levelF,
                "Цена:",    priceF
        };

        int option = JOptionPane.showConfirmDialog(SkateApp.this, message,
                "Редактировать модель", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            tableModel.setValueAt(brandF.getText(), row, 0);
            tableModel.setValueAt(modelF.getText(), row, 1);
            tableModel.setValueAt(sizeF.getText(),  row, 2);
            tableModel.setValueAt(levelF.getText(), row, 3);
            tableModel.setValueAt(priceF.getText(), row, 4);
        }
    }

    /**
     * Обработчик мыши для поля поиска.
     * По клику очищает текст-подсказку.
     */
    private class MouseHandler implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            if (searchField.getText().equals("Введите данные")) {
                searchField.setText("");
            }
        }
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
    }

    /**
     * Обработчик выбора строки в таблице.
     * Выводит информацию о выбранной модели в текстовую область.
     */
    private class ListHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) return;

            int row = table.getSelectedRow();
            if (row == -1) {
                textArea.setText("");
                return;
            }

            String brand = (String) tableModel.getValueAt(row, 0);
            String model = (String) tableModel.getValueAt(row, 1);
            String size  = (String) tableModel.getValueAt(row, 2);
            String level = (String) tableModel.getValueAt(row, 3);
            String price = (String) tableModel.getValueAt(row, 4);

            textArea.setText(
                    "Выбранная модель:\n" +
                    "Бренд: " + brand + "\n" +
                    "Модель: " + model + "\n" +
                    "Размер: " + size + "\n" +
                    "Уровень: " + level + "\n" +
                    "Цена: " + price + " руб.\n"
            );
        }
    }

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SkateApp app = new SkateApp();
        app.setVisible(true);
        app.setLocationRelativeTo(null);
    }
}
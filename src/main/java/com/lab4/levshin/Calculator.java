package com.lab4.levshin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Calculator extends JFrame implements ActionListener {
  private JTextField display;
  private String operator = "";
  private double firstNumber = 0;

  public Calculator() {
    setTitle("Калькулятор");
    setSize(400, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Создание поля ввода
    display = new JTextField();
    display.setFont(new Font("Arial", Font.BOLD, 24));
    display.setHorizontalAlignment(JTextField.RIGHT);
    display.setEditable(false);
    display.setPreferredSize(new Dimension(50, 100));

    // Панель кнопок
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));

    buttonPanel.setPreferredSize(new Dimension(300, 300));

    // Кнопки для чисел и операций
    String[] buttons = {
        "7", "8", "9", "/",
        "4", "5", "6", "*",
        "1", "2", "3", "-",
        "0", "C", "=", "+"
    };

    for (String text : buttons) {
      JButton button = new JButton(text);
      button.setFont(new Font("Arial", Font.BOLD, 24));
      button.addActionListener(this);
      buttonPanel.add(button);
    }

    // Организация компонентов
    setLayout(new BorderLayout());
    add(display, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.CENTER);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();

    if (command.matches("[0-9]")) {
      if (display.getText().length() >= 15) {
        JOptionPane.showMessageDialog(this, "Превышена максимальная длина ввода.");
        return;
      }
      display.setText(display.getText() + command);
    } else if (command.equals("C")) {
      display.setText("");
      firstNumber = 0;
      operator = "";
    } else if (command.equals("=")) {
      calculate();
    } else {
      // Операторы
      if (!display.getText().isEmpty()) {
        firstNumber = Double.parseDouble(display.getText());
        display.setText("");
        operator = command;
      }
    }
  }

  private void calculate() {
    double secondNumber = 0;
    if (!display.getText().isEmpty()) {
      try {
        secondNumber = Double.parseDouble(display.getText());

      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(rootPane, ex);
        return;
      }
      
      double result = 0;

      switch (operator) {
        case "+":
          result = firstNumber + secondNumber;
          break;
        case "-":
          result = firstNumber - secondNumber;
          break;
        case "*":
          result = firstNumber * secondNumber;
          break;
        case "/":
          if (secondNumber != 0) {
            result = firstNumber / secondNumber;
          } else {
            JOptionPane.showMessageDialog(this, "Деление на ноль невозможно!");
            return;
          }
          break;
      }
      display.setText(String.valueOf(result));
      firstNumber = result;
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new Calculator().setVisible(true);
    });
  }
}

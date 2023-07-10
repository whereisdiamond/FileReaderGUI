package com.example.filereadergui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * A GUI application for reading a text file, counting the occurrences of words, and displaying the word frequencies.
 */
public class FileReaderGUI extends JFrame implements ActionListener {
    private JTextField filePathField;
    private JTextArea resultArea;

    /**
     * Constructs a FileReaderGUI instance.
     */
    public FileReaderGUI() {
        setTitle("File Reader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel filePathLabel = new JLabel("Enter Txt File path:");
        topPanel.add(filePathLabel);

        filePathField = new JTextField(20);
        topPanel.add(filePathField);

        JButton processButton = new JButton("Process");
        processButton.addActionListener(this);
        topPanel.add(processButton);

        add(topPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Invoked when a button is clicked.
     *
     * @param e the action event
     */
    public void actionPerformed(ActionEvent e) {
        String filePath = filePathField.getText();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            Map<String, Integer> wordCount = new HashMap<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] words = line.split("\\W+");
                for (String word : words) {
                    word = word.trim();
                    if (word.length() > 0) {
                        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                    }
                }
            }

            Map<String, Integer> sortedWordCounts = wordCount.entrySet().stream()
                    .sorted(Collections.reverseOrder(Entry.comparingByValue()))
                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            StringBuilder resultBuilder = new StringBuilder();
            resultBuilder.append(String.format("%-20s%15s\n", "Word", "Frequency"));
            resultBuilder.append(String.format("%-20s%15s\n", "====", "========="));

            for (Map.Entry<String, Integer> entry : sortedWordCounts.entrySet()) {
                resultBuilder.append(String.format("%-20s%10s\n", entry.getKey(), entry.getValue()));
            }

            resultArea.setText(resultBuilder.toString());

            bufferedReader.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * The entry point of the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileReaderGUI fileReaderGUI = new FileReaderGUI();
            fileReaderGUI.setVisible(true);
        });
    }
}

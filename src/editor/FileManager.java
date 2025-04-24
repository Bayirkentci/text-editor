package editor;

import javax.swing.*;
import java.io.*;

public class FileManager {
    public static void openFile(TextEditor textEditor, JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(textEditor) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                textArea.setText(content.toString());
                textEditor.currentFile = file;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(textEditor, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void saveFile(TextEditor textEditor, JTextArea textArea) {
        if (textEditor.currentFile == null) {
            JFileChooser chooser = new JFileChooser();
            int userChoice = chooser.showSaveDialog(textEditor);

            if (userChoice == JFileChooser.APPROVE_OPTION) {
                textEditor.currentFile = chooser.getSelectedFile();
            } else {
                return;
            }
        }

        try {
            FileWriter fileWrite = new FileWriter(textEditor.currentFile);
            BufferedWriter bufferWrite = new BufferedWriter(fileWrite);
            bufferWrite.write(textArea.getText());
            bufferWrite.close();

            textEditor.currentFile = textEditor.currentFile;
            textEditor.setTitle("Simple Text Editor - " + textEditor.currentFile.getName());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    textEditor,
                    "Failed to save file: " + ex.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void newFile(TextEditor textEditor, JTextArea textArea) {
        textArea.setText("");
        textEditor.currentFile = null;
        textEditor.setTitle("Simple Text Editor");
    }
}
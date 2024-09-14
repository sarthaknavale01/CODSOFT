// Student Grade Calculator GUI

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Student_Grade_Calculator_GUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Grade Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Create the main panel with custom gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(132, 202, 249); 
                Color color2 = new Color(199, 149, 237); 
                GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel subjectsLabel = new JLabel("Number of Subjects: ");
        JTextField subjectsField = new JTextField(5);
        JButton submitSubjectsButton = new JButton("Enter Marks");
        topPanel.add(subjectsLabel);
        topPanel.add(subjectsField);
        topPanel.add(submitSubjectsButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setPreferredSize(new Dimension(450, 200));

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JTextArea resultArea = new JTextArea(5, 40);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        resultScrollPane.setPreferredSize(new Dimension(450, 150));
        bottomPanel.add(resultScrollPane);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);

        submitSubjectsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int numSubjects;
                try {
                    numSubjects = Integer.parseInt(subjectsField.getText());
                    if (numSubjects <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid positive number of subjects.");
                    return;
                }

                centerPanel.removeAll();
                centerPanel.revalidate();
                centerPanel.repaint();
                resultArea.setText("");

                ArrayList<JTextField> marksFields = new ArrayList<>();
                for (int i = 0; i < numSubjects; i++) {
                    JPanel marksPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel marksLabel = new JLabel("Marks for subject " + (i + 1) + ": ");
                    JTextField marksField = new JTextField(5);
                    marksFields.add(marksField);
                    marksPanel.add(marksLabel);
                    marksPanel.add(marksField);
                    centerPanel.add(marksPanel);
                }

                JButton calculateButton = new JButton("Calculate");
                centerPanel.add(calculateButton);
                centerPanel.revalidate();
                centerPanel.repaint();

                calculateButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int[] marks = new int[numSubjects];
                        for (int i = 0; i < numSubjects; i++) {
                            try {
                                marks[i] = Integer.parseInt(marksFields.get(i).getText());
                                if (marks[i] < 0 || marks[i] > 100) {
                                    JOptionPane.showMessageDialog(frame, "Please enter marks between 0 and 100 for subject " + (i + 1) + ".");
                                    return;
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(frame, "Please enter valid marks for subject " + (i + 1) + ".");
                                return;
                            }
                        }

                        int totalMarks = 0;
                        for (int mark : marks) {
                            totalMarks += mark;
                        }
                        double averagePercentage = (double) totalMarks / numSubjects;

                        char grade;
                        if (averagePercentage >= 90) {
                            grade = 'A';
                        } else if (averagePercentage >= 80) {
                            grade = 'B';
                        } else if (averagePercentage >= 70) {
                            grade = 'C';
                        } else if (averagePercentage >= 60) {
                            grade = 'D';
                        } else {
                            grade = 'F';
                        }

                        resultArea.setText("Total Marks: " + totalMarks + "\n" +
                                           "Average Percentage: " + String.format("%.2f", averagePercentage) + "\n" +
                                           "Grade: " + grade);
                    }
                });
            }
        });
    }
}

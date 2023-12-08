import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Random;

public class ColorGame extends JFrame {
    private static final int TOTAL_ROUNDS = 10;

    private JLabel wordLabel;
    private JButton[] colorButtons;
    private JButton resetButton;
    private int currentRound;
    private long startTime;
    private long[] roundTimes;
    private int correctAnswers;
    private boolean[] roundResults;

    private String[] colorNames = {"Red", "Green", "Blue", "Yellow", "Orange"};
    private Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE};

    public ColorGame() {
        setTitle("Color Game");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        wordLabel = new JLabel();
        wordLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(wordLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        colorButtons = new JButton[colorNames.length];
        for (int i = 0; i < colorButtons.length; i++) {
            colorButtons[i] = new JButton(colorNames[i]);
            colorButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            colorButtons[i].setPreferredSize(new Dimension(90, 40));
            colorButtons[i].addActionListener(new ColorButtonListener());
            buttonPanel.add(colorButtons[i]);
        }
        add(buttonPanel, BorderLayout.SOUTH);

        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 16));
        resetButton.addActionListener(new ResetButtonListener());
        add(resetButton, BorderLayout.NORTH);

        roundTimes = new long[TOTAL_ROUNDS];
        roundResults = new boolean[TOTAL_ROUNDS];
        startNewRound();
    }

    private void startNewRound() {
        if (currentRound < TOTAL_ROUNDS) {
            if (currentRound < TOTAL_ROUNDS ) {
                wordLabel.setText(colorNames[currentRound]);
                int colorIndex = new Random().nextInt(colors.length);
                wordLabel.setForeground(colors[colorIndex]);

               

            startTime = System.currentTimeMillis();
        } else {
            showResults();
        }
    }
    }
    private void showResults() {
        StringBuilder resultMessage = new StringBuilder("Game Over!\nCorrect Answers: " + correctAnswers + "\n");
        double totalSeconds = 0;

        for (int i = 0; i < TOTAL_ROUNDS; i++) {
            long roundTime = roundTimes[i];
            double seconds = roundTime / 1000.0;
            totalSeconds += seconds;
            resultMessage.append("Round ").append(i + 1).append(": ").append(new DecimalFormat("#.##").format(seconds)).append(" seconds");

            if (roundResults[i]) {
                resultMessage.append(" - Correct\n");
            } else {
                resultMessage.append(" - Incorrect\n");
            }
        }

        resultMessage.append("Total Time: ").append(new DecimalFormat("#.##").format(totalSeconds)).append(" seconds");

        JOptionPane.showMessageDialog(this, resultMessage.toString(), "Game Over", JOptionPane.INFORMATION_MESSAGE);

        resetGame();
    }

    private void resetGame() {
        currentRound = 0;
        correctAnswers = 0;
        roundResults = new boolean[TOTAL_ROUNDS];
        startNewRound();
    }

    private class ColorButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            String selectedColor = clickedButton.getText();
            Color textColor = wordLabel.getForeground();
            String actualColorName = getColorNameByColor(textColor);

            boolean isCorrect = selectedColor.equalsIgnoreCase(actualColorName);
            roundResults[currentRound] = isCorrect;

            if (isCorrect) {
                correctAnswers++;
            }

            long endTime = System.currentTimeMillis();
            long roundTime = endTime - startTime;
            roundTimes[currentRound] = roundTime;

            currentRound++;
            startNewRound();
        }
    }

    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame();
        }
    }

    private String getColorNameByColor(Color color) {
        for (int i = 0; i < colors.length; i++) {
            if (colors[i].equals(color)) {
                return colorNames[i];
            }
        }
        return "";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ColorGame game = new ColorGame();
            game.setVisible(true);
        });
    }
}

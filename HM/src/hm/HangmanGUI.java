package hm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class HangmanGUI extends JFrame {
	private Game game;
	private JLabel wordLabel;
	private JTextField guessTextField;
	private JLabel wrongGuessesLabel;
	private JLabel hangmanLabel;
	private JLabel livesLabel;
	private List<String> wrongGuesses = new ArrayList<>();

	public HangmanGUI() {
		game = new Game();
		setupUI();
	}

	private void setupUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setLayout(new FlowLayout());

		wordLabel = new JLabel(game.getCurrentGuess());
		add(wordLabel);

		guessTextField = new JTextField(20);
		add(guessTextField);

		JButton guessButton = new JButton("Guess");
		guessButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String guess = guessTextField.getText().trim().toLowerCase();
				if (guess.length() == 1) {
					char guessedLetter = guess.charAt(0);
					if (!game.guess(guessedLetter)) {
						if (!wrongGuesses.contains(guess)) {
							wrongGuesses.add(guess);
						}
					}
					wordLabel.setText(game.getCurrentGuess());
					wrongGuessesLabel.setText("Wrong Guesses: "
							+ String.join(", ", wrongGuesses));
					livesLabel.setText(
							"Lives Left: " + (6 - game.getWrongGuesses()));
					guessTextField.setText("");
					hangmanLabel.setIcon(new ImageIcon(getClass().getResource(
							"/images/" + game.getWrongGuesses() + ".png")));

					// 답을 모두 맞췄는지 확인
					if (game.getCurrentGuess().equals(game.getCurrentWord())) {
						JOptionPane.showMessageDialog(null,
								"Congratulations! You've guessed the word: "
										+ game.getCurrentWord());
						
						game.reset();
						wrongGuesses.clear();
						wordLabel.setText(game.getCurrentGuess());
						wrongGuessesLabel.setText("Wrong Guesses: ");
						livesLabel.setText("Lives Left: 6");
						hangmanLabel.setIcon(new ImageIcon(
								getClass().getResource("/images/0.png")));
					} else if (game.isGameOver()) {
						JOptionPane.showMessageDialog(null,
								"Game Over! The word was: "
										+ game.getCurrentWord());
						game.reset();
						wrongGuesses.clear();
						wordLabel.setText(game.getCurrentGuess());
						wrongGuessesLabel.setText("Wrong Guesses: ");
						livesLabel.setText("Lives Left: 6");
						hangmanLabel.setIcon(new ImageIcon(
								getClass().getResource("/images/0.png")));
					}

				}
			}
		});
		add(guessButton);

		wrongGuessesLabel = new JLabel("Wrong Guesses: ");
		add(wrongGuessesLabel);

		livesLabel = new JLabel("Lives Left: 6 / Word Length: "
				+ game.getCurrentWord().length());

		add(livesLabel);

		hangmanLabel = new JLabel(
				new ImageIcon(getClass().getResource("/images/0.png")));
		add(hangmanLabel);

		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new HangmanGUI());
	}
}

package CorpusDir;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.CardLayout;
import javax.swing.JPanel;

public class GUI {

	public JFrame frame;
	private JTextField searchField;
//	private JScrollPane scrollPane;
	private JLayeredPane layeredPane;
	private JButton allwordBtn;
	private JButton usersearchWords;
//	private JScrollPane allResults;
//	private JScrollPane userResults;
//	private JScrollPane scrollPane_1;
//	private JPanel panel;
	private JPanel resultman;
	private JPanel uiman;
	private JScrollPane userScrollPane;
	private JScrollPane allResultScrollPane;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 */
	public GUI() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 1292, 782);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		searchField = new JTextField();
		searchField.setBounds(10, 11, 1067, 38);
		frame.getContentPane().add(searchField);
		searchField.setColumns(10);

		JTextArea userResultTA = new JTextArea();
		userResultTA.setFont(new Font("Monospaced", Font.PLAIN, 16));
		userResultTA.setEditable(false);

		JTextArea allResultTA = new JTextArea();
		userResultTA.setFont(new Font("Monospaced", Font.PLAIN, 16));
		userResultTA.setEditable(false);

//		scrollPane = new JScrollPane();
//		scrollPane.setBounds(33, 71, 1233, 672);
//		scrollPane.setViewportView(result);
//		frame.getContentPane().add(scrollPane);
//		

//		result.setBounds(73, 604, 1233, 672);
//		frame.getContentPane().add(result);
//		

		JButton searchWord = new JButton("Search");
		searchWord.setFont(new Font("Tahoma", Font.PLAIN, 14));
		searchWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SearchEngine.searchWord = searchField.getText();

				try {
					SearchEngine.run(SearchEngine.cmdlineargs);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					BufferedReader reader;
					reader = new BufferedReader(new FileReader(SearchEngine.resultsOutput));
					String line;
					line = reader.readLine();
					String ans = "";
					while (line != null) {
						ans += line + "\n";
						line = reader.readLine();
					}
					allResultTA.append(ans);

					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					BufferedReader reader;
					reader = new BufferedReader(new FileReader(SearchEngine.queriesOutput));
					String line;
					line = reader.readLine();
					String ans = "";
					while (line != null) {
						ans += line + "\n";
						line = reader.readLine();
					}
					userResultTA.append(ans);

					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				///

			}
		});
		searchWord.setBounds(1087, 11, 179, 38);
		frame.getContentPane().add(searchWord);

		layeredPane = new JLayeredPane();
		layeredPane.setBounds(10, 107, 1256, 625);
		frame.getContentPane().add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));

//		layeredPane.add(userResults, "name_138396255710400");
//		layeredPane.add(allResults, "name_138388246459399");

		resultman = new JPanel();
		layeredPane.add(resultman, "name_138934258286600");
		resultman.setLayout(null);

		allResultScrollPane = new JScrollPane();
		allResultScrollPane.setBounds(10, 11, 1236, 603);
		allResultScrollPane.setViewportView(allResultTA);
		resultman.add(allResultScrollPane);

		uiman = new JPanel();
		layeredPane.add(uiman, "name_138938030254399");
		uiman.setLayout(null);

		userScrollPane = new JScrollPane();
		userScrollPane.setBounds(10, 11, 1236, 603);
		userScrollPane.setViewportView(userResultTA);
		uiman.add(userScrollPane);

		allwordBtn = new JButton("All Words");
		allwordBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				switchPanel(resultman);
			}
		});
		allwordBtn.setBounds(10, 60, 89, 23);
		frame.getContentPane().add(allwordBtn);

		usersearchWords = new JButton("User Word");
		usersearchWords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switchPanel(uiman);
			}
		});
		usersearchWords.setBounds(127, 60, 89, 23);
		frame.getContentPane().add(usersearchWords);

	}

	private void switchPanel(JPanel panel) {

		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
}

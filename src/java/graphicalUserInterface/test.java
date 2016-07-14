package graphicalUserInterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;

import generated.Charakter;
import utils.MarshallingHelper;
import utils.TestPreparer;

public class test extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public test() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 749, 517);
		
		TestPreparer preparer = new TestPreparer();
		Charakter barundar = preparer.getBarundar();
		
		
		PaneBuilder paneBuilder = new PaneBuilder(barundar);

		setJMenuBar(makeMenuBar());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		Component tableInfos = paneBuilder.makeBaseInfos();
		Component tableAbilitys = paneBuilder.makeAbilityPane();
		Component tableBaseSkills = new JTable();
		JTable tableComabtSkills = new JTable();
		JTable tableMagicSkills = new JTable();
		JTable tablePriestSkills = new JTable();
		tabbedPane.addTab("Angaben", null, tableInfos, "Angaben und Allgemeines");
		tabbedPane.addTab("Attribute", null, tableAbilitys, "Attribute und grundlegende Steigerungen");
		tabbedPane.setSelectedIndex(0);
		tabbedPane.addTab("Profan", null, tableBaseSkills, "Basisfertigkeiten und profane Sonderfertigkeiten");
		tabbedPane.addTab("Kampf", null, tableComabtSkills, "Kampftechniken und Kampfsonderfertigkeiten");
		tabbedPane.addTab("Magie", null, tableMagicSkills, "Zauber, Rituale und magische Sonderfertigkeitn");
		tabbedPane.addTab("Geweiht", null, tablePriestSkills, "Liturgien, Zeremonien und Geweihten Sonderfertigkeitn");

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.WEST);

		JTree tree = new JTree();
		scrollPane.setViewportView(tree);
	}

	private JMenuBar makeMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu popup = new JMenu();
		popup.setText("Datei");
		menuBar.add(popup);
		return menuBar;
	}

}

package graphicalUserInterface;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8630573095088529781L;

	private PaneBuilder paneBuilder;

	/**
	 * Create the frame.
	 * 
	 * @param metaData
	 * 
	 * @throws Exception
	 */
	public MainFrame(PaneBuilder paneBuilder) throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 749, 517);

		this.paneBuilder = paneBuilder;

		setJMenuBar(paneBuilder.makeMenuBar());
		setContentPane(makeCurrentContentPane());

	}

	private JComponent makeCurrentContentPane() {
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));

		if (paneBuilder.hasData()) {
			JTabbedPane tabbedPane = makeTabsForActiveCharakter();
			contentPane.add(tabbedPane, BorderLayout.CENTER);
		}

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.WEST);
		JTree tree = paneBuilder.getCurrentTree();
		scrollPane.setViewportView(tree);
		return contentPane;
	}

	private JTabbedPane makeTabsForActiveCharakter() {
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		addBaseInfosTabIfNeeded(tabbedPane);
		addAbilityTabIfNeeded(tabbedPane);
		addBaseSkillsTabIfNeeded(tabbedPane);
		addCombatSkillsTabIfNeeded(tabbedPane);
		addMagicSkillsTabIfNeeded(tabbedPane);
		addPriestSkillsTabIfNeeded(tabbedPane);

		tabbedPane.setSelectedIndex(0);
		return tabbedPane;
	}

	private void addBaseInfosTabIfNeeded(JTabbedPane tabbedPane) {
		if (paneBuilder.handlesCharakter() || paneBuilder.handlesProfession()) {
			Component tableInfos = paneBuilder.makeBaseInfos();
			tabbedPane.addTab("Angaben", null, tableInfos, "Angaben und Allgemeines");
		}
	}

	private void addAbilityTabIfNeeded(JTabbedPane tabbedPane) {
		if (paneBuilder.handlesCharakter() || paneBuilder.handlesProfession()) {
			Component tableAbilitys = paneBuilder.makeAbilityPane();
			tabbedPane.addTab("Attribute", null, tableAbilitys, "Attribute und grundlegende Steigerungen");
		}
	}

	private void addBaseSkillsTabIfNeeded(JTabbedPane tabbedPane) {
		if (paneBuilder.handlesCharakter() || paneBuilder.handlesProfession() || paneBuilder.handlesCulture()) {
			Component tableBaseSkills = paneBuilder.makeBaseSkillPane(tabbedPane);
			tabbedPane.addTab("Profan", null, tableBaseSkills, "Basisfertigkeiten und profane Sonderfertigkeiten");
		}
	}

	private void addCombatSkillsTabIfNeeded(JTabbedPane tabbedPane) {
		if (paneBuilder.handlesCharakter() || paneBuilder.handlesProfession()) {
			Component tableComabtSkills = paneBuilder.makeCombatPane(tabbedPane);
			tabbedPane.addTab("Kampf", null, tableComabtSkills, "Kampftechniken und Kampfsonderfertigkeiten");
		}
	}

	private void addMagicSkillsTabIfNeeded(JTabbedPane tabbedPane) {
		if (paneBuilder.handlesCharakter() || paneBuilder.handlesProfession()) {
			Component tableMagicSkills = paneBuilder.makeMagicPane(tabbedPane);
			tabbedPane.addTab("Magie", null, tableMagicSkills, "Zauber, Rituale und magische Sonderfertigkeitn");
		}
	}

	private void addPriestSkillsTabIfNeeded(JTabbedPane tabbedPane) {
		if (paneBuilder.handlesCharakter() || paneBuilder.handlesProfession()) {
			Component tablePriestSkills = paneBuilder.makePriestPane(tabbedPane);
			tabbedPane.addTab("Geweiht", null, tablePriestSkills,
					"Liturgien, Zeremonien und Geweihten Sonderfertigkeitn");
		}
	}

	public void setPaneBuilder(PaneBuilder paneBuilder) {
		this.paneBuilder = paneBuilder;
		setContentPane(makeCurrentContentPane());
		setVisible(true);
	}

}

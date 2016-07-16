package graphicalUserInterface;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.commons.lang3.StringUtils;

import controle.AddFeatDialogResult;
import controle.MasterControleProgramm;
import dataBase.Ability;
import dataBase.BaseSkills;
import dataBase.FeatGroup;
import generated.Attribut;
import generated.Charakter;
import generated.MerkmalProfan;
import generated.Sonderfertigkeit;
import utils.Skill;
import utils.SkillFinder;

public class PaneBuilder {

	Charakter charakter;
	SkillFinder finder;
	MasterControleProgramm controleInstance;

	public PaneBuilder(Charakter charakter, MasterControleProgramm controleInstance) {
		this.charakter = charakter;
		finder = new SkillFinder(charakter);
		this.controleInstance = controleInstance;
	}

	JMenuBar makeMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu popup = new JMenu();
		popup.setText("Datei");

		menuBar.add(popup);

		JMenuItem menuPopupSave = new JMenuItem("Charakter speichern");
		menuPopupSave.addActionListener((ActionEvent e) -> {
			controleInstance.handleSaveActiveCharakter();
		});
		popup.add(menuPopupSave);

		JMenuItem menuPopupPrint = new JMenuItem("Charakter ausgeben");
		menuPopupPrint.addActionListener((ActionEvent e) -> {
			controleInstance.hanldePrintCharakter();
		});
		popup.add(menuPopupPrint);
		return menuBar;
	}

	public Component makeBaseInfos() {
		JPanel baseSkillPanel = new JPanel();
		baseSkillPanel.setLayout(new BoxLayout(baseSkillPanel, BoxLayout.Y_AXIS));
		return baseSkillPanel;
	}

	public Component makeAbilityPane() {
		JPanel abilityPanel = new JPanel();
		abilityPanel.setLayout(new GridLayout(8, 4));
		for (Ability abilityAbstract : Ability.values()) {

			abilityPanel.add(new JLabel(abilityAbstract.getName()));

			JTextField field = new JTextField();
			Attribut ability = finder.getAbilty(abilityAbstract.getAcronym());
			Skill skillable = finder.findSkill(Ability.getAbility(ability.getKürzel()).getName());
			field.setText(new String(Integer.toString(ability.getAttributswert())));
			field.setEnabled(false);
			abilityPanel.add(field);

			JLabel costs = new JLabel(Integer.toString(skillable.getCostForNextLevel()) + " AP");

			JButton button = new JButton("+1");
			button.addActionListener(((ActionEvent e) -> {
				controleInstance.handlencreaseSkill(Ability.getAbility(ability.getKürzel()).getName());
				field.setText(new String(Integer.toString(ability.getAttributswert())));
				costs.setText(Integer.toString(skillable.getCostForNextLevel()) + " AP");
			}));
			abilityPanel.add(button);
			abilityPanel.add(costs);

		}
		return abilityPanel;
	}

	public Component makeBaseSkillPane() {
		JPanel baseSkillPanel = new JPanel();
		baseSkillPanel.setLayout(new BoxLayout(baseSkillPanel, BoxLayout.Y_AXIS));
		for (MerkmalProfan merkmal : MerkmalProfan.values()) {
			JLabel labelSkillGroup = new JLabel(merkmal.name());
			labelSkillGroup.setHorizontalAlignment(SwingConstants.CENTER);
			baseSkillPanel.add(labelSkillGroup);
			Component baseSkillsByCategory = makeSkillTable(BaseSkills.getAmountByCategory(merkmal), merkmal);
			baseSkillPanel.add(baseSkillsByCategory);
		}

		baseSkillPanel.add(makeFeatsComponent(FeatGroup.COMMON));

		JScrollPane scrollableBaseSkillPanel = new JScrollPane(baseSkillPanel);
		return scrollableBaseSkillPanel;
	}
	
	public Component makeMagicPane(){
		JPanel magicSkillPane = new JPanel();
		magicSkillPane.setLayout(new BoxLayout(magicSkillPane, BoxLayout.Y_AXIS));
		
		JScrollPane scrollableMagicSkillPanel = new JScrollPane(magicSkillPane);
		return scrollableMagicSkillPanel;
	}

	private Component makeSkillTable(int rows, Object category) {
		JPanel skillTable = new JPanel();
		skillTable.setLayout(new GridLayout(rows, 6));
		for (BaseSkills abstractSkill : BaseSkills.values()) {
			if (abstractSkill.getMerkmal().equals(category)) {
				Skill skill = finder.findSkill(abstractSkill.getName());
				skillTable.add(new JLabel(abstractSkill.getName()));
				// physicalSkills.add(abstractSkill.getAttribut1+abstractSkill.getAttribut2abstractSkill.getAttribut3);
				JLabel labelCostCategory = new JLabel(abstractSkill.getCategory().toString());
				labelCostCategory.setHorizontalAlignment(SwingConstants.CENTER);
				skillTable.add(labelCostCategory);
				JTextField field = new JTextField();
				field.setText(Integer.toString(skill.getCurrentValue()));
				field.setEnabled(false);
				skillTable.add(field);
				JLabel costs = new JLabel(Integer.toString(skill.getCostForNextLevel()) + " AP");
				JButton increaseSkillButton = makeIncreaseSkillButton(skill, field, costs);
				skillTable.add(increaseSkillButton);

				skillTable.add(costs);

				JButton buttonAddSkillSpecalisation = buttonAddSkillSpecialisation(skillTable, skill.getName());
				skillTable.add(buttonAddSkillSpecalisation);

			}
		}
		return skillTable;
	}

	private JButton makeIncreaseSkillButton(Skill skill, JTextField field, JLabel costs) {
		JButton increaseSkillButton = new JButton("+1");
		increaseSkillButton.addActionListener(((ActionEvent e) -> {
			controleInstance.handlencreaseSkill(skill.getName());
			field.setText(Integer.toString(skill.getCurrentValue()));
			costs.setText(Integer.toString(skill.getCostForNextLevel()) + " AP");
		}));
		return increaseSkillButton;
	}

	private JButton buttonAddSkillSpecialisation(JPanel skillTable, String skillName) {
		JButton buttonAddSkillSpecalisation = new JButton("Talentspezialisierung hinzufügen");
		buttonAddSkillSpecalisation.addActionListener((ActionEvent e) -> {
			AddFeatDialogResult result = getAddFeatResultDialog(skillTable, false);
			if (result != null) {
				controleInstance.handleNewSkillSpecialisation(result, skillName);
			}
		});
		return buttonAddSkillSpecalisation;
	}

	private Component makeFeatsComponent(FeatGroup group) {
		JPanel featComponent = new JPanel();
		featComponent.setLayout(new BoxLayout(featComponent, BoxLayout.Y_AXIS));
		featComponent.add(new JLabel("Sonderfertigkeiten " + group.getName()));
		JPanel featTableComponent = new JPanel();
		List<Sonderfertigkeit> feats = finder.getAllFeatsByGroup(group);
		featTableComponent.setLayout(new GridLayout(feats.size(), 3));
		for (Sonderfertigkeit feat : feats) {
			JTextField nameField = new JTextField(feat.getName());
			nameField.setEnabled(false);
			featTableComponent.add(nameField);

			JTextField descriptionField = new JTextField("no short description");
			descriptionField.setEnabled(false);
			featTableComponent.add(descriptionField);

			JTextField costField = new JTextField(Integer.toString(feat.getKosten()) + " AP");
			costField.setEnabled(false);
			featTableComponent.add(costField);
		}

		featComponent.add(featTableComponent);

		JButton buttonAddFeat = new JButton("Sonderfertigkeit hinzufügen");
		buttonAddFeat.addActionListener((ActionEvent e) -> {
			AddFeatDialogResult result = getAddFeatResultDialog(featComponent);
			if (result != null) {
				controleInstance.handleNewFeat(result, group);
			}
		});
		featComponent.add(buttonAddFeat);

		return featComponent;
	}

	private AddFeatDialogResult getAddFeatResultDialog(Component parent) {
		return getAddFeatResultDialog(parent, true);
	}

	private AddFeatDialogResult getAddFeatResultDialog(Component parent, boolean twoFields) {
		JPanel insertFeatPanel = new JPanel(new GridLayout(0, 1));

		JTextField fieldName = new JTextField();
		JTextField fieldCost = new JTextField();
		fieldCost.setToolTipText("Die AP Kosten der neuen Sonderfertigkeit (als ganze Zahl");
		insertFeatPanel.add(new JLabel("Name:"));
		insertFeatPanel.add(fieldName);
		if (twoFields) {
			insertFeatPanel.add(new JLabel("Kosten:"));
			insertFeatPanel.add(fieldCost);
		}

		int result = JOptionPane.showConfirmDialog(parent, insertFeatPanel, "Test", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (result == 0 && StringUtils.isNotEmpty(fieldName.getText())) {
			try {
				int cost = twoFields?Integer.parseInt(fieldCost.getText()):0;
				if (cost < 1) {
					throw new NumberFormatException(Integer.toString(cost) + " is no valid cost value for a feat");
				}
				return new AddFeatDialogResult(fieldName.getText(), cost);
			} catch (NumberFormatException e) {
				// TODO
				// pop something up the user is an idiot
			}
		}
		return null;
	}

}

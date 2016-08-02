package graphicalUserInterface;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;

import org.apache.commons.lang3.StringUtils;

import controle.AddFeatDialogResult;
import controle.AddNewCharakterDialogResult;
import controle.AddNewCombatSkillDialogResult;
import controle.AddSkillDialogResult;
import controle.MasterControleProgramm;
import dataBase.Ability;
import dataBase.BaseSkills;
import dataBase.FeatGroup;
import dataBase.SpecialSkillGroup;
import generated.Attribut;
import generated.Fertigkeit;
import generated.Kampftechnik;
import generated.MerkmalMagie;
import generated.MerkmalProfan;
import generated.Sonderfertigkeit;
import utils.CostCalculator;
import utils.Skill;
import utils.SkillCombat;
import utils.SkillFinder;
import utils.SkillSpecial;
import utils.WrappedCharakter;

public class PaneBuilder {

	WrappedCharakter charakter;
	SkillFinder finder;
	MasterControleProgramm controleInstance;
	List<String> charakterNames;
	TreeGenerator treeGenerator;

	public PaneBuilder(WrappedCharakter charakter, MasterControleProgramm controleInstance,
			List<String> charakterNames) {
		this.charakter = charakter;
		finder = new SkillFinder(charakter);
		this.controleInstance = controleInstance;
		this.charakterNames = charakterNames;
		treeGenerator = new TreeGenerator();
	}

	public boolean hasData() {
		return null != charakter;
	}

	JMenuBar makeMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu popup = new JMenu();
		popup.setText("Datei");

		menuBar.add(popup);

		JMenuItem menuPopupNew = new JMenuItem("neuen Charakter erstellen");
		menuPopupNew.addActionListener((ActionEvent e) -> {
			AddNewCharakterDialogResult addNew = InputPopups.getAddNewCharakterDialogResult(popup);
			controleInstance.handleNewCharakter(addNew);
		});
		popup.add(menuPopupNew);

		JMenuItem menuPopupSave = new JMenuItem("Charakter speichern");
		menuPopupSave.addActionListener((ActionEvent e) -> {
			String result = InputPopups.getSaveDialogResult(popup);
			if (null != result) {
				controleInstance.handleSaveActiveCharakter(result);
			}
		});
		popup.add(menuPopupSave);

		JMenuItem menuPopupPrint = new JMenuItem("Charakter ausgeben");
		menuPopupPrint.addActionListener((ActionEvent e) -> {
			controleInstance.handlePrintCharakter();
		});
		popup.add(menuPopupPrint);

		JMenuItem menuPopupNewCulture = new JMenuItem("Neue Kultur erstellen");
		menuPopupNewCulture.addActionListener((ActionEvent e) -> {
			String result = InputPopups.getNewCultureDialogResult(popup);
			if (null != result) {
				controleInstance.handleNewCulture(result);
			}
		});
		popup.add(menuPopupNewCulture);

		return menuBar;
	}

	public Component makeBaseInfos() {
		JPanel baseInfosPanel = new JPanel();
		baseInfosPanel.setLayout(new BoxLayout(baseInfosPanel, BoxLayout.Y_AXIS));
		JLabel lableAllAP = new JLabel("Gesamte AP");
		JTextField fieldAllAp = new JTextField();
		fieldAllAp.setEditable(false);
		fieldAllAp.setText(Integer.toString(charakter.getAP()));

		JLabel lableUsedAP = new JLabel("Eingesetzte AP");
		JTextField fieldUsedAp = new JTextField();
		fieldUsedAp.setEditable(false);
		fieldUsedAp.setText(Integer.toString(CostCalculator.calcUsedAP(charakter)));

		baseInfosPanel.add(lableAllAP);
		baseInfosPanel.add(fieldAllAp);
		baseInfosPanel.add(lableUsedAP);
		baseInfosPanel.add(fieldUsedAp);

		return baseInfosPanel;
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

	public Component makeBaseSkillPane(JTabbedPane tabbedPane) {
		JPanel baseSkillPanel = new JPanel();
		baseSkillPanel.setLayout(new BoxLayout(baseSkillPanel, BoxLayout.Y_AXIS));
		for (MerkmalProfan merkmal : MerkmalProfan.values()) {
			JLabel labelSkillGroup = new JLabel(merkmal.name());
			labelSkillGroup.setHorizontalAlignment(SwingConstants.CENTER);
			baseSkillPanel.add(labelSkillGroup);
			List<Skill> skills = new ArrayList<Skill>();
			for (BaseSkills abstractSkill : BaseSkills.values()) {
				if (abstractSkill.getMerkmal().equals(merkmal)) {
					skills.add(finder.findSkill(abstractSkill.getName()));
				}
			}
			Component baseSkillsByCategory = makeSkillTable(skills);
			// Component baseSkillsByCategory =
			// makeSkillTable(BaseSkills.getAmountByCategory(merkmal), merkmal);
			baseSkillPanel.add(baseSkillsByCategory);
		}
		if (charakter.isCharakter() || charakter.isProfession()) {
			baseSkillPanel.add(makeFeatsComponent(FeatGroup.COMMON, tabbedPane));
		}

		JScrollPane scrollableBaseSkillPanel = new JScrollPane(baseSkillPanel);
		return scrollableBaseSkillPanel;
	}

	public Component makeCombatPane(JTabbedPane tabbedPane) {
		JPanel combatSkillPane = new JPanel();
		combatSkillPane.setLayout(new BoxLayout(combatSkillPane, BoxLayout.Y_AXIS));
		JPanel skillTabel = new JPanel();
		skillTabel.setLayout(new GridLayout(charakter.getKampftechniken().getKampftechnik().size(), 5));
		for (Kampftechnik combatSkillXML : charakter.getKampftechniken().getKampftechnik()) {
			Skill skill = new SkillCombat(combatSkillXML);
			JLabel labelName = new JLabel(skill.getName());
			skillTabel.add(labelName);
			JTextField fieldAbilityAcronym = new JTextField(combatSkillXML.getLeiteigenschaft().value());
			fieldAbilityAcronym.setEnabled(false);
			skillTabel.add(fieldAbilityAcronym);
			JTextField fieldSkillValue = new JTextField();
			fieldSkillValue.setText(Integer.toString(skill.getCurrentValue()));
			fieldSkillValue.setEnabled(false);
			skillTabel.add(fieldSkillValue);
			JLabel labelCost = new JLabel(Integer.toString(skill.getCostForNextLevel()) + " AP");
			JButton buttonIncreaseSkill = makeIncreaseSkillButton(skill, fieldSkillValue, labelCost);
			skillTabel.add(buttonIncreaseSkill);
			skillTabel.add(labelCost);

		}
		combatSkillPane.add(skillTabel);

		JButton addNewCombatSkillButton = new JButton("Neue Kampftechnik erlernen");
		addNewCombatSkillButton.addActionListener((ActionEvent e) -> {
			AddNewCombatSkillDialogResult toAddCombatSkillResultDialog = InputPopups
					.getAddCombatSkillResultDialog(combatSkillPane);
			Skill skill = controleInstance.handleNewCombatSkill(toAddCombatSkillResultDialog);
			if(null!=skill){
				((JPanel) skillTabel).setLayout(new GridLayout(charakter.getKampftechniken().getKampftechnik().size(), 5));
				
				JLabel labelName = new JLabel(skill.getName());
				skillTabel.add(labelName);
				JTextField fieldAbilityAcronym = new JTextField(toAddCombatSkillResultDialog.getAbility().value());
				fieldAbilityAcronym.setEnabled(false);
				skillTabel.add(fieldAbilityAcronym);
				JTextField fieldSkillValue = new JTextField();
				fieldSkillValue.setText(Integer.toString(skill.getCurrentValue()));
				fieldSkillValue.setEnabled(false);
				skillTabel.add(fieldSkillValue);
				JLabel labelCost = new JLabel(Integer.toString(skill.getCostForNextLevel()) + " AP");
				JButton buttonIncreaseSkill = makeIncreaseSkillButton(skill, fieldSkillValue, labelCost);
				skillTabel.add(buttonIncreaseSkill);
				skillTabel.add(labelCost);
				
				tabbedPane.repaint();
			}
		});
		combatSkillPane.add(addNewCombatSkillButton);

		combatSkillPane.add(makeFeatsComponent(FeatGroup.COMBAT, tabbedPane));

		JScrollPane scrollableCombatSkillPanel = new JScrollPane(combatSkillPane);
		return scrollableCombatSkillPanel;
	}

	public Component makeMagicPane(JTabbedPane tabbedPane) {
		JPanel magicSkillPane = new JPanel();
		magicSkillPane.setLayout(new BoxLayout(magicSkillPane, BoxLayout.Y_AXIS));
		addCompleteSkillPane(tabbedPane, magicSkillPane, "Neuen Zauber lernen", SpecialSkillGroup.SPELL,
				charakter.getZauber().getZauber());

		magicSkillPane.add(makeFeatsComponent(FeatGroup.MAGIC, tabbedPane));

		addCompleteSkillPane(tabbedPane, magicSkillPane, "Neues Ritual lernen", SpecialSkillGroup.RITUAL,
				charakter.getRituale().getRitual());

		JScrollPane scrollableMagicSkillPanel = new JScrollPane(magicSkillPane);

		return scrollableMagicSkillPanel;
	}

	public Component makePriestPane(JTabbedPane tabbedPane) {
		JPanel priestSkillPane = new JPanel();
		priestSkillPane.setLayout(new BoxLayout(priestSkillPane, BoxLayout.Y_AXIS));

		addCompleteSkillPane(tabbedPane, priestSkillPane, "Neue Liturgie lernen", SpecialSkillGroup.LITURGY,
				charakter.getLiturgien().getLiturgie());

		priestSkillPane.add(makeFeatsComponent(FeatGroup.ORDAINED, tabbedPane));

		addCompleteSkillPane(tabbedPane, priestSkillPane, "Neue Zeremonie lernen", SpecialSkillGroup.ZEREMONY,
				charakter.getZeremonien().getZeremonie());

		JScrollPane scrollableMagicSkillPanel = new JScrollPane(priestSkillPane);

		return scrollableMagicSkillPanel;
	}

	private void addCompleteSkillPane(JTabbedPane tabbedPane, JPanel skillPane, String addButtonText,
			SpecialSkillGroup skillGroup, List<Fertigkeit> skills) {
		Component skillTableFromXml = makeSkillTableFromXml(skills, skillGroup);
		skillPane.add(skillTableFromXml);
		JButton addNewSkillButton = new JButton(addButtonText);
		addNewSkillButton.addActionListener((ActionEvent e) -> {
			AddSkillDialogResult toAddSkillResultDialog = InputPopups.getAddSkillResultDialog(skillPane,
					getNamesOfMagicAttributeValues(),
					(skillGroup == SpecialSkillGroup.ZEREMONY || skillGroup == SpecialSkillGroup.LITURGY ? "Tradition"
							: "Repräsentation"));
			toAddSkillResultDialog.group = skillGroup;
			Skill skill = controleInstance.handleNewSkill(toAddSkillResultDialog);
			((JPanel) skillTableFromXml).setLayout(new GridLayout(skills.size(), 7));
			fillSingleSkillRow((JPanel) skillTableFromXml, true, skill);
			tabbedPane.repaint();
		});
		skillPane.add(addNewSkillButton);
	}

	private String[] getNamesOfMagicAttributeValues() {
		String[] names = new String[MerkmalMagie.values().length];
		for (int i = 0; i < MerkmalMagie.values().length; ++i) {
			names[i] = (MerkmalMagie.values()[i]).value();
		}
		return names;
	}

	private Component makeSkillTableFromXml(List<Fertigkeit> skillsAsXml, SpecialSkillGroup skillGroup) {
		List<Skill> skills = new ArrayList<Skill>();
		for (Fertigkeit skill : skillsAsXml) {
			skills.add(new SkillSpecial(skill, skillGroup));
		}
		return makeSkillTable(skills);
	}

	private Component makeSkillTable(List<Skill> skills) {
		JPanel skillTable = new JPanel();
		if (skills != null && skills.size() > 0) {

			SpecialSkillGroup skillGroup = skills.get(0).getGroup();
			SpecialSkillGroup[] supernatural = { SpecialSkillGroup.SPELL, SpecialSkillGroup.RITUAL,
					SpecialSkillGroup.LITURGY, SpecialSkillGroup.ZEREMONY };
			boolean supernaturalSkills = (Arrays.asList(supernatural).contains(skillGroup));
			skillTable.setLayout(new GridLayout(skills.size(), supernaturalSkills ? 7 : 6));
			for (Skill skill : skills) {
				fillSingleSkillRow(skillTable, supernaturalSkills, skill);
			}

		}
		return skillTable;
	}

	private void fillSingleSkillRow(JPanel skillTable, boolean supernaturalSkills, Skill skill) {
		skillTable.add(new JLabel(skill.getName()));
		// physicalSkills.add(abstractSkill.getAttribut1+abstractSkill.getAttribut2abstractSkill.getAttribut3);
		if (supernaturalSkills) {
			JLabel labelAttributes = new JLabel(((SkillSpecial) skill).getAtributes());
			skillTable.add(labelAttributes);
		}
		JLabel labelCostCategory = new JLabel(skill.getCostCategory().toString());
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

	private Component makeFeatsComponent(FeatGroup group, JTabbedPane tabbedPane) {
		JPanel featComponent = new JPanel();
		featComponent.setLayout(new BoxLayout(featComponent, BoxLayout.Y_AXIS));
		featComponent.add(new JLabel("Sonderfertigkeiten " + group.getName()));
		JPanel featTableComponent = new JPanel();
		List<Sonderfertigkeit> feats = finder.getAllFeatsByGroup(group);
		featTableComponent.setLayout(new GridLayout(feats.size(), 3));
		for (Sonderfertigkeit feat : feats) {
			addFeatRow(featTableComponent, feat);
		}

		featComponent.add(featTableComponent);

		JButton buttonAddFeat = new JButton("Sonderfertigkeit hinzufügen");
		buttonAddFeat.addActionListener((ActionEvent e) -> {
			AddFeatDialogResult result = getAddFeatResultDialog(featComponent);
			if (result != null) {
				Sonderfertigkeit newFeat = controleInstance.handleNewFeat(result, group);
				featTableComponent.setLayout(new GridLayout(finder.getAllFeatsByGroup(group).size(), 3));
				addFeatRow(featTableComponent, newFeat);
				tabbedPane.repaint();
				;
			}
		});
		featComponent.add(buttonAddFeat);

		return featComponent;
	}

	private void addFeatRow(JPanel featTableComponent, Sonderfertigkeit feat) {
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
				int cost = twoFields ? Integer.parseInt(fieldCost.getText()) : 0;
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

	public JTree getCurrentTree() {
		return treeGenerator.getCurrentTree(charakterNames, this);
	}

	public void switchActiveCharakter(String newActiveCharakter) {
		controleInstance.switchActiveCharakter(newActiveCharakter);

	}

	public boolean handlesCharakter() {
		return charakter.isCharakter();
	}

	public boolean handlesCulture() {
		return charakter.isCulture();
	}

	public boolean handlesProfession() {
		return charakter.isProfession();
	}

}

package graphicalUserInterface;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.StringUtils;

import controle.AddFeatDialogResult;
import controle.AddNewCharakterDialogResult;
import controle.AddNewCombatSkillDialogResult;
import controle.AddNewScriptDialogResult;
import controle.AddSkillDialogResult;
import controle.AddVantageDialogResult;
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
import generated.Nachteil;
import generated.Schrift;
import generated.Sonderfertigkeit;
import generated.Sprache;
import generated.Vorteil;
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
			AddNewCharakterDialogResult addNew = InputPopups.getAddNewCharakterDialogResult(popup,
					controleInstance.getCultures(), controleInstance.getProfessions());
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
		menuPopupPrint.addActionListener((ActionEvent e) -> controleInstance.handlePrintCharakter());
		popup.add(menuPopupPrint);

		JMenuItem menuPopupNewCulture = new JMenuItem("Neue Kultur erstellen");
		menuPopupNewCulture.addActionListener((ActionEvent e) -> {
			String result = InputPopups.getNewCultureDialogResult(popup);
			if (null != result) {
				controleInstance.handleNewCulture(result);
			}
		});
		popup.add(menuPopupNewCulture);

		JMenuItem menuPopupNewProfession = new JMenuItem("Neue Profession erstellen");
		menuPopupNewProfession.addActionListener((ActionEvent e) -> {
			String result = InputPopups.getNewProfessionDialogResult(popup);
			if (null != result) {
				controleInstance.handleNewProfession(result);
			}
		});
		popup.add(menuPopupNewProfession);

		return menuBar;
	}

	public Component makeBaseInfos() {
		JPanel baseInfosPanel = new JPanel();
		baseInfosPanel.setLayout(new BoxLayout(baseInfosPanel, BoxLayout.Y_AXIS));
		if (handlesCharakter()) {

			JLabel lName = new JLabel("Name");
			JTextField fName = new JTextField();
			fName.setText(charakter.getName());
			fName.addFocusListener(new WriteThroughFocusListener(() -> {
				return fName.getText();
			}, (String value) -> controleInstance.handleChangeName(value)));
			baseInfosPanel.add(lName);
			baseInfosPanel.add(fName);

			JLabel lCulture = new JLabel("Kultur");
			JTextField fCulture = new JTextField();
			fCulture.setText(charakter.getCulture());
			fCulture.addFocusListener(new WriteThroughFocusListener(() -> {
				return fCulture.getText();
			}, (String value) -> {
				controleInstance.handleChangeCulture(value);
			}));
			baseInfosPanel.add(lCulture);
			baseInfosPanel.add(fCulture);

			JLabel lAge = new JLabel("Alter");
			JTextField fAge = new JTextField();
			fAge.setText(Integer.toString(charakter.getAngaben().getAlter()));
			fAge.addFocusListener(new WriteThroughFocusListener(() -> {
				return fAge.getText();
			}, (String value) -> {
				controleInstance.handleChangeAge(value);
			}));
			baseInfosPanel.add(lAge);
			baseInfosPanel.add(fAge);

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

			JButton bAddAp = new JButton("AP hinzufügen");
			bAddAp.addActionListener((ActionEvent e) -> {
				String addUpString = JOptionPane.showInputDialog(baseInfosPanel, "Wie viele Ap hinzufügen?");
				try {
					int addUp = Integer.parseInt(addUpString);
					controleInstance.handleAddAp(addUp);
					fieldAllAp.setText(Integer.toString(charakter.getAP()));
				} catch (NumberFormatException exception) {

				}
			});
			baseInfosPanel.add(bAddAp);
		}

		baseInfosPanel.add(makeCommunicatives());

		JPanel tableAdvantege = makeTableComponent(makeToList(charakter.getVorteile().getVorteil()), baseInfosPanel,
				true);
		if (null != tableAdvantege) {
			baseInfosPanel.add(tableAdvantege);
		}

		if (handlesCharakter()) {
			JPanel tableDisadvantage = makeTableComponent(makeToList(charakter.getNachteile().getNachteil()),
					baseInfosPanel, false);
			if (null != tableDisadvantage) {
				baseInfosPanel.add(tableDisadvantage);
			}
		}

		return baseInfosPanel;
	}

	private JComponent makeCommunicatives() {
		JPanel pCommunicatives = new JPanel();
		JPanel pLanguages = new JPanel();
		pLanguages.setLayout(new GridLayout(charakter.getKommunikatives().getSprachen().size(), 3));
		for (Sprache language : charakter.getKommunikatives().getSprachen()) {
			addLanguageRow(pLanguages, language);
		}
		pCommunicatives.add(pLanguages);

		JButton bAddLanguage = new JButton();
		bAddLanguage.setText("Sprache lernen");
		bAddLanguage.addActionListener((ActionEvent e) -> {
			String language = JOptionPane.showInputDialog(pCommunicatives, "Welche Sprache soll hinzugefügt werden?");
			if (StringUtils.isNotEmpty(language)) {
				controleInstance.handleIncreaseLangue(language);
				pLanguages.setLayout(new GridLayout(charakter.getKommunikatives().getSprachen().size(), 3));
				addLanguageRow(pLanguages, language);
			}
		});
		pCommunicatives.add(bAddLanguage);

		DefaultTableModel tableModel = makeTable(makeToList(charakter.getKommunikatives().getSchriften()));
		JTable scripts = new JTable(tableModel);
		pCommunicatives.add(scripts);

		JButton bAddScript = new JButton();
		bAddScript.setText("Neue Schrift lernen");
		bAddScript.addActionListener((ActionEvent e) -> {
			AddNewScriptDialogResult result = graphicalUserInterface.InputPopups
					.getAddNewScriptDialogResult(pCommunicatives);
			if (result != null) {
				controleInstance.handleAddScript(result);
				tableModel.addRow(new Object[] { result.getName(), result.getCostCategorie() });
				pCommunicatives.repaint();
			}
		});
		pCommunicatives.add(bAddScript);

		return pCommunicatives;
	}

	private void addLanguageRow(JPanel pLanguages, Sprache language) {
		JLabel lLanguageName = new JLabel(language.getName());
		pLanguages.add(lLanguageName);

		JTextField fLanguageLevel = new JTextField();
		fLanguageLevel.setEditable(false);
		fLanguageLevel.setText(language.getStufe() < 4 ? Integer.toString(language.getStufe()) : "M");
		pLanguages.add(fLanguageLevel);

		JButton bIncreaseLanguageLevel = new JButton();
		bIncreaseLanguageLevel.setText("Steigere Sprache");
		bIncreaseLanguageLevel.addActionListener((ActionEvent e) -> {
			controleInstance.handleIncreaseLangue(language.getName());
			fLanguageLevel.setText(language.getStufe() < 4 ? Integer.toString(language.getStufe()) : "M");
		});
		pLanguages.add(bIncreaseLanguageLevel);
	}

	private void addLanguageRow(JPanel pLanguages, String languageName) {
		for (Sprache language : charakter.getKommunikatives().getSprachen()) {
			if (StringUtils.equals(languageName, language.getName())) {
				addLanguageRow(pLanguages, language);
				return;
			}
		}
	}

	private List<Object> makeToList(List<?> list) {
		List<Object> resultList = new ArrayList<Object>();
		for (Object obj : list) {
			resultList.add(obj);
		}
		return resultList;
	}

	private JPanel makeTableComponent(List<Object> data, JPanel baseInfosPanel, boolean isAdvantage) {
		DefaultTableModel tableModel = makeTable(data);
		JTable vanteges = new JTable(tableModel);

		JButton addButton = new JButton("Hinzufügen");
		addButton.addActionListener((ActionEvent e) -> {
			AddVantageDialogResult result = InputPopups.getAddVanatgeDialogResult(baseInfosPanel);
			if (result.isComplete()) {
				result.setAdvantage(isAdvantage);
				if (controleInstance.handleNewAdvantage(result)) {
					tableModel.addRow(new String[] { result.getName(), Integer.toString(result.getCost()) });
				}
			}
		});
		JPanel panel = new JPanel();
		panel.add(vanteges);
		panel.add(addButton);
		return panel;
	}

	private DefaultTableModel makeTable(List<Object> data) {
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("Name");
		tableModel.addColumn("Wert");
		if (null == data) {
			return tableModel;
		}
		if (data.isEmpty()) {
			return tableModel;
		}
		if (!(data.get(0) instanceof Vorteil || data.get(0) instanceof Nachteil || data.get(0) instanceof Schrift)) {
			return tableModel;
		}
		if (data.get(0) instanceof Vorteil) {
			for (Object advantege : data) {
				tableModel.addRow(new String[] { ((Vorteil) advantege).getName(),
						Integer.toString(((Vorteil) advantege).getKosten()) });
			}
		} else if (data.get(0) instanceof Nachteil) {
			for (Object disadvantege : data) {
				tableModel.addRow(new String[] { ((Nachteil) disadvantege).getName(),
						Integer.toString(((Nachteil) disadvantege).getKosten()) });
			}
		} else if (data.get(0) instanceof Schrift) {
			for (Object script : data) {
				tableModel.addRow(
						new String[] { ((Schrift) script).getName(), ((Schrift) script).getKomplexität().toString() });
			}
		}
		return tableModel;
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
			button.addActionListener((ActionEvent e) -> {
				controleInstance.handlencreaseSkill(Ability.getAbility(ability.getKürzel()).getName());
				field.setText(new String(Integer.toString(ability.getAttributswert())));
				costs.setText(Integer.toString(skillable.getCostForNextLevel()) + " AP");
			});
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

		return new JScrollPane(baseSkillPanel);
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
			if (skill != null) {
				addNewombatSkillToInterface(tabbedPane, skillTabel, toAddCombatSkillResultDialog, skill);
			}
		});
		combatSkillPane.add(addNewCombatSkillButton);

		combatSkillPane.add(makeFeatsComponent(FeatGroup.COMBAT, tabbedPane));

		return new JScrollPane(combatSkillPane);
	}

	private void addNewombatSkillToInterface(JTabbedPane tabbedPane, JPanel skillTabel,
			AddNewCombatSkillDialogResult toAddCombatSkillResultDialog, Skill skill) {
		skillTabel.setLayout(new GridLayout(charakter.getKampftechniken().getKampftechnik().size(), 5));

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

		return new JScrollPane(priestSkillPane);
	}

	private void addCompleteSkillPane(JTabbedPane tabbedPane, JPanel skillPane, String addButtonText,
			SpecialSkillGroup skillGroup, List<Fertigkeit> skills) {
		Component skillTableFromXml = makeSkillTableFromXml(skills, skillGroup);
		skillPane.add(skillTableFromXml);
		JButton addNewSkillButton = new JButton(addButtonText);
		addNewSkillButton.addActionListener((ActionEvent e) -> {
			AddSkillDialogResult toAddSkillResultDialog;
			if (skillGroup == SpecialSkillGroup.ZEREMONY || skillGroup == SpecialSkillGroup.LITURGY) {
				toAddSkillResultDialog = InputPopups.getAddSkillResultDialog(skillPane, "Tradition");
			} else {
				toAddSkillResultDialog = InputPopups.getAddSkillResultDialog(skillPane,
						getNamesOfMagicAttributeValues(), "Repräsentation");
			}

			toAddSkillResultDialog.setGroup(skillGroup);
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
		if (skills != null && !skills.isEmpty()) {

			SpecialSkillGroup skillGroup = skills.get(0).getGroup();
			SpecialSkillGroup[] supernatural = { SpecialSkillGroup.SPELL, SpecialSkillGroup.RITUAL,
					SpecialSkillGroup.LITURGY, SpecialSkillGroup.ZEREMONY };
			boolean supernaturalSkills = (Arrays.asList(supernatural).contains(skillGroup));
			int numberOfColums = supernaturalSkills ? 8 : 7;
			if (charakter.isCulture()) {
				--numberOfColums;
			}
			skillTable.setLayout(new GridLayout(skills.size(), numberOfColums));
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
		JTextField fCurrentValue = new JTextField();
		fCurrentValue.setText(Integer.toString(skill.getCurrentValue()));
		fCurrentValue.setEnabled(false);
		skillTable.add(fCurrentValue);
		JLabel costs = new JLabel(Integer.toString(skill.getCostForNextLevel()) + " AP");
		JButton increaseSkillButton = makeIncreaseSkillButton(skill, fCurrentValue, costs);
		skillTable.add(increaseSkillButton);

		skillTable.add(costs);
		if (charakter.isProfession() || charakter.isCharakter()) {
			JTextField fSpecialisations = new JTextField();
			fSpecialisations.setEnabled(false);
			fSpecialisations.setText(finder.getSkillSpecialisations(skill.getName()));

			skillTable.add(fSpecialisations);
			JButton buttonAddSkillSpecalisation = buttonAddSkillSpecialisation(skillTable, fSpecialisations,
					skill.getName());
			skillTable.add(buttonAddSkillSpecalisation);
		}
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

	private JButton buttonAddSkillSpecialisation(JPanel skillTable, JTextField fSpecialisations, String skillName) {
		JButton buttonAddSkillSpecalisation = new JButton("Fertigkeitsspezialisierung hinzufügen");
		buttonAddSkillSpecalisation.addActionListener((ActionEvent e) -> {
			AddFeatDialogResult result = getAddFeatResultDialog(skillTable, false);
			if (result != null) {
				controleInstance.handleNewSkillSpecialisation(result, skillName);
				fSpecialisations.setText(finder.getSkillSpecialisations(skillName));
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
		JScrollPane scrollableFeatPanel = new JScrollPane(featComponent);
		return scrollableFeatPanel;
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
				int cost = twoFields ? Integer.parseInt(fieldCost.getText()) : 1;
				if (cost < 1) {
					throw new NumberFormatException(Integer.toString(cost) + " is no valid cost value for a feat");
				}
				return new AddFeatDialogResult(fieldName.getText(), cost);
			} catch (NumberFormatException e) {
				// TODO pop something up the user is an idiot
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

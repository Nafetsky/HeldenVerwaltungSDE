package graphicalUserInterface;

import api.AbilityGroup;
import api.Advantage;
import api.BaseAttribute;
import api.CombatTechnique;
import api.Disadvantage;
import api.ILanguage;
import api.ISpecialAbility;
import api.SpecialAbility;
import api.Vantage;
import api.base.Character;
import api.skills.BaseDescriptors;
import api.skills.BaseSkills;
import api.skills.Increasable;
import api.skills.Skill;
import api.skills.SkillGroup;
import controle.AddFeatDialogResult;
import controle.AddNewCharakterDialogResult;
import controle.AddNewCombatSkillDialogResult;
import controle.AddNewScriptDialogResult;
import controle.AddSkillDialogResult;
import controle.AddVantageDialogResult;
import main.MasterControleProgramm;
import generated.MerkmalMagie;
import generated.Nachteil;
import generated.Schrift;
import generated.Vorteil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import utility.CostCalculator;

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
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class PaneBuilder {

	private Character charakter;
	private MasterControleProgramm controleInstance;
	private List<String> charakterNames;
	private TreeGenerator treeGenerator;

	public PaneBuilder(Character character, MasterControleProgramm controleInstance,
					   List<String> charakterNames) {
		this.charakter = character;
//		finder = new SkillFinder(character);
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
			String result = InputPopups.getSaveDialogResult();
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
			String result = InputPopups.getNewCultureDialogResult();
			if (null != result) {
				controleInstance.handleNewCulture(result);
			}
		});
		popup.add(menuPopupNewCulture);

		JMenuItem menuPopupNewProfession = new JMenuItem("Neue Profession erstellen");
		menuPopupNewProfession.addActionListener((ActionEvent e) -> {
			String result = InputPopups.getNewProfessionDialogResult();
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
			fName.setText(charakter.getMetaData()
								   .getName());
			fName.addFocusListener(new WriteThroughFocusListener(() -> {
				return fName.getText();
			}, (String value) -> controleInstance.handleChangeName(value)));
			baseInfosPanel.add(lName);
			baseInfosPanel.add(fName);

			JLabel lCulture = new JLabel("Kultur");
			JTextField fCulture = new JTextField();
			fCulture.setText(charakter.getMetaData()
									  .getCulture());
			fCulture.addFocusListener(
					new WriteThroughFocusListener(fCulture::getText, (String) -> {
					}));
			baseInfosPanel.add(lCulture);
			baseInfosPanel.add(fCulture);

			JLabel lAge = new JLabel("Alter");
			JTextField fAge = new JTextField();
			fAge.setText(Integer.toString(charakter.getMetaData()
												   .getAge()));
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
			fieldAllAp.setText(Integer.toString(charakter.getAllAdventurePoints()));

			JLabel lableUsedAP = new JLabel("Eingesetzte AP");
			JTextField fieldUsedAp = new JTextField();
			fieldUsedAp.setEditable(false);
			fieldUsedAp.setText(Integer.toString(charakter.getUsedAdventurePoints()));

			baseInfosPanel.add(lableAllAP);
			baseInfosPanel.add(fieldAllAp);
			baseInfosPanel.add(lableUsedAP);
			baseInfosPanel.add(fieldUsedAp);

			JButton bAddAp = new JButton("AP hinzuf�gen");
			bAddAp.addActionListener((ActionEvent e) -> {
				String addUpString = JOptionPane.showInputDialog(baseInfosPanel, "Wie viele Ap hinzuf�gen?");
				try {
					int addUp = Integer.parseInt(addUpString);
					controleInstance.handleAddAp(addUp);
					fieldAllAp.setText(Integer.toString(charakter.getAllAdventurePoints()));
				} catch (NumberFormatException exception) {

				}
			});
			baseInfosPanel.add(bAddAp);
		}

		baseInfosPanel.add(makeCommunicatives());

		JPanel tableAdvantege = makeTableComponent(charakter.getAdvantages(), baseInfosPanel,
				true);
		if (null != tableAdvantege) {
			baseInfosPanel.add(tableAdvantege);
		}

		if (handlesCharakter()) {
			JPanel tableDisadvantage = makeTableComponent(charakter.getDisadvantages(), baseInfosPanel, false);
			if (null != tableDisadvantage) {
				baseInfosPanel.add(tableDisadvantage);
			}
		}

		return baseInfosPanel;
	}

	private JComponent makeCommunicatives() {
		JPanel pCommunicatives = new JPanel();
		JPanel pLanguages = new JPanel();
		List<ILanguage> languages = charakter.getLanguages();
		pLanguages.setLayout(new GridLayout(languages.size(), 3));
		for (ILanguage language : languages) {
			addLanguageRow(pLanguages, language);
		}
		pCommunicatives.add(pLanguages);

		JButton bAddLanguage = new JButton();
		bAddLanguage.setText("Sprache lernen");
		bAddLanguage.addActionListener((ActionEvent e) -> {
			String language = JOptionPane.showInputDialog(pCommunicatives, "Welche Sprache soll hinzugefügt werden?");
			if (StringUtils.isNotEmpty(language)) {
				controleInstance.handleIncreaseLanguage(language);
				List<ILanguage> currentLanguages = charakter.getLanguages();
				pLanguages.setLayout(new GridLayout(currentLanguages.size(), 3));
				addLanguageRow(pLanguages, language);
			}
		});
		pCommunicatives.add(bAddLanguage);

		charakter.getSpecialAbilities(AbilityGroup.MUNDANE)
				 .stream()
				 .filter(ILanguage.class::isInstance)
				 .map(ILanguage.class::cast)
				 .collect(Collectors.toList());
		DefaultTableModel tableModel = makeTable(makeToList(charakter.getScriptures()));
		for (ISpecialAbility result : charakter.getScriptures()) {
			tableModel.addRow(new Object[]{result.getName(), result.getCost()});
		}
		JTable scripts = new JTable(tableModel);
		pCommunicatives.add(scripts);

		JButton bAddScript = new JButton();
		bAddScript.setText("Neue Schrift lernen");
		bAddScript.addActionListener((ActionEvent e) -> {
			AddNewScriptDialogResult result = graphicalUserInterface.InputPopups
					.getAddNewScriptDialogResult(pCommunicatives);
			if (result != null) {
				controleInstance.handleAddScript(result);
				tableModel.addRow(new Object[]{result.getName(), result.getCostCategorie()});
				pCommunicatives.repaint();
			}
		});
		pCommunicatives.add(bAddScript);

		return pCommunicatives;
	}

	private void addLanguageRow(JPanel pLanguages, ILanguage language) {
		JLabel lLanguageName = new JLabel(language.getName());
		pLanguages.add(lLanguageName);

		JTextField fLanguageLevel = new JTextField();
		fLanguageLevel.setEditable(false);
		fLanguageLevel.setText(language.getLevel() < 4 ? Integer.toString(language.getLevel()) : "M");
		pLanguages.add(fLanguageLevel);

		JButton bIncreaseLanguageLevel = new JButton();
		bIncreaseLanguageLevel.setText("Steigere Sprache");
		bIncreaseLanguageLevel.addActionListener((ActionEvent e) -> {
			controleInstance.handleIncreaseLanguage(language.getName());
			fLanguageLevel.setText(language.getLevel() < 4 ? Integer.toString(language.getLevel()) : "M");
		});
		pLanguages.add(bIncreaseLanguageLevel);
	}

	private void addLanguageRow(JPanel pLanguages, String languageName) {
		for (ILanguage language : charakter.getLanguages()) {
			if (StringUtils.equals(languageName, language.getName())) {
				addLanguageRow(pLanguages, language);
				return;
			}
		}
	}

	private List<Object> makeToList(List<?> list) {
		List<Object> resultList = new ArrayList<>();
		for (Object obj : list) {
			resultList.add(obj);
		}
		return resultList;
	}

	private JPanel makeTableComponent(List<Vantage> data, JPanel baseInfosPanel, boolean isAdvantage) {
		DefaultTableModel tableModel = makeVantageTable(data);
		JTable vanteges = new JTable(tableModel);

		JButton addButton = new JButton("Hinzufügen");
		addButton.addActionListener((ActionEvent e) -> {
			AddVantageDialogResult result = InputPopups.getAddVanatgeDialogResult(baseInfosPanel, isAdvantage);
			if (result.isComplete()) {
				if (controleInstance.handleNewAdvantage(result)) {
					tableModel.addRow(new String[]{result.getName(), Integer.toString(result.getCost())});
				}
			}
		});
		JPanel panel = new JPanel();
		panel.add(vanteges);
		panel.add(addButton);
		return panel;
	}

	private DefaultTableModel makeVantageTable(List<Vantage> data) {
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("Name");
		tableModel.addColumn("Wert");
		if (CollectionUtils.isEmpty(data)) {
			return tableModel;
		}

		if (data.get(0) instanceof Advantage) {
			for (Vantage advantege : data) {
				tableModel.addRow(new String[]{advantege.getName(), Integer.toString(advantege.getCost())});
			}
		} else if (data.get(0) instanceof Disadvantage) {
			for (Vantage disadvantege : data) {
				tableModel.addRow(new String[]{disadvantege.getName(),
						Integer.toString(disadvantege.getCost())});
			}
		}
		return tableModel;
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
				tableModel.addRow(new String[]{((Vorteil) advantege).getName(),
						Integer.toString(((Vorteil) advantege).getKosten())});
			}
		} else if (data.get(0) instanceof Nachteil) {
			for (Object disadvantege : data) {
				tableModel.addRow(new String[]{((Nachteil) disadvantege).getName(),
						Integer.toString(((Nachteil) disadvantege).getKosten())});
			}
		} else if (data.get(0) instanceof Schrift) {
			for (Object script : data) {
				tableModel.addRow(
						new String[]{((Schrift) script).getName(), ((Schrift) script).getKomplexität().toString()});
			}
		}
		return tableModel;
	}

	public Component makeAbilityPane() {
		JPanel abilityPanel = new JPanel();
		abilityPanel.setLayout(new GridLayout(8, 4));
		for (BaseAttribute abilityAbstract : BaseAttribute.values()) {

			abilityPanel.add(new JLabel(abilityAbstract.name()));

			JTextField field = new JTextField();
			int skillLevel = charakter.getAttributes()
									  .getValue(abilityAbstract);
			field.setText(Integer.toString(skillLevel));
			field.setEnabled(false);
			abilityPanel.add(field);

			JLabel costs = new JLabel(CostCalculator.calcCostForNextLevelAbility(skillLevel + 1) + " AP");

			JButton button = new JButton("+1");
			button.addActionListener((ActionEvent e) -> {
				controleInstance.handleIncreaseAttribute(abilityAbstract);
				int newValue = charakter.getAttributes()
										.getValue(abilityAbstract);
				field.setText(Integer.toString(newValue));
				costs.setText(CostCalculator.calcCostForNextLevelAbility(skillLevel + 1) + " AP");
			});
			abilityPanel.add(button);
			abilityPanel.add(costs);

		}
		return abilityPanel;
	}

	public Component makeBaseSkillPane(JTabbedPane tabbedPane) {
		JPanel baseSkillPanel = new JPanel();
		baseSkillPanel.setLayout(new BoxLayout(baseSkillPanel, BoxLayout.Y_AXIS));
		for (BaseDescriptors merkmal : BaseDescriptors.values()) {
			JLabel labelSkillGroup = new JLabel(merkmal.name());
			labelSkillGroup.setHorizontalAlignment(SwingConstants.CENTER);
			baseSkillPanel.add(labelSkillGroup);
//			List<Skill> skills = Arrays.stream(BaseSkills.values())
//									   .filter(skill -> skill.getMerkmal()
//															 .equals(merkmal))
//									   .map(BaseSkills::getSkill)
//									   .collect(Collectors.toList());
			List<Skill> skills = charakter.getSkills(SkillGroup.BASE)
										  .stream()
										  .filter(skill -> ArrayUtils.contains(skill.getDescriptors(), merkmal))
										  .sorted((left, right) -> String.CASE_INSENSITIVE_ORDER.compare(left.getName(), right.getName()))
										  .collect(Collectors.toList());
			Component baseSkillsByCategory = makeSkillTable(skills);
			baseSkillPanel.add(baseSkillsByCategory);
		}
		baseSkillPanel.add(makeFeatsComponent(AbilityGroup.MUNDANE, tabbedPane));

		return new JScrollPane(baseSkillPanel);
	}

	public Component makeCombatPane(JTabbedPane tabbedPane) {
		JPanel combatSkillPane = new JPanel();
		combatSkillPane.setLayout(new BoxLayout(combatSkillPane, BoxLayout.Y_AXIS));
		JPanel skillTabel = new JPanel();
		skillTabel.setLayout(new GridLayout(charakter.getCombatTechniques()
													 .size(), 5));
		for (CombatTechnique combatTechnique : charakter.getCombatTechniques()) {
			JLabel labelName = new JLabel(combatTechnique.getName());
			skillTabel.add(labelName);
			JTextField fieldAbilityAcronym = new JTextField(combatTechnique.getAttribute()
																		   .name());
			fieldAbilityAcronym.setEnabled(false);
			skillTabel.add(fieldAbilityAcronym);
			JTextField fieldSkillValue = new JTextField();
			fieldSkillValue.setText(Integer.toString(combatTechnique.getLevel()));
			fieldSkillValue.setEnabled(false);
			skillTabel.add(fieldSkillValue);
			JLabel labelCost = new JLabel(CostCalculator.calcCostForNextLevel(combatTechnique) + " AP");
			JButton buttonIncreaseSkill = makeIncreaseSkillButton(combatTechnique, fieldSkillValue, labelCost);
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
				addNewCombatSkillToInterface(tabbedPane, skillTabel, toAddCombatSkillResultDialog, skill);
			}
		});
		combatSkillPane.add(addNewCombatSkillButton);

		combatSkillPane.add(makeFeatsComponent(AbilityGroup.COMBAT, tabbedPane));

		return new JScrollPane(combatSkillPane);
	}

	private void addNewCombatSkillToInterface(JTabbedPane tabbedPane, JPanel skillTabel,
											  AddNewCombatSkillDialogResult toAddCombatSkillResultDialog, Skill skill) {
		skillTabel.setLayout(new GridLayout(charakter.getCombatTechniques()
													 .size(), 5));

		JLabel labelName = new JLabel(skill.getName());
		skillTabel.add(labelName);
		JTextField fieldAbilityAcronym = new JTextField(toAddCombatSkillResultDialog.getAbility()
																					.name());
		fieldAbilityAcronym.setEnabled(false);
		skillTabel.add(fieldAbilityAcronym);
		JTextField fieldSkillValue = new JTextField();
		fieldSkillValue.setText(Integer.toString(skill.getLevel()));
		fieldSkillValue.setEnabled(false);
		skillTabel.add(fieldSkillValue);
		JLabel labelCost = new JLabel(CostCalculator.calcCostForNextLevel(skill) + " AP");
		JButton buttonIncreaseSkill = makeIncreaseSkillButton(skill, fieldSkillValue, labelCost);
		skillTabel.add(buttonIncreaseSkill);
		skillTabel.add(labelCost);

		tabbedPane.repaint();
	}

	public Component makeMagicPane(JTabbedPane tabbedPane) {
		JPanel magicSkillPane = new JPanel();
		magicSkillPane.setLayout(new BoxLayout(magicSkillPane, BoxLayout.Y_AXIS));
		addCompleteSkillPane(tabbedPane,
				magicSkillPane,
				"Neuen Zauber lernen",
				SkillGroup.SPELL,
				charakter.getSkills(SkillGroup.SPELL));

		magicSkillPane.add(makeFeatsComponent(AbilityGroup.MAGICAL, tabbedPane));

		addCompleteSkillPane(tabbedPane,
				magicSkillPane,
				"Neues Ritual lernen",
				SkillGroup.RITUAL,
				charakter.getSkills(SkillGroup.RITUAL));

		return new JScrollPane(magicSkillPane);
	}

	public Component makePriestPane(JTabbedPane tabbedPane) {
		JPanel priestSkillPane = new JPanel();
		priestSkillPane.setLayout(new BoxLayout(priestSkillPane, BoxLayout.Y_AXIS));

		addCompleteSkillPane(tabbedPane,
				priestSkillPane,
				"Neue Liturgie lernen",
				SkillGroup.LITURGICAL_CHANT,
				charakter.getSkills(SkillGroup.LITURGICAL_CHANT));

		priestSkillPane.add(makeFeatsComponent(AbilityGroup.KARMA, tabbedPane));

		addCompleteSkillPane(tabbedPane,
				priestSkillPane,
				"Neue Zeremonie lernen",
				SkillGroup.CEREMONY,
				charakter.getSkills(SkillGroup.CEREMONY));

		return new JScrollPane(priestSkillPane);
	}

	private void addCompleteSkillPane(JTabbedPane tabbedPane, JPanel skillPane, String addButtonText,
									  SkillGroup skillGroup, List<Skill> skills) {
		Component skillTableFromXml = makeSkillTable(skills);
		skillPane.add(skillTableFromXml);
		JButton addNewSkillButton = new JButton(addButtonText);
		addNewSkillButton.addActionListener((ActionEvent e) -> {
			AddSkillDialogResult toAddSkillResultDialog;
			if (skillGroup == SkillGroup.CEREMONY || skillGroup == SkillGroup.LITURGICAL_CHANT) {
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

	private Component makeSkillTable(List<Skill> skills) {
		JPanel skillTable = new JPanel();

		if (skills != null && !skills.isEmpty()) {

			SkillGroup skillGroup = skills.get(0)
										  .getGroup();
			SkillGroup[] supernatural = {SkillGroup.SPELL, SkillGroup.RITUAL,
					SkillGroup.LITURGICAL_CHANT, SkillGroup.CEREMONY};
			boolean supernaturalSkills = (Arrays.asList(supernatural)
												.contains(skillGroup));
			int numberOfColums = supernaturalSkills ? 8 : 7;
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
			Optional<BaseAttribute[]> attributes = skill.getAttributes();
			String labelText = attributes.map(Arrays::toString)
										 .orElse("Attribute nicht gesetzt");
			JLabel labelAttributes = new JLabel(labelText);
			skillTable.add(labelAttributes);
		}
		JLabel labelCostCategory = new JLabel(skill.getComplexity()
												   .name());
		labelCostCategory.setHorizontalAlignment(SwingConstants.CENTER);
		skillTable.add(labelCostCategory);
		JTextField fCurrentValue = new JTextField();
		fCurrentValue.setText(Integer.toString(skill.getLevel()));
		fCurrentValue.setEnabled(false);
		skillTable.add(fCurrentValue);
		JLabel costs = new JLabel(CostCalculator.calcCostForNextLevel(skill) + " AP");
		JButton increaseSkillButton = makeIncreaseSkillButton(skill, fCurrentValue, costs);
		skillTable.add(increaseSkillButton);

		skillTable.add(costs);
		JTextField fSpecialisations = new JTextField();
		fSpecialisations.setEnabled(false);
		String specialisations = charakter.getSpecialAbilities(AbilityGroup.SPECIALISATION)
										  .stream()
										  .map(ISpecialAbility::getName)
										  .collect(Collectors.joining(", "));
		fSpecialisations.setText(specialisations);

		skillTable.add(fSpecialisations);
		JButton buttonAddSkillSpecalisation = buttonAddSkillSpecialisation(skillTable, fSpecialisations,
				skill.getName());
		skillTable.add(buttonAddSkillSpecalisation);
	}

	private JButton makeIncreaseSkillButton(Increasable skill, JTextField field, JLabel costs) {
		JButton increaseSkillButton = new JButton("+1");
		increaseSkillButton.addActionListener(((ActionEvent e) -> {
			controleInstance.handleIncreaseSkill(skill.getName());
			field.setText(Integer.toString(skill.getLevel()));
			costs.setText(CostCalculator.calcCostForNextLevel(skill) + " AP");
		}));
		return increaseSkillButton;
	}

	private JButton buttonAddSkillSpecialisation(JPanel skillTable, JTextField fSpecialisations, String skillName) {
		JButton buttonAddSkillSpecalisation = new JButton("Fertigkeitsspezialisierung hinzufügen");
		buttonAddSkillSpecalisation.addActionListener((ActionEvent e) -> {
			AddFeatDialogResult result = InputPopups.getAddFeatResultDialog(skillTable, false);
			if (result != null) {
				controleInstance.handleNewSkillSpecialisation(result, skillName);
				String specialisations = charakter.getSpecialAbilities(AbilityGroup.SPECIALISATION)
												  .stream()
												  .map(ISpecialAbility::getName)
												  .collect(Collectors.joining(", "));
				fSpecialisations.setText(specialisations);
			}
		});
		return buttonAddSkillSpecalisation;
	}

	private Component makeFeatsComponent(AbilityGroup group, JTabbedPane tabbedPane) {
		JPanel featComponent = new JPanel();
		featComponent.setLayout(new BoxLayout(featComponent, BoxLayout.Y_AXIS));
		featComponent.add(new JLabel("Sonderfertigkeiten " + group.name()));
		JPanel featTableComponent = new JPanel();
		List<ISpecialAbility> feats = charakter.getSpecialAbilities(group);
		featTableComponent.setLayout(new GridLayout(feats.size(), 3));
		for (ISpecialAbility feat : feats) {
			addFeatRow(featTableComponent, feat);
		}

		featComponent.add(featTableComponent);

		JButton buttonAddFeat = new JButton("Sonderfertigkeit hinzufügen");
		buttonAddFeat.addActionListener((ActionEvent e) -> {
			AddFeatDialogResult result = getAddFeatResultDialog(featComponent, true, group);
			if (result != null) {
				ISpecialAbility newFeat = controleInstance.handleNewFeat(result);
				featTableComponent.setLayout(new GridLayout(charakter.getSpecialAbilities(group)
																	 .size(), 3));
				addFeatRow(featTableComponent, newFeat);
				tabbedPane.repaint();
			}
		});
		featComponent.add(buttonAddFeat);
		JScrollPane scrollableFeatPanel = new JScrollPane(featComponent);
		return scrollableFeatPanel;
	}

	private void addFeatRow(JPanel featTableComponent, ISpecialAbility feat) {
		JTextField nameField = new JTextField(feat.getName());
		nameField.setEnabled(false);
		featTableComponent.add(nameField);

		JTextField descriptionField = new JTextField("no short description");
		descriptionField.setEnabled(false);
		featTableComponent.add(descriptionField);

		JTextField costField = new JTextField(feat.getCost() + " AP");
		costField.setEnabled(false);
		featTableComponent.add(costField);
	}

	private AddFeatDialogResult getAddFeatResultDialog(Component parent, boolean twoFields, AbilityGroup group) {
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
					throw new NumberFormatException(cost + " is no valid cost value for a feat");
				}
				return new AddFeatDialogResult(fieldName.getText(), cost, group);
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
		return true;
	}

	public boolean handlesCulture() {
		return false;
	}

	public boolean handlesProfession() {
		return false;
	}

}

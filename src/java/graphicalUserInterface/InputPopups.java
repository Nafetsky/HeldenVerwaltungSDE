package graphicalUserInterface;

import api.AbilityGroup;
import api.BaseAttribute;
import api.Sex;
import api.skills.ImprovementComplexity;
import controle.AddFeatDialogResult;
import controle.AddNewCharakterDialogResult;
import controle.AddNewCombatSkillDialogResult;
import controle.AddNewScriptDialogResult;
import controle.AddSkillDialogResult;
import controle.AddVantageDialogResult;
import database.Species;
import generated.Attributskürzel;
import org.apache.commons.lang3.StringUtils;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;
import java.util.Optional;

public class InputPopups {

	private static final String COSTCATEGORIE_LABEL = "Steigerungskategorie:";
	private static final String NAME_LABEL = "Name:";
	public static final int OK_CANCEL_OPTION = 2;

	private InputPopups() {
		//no op; helper class
	}


	public static AddSkillDialogResult getAddSkillResultDialog(Component parent, String representationTradition) {
		return getAddSkillResultDialog(parent, null, representationTradition);
	}

	public static AddSkillDialogResult getAddSkillResultDialog(Component parent, Object[] attributesToChoose,
															   String representationTradition) {
		JPanel insertSkillPanel = new JPanel(new GridLayout(0, 2));

		JTextField fieldName = new JTextField();
		insertSkillPanel.add(new JLabel(NAME_LABEL));
		insertSkillPanel.add(fieldName);

		JComboBox<Attributskürzel> ability1 = new JComboBox<>();
		JComboBox<Attributskürzel> ability2 = new JComboBox<>();
		JComboBox<Attributskürzel> ability3 = new JComboBox<>();

		for (Attributskürzel acronym : Attributskürzel.values()) {
			ability1.addItem(acronym);
			ability2.addItem(acronym);
			ability3.addItem(acronym);
		}

		JComboBox<ImprovementComplexity> costCategory = new JComboBox<>();
		for (int i = 0; i < 4; ++i) {
			costCategory.addItem(ImprovementComplexity.values()[i]);
		}
		insertSkillPanel.add(new JLabel(COSTCATEGORIE_LABEL));
		insertSkillPanel.add(costCategory);

		JLabel labelRepresentation = new JLabel(representationTradition);
		JTextField fieldRepresentation = new JTextField();
		insertSkillPanel.add(labelRepresentation);
		insertSkillPanel.add(fieldRepresentation);

		Component attribute;
		if (attributesToChoose == null) {
			attribute = new JTextField();
		} else {
			attribute = new JComboBox<>(attributesToChoose);
		}
		insertSkillPanel.add(new JLabel("Merkmal:"));
		insertSkillPanel.add(attribute);

		insertSkillPanel.add(new JLabel("Attribut 1:"));
		insertSkillPanel.add(ability1);
		insertSkillPanel.add(new JLabel("Attribut 2:"));
		insertSkillPanel.add(ability2);
		insertSkillPanel.add(new JLabel("Attribut 3:"));
		insertSkillPanel.add(ability3);

		int result = JOptionPane.showConfirmDialog(parent, insertSkillPanel, "Test", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (result == 0 && StringUtils.isNotEmpty(fieldName.getText())) {
			BaseAttribute[] abilities = new BaseAttribute[3];
			abilities[0] = (BaseAttribute) ability1.getSelectedItem();
			abilities[1] = (BaseAttribute) ability2.getSelectedItem();
			abilities[2] = (BaseAttribute) ability3.getSelectedItem();
			String[] attributesToReturn = new String[2];
			attributesToReturn[0] = representationTradition.toUpperCase() + "_" + fieldRepresentation.getText()
																									 .toUpperCase();
			attributesToReturn[1] = attributesToChoose == null ? ((JTextField) attribute).getText()
					: ((JComboBox<?>) attribute).getSelectedItem()
												.toString();
			return new AddSkillDialogResult(fieldName.getText(), (ImprovementComplexity) costCategory.getSelectedItem(),
					abilities, attributesToReturn);
		}
		return null;
	}

	public static Optional<AddNewCombatSkillDialogResult> getAddCombatSkillResultDialog(Component parent) {
		JPanel insertSkillPanel = new JPanel(new GridLayout(0, 2));

		JTextField fieldName = new JTextField();
		insertSkillPanel.add(new JLabel(NAME_LABEL));
		insertSkillPanel.add(fieldName);

		JComboBox<BaseAttribute> ability1 = new JComboBox<>();


		for (BaseAttribute acronym : BaseAttribute.values()) {
			ability1.addItem(acronym);
		}

		JComboBox<ImprovementComplexity> costCategory = new JComboBox<>();
		for (int i = 0; i < 4; ++i) {
			costCategory.addItem(ImprovementComplexity.values()[i]);
		}
		insertSkillPanel.add(new JLabel(COSTCATEGORIE_LABEL));
		insertSkillPanel.add(costCategory);

		insertSkillPanel.add(new JLabel("Attribut 1:"));
		insertSkillPanel.add(ability1);

		int result = JOptionPane.showConfirmDialog(parent, insertSkillPanel, "Neue Kampftechnik", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (result == 0 && StringUtils.isNotEmpty(fieldName.getText())) {
			BaseAttribute ability = (BaseAttribute) ability1.getSelectedItem();
			return Optional.of(new AddNewCombatSkillDialogResult(fieldName.getText(), ability,
					(ImprovementComplexity) costCategory.getSelectedItem()));
		}
		return Optional.empty();
	}

	public static AddNewScriptDialogResult getAddNewScriptDialogResult(Component parent) {
		JPanel insertScriptPanel = new JPanel(new GridLayout(0, 2));

		JTextField fieldName = new JTextField();
		insertScriptPanel.add(new JLabel(NAME_LABEL));
		insertScriptPanel.add(fieldName);

		JComboBox<ImprovementComplexity> costCategoryChooser = new JComboBox<>();
		for (int i = 0; i < 4; ++i) {
			costCategoryChooser.addItem(ImprovementComplexity.values()[i]);
		}
		insertScriptPanel.add(new JLabel(COSTCATEGORIE_LABEL));
		insertScriptPanel.add(costCategoryChooser);

		int result = JOptionPane.showConfirmDialog(parent, insertScriptPanel, "Neue Schrift", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (result == 0 && StringUtils.isNotEmpty(fieldName.getText())) {
			ImprovementComplexity costCategory = (ImprovementComplexity) costCategoryChooser.getSelectedItem();
			return new AddNewScriptDialogResult(fieldName.getText(), costCategory);
		}
		return null;
	}

	public static AddNewCharakterDialogResult getAddNewCharakterDialogResult(JComponent parent, List<String> cultures, List<String> professions) {

		JPanel newCharakterPanel = new JPanel(new GridLayout(0, 2));

		JTextField fieldName = new JTextField();
		newCharakterPanel.add(new JLabel(NAME_LABEL));
		newCharakterPanel.add(fieldName);

		JComboBox<Sex> comboBoxSex = new JComboBox<>();

		for (Sex sex : Sex.values()) {
			comboBoxSex.addItem(sex);
		}
		newCharakterPanel.add(new JLabel("Geschlecht:"));
		newCharakterPanel.add(comboBoxSex);


		JComboBox<String> comboBoxSpecies = new JComboBox<>();

		for (Species acronym : Species.values()) {
			comboBoxSpecies.addItem(acronym.getName());
		}

		newCharakterPanel.add(new JLabel("Spezies:"));
		newCharakterPanel.add(comboBoxSpecies);

		JComboBox<String> comboBoxCulture = new JComboBox<>();
		for (String culture : cultures) {
			comboBoxCulture.addItem(culture);
		}
		comboBoxCulture.addItem("");
		newCharakterPanel.add(new JLabel("Kultur:"));
		newCharakterPanel.add(comboBoxCulture);


		JComboBox<String> comboBoxProfession = new JComboBox<>();
		for (String profession : professions) {
			comboBoxProfession.addItem(profession);
		}
		comboBoxProfession.addItem("");
		newCharakterPanel.add(new JLabel("Profession:"));
		newCharakterPanel.add(comboBoxProfession);

		int result = JOptionPane.showConfirmDialog(parent, newCharakterPanel, "Neuer Charakter", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (result == 0 && StringUtils.isNotEmpty(fieldName.getText())) {

			return AddNewCharakterDialogResult.builder()
											  .name(fieldName.getText())
											  .species((String) (comboBoxSpecies.getSelectedItem()))
											  .culture((String) comboBoxCulture.getSelectedItem())
											  .profession((String) comboBoxProfession.getSelectedItem())
											  .sex((Sex) comboBoxSex.getSelectedItem())
											  .build();
		}

		return null;


	}

	public static String getSaveDialogResult() {
		return JOptionPane.showInputDialog("Was ist der Grund der zu speichernden �nderung/Steigerung?");
	}

	public static String getNewCultureDialogResult() {
		return JOptionPane.showInputDialog("Name der neuen Kultur?");
	}

	public static String getNewProfessionDialogResult() {
		return JOptionPane.showInputDialog("Name der neuen Profession?");
	}

	public static AddVantageDialogResult getAddVanatgeDialogResult(JComponent parent, boolean isAdvantage) {
		JPanel insertSkillPanel = new JPanel(new GridLayout(0, 2));

		JTextField fieldName = new JTextField();
		insertSkillPanel.add(new JLabel(NAME_LABEL));
		insertSkillPanel.add(fieldName);

		JTextField fieldCots = new JTextField();
		insertSkillPanel.add(new JLabel("Kosten:"));
		insertSkillPanel.add(fieldCots);

		int result = JOptionPane.showConfirmDialog(parent, insertSkillPanel, "Neue Eigenheit", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (result == 0 && StringUtils.isNotEmpty(fieldName.getText())) {
			int cost;
			try {
				cost = Integer.parseInt(fieldCots.getText());
			} catch (NumberFormatException e) {
				cost = -1;
			}

			return AddVantageDialogResult.builder()
										 .name(fieldName.getText())
										 .cost(cost)
										 .advantage(isAdvantage)
										 .build();
		}
		return AddVantageDialogResult.builder()
									 .build();

	}

	public static AddFeatDialogResult getAddFeatResultDialog(Component parent, boolean twoFields) {
		JPanel insertFeatPanel = new JPanel(new GridLayout(0, 1));

		JTextField fieldName = new JTextField();
		insertFeatPanel.add(fieldName);
		insertFeatPanel.add(new JLabel("Name:"));
		JTextField fieldCost = new JTextField();
		if (twoFields) {
			fieldCost.setToolTipText("Die AP Kosten der neuen Sonderfertigkeit (als ganze Zahl");
			insertFeatPanel.add(new JLabel("Kosten:"));
			insertFeatPanel.add(fieldCost);
		}

		JComboBox<AbilityGroup> comboBoxAbilityGroup = new JComboBox<>();
		for (AbilityGroup group : AbilityGroup.values()) {
			comboBoxAbilityGroup.addItem(group);
		}

		int result = JOptionPane.showConfirmDialog(parent, insertFeatPanel, "Test", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (result == 0 && StringUtils.isNotEmpty(fieldName.getText())) {
			try {
				int cost = twoFields ? Integer.parseInt(fieldCost.getText()) : 1;
				if (cost < 1) {
					throw new NumberFormatException(cost + " is no valid cost value for a feat");
				}
				return new AddFeatDialogResult(fieldName.getText(), cost, (AbilityGroup) comboBoxAbilityGroup.getSelectedItem());
			} catch (NumberFormatException e) {
				// TODO pop something up the user is an idiot
			}
		}
		return null;
	}
}

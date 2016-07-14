package graphicalUserInterface;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dataBase.Ability;
import generated.Attribut;
import generated.Charakter;
import utils.CharacterModifier;
import utils.MarshallingHelper;
import utils.Skill;
import utils.SkillFinder;

public class PaneBuilder {

	Charakter charakter;
	SkillFinder finder;
	CharacterModifier modifier;
	MarshallingHelper helper;

	PaneBuilder(Charakter charakter) {
		this.charakter = charakter;
		modifier = new CharacterModifier(charakter);
		finder = new SkillFinder(charakter);
		helper = MarshallingHelper.getInstance();
	}
	
	public Component makeBaseInfos() {
		JPanel baseSkillPanel = new JPanel();
		baseSkillPanel.setLayout(new BoxLayout(baseSkillPanel, BoxLayout.Y_AXIS));
		JButton button = new JButton("print for test");
		button.addActionListener((ActionEvent e) -> {
			modifier.saveChanges();
			System.out.println(helper.marshall(charakter));
		});
		baseSkillPanel.add(button);
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
			
			JLabel costs = new JLabel(Integer.toString(skillable.getCostForNextLevel())+" AP");

			JButton button = new JButton("+1");
			button.addActionListener(((ActionEvent e) -> {
				modifier.increaseSkillByOne(Ability.getAbility(ability.getKürzel()).getName());
				field.setText(new String(Integer.toString(ability.getAttributswert())));
				costs.setText(Integer.toString(skillable.getCostForNextLevel())+" AP");
			}));
			abilityPanel.add(button);
			abilityPanel.add(costs);
			
		}
		return abilityPanel;
	}

}

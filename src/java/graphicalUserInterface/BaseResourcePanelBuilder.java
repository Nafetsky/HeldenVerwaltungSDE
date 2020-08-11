package graphicalUserInterface;

import api.base.Character;
import database.BaseResourceData;
import main.MasterControleProgramm;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BaseResourcePanelBuilder {


	public JPanel buildBaseResourcePanel(Character charakter, MasterControleProgramm controleInstance) {
		JPanel pResources = new JPanel();
		List<BaseResourceData> relevantResources = Arrays.stream(BaseResourceData.values())
														 .filter(baseResourceData -> baseResourceData.isRelevant(charakter))
														 .collect(Collectors.toList());
		int rows = relevantResources.size();
		pResources.setLayout(new GridLayout(rows, 9));


		relevantResources.stream()
						 .map(baseResourceData -> buildBaseResourceRow(baseResourceData, charakter, controleInstance))
						 .flatMap(List::stream)
						 .forEach(pResources::add);

		return pResources;

	}

	private List<Component> buildBaseResourceRow(BaseResourceData baseResourceData, Character charakter, MasterControleProgramm controleInstance) {
		List<Component> components = new ArrayList<>();

		List<Component> buyEnergyButton = BuildLabelFieldButtonGroup(baseResourceData, charakter, controleInstance, BaseResourceData.BOUGHT_PREFIX, BaseResourceData.BUY_PREFIX);
		components.addAll(buyEnergyButton);

		if(baseResourceData == BaseResourceData.LIFE){
			components.add(new JLabel(""));
			components.add(new JLabel(""));
			components.add(new JLabel(""));
			components.add(new JLabel(""));
			components.add(new JLabel(""));
			components.add(new JLabel(""));
			return components;
		}
		List<Component> looseEnergyButton = BuildLabelFieldButtonGroup(baseResourceData, charakter, controleInstance, BaseResourceData.LOST_PREFIX, BaseResourceData.LOOSE_PREFIX);
		components.addAll(looseEnergyButton);
		List<Component> restoreEnergyButton = BuildLabelFieldButtonGroup(baseResourceData, charakter, controleInstance, BaseResourceData.RESTORED_PREFIX, BaseResourceData.RESTORE_PREFIX);
		components.addAll(restoreEnergyButton);


		return components;
	}

	private List<Component> BuildLabelFieldButtonGroup(BaseResourceData baseResourceData, Character charakter, MasterControleProgramm controleInstance, String labelText, String buttonPrefix) {
		String name = baseResourceData.getName();

		List<Component> components = new ArrayList<>();
		JLabel boughtEnergyLabel = new JLabel(labelText + name);
		components.add(boughtEnergyLabel);
		JTextField boughtEnergyField = new JTextField();
		boughtEnergyField.setEditable(false);
		boughtEnergyField.setText(Integer.toString(baseResourceData.getBoughtPoints(charakter)));
		components.add(boughtEnergyField);

		JButton buyEnergyButton = new JButton();
		buyEnergyButton.setText(buttonPrefix + name);
		buyEnergyButton.addActionListener((ActionEvent e) -> {
			controleInstance.handleIncreaseSkill(buttonPrefix + name);
			boughtEnergyField.setText(Integer.toString(Integer.parseInt(boughtEnergyField.getText()) + 1));
		});
		components.add(buyEnergyButton);
		return components;
	}
}

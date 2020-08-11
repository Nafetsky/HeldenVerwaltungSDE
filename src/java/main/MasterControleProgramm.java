package main;

import XsdWrapper.CharacterXml;
import XsdWrapper.DescriptorTranslator;
import XsdWrapper.MarshallingHelper;
import api.AbilityGroup;
import api.Advantage;
import api.BaseAttribute;
import api.CombatTechnique;
import api.CombatTechniqueImpl;
import api.DescribesSkill;
import api.Disadvantage;
import api.Event;
import api.ILanguage;
import api.ISpecialAbility;
import api.Language;
import api.SpecialAbility;
import api.base.Character;
import api.skills.Descriptor;
import api.skills.Skill;
import api.skills.SkillImpl;
import controle.AddFeatDialogResult;
import controle.AddNewCharakterDialogResult;
import controle.AddNewCombatSkillDialogResult;
import controle.AddNewScriptDialogResult;
import controle.AddSkillDialogResult;
import controle.AddVantageDialogResult;
import database.BaseResourceData;
import generated.MetaData;
import generated.MetaDataLine;
import graphicalUserInterface.MainFrame;
import graphicalUserInterface.PaneBuilder;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.JAXBException;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * @author Stefan no chess computer
 */
@Log4j2
public class MasterControleProgramm {


	private static final String METADATA_XML = "metadata.xml";
	private static final String MAIN_DIRECTORY = "heldenverwaltungsDaten";
	private static final String CHARAKTER_DIRECTORY = "charaktere";
	private static final String CULTURE_DIRECTORY = "kulturen";
	private static final String PROFESSION_DIRECTORY = "professionen";

	private static final int CHARACTER = 1;
	private static final int CULTURE = 2;
	private static final int PROFESSION = 3;


	private Character activeCharacter;
	private MarshallingHelper helper;
	private PaneBuilder paneBuilder;
	private MetaData metaData;
	private MetaData metaDataCultures;
	private MetaData metaDataProfessions;
	private MainFrame frame;
	private String fileSeperator;
	private List<Character> unsavedCharakters;

	public MasterControleProgramm() throws Exception {
		fileSeperator = File.separator;
		unsavedCharakters = new ArrayList<>();
		helper = new MarshallingHelper();
		prepareDirectoryStructure();
		// loadCharakter("Barundar");
		loadMetaData();
		loadCharakter(!getAllCharakterNames().isEmpty() ? getAllCharakterNames().get(0) : "");
		paneBuilder = new PaneBuilder(activeCharacter, this, getAllCharakterNames());
		frame = new MainFrame(paneBuilder);
		frame.setVisible(true);
	}

	public void handleIncreaseSkill(String name) {
		activeCharacter.getSkillLevler(name)
					   .level();
	}

	public void handleAddAp(int newAp) {
		Event newApEvent = Event.builder()
								.adventurePoints(newAp)
								.build();
		activeCharacter.increase(newApEvent);
	}

	public void handleSaveActiveCharakter(String reason) {
		activeCharacter.save(reason);

		try {
			FileOutputStream fileWriter;

			String fileToName = "";
			// TODO clean this mess up
//			if (activeCharacter.isCharakter()) {
			fileToName = MetaDataHandler.getFileToName(metaData, activeCharacter.getMetaData()
																				.getName());
//			}
//			if (activeCharacter.isCulture()) {
//				fileToName = MetaDataHandler.getFileToName(metaDataCultures, activeCharacter.getName());
//			}
//			if (activeCharacter.isProfession()) {
//				fileToName = MetaDataHandler.getFileToName(metaDataProfessions, activeCharacter.getName());
//			}
			if (StringUtils.isEmpty(fileToName)) {
//				if (activeCharacter.isCharakter()) {
				fileToName = MetaDataHandler.addEntryToMetaData(metaData, activeCharacter.getMetaData()
																						 .getName());
				fileWriter = new FileOutputStream(new File(MAIN_DIRECTORY + fileSeperator + METADATA_XML));
				unsavedCharakters.remove(activeCharacter);
				fileWriter.write(helper.marshall(metaData)
									   .getBytes(StandardCharsets.UTF_8));
				fileWriter.flush();
				fileWriter.close();
				loadMetaData();
//				} else if (activeCharacter.isCulture()) {
//					fileToName = MetaDataHandler.addEntryToMetaData(metaDataCultures, activeCharacter.getName());
//					fileWriter = new FileOutputStream(new File(
//							MAIN_DIRECTORY + fileSeperator + CULTURE_DIRECTORY + fileSeperator + METADATA_XML));
//					unsavedCharakters.remove(activeCharacter);
//					fileWriter.write(helper.marshall(metaDataCultures)
//										   .getBytes(StandardCharsets.UTF_8));
//					fileWriter.flush();
//					fileWriter.close();
//					loadMetaData();
//				} else if (activeCharacter.isProfession()) {
//					fileToName = MetaDataHandler.addEntryToMetaData(metaDataProfessions, activeCharacter.getName());
//					fileWriter = new FileOutputStream(new File(
//							MAIN_DIRECTORY + fileSeperator + PROFESSION_DIRECTORY + fileSeperator + METADATA_XML));
//					unsavedCharakters.remove(activeCharacter);
//					fileWriter.write(helper.marshall(metaDataProfessions)
//										   .getBytes(StandardCharsets.UTF_8));
//					fileWriter.flush();
//					fileWriter.close();
//					loadMetaData();
//				}
			}

			fileWriter = new FileOutputStream(new File(makeFilePathAndName(fileToName, CHARACTER)));
			fileWriter.write(activeCharacter.getContent()
											.getBytes(StandardCharsets.UTF_8));
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			LOGGER.error("wentWrong", e);
		} finally {

		}
	}

	private void prepareDirectoryStructure() {
		File mainDirectory = new File(MAIN_DIRECTORY);
		if (!(mainDirectory.exists() && mainDirectory.isDirectory())) {
			mainDirectory.mkdir();
		}
		File charakterDirectory = new File(MAIN_DIRECTORY + fileSeperator + CHARAKTER_DIRECTORY);
		if (!(charakterDirectory.exists() && charakterDirectory.isDirectory())) {
			charakterDirectory.mkdir();
		}

		File cultureDirectory = new File(MAIN_DIRECTORY + fileSeperator + CULTURE_DIRECTORY);
		if (!(cultureDirectory.exists() && cultureDirectory.isDirectory())) {
			cultureDirectory.mkdir();
		}

		File professionDirectory = new File(MAIN_DIRECTORY + fileSeperator + PROFESSION_DIRECTORY);
		if (!(professionDirectory.exists() && professionDirectory.isDirectory())) {
			professionDirectory.mkdir();
		}

	}

	public void handlePrintCharakter() {
		System.out.println(activeCharacter.getContent());
	}

	public void handleNewSkillSpecialisation(AddFeatDialogResult result, String skillName) {
		ISpecialAbility o = SpecialAbility.builder()
										  .name(result.getName())
										  .descriptors(Collections.singletonList(new DescribesSkill(skillName)))
										  .cost(result.getCost())
										  .group(AbilityGroup.SPECIALISATION)
										  .build();
		List<ISpecialAbility> abilities = Collections.singletonList(o);
		Event event = Event.builder()
						   .abilities(abilities)
						   .build();
		activeCharacter.increase(event);
	}

	public ISpecialAbility handleNewFeat(AddFeatDialogResult result) {
		ISpecialAbility feat = SpecialAbility.builder()
											 .name(result.getName())
											 .cost(result.getCost())
											 .group(result.getGroup())
											 .build();
		Event build = Event.builder()
						   .abilities(Collections.singletonList(feat))
						   .build();
		activeCharacter.increase(build);
		return feat;
	}

	public Optional<Skill> handleNewSkill(AddSkillDialogResult result) {
		if (result.isComplete()) {
			DescriptorTranslator translator = new DescriptorTranslator();
			Descriptor[] descriptors = translator.translateToDescriptors(Arrays.asList(result.getDescriptors()));
			SkillImpl o = new SkillImpl(result.getName(), result.getGroup(), result.getAbilities(), descriptors, result.getCostCategory());
			Event build = Event.builder()
							   .learnedSkills(Collections.singletonList(o))
							   .build();
			activeCharacter.increase(build);
			return activeCharacter.getSkills()
						   .stream()
						   .filter(skill -> StringUtils.equals(skill.getName(), result.getName()))
						   .findFirst();
		}
		return Optional.empty();
	}

	public CombatTechnique handleNewCombatSkill(AddNewCombatSkillDialogResult result) {
		if (result.isComplete()) {
			CombatTechnique combatTechnique = new CombatTechniqueImpl(result.getName(), result.getAbility(), result.getCostCategory());
			combatTechnique.setLevel(6);
			Event event = Event.builder()
							   .learnedCombatTechniques(Collections.singletonList(combatTechnique))
							   .build();
			activeCharacter.increase(event);
			return combatTechnique;
		}
		return null;
	}

	private void loadMetaData() {
		metaData = loadMetaDataFile(MAIN_DIRECTORY + fileSeperator + METADATA_XML);
		metaDataCultures = loadMetaDataFile(
				MAIN_DIRECTORY + fileSeperator + CULTURE_DIRECTORY + fileSeperator + METADATA_XML);
		metaDataProfessions = loadMetaDataFile(
				MAIN_DIRECTORY + fileSeperator + PROFESSION_DIRECTORY + fileSeperator + METADATA_XML);
	}

	private MetaData loadMetaDataFile(String qualifiedPath) {
		File metaDataProfessionFile = new File(qualifiedPath);
		if (metaDataProfessionFile.exists()) {
			return loadMetaData(metaDataProfessionFile);
		} else {
			return helper.makeNewEmptyMetaData();
		}
	}

	private MetaData loadMetaData(File metaDataFile) {
		return helper.unmarshallMetaData(metaDataFile);
	}

	private void loadCharakter(String name) {
		try {
			Character newActiveCharakter = loadCharakterUnsave(name);
			if (null != newActiveCharakter) {
				activeCharacter = newActiveCharakter;
			}

//			finder = new SkillFinder(activeCharacter);
//			modifier = new CharacterModifier(activeCharacter);
		} catch (Exception e) {
			LOGGER.error("Went wrong", e);
		}
	}

	private Character loadCharakterUnsave(String name) {
		Character newCharkter;
		newCharkter = searchMetaData(metaData, name, CHARACTER);
		if (null != newCharkter) {
			return newCharkter;
		}
		newCharkter = searchMetaData(metaDataCultures, name, CULTURE);
		if (null != newCharkter) {
			return newCharkter;
		}

		newCharkter = searchMetaData(metaDataProfessions, name, PROFESSION);
		if (null != newCharkter) {
			return newCharkter;
		}
		for (Character character : unsavedCharakters) {
			if (StringUtils.equals(name, character.getMetaData()
												  .getName())) {
				return character;
			}
		}
		return newCharkter;
	}

	private Character searchMetaData(MetaData metaDataParam, String name, int type) {
		for (MetaDataLine charakterLine : metaDataParam.getCharakters()) {
			if (StringUtils.equals(charakterLine.getName(), name)) {
				try {
					return loadCharacter(type, charakterLine);
				} catch (JAXBException e) {
					LOGGER.error("Unmarshalling of " + name + " failed", e);
				}
			}
		}
		return null;
	}

	private Character loadCharacter(int type, MetaDataLine charakterLine) throws JAXBException {
		File inputFile = new File(makeFilePathAndName(charakterLine.getDatei(), type));
		if (inputFile.exists()) {
			switch (type) {
				case CHARACTER:
					return new CharacterXml(helper.unmarshallCharakter(inputFile));
				case CULTURE:
//					return WrappedCharakter.getWrappedCulture(helper.unmarshallSchablone(inputFile));
				case PROFESSION:
//					return WrappedCharakter.getWrappedProfession(helper.unmarshallSchablone(inputFile));
					throw new UnsupportedOperationException(
							"No support for cultures and professions yet. And perhaps never");
				default:
					throw new UnsupportedOperationException(
							"You screwed big time. In this enum is no other value.");
			}
		} else {
			return null;
		}
	}

	private String makeFilePathAndName(String fileName, int type) {
		switch (type) {
			case CHARACTER:
				return MAIN_DIRECTORY + fileSeperator + CHARAKTER_DIRECTORY + fileSeperator + fileName;
			case CULTURE:
				return MAIN_DIRECTORY + fileSeperator + CULTURE_DIRECTORY + fileSeperator + fileName;
			case PROFESSION:
				return MAIN_DIRECTORY + fileSeperator + PROFESSION_DIRECTORY + fileSeperator + fileName;
			default:
				throw new UnsupportedOperationException(
						"trying to save something impossible to save (Profession for the moment)");
		}
	}

	protected List<String> getAllCharakterNames() {
		List<String> allCharakterNames = new ArrayList<>();
		for (MetaDataLine metaDate : metaData.getCharakters()) {
			allCharakterNames.add(metaDate.getName());
		}
		allCharakterNames.addAll(getCultures());
		for (Character charakter : unsavedCharakters) {
			allCharakterNames.add(charakter.getMetaData()
										   .getName());
		}
		return allCharakterNames;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				@SuppressWarnings("unused")
				MasterControleProgramm control = new MasterControleProgramm();
			} catch (Exception e) {
				LOGGER.error("", e);
			}
		});
	}

	public void switchActiveCharakter(String newActiveCharakter) {
		if (StringUtils.isEmpty(newActiveCharakter)) {
			return;
		}
		if (!getAllCharakterNames().contains(newActiveCharakter)) {
			return;
		}
		if (activeCharacter == null || !StringUtils.equals(activeCharacter.getMetaData()
																		  .getName(), newActiveCharakter)) {
			loadCharakter(newActiveCharakter);
			paneBuilder = new PaneBuilder(activeCharacter, this, getAllCharakterNames());
			frame.setPaneBuilder(paneBuilder);
		}
	}

	public void handleNewCharakter(AddNewCharakterDialogResult addNew) {
		if (null != addNew && addNew.isComplete()) {
			Character character = new CharacterXml(CharakterGenerator.generteNewCharakter(addNew));
			unsavedCharakters.add(character);
			switchActiveCharakter(addNew.getName());
			if (getCultures().contains(addNew.getCulture())) {
//				WrappedCharakter cultureToAdd = loadCharakterUnsave(addNew.getCulture());
//				modifier.applyTemplate(cultureToAdd);
				paneBuilder = new PaneBuilder(activeCharacter, this, getAllCharakterNames());
				frame.setPaneBuilder(paneBuilder);
			}

			if (getProfessions().contains(addNew.getProfession())) {
//				WrappedCharakter professionToAply = loadCharakterUnsave(addNew.getProfession());
//				modifier.applyTemplate(professionToAply);
				paneBuilder = new PaneBuilder(activeCharacter, this, getAllCharakterNames());
				frame.setPaneBuilder(paneBuilder);
			}
		}
	}

	public void handleNewCulture(String result) {
		throw new UnsupportedOperationException("Not yet supported");
//		Schablone template = CharakterGenerator.generateNewSchablone(result);
//		unsavedCharakters.add(WrappedCharakter.getWrappedCulture(template));
//		switchActiveCharakter(template.getName());
	}

	public void handleNewProfession(String result) {
		throw new UnsupportedOperationException("Not yet supported");
//		Schablone template = CharakterGenerator.generateNewSchablone(result);
//		unsavedCharakters.add(WrappedCharakter.getWrappedProfession(template));
//		switchActiveCharakter(template.getName());
	}

	public List<String> getCultures() {
		return getAllFromMeta(CULTURE);
	}

	public List<String> getProfessions() {
		return getAllFromMeta(PROFESSION);
	}

	private List<String> getAllFromMeta(int type) {
		List<String> allCulturesNames = new ArrayList<String>();
		MetaData metaDataToRead;
		switch (type) {
			case CHARACTER:
				metaDataToRead = metaData;
				break;
			case CULTURE:
				metaDataToRead = metaDataCultures;
				break;
			case PROFESSION:
				metaDataToRead = metaDataProfessions;
				break;
			default:
				throw new UnsupportedOperationException(Integer.toString(type) + " is no valid WrappedCharakter type");
		}
		for (MetaDataLine metaDate : metaDataToRead.getCharakters()) {
			allCulturesNames.add(metaDate.getName());
		}
		return allCulturesNames;
	}

	public boolean handleNewAdvantage(AddVantageDialogResult result) {
		Event.EventBuilder builder = Event.builder();
		if (result.isAdvantage()) {
			List<Advantage> advantages = Collections.singletonList(new Advantage(result.getName(), result.getCost()));
			builder.advantages(advantages);
		} else {
			List<Disadvantage> disadvantages = Collections.singletonList(new Disadvantage(result.getName(), result.getCost()));
			builder.disadvantages(disadvantages);
		}
		Event event = builder.build();
		activeCharacter.increase(event);
		return true;

	}

	public void handleIncreaseLanguage(String name) {
//		modifier.increaseLanguage(name);
		ILanguage o = new Language(name);
		o.setLevel(1);
		Event build = Event.builder()
						   .abilities(Collections.singletonList(o))
						   .build();
		activeCharacter.increase(build);
	}

	public void handleAddScript(AddNewScriptDialogResult result) {
		ISpecialAbility o = SpecialAbility.builder()
										  .name(result.getName())
										  .cost(result.getCostCategorie()
													  .getFactor() * 2)
										  .group(AbilityGroup.SCRIPTURE)
										  .build();
		Event build = Event.builder()
						   .abilities(Collections.singletonList(o))
						   .build();
		activeCharacter.increase(build);
	}

	public void handleChangeName(String value) {
//		activeCharacter.getMetaData().setName(value);

	}

	public void handleChangeCulture(String value) {
		//
	}

	public void handleChangeAge(String value) {
		throw new UnsupportedOperationException("Age change not yet implemented");
//		try {
//			activeCharacter.getMetaData()
//						   .setAge(Integer.parseInt(value));
//		} catch (NumberFormatException e) {
//			LOGGER.error(value + " is no valid age", e);
//		}

	}

	public void handleIncreaseAttribute(BaseAttribute abilityAbstract) {
		activeCharacter.getSkillLevler(abilityAbstract)
					   .level();
	}
}

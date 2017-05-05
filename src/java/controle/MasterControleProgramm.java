package controle;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import database.FeatGroup;
import generated.Charakter;
import generated.MetaData;
import generated.MetaDataLine;
import generated.Schablone;
import generated.Sonderfertigkeit;
import graphicalUserInterface.MainFrame;
import graphicalUserInterface.PaneBuilder;
import utils.CharacterModifier;
import utils.CharakterGenerator;
import utils.MarshallingHelper;
import utils.MetaDataHandler;
import utils.Skill;
import utils.SkillFinder;
import utils.WrappedCharakter;

/**
 * 
 * @author Stefan no chess computer
 */
public class MasterControleProgramm {

	private static final Logger LOGGER = Logger.getLogger(MasterControleProgramm.class);

	private static final String METADATA_XML = "metadata.xml";
	private static final String MAIN_DIRECTORY = "heldenverwaltungsDaten";
	private static final String CHARAKTER_DIRECTORY = "charaktere";
	private static final String CULTURE_DIRECTORY = "kulturen";
	private static final String PROFESSION_DIRECTORY = "professionen";
	WrappedCharakter activeCharakter;
	SkillFinder finder;
	CharacterModifier modifier;
	MarshallingHelper helper;
	PaneBuilder paneBuilder;
	MetaData metaData;
	MetaData metaDataCultures;
	MetaData metaDataProfessions;
	MainFrame frame;
	String fileSeperator;
	List<WrappedCharakter> unsavedCharakters;

	public MasterControleProgramm() throws Exception {
		fileSeperator = File.separator;
		unsavedCharakters = new ArrayList<WrappedCharakter>();
		helper = MarshallingHelper.getInstance();
		prepareDirectoryStructure();
		// loadCharakter("Barundar");
		loadMetaData();
		loadCharakter(!getAllCharakterNames().isEmpty() ? getAllCharakterNames().get(0) : "");
		paneBuilder = new PaneBuilder(activeCharakter, this, getAllCharakterNames());
		frame = new MainFrame(paneBuilder);
		frame.setVisible(true);
	}

	public void handlencreaseSkill(String name) {
		modifier.increaseSkillByOne(name);
	}

	public void handleAddAp(int newAp) {
		modifier.changeApBy(newAp);
	}

	public void handleSaveActiveCharakter(String reason) {
		modifier.saveChanges(reason);
		try {
			FileOutputStream fileWriter;

			String fileToName = "";
			// TODO clean this mess up
			if (activeCharakter.isCharakter()) {
				fileToName = MetaDataHandler.getFileToName(metaData, activeCharakter.getName());
			}
			if (activeCharakter.isCulture()) {
				fileToName = MetaDataHandler.getFileToName(metaDataCultures, activeCharakter.getName());
			}
			if (activeCharakter.isProfession()) {
				fileToName = MetaDataHandler.getFileToName(metaDataProfessions, activeCharakter.getName());
			}
			if (StringUtils.isEmpty(fileToName)) {
				if (activeCharakter.isCharakter()) {
					fileToName = MetaDataHandler.addEntryToMetaData(metaData, activeCharakter.getName());
					fileWriter = new FileOutputStream(new File(MAIN_DIRECTORY + fileSeperator + METADATA_XML));
					unsavedCharakters.remove(activeCharakter);
					fileWriter.write(helper.marshall(metaData).getBytes(StandardCharsets.UTF_8));
					fileWriter.flush();
					fileWriter.close();
					loadMetaData();
				} else if (activeCharakter.isCulture()) {
					fileToName = MetaDataHandler.addEntryToMetaData(metaDataCultures, activeCharakter.getName());
					fileWriter = new FileOutputStream(new File(
							MAIN_DIRECTORY + fileSeperator + CULTURE_DIRECTORY + fileSeperator + METADATA_XML));
					unsavedCharakters.remove(activeCharakter);
					fileWriter.write(helper.marshall(metaDataCultures).getBytes(StandardCharsets.UTF_8));
					fileWriter.flush();
					fileWriter.close();
					loadMetaData();
				} else if (activeCharakter.isProfession()) {
					fileToName = MetaDataHandler.addEntryToMetaData(metaDataProfessions, activeCharakter.getName());
					fileWriter = new FileOutputStream(new File(
							MAIN_DIRECTORY + fileSeperator + PROFESSION_DIRECTORY + fileSeperator + METADATA_XML));
					unsavedCharakters.remove(activeCharakter);
					fileWriter.write(helper.marshall(metaDataProfessions).getBytes(StandardCharsets.UTF_8));
					fileWriter.flush();
					fileWriter.close();
					loadMetaData();
				}
			}

			fileWriter = new FileOutputStream(new File(makeFilePathAndName(fileToName, activeCharakter.getType())));
			fileWriter.write(helper.marshall(activeCharakter).getBytes(StandardCharsets.UTF_8));
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
		System.out.println(helper.marshall(activeCharakter));
	}

	public void handleNewSkillSpecialisation(AddFeatDialogResult result, String skillName) {
		modifier.addSkillSpecialisation(result.name, skillName);
	}

	public Sonderfertigkeit handleNewFeat(AddFeatDialogResult result, FeatGroup group) {
		return handleNewFeat(result.name, result.cost, group);
	}

	public Sonderfertigkeit handleNewFeat(String name, int cost, FeatGroup group) {
		return modifier.addFeat(name, cost, group);
	}

	public Skill handleNewSkill(AddSkillDialogResult result) {
		if (result.isComplete()) {
			return modifier.addSkill(result);
		}
		return null;
	}

	public Skill handleNewCombatSkill(AddNewCombatSkillDialogResult result) {
		if (result.isComplete()) {
			return modifier.addSkill(result);
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
			WrappedCharakter newActiveCharakter = loadCharakterUnsave(name);
			if (null != newActiveCharakter) {
				activeCharakter = newActiveCharakter;
			}

			finder = new SkillFinder(activeCharakter);
			modifier = new CharacterModifier(activeCharakter);
		} catch (Exception e) {
			LOGGER.error("Went wrong", e);
		}
	}

	private WrappedCharakter loadCharakterUnsave(String name) {
		WrappedCharakter newCharkter;
		newCharkter = searchMetaData(metaData, name, WrappedCharakter.CHARAKTER);
		if (null != newCharkter) {
			return newCharkter;
		}
		newCharkter = searchMetaData(metaDataCultures, name, WrappedCharakter.CULTURE);
		if (null != newCharkter) {
			return newCharkter;
		}

		newCharkter = searchMetaData(metaDataProfessions, name, WrappedCharakter.PROFESSION);
		if (null != newCharkter) {
			return newCharkter;
		}
		for (WrappedCharakter charakter : unsavedCharakters) {
			if (StringUtils.equals(name, charakter.getName())) {
				return charakter;
			}
		}
		return newCharkter;
	}

	private WrappedCharakter searchMetaData(MetaData metaDataParam, String name, int type) {
		for (MetaDataLine charakterLine : metaDataParam.getCharakters()) {
			if (StringUtils.equals(charakterLine.getName(), name)) {
				try {
					return loadCharacter(type, charakterLine);
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private WrappedCharakter loadCharacter(int type, MetaDataLine charakterLine) throws JAXBException {
		File inputFile = new File(makeFilePathAndName(charakterLine.getDatei(), type));
		if (inputFile.exists()) {
			switch (type) {
			case WrappedCharakter.CHARAKTER:
				return WrappedCharakter.getWrappedCharakter(helper.unmarshallCharakter(inputFile));
			case WrappedCharakter.PROFESSION:
				return WrappedCharakter.getWrappedProfession(helper.unmarshallSchablone(inputFile));
			case WrappedCharakter.CULTURE:
				return WrappedCharakter.getWrappedCulture(helper.unmarshallSchablone(inputFile));
			default:
				throw new UnsupportedOperationException(
						"You screwd big time. In this enum is no other value.");
			}
		} else {
			return null;
		}
	}

	private String makeFilePathAndName(String fileName, int type) {
		switch (type) {
		case WrappedCharakter.CHARAKTER:
			return MAIN_DIRECTORY + fileSeperator + CHARAKTER_DIRECTORY + fileSeperator + fileName;
		case WrappedCharakter.CULTURE:
			return MAIN_DIRECTORY + fileSeperator + CULTURE_DIRECTORY + fileSeperator + fileName;
		case WrappedCharakter.PROFESSION:
			return MAIN_DIRECTORY + fileSeperator + PROFESSION_DIRECTORY + fileSeperator + fileName;
		default:
			throw new UnsupportedOperationException(
					"trying to save something impossible to save (Profession for the moment)");
		}
	}

	protected List<String> getAllCharakterNames() {
		List<String> allCharakterNames = new ArrayList<String>();
		for (MetaDataLine metaDate : metaData.getCharakters()) {
			allCharakterNames.add(metaDate.getName());
		}
		allCharakterNames.addAll(getCultures());
		for (WrappedCharakter charakter : unsavedCharakters) {
			allCharakterNames.add(charakter.getName());
		}
		return allCharakterNames;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() ->{
			try {
					@SuppressWarnings("unused")
					MasterControleProgramm control = new MasterControleProgramm();
				} catch (Exception e) {
					e.printStackTrace();
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
		if (activeCharakter == null || !StringUtils.equals(activeCharakter.getName(), newActiveCharakter)) {
			loadCharakter(newActiveCharakter);
			paneBuilder = new PaneBuilder(activeCharakter, this, getAllCharakterNames());
			frame.setPaneBuilder(paneBuilder);
		}
	}

	public void handleNewCharakter(AddNewCharakterDialogResult addNew) {
		if (null != addNew && addNew.isComplete()) {
			Charakter charakter = CharakterGenerator.generteNewCharakter(addNew);
			unsavedCharakters.add(WrappedCharakter.getWrappedCharakter(charakter));
			switchActiveCharakter(addNew.getName());
			if (getCultures().contains(addNew.getCulture())) {
				WrappedCharakter cultureToAdd = loadCharakterUnsave(addNew.getCulture());
				modifier.applyTemplate(cultureToAdd);
				paneBuilder = new PaneBuilder(activeCharakter, this, getAllCharakterNames());
				frame.setPaneBuilder(paneBuilder);
			}

			if (getProfessions().contains(addNew.getProfession())) {
				WrappedCharakter professionToAply = loadCharakterUnsave(addNew.getProfession());
				modifier.applyTemplate(professionToAply);
				paneBuilder = new PaneBuilder(activeCharakter, this, getAllCharakterNames());
				frame.setPaneBuilder(paneBuilder);
			}
		}
	}

	public void handleNewCulture(String result) {
		Schablone template = CharakterGenerator.generateNewSchablone(result);
		unsavedCharakters.add(WrappedCharakter.getWrappedCulture(template));
		switchActiveCharakter(template.getName());
	}

	public void handleNewProfession(String result) {
		Schablone template = CharakterGenerator.generateNewSchablone(result);
		unsavedCharakters.add(WrappedCharakter.getWrappedProfession(template));
		switchActiveCharakter(template.getName());
	}

	public List<String> getCultures() {
		return getAllFromMeta(WrappedCharakter.CULTURE);
	}

	public List<String> getProfessions() {
		return getAllFromMeta(WrappedCharakter.PROFESSION);
	}

	private List<String> getAllFromMeta(int type) {
		List<String> allCulturesNames = new ArrayList<String>();
		MetaData metaDataToRead;
		switch (type) {
		case WrappedCharakter.CHARAKTER:
			metaDataToRead = metaData;
			break;
		case WrappedCharakter.CULTURE:
			metaDataToRead = metaDataCultures;
			break;
		case WrappedCharakter.PROFESSION:
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
		if (result.isAdvantage()) {
			modifier.addAdvantage(result.getName(), result.getCost());
		} else {
			modifier.addDisadvantage(result.getName(), result.getCost());
		}
		return true;

	}

	public void handleIncreaseLangue(String name) {
		modifier.increaseLanguage(name);
	}

	public void handleAddScript(AddNewScriptDialogResult result) {
		modifier.addScript(result.getName(), result.getCostCategorie());
	}

	public void handleChangeName(String value) {
		activeCharakter.setName(value);

	}

	public void handleChangeCulture(String value) {
		activeCharakter.setCulture(value);
	}

	public void handleChangeAge(String value) {
		try {
			Integer.parseInt(value);
			activeCharakter.getAngaben().setAlter(Integer.parseInt(value));
		} catch (NumberFormatException e) {

		}

	}

}

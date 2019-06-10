package XsdWrapper;

import api.BaseSkills;
import api.ISpecialAbility;
import generated.Basistalent;
import generated.Charakter;
import generated.Fertigkeit;
import generated.Fertigkeitskategorie;
import generated.MerkmalProfan;
import generated.ObjectFactory;
import generated.Steigerungskategorie;
import generated.Talentspezialisierung;
import org.junit.jupiter.api.Test;
import utility.TestPreparer;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

class SkillSpecialisationTranslatorTest {


	@Test
	void integrationTest() throws Exception {
		TestPreparer testPreparer = new TestPreparer();
		Charakter barundar = testPreparer.getRawBarundar();
		SkillSpecialisationTranslator translator = new SkillSpecialisationTranslator(barundar);
		List<ISpecialAbility> specialAbilities = translator.translate(barundar.getSonderfertigkeiten()
																	   .getTalentspezialisierung());
		assertThat(specialAbilities, hasSize(3));
		int sum = specialAbilities.stream()
								  .mapToInt(ISpecialAbility::getCost)
								  .sum();
		assertThat(sum, is(10));
	}


	@Test
	void singleTest(){
		ObjectFactory factory = new ObjectFactory();
		Charakter charakter = factory.createCharakter();
		charakter.setSonderfertigkeiten(factory.createSonderfertigkeiten());
		List<Talentspezialisierung> talentspezialisierungs = charakter.getSonderfertigkeiten()
																	  .getTalentspezialisierung();
		charakter.setTalente(factory.createTalente());
		Basistalent metalCraft = factory.createBasistalent();
		metalCraft.setName(BaseSkills.METALCRAFT.getName());
		metalCraft.setMerkmal(MerkmalProfan.HANDWERK);
		charakter.getTalente().getTalent().add(metalCraft);
		Talentspezialisierung blacksmith = factory.createTalentspezialisierung();
		blacksmith.setFertigkeit(BaseSkills.METALCRAFT.getName());
		blacksmith.setName("Grobschmied");
		talentspezialisierungs.add(blacksmith);
		SkillSpecialisationTranslator translator = new SkillSpecialisationTranslator(charakter);

		//execute
		List<ISpecialAbility> specialAbilities = translator.translate(charakter.getSonderfertigkeiten()
																		.getTalentspezialisierung());

		//assert
		assertThat(specialAbilities, hasSize(1));
		int sum = specialAbilities.stream()
								  .mapToInt(ISpecialAbility::getCost)
								  .sum();
		assertThat(sum, is(3));
	}

	@Test
	void testMultipleSpecialisations(){
		ObjectFactory factory = new ObjectFactory();
		Charakter charakter = factory.createCharakter();
		charakter.setSonderfertigkeiten(factory.createSonderfertigkeiten());
		List<Talentspezialisierung> talentspezialisierungs = charakter.getSonderfertigkeiten()
																	  .getTalentspezialisierung();
		charakter.setTalente(factory.createTalente());
		Basistalent metalCraft = factory.createBasistalent();
		metalCraft.setName(BaseSkills.METALCRAFT.getName());
		metalCraft.setMerkmal(MerkmalProfan.HANDWERK);
		charakter.getTalente().getTalent().add(metalCraft);
		Talentspezialisierung blacksmith = factory.createTalentspezialisierung();
		blacksmith.setFertigkeit(BaseSkills.METALCRAFT.getName());
		blacksmith.setName("Grobschmied");
		talentspezialisierungs.add(blacksmith);

		Talentspezialisierung weaponsmith = factory.createTalentspezialisierung();
		weaponsmith.setFertigkeit(BaseSkills.METALCRAFT.getName());
		weaponsmith.setName("Waffenschmied");
		talentspezialisierungs.add(weaponsmith);



		//execute
		SkillSpecialisationTranslator translator = new SkillSpecialisationTranslator(charakter);
		List<ISpecialAbility> specialAbilities = translator.translate(charakter.getSonderfertigkeiten()
																			   .getTalentspezialisierung());

		//assert
		assertThat(specialAbilities, hasSize(2));
		int sum = specialAbilities.stream()
								  .mapToInt(ISpecialAbility::getCost)
								  .sum();
		assertThat(sum, is(9));
	}

	@Test
	void testSpecialisationSpecialSkill(){
		ObjectFactory factory = new ObjectFactory();
		Charakter charakter = factory.createCharakter();
		charakter.setSonderfertigkeiten(factory.createSonderfertigkeiten());
		List<Talentspezialisierung> talentspezialisierungs = charakter.getSonderfertigkeiten()
																	  .getTalentspezialisierung();
		charakter.setZauber(factory.createZauber());
		charakter.setRituale(factory.createRituale());
		charakter.setLiturgien(factory.createLiturgien());
		charakter.setZeremonien(factory.createZeremonien());

		Fertigkeit spell = factory.createFertigkeit();
		spell.setName("Pentagramma");
		spell.setKategorie(Fertigkeitskategorie.ZAUBER);
		spell.setSteigerungskosten(Steigerungskategorie.C);
		charakter.getZauber().getZauber().add(spell);
		Talentspezialisierung castingTime = factory.createTalentspezialisierung();
		castingTime.setFertigkeit("Pentagramma");
		castingTime.setName("Zauberdauer");
		talentspezialisierungs.add(castingTime);
		SkillSpecialisationTranslator translator = new SkillSpecialisationTranslator(charakter);

		//execute
		List<ISpecialAbility> specialAbilities = translator.translate(charakter.getSonderfertigkeiten()
																			   .getTalentspezialisierung());

		//assert
		assertThat(specialAbilities, hasSize(1));
		int sum = specialAbilities.stream()
								  .mapToInt(ISpecialAbility::getCost)
								  .sum();
		assertThat(sum, is(3));
	}



}
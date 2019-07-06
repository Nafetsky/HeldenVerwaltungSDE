package XsdWrapper;

import api.AbilityGroup;
import api.BaseAttribute;
import api.ISpecialAbility;
import api.Language;
import api.skills.ImprovementComplexity;
import api.skills.Skill;
import api.skills.SkillGroup;
import api.history.AttributeChange;
import api.Event;
import api.history.SkillChange;
import api.skills.MagicDescriptors;
import api.skills.TraditionDescriptors;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import generated.Attributskürzel;
import generated.Eigenschaftssteigerung;
import generated.Ereignis;
import generated.Fertigkeit;
import generated.Fertigkeitskategorie;
import generated.Fertigkeitsmodifikation;
import generated.Kampftechnik;
import generated.Nachteil;
import generated.ObjectFactory;
import generated.Schrift;
import generated.Sonderfertigkeit;
import generated.Sprache;
import generated.Steigerungskategorie;
import generated.Vorteil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

class EventParserTest {

	private static final String DATE_AS_TEXT = "201906101409";
	private EventParser parser = new EventParser();
	private ObjectFactory factory = new ObjectFactory();

	@Test
	void testParseAdvantages(){
		Ereignis ereignis = buildEmptyEreignis();
		Vorteil vorteil = factory.createVorteil();
		vorteil.setName("Just better");
		vorteil.setKosten(10);
		ereignis.getVorteil().add(vorteil);

		Event event = parser.parse(ereignis);

		assertThat(event.getAdvantages(), hasSize(1));
		assertThat(event.getAdvantages().get(0).getCost(), is(10));
	}

	@Test
	void testParseDisadvantages(){
		Ereignis ereignis = buildEmptyEreignis();
		Nachteil nachteil = factory.createNachteil();
		nachteil.setName("Just worse");
		nachteil.setKosten(10);
		ereignis.getNachteil().add(nachteil);

		Event event = parser.parse(ereignis);

		assertThat(event.getDisadvantages(), hasSize(1));
		assertThat(event.getDisadvantages().get(0).getCost(), is(10));
	}

	@Test
	void testParseAdventurePoints(){
		Ereignis ereignis = buildEmptyEreignis();
		ereignis.setAP(25);

		Event event = parser.parse(ereignis);

		assertThat(event.getAdventurePoints(), is(25));
	}

	@Test
	void testParseAttributeChange(){
		Ereignis ereignis = buildEmptyEreignis();
		List<Eigenschaftssteigerung> eigenschaftssteigerungs = ereignis.getEigenschaftssteigerung();
		Eigenschaftssteigerung eigenschaftssteigerung = factory.createEigenschaftssteigerung();
		eigenschaftssteigerung.setEigenschaft(Attributskürzel.KK);
		eigenschaftssteigerung.setNeuerWert(15);
		eigenschaftssteigerung.setSteigerung(1);
		eigenschaftssteigerungs.add(eigenschaftssteigerung);

		Event event = parser.parse(ereignis);

		List<AttributeChange> attributeChanges = event.getAttributeChanges();
		assertThat(attributeChanges, hasSize(1));

	}

	@Test
	void testParseDate(){
		Ereignis ereignis = buildEmptyEreignis();

		Event event = parser.parse(ereignis);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		TemporalAccessor expectation = LocalDateTime.parse(DATE_AS_TEXT, formatter);
		assertThat(event.getDate(), is(expectation));
	}

	@Test
	void testParseSkillChange(){
		Ereignis ereignis = buildEmptyEreignis();
		Fertigkeitsmodifikation skillChange = factory.createFertigkeitsmodifikation();
		skillChange.setName("Zechen");
		skillChange.setNeuerWert(10);
		skillChange.setÄnderung(1);
		ereignis.getFertigkeitsänderung().add(skillChange);

		Event event = parser.parse(ereignis);

		List<SkillChange> skillChanges = event.getSkillChanges();
		assertThat(skillChanges, hasSize(1));
		assertThat(skillChanges.get(0).getIncrease(), is(1));
		assertThat(skillChanges.get(0).getNewValue(), is(10));
	}

	@Test
	void testLearnedNewSkill(){
		Ereignis ereignis = buildEmptyEreignis();
		Fertigkeitsmodifikation skillChange = factory.createFertigkeitsmodifikation();
		String spellName = "Ignifaxius";
		skillChange.setName(spellName);
		skillChange.setNeuerWert(2);
		skillChange.setÄnderung(3);
		Fertigkeit skill = factory.createFertigkeit();
		skill.setName(spellName);
		skill.setSteigerungskosten(Steigerungskategorie.C);
		skill.setAttribut1(Attributskürzel.MU);
		skill.setAttribut2(Attributskürzel.KL);
		skill.setAttribut3(Attributskürzel.IN);
		skill.setKategorie(Fertigkeitskategorie.ZAUBER);
		List<String> merkmals = skill.getMerkmal();
		merkmals.add("REPRÄSENTATION_MAGIER");
		merkmals.add("Elementar");
		skillChange.setNeueFertigkeit(skill);
		ereignis.getFertigkeitsänderung().add(skillChange);


		Event event = parser.parse(ereignis);


		List<SkillChange> skillChanges = event.getSkillChanges();
		assertThat(skillChanges, hasSize(1));

		List<Skill> learnedSkills = event.getLearnedSkills();
		assertThat(learnedSkills, hasSize(1));
		Skill ignifaxius = learnedSkills.get(0);
		assertThat(ignifaxius.getName(), is(spellName));
		assertThat(ignifaxius.getGroup(), is(SkillGroup.Spell));
		assertThat(ignifaxius.getComplexity(), is(ImprovementComplexity.C));
		assertThat(ignifaxius.getAttributes().isPresent(), is(true));
		BaseAttribute[] baseAttributes = ignifaxius.getAttributes()
												   .get();
		assertThat(baseAttributes[0], is(BaseAttribute.Courage));
		assertThat(baseAttributes[1], is(BaseAttribute.Sagacity));
		assertThat(baseAttributes[2], is(BaseAttribute.Intuition));

		assertThat(Arrays.asList(ignifaxius.getDescriptors()), containsInAnyOrder(MagicDescriptors.Elemental, TraditionDescriptors.GUILD_MAGE));
	}


	@Test
	void testLearnSpecialAbility(){
		int cost = 15;
		String name = "Wuchtschlag I";

		Sonderfertigkeit sonderfertigkeit = factory.createSonderfertigkeit();
		sonderfertigkeit.setKategorie("Kampf");
		sonderfertigkeit.setKosten(cost);
		sonderfertigkeit.setName(name);
		Ereignis ereignis = buildEmptyEreignis();
		ereignis.getSonderfertigkeitshinzugewinn().add(sonderfertigkeit);

		Event event = parser.parse(ereignis);

		List<ISpecialAbility> learnedAbilities = event.getAbilities();
		assertThat(learnedAbilities, hasSize(1));
		ISpecialAbility specialAbility = learnedAbilities.get(0);
		assertThat(specialAbility.getCost(), is(cost));
		assertThat(specialAbility.getName(), is(name));
		assertThat(specialAbility.getGroup(), is(AbilityGroup.COMBAT));
	}

	@Test
	void testLearnLanguage(){
		Sprache sprache = factory.createSprache();
		String name = "Rogolan";
		sprache.setName(name);
		sprache.setStufe(1);
		Ereignis ereignis = buildEmptyEreignis();
		ereignis.setKommunikatives(factory.createKommunikatives());
		ereignis.getKommunikatives().getSprachen().add(sprache);

		Event event = parser.parse(ereignis);

		List<ISpecialAbility> learnedAbilities = event.getAbilities();
		assertThat(learnedAbilities, hasSize(1));
		ISpecialAbility specialAbility = learnedAbilities.get(0);
		assertThat(specialAbility, instanceOf(Language.class));
		assertThat(specialAbility.getName(), is(name));
		assertThat(specialAbility.getGroup(), is(AbilityGroup.MUNDANE));
		assertThat(((Language)specialAbility).getLevel(), is(1));
	}


	@Test
	void testLearnScripture() {
		Schrift schrift = factory.createSchrift();
		String name = "Rogolan Runen";
		schrift.setName(name);
		schrift.setKomplexität(Steigerungskategorie.B);
		Ereignis ereignis = buildEmptyEreignis();
		ereignis.setKommunikatives(factory.createKommunikatives());
		ereignis.getKommunikatives()
				.getSchriften()
				.add(schrift);

		Event event = parser.parse(ereignis);

		List<ISpecialAbility> learnedAbilities = event.getAbilities();
		assertThat(learnedAbilities, hasSize(1));
		ISpecialAbility specialAbility = learnedAbilities.get(0);
		assertThat(specialAbility.getName(), is(name));
		assertThat(specialAbility.getCost(), is(4));
		assertThat(specialAbility.getGroup(), is(AbilityGroup.MUNDANE));
	}


	@Test
	void testIncreaseCombat(){
		String name = "Hiebwaffen";
		Ereignis ereignis = buildEmptyEreignis();
		Fertigkeitsmodifikation learToFight = factory.createFertigkeitsmodifikation();
		learToFight.setName(name);
		learToFight.setNeuerWert(10);
		learToFight.setÄnderung(4);
		Kampftechnik kampftechnik = factory.createKampftechnik();
		kampftechnik.setKampftechnikwert(6);
		kampftechnik.setLeiteigenschaft(Attributskürzel.KK);
		kampftechnik.setBasistechnik(true);
		kampftechnik.setSteigerungskosten(Steigerungskategorie.C);
		kampftechnik.setName(name);
		learToFight.setNeueKampftechnik(kampftechnik);
		ereignis.getKampftechnikänderung().add(learToFight);

		Event event = parser.parse(ereignis);

		List<SkillChange> skillChanges = event.getSkillChanges();
		assertThat(skillChanges, hasSize(1));
		SkillChange skillChange = skillChanges.get(0);
		assertThat(skillChange.getName(), is(name));
		assertThat(skillChange.getIncrease(), is(4));
		assertThat(skillChange.getNewValue(), is(10));
	}

	private Ereignis buildEmptyEreignis() {
		Ereignis ereignis = factory.createEreignis();
		XMLGregorianCalendarImpl xmlDate = buildCalendar();
		ereignis.setDatum(xmlDate);
		return ereignis;
	}

	private XMLGregorianCalendarImpl buildCalendar() {
		XMLGregorianCalendarImpl xmlDate = new XMLGregorianCalendarImpl();
		xmlDate.setYear(2019);
		xmlDate.setMonth(6);
		xmlDate.setDay(10);
		xmlDate.setHour(14);
		xmlDate.setMinute(9);
		return xmlDate;
	}

}
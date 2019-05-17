package utils;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;

import org.apache.commons.lang3.StringUtils;

import database.BaseSkills;
import generated.Attributskürzel;
import generated.Charakter;
import generated.Fertigkeit;
import generated.Fertigkeitskategorie;
import generated.MerkmalMagie;
import generated.ObjectFactory;
import generated.Sonderfertigkeit;
import generated.Steigerungskategorie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharacterModifyerTest {

	private static final String SMALL_ANATHEMA = "Kleiner Bannstrahl";
	private static final String GARDIANUM = "Gardianum";
	Charakter barundar;
	JAXBContext jaxbContext;
	ObjectFactory factory;
	String barundarOrig;
	MarshallingHelper marshallHelper;

	@BeforeEach
	void init() throws Exception {
		factory = new ObjectFactory();
		TestPreparer preparer = new TestPreparer();
		marshallHelper = preparer.getMarshallingHelper();
		barundar = preparer.getBarundar();
		barundarOrig = preparer.getBarundarOrig();
	}

	@Test
	void testMarshalling() {
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertThat(barundarOrig, is(barundarAsXml.replaceAll("><", ">\r\n<")));
	}

	@Test
	void testIncreaseAbility() {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		modifier.increaseSkillByOne("Gewandheit");
		modifier.increaseSkillByOne("Gewandheit");
		modifier.saveChanges("test");
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertThat(barundarAsXml, containsString("Kürzel=\"FF\"/><Gewandheit Attributswert=\"16\" Kürzel=\"GE\"/><Konstitution"));
		String newEvent = makeCurrentEventHead()
				+ "</Datum><Grund>test</Grund><Eigenschaftssteigerung><Eigenschaft>GE</Eigenschaft><Steigerung>2</Steigerung><neuerWert>16</neuerWert></Eigenschaftssteigerung></Ereignis>";
		assertThat(barundarAsXml, containsString(newEvent));
	}

	@Test
	void testAddBaseSkill() throws Exception {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		modifier.increaseSkillByOne("Betören");
		modifier.saveChanges("test");
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertThat(barundarAsXml, containsString(
				"<Talent><Name>Betören</Name><Fertigkeitswert>1</Fertigkeitswert><Merkmal>Gesellschaft</Merkmal></Talent>"));
		String newEvent = makeCurrentEventHead()
				+ "</Datum><Grund>test</Grund><Fertigkeitsänderung><Name>Betören</Name><Änderung>1</Änderung><neuerWert>1</neuerWert></Fertigkeitsänderung></Ereignis>";
		assertThat(barundarAsXml, containsString(newEvent));
	}

	@Test
	void testIncreaseBaseSkill() throws Exception {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		modifier.increaseSkillByOne("Klettern");
		modifier.increaseSkillByOne("Klettern");
		modifier.saveChanges("test");
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertThat(barundarAsXml, containsString("<Talent><Name>Klettern</Name><Fertigkeitswert>9</Fertigkeitswert><Merkmal>Körper</Merkmal></Talent>"));
		String newEvent = makeCurrentEventHead()
				+ "</Datum><Grund>test</Grund><Fertigkeitsänderung><Name>Klettern</Name><Änderung>2</Änderung><neuerWert>9</neuerWert></Fertigkeitsänderung></Ereignis>";
		assertThat(barundarAsXml, containsString(newEvent));
	}

	@Test
	void testAddLiturgy() {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		Fertigkeit fertigkeit = makeSmallAnathema();
		modifier.addSkill(fertigkeit);
		modifier.increaseSkillByOne(SMALL_ANATHEMA);
		modifier.saveChanges("test");
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertThat(barundarAsXml, containsString(
				"<Liturgien><Liturgie><Name>Kleiner Bannstrahl</Name><Attribut1>MU</Attribut1><Attribut2>IN</Attribut2>"
						+ "<Attribut3>CH</Attribut3><Fertigkeitswert>1</Fertigkeitswert><Steigerungskosten>B</Steigerungskosten><Kategorie>Liturgie</Kategorie><Merkmal>TRADITION_Praios</Merkmal><Merkmal>ASPEKT_Antimagie</Merkmal></Liturgie></Liturgien>"));
		String newEvent = makeCurrentEventHead()
				+ "</Datum><Grund>test</Grund><Fertigkeitsänderung><Name>Kleiner Bannstrahl</Name><Änderung>2</Änderung><neuerWert>1</neuerWert>"
				+ "<NeueFertigkeit><Name>Kleiner Bannstrahl</Name><Attribut1>MU</Attribut1><Attribut2>IN</Attribut2><Attribut3>CH</Attribut3>"
				+ "<Fertigkeitswert>1</Fertigkeitswert><Steigerungskosten>B</Steigerungskosten><Kategorie>Liturgie</Kategorie><Merkmal>TRADITION_Praios</Merkmal>"
				+ "<Merkmal>ASPEKT_Antimagie</Merkmal></NeueFertigkeit></Fertigkeitsänderung></Ereignis>";
		assertThat(barundarAsXml, containsString(newEvent));
		assertThat(modifier.getCostForSkillIncreasment(SMALL_ANATHEMA), is(2));
	}

	@Test
	void testAddSpell() {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		Fertigkeit fertigkeit = makeGardianum();
		modifier.addSkill(fertigkeit);
		for (int i = 0; i < 2; ++i) {
			modifier.increaseSkillByOne(GARDIANUM);
		}
		modifier.saveChanges("test");
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertThat(barundarAsXml, containsString("<Zauber><Zauber><Name>" + GARDIANUM
				+ "</Name><Attribut1>MU</Attribut1><Attribut2>KL</Attribut2>"
				+ "<Attribut3>CH</Attribut3><Fertigkeitswert>2</Fertigkeitswert><Steigerungskosten>B</Steigerungskosten><Kategorie>Zauber</Kategorie><Merkmal>REP�SENTATION_MAGIER</Merkmal><Merkmal>Antimagie</Merkmal></Zauber></Zauber>"));
		String newEvent = makeCurrentEventHead() + "</Datum><Grund>test</Grund><Fertigkeitsänderung><Name>" + GARDIANUM
				+ "</Name><Änderung>3</Änderung><neuerWert>2</neuerWert>" + "<NeueFertigkeit><Name>" + GARDIANUM
				+ "</Name><Attribut1>MU</Attribut1><Attribut2>KL</Attribut2><Attribut3>CH</Attribut3>"
				+ "<Fertigkeitswert>2</Fertigkeitswert><Steigerungskosten>B</Steigerungskosten><Kategorie>Zauber</Kategorie><Merkmal>REP�SENTATION_MAGIER</Merkmal>"
				+ "<Merkmal>Antimagie</Merkmal></NeueFertigkeit></Fertigkeitsänderung></Ereignis>";
		assertThat(barundarAsXml, containsString(newEvent));
		assertThat(modifier.getCostForSkillIncreasment(GARDIANUM), is(2));
	}

	@Test
	void testIncreaseCombatSkill() throws Exception {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		modifier.increaseSkillByOne("Schilde");
		modifier.increaseSkillByOne("Schilde");
		modifier.increaseSkillByOne("Hiebwaffen");
		modifier.saveChanges("test");
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertThat(barundarAsXml,containsString(
				"<Kampftechnik><Name>Schilde</Name><Kampftechnikwert>18</Kampftechnikwert><Leiteigenschaft>KK</Leiteigenschaft><Steigerungskosten>C</Steigerungskosten></Kampftechnik>"));
		String newEvent = makeCurrentEventHead() + "</Datum><Grund>test</Grund>"
				+ "<Kampftechnikänderung><Name>Schilde</Name><Änderung>2</Änderung><neuerWert>18</neuerWert></Kampftechnikänderung>"
				+ "<Kampftechnikänderung><Name>Hiebwaffen</Name><Änderung>1</Änderung><neuerWert>15</neuerWert></Kampftechnikänderung>"
				+ "</Ereignis>";
		assertThat(barundarAsXml, containsString(newEvent));
	}

	@Test
	void testAddAdvantage() {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		modifier.addAdvantage("Geweihter", 25);
		modifier.saveChanges("test");
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertThat(barundarAsXml, containsString(
				"<Vorteil><Name>Geweihter</Name><Kosten>25</Kosten></Vorteil></Vorteile>"));
		String newEvent = makeCurrentEventHead() + "</Datum><Grund>test</Grund>"
				+ "<Vorteil><Name>Geweihter</Name><Kosten>25</Kosten></Vorteil></Ereignis>";
		assertThat(barundarAsXml, containsString(newEvent));
	}

	@Test
	void testRemoveAdvantage() {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		modifier.removeAdvantage("Dunkelsicht II");
		modifier.saveChanges("test");
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertThat(barundarAsXml,not(containsString(
				"<Vorteil><Name>Dunkelsicht II</Name><Kosten>20</Kosten></Vorteil>")));
		String newEvent = makeCurrentEventHead() + "</Datum><Grund>test</Grund>"
				+ "<Vorteil><Name>Dunkelsicht II</Name><Kosten>-20</Kosten></Vorteil></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}

	@Test
	void testAddDisadvantage() {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		modifier.addDisadvantage("Schwacher Karmalkörper", 15);
		modifier.saveChanges("test");
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml,
				"<Nachteil><Name>Schwacher Karmalkörper</Name><Kosten>15</Kosten></Nachteil></Nachteile>"));
		String newEvent = makeCurrentEventHead() + "</Datum><Grund>test</Grund>"
				+ "<Nachteil><Name>Schwacher Karmalkörper</Name><Kosten>15</Kosten></Nachteil>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}

	@Test
	void testRemoveDisadvantage() {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		modifier.removeDisadvantage("Aberglaube (Schlechte Eigenschaft)");
		modifier.saveChanges("test");
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertThat(barundarAsXml, not(containsString(
				"<Nachteil><Name>Aberglaube (Schlechte Eigenschaft)</Name><Kosten>5</Kosten></Nachteil>")));
		String newEvent = makeCurrentEventHead() + "</Datum>"
				+ "<Grund>test</Grund>"
				+ "<Nachteil><Name>Aberglaube (Schlechte Eigenschaft)</Name><Kosten>-5</Kosten></Nachteil></Ereignis>";
		assertThat(barundarAsXml, containsString(newEvent));
	}

	@Test
	void testGetCostForSkillIncreasment() {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		assertThat(modifier.getCostForSkillIncreasment("Schilde"), is(18));
	}

	@Test
	void testAddAP() {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		modifier.changeApBy(100);
		modifier.saveChanges("test");
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml, "</Nachteile><AP>2352</AP>"));
		assertTrue(StringUtils.contains(barundarAsXml,
				makeCurrentEventHead() + "</Datum><Grund>test</Grund><AP>100</AP><neueAP>2352</neueAP></Ereignis>"));
	}

	@Test
	void testBuyLife() {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		for (int i = 0; i < 6; ++i) {
			modifier.buyLifePoint();
		}
		modifier.saveChanges("test");
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml, "</AP><LeP>6</LeP>"));
		String newEvent = makeCurrentEventHead() + "</Datum><Grund>test</Grund><LePGekauft>6</LePGekauft></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}

	@Test
	void testBuyAsP() {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		modifier.buyAstralPoint();
		modifier.saveChanges("test");
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml, "</AP><AsP>1</AsP>"));
		String newEvent = makeCurrentEventHead() + "</Datum><Grund>test</Grund><AsPGekauft>1</AsPGekauft></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}

	@Test
	void testBuyKaP() {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		modifier.buyKarmaPoint();
		modifier.buyKarmaPoint();
		modifier.saveChanges("test");
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml, "</AP><KaP>2</KaP>"));
		String newEvent = makeCurrentEventHead() + "</Datum><Grund>test</Grund><KaPGekauft>2</KaPGekauft></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}

	@Test
	void testAddFeat() {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		Sonderfertigkeit newFeat = factory.createSonderfertigkeit();
		newFeat.setName("Wuchtschlag III");
		newFeat.setKosten(25);
		newFeat.setKategorie("Kampf");
		modifier.addFeat(newFeat);
		modifier.saveChanges("test");
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml,
				"<Sonderfertigkeit><Name>Wuchtschlag III</Name><Kosten>25</Kosten><Kategorie>Kampf</Kategorie></Sonderfertigkeit><Talentspezialisierung>"));
		String newEvent = makeCurrentEventHead()
				+ "</Datum><Grund>test</Grund><Sonderfertigkeitshinzugewinn><Name>Wuchtschlag III</Name><Kosten>25</Kosten><Kategorie>Kampf</Kategorie></Sonderfertigkeitshinzugewinn></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}
	
	@Test
	void testAddSkillSpecialisation() {
		CharacterModifier modifier = new CharacterModifier(WrappedCharakter.getWrappedCharakter(barundar));
		modifier.addSkillSpecialisation("Maurer", BaseSkills.STONECRAFT.getName());
		modifier.saveChanges("test");
		String barundarAsXml = marshallHelper.marshall(barundar);
		System.out.println(barundarAsXml);
	}

	private String makeCurrentEventHead() {
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddXXX");
		return "<Ereignis><Datum>" + format.format(now);
	}

	private Fertigkeit makeSmallAnathema() {
		Fertigkeit fertigkeit = factory.createFertigkeit();
		fertigkeit.setAttribut1(Attributskürzel.MU);
		fertigkeit.setAttribut2(Attributskürzel.IN);
		fertigkeit.setAttribut3(Attributskürzel.CH);
		fertigkeit.setFertigkeitswert(0);
		fertigkeit.setSteigerungskosten(Steigerungskategorie.B);
		fertigkeit.setName(SMALL_ANATHEMA);
		fertigkeit.setKategorie(Fertigkeitskategorie.LITURGIE);
		fertigkeit.getMerkmal().add("TRADITION_Praios");
		fertigkeit.getMerkmal().add("ASPEKT_Antimagie");
		return fertigkeit;
	}

	private Fertigkeit makeGardianum() {
		Fertigkeit fertigkeit = factory.createFertigkeit();
		fertigkeit.setAttribut1(Attributskürzel.MU);
		fertigkeit.setAttribut2(Attributskürzel.KL);
		fertigkeit.setAttribut3(Attributskürzel.CH);
		fertigkeit.setFertigkeitswert(0);
		fertigkeit.setSteigerungskosten(Steigerungskategorie.B);
		fertigkeit.setName(GARDIANUM);
		fertigkeit.setKategorie(Fertigkeitskategorie.ZAUBER);
		fertigkeit.getMerkmal().add("REP�SENTATION_MAGIER");
		fertigkeit.getMerkmal().add(MerkmalMagie.ANTIMAGIE.value());
		return fertigkeit;
	}
}

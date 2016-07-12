package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import generated.Attributsk�rzel;
import generated.Charakter;
import generated.Fertigkeit;
import generated.Fertigkeitskategorie;
import generated.ObjectFactory;
import generated.Sonderfertigkeit;
import generated.Steigerungskategorie;

public class CharacterModifyerTest {

	private static final String SMALL_ANATHEMA = "Kleiner Bannstrahl";
	Charakter barundar;
	JAXBContext jaxbContext;
	ObjectFactory factory;
	String barundarOrig;
	MarshallingHelper marshallHelper;

	@Before
	public void init() throws Exception {
		factory = new ObjectFactory();
		TestPreparer preparer = new TestPreparer();
		marshallHelper = preparer.getMarshallingHelper();
		barundar = preparer.getBarundar();
		barundarOrig = preparer.getBarundarOrig();
	}

	@Test
	public void testMarhsalling() throws JAXBException, Exception {
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertEquals(barundarOrig, barundarAsXml.replaceAll("><", ">\r\n<"));
	}

	@Test
	public void testAddBaseSkill() throws Exception {
		CharacterModifier modifier = new CharacterModifier(barundar);
		modifier.increaseSkillByOne("Bet�ren");
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml,
				"<Talent><Name>Bet�ren</Name><Fertigkeitswert>1</Fertigkeitswert><Merkmal>Gesellschaft</Merkmal></Talent>"));
		String newEvent = makeCurrentEventHead()
				+ "</Datum><Fertigkeits�nderung><Name>Bet�ren</Name><�nderung>1</�nderung><neuerWert>1</neuerWert></Fertigkeits�nderung></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}

	@Test
	public void testIncreaseBaseSkill() throws Exception {
		CharacterModifier modifier = new CharacterModifier(barundar);
		modifier.increaseSkillByOne("Klettern");
		modifier.increaseSkillByOne("Klettern");
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml,
				"<Talent><Name>Klettern</Name><Fertigkeitswert>9</Fertigkeitswert><Merkmal>K�rper</Merkmal></Talent>"));
		String newEvent = makeCurrentEventHead()
				+ "</Datum><Fertigkeits�nderung><Name>Klettern</Name><�nderung>2</�nderung><neuerWert>9</neuerWert></Fertigkeits�nderung></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}

	@Test
	public void testAddLiturgy() {
		CharacterModifier modifier = new CharacterModifier(barundar);
		Fertigkeit fertigkeit = makeSmallAnathema();
		modifier.addSkill(fertigkeit);
		modifier.increaseSkillByOne(SMALL_ANATHEMA);
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml,
				"<Liturgien><Liturgie><Name>Kleiner Bannstrahl</Name><Attribut1>MU</Attribut1><Attribut2>IN</Attribut2>"
						+ "<Attribut3>CH</Attribut3><Fertigkeitswert>1</Fertigkeitswert><Steigerungskosten>B</Steigerungskosten><Kategorie>Liturgie</Kategorie><Merkmal>TRADITION_Praios</Merkmal><Merkmal>ASPEKT_Antimagie</Merkmal></Liturgie></Liturgien>"));
		String newEvent = makeCurrentEventHead()
				+ "</Datum><Fertigkeits�nderung><Name>Kleiner Bannstrahl</Name><�nderung>2</�nderung><neuerWert>1</neuerWert>"
				+ "<NeueFertigkeit><Name>Kleiner Bannstrahl</Name><Attribut1>MU</Attribut1><Attribut2>IN</Attribut2><Attribut3>CH</Attribut3>"
				+ "<Fertigkeitswert>1</Fertigkeitswert><Steigerungskosten>B</Steigerungskosten><Kategorie>Liturgie</Kategorie><Merkmal>TRADITION_Praios</Merkmal>"
				+ "<Merkmal>ASPEKT_Antimagie</Merkmal></NeueFertigkeit></Fertigkeits�nderung></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
		assertEquals(2, modifier.getCostForSkillIncreasment(SMALL_ANATHEMA));

	}

	@Test
	public void testIncreaseCombatSkill() throws Exception {
		CharacterModifier modifier = new CharacterModifier(barundar);
		modifier.increaseSkillByOne("Schilde");
		modifier.increaseSkillByOne("Schilde");
		modifier.increaseSkillByOne("Hiebwaffen");
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml,
				"<Kampftechnik><Name>Schilde</Name><Kampftechnikwert>18</Kampftechnikwert><Leiteigenschaft>KK</Leiteigenschaft><Steigerungskosten>C</Steigerungskosten></Kampftechnik>"));
		String newEvent = makeCurrentEventHead() + "</Datum>"
				+ "<Kampftechnik�nderung><Name>Schilde</Name><�nderung>2</�nderung><neuerWert>18</neuerWert></Kampftechnik�nderung>"
				+ "<Kampftechnik�nderung><Name>Hiebwaffen</Name><�nderung>1</�nderung><neuerWert>15</neuerWert></Kampftechnik�nderung>"
				+ "</Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}

	@Test
	public void testAddAdvantage() {
		CharacterModifier modifier = new CharacterModifier(barundar);
		modifier.addAdvantage("Geweihter", 25);
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml,
				"<Vorteil><Name>Geweihter</Name><Kosten>25</Kosten></Vorteil></Vorteile>"));
		String newEvent = makeCurrentEventHead() + "</Datum>"
				+ "<Vorteil><Name>Geweihter</Name><Kosten>25</Kosten></Vorteil></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}

	@Test
	public void testAddDisadvantage() {
		CharacterModifier modifier = new CharacterModifier(barundar);
		modifier.addDisadvantage("Schwacher Karmalk�rper", 15);
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml,
				"<Nachteil><Name>Schwacher Karmalk�rper</Name><Kosten>15</Kosten></Nachteil></Nachteile>"));
		String newEvent = makeCurrentEventHead() + "</Datum>"
				+ "<Nachteil><Name>Schwacher Karmalk�rper</Name><Kosten>15</Kosten></Nachteil>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}

	@Test
	public void testGetCostForSkillIncreasment() {
		CharacterModifier modifier = new CharacterModifier(barundar);
		assertEquals(18, modifier.getCostForSkillIncreasment("Schilde"));
	}

	@Test
	public void testAddAP() {
		CharacterModifier modifier = new CharacterModifier(barundar);
		modifier.changeApBy(100);
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml, "</Nachteile><AP>2352</AP>"));
		assertTrue(StringUtils.contains(barundarAsXml,
				makeCurrentEventHead() + "</Datum><AP>100</AP><neueAP>2352</neueAP></Ereignis>"));
	}

	@Test
	public void testAddFeat() {
		CharacterModifier modifier = new CharacterModifier(barundar);
		Sonderfertigkeit newFeat = factory.createSonderfertigkeit();
		newFeat.setName("Wuchtschlag III");
		newFeat.setKosten(25);
		newFeat.setKategorie("Kampf");
		modifier.addFeat(newFeat);
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml,
				"<Sonderfertigkeit><Name>Wuchtschlag III</Name><Kosten>25</Kosten><Kategorie>Kampf</Kategorie></Sonderfertigkeit><Talentspezialisierung>"));
		String newEvent = makeCurrentEventHead()
				+ "</Datum><Sonderfertigkeitshinzugewinn><Name>Wuchtschlag III</Name><Kosten>25</Kosten><Kategorie>Kampf</Kategorie></Sonderfertigkeitshinzugewinn></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}

	private String makeCurrentEventHead() {
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddXXX");
		return "<Ereignis><Datum>" + format.format(now);
	}

	private Fertigkeit makeSmallAnathema() {
		Fertigkeit fertigkeit = factory.createFertigkeit();
		fertigkeit.setAttribut1(Attributsk�rzel.MU);
		fertigkeit.setAttribut2(Attributsk�rzel.IN);
		fertigkeit.setAttribut3(Attributsk�rzel.CH);
		fertigkeit.setFertigkeitswert(0);
		fertigkeit.setSteigerungskosten(Steigerungskategorie.B);
		fertigkeit.setName(SMALL_ANATHEMA);
		fertigkeit.setKategorie(Fertigkeitskategorie.LITURGIE);
		fertigkeit.getMerkmal().add("TRADITION_Praios");
		fertigkeit.getMerkmal().add("ASPEKT_Antimagie");
		return fertigkeit;
	}
}

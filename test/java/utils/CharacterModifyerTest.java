package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import generated.Attributskürzel;
import generated.Charakter;
import generated.Fertigkeit;
import generated.Fertigkeitskategorie;
import generated.MerkmalMagie;
import generated.ObjectFactory;
import generated.Sonderfertigkeit;
import generated.Steigerungskategorie;

public class CharacterModifyerTest {

	private static final String SMALL_ANATHEMA = "Kleiner Bannstrahl";
	private static final String GARDIANUM = "Gardianum";
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
	public void testIncreaseAbility() throws Exception {
		CharacterModifier modifier = new CharacterModifier(barundar);
		modifier.increaseSkillByOne("Gewandheit");
		modifier.increaseSkillByOne("Gewandheit");
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml,
				"Kürzel=\"FF\"/><Gewandheit Attributswert=\"16\" Kürzel=\"GE\"/><Konstitution"));
		String newEvent = makeCurrentEventHead()
				+ "</Datum><Eigenschaftssteigerung><Eigenschaft>GE</Eigenschaft><Steigerung>2</Steigerung><neuerWert>16</neuerWert></Eigenschaftssteigerung></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}

	@Test
	public void testAddBaseSkill() throws Exception {
		CharacterModifier modifier = new CharacterModifier(barundar);
		modifier.increaseSkillByOne("Betören");
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml,
				"<Talent><Name>Betören</Name><Fertigkeitswert>1</Fertigkeitswert><Merkmal>Gesellschaft</Merkmal></Talent>"));
		String newEvent = makeCurrentEventHead()
				+ "</Datum><Fertigkeitsänderung><Name>Betören</Name><Änderung>1</Änderung><neuerWert>1</neuerWert></Fertigkeitsänderung></Ereignis>";
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
				"<Talent><Name>Klettern</Name><Fertigkeitswert>9</Fertigkeitswert><Merkmal>Körper</Merkmal></Talent>"));
		String newEvent = makeCurrentEventHead()
				+ "</Datum><Fertigkeitsänderung><Name>Klettern</Name><Änderung>2</Änderung><neuerWert>9</neuerWert></Fertigkeitsänderung></Ereignis>";
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
				+ "</Datum><Fertigkeitsänderung><Name>Kleiner Bannstrahl</Name><Änderung>2</Änderung><neuerWert>1</neuerWert>"
				+ "<NeueFertigkeit><Name>Kleiner Bannstrahl</Name><Attribut1>MU</Attribut1><Attribut2>IN</Attribut2><Attribut3>CH</Attribut3>"
				+ "<Fertigkeitswert>1</Fertigkeitswert><Steigerungskosten>B</Steigerungskosten><Kategorie>Liturgie</Kategorie><Merkmal>TRADITION_Praios</Merkmal>"
				+ "<Merkmal>ASPEKT_Antimagie</Merkmal></NeueFertigkeit></Fertigkeitsänderung></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
		assertEquals(2, modifier.getCostForSkillIncreasment(SMALL_ANATHEMA));
	}

	@Test
	public void testAddSpell() {
		CharacterModifier modifier = new CharacterModifier(barundar);
		Fertigkeit fertigkeit = makeGardianum();
		modifier.addSkill(fertigkeit);
		for (int i = 0; i < 2; ++i) {
			modifier.increaseSkillByOne(GARDIANUM);
		}
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml, "<Zauber><Zauber><Name>" + GARDIANUM
				+ "</Name><Attribut1>MU</Attribut1><Attribut2>KL</Attribut2>"
				+ "<Attribut3>CH</Attribut3><Fertigkeitswert>2</Fertigkeitswert><Steigerungskosten>B</Steigerungskosten><Kategorie>Zauber</Kategorie><Merkmal>REPÄSENTATION_MAGIER</Merkmal><Merkmal>Antimagie</Merkmal></Zauber></Zauber>"));
		String newEvent = makeCurrentEventHead() + "</Datum><Fertigkeitsänderung><Name>" + GARDIANUM
				+ "</Name><Änderung>3</Änderung><neuerWert>2</neuerWert>" + "<NeueFertigkeit><Name>" + GARDIANUM
				+ "</Name><Attribut1>MU</Attribut1><Attribut2>KL</Attribut2><Attribut3>CH</Attribut3>"
				+ "<Fertigkeitswert>2</Fertigkeitswert><Steigerungskosten>B</Steigerungskosten><Kategorie>Zauber</Kategorie><Merkmal>REPÄSENTATION_MAGIER</Merkmal>"
				+ "<Merkmal>Antimagie</Merkmal></NeueFertigkeit></Fertigkeitsänderung></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
		assertEquals(2, modifier.getCostForSkillIncreasment(GARDIANUM));
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
				+ "<Kampftechnikänderung><Name>Schilde</Name><Änderung>2</Änderung><neuerWert>18</neuerWert></Kampftechnikänderung>"
				+ "<Kampftechnikänderung><Name>Hiebwaffen</Name><Änderung>1</Änderung><neuerWert>15</neuerWert></Kampftechnikänderung>"
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
	public void testRemoveAdvantage() {
		CharacterModifier modifier = new CharacterModifier(barundar);
		modifier.removeAdvantage("Dunkelsicht II");
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertFalse(StringUtils.contains(barundarAsXml,
				"<Vorteil><Name>Dunkelsicht II</Name><Kosten>20</Kosten></Vorteil>"));
		String newEvent = makeCurrentEventHead() + "</Datum>"
				+ "<Vorteil><Name>Dunkelsicht II</Name><Kosten>-20</Kosten></Vorteil></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}

	@Test
	public void testAddDisadvantage() {
		CharacterModifier modifier = new CharacterModifier(barundar);
		modifier.addDisadvantage("Schwacher Karmalkörper", 15);
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml,
				"<Nachteil><Name>Schwacher Karmalkörper</Name><Kosten>15</Kosten></Nachteil></Nachteile>"));
		String newEvent = makeCurrentEventHead() + "</Datum>"
				+ "<Nachteil><Name>Schwacher Karmalkörper</Name><Kosten>15</Kosten></Nachteil>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}

	@Test
	public void testRemoveDisadvantage() {
		CharacterModifier modifier = new CharacterModifier(barundar);
		modifier.removeDisadvantage("Aberglaube (Schlechte Eigenschaft)");
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertFalse(StringUtils.contains(barundarAsXml,
				"<Nachteil><Name>Aberglaube (Schlechte Eigenschaft)</Name><Kosten>5</Kosten></Nachteil>"));
		String newEvent = makeCurrentEventHead() + "</Datum>"
				+ "<Nachteil><Name>Aberglaube (Schlechte Eigenschaft)</Name><Kosten>-5</Kosten></Nachteil></Ereignis>";
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
	public void testBuyLife() {
		CharacterModifier modifier = new CharacterModifier(barundar);
		for (int i = 0; i < 6; ++i) {
			modifier.buyLifePoint();
		}
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml, "</AP><LeP>6</LeP>"));
		String newEvent = makeCurrentEventHead() + "</Datum><LePGekauft>6</LePGekauft></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}

	@Test
	public void testBuyAsP() {
		CharacterModifier modifier = new CharacterModifier(barundar);
		modifier.buyAstralPoint();
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml, "</AP><AsP>1</AsP>"));
		String newEvent = makeCurrentEventHead() + "</Datum><AsPGekauft>1</AsPGekauft></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
	}

	@Test
	public void testBuyKaP() {
		CharacterModifier modifier = new CharacterModifier(barundar);
		modifier.buyKarmaPoint();
		modifier.buyKarmaPoint();
		modifier.saveChanges();
		String barundarAsXml = marshallHelper.marshall(barundar);
		assertTrue(StringUtils.contains(barundarAsXml, "</AP><KaP>2</KaP>"));
		String newEvent = makeCurrentEventHead() + "</Datum><KaPGekauft>2</KaPGekauft></Ereignis>";
		assertTrue(StringUtils.contains(barundarAsXml, newEvent));
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
		fertigkeit.getMerkmal().add("REPÄSENTATION_MAGIER");
		fertigkeit.getMerkmal().add(MerkmalMagie.ANTIMAGIE.value());
		return fertigkeit;
	}
}

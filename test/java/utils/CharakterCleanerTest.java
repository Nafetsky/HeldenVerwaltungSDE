package utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import api.BaseSkills;
import generated.Basistalent;
import generated.Charakter;
import generated.Talente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharakterCleanerTest {
	
	WrappedCharakter wrappedCharakter;
	
	@BeforeEach
	void init(){
		Charakter charakter = new Charakter();
		Talente talente = new Talente();
		charakter.setTalente(talente);
		Basistalent carouse = new Basistalent();
		carouse.setName(BaseSkills.CAROUSE.getName());
		carouse.setMerkmal(BaseSkills.CAROUSE.getMerkmal());
		talente.getTalent().add(carouse);
		
		Basistalent bodyControl = new Basistalent();
		bodyControl.setName(BaseSkills.BODY_CONTROL.getName());
		bodyControl.setMerkmal(BaseSkills.BODY_CONTROL.getMerkmal());
		talente.getTalent().add(bodyControl);
		
		
		Basistalent cozen = new Basistalent();
		cozen.setName(BaseSkills.COZEN.getName());
		cozen.setMerkmal(BaseSkills.COZEN.getMerkmal());
		talente.getTalent().add(cozen);
		
		wrappedCharakter = WrappedCharakter.getWrappedCharakter(charakter);
	}
	
	@Test
	void test(){
		CharakterCleaner cleaner = new CharakterCleaner(wrappedCharakter);
		cleaner.cleanUpCharakter();
		List<Basistalent> talent = wrappedCharakter.getTalente().getTalent();
		assertThat(talent.get(0).getName(), is(BaseSkills.BODY_CONTROL.getName()));
		assertThat(talent.get(1).getName(), is(BaseSkills.CAROUSE.getName()));
		assertThat(talent.get(2).getName(), is(BaseSkills.COZEN.getName()));
	}

}

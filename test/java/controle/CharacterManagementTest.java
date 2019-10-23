package controle;

import api.base.Character;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.TestPreparer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

class CharacterManagementTest {

	private Path tempDirectory;
	private TestPreparer testPreparer = new TestPreparer();


	@BeforeEach
	void setUp() throws IOException {
		tempDirectory = Files.createTempDirectory(CharacterManagement.class.getName());
	}


	@Test
	void testEmptyDirectory(){
		CharacterManagement management = new CharacterManagement(tempDirectory);

		List<Character> characters = management.getCharacters();

		assertThat(characters, hasSize(0));
	}

	@Test
	void testOneInDirectory() throws IOException {
		String barundarAsText = testPreparer.getBarundarAsText();
		OutputStream output = new FileOutputStream(tempDirectory.resolve("barundar.xml").toFile());
		IOUtils.write(barundarAsText, output, StandardCharsets.UTF_8);
		IOUtils.closeQuietly(output);
		CharacterManagement management = new CharacterManagement(tempDirectory);

		List<Character> characters = management.getCharacters();

		assertThat(characters, hasSize(1));
	}

	@AfterEach
	void cleanUp() throws IOException {
		FileUtils.deleteDirectory(tempDirectory.toFile());
	}

}
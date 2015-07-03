package org.brunocvcunha.phantranslator;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.brunocvcunha.phantranslator.testrules.Repeat;
import org.brunocvcunha.phantranslator.testrules.RepeatRule;
import org.junit.Rule;
import org.junit.Test;

public class PhantranslatorTest {

	@Rule
	public RepeatRule repeatRule = new RepeatRule();

	@Test
	@Repeat(times = 3)
	public void simpleHealthCheck() throws IOException {
		PhantranslatorExecutor executor = new PhantranslatorExecutor("pt", "en");
		assertEquals("tests", executor.getTranslation("testes").toLowerCase());
		assertEquals("useful library",
				executor.getTranslation("biblioteca útil").toLowerCase());
		executor.destroy();
	}

}

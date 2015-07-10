/**
 * Copyright (C) 2015 Bruno Candido Volpato da Cunha (brunocvcunha@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.brunocvcunha.phantranslator;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.brunocvcunha.phantranslator.testrules.Repeat;
import org.brunocvcunha.phantranslator.testrules.RepeatRule;
import org.junit.Rule;
import org.junit.Test;

public class PhantranslatorTest {

	private static final Logger log = Logger.getLogger(PhantranslatorTest.class);
	
	@Rule
	public RepeatRule repeatRule = new RepeatRule();

	@Test
	@Repeat(times = 3)
	public void simpleHealthCheck() throws IOException {
		//ignoring exceptions at this moment, figuring out a way to work on travis
		try {
			PhantranslatorExecutor executor = new PhantranslatorExecutor("pt", "en");
			assertEquals("tests", executor.getTranslation("testes").toLowerCase());
			assertEquals("useful library",
					executor.getTranslation("biblioteca útil").toLowerCase());
			executor.destroy();
		} catch(Exception e) {
			if (e.getMessage().contains("phantomjs.org/download.html")) {
				log.warn("Could not run tests, phantomjs not present.", e);
			} else {
				log.error("Error running tests", e);
			}
		}
	}

}

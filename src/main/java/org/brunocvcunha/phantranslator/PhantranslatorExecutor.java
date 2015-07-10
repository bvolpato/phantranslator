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

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

/**
 * PhantomJS Google Translator Client
 * 
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class PhantranslatorExecutor {

	private static final Logger log = Logger
			.getLogger(PhantranslatorExecutor.class);

	private WebDriver _driver;

	private WebDriverBackedSelenium _selenium;

	private String _from;

	private String _to;

	private GoogleTranslatorPage _page;

	private int maxTries = 10;

	private long intervalBetweenTries = 1000L;

	private String spoofUserAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36";

	/**
	 * Creates new translator instance.
	 * 
	 * @param from
	 *            Language to translate from
	 * @param to
	 *            Language to return the literals
	 * @see <a
	 *      href="https://cloud.google.com/translate/v2/using_rest#language-params">https://cloud.google.com/translate/v2/using_rest#language-params</a>
	 *      to check possible parameter values
	 */
	public PhantranslatorExecutor(String from, String to) {
		this._from = from;
		this._to = to;
	}

	/**
	 * Puts the literal on the source field, wait for translation and return the
	 * translated result
	 * 
	 * @param literal Literal in the source language to translate
	 * @return Translated text
	 */
	public String getTranslation(String literal) {
		log.debug("Translating " + literal);

		if (literal.trim().matches("\\d+")) {
			log.debug("Number literal: " + literal);
			return literal;
		}

		log.debug("Not a number: " + literal);

		getTranslationPage().insertLiteral(literal);
		getWebDriverBackedSelenium().waitForPageToLoad("10000");

		String result = null;

		int triesCount = 0;
		while (result == null || result.equals("") || result.endsWith("...")) {
			if (triesCount > 0) {
				try {
					Thread.sleep(intervalBetweenTries);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (triesCount++ > maxTries) {
				throw new RuntimeException(
						"Failed to fetch translation for literal '" + literal
								+ "'");
			}

			log.debug("Trying to get translation: " + literal);

			result = getWebDriverBackedSelenium().getText(
					"//span[@id='result_box']");

		}

		log.info("Result: " + result);

		return result;

	}

	protected void setDriver(WebDriver driver) {
		this._driver = driver;
	}

	protected GoogleTranslatorPage getTranslationPage() {
		if (this._page == null) {
			getWebDriverBackedSelenium();

			this._page = PageFactory.initElements(getDriver(),
					GoogleTranslatorPage.class);
			this._selenium.waitForPageToLoad("10000");
		}
		return this._page;
	}

	protected boolean hasDriver() {
		return this._driver != null;
	}

	protected WebDriver getDriver() {
		if (this._driver == null) {
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setJavascriptEnabled(true);
			caps.setCapability(
					PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX
							+ "userAgent", spoofUserAgent);

			caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
					new String[] { "--web-security=false",
							"--ssl-protocol=any", "--ignore-ssl-errors=true",
							"--webdriver-loglevel=INFO" });

			this._driver = new PhantomJSDriver(caps);
		}

		return this._driver;
	}

	protected WebDriverBackedSelenium getWebDriverBackedSelenium() {
		if (this._selenium == null) {
			this._selenium = new WebDriverBackedSelenium(getDriver(),
					"https://translate.google.com/");
			this._selenium.open("/#" + _from + "/" + _to + "/");
		}

		return this._selenium;
	}

	protected void destroy() {
		if (hasDriver()) {
			try {
				getDriver().close();
			} catch(Exception e) {
			}
			
			try {
				getDriver().quit();
			} catch(Exception e) {
			}
			
			
		}
	}

	public int getMaxTries() {
		return maxTries;
	}

	public void setMaxTries(int maxTries) {
		this.maxTries = maxTries;
	}

	public long getIntervalBetweenTries() {
		return intervalBetweenTries;
	}

	public void setIntervalBetweenTries(long intervalBetweenTries) {
		this.intervalBetweenTries = intervalBetweenTries;
	}

	public String getSpoofUserAgent() {
		return spoofUserAgent;
	}

	public void setSpoofUserAgent(String spoofUserAgent) {
		this.spoofUserAgent = spoofUserAgent;
	}

}

package org.brunocvcunha.phantranslator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Simple mirror to Google Translator Panel
 * 
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class GoogleTranslatorPage {

	@FindBy(how = How.ID_OR_NAME, using = "source")
	@CacheLookup
	private WebElement literal;

	public void insertLiteral(String literal) {
		this.literal.clear();
		this.literal.sendKeys(literal);
		// this.literal.submit();
	}
}

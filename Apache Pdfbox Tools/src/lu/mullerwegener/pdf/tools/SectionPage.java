package lu.mullerwegener.pdf.tools;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * Manage a section of the page of the pdf file.
 * Initially, it's dedicated to repeated content in different pages, such as headers or footers.
 * @author paquet
 *
 */
public class SectionPage{
	private ArrayList<SectionElement> elements;
	private PDFont font;
	private float fontSize;

	/**
	 * Default constructor. 
	 * Sets the font to Helvetica and the size font to 10.0f. 
	 */
	public SectionPage(){
		elements = new ArrayList<SectionElement>();
		font = PDType1Font.HELVETICA;
		fontSize = 10.0f;
	}

	/**
	 * Add an element to the section.
	 * @param posX		the position of the element from the left of the page. In pixels.
	 * @param posY		the position of the element from the bottom of the page. In pixels.
	 * @param value		the value of the element.
	 * @return			the SectionElement object created.
	 */
	public SectionElement add(float posX, float posY, String value){
		SectionElement se = new SectionElement(posX, posY, value);
		elements.add(se);
		return se;
	}

	/**
	 * Add an element to the section. The text will be underline.
	 * @param posX		the position of the element from the left of the page. In pixels.
	 * @param posY		the position of the element from the bottom of the page. In pixels.
	 * @param value		the value of the element.
	 * @return			the SectionElement object created.
	 */
	public SectionElement addUnderline(float posX, float posY, String value){
		SectionElement se = new SectionElement(posX, posY, value, true);
		elements.add(se);
		return se;
	}

	/**
	 * The object that compose a SectionPage object
	 * @author paquet
	 *
	 */
	class SectionElement{
		/**
		 * 	the position of the element from the left of the page. In pixels.
		 */
		public float posX;
		/**
		 * the position of the element from the bottom of the page. In pixels.
		 */
		public float posY;
		/**
		 * the value of the element.
		 */
		public String value;
		/**
		 * Define if the value is underlined
		 */
		public boolean underline;

		/**
		 * Default constructor. No underline.
		 * @param newPosX		the position of the element from the left of the page. In pixels.
		 * @param newPosY		the position of the element from the bottom of the page. In pixels.
		 * @param newValue		the value of the element.
		 */
		public SectionElement(float newPosX, float newPosY, String newValue){
			this(newPosX, newPosY, newValue, false);			
		}

		/**
		 * Constructor with the possibility to underline the value.
		 * @param newPosX		the position of the element from the left of the page. In pixels.
		 * @param newPosY		the position of the element from the bottom of the page. In pixels.
		 * @param newValue		the value of the element.
		 * @param newUnderline	is the value underlined ?
		 */
		public SectionElement(float newPosX, float newPosY, String newValue, boolean newUnderline){
			this.posX = newPosX;
			this.posY = newPosY;
			this.value = newValue;			
			this.underline = newUnderline;
		}
	}

	/**
	 * Draw the section on the pdf file represented by contents.
	 * @param pdft			an instance of PdfTools for the document.
	 * @param contents		the content that represents the page.
	 * @throws IOException	when the content cannot be written on.
	 */
	public void draw(PdfTools pdft, PDPageContentStream contents) throws IOException{
		for(SectionElement se : elements){
			if(se.underline){
				pdft.underlineText(contents, font, fontSize, se.posX, se.posY, se.value);
			}
			contents.setFont(font, fontSize);					
			pdft.addText(contents, se.posX, se.posY, se.value);
			contents.setFont(pdft.getLastUsedFont(), pdft.getLastUsedSize());
		}
	}

	/**
	 * Change the font of the section
	 * @param newFont	the desired font.
	 * @see PDFont
	 */
	public void setFont(PDFont newFont){
		this.font = newFont;
	}

	/**
	 * Change the size of the section.
	 * @param newSize	the desired size.
	 */
	public void setSize(float newSize){
		this.fontSize = newSize;
	}
}
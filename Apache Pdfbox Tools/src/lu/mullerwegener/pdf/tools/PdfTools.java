package lu.mullerwegener.pdf.tools;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

/**
 * PdfTools is a Library of tools to generate or manipulate PDF files with the help of Apache PdfBox.
 * @author paquet, pierson, chipon
 *
 */
public class PdfTools {

	PDFont lastUsedFont;
	float lastUsedSize;
	PDDocument doc;
	PDPage currentPage;	
	float ligneDetail = 0;
	Color color00506f = new Color(0, 80, 111);
	int num_ligne = 1;
	float tableHeight = 0;
	boolean underline = false;

	/**
	 * PdfTools is bind to a PDDocument which is a class of Apache PdfBox.
	 * @param pdoc the document to bind pdftools
	 */
	public PdfTools(PDDocument pdoc) {
		doc = pdoc;
		lastUsedFont = PDType1Font.HELVETICA;
		lastUsedSize = 10.0f;
	}	

	/**
	 * Add pText to the pdf which contents represents at the point (posX, posY).
	 * @param contents 		the content of the page
	 * @param posX     		the position in pixels from the left side of the page
	 * @param posY			the position in pixels from the bottom of the page
	 * @param pText			the text to show at the point (posX, posY)
	 * @throws IOException	when the contents cannot be written on.
	 */
	public void addText(PDPageContentStream contents, float posX, float posY, String pText) throws IOException {
		contents.beginText();		
		contents.setTextMatrix(Matrix.getTranslateInstance(posX, posY));
		contents.showText(pText);
		contents.endText();
		if(this.underline)
			underlineText(contents, posX, posY, pText);
	}

	/**
	 * Add pText to the pdf which contents represents at the height posY.
	 * @param contents		the content of the page
	 * @param posY			the position in pixels from the bottom of the page
	 * @param pText			the text to show at height posY
	 * @throws IOException	when the contents cannot be written on.
	 */
	public void addTextCentered(PDPageContentStream contents, float posY, String pText) throws IOException {
		PDRectangle pageSize = PDRectangle.A4;
		float stringWidth = this.lastUsedFont.getStringWidth( pText );
		float centeredXPosition = (pageSize.getWidth() - (stringWidth*this.lastUsedSize)/1000f)/2f;
		addText(contents, centeredXPosition, posY, pText);
	}

	/**
	 * Add pText to the pdf which contents represents at the height posY between boundaryLeft and boundaryRight.
	 * @param contents			the content of the page
	 * @param posY				the position in pixels from the bottom of the page
	 * @param boundaryLeft		the left position of the zone where the text is shown.
	 * @param boundaryRight		the right position of the zone where the text is shown.
	 * @param pText				the text to show at height posY
	 * @throws IOException		when the contents cannot be written on.
	 */
	public void addTextCentered(PDPageContentStream contents, float posY, float boundaryLeft, float boundaryRight, String pText) throws IOException {		
		float stringWidth = this.lastUsedFont.getStringWidth( pText );
		float centeredXPosition = boundaryLeft + (boundaryRight - boundaryLeft - (stringWidth*this.lastUsedSize)/1000f)/2f;
		addText(contents, centeredXPosition, posY, pText);
	}
	
	/**
	 * Add pText to the pdf which contents represents at the height posY.
	 * Align the text 5 pixels to the left of boundaryRight. 
	 * @param contents 			the content of the page
	 * @param posY				the position in pixels from the bottom of the page
	 * @param boundaryRight		the right position of the zone where the text is aligned.
	 * @param pText				the text to show
	 * @throws IOException		when the contents cannot be written on.
	 */
	public void addTextRightAligned(PDPageContentStream contents, float posY, float boundaryRight, String pText) throws IOException {		
		float stringWidth = this.lastUsedFont.getStringWidth( pText );
		float centeredXPosition = (boundaryRight - 5 - (stringWidth*this.lastUsedSize)/1000f);
		addText(contents, centeredXPosition, posY, pText);
	}	
	
	/**
	 * Stock pfont and psize in lastUsedFont and lastUsedSize respectively and then apply them to contents.
	 * The main purpose is to conviniently roll back to the main font and size for the document.
	 * @param contents			the content of the page
	 * @param pfont				the font you want to write with
	 * @param psize				the size of pfont
	 * @throws IOException		when the contents cannot be written on.
	 */
	public void setFont(PDPageContentStream contents, PDFont pfont, float psize) throws IOException {
		this.lastUsedFont = pfont;
		this.lastUsedSize = psize;
		contents.setFont(pfont, psize);		
	}
	
	/**
	 * Underline the specified text.
	 * Beware it just draw a line at the bottom of the text. It doesn't show the actual text.
	 * @param contents			the content of the page
	 * @param posX				the position from the left border of the page
	 * @param posY				the position from the bottom of the page
	 * @param pText				the text to be underlined.
	 * @throws IOException		when the contents cannot be written on.
	 */
	private void underlineText(PDPageContentStream contents, float posX, float posY, String pText) throws IOException{
		float stringWidth = this.lastUsedFont.getStringWidth( pText );
		float posRight = posX + 1 + (stringWidth*this.lastUsedSize)/1000f;
		drawLine(contents, posX, posY - 2, posRight, posY - 2);
	}
	
	/**
	 * Underline the specified text.
	 * Beware it just draw a line at the bottom of the text. It doesn't show the actual text.
	 * @param contents			the content of the page
	 * @param font				the font of the underlined text.
	 * @param fontSize			the size of the font of the underlined text.
	 * @param posX				the position from the left border of the page
	 * @param posY				the position from the bottom of the page
	 * @param pText				the text to be underlined.
	 * @throws IOException		when the contents cannot be written on.
	 */
	public void underlineText(PDPageContentStream contents, PDFont font, float fontSize, float posX, float posY, String pText) throws IOException{
		float stringWidth = font.getStringWidth( pText );
		float posRight = posX + 1 + (stringWidth*fontSize)/1000f;
		drawLine(contents, posX, posY - 2, posRight, posY - 2);
	}

	/**
	 * Draw the image from the file chemin in contents at point (posX, posY), with a width and height.
	 * @param contents			the content of the page
	 * @param chemin			path of the image file
	 * @param posX				the position from the left border of the page
	 * @param posY				the position from the bottom border of the page
	 * @param width				the width of the image you want
	 * @param height			the height of the image you want
	 * @throws IOException		when the contents cannot be written on.
	 */
	public void drawImage(PDPageContentStream contents, String chemin, float posX, float posY, float width, float height) throws IOException {
		PDImageXObject pdImageLogoMW = PDImageXObject.createFromFile(chemin, this.doc);
		contents.drawImage(pdImageLogoMW, posX, posY, width, height);
	}

	/**
	 * Add a page with a hautDePage and a basDePage.
	 * @param page				the content of the page
	 * @param style				a String to retrieve the style you want
	 * @throws IOException		when the contents cannot be written on.
	 */
	public void addPage(PDPage page, String style) throws IOException {
		this.currentPage = page;
		this.doc.addPage(page);
	}

	/**
	 * Returns the today date in the form dd-MM-yyyy with day, month and year separate by separator.
	 * @param separator 		the String to be displayed between day, month and year.
	 * @return the String representation of today date
	 */
	public static String getStringDateAuj(String separator) {
		String result = "";		
		ZoneId zonedId = ZoneId.of( "Europe/Paris" );
		LocalDate today = LocalDate.now( zonedId );		
		int month = today.getMonth().getValue();
		String sMonth = Integer.toString(month);
		if(month < 10) {
			sMonth = "0" + month;
		}
		result += + today.getDayOfMonth() + separator + sMonth + separator + today.getYear();
		return result;
	}
	
	/**
	 * Returns the today date in the form dd-MM-yyyy with day, month and year separate by separator.
	 * @return the String representation of today year
	 */
	public static String getStringAnneeAuj() {
		String result = "";		
		ZoneId zonedId = ZoneId.of( "Europe/Paris" );
		LocalDate today = LocalDate.now( zonedId );				
		result += today.getYear();
		return result;
	}

	/**
	 * Draw a line on contents from the point (posxOrigine, posyOrigine) to the point (posxDestination, posyDestination).
	 * The line will be of width lineWidth and color lineColor.
	 * @param contents				the content of the page.
	 * @param posxOrigine			the position from the left border of the page where the line starts. In pixels.
	 * @param posyOrigine			the position from the bottom border of the page where the line starts. In pixels.
	 * @param posxDestination		the position from the left border of the page where the line ends. In pixels.
	 * @param posyDestination		the position from the bottom border of the page where the line ends. In pixels.
	 * @param lineWidth				the width of the line. In pixels.
	 * @param lineColor				the color of the line.
	 * @throws IOException			when the contents cannot be written on.
	 * @see Color
	 */
	public void drawLine(PDPageContentStream contents, float posxOrigine, float posyOrigine, float posxDestination, float posyDestination, float lineWidth, Color lineColor) throws IOException{		
		contents.setStrokingColor(lineColor);
		contents.setLineWidth(lineWidth);
		contents.moveTo(posxOrigine, posyOrigine);
		contents.lineTo(posxDestination, posyDestination);					
		contents.stroke();	
	}

	/**
	 * Draw a line on contents from the point (posxOrigine, posyOrigine) to the point (posxDestination, posyDestination).
	 * Set the width of the line to the 0.5 pixels and the color will be black.
	 * @param contents				the content of the page.
	 * @param posxOrigine			the position from the left border of the page where the line starts. In pixels.
	 * @param posyOrigine			the position from the bottom border of the page where the line starts. In pixels.
	 * @param posxDestination		the end position from the left border of the page where the line ends. In pixels.
	 * @param posyDestination		the position from the bottom border of the page where the line ends. In pixels.
	 * @throws IOException			when the contents cannot be written on.
	 */
	public void drawLine(PDPageContentStream contents, float posxOrigine, float posyOrigine, float posxDestination, float posyDestination) throws IOException{		
		contents.setStrokingColor(Color.black);
		contents.setLineWidth(0.5f);
		contents.moveTo(posxOrigine, posyOrigine);
		contents.lineTo(posxDestination, posyDestination);					
		contents.stroke();	
	}

	/**
	 * Get the last font size used by {@link PdfTools#setFont(PDPageContentStream, PDFont, float) PdfTools.setFont}
	 * @return the last font size used.
	 */
	public float getLastUsedSize() {
		return this.lastUsedSize;
	}

	/**
	 * Get the last font used by {@link PdfTools#setFont(PDPageContentStream, PDFont, float) PdfTools.setFont}
	 * @return		the last font used.
	 * @see PDFont
	 */
	public PDFont getLastUsedFont() {
		return this.lastUsedFont;
	}

	/**
	 * Round a float number with decimalPlace decimal places and return its {@link String} representation.
	 * @param d					the number to be rounded.
	 * @param decimalPlace		the number of decimal places.
	 * @param afficheZero		do we return something is the value is zero ?
	 * @return					the String reprsentation of the number.
	 */
	public static String round(float d, int decimalPlace, boolean afficheZero) {				
		BigDecimal bd = new BigDecimal(Float.toString(d));
		BigDecimal bdzero = BigDecimal.ZERO;
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP); 
		bdzero = bdzero.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);			
		if(bdzero.equals(bd) && !afficheZero){
			return "";
		}else{
			String test = bd.toString();
			return test.replaceAll("\\.", ",");	
		}	        
	}

	/**
	 * Open the PDF file with the default reader.
	 * @param absolutePath		the absolute path of the file.
	 */
	public static void openPDFFile(String absolutePath){
		if (Desktop.isDesktopSupported()) {
			try {
				File pdfFile = new File(absolutePath);
				Desktop.getDesktop().open(pdfFile);
			} catch (IOException ex) {
				System.out.println("Cannot open the file specified !");
			}
		}
	}

	/**
	 * Return a PDDocument which contains only the specified page.
	 * @param doc			the PDDocument where to extract the page.
	 * @param numPage		the number of the page to be extracted.
	 * @return				the new document of only one page.
	 */
	public PDDocument getPageAlone(PDDocument doc, int numPage){
		if(numPage != 0){
			PDPage pageAGarder = doc.getPage(numPage); 
			while(doc.getNumberOfPages()>0){
				doc.removePage(0);
			}
			doc.addPage(pageAGarder) ;
		}		
		return doc;
	}

	/**
	 * Delete all the files which ends with "pdf" of the specified directory.
	 * Very useful to clean your working  folder.
	 * @param pathOfDirectory			the path of the directory. Should be "./" in test environment.
	 * @param doNotDeleteThisFile		the file you don't want to delete.
	 */
	public void deletePDFInDirectory(String pathOfDirectory, String doNotDeleteThisFile){
		File dir = new File(pathOfDirectory);
		if(dir.isDirectory()){
			File[] listf = dir.listFiles(new FileFilter(){
				public boolean accept(File pathname){										
					return pathname.getName().toLowerCase().endsWith("pdf");
				}
			});
			for(File f : listf){
				if(!f.getName().equals(doNotDeleteThisFile))
					f.delete();
			}	
		}
	}
	
	/**
	 * All the texts added with addText method after this instruction, will be underlined. 
	 */
	public void enableUnderline() {
		this.underline = true;
	}
	
	/**
	 * All the texts added with addText method before this instruction, will be underlined.
	 */
	public void disableUnderline() {
		this.underline = false;
	}
	
	/**
	 * Add at the position (posX, posY) the total number of page in each page of the document.
	 * @param posX				the position from the left border of the page. In pixels.
	 * @param posY				the position from the top border of the page. In pixels.
	 * @throws IOException		if the content is not writable
	 */
	public void addTotalNumberofPagesInEachPage(float posX, float posY) throws IOException{
		for(PDPage page : doc.getPages()){
			PDPageContentStream contents = new PDPageContentStream(doc, page, AppendMode.APPEND, false);
			contents.setFont(lastUsedFont, lastUsedSize);
			addText(contents, posX, posY, Integer.toString(doc.getNumberOfPages()));
			contents.close();
			contents = null;
		}
	}
}

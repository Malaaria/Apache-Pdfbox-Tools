package lu.mullerwegener.pdf.tools.pdftable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import lu.mullerwegener.pdf.tools.PdfTools;

/**
 * A class to manage a table of data.
 * You can load it in one time and specify where it must stop at each page.
 * @author paquet
 *
 */
public class PdfTable implements Iterator<PdfRow> {
	private List<PdfRow> tableRows;
	private float posX;
	private float posY;
	int currentIndex = 0;
	private float[] defaultColumns;
	private float defaultRowHeight;
	private PdfRow rowEntete;
	/**
	 * Create a table with a top left corner at point (posX, posY), with a width and height of 100.
	 * @param posX	the position from the left of the page. In pixels.
	 * @param posY	the position from the bottom of the page. In pixels.
	 */
	public PdfTable(float posX, float posY){
		this(posX, posY, 0, 0);		
	}

	/**
	 * Create a table with a top left corner at point (posX, posY) with a width of width pixels and a height of height pixels.
	 * @param posX		the gap from the left border of the page.
	 * @param posY		the gap from the bottom of the page.
	 * @param width		the width of the table.
	 * @param height	the height of the table.
	 */
	public PdfTable(float posX, float posY, float width, float height){
		this.tableRows = new ArrayList<PdfRow>();
		this.posX = posX;
		this.posY = posY;
		this.defaultColumns = new float[2];
		this.defaultColumns[0] = posX;
		this.defaultColumns[1] = width;
		this.defaultRowHeight = height;
		if(width != 0 || height != 0){
			PdfRow row = new PdfRow(height);
			PdfCell cell = new PdfCell(width);
			row.addCell(cell);
			this.tableRows.add(row);
		}
		rowEntete = new PdfRow(32);
	}			

	/**
	 * Return the position of the table from the left border of the page.
	 * @return		the position from the the left border of the page. Measured in pixels.
	 */
	public float getPosX(){
		return this.posX;
	}

	/**
	 * Return the position of the table from the bottom border of the page.
	 * @return		the position from the the bottom border of the page. Measured in pixels.
	 */
	public float getPosY(){
		return this.posY;
	}

	/**
	 * Set the position of the top left corner of the table from the left border of the page.
	 * @param desiredPosX		the position of the table from the left border of the page. Measured in pixels.		
	 */
	public void setPosX(float desiredPosX){
		this.posX = desiredPosX;
	}

	/**
	 * Set the position of the top left corner of the table from the bottom border of the page.
	 * @param desiredPosY		the position of the table from the bottom border of the page. Measured in pixels.
	 */
	public void setPosY(float desiredPosY){
		this.posY = desiredPosY;
	}

	/**
	 * Set the position of the top left corner of the table.
	 * @param desiredPosX		the position of the table from the left border of the page. Measured in pixels.
	 * @param desiredPosY		the position of the table from the bottom border of the page. Measured in pixels.
	 */
	public void setOrigin(float desiredPosX, float desiredPosY){
		this.setPosX(desiredPosX);
		this.setPosX(desiredPosY);
	}

	/**
	 * Return the list of rows of the table.
	 * @return	the list of rows of the table. 
	 * @see PdfRow
	 */
	public List<PdfRow> getRows(){
		return this.tableRows;
	}

	/**
	 * Return the specified row of the table.
	 * @param index		the row number you want to retrieve.
	 * @return			the PdfRow object that represents the row.
	 * @see PdfRow
	 */
	public PdfRow getRow(int index){
		return this.tableRows.get(index);
	}

	/**
	 * Add a row of defaultRowHeight to the table
	 * @return		the PdfRow object that represents the row.
	 * @see PdfRow
	 */
	public PdfRow addRow(){
		return this.addRow(this.defaultRowHeight);
	}

	/**
	 * Add a row with the specified height to the table.
	 * @param height	the height of the row.
	 * @return			the PdfRow object that represents the row.
	 * @see PdfRow
	 */
	public PdfRow addRow(float height){
		PdfRow row = new PdfRow(height);
		tableRows.add(row);
		return row;
	}

	/**
	 * Add a row represented by a PdfRow object to the table.
	 * @param newRow	the row to be added.
	 * @see PdfRow
	 */
	public void addRow(PdfRow newRow){
		tableRows.add(newRow);
	}

	/**
	 * Returns the number of rows currently in the table.
	 * @return		the number of rows of the table.
	 */
	public int getNumberOfRows(){
		return this.tableRows.size();
	}

	/**
	 * Draw the table represented by the PdfTable object.
	 * In order to draw the table you need a PDPageContentStream object.
	 * @param pdft			an instance of PdfTools.
	 * @param contents		the content where to draw the table.
	 * @param rupture_page	the line in pixels where to stop drawing.
	 * @see	PDPageContentStream PdfTools
	 * @throws IOException	 when the contents cannot write to the page
	 */
	public void drawTable(PdfTools pdft, PDPageContentStream contents, float rupture_page) throws IOException{
		float origX = this.posX;
		float origY = this.posY;
		boolean entete = false;
		while(this.hasNext()){
			if(!entete){
				entete = true;
				contents.setFont(PDType1Font.HELVETICA_BOLD, pdft.getLastUsedSize());
				for(PdfCell cell : rowEntete.getCells()){
					origX = drawBordersAndText(pdft, contents, origX, origY, rowEntete.getHeight(), cell);
				}
			origY -= rowEntete.getHeight();
			origX = this.posX;
			}			
			contents.setFont(pdft.getLastUsedFont(), pdft.getLastUsedSize());
			PdfRow row = this.next();
			for(PdfCell cell : row.getCells()){
				origX = drawBordersAndText(pdft, contents, origX, origY, row.getHeight(), cell);				
			}
			origX = this.posX;
			origY -= row.getHeight();
			if(origY < rupture_page){
				break;				
			}
		}
	}

	@Override
	public boolean hasNext() {
		return currentIndex<tableRows.size();
	}

	@Override
	public PdfRow next() {			
		return tableRows.get(currentIndex++);							
	}

	/**
	 * Set default width and number of columns for the table.
	 * Columns means the lines draw on the pdf and not the actual columns of the table.
	 * So if you want 2 columns of data , you must sepicified 3 values :
	 * <ul>
	 * <li>one for the left border
	 * <li>one for the middle one
	 * <li>one for the right border
	 * </ul>
	 * @param desiredColumns	an array of float, must be at least two values
	 */
	public void setDefaultColumn(float[] desiredColumns){
		this.defaultColumns = new float[desiredColumns.length];
		this.defaultColumns = desiredColumns;
	}

	/**
	 * Get the array that represents the columns of the table.
	 * @return		an array of float which contains each x positions of the columns to be drawn. In pixels.
	 */
	public float[] getDefaultColumns(){
		return this.defaultColumns;
	}

	/**
	 * Set the default row height of the table to be used with {@link #addRow()}.
	 * @param desiredHeight		the default height of a row for the table
	 */
	public void setDefaultRowHeight(float desiredHeight){
		this.defaultRowHeight = desiredHeight;
	}

	/**
	 *Get the default row height of the table.
	 * @return		the default row height of the table. In pixels.
	 */
	public float getDefaultRowHeight(){
		return this.defaultRowHeight;
	}	
	
	/**
	 * Get the row that represents the header of the table.
	 * @return		the header row of the table
	 */
	public PdfRow getEntete(){
		return this.rowEntete;
	}
	
	/**
	 * Draw borders and value of the cells in the pdf at the right place
	 * @param pdft			an instance of PdfTools.
	 * @param contents		the contents that represents the page.
	 * @param origX			the position from the left of the page where to start to draw. In pixels.
	 * @param origY			the position from the bottom of the page where to start to draw. In pixels.
	 * @param height		the height of a row.
	 * @param cell			the cell to be drawn.
	 * @return				the position where to start the next row.
	 * @throws IOException	when the contents cannot be written on.
	 */
	private float drawBordersAndText(PdfTools pdft, PDPageContentStream contents, float origX, float origY, float height, PdfCell cell) throws IOException{
		// top border		
		if(cell.getBorderTop().isShown())
		pdft.drawLine(contents, origX, origY, origX + cell.getWidth(), origY);
		// right border
		if(cell.getBorderRight().isShown())
		pdft.drawLine(contents, origX + cell.getWidth(), origY, origX + cell.getWidth(), origY - height);
		// bottom border
		if(cell.getBorderBottom().isShown())
		pdft.drawLine(contents, origX + cell.getWidth(), origY - height, origX, origY - height);
		// left border
		if(cell.getBorderLeft().isShown())
		pdft.drawLine(contents, origX, origY - height, origX, origY);
		// Text if present
		float stringHeight = pdft.getLastUsedSize();
		if(cell.getAlignment() == PdfHorizontalAlignment.TEXT_CENTER){
			pdft.addTextCentered(contents, (origY - height / 2 - stringHeight / 2), origX, (origX + cell.getWidth()), cell.getValue());
		}else if(cell.getAlignment() == PdfHorizontalAlignment.TEXT_RIGHT){
			pdft.addTextRightAligned(contents, (origY - height / 2 - stringHeight / 2), origX + cell.getWidth(), cell.getValue() );
		}else{
			pdft.addText(contents, origX + 2, (origY - height / 2 - stringHeight / 2), cell.getValue());
		}
		origX += cell.getWidth();
		return origX;
	}
	
	/**
	 * Show all the borders of all rows of the table.
	 */
	public void showAllBorders(){
		for(PdfRow row : tableRows){
			row.showAllBorders();
		}
	}
	
	/**
	 * Hide all the borders of all rows of the table.
	 */
	public void hideAllBorders(){
		for(PdfRow row : tableRows){
			row.hideAllBorders();
		}
	}
}	
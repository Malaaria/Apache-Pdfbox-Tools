package lu.mullerwegener.pdf.tools.pdftable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PdfRow implements Iterator<PdfCell> {
	private float rowHeight;
	List<PdfCell> rowCells;
	int currentIndex = 0;
	
	/**
	 * Create a PdfRow object that represents a row of a PdfTable object.
	 * @param initialHeight		the height of the row in pixels.
	 */
	public PdfRow(float initialHeight){
		this.rowHeight = initialHeight;
		rowCells = new ArrayList<PdfCell>();
	}
	
	/**
	 * Returns the height of the PdfRow object.
	 * @return		the height of the row in pixels.
	 */
	public float getHeight(){
		return this.rowHeight;
	}
	
	/**
	 * Sets the height of the row.	
	 * @param desiredHeight		the height of the row in pixels.
	 */
	public void setHeight(float desiredHeight){
		this.rowHeight = desiredHeight;
	}
	
	/**
	 * Returns the list of the columns of the table.
	 * @return	The list of PdfCell objects composing the row.
	 * @see PdfCell
	 */
	public List<PdfCell> getCells(){
		return this.rowCells;
	}
	
	/**
	 * Add a cell to the row.
	 * @param newCell	the cell to add.	
	 * @see PdfCell
	 */
	public void addCell(PdfCell newCell){
		rowCells.add(newCell);
	}
	
	/**
	 * Add a new cell to the row with the specified width and returns it.
	 * @param cellWidth		the wifth of the cell.
	 * @return	the PdfCell object that reprsents the cell added to the row.
	 * @see PdfCell
	 */
	public PdfCell addCell(float cellWidth){
		PdfCell cell = new PdfCell(cellWidth); 
		rowCells.add(cell);
		return cell;
	}
	
	/**
	 * Add a new cell to the row with the specified width and the specified String value and returns the PdfCEll object that represents it.
	 * @param cellWidth		the width of the cell.
	 * @param cellValue		the String value of the cell.
	 * @return	PdfCell
	 */
	public PdfCell addCell(float cellWidth, String cellValue){
		PdfCell cell = new PdfCell(cellWidth, cellValue);
		rowCells.add(cell);
		return cell;
	}
	
	/**
	 * Add a new cell to the rpw with the specified width, value and alignment.
	 * @param cellWidth		the width of the cell.
	 * @param cellValue		the value of the cell.
	 * @param align			the alignment of the text in the cell.
	 * @return PdfCell		the cell that was added.
	 */
	public PdfCell addCell(float cellWidth, String cellValue, PdfHorizontalAlignment align){
		PdfCell cell = new PdfCell(cellWidth, cellValue);
		cell.setTextAlignment(align);
		rowCells.add(cell);
		return cell;
	}
	
	/**
	 * Return the cell at the index.
	 * @param index		the desired cell index
	 * @return PdfCell	the Pdfcell object that represents the cell.
	 */
	public PdfCell getCell(int index){
		return rowCells.get(index);
	}
	
	/**
	 * Returns the number of cells in the row.
	 * @return	the number of cells in the row.
	 */
	public int getNumberOfCells(){
		return this.rowCells.size();
	}

	@Override
	public boolean hasNext() {		
		return this.currentIndex<this.rowCells.size();
	}

	
	@Override
	public PdfCell next() {		
			return this.rowCells.get(this.currentIndex++);		
	}
	
	/**
	 * Show the specified border for all the cells in the row.
	 * @param border	the border to show.
	 * @see PdfBorder
	 */
	public void showBorder(PdfBorder border){
		for(PdfCell cell : rowCells){
			cell.showBorder(border);
		}
	}
	
	/**
	 * Hide the specified border for all the cells in the row.
	 * @param border	the border to hide.
	 * @see	PdfBorder
	 */
	public void hideBorder(PdfBorder border){
		for(PdfCell cell : rowCells){
			cell.hideBorder(border);
		}
	}
	
	/**
	 * Hide all the borders of all the cell in the row.
	 */
	public void hideAllBorders(){
		for(PdfCell cell : rowCells){
			cell.hideAllBorders();
		}
	}
	
	/**
	 * Show all the borders of all the cell in the row.
	 */
	public void showAllBorders(){
		for(PdfCell cell : rowCells){
			cell.showAllBorders();
		}
	}
}
package lu.mullerwegener.pdf.tools.pdftable;

public class PdfCell {
	private float cellWidth;
	private String cellValue;
	private PdfHorizontalAlignment align;
	private PdfCellBorder border_bottom;
	private PdfCellBorder border_top;
	private PdfCellBorder border_left;
	private PdfCellBorder border_right; 

	/**
	 * Default constructor create a cell with the specified width, no text and aligned to the left.
	 * @param pcellWidth	the width of the cell
	 */
	public PdfCell(float pcellWidth){
		this(pcellWidth, "", PdfHorizontalAlignment.TEXT_LEFT);
	}

	/**
	 * Create a cell with the specified width, the specified text and aligned left.  
	 * @param pcellWidth	the width of the cell
	 * @param ptext			the text of the cell
	 */
	public PdfCell(float pcellWidth, String ptext){
		this(pcellWidth, ptext, PdfHorizontalAlignment.TEXT_LEFT);
	}

	/**
	 * Create a cell with the specified width, text and alignment.
	 * @param pcellWidth	the width of the cell
	 * @param ptext			the text of the cell
	 * @param palign		{@link PdfHorizontalAlignment}
	 */
	public PdfCell(float pcellWidth, String ptext, PdfHorizontalAlignment palign){
		cellWidth = pcellWidth;
		cellValue = ptext;
		align = palign;
		border_top = new PdfCellBorder();
		border_right = new PdfCellBorder();
		border_bottom = new PdfCellBorder();
		border_left = new PdfCellBorder();
	}

	/**
	 * Get the width of the cell
	 * @return	the width of the cell
	 */
	public float getWidth(){
		return this.cellWidth;
	}

	/**
	 *
	 * @return the value of the cell
	 */
	public String getValue(){
		return this.cellValue;
	}

	/**
	 * Sets the cell to the desired width
	 * @param desiredWidth	the width of the cell
	 */
	public void setWidth(float desiredWidth){
		this.cellWidth = desiredWidth;
	}

	/**
	 * Sets the value of the cell
	 * @param desiredValue	the text in the cell
	 */
	public void setValue(String desiredValue){
		this.cellValue = desiredValue;
	}

	/**
	 * Change the alignment for the content of the cell.
	 * @param align		{@link PdfHorizontalAlignment}
	 */
	public void setTextAlignment(PdfHorizontalAlignment align){
		this.align = align;		
	}

	/**
	 * Returns the object that represents the right border.
	 * @return 		the PdfCell that represents the right border
	 */
	public PdfCellBorder getBorderRight(){
		return this.border_right;
	}

	/**
	 * Returns the object that represents the top border.
	 * @return		the PdfCell that represents the top border
	 */
	public PdfCellBorder getBorderTop(){
		return this.border_top;
	}

	/**
	 * Returns the object that represents the left border.
	 * @return		the PdfCell that represents the left border
	 */
	public PdfCellBorder getBorderLeft(){
		return this.border_left;
	}

	/**
	 * Returns the object that represents the bottom border.
	 * @return		the PdfCell that represents the bottom border
	 */
	public PdfCellBorder getBorderBottom(){
		return this.border_bottom;
	}

	/**
	 * Returns the integer that represents the alignment of the cell.
	 * @return		the integer that represents the alignment of the cell.
	 * @see 	PdfHorizontalAlignment
	 */
	public PdfHorizontalAlignment getAlignment(){
		return this.align;
	}

	/**
	 * Hide the specified border.
	 * @param border	The border you want to hide.
	 * @see PdfBorder
	 */
	public void hideBorder(PdfBorder border){
		switch (border) {
		case BORDER_TOP:
			border_top.hide();
			break;
		case BORDER_RIGHT:
			border_right.hide();
			break;
		case BORDER_BOTTOM:
			border_bottom.hide();
			break;
		case BORDER_LEFT:
			border_left.hide();
			break;			
		default:
			break;
		}
	}

	/**
	 * Show the specified border.
	 * @param border	The border you want to show.
	 * @see PdfBorder
	 */
	public void showBorder(PdfBorder border){
		switch (border) {
		case BORDER_TOP:
			border_top.show();
			break;
		case BORDER_RIGHT:
			border_right.show();
			break;
		case BORDER_BOTTOM:
			border_bottom.show();
			break;
		case BORDER_LEFT:
			border_left.show();
			break;			
		default:
			break;
		}
	}

	/**
	 * Set the width of the line that represents the border in the pdf file.
	 * @param border	The border to define.
	 * @param width		The width of the line. In pixels.
	 * @see PdfBorder
	 */
	public void setBorderWidth(PdfBorder border, float width){
		switch (border) {
		case BORDER_TOP:
			border_top.setLineWidth(width);
			break;
		case BORDER_RIGHT:
			border_right.setLineWidth(width);
			break;
		case BORDER_BOTTOM:
			border_bottom.setLineWidth(width);
			break;
		case BORDER_LEFT:
			border_left.setLineWidth(width);
			break;			
		default:
			break;
		}
	}

	/**
	 * Hide the four borders of the cell.
	 */
	public void hideAllBorders(){		
		hideBorder(PdfBorder.BORDER_TOP);
		hideBorder(PdfBorder.BORDER_RIGHT);
		hideBorder(PdfBorder.BORDER_BOTTOM);
		hideBorder(PdfBorder.BORDER_LEFT);		
	}

	/**
	 * Show the four borders of the cell.
	 */
	public void showAllBorders(){		
		showBorder(PdfBorder.BORDER_TOP);
		showBorder(PdfBorder.BORDER_RIGHT);
		showBorder(PdfBorder.BORDER_BOTTOM);
		showBorder(PdfBorder.BORDER_LEFT);		
	}
}
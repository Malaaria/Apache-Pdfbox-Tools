package lu.mullerwegener.pdf.tools.pdftable;

public class PdfCellBorder {
	private boolean show;
	private float lineWidth;
	
	/**
	 * Default constructor create a border of 0.5f width and is shown.
	 */
	public PdfCellBorder(){
		this(0.5f, true);
	}
	
	/**
	 * Create a border with the specified width and is shown.
	 * @param width		the width of the border when it's stroke on the pdf
	 */
	public PdfCellBorder(float width){
		this(width, true);
	}
	
	/**
	 * Create a border with the specified width and the desired state.
	 * @param width		the width of the border when it's stroke on the pdf
	 * @param show		is the border stroke on the pdf
	 */
	public PdfCellBorder(float width, boolean show){
		this.lineWidth = width;
		this.show = show;
	}
	
	/**
	 * Show the border on the pdf. 
	 */
	public void show() {
		this.show = true;
	}
	
	/**
	 * Hide the border ont the pdf.
	 */
	public void hide(){
		this.show = false;
	}
	
	/**
	 * Retrieve the width of the border.
	 * @return the width of the border
	 */
	public float getLineWidth(){
		return this.lineWidth;
	}
	
	/**
	 * Sets the width of the border.
	 * @param desiredLineWidth	the desired width for the border 
	 */
	public void setLineWidth(float desiredLineWidth){
		this.lineWidth = desiredLineWidth;
	}
	
	/**
	 * Returns wether the border is shown on the pdf. 
	 * @return	the state of the border
	 */
	public boolean isShown(){
		return this.show;
	}
	
	
}
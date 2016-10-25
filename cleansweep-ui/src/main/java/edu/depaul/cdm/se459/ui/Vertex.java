package edu.depaul.cdm.se459.ui;

public class Vertex {
	Cell cell;
	Vertex[] nearByCells;
	Vertex predecessor; // previous vertex
	double stationDistance;
	
	public Vertex(Cell cell){
		
		this.cell = cell;
		this.nearByCells = new Vertex[4];
		this.stationDistance = Double.POSITIVE_INFINITY; //the distance to the station
		this.predecessor = null;
				
	}
	
	public void setParent(Vertex vertex){
		
		this.predecessor = vertex;
	}
	
	public Vertex getParent(){
		
		return this.predecessor;
	}
	
	
	public void setNearCell(int index, Vertex nearCell){
		
			this.nearByCells[index] = nearCell;
	}
	
	public Vertex[] getNearCell(){
		
		return this.nearByCells;
	}
	
	public Cell getCell(){
		
		return this.cell;
	}
	
	
	public void setSourceDistance(double d){
		
		this.stationDistance = d;
	}
	
	public double getSourceDistance(){
		
		return this.stationDistance;
	}
	
	
	public String toString() {
		
		String CellName = "Cell : (" + this.cell.getX() + " , " + this.cell.getY() + ")";
		
		return CellName;
		
	}	
		
	
	public boolean sameAs(Vertex v) {
		
		if ((this.getCell().getX() == v.getCell().getX()) && (this.getCell().getY() == v.getCell().getY())){
			return true;
		}
		return false;
	}

	
	

public int compareTo(Object obj) {
	return 0;
	
}
	
}

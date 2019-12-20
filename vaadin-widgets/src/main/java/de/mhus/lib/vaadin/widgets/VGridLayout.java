package de.mhus.lib.vaadin.widgets;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;

public class VGridLayout extends Div {

    private static final long serialVersionUID = 1L;

    private int cols;
    private int rows;
    
    public VGridLayout(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
    }
    
    public int getCols() {
        return cols;
    }
    public void setCols(int cols) {
        this.cols = cols;
    }
    public int getRows() {
        return rows;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setMargin(boolean b) {
        // TODO Auto-generated method stub
        
    }

    public void setSpacing(boolean b) {
        // TODO Auto-generated method stub
        
    }

    public void setHideEmptyRowsAndColumns(boolean b) {
        // TODO Auto-generated method stub
        
    }

    public void setColumnExpandRatio(int i, float f) {
        // TODO Auto-generated method stub
        
    }

    public void add(Component component, int col, int row) {
        // TODO Auto-generated method stub
        
    }

    public void add(Component component, int i, int row, int j, int row2) {
        // TODO Auto-generated method stub
        
    }
    
}

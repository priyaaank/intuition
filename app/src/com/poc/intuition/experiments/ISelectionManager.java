package com.poc.intuition.experiments;

public interface ISelectionManager {

    public void addNewSelection(int position);

    public void removeSelection(int position);

    public void clearSelection();

    public void processCategoryChange();

}

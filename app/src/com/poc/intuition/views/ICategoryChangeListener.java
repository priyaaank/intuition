package com.poc.intuition.views;

public interface ICategoryChangeListener {

    void processCategoryChange();
    void removeSelection(int position);
    void addNewSelection(int position);
    void clearSelection();

}

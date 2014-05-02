package com.poc.intuition.experiments;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import com.poc.intuition.R;
import com.poc.intuition.views.ICategoryChangeListener;

public class ContextualMultiTransactionSelectListener implements AbsListView.MultiChoiceModeListener {

    private ICategoryChangeListener categoryChangeListener;
    private ActionMode contextualBarActionMode;

    public ContextualMultiTransactionSelectListener(ICategoryChangeListener categoryChangeListener) {
        this.categoryChangeListener = categoryChangeListener;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.context_transaction_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_category:
                categoryChangeListener.processCategoryChange();
                contextualBarActionMode = mode;
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        categoryChangeListener.clearSelection();
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        if (checked) {
            categoryChangeListener.addNewSelection(position);
        } else {
            categoryChangeListener.removeSelection(position);
        }
    }

    public void markActionModeFinished() {
        if(contextualBarActionMode != null) contextualBarActionMode.finish();
    }

}

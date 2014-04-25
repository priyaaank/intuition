package com.poc.intuition.views;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import com.poc.intuition.R;

public class ContextualMultiTransactionSelectListener implements AbsListView.MultiChoiceModeListener {

    private final TransactionListingAdapter mAdapter;

    public ContextualMultiTransactionSelectListener(TransactionListingAdapter adapter) {
        this.mAdapter = adapter;
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
                mAdapter.clearSelection();
                mAdapter.notifyDataSetChanged();
                mode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mAdapter.clearSelection();
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        if (checked) {
            mAdapter.addNewSelection(position);
        } else {
            mAdapter.removeSelection(position);
        }
        mAdapter.notifyDataSetChanged();
    }
}

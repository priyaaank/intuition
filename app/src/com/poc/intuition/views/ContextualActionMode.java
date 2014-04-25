package com.poc.intuition.views;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.poc.intuition.R;

public class ContextualActionMode implements ActionMode.Callback {
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//        MenuInflater inflater = mode.getMenuInflater();
//        inflater.inflate(R.menu.context_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_share:
//                //do something;
//                mode.finish(); // Action picked, so close the CAB
//                return true;
//            default:
//                return false;
//        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        //Do nothing
    }
}

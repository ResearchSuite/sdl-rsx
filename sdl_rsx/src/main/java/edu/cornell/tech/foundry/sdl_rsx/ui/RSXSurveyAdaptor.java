package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import java.util.Set;

/**
 * Created by jameskizer on 12/8/16.
 */
public abstract class RSXSurveyAdaptor <T> extends BaseAdapter {
    public abstract void clearCurrentSelected();
    public abstract Set<T> getCurrentSelected();
    abstract public void setSelectedForValue(T value, boolean selected);
    public abstract boolean getSelectedForValue(T value);
}

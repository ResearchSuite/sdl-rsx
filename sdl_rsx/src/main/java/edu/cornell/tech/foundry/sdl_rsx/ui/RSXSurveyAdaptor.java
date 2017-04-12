package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.support.annotation.Nullable;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import org.researchstack.backbone.model.Choice;

import java.util.Set;

/**
 * Created by jameskizer on 12/8/16.
 */
public abstract class RSXSurveyAdaptor <T> extends BaseAdapter {
    public abstract void clearCurrentSelected();
    public abstract Set<Choice> getCurrentSelected();
    abstract public void setSelectedForChoice(Choice choice, boolean selected);
    public abstract boolean getSelectedForChoice(Choice choice);
    @Nullable
    public Choice getChoiceForValue(Object value, Choice[] choices) {
        for(int i=0; i<choices.length; i++) {
            if (value.equals(choices[i].getValue())) {
                return choices[i];
            }
        }
        return null;
    }

    public T getValueForChoice(Choice choice) {
        return (T)choice.getValue();
    }
}

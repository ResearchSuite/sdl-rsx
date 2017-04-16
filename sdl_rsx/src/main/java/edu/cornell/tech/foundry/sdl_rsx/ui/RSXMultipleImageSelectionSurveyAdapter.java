package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.researchstack.backbone.model.Choice;
import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.utils.ResUtils;
import org.researchstack.backbone.utils.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import edu.cornell.tech.foundry.sdl_rsx.answerformat.RSXImageChoiceAnswerFormat;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXMultipleImageSelectionSurveyStep;
import edu.cornell.tech.foundry.sdl_rsx.R;

/**
 * Created by jk on 6/15/16.
 */
public class RSXMultipleImageSelectionSurveyAdapter <T> extends RSXSurveyAdaptor {

    private Set<Choice> currentSelected;
    private RSXImageChoice[] imageChoices;
    private RSXMultipleImageSelectionSurveyStep step;
    private int cellWidth;

    //TODO: Add excluded items to result


    public RSXMultipleImageSelectionSurveyAdapter(
            RSXMultipleImageSelectionSurveyStep step,
            StepResult<T[]> result) {
        super();
        this.step = step;


        RSXImageChoiceAnswerFormat answerFormat = (RSXImageChoiceAnswerFormat)this.getStep().getAnswerFormat();

        if (this.step.getExcludedIdentifiers() != null) {
            ArrayList<RSXImageChoice> filteredChoices = new ArrayList<>();
            for (RSXImageChoice imageChoice : answerFormat.getImageChoices()) {
                boolean exclude = false;
                for (int i=0; i<this.step.getExcludedIdentifiers().length; i++) {
                    String identifier = this.step.getExcludedIdentifiers()[i];
                    if (identifier.equals(imageChoice.getValue())) {
                        exclude = true;
                    }
                }
                if (!exclude) {
                    filteredChoices.add(imageChoice);
                }
            }
            this.imageChoices = new RSXImageChoice[filteredChoices.size()];
            this.imageChoices = filteredChoices.toArray(this.imageChoices);
        }
        else {
            this.imageChoices = answerFormat.getImageChoices();
        }


        // Restore results
        currentSelected = new HashSet<>();

        T[] resultArray = result.getResult();
        if(resultArray != null && resultArray.length > 0)
        {
            for(int i=0; i<resultArray.length; i++) {
                currentSelected.add(this.getChoiceForValue(resultArray[i], this.imageChoices));
            }
        }
    }

    @Override
    public int getCount() {
        int length = this.imageChoices.length;
        return length;
    }

    protected RSXMultipleImageSelectionSurveyStep getStep() {
        return this.step;
    }

    @Override
    public Object getItem(int position) {
        return this.imageChoices[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected View configureCellForImageChoice(View missCell, RSXImageChoice<T> imageChoice, ViewGroup parent) {

        ImageView itemImageView = (ImageView) missCell.findViewById(R.id.item_image_view);
        ImageView checkImageView = (ImageView) missCell.findViewById(R.id.check_image_view);
        LinearLayout textContainer = (LinearLayout) missCell.findViewById(R.id.text_container);

        try {
            InputStream inputStream = missCell.getContext().getAssets().open(imageChoice.getNormalStateImage());
            Drawable d = Drawable.createFromStream(inputStream, null);
            itemImageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (this.getSelectedForChoice(imageChoice)) {

            if(this.getStep().getOptions().getItemCellSelectedColor() != 0) {
                int color = this.getStep().getOptions().getItemCellSelectedColor();
                missCell.setBackgroundColor(color);
            }

            if (!TextUtils.isEmpty( this.getStep().getOptions().getItemCellSelectedOverlayImageTitle() )) {
                try {
                    InputStream inputStream = missCell.getContext().getAssets().open(this.getStep().getOptions().getItemCellSelectedOverlayImageTitle());
                    Drawable d = Drawable.createFromStream(inputStream, null);
                    checkImageView.setImageDrawable(d);
                    checkImageView.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            missCell.setBackgroundColor(missCell.getResources().getColor(android.R.color.transparent));
            checkImageView.setVisibility(View.INVISIBLE);
        }

        textContainer.setVisibility(View.GONE);

        return missCell;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FrameLayout cell;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (convertView == null) {
            cell = (FrameLayout) inflater.inflate(
                    R.layout.rsx_multiple_image_selection_survey_cell,
                    parent,
                    false
            );
        }
        else {
            cell = (FrameLayout) convertView;
        }

        return this.configureCellForImageChoice(cell, this.imageChoices[position], parent);
    }

    @Override
    public Set<Choice> getCurrentSelected() {
        return this.currentSelected;
    }

    @Override
    public void clearCurrentSelected() {
        this.currentSelected.clear();
    }

    @Override
    public void setSelectedForChoice(Choice choice, boolean selected) {
        //add or remove from hash based on selected
        if (selected) {
            this.currentSelected.add(choice);
        }
        else {
            this.currentSelected.remove(choice);
        }
    }

    @Override
    public boolean getSelectedForChoice(Choice choice) {
        return this.currentSelected.contains(choice);
    }
}

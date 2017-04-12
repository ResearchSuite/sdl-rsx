package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.model.Choice;
import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.utils.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import edu.cornell.tech.foundry.sdl_rsx.R;
import edu.cornell.tech.foundry.sdl_rsx.answerformat.RSXImageChoiceAnswerFormat;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXMultipleImageSelectionSurveyStep;

/**
 * Created by jk on 6/15/16.
 */
public class CTFTextVSRAssessmentAdaptor<T> extends RSXSurveyAdaptor {

    private Set<Choice> currentSelected;
    private Choice[] textChoices;
    private RSXMultipleImageSelectionSurveyStep step;
    private int cellWidth;

    public CTFTextVSRAssessmentAdaptor(
            RSXMultipleImageSelectionSurveyStep step,
            StepResult<T[]> result) {
        super();
        this.step = step;


        ChoiceAnswerFormat answerFormat = (ChoiceAnswerFormat)this.getStep().getAnswerFormat();

        this.textChoices = answerFormat.getChoices();

        // Restore results
        currentSelected = new HashSet<>();

        T[] resultArray = result.getResult();
        if(resultArray != null && resultArray.length > 0) {
            for (int i = 0; i < resultArray.length; i++) {
                currentSelected.add(this.getChoiceForValue(resultArray[i], this.textChoices));

            }
        }
    }

    @Override
    public int getCount() {
        int length = this.textChoices.length;
        return length;
    }

    protected RSXMultipleImageSelectionSurveyStep getStep() {
        return this.step;
    }

    @Override
    public Object getItem(int position) {
        return this.textChoices[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected View configureCellForImageChoice(View textVSRCell, Choice<T> textChoice, ViewGroup parent) {

        TextView textView = (TextView) textVSRCell.findViewById(R.id.item_text_view);
        ImageView checkImageView = (ImageView) textVSRCell.findViewById(R.id.check_image_view);
        LinearLayout textContainer = (LinearLayout) textVSRCell.findViewById(R.id.text_container);

        textView.setText(textChoice.getText());

//        try {
////            InputStream inputStream = missCell.getContext().getAssets().open(imageChoice.getNormalStateImage());
////            Drawable d = Drawable.createFromStream(inputStream, null);
////            itemImageView.setImageDrawable(d);
//
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        if (this.getSelectedForChoice(textChoice)) {

            if(this.getStep().getOptions().getItemCellSelectedColor() != 0) {
                int color = this.getStep().getOptions().getItemCellSelectedColor();
                textVSRCell.setBackgroundColor(color);
            }

            if (!TextUtils.isEmpty( this.getStep().getOptions().getItemCellSelectedOverlayImageTitle() )) {
                try {
                    InputStream inputStream = textVSRCell.getContext().getAssets().open(this.getStep().getOptions().getItemCellSelectedOverlayImageTitle());
                    Drawable d = Drawable.createFromStream(inputStream, null);
                    checkImageView.setImageDrawable(d);
                    checkImageView.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            textVSRCell.setBackgroundColor(textVSRCell.getResources().getColor(android.R.color.transparent));
            checkImageView.setVisibility(View.INVISIBLE);
        }

        textContainer.setVisibility(View.GONE);

        return textVSRCell;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FrameLayout cell;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (convertView == null) {
            cell = (FrameLayout) inflater.inflate(
                    R.layout.rsx_text_vsr_multiple_selection_survey_cell,
                    parent,
                    false
            );
        }
        else {
            cell = (FrameLayout) convertView;
        }

        return this.configureCellForImageChoice(cell, this.textChoices[position], parent);
    }

    public Set<Choice> getCurrentSelected() {
        return this.currentSelected;
    }

    @Nullable
    @Override
    public Choice getChoiceForValue(Object value, Choice[] choices) {
        return null;
    }

    @Override
    public Object getValueForChoice(Choice choice) {
        return null;
    }

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

    public boolean getSelectedForChoice(Choice choice) {
        return this.currentSelected.contains(choice);
    }

}

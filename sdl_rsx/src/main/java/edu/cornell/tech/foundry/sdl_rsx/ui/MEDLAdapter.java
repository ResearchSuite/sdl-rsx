package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.utils.TextUtils;

import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyResult;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXMultipleImageSelectionSurveyStep;
import edu.cornell.tech.foundry.sdl_rsx.R;

/**
 * Created by jk on 6/23/16.
 */
public class MEDLAdapter <T> extends RSXMultipleImageSelectionSurveyAdapter {

    public MEDLAdapter(
            RSXMultipleImageSelectionSurveyStep step,
            StepResult<RSXMultipleImageSelectionSurveyResult> result) {
        super(step, result);

    }

    @Override
    protected View configureCellForImageChoice(View missCell, RSXImageChoice imageChoice, ViewGroup parent) {

        View cell = super.configureCellForImageChoice(missCell, imageChoice, parent);

        LinearLayout textContainer = (LinearLayout) missCell.findViewById(R.id.text_container);
        TextView primaryTextView = (TextView) missCell.findViewById(R.id.primary_text_label);
        TextView secondaryTextView = (TextView) missCell.findViewById(R.id.secondary_text_label);

        textContainer.setVisibility(View.VISIBLE);
        primaryTextView.setVisibility(View.VISIBLE);
        secondaryTextView.setVisibility(View.VISIBLE);

        if(this.getStep().getOptions().getItemCellTextBackgroundColor() != 0) {
            textContainer.setBackgroundColor(this.getStep().getOptions().getItemCellTextBackgroundColor());
//            primaryTextView.setBackgroundColor(this.getStep().getOptions().getItemCellTextBackgroundColor());
//            secondaryTextView.setBackgroundColor(this.getStep().getOptions().getItemCellTextBackgroundColor());
        }

        if (!TextUtils.isEmpty( imageChoice.getText() )) {
            primaryTextView.setText(imageChoice.getText());
            secondaryTextView.setText(imageChoice.getDetailText());
        }
        else {
            secondaryTextView.setText(imageChoice.getText());
        }

        return cell;
    }
}

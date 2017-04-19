package edu.cornell.tech.foundry.sdl_rsx.ui;

import edu.cornell.tech.foundry.sdl_rsx.choice.RSXTextChoiceWithColor;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXSingleImageClassificationSurveyStep;
import edu.cornell.tech.foundry.sdl_rsx.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;


import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.model.Choice;
import org.researchstack.backbone.ui.step.layout.StepLayout;
import org.researchstack.backbone.utils.ResUtils;
import org.researchstack.backbone.utils.TextUtils;

import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.callbacks.StepCallbacks;
import org.researchstack.backbone.utils.ThemeUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jk on 5/26/16.
 */
public class RSXSingleImageClassificationSurveyLayout extends FrameLayout implements StepLayout {


    public static final String TAG = RSXSingleImageClassificationSurveyLayout.class.getSimpleName();


    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Data used to initializeLayout and return
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private StepResult   stepResult;
    private RSXSingleImageClassificationSurveyStep step;


    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Communicate w/ host
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private StepCallbacks callbacks;

    //Result State
    private Object currentSelected;

    //Getters and Setters
    public Step getStep()
    {
        return this.step;
    }


    //Constructors
    public RSXSingleImageClassificationSurveyLayout(Context context)
    {
        super(context);
    }

    public RSXSingleImageClassificationSurveyLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RSXSingleImageClassificationSurveyLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    //StepLayout Methods

    @Override
    public void initialize(Step step, StepResult result)
    {
        if(! (step instanceof RSXSingleImageClassificationSurveyStep))
        {
            throw new RuntimeException("Step being used in RSXSingleImageClassificationSurveyLayout is not a RSXSingleImageClassificationSurveyStep");
        }

        this.step = (RSXSingleImageClassificationSurveyStep) step;
        this.stepResult = result == null ? new StepResult<>(step) : result;

        this.initializeStep((RSXSingleImageClassificationSurveyStep) step, result);
    }

    @Override
    public View getLayout() {
        return this;
    }

    @Override
    public boolean isBackEventConsumed()
    {
        callbacks.onSaveStep(StepCallbacks.ACTION_PREV, this.getStep(), this.getStepResult(false));
        return false;
    }

    @Override
    public void setCallbacks(StepCallbacks callbacks)
    {
        this.callbacks = callbacks;
    }


    //Init Methods
    public void initializeStep(RSXSingleImageClassificationSurveyStep step, StepResult result)
    {
        this.initStepLayout(step);
    }

    public void initStepLayout(RSXSingleImageClassificationSurveyStep step) {

        // Init root
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.rsx_single_image_classification_survey_layout, this, true);

        ImageView imageView = (ImageView) findViewById(R.id.item_image_view);
        TextView itemDescriptionTextView = (TextView) findViewById(R.id.item_description_text_view);
        TextView questionTextView = (TextView) findViewById(R.id.question_text_view);

        Button skipButton = (Button) findViewById(R.id.skip_button);
        skipButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSkipClicked();
            }
        });

        if(step != null) {
            if (!TextUtils.isEmpty(step.getTitle()) && itemDescriptionTextView!=null) {
                itemDescriptionTextView.setText(step.getTitle());
            }

            if (!TextUtils.isEmpty(step.getText())) {
                questionTextView.setText(step.getText());
            }

            if (step.getImage() != null) {
                try {
                    InputStream inputStream = getContext().getAssets().open(step.getImage());
                    Drawable d = Drawable.createFromStream(inputStream, null);
                    imageView.setImageDrawable(d);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            LinearLayout buttonLayout = (LinearLayout) findViewById(R.id.button_container_view);
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

            //remove all previous buttons
            buttonLayout.removeAllViews();

            ChoiceAnswerFormat format = (ChoiceAnswerFormat) ((RSXSingleImageClassificationSurveyStep)step).getAnswerFormat();
            final Choice<?>[] choices = format.getChoices();

            for(int i=0; i<choices.length; i++) {
                RSXTextChoiceWithColor choice = (RSXTextChoiceWithColor) choices[i];
//                Button button = new AppCompatButton(getContext(), null, R.style.SingleImageClassificationButtonStyle);
                Button button = new AppCompatButton(getContext());
                button.setText(choice.getText());
                button.setId(i);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f);

                int marginInDP = 10;
                int paddingInDP = 20;
                final float scale = getResources().getDisplayMetrics().density;

                int marginInPX = (int)(marginInDP * scale + 0.5f);
                int paddingInPX = (int)(paddingInDP * scale + 0.5f);

                layoutParams.setMargins(marginInPX, 0, marginInPX, 0);
                button.setLayoutParams(layoutParams);
                button.setPadding(0, paddingInPX, 0, paddingInPX);

                GradientDrawable gd = new GradientDrawable();


//                button.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                if(choice.getColor() != 0) {
                    button.setTextColor(choice.getColor());
                    gd.setStroke(2, choice.getColor());
                }
                else {
                    button.setTextColor(getResources().getColor(ThemeUtils.getAccentColor(getContext())));
                    gd.setStroke(2, getResources().getColor(ThemeUtils.getAccentColor(getContext())));
                }

                gd.setCornerRadius(8);
                button.setBackground(gd);

                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Perform action on click
                        int i = v.getId();
                        Choice<?> choice = choices[i];
                        currentSelected = choice.getValue();
                        onNextClicked();
                    }
                });

                buttonLayout.addView(button, i);
            }
        }
    }

    @Override
    public Parcelable onSaveInstanceState()
    {
        callbacks.onSaveStep(StepCallbacks.ACTION_NONE, getStep(), this.getStepResult(false));
        return super.onSaveInstanceState();
    }

    protected void onNextClicked()
    {

        callbacks.onSaveStep(StepCallbacks.ACTION_NEXT,
                this.getStep(),
                this.getStepResult(false));

    }

    public void onSkipClicked()
    {
        if(callbacks != null)
        {
            // empty step result when skipped
            callbacks.onSaveStep(StepCallbacks.ACTION_NEXT,
                    this.getStep(),
                    this.getStepResult(true));
        }
    }

    public String getString(@StringRes int stringResId)
    {
        return getResources().getString(stringResId);
    }


    public StepResult getStepResult(boolean skipped)
    {
        if(skipped)
        {
            stepResult.setResult(null);
        }
        else
        {
            stepResult.setResult(currentSelected);
        }

        return stepResult;
    }

}

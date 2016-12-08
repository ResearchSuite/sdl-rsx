package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.researchstack.backbone.model.Choice;
import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.callbacks.StepCallbacks;
import org.researchstack.backbone.ui.step.body.BodyAnswer;
import org.researchstack.backbone.ui.step.body.StepBody;
import org.researchstack.backbone.ui.step.layout.StepLayout;
import org.researchstack.backbone.ui.step.layout.SurveyStepLayout;
import org.researchstack.backbone.ui.views.SubmitBar;
import org.researchstack.backbone.utils.LogExt;
import org.researchstack.backbone.utils.ResUtils;
import org.researchstack.backbone.utils.TextUtils;

import java.lang.reflect.Constructor;
import java.util.Set;

import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXMultipleImageSelectionSurveyStep;
import edu.cornell.tech.foundry.sdl_rsx.R;

/**
 * Created by jk on 6/2/16.
 */
abstract public class RSXMultipleImageSelectionSurveyLayout extends FrameLayout implements StepLayout {

    public static final String TAG = RSXMultipleImageSelectionSurveyLayout.class.getSimpleName();

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Data used to initializeLayout and return
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private StepResult   stepResult;
    private RSXMultipleImageSelectionSurveyStep step;

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Communicate w/ host
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private StepCallbacks callbacks;

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Child Views
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//    private LinearLayout container;
//    private RSXMultipleImageSelectionSurveyBody stepBody;
    private Button somethingSelectedButton;
    private Button nothingSelectedButton;

    private RSXSurveyAdaptor collectionAdapter;
//    private GridView imagesGridView;


    //Getters and Setters
    public Step getStep()
    {
        return this.step;
    }
    public Class<?> getAdaptorClass() {
        return RSXMultipleImageSelectionSurveyAdapter.class;
    }


    //Constructors
    public RSXMultipleImageSelectionSurveyLayout(Context context)
    {
        super(context);
    }

    public RSXMultipleImageSelectionSurveyLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RSXMultipleImageSelectionSurveyLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    //Step Layout Methods
    @Override
    public void initialize(Step step, StepResult result)
    {
        if(! (step instanceof RSXMultipleImageSelectionSurveyStep))
        {
            throw new RuntimeException("Step being used in RSXMultipleImageSelectionSurveyStep is not a RSXMultipleImageSelectionSurveyStep");
        }

        this.step = (RSXMultipleImageSelectionSurveyStep) step;
        this.stepResult = result == null ? new StepResult<>(step) : result;
        this.initializeStep((RSXMultipleImageSelectionSurveyStep) step, result);
    }

    @Override
    public View getLayout()
    {
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
    public void initializeStep(RSXMultipleImageSelectionSurveyStep step, StepResult result)
    {
        this.initStepLayout(step);
        this.updateUI();
    }

    public void initStepLayout(RSXMultipleImageSelectionSurveyStep step) {

        RSXMultipleImageSelectionSurveyOptions options = step.getOptions();
        final int maximumNumberOfSelectedItems = options.getMaximumSelectedNumberOfItems();


        // Init root
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.rsx_multiple_image_selection_survey, this, true);

        TextView questionTextView = (TextView) findViewById(R.id.question_text_view);
        TextView additionalTextview = (TextView) findViewById(R.id.additional_text_view);

        this.somethingSelectedButton = (Button) findViewById(R.id.something_selected_button);
        this.somethingSelectedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                somethingSelectedButtonPressed();
            }
        });

        this.somethingSelectedButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        if (options.getSomethingSelectedButtonColor() != 0) {
            this.somethingSelectedButton.setTextColor(options.getSomethingSelectedButtonColor());
        }

        this.nothingSelectedButton = (Button) findViewById(R.id.nothing_selected_button);
        this.nothingSelectedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nothingSelectedButtonPressed();
            }
        });
        this.nothingSelectedButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        if (options.getNothingSelectedButtonColor() != 0) {
            this.nothingSelectedButton.setTextColor(options.getNothingSelectedButtonColor());
        }

        if (step != null) {

            String additionalText = this.getAdditionalText();
            if (!TextUtils.isEmpty(additionalText) && additionalTextview != null) {
                additionalTextview.setVisibility(View.VISIBLE);
                additionalTextview.setText(additionalText);
            }
            else {
                additionalTextview.setVisibility(View.GONE);
            }

            String questionText = this.getQuestionText();
            if (!TextUtils.isEmpty(questionText) && questionTextView != null) {
                questionTextView.setText(questionText);
            }

            GridView imagesGridView = (GridView) findViewById(R.id.images_grid_view);
            imagesGridView.setNumColumns(options.getItemsPerRow());
            imagesGridView.setHorizontalSpacing(options.getItemMinSpacing());
            imagesGridView.setVerticalSpacing(options.getItemMinSpacing());

            FrameLayout imagesGridViewContainer = (FrameLayout) findViewById(R.id.images_grid_view_container);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)imagesGridViewContainer.getLayoutParams();
            params.setMargins(options.getItemMinSpacing(), options.getItemMinSpacing(), options.getItemMinSpacing(), options.getItemMinSpacing());
            imagesGridViewContainer.setLayoutParams(params);

            try {
                Class cls = this.getAdaptorClass();
                Constructor constructor = cls.getConstructor(
                        RSXMultipleImageSelectionSurveyStep.class,
                        StepResult.class);

                this.collectionAdapter = (RSXSurveyAdaptor)
                        constructor.newInstance(this.step, this.stepResult);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            if (options.getItemCollectionViewBackgroundColor() != 0) {
                imagesGridViewContainer.setBackgroundColor(options.getItemCollectionViewBackgroundColor());
            }

            imagesGridView.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            imagesGridViewContainer.setVisibility(View.VISIBLE);
            imagesGridView.setVisibility(View.VISIBLE);

            imagesGridView.setAdapter(this.collectionAdapter);
            //set on click listener
            final RSXMultipleImageSelectionSurveyLayout self = this;

            imagesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(
                        AdapterView<?> parent,
                        View v,
                        int position,
                        long id) {

                    Choice choice = (Choice) parent.getItemAtPosition(position);
                    RSXSurveyAdaptor adapter = (RSXSurveyAdaptor) parent.getAdapter();

                    if (self.supportsMultipleSelection()) {
                        if (!adapter.getSelectedForValue(choice.getValue()) &&
                                maximumNumberOfSelectedItems != 0) {
                            if(adapter.getCurrentSelected().size() < maximumNumberOfSelectedItems) {
                                adapter.setSelectedForValue(choice.getValue(), !adapter.getSelectedForValue(choice.getValue()));
                            }
                        }
                        else {
                            adapter.setSelectedForValue(choice.getValue(), !adapter.getSelectedForValue(choice.getValue()));
                        }
                    }
                    else {
                        //multiple selections not allowed
                        boolean currentSelectionSetting = adapter.getSelectedForValue(choice.getValue());
                        adapter.clearCurrentSelected();
                        adapter.setSelectedForValue(choice.getValue(), !currentSelectionSetting);
                    }
                    self.onSelection();
                }
            });
            this.collectionAdapter.notifyDataSetChanged();
        }

    }

    protected void updateUI() {

        Set<?> selectedAnswers = this.getCurrentSelected();
        if (selectedAnswers != null) {
            this.somethingSelectedButton.setText(this.getSomethingSelectedButtonText());
            this.nothingSelectedButton.setText(this.getNothingSelectedButtonText());

            this.somethingSelectedButton.setVisibility((selectedAnswers.size() > 0) ? View.VISIBLE : View.INVISIBLE);
            this.nothingSelectedButton.setVisibility((selectedAnswers.size() > 0) ? View.INVISIBLE : View.VISIBLE);
        }

        this.collectionAdapter.notifyDataSetChanged();

    }

    private void onSelection() {
        if (this.transitionOnSelection()) {
            this.onNextClicked();
        }
        else {
            this.updateUI();
        }
    }

    protected String getQuestionText() {
        return this.step.getTitle();
    }
    protected String getAdditionalText() {
        return "";
    }

    /**
     * Method allowing a step to consume a back event.
     *
     * @return
     */

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

    public String getString(@StringRes int stringResId)
    {
        return getResources().getString(stringResId);
    }

    public StepResult getStepResult(boolean skipped)
    {
        if(skipped)
        {
            this.collectionAdapter.clearCurrentSelected();
            stepResult.setResult( this.collectionAdapter.getCurrentSelected().toArray());
        }
        else
        {
            stepResult.setResult( this.collectionAdapter.getCurrentSelected().toArray());
        }
        return stepResult;
    }

    protected Set<?> getCurrentSelected() {
        return this.collectionAdapter.getCurrentSelected();
    }


    //abstracts
    abstract protected boolean transitionOnSelection();
    abstract protected String getSomethingSelectedButtonText();
    abstract protected String getNothingSelectedButtonText();
    abstract protected void somethingSelectedButtonPressed();
    abstract protected void nothingSelectedButtonPressed();
    abstract protected boolean supportsMultipleSelection();
}

package edu.cornell.tech.foundry.sdl_rsx.step;

import android.content.Context;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;
import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.step.InstructionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.utils.ThemeUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import edu.cornell.tech.foundry.sdl_rsx.R;
import edu.cornell.tech.foundry.sdl_rsx.answerformat.RSXImageChoiceAnswerFormat;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXAffectItem;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXAssessment;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXSummary;
import edu.cornell.tech.foundry.sdl_rsx.ui.PAMLayout;

/**
 * Created by jk on 6/18/16.
 */
public class PAMStep extends RSXMultipleImageSelectionSurveyStep {
    @Override
    public Class getStepLayoutClass()
    {
        return PAMLayout.class;
    }

    @Override
    public Class<?> getStepBodyClass()
    {
        return null;
    }

    public PAMStep(
            String identifier,
            String title,
            AnswerFormat answerFormat,
            RSXMultipleImageSelectionSurveyOptions options
    )
    {
        super(identifier, title, answerFormat, options);
    }

    static RSXMultipleImageSelectionSurveyOptions defaultOptions(Context context) {
        RSXMultipleImageSelectionSurveyOptions options = new RSXMultipleImageSelectionSurveyOptions();
        options.setItemMinSpacing(0);
        options.setItemsPerRow(4);
        options.setSomethingSelectedButtonColor(ThemeUtils.getAccentColor(context));
        options.setNothingSelectedButtonColor(ThemeUtils.getAccentColor(context));
        return options;
    }

    @Nullable
    public static PAMStep create(String identifier, RSXAssessment assessment, Context context) {
        List<Step> steps = new ArrayList<>();

        if (assessment.getItems().isEmpty()) {
            return null;
        }
        else {

            List<RSXImageChoice> choices = new ArrayList();

            for(int i=0; i<assessment.getItems().size(); i++) {
                RSXAffectItem affect = (RSXAffectItem) assessment.getItems().get(i);
                choices.add(affect.getImageChoice());
            }

            AnswerFormat answerFormat = new RSXImageChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.MultipleChoice, choices);

            PAMStep step = new PAMStep(identifier, assessment.getPrompt(), answerFormat, PAMStep.defaultOptions(context));
            return step;

        }
    }

    //context contains the resources for the config file as well as images
    @Nullable
    public static PAMStep create(String identifier, String propertiesFileName, Context context) {

        RSXAssessment assessment = null;
        int ctr;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            InputStream inputStream = context.getAssets().open(propertiesFileName);

            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Parse the data into jsonobject to get original data in form of json.
            JSONObject completeJSON = new JSONObject(
                    byteArrayOutputStream.toString());
            JSONObject assessmentJSON = completeJSON.getJSONObject("PAM");
            JSONArray affectsJSON = assessmentJSON.getJSONArray("affects");

            assessment = new RSXAssessment(assessmentJSON, affectsJSON, context) {
                @Override
                public Class<?> getItemClass() {
                    return RSXAffectItem.class;
                }
            };

        } catch (Exception e) {
            e.printStackTrace();
        }

        return PAMStep.create(identifier, assessment, context);

    }

    @Nullable
    public static PAMStep create(String identifier, Context context) {
        PAMStep step = PAMStep.create(identifier, context.getResources().getString(R.string.pam_json), context);
        return step;
    }
}

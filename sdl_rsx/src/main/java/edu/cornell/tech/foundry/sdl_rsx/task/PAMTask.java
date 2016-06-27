package edu.cornell.tech.foundry.sdl_rsx.task;

import android.content.Context;
import android.content.res.Resources;

import org.json.JSONArray;
import org.json.JSONObject;
import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.step.InstructionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.utils.ResUtils;
import org.researchstack.backbone.utils.ThemeUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import edu.cornell.tech.foundry.sdl_rsx.answerformat.RSXImageChoiceAnswerFormat;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXAffectItem;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXAssessment;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXSummary;
import edu.cornell.tech.foundry.sdl_rsx.step.PAMStep;

/**
 * Created by jk on 6/18/16.
 */
public class PAMTask extends RSXMultipleImageSelectionSurveyTask {

    static RSXMultipleImageSelectionSurveyOptions defaultOptions(Context context) {
        RSXMultipleImageSelectionSurveyOptions options = new RSXMultipleImageSelectionSurveyOptions();
        options.setItemMinSpacing(0);
        options.setItemsPerRow(4);
        options.setSomethingSelectedButtonColor(ThemeUtils.getAccentColor(context));
        options.setNothingSelectedButtonColor(ThemeUtils.getAccentColor(context));
        return options;
    }

    private PAMTask(String identifier, RSXMultipleImageSelectionSurveyOptions options, List<Step> steps) {
        super(identifier, options, steps);
    }

    public static PAMTask create(String identifier, RSXAssessment assessment, Context context) {
        List<Step> steps = new ArrayList<>();

        if (assessment.getItems().isEmpty()) {
            RSXSummary noItemsSummary = assessment.getNoItemsSummary();
            InstructionStep summaryStep = new InstructionStep(
                    noItemsSummary.getIdentifier(),
                    noItemsSummary.getTitle(),
                    noItemsSummary.getText()
            );

            steps.add(summaryStep);
        }
        else {

            List<RSXImageChoice> choices = new ArrayList();

            for(int i=0; i<assessment.getItems().size(); i++) {
                RSXAffectItem activity = (RSXAffectItem) assessment.getItems().get(i);
                choices.add(activity.getImageChoice());
            }

            AnswerFormat answerFormat = new RSXImageChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.MultipleChoice, choices);

            PAMStep pamStep = new PAMStep(assessment.getIdentifier(), assessment.getPrompt(), answerFormat, PAMTask.defaultOptions(context));

            steps.add(pamStep);

            RSXSummary summary = assessment.getSummary();
            InstructionStep summaryStep = new InstructionStep(
                    summary.getIdentifier(),
                    summary.getTitle(),
                    summary.getText()
            );

            steps.add(summaryStep);

        }

        return new PAMTask(identifier, PAMTask.defaultOptions(context), steps);
    }

    //context contains the resources for the config file as well as images
    public static PAMTask create(String identifier, String propertiesFileName, Context context) {

        Resources r = context.getResources();

        RSXAssessment assessment = null;
        //Get Data From Text Resource File Contains Json Data.
        int resourceID = ResUtils.getRawResourceId(context, propertiesFileName);
        InputStream inputStream = r.openRawResource(resourceID);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
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

            assessment = new RSXAssessment(assessmentJSON, affectsJSON) {
                @Override
                public Class<?> getItemClass() {
                    return RSXAffectItem.class;
                }
            };

        } catch (Exception e) {
            e.printStackTrace();
        }

        return PAMTask.create(identifier, assessment, context);

    }


}

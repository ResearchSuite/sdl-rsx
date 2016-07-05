package edu.cornell.tech.foundry.sdl_rsx.task;

import android.content.Context;
import android.content.res.Resources;

import org.json.JSONArray;
import org.json.JSONObject;
import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.step.InstructionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.utils.ResUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.cornell.tech.foundry.sdl_rsx.answerformat.RSXImageChoiceAnswerFormat;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXActivityItem;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXSummary;
import edu.cornell.tech.foundry.sdl_rsx.model.YADLSpotAssessment;
import edu.cornell.tech.foundry.sdl_rsx.step.YADLSpotAssessmentStep;

/**
 * Created by jk on 6/1/16.
 */
public class YADLSpotAssessmentTask extends RSXMultipleImageSelectionSurveyTask {

    private YADLSpotAssessmentTask(String identifier, RSXMultipleImageSelectionSurveyOptions options, List<Step> steps) {
        super(identifier, options, steps);
    }

    public static YADLSpotAssessmentTask create(String identifier, YADLSpotAssessment assessment, Set<String> activityIdentifiers) {
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
                RSXActivityItem activity = (RSXActivityItem) assessment.getItems().get(i);
                //if activityIdentifiers not specified, include all activities,
                //otherwise, include only activities that are in the list of activityIdentifiers
                if (activityIdentifiers == null || activityIdentifiers.contains(activity.getIdentifier())) {
                    choices.add(activity.getImageChoice());
                }
            }

            AnswerFormat answerFormat = new RSXImageChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.MultipleChoice, choices);

            YADLSpotAssessmentStep spotAssessmentStep = new YADLSpotAssessmentStep(assessment.getIdentifier(), assessment.getPrompt(), answerFormat, assessment.getOptions());

            steps.add(spotAssessmentStep);

            RSXSummary summary = assessment.getSummary();
            InstructionStep summaryStep = new InstructionStep(
                    summary.getIdentifier(),
                    summary.getTitle(),
                    summary.getText()
            );

            steps.add(summaryStep);

        }

        return new YADLSpotAssessmentTask(identifier, assessment.getOptions(), steps);
    }

    //context contains the resources for the config file as well as images
    public static YADLSpotAssessmentTask create(String identifier,  String propertiesFileName, Context context, Set<String> activityIdentifiers) {

        YADLSpotAssessment assessment = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
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
            JSONObject typeJSON = completeJSON.getJSONObject("YADL");
            JSONObject assessmentJSON = typeJSON.getJSONObject("spot");
            JSONArray activitiesJSON = typeJSON.getJSONArray("activities");

            assessment = new YADLSpotAssessment(assessmentJSON, activitiesJSON, context);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return YADLSpotAssessmentTask.create(identifier, assessment, activityIdentifiers);

    }

    public static YADLSpotAssessmentTask create(String identifier,  String propertiesFileName, Context context) {
        return YADLSpotAssessmentTask.create(identifier, propertiesFileName, context, null);
    }

}

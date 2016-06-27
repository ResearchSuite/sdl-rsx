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
import edu.cornell.tech.foundry.sdl_rsx.model.MEDLSpotAssessment;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXActivityItem;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXCopingMechanismItem;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXSummary;
import edu.cornell.tech.foundry.sdl_rsx.model.YADLSpotAssessment;
import edu.cornell.tech.foundry.sdl_rsx.step.MEDLSpotAssessmentStep;
import edu.cornell.tech.foundry.sdl_rsx.step.YADLSpotAssessmentStep;

/**
 * Created by jk on 6/23/16.
 */
public class MEDLSpotAssessmentTask extends RSXMultipleImageSelectionSurveyTask {
    private MEDLSpotAssessmentTask(String identifier, RSXMultipleImageSelectionSurveyOptions options, List<Step> steps) {
        super(identifier, options, steps);
    }

    public static MEDLSpotAssessmentTask create(String identifier, MEDLSpotAssessment assessment, Set<String> itemIdentifiers) {
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
                RSXCopingMechanismItem item = (RSXCopingMechanismItem) assessment.getItems().get(i);
                //if itemIdentifiers not specified, include all activities,
                //otherwise, include only activities that are in the list of activityIdentifiers
                if (itemIdentifiers == null || itemIdentifiers.contains(item.getIdentifier())) {
                    choices.add(item.getImageChoice());
                }
            }

            AnswerFormat answerFormat = new RSXImageChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.MultipleChoice, choices);

            MEDLSpotAssessmentStep spotAssessmentStep = new MEDLSpotAssessmentStep(assessment.getIdentifier(), assessment.getPrompt(), answerFormat, assessment.getOptions());

            steps.add(spotAssessmentStep);

            RSXSummary summary = assessment.getSummary();
            InstructionStep summaryStep = new InstructionStep(
                    summary.getIdentifier(),
                    summary.getTitle(),
                    summary.getText()
            );

            steps.add(summaryStep);

        }

        return new MEDLSpotAssessmentTask(identifier, assessment.getOptions(), steps);
    }

    //context contains the resources for the config file as well as images
    public static MEDLSpotAssessmentTask create(String identifier, String propertiesFileName, Context context, Set<String> itemIdentifiers) {

        Resources r = context.getResources();

        MEDLSpotAssessment assessment = null;
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
            JSONObject typeJSON = completeJSON.getJSONObject("MEDL");
            JSONObject assessmentJSON = typeJSON.getJSONObject("spot");
            JSONArray itemsJSON = typeJSON.getJSONArray("medications");

            assessment = new MEDLSpotAssessment(assessmentJSON, itemsJSON);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return MEDLSpotAssessmentTask.create(identifier, assessment, itemIdentifiers);

    }

    public static MEDLSpotAssessmentTask create(String identifier,  String propertiesFileName, Context context) {
        return MEDLSpotAssessmentTask.create(identifier, propertiesFileName, context, null);
    }

}

package edu.cornell.tech.foundry.sdl_rsx.task;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.model.Choice;
import org.researchstack.backbone.step.InstructionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.utils.ResUtils;

import edu.cornell.tech.foundry.sdl_rsx.choice.RSXTextChoiceWithColor;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXActivityItem;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXSummary;
import edu.cornell.tech.foundry.sdl_rsx.model.YADLFullAssessment;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXSingleImageClassificationSurveyStep;

/**
 * Created by jk on 5/31/16.
 */
public class YADLFullAssessmentTask extends RSXSingleImageClassificationSurveyTask {

    private YADLFullAssessmentTask(String identifier, List<Step> steps) {
        super(identifier, steps);
    }

    public static YADLFullAssessmentTask create(String identifier, YADLFullAssessment assessment) {
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
            RSXTextChoiceWithColor[] choices = new RSXTextChoiceWithColor[assessment.getChoices().size()];
            assessment.getChoices().toArray(choices);

            AnswerFormat answerFormat = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.SingleChoice, choices);

            for(int i=0; i<assessment.getItems().size(); i++) {

                RSXActivityItem activity = (RSXActivityItem) assessment.getItems().get(i);

                RSXSingleImageClassificationSurveyStep yadlFullStep = new RSXSingleImageClassificationSurveyStep(
                        activity.getIdentifier(),
                        activity.getActivityDescription(),
                        assessment.getPrompt(),
                        activity.getImageTitle(),
                        answerFormat
                );

                steps.add(yadlFullStep);
            }

            RSXSummary summary = assessment.getSummary();
            InstructionStep summaryStep = new InstructionStep(
                    summary.getIdentifier(),
                    summary.getTitle(),
                    summary.getText()
            );

            steps.add(summaryStep);

        }

        return new YADLFullAssessmentTask(identifier, steps);
    }

    //context contains the resources for the config file as well as images
    public static YADLFullAssessmentTask create(String identifier,  String propertiesFileName, Context context) {

        YADLFullAssessment assessment = null;
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
            JSONObject assessmentJSON = typeJSON.getJSONObject("full");
            JSONArray activitiesJSON = typeJSON.getJSONArray("activities");

            assessment = new YADLFullAssessment(assessmentJSON, activitiesJSON, context);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return YADLFullAssessmentTask.create(identifier, assessment);

    }

}

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cornell.tech.foundry.sdl_rsx.answerformat.RSXImageChoiceAnswerFormat;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.model.MEDLFullAssessment;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXAffectItem;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXAssessment;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXCopingMechanismItem;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXSummary;
import edu.cornell.tech.foundry.sdl_rsx.step.MEDLFullAssessmentStep;
import edu.cornell.tech.foundry.sdl_rsx.step.PAMStep;

/**
 * Created by jk on 6/23/16.
 */
public class MEDLFullAssessmentTask extends RSXMultipleImageSelectionSurveyTask {
    private MEDLFullAssessmentTask(String identifier, RSXMultipleImageSelectionSurveyOptions options, List<Step> steps) {
        super(identifier, options, steps);
    }

    public static MEDLFullAssessmentTask create(String identifier, MEDLFullAssessment assessment) {
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

            Map<String, List<RSXCopingMechanismItem>> categoryGroups = new HashMap();
            for(int i=0; i<assessment.getItems().size(); i++) {
                RSXCopingMechanismItem item = (RSXCopingMechanismItem) assessment.getItems().get(i);
                String category = item.getCategory();
                if(!categoryGroups.containsKey(category)) {
                    List<RSXCopingMechanismItem> items = new ArrayList();
                    items.add(item);
                    categoryGroups.put(category, items);
                }
                else {
                    categoryGroups.get(category).add(item);
                }
            }

            for (Map.Entry<String, List<RSXCopingMechanismItem>> entry : categoryGroups.entrySet())
            {
                List<RSXImageChoice> choices = new ArrayList();
                String category = entry.getKey();
                for(int i=0; i<entry.getValue().size(); i++) {
                    RSXCopingMechanismItem item = entry.getValue().get(i);
                    choices.add(item.getImageChoice());
                }

                AnswerFormat answerFormat = new RSXImageChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.MultipleChoice, choices);

                MEDLFullAssessmentStep medlFullStep = new MEDLFullAssessmentStep(category, assessment.getPrompt(), category, answerFormat, assessment.getOptions());
                steps.add(medlFullStep);
            }

            RSXSummary summary = assessment.getSummary();
            InstructionStep summaryStep = new InstructionStep(
                    summary.getIdentifier(),
                    summary.getTitle(),
                    summary.getText()
            );

            steps.add(summaryStep);

        }

        return new MEDLFullAssessmentTask(identifier, assessment.getOptions(), steps);
    }

    //context contains the resources for the config file as well as images
    public static MEDLFullAssessmentTask create(String identifier, String propertiesFileName, Context context) {

        MEDLFullAssessment assessment = null;
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
            JSONObject typeJSON = completeJSON.getJSONObject("MEDL");
            JSONObject assessmentJSON = typeJSON.getJSONObject("full");
            JSONArray copingMechanismsJSON = typeJSON.getJSONArray("medications");

            assessment = new MEDLFullAssessment(assessmentJSON, copingMechanismsJSON, context);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return MEDLFullAssessmentTask.create(identifier, assessment);

    }
}

package edu.cornell.tech.foundry.sdl_rsx_rstbsupport;

import android.graphics.Color;

import com.google.gson.JsonObject;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.utils.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.RSTBBaseStepGenerator;
import edu.cornell.tech.foundry.researchsuitetaskbuilder.RSTBTaskBuilderHelper;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXTextChoiceWithColor;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXSingleImageClassificationSurveyStep;

/**
 * Created by jameskizer on 4/13/17.
 */

public class YADLFullStepGenerator extends RSTBBaseStepGenerator {
    public YADLFullStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "YADLFullAssessment"
        );
    }

    protected String generateImagePath(YADLFullStepDescriptor yadlFullStepDescriptor, YADLItemDescriptor itemDescriptor) {
        StringBuilder imageTitleStringBuilder = new StringBuilder();

        if (!TextUtils.isEmpty(yadlFullStepDescriptor.imagePath)) {
            imageTitleStringBuilder.append(yadlFullStepDescriptor.imagePath);
            imageTitleStringBuilder.append("/");
        }

        imageTitleStringBuilder.append(itemDescriptor.imageTitle);

        if (!TextUtils.isEmpty(yadlFullStepDescriptor.imageExtension)) {
            imageTitleStringBuilder.append(".");
            imageTitleStringBuilder.append(yadlFullStepDescriptor.imageExtension);
        }

        return imageTitleStringBuilder.toString();
    }

    @Override
    public List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        YADLFullStepDescriptor yadlFullStepDescriptor = helper.getGson().fromJson(jsonObject, YADLFullStepDescriptor.class);

        if (yadlFullStepDescriptor == null) {
            return null;
        }

        RSXTextChoiceWithColor[] choices = new RSXTextChoiceWithColor[yadlFullStepDescriptor.choices.size()];
        for(int i=0; i<yadlFullStepDescriptor.choices.size(); i++) {
            YADLDifficultyChoiceDescriptor choice = yadlFullStepDescriptor.choices.get(i);
            choices[i] = new RSXTextChoiceWithColor(
                    choice.text,
                    choice.value,
                    Color.parseColor(choice.color)
            );
        }
        AnswerFormat answerFormat = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.SingleChoice, choices);

        List<Step> yadlFullSteps = new ArrayList<>();
        for ( YADLItemDescriptor item : yadlFullStepDescriptor.items ) {

            //does imageTitle need to be full path?
            String imagePath = generateImagePath(yadlFullStepDescriptor, item);
            StringBuilder identifierBuilder = new StringBuilder(yadlFullStepDescriptor.identifier);
            identifierBuilder.append(".");
            identifierBuilder.append(item.identifier);

            RSXSingleImageClassificationSurveyStep yadlFullStep = new RSXSingleImageClassificationSurveyStep(
                    identifierBuilder.toString(),
                    item.description,
                    yadlFullStepDescriptor.text,
                    imagePath,
                    answerFormat
            );

            yadlFullSteps.add(yadlFullStep);

        }

        return yadlFullSteps;
    }
}

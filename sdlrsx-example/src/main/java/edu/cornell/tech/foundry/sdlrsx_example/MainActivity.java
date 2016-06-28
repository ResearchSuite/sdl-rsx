package edu.cornell.tech.foundry.sdlrsx_example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.researchstack.backbone.StorageAccess;
import org.researchstack.backbone.answerformat.TextAnswerFormat;
import org.researchstack.backbone.model.ConsentDocument;
import org.researchstack.backbone.model.ConsentSection;
import org.researchstack.backbone.model.ConsentSignature;
import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.result.TaskResult;
import org.researchstack.backbone.step.ConsentDocumentStep;
import org.researchstack.backbone.step.ConsentSignatureStep;
import org.researchstack.backbone.step.ConsentVisualStep;
import org.researchstack.backbone.step.FormStep;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.task.OrderedTask;
import org.researchstack.backbone.task.Task;
import org.researchstack.backbone.ui.PinCodeActivity;
import org.researchstack.backbone.ui.ViewTaskActivity;
import org.researchstack.backbone.ui.step.layout.ConsentSignatureStepLayout;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.cornell.tech.foundry.sdl_rsx.task.MEDLFullAssessmentTask;
import edu.cornell.tech.foundry.sdl_rsx.task.MEDLSpotAssessmentTask;
import edu.cornell.tech.foundry.sdl_rsx.task.PAMTask;
import edu.cornell.tech.foundry.sdl_rsx.task.YADLFullAssessmentTask;
import edu.cornell.tech.foundry.sdl_rsx.task.YADLSpotAssessmentTask;

public class MainActivity extends PinCodeActivity {

    public static final String PREFS_NAME = "MyPrefsFile";

    private static final String LOG_TAG = "SDL-RSX";

    // Activity Request Codes
    private static final int REQUEST_YADL_FULL  = 2;
    private static final int REQUEST_YADL_SPOT  = 3;
    private static final int REQUEST_PAM  = 4;
    private static final int REQUEST_MEDL_FULL  = 5;
    private static final int REQUEST_MEDL_SPOT  = 6;


    public static final  String YADL_FULL_ASSESSMENT             = "yadl_full_assessment";
    public static final  String YADL_SPOT_ASSESSMENT             = "yadl_spot_assessment";
    public static final  String PAM_ASSESSMENT             = "pam_assessment";
    public static final  String MEDL_FULL_ASSESSMENT             = "medl_full_assessment";
    public static final  String MEDL_SPOT_ASSESSMENT             = "medl_spot_assessment";

//    public static final  String CONSENT                   = "consent";
//    public static final  String CONSENT_DOC               = "consent_doc";
//    public static final  String VISUAL_CONSENT_IDENTIFIER = "visual_consent_identifier";
//    public static final  String SIGNATURE_FORM_STEP       = "form_step";
//    public static final  String NAME                      = "name";
//    public static final  String SIGNATURE                 = "signature";

    // Views
//    private AppCompatButton consentButton;
    private AppCompatButton yadlFullButton;
    private AppCompatButton yadlSpotButton;
    private AppCompatButton medlFullButton;
    private AppCompatButton medlSpotButton;
    private AppCompatButton pamButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        consentButton = (AppCompatButton) findViewById(R.id.consent_button);
//        consentButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                launchConsent();
//            }
//        });

        yadlFullButton = (AppCompatButton) findViewById(R.id.yadl_full_button);
        yadlFullButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchYADLFull();
            }
        });

        yadlSpotButton = (AppCompatButton) findViewById(R.id.yadl_spot_button);
        yadlSpotButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchYADLSpot();
            }
        });

        medlFullButton = (AppCompatButton) findViewById(R.id.medl_full_button);
        medlFullButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchMEDLFull();
            }
        });

        medlSpotButton = (AppCompatButton) findViewById(R.id.medl_spot_button);
        medlSpotButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchMEDLSpot();
            }
        });

        pamButton = (AppCompatButton) findViewById(R.id.pam_button);
        pamButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchPAM();
            }
        });

    }

    // Consent stuff

//    private void launchConsent()
//    {
//        ConsentDocument document = new ConsentDocument();
//        document.setTitle("Demo Consent");
//        document.setSignaturePageTitle(R.string.rsb_consent);
//
//        // Create consent visual sections
//        ConsentSection section1 = new ConsentSection(ConsentSection.Type.DataGathering);
//        section1.setTitle("The title of the section goes here ...");
//        section1.setSummary("The summary about the section goes here ...");
//        section1.setContent("The content to show in learn more ...");
//
//        // ...add more sections as needed, then create a visual consent step
//        ConsentVisualStep visualStep = new ConsentVisualStep(VISUAL_CONSENT_IDENTIFIER);
//        visualStep.setStepTitle(R.string.rsb_consent);
//        visualStep.setSection(section1);
//        visualStep.setNextButtonString(getString(R.string.rsb_next));
//
//        // Create consent signature object and set what info is required
//        ConsentSignature signature = new ConsentSignature();
//        signature.setRequiresName(true);
//        signature.setRequiresSignatureImage(true);
//
//        // Create our HTML to show the user and have them accept or decline.
//        StringBuilder docBuilder = new StringBuilder(
//                "</br><div style=\"padding: 10px 10px 10px 10px;\" class='header'>");
//        String title = getString(R.string.rsb_consent_review_title);
//        docBuilder.append(String.format(
//                "<h1 style=\"text-align: center; font-family:sans-serif-light;\">%1$s</h1>",
//                title));
//        String detail = getString(R.string.rsb_consent_review_instruction);
//        docBuilder.append(String.format("<p style=\"text-align: center\">%1$s</p>", detail));
//        docBuilder.append("</div></br>");
//        docBuilder.append("<div><h2> HTML Consent Doc goes here </h2></div>");
//
//        // Create the Consent doc step, pass in our HTML doc
//        ConsentDocumentStep documentStep = new ConsentDocumentStep(CONSENT_DOC);
//        documentStep.setConsentHTML(docBuilder.toString());
//        documentStep.setConfirmMessage(getString(R.string.rsb_consent_review_reason));
//
//        // Create Consent form step, to get users first & last name
//        FormStep formStep = new FormStep(SIGNATURE_FORM_STEP,
//                "Form Title",
//                "Form step description");
//        formStep.setStepTitle(R.string.rsb_consent);
//
//        TextAnswerFormat format = new TextAnswerFormat();
//        format.setIsMultipleLines(false);
//
//        QuestionStep fullName = new QuestionStep(NAME, "Full name", format);
//        formStep.setFormSteps(Collections.singletonList(fullName));
//
//        // Create Consent signature step, user can sign their name
//        ConsentSignatureStep signatureStep = new ConsentSignatureStep(SIGNATURE);
//        signatureStep.setStepTitle(R.string.rsb_consent);
//        signatureStep.setTitle(getString(R.string.rsb_consent_signature_title));
//        signatureStep.setText(getString(R.string.rsb_consent_signature_instruction));
//        signatureStep.setSignatureDateFormat(signature.getSignatureDateFormatString());
//        signatureStep.setOptional(false);
//        signatureStep.setStepLayoutClass(ConsentSignatureStepLayout.class);
//
//        // Finally, create and present a task including these steps.
//        Task consentTask = new OrderedTask(CONSENT,
//                visualStep,
//                documentStep,
//                formStep,
//                signatureStep);
//
//        // Launch using hte ViewTaskActivity and make sure to listen for the activity result
//        Intent intent = ViewTaskActivity.newIntent(this, consentTask);
//        startActivityForResult(intent, REQUEST_CONSENT);
//    }

//    private void processConsentResult(TaskResult result)
//    {
//        boolean consented = (boolean) result.getStepResult(CONSENT_DOC).getResult();
//
//        if(consented)
//        {
//            StorageAccess.getInstance().getAppDatabase().saveTaskResult(result);
//
//            AppPrefs prefs = AppPrefs.getInstance(this);
//            prefs.setHasConsented(true);
//
//            initViews();
//        }
//    }

//    @Override
//    public void onDataReady()
//    {
//        super.onDataReady();
//        initViews();
//    }

//    private void initViews()
//    {
//        AppPrefs prefs = AppPrefs.getInstance(this);
//
//        View lblConsentedDate = findViewById(R.id.consented_date_lbl);
//        TextView consentedDate = (TextView)findViewById(R.id.consented_date);
//        ImageView consentedSig = (ImageView) findViewById(R.id.consented_signature);
//
//
//        if(prefs.hasConsented())
//        {
//            consentButton.setVisibility(View.GONE);
//            Boolean enabled = true;
//            yadlFullButton.setEnabled(enabled);
//            yadlSpotButton.setEnabled(enabled);
//            medlFullButton.setEnabled(enabled);
//            medlSpotButton.setEnabled(enabled);
//            pamButton.setEnabled(enabled);
//
//            consentedSig.setVisibility(View.VISIBLE);
//            consentedDate.setVisibility(View.VISIBLE);
//            lblConsentedDate.setVisibility(View.VISIBLE);
//
//        }
//        else
//        {
//            consentButton.setVisibility(View.VISIBLE);
//            Boolean enabled = true;
//            yadlFullButton.setEnabled(enabled);
//            yadlSpotButton.setEnabled(enabled);
//            medlFullButton.setEnabled(enabled);
//            medlSpotButton.setEnabled(enabled);
//            pamButton.setEnabled(enabled);
//
//            consentedSig.setVisibility(View.INVISIBLE);
//            consentedSig.setImageBitmap(null);
//            consentedDate.setVisibility(View.INVISIBLE);
//            lblConsentedDate.setVisibility(View.INVISIBLE);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

//        if(requestCode == REQUEST_CONSENT && resultCode == RESULT_OK)
//        {
//            processConsentResult((TaskResult) data.getSerializableExtra(ViewTaskActivity.EXTRA_TASK_RESULT));
//        }
//        else
        if(requestCode == REQUEST_YADL_FULL && resultCode == RESULT_OK) {
            Log.i(LOG_TAG, "YADL FULL FINISHED");

            processYADLFullResult((TaskResult) data.getSerializableExtra(ViewTaskActivity.EXTRA_TASK_RESULT));
        }
        else if(requestCode == REQUEST_YADL_SPOT && resultCode == RESULT_OK) {
            Log.i(LOG_TAG, "YADL SPOT FINISHED");
        }
        else if(requestCode == REQUEST_PAM && resultCode == RESULT_OK) {
            Log.i(LOG_TAG, "PAM FINISHED");
            processPAMResult((TaskResult) data.getSerializableExtra(ViewTaskActivity.EXTRA_TASK_RESULT));
        }
        else if(requestCode == REQUEST_MEDL_FULL && resultCode == RESULT_OK) {
            Log.i(LOG_TAG, "MEDL FULL FINISHED");

            processMEDLFullResult((TaskResult) data.getSerializableExtra(ViewTaskActivity.EXTRA_TASK_RESULT));
        }
        else if(requestCode == REQUEST_MEDL_SPOT && resultCode == RESULT_OK) {
            Log.i(LOG_TAG, "MEDL SPOT FINISHED");
        }

    }

    private void processYADLFullResult(TaskResult result)
    {
        StorageAccess.getInstance().getAppDatabase().saveTaskResult(result);

        Set<String> activities = new HashSet<String>();
        for(String id : result.getResults().keySet())
        {
            StepResult stepResult = result.getStepResult(id);
            if (stepResult != null && stepResult.getResults() != null && stepResult.getResults().get("answer") != null) {
                String answer = (String) stepResult.getResults().get("answer");
                if (answer.equals("hard") || answer.equals("moderate" )) {
                    activities.add(id);
                }
            }
        }

        AppPrefs prefs = AppPrefs.getInstance(this);
        prefs.setYADLActivities(activities);

    }

    private void processMEDLFullResult(TaskResult result)
    {
        StorageAccess.getInstance().getAppDatabase().saveTaskResult(result);

        Set<String> items = new HashSet<String>();
        for(String id : result.getResults().keySet())
        {
            StepResult stepResult = result.getStepResult(id);
            if (stepResult != null && stepResult.getResults() != null && stepResult.getResults().get("answer") != null) {
                Object[] answerItems = (Object[]) stepResult.getResults().get("answer");
                for(int i=0; i<answerItems.length; i++) {
                    items.add((String)answerItems[i]);
                }
            }
        }

        AppPrefs prefs = AppPrefs.getInstance(this);
        prefs.setMEDLItems(items);

    }

    private void processPAMResult(TaskResult result)
    {
        Log.i(LOG_TAG, result.toString());
    }


    private void launchYADLFull()
    {
        Log.i(LOG_TAG, "Launching YADL Full Assessment");

        OrderedTask task = YADLFullAssessmentTask.create(YADL_FULL_ASSESSMENT, "yadl", this);

        // Create an activity using the task and set a delegate.
        Intent intent = ViewTaskActivity.newIntent(this, task);
        startActivityForResult(intent, REQUEST_YADL_FULL);

    }

    private void launchYADLSpot()
    {
        Log.i(LOG_TAG, "Launching YADL Spot Assessment");

        AppPrefs prefs = AppPrefs.getInstance(this);

        Set<String> selectedActivies = prefs.getYADLActivities();

        OrderedTask task = YADLSpotAssessmentTask.create(YADL_SPOT_ASSESSMENT, "yadl", this, selectedActivies);

        // Create an activity using the task and set a delegate.
        Intent intent = ViewTaskActivity.newIntent(this, task);
        startActivityForResult(intent, REQUEST_YADL_SPOT);

    }

    private void launchMEDLFull()
    {
        Log.i(LOG_TAG, "Launching MEDL Full Assessment");
        OrderedTask task = MEDLFullAssessmentTask.create(MEDL_FULL_ASSESSMENT, "medl", this);

        // Create an activity using the task and set a delegate.
        Intent intent = ViewTaskActivity.newIntent(this, task);
        startActivityForResult(intent, REQUEST_MEDL_FULL);

    }

    private void launchMEDLSpot() {
        Log.i(LOG_TAG, "Launching MEDL Spot Assessment");

        AppPrefs prefs = AppPrefs.getInstance(this);

        Set<String> selectedItems = prefs.getMEDLItems();

        OrderedTask task = MEDLSpotAssessmentTask.create(MEDL_SPOT_ASSESSMENT, "medl", this, selectedItems);

        // Create an activity using the task and set a delegate.
        Intent intent = ViewTaskActivity.newIntent(this, task);
        startActivityForResult(intent, REQUEST_MEDL_SPOT);

    }

    private void launchPAM()
    {
        Log.i(LOG_TAG, "Launching PAM");

        OrderedTask task = PAMTask.create(PAM_ASSESSMENT, "pam", this);

        // Create an activity using the task and set a delegate.
        Intent intent = ViewTaskActivity.newIntent(this, task);
        startActivityForResult(intent, REQUEST_PAM);

    }
}

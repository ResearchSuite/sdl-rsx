package edu.cornell.tech.foundry.sdlrsx_example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;

import org.researchstack.backbone.StorageAccess;
import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.result.TaskResult;
import org.researchstack.backbone.task.OrderedTask;
import org.researchstack.backbone.ui.ViewTaskActivity;

import java.util.HashSet;
import java.util.Set;

import edu.cornell.tech.foundry.sdl_rsx.task.MEDLFullAssessmentTask;
import edu.cornell.tech.foundry.sdl_rsx.task.MEDLSpotAssessmentTask;
import edu.cornell.tech.foundry.sdl_rsx.task.PAMTask;
import edu.cornell.tech.foundry.sdl_rsx.task.YADLFullAssessmentTask;
import edu.cornell.tech.foundry.sdl_rsx.task.YADLSpotAssessmentTask;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";

    private static final String LOG_TAG = "SDL-RSX";

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

    private AppCompatButton yadlFullButton;
    private AppCompatButton yadlSpotButton;
    private AppCompatButton medlFullButton;
    private AppCompatButton medlSpotButton;
    private AppCompatButton pamButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

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

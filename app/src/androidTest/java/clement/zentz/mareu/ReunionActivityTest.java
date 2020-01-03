package clement.zentz.mareu;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import clement.zentz.mareu.utils.DeleteViewAction;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static clement.zentz.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ReunionActivityTest {

    private static int ITEM_COUNT = 10;

    @Rule
    public ActivityTestRule<ReunionActivity> mTestRule = new ActivityTestRule<>(ReunionActivity.class);

   @Test
    public void reunionRecyclerViewList_shouldNotBeEmpty(){
        onView(withId(R.id.recyclerView)).check(matches(hasMinimumChildCount(1)));
   }

   @Test
    public void reunionRV_clickOnItem_shouldDisplayCorrectReunionDatas(){
       onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
       onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
       onView(withId(R.id.manageReu_container)).check(matches(isDisplayed()));
       onView(withId(R.id.sujetReunion_edt)).check(matches(withText("AWS")));
       onView(withId(R.id.emailReunion_edt)).check(matches(withText("andrea@gmail.com")));
   }

    @Test
    public void reunionRV_deleteReunion_shouldRemoveOneItem(){
        onView(ViewMatchers.withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(9, new DeleteViewAction()));
        onView(withId(R.id.recyclerView)).check(withItemCount(ITEM_COUNT -1));
        ITEM_COUNT--;
    }

    @Test
    public void reunionActivity_addNewReunion_shouldAddOneReunion(){
        onView(ViewMatchers.withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).check(withItemCount(ITEM_COUNT));
        onView(withId(R.id.addReu_fab)).perform(ViewActions.click());
        onView(withId(R.id.manageReu_container)).check(matches(isDisplayed()));
        onView(withId(R.id.sujetReunion_edt)).perform(typeText("React"));
        onView(withId(R.id.emailReunion_edt)).perform(typeText("curie@gmail.com"));
        onView(withId(R.id.lieuReunion_spinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.lieuReunion_spinner)).check(matches(withSpinnerText(containsString("Laboratoire"))));
        onView(withId(R.id.addReu_btn)).perform(click());
        onView(withId(R.id.recyclerView)).check(withItemCount(ITEM_COUNT+1));
        ITEM_COUNT++;
    }

   @Test
   public void reunionActivity_UpdateOldReunion_shouldUpdateData(){
       onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
       onView(ViewMatchers.withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(9, click()));
       onView(withId(R.id.sujetReunion_edt)).perform(clearText());
       onView(withId(R.id.sujetReunion_edt)).perform(typeText("Android"));
       closeSoftKeyboard();
       onView(withId(R.id.updateReu_btn)).perform(ViewActions.click());
       onView(ViewMatchers.withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(9, click()));
       onView(withId(R.id.sujetReunion_edt)).check(matches(withText("Android")));
   }
}
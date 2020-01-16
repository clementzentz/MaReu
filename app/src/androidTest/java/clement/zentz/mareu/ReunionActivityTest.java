package clement.zentz.mareu;

import android.view.KeyEvent;
import android.widget.EditText;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import clement.zentz.mareu.utils.DeleteViewAction;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static clement.zentz.mareu.utils.RecyclerViewAtPositionOnView.recyclerViewAtPositionOnView;
import static clement.zentz.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static clement.zentz.mareu.utils.RecyclerViewSizeMatcher.recyclerViewSizeMatcher;
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
       onView(isRoot()).perform(pressBack());
   }

    @Test
    public void reunionRV_deleteReunion_shouldRemoveOneItem(){
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(9, new DeleteViewAction()));
        onView(withId(R.id.recyclerView)).check(matches(recyclerViewSizeMatcher(ITEM_COUNT -1)));
        ITEM_COUNT--;
    }

    @Test
    public void reunionActivity_addNewReunion_shouldAddOneReunion(){
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).check(withItemCount(ITEM_COUNT));
        onView(withId(R.id.addReu_fab)).perform(click());
        onView(withId(R.id.manageReu_container)).check(matches(isDisplayed()));
        onView(withId(R.id.sujetReunion_edt)).perform(typeText("React"));
        onView(withId(R.id.emailReunion_edt)).perform(typeText("curie@gmail.com"));
        onView(withId(R.id.lieuReunion_spinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.lieuReunion_spinner)).check(matches(withSpinnerText(containsString("Laboratoire"))));
        onView(withId(R.id.addReu_btn)).perform(click());
        onView(withId(R.id.recyclerView)).check(matches(recyclerViewSizeMatcher(ITEM_COUNT+1)));
        ITEM_COUNT++;
    }

   @Test
   public void reunionActivity_UpdateOldReunion_shouldUpdateData(){
       onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
       onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(9, click()));
       onView(withId(R.id.sujetReunion_edt)).perform(clearText());
       onView(withId(R.id.sujetReunion_edt)).perform(typeText("ReactNative"));
       closeSoftKeyboard();
       onView(withId(R.id.updateReu_btn)).perform(click());
       onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(9, click()));
       onView(withId(R.id.sujetReunion_edt)).check(matches(withText("ReactNative")));
       onView(isRoot()).perform(pressBack());
   }

   @Test
    public void reunionActivitySearchView_typeText_shouldHaveOneReusult(){
       onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
       onView(withId(R.id.item0)).perform(click());
       onView(isAssignableFrom(EditText.class)).perform(typeText("aws"), pressKey(KeyEvent.KEYCODE_ENTER));
       onView(withId(R.id.recyclerView)).check(matches(recyclerViewSizeMatcher(1)));
       onView(isAssignableFrom(EditText.class)).perform(clearText(), pressKey(KeyEvent.KEYCODE_ENTER));
       openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
       onView(withText("reset")).perform(click());
   }

   @Test
    public void actionBarMenu_performClickReset_shouldSortReunions(){
       onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
       openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
       onView(withText("reset")).perform(click());
       onView(withId(R.id.recyclerView)).check(matches(recyclerViewAtPositionOnView(0, withText("AWS - 11:10 - andrea"), R.id.sujetReu_txt)));
//       onView(withId(R.id.recyclerView)).check(matches(recyclerViewAtPositionOnView(1, withText("DNS Load Balancing - 17:05 - sarah"), R.id.sujetReu_txt)));
//       onView(withId(R.id.recyclerView)).check(matches(recyclerViewAtPositionOnView(2, withText("Django - 12:25 - alan"), R.id.sujetReu_txt)));
   }

    @Test
    public void actionBarMenu_performClickDate_shouldSortReunions(){
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("trier par date")).perform(click());
        onView(withId(R.id.recyclerView)).check(matches(recyclerViewAtPositionOnView(0, withText("DNS Load Balancing - 17:05 - sarah"), R.id.sujetReu_txt)));
        onView(withId(R.id.recyclerView)).check(matches(recyclerViewAtPositionOnView(1, withText("Django - 12:25 - alan"), R.id.sujetReu_txt)));
        onView(withId(R.id.recyclerView)).check(matches(recyclerViewAtPositionOnView(2, withText("PWA - 10:10 - melanie"), R.id.sujetReu_txt)));
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("reset")).perform(click());
    }

    @Test
    public void actionBarMenu_performClickLieu_shouldSortReunions(){
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("trier par lieu")).perform(click());
        onView(withId(R.id.recyclerView)).check(matches(recyclerViewAtPositionOnView(0, withText("AWS - 11:10 - andrea"), R.id.sujetReu_txt)));
        onView(withId(R.id.recyclerView)).check(matches(recyclerViewAtPositionOnView(1, withText("Docker - 15:00 - nico"), R.id.sujetReu_txt)));
        onView(withId(R.id.recyclerView)).check(matches(recyclerViewAtPositionOnView(2, withText("flutter - 10:15 - teddy"), R.id.sujetReu_txt)));
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("reset")).perform(click());
    }
}
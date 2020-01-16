package clement.zentz.mareu.utils;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Objects;

public class RecyclerViewSizeMatcher {

    public static Matcher<View> recyclerViewSizeMatcher(final int matcherSize) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with list size: " + matcherSize);
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                return matcherSize == Objects.requireNonNull(recyclerView.getAdapter()).getItemCount();
            }
        };
    }
}

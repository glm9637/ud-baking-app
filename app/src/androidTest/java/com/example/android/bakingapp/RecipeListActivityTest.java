package com.example.android.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * Erzeugt von M. Fengels am 16.05.2018.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {
	
	@Rule
	public ActivityTestRule<RecipeList> mActivityTestRule =
			new ActivityTestRule<>(RecipeList.class);
	
	private IdlingResource mIdlingResource;
	
	@Before
	public void registerIdlingResource() {
		mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
		IdlingRegistry.getInstance().register(mIdlingResource);
		
	}
	
	/**
	 * Check if the Recyclerview works as expected
	 */
	@Test
	public void clickRecipeTest() {
		onView(withText("Brownies")).perform(click());
		onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));
	}
	
	@After
	public void unregisterIdlingResource(){
		if(mIdlingResource != null){
			IdlingRegistry.getInstance().unregister(mIdlingResource);
		}
	}
}

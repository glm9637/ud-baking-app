package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.model.Recipe;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Erzeugt von M. Fengels am 16.05.2018.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {
	
	private Recipe mTestRecipe;
	
	@Rule
	public ActivityTestRule<RecipeDetail> mActivityTestRule =
			new ActivityTestRule<>(RecipeDetail.class,false,false);
	
	/**
	 * The DetailActivity needs to be started with a recipe, so mock up one here
	 */
	@Before
	public void setupRecipe(){
		try {
			JSONObject object = new JSONObject(mRecipeJson);
			mTestRecipe = new Recipe(object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Intent i = new Intent();
		i.putExtra("Recipe",(Parcelable) mTestRecipe);
		mActivityTestRule.launchActivity(i);
	}
	
	/**
	 * Test if the PreviosItemButton goes further back than possible
	 */
	@Test
	public void testPreviosItemButton() {
		onView(withId(R.id.btn_previous)).perform(click());
		onView(withId(R.id.txt_stepper)).check(matches(withText("1/8")));
	}
	
	/**
	 * Test if the NextItemButton works as expected
	 */
	@Test
	public void testNextItemButton() {
		onView(withId(R.id.btn_next)).perform(click());
		onView(withId(R.id.txt_stepper)).check(matches(withText("2/8")));
	}
	
	
	/**
	 * Test is both Navigation Buttons work together
	 */
	@Test
	public void testNextAndPreviosItemButton() {
		onView(withId(R.id.btn_next)).perform(click());
		onView(withId(R.id.txt_stepper)).check(matches(withText("2/8")));
		
		onView(withId(R.id.btn_previous)).perform(click());
		onView(withId(R.id.txt_stepper)).check(matches(withText("1/8")));
	}
	
	/**
	 * check if the Navigation expands as expected
	 */
	@Test
	public void testExpandNavigation() {
		onView(withId(R.id.txt_stepper)).perform(click());
		onView(withId(R.id.navigation_list)).check(matches(isDisplayed()));
	}
	
	/**
	 * check if the Navigation items work as expected
	 */
	@Test
	public void testNavigationList() {
		onView(withId(R.id.txt_stepper)).perform(click());
		onView(withText("Start filling prep")).perform(click());
		onView(withId(R.id.txt_stepper)).check(matches(withText("6/8")));
	}
	
	private String mRecipeJson = "{\n" +
			"    \"id\": 1,\n" +
			"    \"name\": \"Nutella Pie\",\n" +
			"    \"ingredients\": [\n" +
			"      {\n" +
			"        \"quantity\": 2,\n" +
			"        \"measure\": \"CUP\",\n" +
			"        \"ingredient\": \"Graham Cracker crumbs\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 6,\n" +
			"        \"measure\": \"TBLSP\",\n" +
			"        \"ingredient\": \"unsalted butter, melted\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 0.5,\n" +
			"        \"measure\": \"CUP\",\n" +
			"        \"ingredient\": \"granulated sugar\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 1.5,\n" +
			"        \"measure\": \"TSP\",\n" +
			"        \"ingredient\": \"salt\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 5,\n" +
			"        \"measure\": \"TBLSP\",\n" +
			"        \"ingredient\": \"vanilla\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 1,\n" +
			"        \"measure\": \"K\",\n" +
			"        \"ingredient\": \"Nutella or other chocolate-hazelnut spread\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 500,\n" +
			"        \"measure\": \"G\",\n" +
			"        \"ingredient\": \"Mascapone Cheese(room temperature)\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 1,\n" +
			"        \"measure\": \"CUP\",\n" +
			"        \"ingredient\": \"heavy cream(cold)\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 4,\n" +
			"        \"measure\": \"OZ\",\n" +
			"        \"ingredient\": \"cream cheese(softened)\"\n" +
			"      }\n" +
			"    ],\n" +
			"    \"steps\": [\n" +
			"      {\n" +
			"        \"id\": 0,\n" +
			"        \"shortDescription\": \"Recipe Introduction\",\n" +
			"        \"description\": \"Recipe Introduction\",\n" +
			"        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4\",\n" +
			"        \"thumbnailURL\": \"\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"id\": 1,\n" +
			"        \"shortDescription\": \"Starting prep\",\n" +
			"        \"description\": \"1. Preheat the oven to 350\\u00b0F. Butter a 9\\\" deep dish pie pan.\",\n" +
			"        \"videoURL\": \"\",\n" +
			"        \"thumbnailURL\": \"\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"id\": 2,\n" +
			"        \"shortDescription\": \"Prep the cookie crust.\",\n" +
			"        \"description\": \"2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.\",\n" +
			"        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4\",\n" +
			"        \"thumbnailURL\": \"\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"id\": 3,\n" +
			"        \"shortDescription\": \"Press the crust into baking form.\",\n" +
			"        \"description\": \"3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.\",\n" +
			"        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4\",\n" +
			"        \"thumbnailURL\": \"\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"id\": 4,\n" +
			"        \"shortDescription\": \"Start filling prep\",\n" +
			"        \"description\": \"4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.\",\n" +
			"        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4\",\n" +
			"        \"thumbnailURL\": \"\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"id\": 5,\n" +
			"        \"shortDescription\": \"Finish filling prep\",\n" +
			"        \"description\": \"5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.\",\n" +
			"        \"videoURL\": \"\",\n" +
			"        \"thumbnailURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"id\": 6,\n" +
			"        \"shortDescription\": \"Finishing Steps\",\n" +
			"        \"description\": \"6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!\",\n" +
			"        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4\",\n" +
			"        \"thumbnailURL\": \"\"\n" +
			"      }\n" +
			"    ],\n" +
			"    \"servings\": 8,\n" +
			"    \"image\": \"\"\n" +
			"  },\n" +
			"  {\n" +
			"    \"id\": 2,\n" +
			"    \"name\": \"Brownies\",\n" +
			"    \"ingredients\": [\n" +
			"      {\n" +
			"        \"quantity\": 350,\n" +
			"        \"measure\": \"G\",\n" +
			"        \"ingredient\": \"Bittersweet chocolate (60-70% cacao)\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 226,\n" +
			"        \"measure\": \"G\",\n" +
			"        \"ingredient\": \"unsalted butter\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 300,\n" +
			"        \"measure\": \"G\",\n" +
			"        \"ingredient\": \"granulated sugar\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 100,\n" +
			"        \"measure\": \"G\",\n" +
			"        \"ingredient\": \"light brown sugar\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 5,\n" +
			"        \"measure\": \"UNIT\",\n" +
			"        \"ingredient\": \"large eggs\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 1,\n" +
			"        \"measure\": \"TBLSP\",\n" +
			"        \"ingredient\": \"vanilla extract\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 140,\n" +
			"        \"measure\": \"G\",\n" +
			"        \"ingredient\": \"all purpose flour\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 40,\n" +
			"        \"measure\": \"G\",\n" +
			"        \"ingredient\": \"cocoa powder\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 1.5,\n" +
			"        \"measure\": \"TSP\",\n" +
			"        \"ingredient\": \"salt\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"quantity\": 350,\n" +
			"        \"measure\": \"G\",\n" +
			"        \"ingredient\": \"semisweet chocolate chips\"\n" +
			"      }\n" +
			"    ],\n" +
			"    \"steps\": [\n" +
			"      {\n" +
			"        \"id\": 0,\n" +
			"        \"shortDescription\": \"Recipe Introduction\",\n" +
			"        \"description\": \"Recipe Introduction\",\n" +
			"        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc33_-intro-brownies/-intro-brownies.mp4\",\n" +
			"        \"thumbnailURL\": \"\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"id\": 1,\n" +
			"        \"shortDescription\": \"Starting prep\",\n" +
			"        \"description\": \"1. Preheat the oven to 350�F. Butter the bottom and sides of a 9\\\"x13\\\" pan.\",\n" +
			"        \"videoURL\": \"\",\n" +
			"        \"thumbnailURL\": \"\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"id\": 2,\n" +
			"        \"shortDescription\": \"Melt butter and bittersweet chocolate.\",\n" +
			"        \"description\": \"2. Melt the butter and bittersweet chocolate together in a microwave or a double boiler. If microwaving, heat for 30 seconds at a time, removing bowl and stirring ingredients in between.\",\n" +
			"        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc43_1-melt-choclate-chips-and-butter-brownies/1-melt-choclate-chips-and-butter-brownies.mp4\",\n" +
			"        \"thumbnailURL\": \"\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"id\": 3,\n" +
			"        \"shortDescription\": \"Add sugars to wet mixture.\",\n" +
			"        \"description\": \"3. Mix both sugars into the melted chocolate in a large mixing bowl until mixture is smooth and uniform.\",\n" +
			"        \"videoURL\": \"\",\n" +
			"        \"thumbnailURL\": \"\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"id\": 4,\n" +
			"        \"shortDescription\": \"Mix together dry ingredients.\",\n" +
			"        \"description\": \"4. Sift together the flour, cocoa, and salt in a small bowl and whisk until mixture is uniform and no clumps remain. \",\n" +
			"        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc9e_4-sift-flower-add-coco-powder-salt-brownies/4-sift-flower-add-coco-powder-salt-brownies.mp4\",\n" +
			"        \"thumbnailURL\": \"\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"id\": 5,\n" +
			"        \"shortDescription\": \"Add eggs.\",\n" +
			"        \"description\": \"5. Crack 3 eggs into the chocolate mixture and carefully fold them in. Crack the other 2 eggs in and carefully fold them in. Fold in the vanilla.\",\n" +
			"        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc62_2-mix-egss-with-choclate-butter-brownies/2-mix-egss-with-choclate-butter-brownies.mp4\",\n" +
			"        \"thumbnailURL\": \"\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"id\": 6,\n" +
			"        \"shortDescription\": \"Add dry mixture to wet mixture.\",\n" +
			"        \"description\": \"6. Dump half of flour mixture into chocolate mixture and carefully fold in, just until no streaks remain. Repeat with the rest of the flour mixture. Fold in the chocolate chips.\",\n" +
			"        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdcc8_5-mix-wet-and-cry-batter-together-brownies/5-mix-wet-and-cry-batter-together-brownies.mp4\",\n" +
			"        \"thumbnailURL\": \"\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"id\": 7,\n" +
			"        \"shortDescription\": \"Add batter to pan.\",\n" +
			"        \"description\": \"7. Pour the batter into the prepared pan and bake for 30 minutes.\",\n" +
			"        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdcf4_8-put-brownies-in-oven-to-bake-brownies/8-put-brownies-in-oven-to-bake-brownies.mp4\",\n" +
			"        \"thumbnailURL\": \"\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"id\": 8,\n" +
			"        \"shortDescription\": \"Remove pan from oven.\",\n" +
			"        \"description\": \"8. Remove the pan from the oven and let cool until room temperature. If you want to speed this up, you can feel free to put the pan in a freezer for a bit.\",\n" +
			"        \"videoURL\": \"\",\n" +
			"        \"thumbnailURL\": \"\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"id\": 9,\n" +
			"        \"shortDescription\": \"Cut and serve.\",\n" +
			"        \"description\": \"9. Cut and serve.\",\n" +
			"        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdcf9_9-final-product-brownies/9-final-product-brownies.mp4\",\n" +
			"        \"thumbnailURL\": \"\"\n" +
			"      }\n" +
			"    ],\n" +
			"    \"servings\": 8,\n" +
			"    \"image\": \"\"\n" +
			"  }";
}

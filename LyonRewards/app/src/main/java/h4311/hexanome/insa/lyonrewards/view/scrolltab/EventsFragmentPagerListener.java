package h4311.hexanome.insa.lyonrewards.view.scrolltab;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import h4311.hexanome.insa.lyonrewards.R;

/**
 * Created by Pierre on 27/04/2016.
 */
public class EventsFragmentPagerListener implements MaterialViewPager.Listener {
    @Override
    public HeaderDesign getHeaderDesign(int page) {
        switch (page) {
            case 0:
                return HeaderDesign.fromColorResAndUrl(
                        R.color.green,
                        "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
            case 1:
                return HeaderDesign.fromColorResAndUrl(
                        R.color.blue,
                        "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");
            case 2:
                return HeaderDesign.fromColorResAndUrl(
                        R.color.cyan,
                        "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
            case 3:
                return HeaderDesign.fromColorResAndUrl(
                        R.color.red,
                        "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
        }

        //execute others actions if needed (ex : modify your header logo)

        return null;
    }
}

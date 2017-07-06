package com.example.qsuo.kotlinapp

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.qsuo.kotlinapp.features.news.NewsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        setText(R.id.textView3, getString(R.string.view3))
        setText(R.id.textView4, getString(R.string.view4))

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            cleanUpText(R.id.textView1)
            cleanUpText(R.id.textView2)
            cleanUpText(R.id.textView3)
            cleanUpText(R.id.textView4)
            Snackbar.make(view, getString(R.string.snackBar), Snackbar.LENGTH_LONG)
                    .setAction("Refresh", View.OnClickListener { changeFragment(NewsFragment()) }).show()
        }

        val closeIcon = findViewById(R.id.cross) as FloatingActionButton
        closeIcon.setOnClickListener { _ ->
            clearBackStack()
            setText(R.id.textView3, "you are back.")
        }



    }

    private fun setText(id: Int, content: String) {
        val textView = findViewById(id) as TextView
        textView.text = content
    }

    private fun cleanUpText(id: Int) {
        val view1 = findViewById(id) as TextView
        view1.text = ""
    }

    private fun changeFragment(f: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_popup_enter, R.anim.abc_popup_exit)
        ft.replace(R.id.activity_base_content, f)
        ft.addToBackStack(null)
        ft.commit()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun clearBackStack() {
        val manager = supportFragmentManager;
        if (manager.backStackEntryCount > 0) {
            val first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

}

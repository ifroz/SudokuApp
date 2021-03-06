package ifroz.sudoku

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.InputFilter
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import ifroz.sudoku.app.Puzzle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val ROW_IDS = listOf(
        R.id.row_0,
        R.id.row_1,
        R.id.row_2,
        R.id.row_3,
        R.id.row_4,
        R.id.row_5,
        R.id.row_6,
        R.id.row_7,
        R.id.row_8
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        for (rowId in ROW_IDS) createRow(findViewById<TableRow>(rowId))

        displayPuzzle(Puzzle());
    }

    fun createRow(tableRow: TableRow) {
        repeat(9, { _ ->
            val field = EditText(this);
            field.inputType = InputType.TYPE_CLASS_NUMBER
            field.filters = arrayOf(InputFilter.LengthFilter(1))
            tableRow.addView(field)
        })
    }

    fun doIt(v: View) {
        getMatrix();
    }

    fun getMatrix():MutableList<MutableList<Int>> {
        val responses =
                MutableList(9, { rowIndex ->
                val row = findViewById<TableRow>(ROW_IDS[rowIndex])
                    MutableList(9, { colIndex ->
                    val field = row.getChildAt(colIndex) as EditText
                    try {
                        field.text.toString().toInt()
                    } catch (err: Error) {
                        0
                    }
                })
            })
        Toast.makeText(this, "WowW: ${responses[0][5]}", Toast.LENGTH_LONG).show()
        return responses
    }

    fun displayPuzzle(puzzleInstance: Puzzle) {
        val puzzle = puzzleInstance.toMutableList();
        for ((rowIndex, rowId) in ROW_IDS.withIndex()) {
            val row = findViewById<TableRow>(rowId);
            var colIndex = 0;
            while (colIndex < 9) {
                (row.getChildAt(colIndex) as EditText).setText(puzzle[rowIndex][colIndex].toString());
                colIndex++;
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}

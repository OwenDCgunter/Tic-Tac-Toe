package gunt0023.example.com

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.TableLayout
import android.widget.TableRow
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.app.AlertDialog.Builder
import android.support.v7.app.AlertDialog

class MainActivity : AppCompatActivity() {

    var turn: Char = 'X'
    var turnCounter: Int = 0
    var gameBoard : Array<CharArray> = Array(3) { CharArray(3) }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var turnLayout: TableLayout = findViewById(R.id.table_layout)
        var turnTextView: TextView = findViewById(R.id.turnTextView)



        fab.setOnClickListener {
            startNewGame(setClickListener = false)
        }

        startNewGame(setClickListener = true)
    }

    private fun startNewGame(setClickListener: Boolean): Boolean{


        turn = 'X'
        turnCounter = 0
        turnTextView?.text = String.format(resources.getString(R.string.turn), turn)


        for(i in 0 until gameBoard.size)
        {
            for(j in 0 until gameBoard[i].size)
            {
                gameBoard[i][j] = ' '
                var cell: TextView = (table_layout?.getChildAt(i) as TableRow).getChildAt(j) as TextView
                cell.text = ""

                if(setClickListener)
                {
                    cell.setOnClickListener { cellClickListener(i, j) }

                }
            }
        }

        return false
    }


    private fun cellClickListener(row: Int, col: Int)
    {

        gameBoard[row][col] = turn

        val cell = ((table_layout?.getChildAt(row)
                as TableRow).getChildAt(col)
                as TextView)
        cell.text = Character.toString(turn)

        if(turn == 'X')
        {
            turnCounter++
            turn = 'O'
        }
        else
        {
            turnCounter++
            turn = 'X'
        }
        turnTextView?.text = String.format(resources.getString(R.string.turn), turn)

        checkGameStatus()
    }



    private fun isBoardFull(gameBoard: Array<CharArray>): Boolean{

        return turnCounter == 9

      /*  for(i in 0 until gameBoard.size)
        {
            for(j in 0 until gameBoard.size)
            {

                if(gameBoard[i][j] != ' ')
                {
                    return true
                }
            }
        }


        return false*/
    }

    private fun isWinner(gameBoard: Array<CharArray>, w: Char):Boolean{


        if(w == gameBoard[0][0] && w == gameBoard[1][1] && w == gameBoard[2][2])
        {
            return true
        }
        else if(w == gameBoard[0][2] && w == gameBoard[1][1] && w == gameBoard[2][0])
        {
            return true
        }

        for(i in 0 until gameBoard.size)
        {
            for(j in 0 until gameBoard[i].size)
            {
                if(w == gameBoard[i][0] && w == gameBoard[i][1] && w == gameBoard[i][2])
                {
                    return true
                }
              else if(w == gameBoard[0][j] && w == gameBoard[1][j] && w == gameBoard[2][j])
                {
                    return true
                }

            }
        }

        return false
    }

    private fun checkGameStatus(){
        var state: String? = null

        var builder = AlertDialog.Builder(this)

        if(isWinner(gameBoard, 'X'))
        {
            state = String.format(resources.getString(R.string.winner), 'X')

            builder.setMessage(state)
            builder.setPositiveButton(android.R.string.ok) { dialog, id ->
                startNewGame(false)
            }
            val dialog = builder.create()
            dialog.show()
        }

        else if(isWinner(gameBoard, 'O'))
        {
            state = String.format(resources.getString(R.string.winner), 'O')

            builder.setMessage(state)
            builder.setPositiveButton(android.R.string.ok) { dialog, id ->
                startNewGame(false)
            }
            val dialog = builder.create()
            dialog.show()
        }
        else if(isBoardFull(gameBoard))
        {
            state = resources.getString(R.string.draw)

            builder.setMessage(state)
            builder.setPositiveButton(android.R.string.ok) { dialog, id ->
                startNewGame(false)
            }
            val dialog = builder.create()
            dialog.show()
        }
        if(state != null)
        {
            turnTextView.text = state
        }






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
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

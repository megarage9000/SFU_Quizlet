package com.example.sfuquizlet.recyclerviews

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sfuquizlet.Deck
import com.example.sfuquizlet.R



data class ColorPairing(val primary: Int, val secondary: Int)

// A color list to color code each department
// Feel free to add more!
val colorLists = arrayOf(
    ColorPairing(
        Color.parseColor("#C2FDD6"),
        Color.parseColor("#00812B")
    ),
    ColorPairing(
        Color.parseColor("#A7D6FF"),
        Color.parseColor("#006CCA")
    ),
    ColorPairing(
        Color.parseColor("#D6C5FF"),
        Color.parseColor("#3E00D5")
    )
)

// Recycler view to store courses, assorted by their department
class CoursesListRecyclerView(private val departmentGroups: Map<String, List<Deck>>,
                              private val viewListener: CardDeckViewListener,
                              val context: Context
                                ): RecyclerView.Adapter<CoursesListRecyclerView.CoursesListHolder>(){

    // View holder for each department, see
    // department_list.xml for the layout
    class CoursesListHolder(private val viewListener: CardDeckViewListener, val parent: ViewGroup, view: View)
        : RecyclerView.ViewHolder(view) {

        // Sets the content of a department_list.xml
        fun setContent(departmentName: String,
                       decks: List<Deck>,
                       color: ColorPairing) {
            val view = this.itemView
            // Sets the title of the department_list.xml
            view.findViewById<TextView>(R.id.departmentHeader).text = departmentName

            // Creates a recycler view of the associated decks with the department
            val recyclerView = view.findViewById<RecyclerView>(R.id.departmentRecycler)
            recyclerView.layoutManager = LinearLayoutManager(parent.context)
            recyclerView.adapter = DepartmentListRecycler(departmentName, decks, viewListener, parent.context, color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.department_list, parent, false)

        // To alter the width / height parameters,
        // use layout params!
        return CoursesListHolder(viewListener, parent, view)
    }

    override fun onBindViewHolder(holder: CoursesListHolder, position: Int) {
        // Fills up the department and associated decks to
        // a given CourseListHolder
        val department = departmentGroups.keys.toTypedArray()[position]
        val departmentCourses = departmentGroups[department]
        if(departmentCourses != null){

            // Assign a color to each department
            // May be reused
            val color = colorLists[position % colorLists.size]
            holder.setContent(department, departmentCourses, color)
        }
    }

    override fun getItemCount(): Int {
        return departmentGroups.size
    }
}




















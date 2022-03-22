package com.example.sfuquizlet

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Stores all the courses into several

val colorLists = arrayOf(
    Color.valueOf(0xC2FDD6),
    Color.valueOf(0xA7D6FF),
    Color.valueOf(0xD6C5FF)
)

// Recycler view to store courses, assorted by their department
class CoursesListRecyclerView(private val departmentGroups: Map<String, List<Deck>>,
                              val context: Context
                                ): RecyclerView.Adapter<CoursesListRecyclerView.CoursesListHolder>(){

    // View holder for each department, see
    // department_list.xml for the layout
    class CoursesListHolder(private val inflater: LayoutInflater, val parent: ViewGroup, view: View)
        : RecyclerView.ViewHolder(view) {

        // Sets the content of a department_list.xml
        fun setContent(departmentName: String,
                       decks: List<Deck>,
                       color: Color) {
            val view = this.itemView
            // Sets the title of the department_list.xml
            view.findViewById<TextView>(R.id.departmentHeader).text = departmentName

            // Creates a recycler view of the associated decks with the department
            val recyclerView = view.findViewById<RecyclerView>(R.id.departmentRecycler)
            recyclerView.layoutManager = LinearLayoutManager(parent.context)
            recyclerView.adapter = DepartmentListRecycler(decks, parent.context, inflater, color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.department_list, parent, false)

        // To alter the width / height parameters,
        // use layout params!
        return CoursesListHolder(inflater, parent, view)
    }

    override fun onBindViewHolder(holder: CoursesListHolder, position: Int) {
        // Fills up the department and associated decks to
        // a given CourseListHolder
        val department = departmentGroups.keys.toTypedArray()[position]
        val departmentCourses = departmentGroups[department]
        if(departmentCourses != null){
            val color = colorLists[position % colorLists.size]
            holder.setContent(department, departmentCourses, color)
        }
    }

    override fun getItemCount(): Int {
        return departmentGroups.size
    }
}




















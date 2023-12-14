package aziz6292.studio.settleease

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class StudentRegistrationActivity : AppCompatActivity() {

    private lateinit var courseEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var rollNoEditText: EditText
    private lateinit var semesterEditText: EditText
    private lateinit var addStudentButton: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_registration)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        courseEditText = findViewById(R.id.courseEditText)
        nameEditText = findViewById(R.id.nameEditText)
        rollNoEditText = findViewById(R.id.rollNoEditText)
        semesterEditText = findViewById(R.id.semesterEditText)
        addStudentButton = findViewById(R.id.addStudentButton)

        addStudentButton.setOnClickListener {
            addStudentToDatabase()
        }
    }

    private fun addStudentToDatabase() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid

            val course = courseEditText.text.toString().trim()
            val name = nameEditText.text.toString().trim()
            val rollNo = rollNoEditText.text.toString().trim()
            val semester = semesterEditText.text.toString().trim()

            if (course.isEmpty() || name.isEmpty() || rollNo.isEmpty() || semester.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                return
            }

            val studentData = Student(course, name, rollNo, semester)

            // Write student data to the database
            writeToDatabase(userId, studentData)

            // You can perform additional actions or navigate to other activities if needed
            Toast.makeText(this, "Student added successfully!", Toast.LENGTH_SHORT).show()
        } else {
            // Handle the case where the user is not authenticated
            Toast.makeText(this, "User is not authenticated.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun writeToDatabase(userId: String, studentData: Student) {
        val database = FirebaseDatabase.getInstance()
        val studentsRef: DatabaseReference = database.getReference("users").child(userId).child("students")
        val studentKey = studentsRef.push().key // Create a unique key for each student
        studentsRef.child(studentKey!!).setValue(studentData)
    }

}

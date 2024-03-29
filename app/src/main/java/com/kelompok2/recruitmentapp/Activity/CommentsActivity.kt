package com.kelompok2.recruitmentapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompok2.recruitmentapp.Adapter.CommentsAdapter
import com.kelompok2.recruitmentapp.Model.Comment
import com.kelompok2.recruitmentapp.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kelompok2.recruitmentapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_comments.*

class CommentsActivity : AppCompatActivity() {
    private var postId  = ""
    private var publisherId  = ""
    private var firebaseUser:FirebaseUser? = null
    private var commentAdapter: CommentsAdapter? = null
    private var commentList: MutableList<Comment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        val intent = intent
        postId = intent.getStringExtra("postId").toString()
        publisherId = intent.getStringExtra("publisherId").toString()

        firebaseUser = FirebaseAuth.getInstance().currentUser

        var recyclerView: RecyclerView
        recyclerView = findViewById(R.id.recycler_view_comments)
        val linearLayoutManager =  LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        recyclerView.layoutManager = linearLayoutManager

        commentList = ArrayList()
        commentAdapter = CommentsAdapter(this, commentList)
        recyclerView.adapter = commentAdapter

        userInfo()
        readComments()
        getPostImage()
        getDescription()


        post_comment.setOnClickListener(View.OnClickListener {
            if (add_comment!!.text.toString() == "")
            {
                Toast.makeText(this,"Please write comment first", Toast.LENGTH_LONG).show()
            }
            else
            {
                addComment()
            }
        })


    }

    private fun addComment() {
        val commentsRef = FirebaseDatabase.getInstance().reference.child("Comments").child(postId)

        val commentsMap = HashMap<String, Any>()
        commentsMap["comment"] = add_comment!!.text.toString()
        commentsMap["publisher"] = firebaseUser!!.uid

        commentsRef.push().setValue(commentsMap)

        add_comment!!.text.clear()

    }

    private fun userInfo()
    {
        val jobsRef = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)

        jobsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists())
                {
                    val user = p0.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.onee).into(profile_image_comment)


                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }



    private fun getPostImage()
    {
        val postRef = FirebaseDatabase.getInstance().reference.child("Posts").child(postId!!).child("postimage")

        postRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists())
                {
                    val image = p0.value.toString()

                    Picasso.get().load(image).placeholder(R.drawable.onee).into(post_image_comment)


                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    private fun getDescription()
    {
        val postRef = FirebaseDatabase.getInstance().reference.child("Posts").child(postId!!).child("description")

        postRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists())
                {
                    val description = p0.value.toString()

                    text_post_comment.text = description


                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    private fun readComments(){
        val commentsRef = FirebaseDatabase.getInstance().reference.child("Comments").child(postId)

        commentsRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists())
                {
                    commentList!!.clear()

                    for (snapshot in p0.children)
                    {
                        val comment = snapshot.getValue(Comment::class.java)
                        commentList!!.add(comment!!)
                    }

                    commentAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}
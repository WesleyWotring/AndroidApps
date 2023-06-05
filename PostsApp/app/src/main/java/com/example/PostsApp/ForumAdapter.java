package com.example.PostsApp;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * HW05
 * Group F22
 * Wesley Wotring and Kyle Prindle
 */
public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {
    ArrayList<Forum> forumsList = new ArrayList<>();
    onForumListener monForumListener;
    FirebaseAuth mAuth2 = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth2.getCurrentUser();
    String userId = user.getUid(); //user uid
    String forumUserId;
    int likeFlag;


    public ForumAdapter(ArrayList<Forum> forumsList, onForumListener onForumListener) {
        this.forumsList = forumsList;
        this.monForumListener = onForumListener;
    }

    @NonNull
    @Override
    public ForumAdapter.ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forums_layout, parent, false);
        ForumAdapter.ForumViewHolder forumViewHolder = new ForumAdapter.ForumViewHolder(view, monForumListener);

        return forumViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {

        Forum forum = forumsList.get(position);
        holder.name.setText(forum.getName());
        holder.time.setText(forum.getDate().toString());
        holder.desc.setText(forum.getDescription());
        holder.title.setText(forum.getTitle());
        holder.likes.setText(String.valueOf(forum.getLikes().size()) + " Likes");
        //forumUserId = forum.getUser_id(); forum uid

        Log.d("demo", "like counter: " + forum.getLikes().size());
        //trash icon
        if(user.getUid().equals(forum.getUser_id())){
            holder.delete.setVisibility(View.VISIBLE);
        }else{
            holder.delete.setVisibility(View.INVISIBLE);
        }

        //like icon
        if(forum.likes.containsKey(user.getUid())){
           // likeFlag = 1;
            holder.like.setImageResource(R.drawable.like_favorite);

        }else{
           // likeFlag = likeFlag * -1;
            holder.like.setImageResource(R.drawable.like_not_favorite);

        }


        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeFlag = likeFlag * -1;
                if(forum.likes.containsKey(user.getUid())){
                    forum.likes.remove(user.getUid());
                }else{
                    forum.likes.put(user.getUid(), true);
                }

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference likeUpdate = db.collection("forums").document(forum.getDoc_id());
                likeUpdate.update("likes", forum.getLikes())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
//                                Log.d("demo", "LIKE ");
//                                Log.d("demo", "like counter: " + forum.getLikeMap().size());
//                                holder.likes.setText(String.valueOf(forum.getLikeMap().size()) + " Likes");
//
//                                if(likeFlag < 1){
//                                    likeFlag = 1;
//                                }else{
//                                    likeFlag = -1;
//                                }
//
//                                if(likeFlag == 1){
//                                    holder.like.setImageResource(R.drawable.like_favorite);
//                                }else{
//                                    holder.like.setImageResource(R.drawable.like_not_favorite);
//                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("demo", "FAILED TO LIKE ");
                            }
                        });
            }
        });





        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForumsFragment.deleteForum(forum);
            }
        });


    }

    @Override
    public int getItemCount() {
        return this.forumsList.size();
    }

    public class ForumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView delete, like;
        TextView name, desc, time, title, likes;
        onForumListener onForumListener;


        public ForumViewHolder(@NonNull View itemView, onForumListener onForumListener) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            title = itemView.findViewById(R.id.textViewTitle);
            desc = itemView.findViewById(R.id.textViewDesc);
            time = itemView.findViewById(R.id.textViewTime);
            like = itemView.findViewById(R.id.imageViewLike);
            likes = itemView.findViewById(R.id.textViewLikes);

            delete = itemView.findViewById(R.id.imageViewDelete);

            this.onForumListener = onForumListener;

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            onForumListener.onForumClick(getAdapterPosition());
        }
    }
    public interface onForumListener{
        void onForumClick(int position);
    }

}
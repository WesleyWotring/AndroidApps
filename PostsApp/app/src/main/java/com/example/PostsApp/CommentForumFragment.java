package com.example.PostsApp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

/**
 * HW05
 * Group F22
 * Wesley Wotring and Kyle Prindle
 */
public class CommentForumFragment extends Fragment {
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private static final String ARG_USER_TOKEN = "ARG_USER_TOKEN";
    Button createComment;
    RecyclerView commentsRecycler;
    TextView title, name, commentMain, commentCounter;
    EditText commentText;
    Forum chosenForum = new Forum();


    View view;
    Forum forum;
    ArrayList<Comment> commentArray = new ArrayList<>();
    CommentForumFragment.CommentAdapter adapter;
    LinearLayoutManager manager;

    String forumId;


    public CommentForumFragment() {
        // Required empty public constructor
    }


    public static CommentForumFragment newInstance(String forumId) {
        CommentForumFragment fragment = new CommentForumFragment();

        Bundle args = new Bundle();
        args.putString(ARG_USER_TOKEN, forumId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           forumId = (String) getArguments().getSerializable(ARG_USER_TOKEN);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_comment_forum, container, false);

        name = view.findViewById(R.id.tvName);
        commentMain = view.findViewById(R.id.tvForumDesc);
        title = view.findViewById(R.id.tvTitle);
        commentCounter = view.findViewById(R.id.tvCommentCount);


        commentText = view.findViewById(R.id.editComment);
        createComment = view.findViewById(R.id.buttonCreateComment);
        commentsRecycler = view.findViewById(R.id.commentRV);


        commentArray.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        commentsRecycler.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity());
        commentsRecycler.setLayoutManager(manager);

        //JUST FOR YOU CARLOS
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(commentsRecycler.getContext(),
                manager.getOrientation());
        commentsRecycler.addItemDecoration(dividerItemDecoration);

        adapter = new CommentForumFragment.CommentAdapter(commentArray);
        commentsRecycler.setAdapter(adapter);

        DocumentReference selectedForum = db.collection("forums").document(forumId);
        //setting the document to a forum object
        selectedForum.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){

                        String commentName = document.getString("name");
                        String commentTextMain= document.getString("description");
                        String commentTitle = document.getString("title");

                        getActivity().setTitle("Comments for post " + commentTitle);

                        name.setText(commentName);
                        commentMain.setText(commentTextMain);
                        title.setText(commentTitle);

                    }else{
                        Log.d("demo", "Document does not exist");
                    }
                }else{
                    Log.d("demo", "Failed " + task.getException());
                }
            }
        });








        selectedForum.collection("comments")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        commentArray.clear();
                        for(QueryDocumentSnapshot document : value){
                            Comment commentUpdate = document.toObject(Comment.class);
//                            Comment commentUpdate = new Comment(document.getString("name"),document.getString("comment"),
//                                    document.getString("user_id"),document.getString("doc_id"), new Date());

                            commentArray.add(commentUpdate);
                        }
                        adapter.notifyDataSetChanged();
                        commentCounter.setText(commentArray.size() + " Comments ");

                    }

                });



        createComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentString = commentText.getText().toString();
                if (commentString.isEmpty()){
                    Toast.makeText(getContext(),"Fill all fields " ,Toast.LENGTH_SHORT);
                }else{
                    addComment(forumId);
                }
            }
        });



        return view;
    }

    private void addComment(String forumId){
        Comment comment = new Comment();
        comment.setComment(commentText.getText().toString());
        comment.setName(user.getDisplayName());
        comment.setDate(new Date());
        comment.setUser_id(user.getUid());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("forums").document(forumId)
                .collection("comments")
                .add(comment)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("demo", "Error deleting document", e);
                    }
                });

    }

    private void deleteComment(Comment comment){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("forums").document(forumId)
                .collection("comments")
                .document(comment.getDoc_id())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("demo", "DocumentSnapshot successfully deleted!");
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("demo", "Error deleting document", e);
                    }
                });

    }



    CommentForumFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CommentForumFragmentListener) {
            mListener = (CommentForumFragmentListener) context;
        } else {
            //"Must implement CitiesList Interface"
            throw new RuntimeException("Not Working ");
        }

    }


    interface CommentForumFragmentListener {
        void createComment();
    }


    public class CommentAdapter extends RecyclerView.Adapter<CommentForumFragment.CommentAdapter.CommentViewHolder> {
        ArrayList<Comment> commentsList;
        FirebaseAuth mAuth2 = FirebaseAuth.getInstance();
        FirebaseUser user2 = mAuth2.getCurrentUser();
        String userId = user2.getUid(); //user uid
        String forumUserId;


        public CommentAdapter(ArrayList<Comment> commentsList /**,FirebaseUser user**/ ) {
            this.commentsList = commentsList;
            // this.user = user;
        }

        @NonNull
        @Override
        public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout, parent, false);
            CommentAdapter.CommentViewHolder commentViewHolder = new CommentAdapter.CommentViewHolder(view);

            return commentViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {

            Comment comment = commentsList.get(position);
            holder.name.setText(comment.getName());
            holder.time.setText(comment.getDate().toString());
            holder.desc.setText(comment.getComment());
            //forumUserId = forum.getUser_id(); forum uid

            //trash icon
            if(user2.getUid().equals(comment.getUser_id())){
                holder.delete.setVisibility(View.VISIBLE);
            }else{
                holder.delete.setVisibility(View.INVISIBLE);
            }




            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteComment(comment);
                }
            });


        }

        @Override
        public int getItemCount() {
            return this.commentsList.size();
        }

        public class CommentViewHolder extends RecyclerView.ViewHolder {
            ImageView delete;
            TextView name, desc, time;


            public CommentViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.tvNameComment);
                desc = itemView.findViewById(R.id.tvCommentOther);
                time = itemView.findViewById(R.id.tvTime);

                delete = itemView.findViewById(R.id.imageViewDeleteComment);


            }
        }

    }

}
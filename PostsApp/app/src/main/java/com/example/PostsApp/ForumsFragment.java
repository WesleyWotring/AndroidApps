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

import com.example.inclass08.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * HW05
 * Group F22
 * Wesley Wotring and Kyle Prindle
 */
public class ForumsFragment extends Fragment implements ForumAdapter.onForumListener{
    private FirebaseAuth mAuth;
    private static final String ARG_USER_TOKEN = "ARG_USER_TOKEN";
    Button createPost, logout;
    RecyclerView forumsRecycler;
    View view;
    Forum forum;
    ArrayList<Forum> forumArrayList = new ArrayList<>();
    ForumAdapter adapter;
    LinearLayoutManager manager;


    //like boolean
    Boolean likeCheck = false;

    FirebaseUser user;

    public ForumsFragment() {
        // Required empty public constructor
    }


    public static ForumsFragment newInstance() {
        ForumsFragment fragment = new ForumsFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(ARG_USER_TOKEN, (Serializable) user);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            user = (FirebaseUser) getArguments().getSerializable(ARG_USER_TOKEN);
//
//        }
        getActivity().setTitle("Forums");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_forums, container, false);

        logout = view.findViewById(R.id.buttonLogOut);
        createPost = view.findViewById(R.id.buttonCreatePost);
        forumsRecycler = view.findViewById(R.id.list);


        forumArrayList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        forumsRecycler.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity());
        forumsRecycler.setLayoutManager(manager);

        //JUST FOR YOU CARLOS
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(forumsRecycler.getContext(),
                manager.getOrientation());
        forumsRecycler.addItemDecoration(dividerItemDecoration);

        adapter = new ForumAdapter(forumArrayList, this);
        forumsRecycler.setAdapter(adapter);


        db.collection("forums")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        forumArrayList.clear();
                        for(QueryDocumentSnapshot document : value){
                            Forum forumUpdate = document.toObject(Forum.class);
                            forumArrayList.add(forumUpdate);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });


//        db.collection("forums").get()
//                .addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                        if (task.isSuccessful()) {
//                            if (task.getResult().size() > 0) {
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    forum = document.toObject(Forum.class);
//                                    forumArrayList.add(forum);
//
//
//                                    getActivity().runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//
//                                            forumsRecycler.setHasFixedSize(true);
//                                            manager = new LinearLayoutManager(getActivity());
//                                            forumsRecycler.setLayoutManager(manager);
//
//                                            //JUST FOR YOU CARLOS
//                                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(forumsRecycler.getContext(),
//                                                    manager.getOrientation());
//                                            forumsRecycler.addItemDecoration(dividerItemDecoration);
//
//                                            adapter = new ForumAdapter(forumArrayList /**,user**/);
//                                            forumsRecycler.setAdapter(adapter);
//
//                                            db.collection("forums")
//                                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                                                        @Override
//                                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                                                            forumArrayList.clear();
//                                                            for(QueryDocumentSnapshot document : value){
//                                                                Forum forumUpdate = new Forum(document.getString("name"),document.getString("title")
//                                                                        ,document.getString("description"), user.getUid(), document.getId(),  document.getDate("date"));
//                                                                forumArrayList.add(forumUpdate);
//                                                            }
//                                                            adapter.notifyDataSetChanged();
//                                                        }
//                                                    });
//
//                                        }
//                                    });
//
//
//                                }
//                            } else {
//                                Toast.makeText(getActivity(), R.string.error + " " + task.getException(), Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(getActivity(), R.string.error + " " + task.getException(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                mListener.logout();

            }
        });

        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToCreateForum();
            }
        });


        return view;
    }

    public static void deleteForum(Forum forum){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("forums").document(forum.doc_id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("demo", "DocumentSnapshot successfully deleted!");
                        //adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("demo", "Error deleting document", e);
                    }
                });

    }






    ForumsFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ForumsFragmentListener) context;
    }

    @Override
    public void onForumClick(int position) {
        Log.d("demo", "Clicked Item " + (position + 1) +" Document ID " + forumArrayList.get(position).getDoc_id());

        Forum selectedForum = new Forum();
        String selectedForumDocId;
        selectedForum = forumArrayList.get(position);
        selectedForumDocId = selectedForum.getDoc_id();
        mListener.goToForum(selectedForumDocId);

    }


    interface ForumsFragmentListener {
        void logout();
        void goToCreateForum();
        void goToForum(String docID);
    }




}
package com.tapan.collegememories.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tapan.collegememories.Adapters.LovedPhotoAdapter;
import com.tapan.collegememories.Models.PhotosModel;
import com.tapan.collegememories.R;
import com.tapan.collegememories.activities.MainActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BookmarkPhotoFragment extends Fragment {


    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerViewLovedPhotos;
    LovedPhotoAdapter lovedPhotoAdapter;
    HashMap<String, Object> hm = new HashMap();
    ArrayList<PhotosModel> arrayList;

    public BookmarkPhotoFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerViewLovedPhotos = view.findViewById(R.id.recycler_photos);

        if ("Akanksha Gupta".equals(MainActivity.profile_activity_data.get("peopleName"))){
            Query query= firebaseFirestore.collection("Users").document("D3HhH91OOjWi1Y891cMfbHXbhHk1").collection("Photos").whereEqualTo("photoStatus","Loved").orderBy("dateTime", Query.Direction.DESCENDING);

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }

        if (MainActivity.profile_activity_data.get("peopleName").equals("Utkarsh Gupta")){
            Query query= firebaseFirestore.collection("Users").document("AFkEytVIQCRLizoiCNZn1KSKiDg2").collection("Photos").whereEqualTo("photoStatus","Loved").orderBy("dateTime", Query.Direction.DESCENDING);

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }

        if (MainActivity.profile_activity_data.get("peopleName").equals("Parul Singh")){
            Query query= firebaseFirestore.collection("Users").document("XxSGgvFuuIeuDlcLFHjEPzv99wf2").collection("Photos").whereEqualTo("photoStatus","Loved").orderBy("dateTime", Query.Direction.DESCENDING);

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }

        if (MainActivity.profile_activity_data.get("peopleName").equals("Sonil Rastogi")){
            Query query= firebaseFirestore.collection("Users").document("BXwbbUN3jRakBIfZbzWMpgeEa0O2").collection("Photos").whereEqualTo("photoStatus","Loved").orderBy("dateTime", Query.Direction.DESCENDING);

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }

        if (MainActivity.profile_activity_data.get("peopleName").equals("Shivam Tyagi")){
            Query query= firebaseFirestore.collection("Users").document("RcjUVqbCoyc0XZtnvn5tT7M10uE2").collection("Photos").whereEqualTo("photoStatus","Loved").orderBy("dateTime", Query.Direction.DESCENDING);

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }

        if (MainActivity.profile_activity_data.get("peopleName").equals("Nikhil Yadav")){
            Query query= firebaseFirestore.collection("Users").document("FLisLrVPG3dpmvdcFcL2yf2dg3j1").collection("Photos").whereEqualTo("photoStatus","Loved").orderBy("dateTime", Query.Direction.DESCENDING);

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }
        if (MainActivity.profile_activity_data.get("peopleName").equals("Ujjwal Singh")){
            Query query= firebaseFirestore.collection("Users").document("DQfGxGysfhY2jD3gVcOxFkzGtc93").collection("Photos").whereEqualTo("photoStatus","Loved").orderBy("dateTime", Query.Direction.DESCENDING);

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }
        if (MainActivity.profile_activity_data.get("peopleName").equals("Shivam Gupta")){
            Query query= firebaseFirestore.collection("Users").document("bQjHXSg7Rhfr2pCLNudZJ9iPuHh1").collection("Photos").whereEqualTo("photoStatus","Loved").orderBy("dateTime", Query.Direction.DESCENDING);

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }

        if (MainActivity.profile_activity_data.get("peopleName").equals("Tapan Yadav")){
            Query query= firebaseFirestore.collection("Users").document("ecnj8C3fJnU240KthDggUOoQmWE2").collection("Photos").whereEqualTo("photoStatus","Loved").orderBy("dateTime", Query.Direction.DESCENDING);

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }
        if (MainActivity.profile_activity_data.get("peopleName").equals("Prashant Bhardwaj")){
            Query query= firebaseFirestore.collection("Users").document("2PgS11EAEPOn60o2u5o3oYKoMjX2").collection("Photos").whereEqualTo("photoStatus","Loved").orderBy("dateTime", Query.Direction.DESCENDING);

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }

        if (MainActivity.profile_activity_data.get("peopleName").equals("Nikita Sharma")){
            Query query= firebaseFirestore.collection("Users").document("Ba558lrXC6NkBXndyltmmVqx5TN2").collection("Photos").whereEqualTo("photoStatus","Loved").orderBy("dateTime", Query.Direction.DESCENDING);

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }
        if (MainActivity.profile_activity_data.get("peopleName").equals("Akanksha Mishra")){
            Query query= firebaseFirestore.collection("Users").document("CXMlLtaH4jSDHeeBE0lh24kv0N93").collection("Photos").whereEqualTo("photoStatus","Loved").orderBy("dateTime", Query.Direction.DESCENDING);

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }
        return view;


    }

    private void setAdapter() {

        arrayList = new ArrayList();
        recyclerViewLovedPhotos.setHasFixedSize(true);
        recyclerViewLovedPhotos.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        lovedPhotoAdapter = new LovedPhotoAdapter(getActivity(), arrayList);
        recyclerViewLovedPhotos.setAdapter(lovedPhotoAdapter);

        for (Map.Entry mapElement : hm.entrySet()) {
            PhotosModel photosModel = new PhotosModel();
            String key = (String) mapElement.getKey();
            HashMap item = (HashMap) mapElement.getValue();
            photosModel.setPhotoCaption((String) item.get("photoCaption"));
            photosModel.setPhotoImage((String) item.get("photoImage"));

            arrayList.add(photosModel);

        }

        lovedPhotoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if ("Akanksha Gupta".equals(MainActivity.profile_activity_data.get("peopleName"))){
            Query query= firebaseFirestore.collection("Users").document("D3HhH91OOjWi1Y891cMfbHXbhHk1").collection("Photos").whereEqualTo("photoStatus","Loved");

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }

        if (MainActivity.profile_activity_data.get("peopleName").equals("Utkarsh Gupta")){
            Query query= firebaseFirestore.collection("Users").document("AFkEytVIQCRLizoiCNZn1KSKiDg2").collection("Photos").whereEqualTo("photoStatus","Loved");

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }

        if (MainActivity.profile_activity_data.get("peopleName").equals("Parul Singh")){
            Query query= firebaseFirestore.collection("Users").document("XxSGgvFuuIeuDlcLFHjEPzv99wf2").collection("Photos").whereEqualTo("photoStatus","Loved");

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }

        if (MainActivity.profile_activity_data.get("peopleName").equals("Sonil Rastogi")){
            Query query= firebaseFirestore.collection("Users").document("BXwbbUN3jRakBIfZbzWMpgeEa0O2").collection("Photos").whereEqualTo("photoStatus","Loved");

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }

        if (MainActivity.profile_activity_data.get("peopleName").equals("Shivam Tyagi")){
            Query query= firebaseFirestore.collection("Users").document("RcjUVqbCoyc0XZtnvn5tT7M10uE2").collection("Photos").whereEqualTo("photoStatus","Loved");

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }

        if (MainActivity.profile_activity_data.get("peopleName").equals("Nikhil Yadav")){
            Query query= firebaseFirestore.collection("Users").document("FLisLrVPG3dpmvdcFcL2yf2dg3j1").collection("Photos").whereEqualTo("photoStatus","Loved");

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }
        if (MainActivity.profile_activity_data.get("peopleName").equals("Ujjwal Singh")){
            Query query= firebaseFirestore.collection("Users").document("DQfGxGysfhY2jD3gVcOxFkzGtc93").collection("Photos").whereEqualTo("photoStatus","Loved");

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }
        if (MainActivity.profile_activity_data.get("peopleName").equals("Shivam Gupta")){
            Query query= firebaseFirestore.collection("Users").document("bQjHXSg7Rhfr2pCLNudZJ9iPuHh1").collection("Photos").whereEqualTo("photoStatus","Loved");

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }

        if (MainActivity.profile_activity_data.get("peopleName").equals("Tapan Yadav")){
            Query query= firebaseFirestore.collection("Users").document("ecnj8C3fJnU240KthDggUOoQmWE2").collection("Photos").whereEqualTo("photoStatus","Loved");

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }
        if (MainActivity.profile_activity_data.get("peopleName").equals("Prashant Bhardwaj")){
            Query query= firebaseFirestore.collection("Users").document("2PgS11EAEPOn60o2u5o3oYKoMjX2").collection("Photos").whereEqualTo("photoStatus","Loved");

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }

        if (MainActivity.profile_activity_data.get("peopleName").equals("Nikita Sharma")){
            Query query= firebaseFirestore.collection("Users").document("Ba558lrXC6NkBXndyltmmVqx5TN2").collection("Photos").whereEqualTo("photoStatus","Loved");

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }
        if (MainActivity.profile_activity_data.get("peopleName").equals("Akanksha Mishra")){
            Query query= firebaseFirestore.collection("Users").document("CXMlLtaH4jSDHeeBE0lh24kv0N93").collection("Photos").whereEqualTo("photoStatus","Loved");

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter();
                }
            });
        }
    }
}
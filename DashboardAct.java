package com.example.project2;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DashboardAct extends AppCompatActivity {

   private RecyclerView recyclerView;
   private FirebaseFirestore firestore;

    private TextView txName;
    private TextView txPhone;

    private FirestoreRecyclerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firestore = FirebaseFirestore.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Query query = firestore.collection("users");

        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query,User.class).build();

         adapter = new FirestoreRecyclerAdapter<User, porductUserHolder>(options) {
            @NonNull

            @Override
            public porductUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent,false);
                return new porductUserHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull DashboardAct.porductUserHolder holder, int position, @NonNull User model) {

                holder.listName.setText(model.getName());
                holder.listPhone.setText(model.getPhone());
            }
        };

         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         recyclerView.setAdapter(adapter);


    }

    private class porductUserHolder extends RecyclerView.ViewHolder {

        private TextView listName;
        private TextView listPhone;

        public porductUserHolder (@NonNull View itemView){
            super(itemView);

            listName = findViewById(R.id.list_name);
            listPhone = findViewById(R.id.list_phone);
        }

    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginAct.class));
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
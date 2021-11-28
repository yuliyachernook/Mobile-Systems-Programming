package chernook.fit.lab9;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import chernook.fit.lab9.adapter.ContactAdapter;
import chernook.fit.lab9.model.Contact;

public class ContactsFragment extends Fragment {

    Context context;
    RecyclerView recyclerView;
    List<Contact> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    public void setList(List<Contact> list) {
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        recyclerView = view.findViewById(R.id.cardContainer);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ContactAdapter adapter = new ContactAdapter(context, list);
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }
}
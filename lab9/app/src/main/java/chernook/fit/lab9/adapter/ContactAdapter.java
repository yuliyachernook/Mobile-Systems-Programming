package chernook.fit.lab9.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import chernook.fit.lab9.BR;
import chernook.fit.lab9.R;
import chernook.fit.lab9.model.Contact;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private final Context context;
    private final List<Contact> contacts;

    public ContactAdapter(Context context, List<Contact> contacts) {
        this.contacts = contacts;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding vdb = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.contact_card,
                parent, false
        );
        return new ContactHolder(vdb);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.contactBinding.setVariable(BR.contact, contact);
        holder.contact = contact;
        holder.contactBinding.getRoot().setTag(contact.id);
        holder.contactBinding.getRoot().setId(position);
        ((Activity)context).registerForContextMenu(holder.contactBinding.getRoot());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ContactHolder extends RecyclerView.ViewHolder {

        ViewDataBinding contactBinding;
        Contact contact;

        public ContactHolder(@NonNull ViewDataBinding contactBinding) {
            super(contactBinding.getRoot());
            this.contactBinding = contactBinding;
        }
    }
}

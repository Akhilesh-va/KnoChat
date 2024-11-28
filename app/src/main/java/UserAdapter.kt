import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.knochat.MainActivity
import com.example.knochat.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text
import java.util.ArrayList

class UserAdapter(userArrayList: ArrayList<User>, mainActivity: MainActivity) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    lateinit var userArrayList: ArrayList<User>
    lateinit var mainActivity: MainActivity


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val userimg : CircleImageView = itemView.findViewById(R.id.userimg)
        val username : TextView = itemView.findViewById(R.id.username)
        val userstatus : TextView = itemView.findViewById(R.id.userstatus)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(mainActivity).inflate(R.layout.user_item , parent , false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return userArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val user = userArrayList[position]
        holder.username.text = user.username
       

    }

}
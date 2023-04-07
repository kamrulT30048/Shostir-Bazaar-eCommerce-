package com.kamrul_hasan.shosti_ecommerce.ui.fragment

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kamrul_hasan.shosti_ecommerce.R
import com.kamrul_hasan.shosti_ecommerce.databinding.FragmentProfileBinding
import com.kamrul_hasan.shosti_ecommerce.model.user.UserProfile
import com.kamrul_hasan.shosti_ecommerce.utils.MyApplication
import com.kamrul_hasan.shosti_ecommerce.utils.packageName

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        binding.apply {

            tvLogout.setOnClickListener {
                //logout user
                Toast.makeText(
                    MyApplication.appContext,
                    "Not applied yet!!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            tvUploadImage.setOnClickListener {
                //add image button
                openDialog()
            }

            btnSave.setOnClickListener {
                val uid = firebaseAuth.currentUser?.uid

                if (uid != null) {
                    uploadProfilePic()

                    Toast.makeText(
                        MyApplication.appContext,
                        "Image upload successfully!!",
                        Toast.LENGTH_SHORT
                    ).show()

                    val user = UserProfile(
                        tietFullName.text.toString(),
                        tietAge.text.toString(),
                        tietAddress.text.toString()
                    )
                    databaseReference.child(uid).setValue(user).addOnCompleteListener { it1 ->
                        if (it1.isSuccessful) {
                            Toast.makeText(
                                MyApplication.appContext,
                                "success",
                                Toast.LENGTH_SHORT
                            ).show()
                            uploadProfilePic()

                        } else {
                            Toast.makeText(
                                MyApplication.appContext,
                                it1.exception.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        MyApplication.appContext,
                        "error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }

    }

    private fun openDialog() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.upload_image_dialog)
        dialog.show()

        val layoutInflater = requireActivity().layoutInflater
        val view = layoutInflater.inflate(R.layout.upload_image_dialog, null)

        val gallery = view.findViewById<Button>(R.id.btn_gallery)
        val camera = view.findViewById<Button>(R.id.btn_camera)

        gallery.setOnClickListener {
            //gallery
            Toast.makeText(
                MyApplication.appContext,
                "Not Applied Yet!!",
                Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        camera.setOnClickListener {
            //camera
            Toast.makeText(
                MyApplication.appContext,
                "Not Applied Yet!!",
                Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

    }

    private fun uploadProfilePic() {
        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.icon_person}")
        binding.civProfileImage.setImageResource(R.drawable.icon_person)
        storageReference =
            FirebaseStorage.getInstance().getReference("Users/" + firebaseAuth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnCompleteListener {
            if (it.isSuccessful) {
                //
                Toast.makeText(
                    MyApplication.appContext,
                    "Image upload successfully!!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //
                Toast.makeText(
                    MyApplication.appContext,
                    it.exception.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}
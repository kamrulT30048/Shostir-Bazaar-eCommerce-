package com.kamrul_hasan.shosti_ecommerce.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.kamrul_hasan.shosti_ecommerce.R
import com.kamrul_hasan.shosti_ecommerce.databinding.FragmentRegisterBinding
import com.kamrul_hasan.shosti_ecommerce.model.user.User
import com.kamrul_hasan.shosti_ecommerce.utils.MyApplication
import com.kamrul_hasan.shosti_ecommerce.viewmodel.ShostiViewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private var user: User? = null
    private lateinit var viewModel: ShostiViewModel
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ShostiViewModel::class.java]

        firebaseAuth = FirebaseAuth.getInstance()

        binding.apply {
            // register
            btnRegister.setOnClickListener {

                var validFlag = true

                if (etInputEmail.text.isEmpty()) {
                    etInputEmail.error = "Required!!"
                    validFlag = false
                } else if (!Patterns.EMAIL_ADDRESS.matcher(etInputEmail.text).matches()) {
                    // invalid email
                    etInputEmail.error = "Invalid Email Address!!"
                    validFlag = false
                }

                if (etInputPassword.text.isEmpty()) {
                    etInputPassword.error = "Required!!"
                    validFlag = false
                } else if (etInputPassword.text.toString().length < 8) {
                    etInputPassword.error = "Minimum 8 character"
                    validFlag = false
                } else if (etInputPasswordConfirm.text.isEmpty() ||
                    etInputPassword.text.toString() != etInputPasswordConfirm.text.toString()
                ) {

                    etInputPassword.error = "Mismatch Password"
                    validFlag = false
                }

                if (validFlag) {

                    firebaseAuth.createUserWithEmailAndPassword(
                        etInputEmail.text.toString(),
                        etInputPassword.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {

                            Toast.makeText(
                                MyApplication.appContext,
                                "Registration Successfully!!",
                                Toast.LENGTH_SHORT
                            ).show()

                            // navigate to login
                            findNavController().navigate(R.id.loginFragment)

                        } else {
                            Toast.makeText(
                                MyApplication.appContext,
                                it.exception.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            // login page navigation
            tvLogin.setOnClickListener {
                findNavController().navigate(R.id.loginFragment)
            }
        }

    }

}
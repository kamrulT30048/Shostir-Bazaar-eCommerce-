package com.kamrul_hasan.shosti_ecommerce.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kamrul_hasan.shosti_ecommerce.R
import com.kamrul_hasan.shosti_ecommerce.databinding.FragmentLoginBinding
import com.kamrul_hasan.shosti_ecommerce.utils.MyApplication
import com.kamrul_hasan.shosti_ecommerce.viewmodel.ShostiViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ShostiViewModel
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ShostiViewModel::class.java]

        firebaseAuth = FirebaseAuth.getInstance()

        binding.apply {

            //Sign In
            btnLogin.setOnClickListener {
                var errorFlag = false

                if (etLoginEmail.text.isEmpty()) {
                    etLoginEmail.error = "Enter Email Address"
                    errorFlag = true
                }
                if (etLoginPassword.text.isEmpty()) {
                    etLoginPassword.error = "Enter Password"
                    errorFlag = true
                }

                if (!errorFlag) {

                    firebaseAuth.signInWithEmailAndPassword(
                        etLoginEmail.text.toString(),
                        etLoginPassword.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {

                            Toast.makeText(
                                MyApplication.appContext,
                                "Login Successful!!",
                                Toast.LENGTH_SHORT
                            ).show()

                            // navigate to login
                            findNavController().navigate(R.id.profileFragment)

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

            // signup
            tvSignup.setOnClickListener {
                findNavController().navigate(R.id.registerFragment)
            }

            // forgot password functionality
            tvForgotPassword.setOnClickListener {
                Toast.makeText(
                    MyApplication.appContext,
                    "Not Applied Yet..!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        // if already loged in
        if (firebaseAuth.currentUser != null) {
            findNavController().navigate(R.id.profileFragment)
        }
    }

}
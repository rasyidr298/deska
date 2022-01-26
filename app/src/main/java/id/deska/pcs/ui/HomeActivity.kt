package id.deska.pcs.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import id.deska.pcs.MyApp.Companion.prefManager
import id.deska.pcs.R
import id.deska.pcs.databinding.ActivityHomeBinding
import id.deska.pcs.ui.login.LoginActivity
import id.deska.pcs.ui.product.ProductFragment
import id.deska.pcs.ui.report.ReportFragment
import id.deska.pcs.ui.transaction.TransactionFragment

class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        navigationChange(ProductFragment())

        with(binding) {

            btnLogout.setOnClickListener {
                prefManager.clearAllPref()
                Intent(this@HomeActivity, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }

            bottomNavigationContainer.setOnNavigationItemSelectedListener {item ->
                when (item.itemId) {
                    R.id.menu_product -> {
                        navigationChange(ProductFragment())
                    }
                    R.id.menu_transaction  -> {
                        navigationChange(TransactionFragment())
                    }
                    R.id.menu_report  -> {
                        navigationChange(ReportFragment())
                    }
                }
                false
            }
        }
    }

    private fun navigationChange(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}
package com.ocbc.stackholders.ui

import android.app.Application
import android.content.Context
import android.util.Base64
import android.util.Log
import com.ocbc.stackholders.di.ApplicationComponent
import com.ocbc.stackholders.di.ApplicationModule
import com.ocbc.stackholders.di.DaggerApplicationComponent
import com.ocbc.stackholders.storage.KeystoreEncrypted
import com.ocbc.stackholders.storage.StackHolderKeystore
import com.ocbc.stackholders.util.Constants.TOKEN
import java.io.IOException
import java.security.*
import javax.crypto.BadPaddingException
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.inject.Inject


class StackHolderApplication : Application() {

    private lateinit var applicationComponent: ApplicationComponent
    @set:Inject
    var stackHolderKeystore: StackHolderKeystore? = null
    private var instance: StackHolderApplication? = null

    fun getApp(context: Context): StackHolderApplication? {
        return context.getApplicationContext() as StackHolderApplication
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        //keystoreEncrypted.initKeyStore()
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build();
        applicationComponent.inject(this);
    }

    fun getStackAppContext(): Context? {
        return instance
    }

    companion object {
        val appData: HashMap<String, String> = HashMap()
        private val SAMPLE_ALIAS: String = "TOEKN"
        var keystoreEncrypted: KeystoreEncrypted = KeystoreEncrypted()
        init {

        }
        private val TAG: String = "Application"
        fun setAppData(key: String, value: String?) {
            if (value != null) {
                appData.put(key, value)
            }
        }
        fun getAppData(key : String) : String? {
            return appData.get(key)
        }

        fun encryptText(key : String): String {
            try {
                val encryptedText: ByteArray = keystoreEncrypted.encryptText(SAMPLE_ALIAS, key)!!
                return Base64.encodeToString(encryptedText, Base64.DEFAULT)
            } catch (e: UnrecoverableEntryException) {
                Log.e(TAG, "onClick() called with: " + e.message, e)
            } catch (e: NoSuchAlgorithmException) {
                Log.e(TAG, "onClick() called with: " + e.message, e)
            } catch (e: NoSuchProviderException) {
                Log.e(TAG, "onClick() called with: " + e.message, e)
            } catch (e: KeyStoreException) {
                Log.e(TAG, "onClick() called with: " + e.message, e)
            } catch (e: IOException) {
                Log.e(TAG, "onClick() called with: " + e.message, e)
            } catch (e: NoSuchPaddingException) {
                Log.e(TAG, "onClick() called with: " + e.message, e)
            } catch (e: InvalidKeyException) {
                Log.e(TAG, "onClick() called with: " + e.message, e)
            } catch (e: InvalidAlgorithmParameterException) {
                e.printStackTrace()
            } catch (e: SignatureException) {
                e.printStackTrace()
            } catch (e: IllegalBlockSizeException) {
                e.printStackTrace()
            } catch (e: BadPaddingException) {
                e.printStackTrace()
            }
            return ""
        }

        private fun decryptText(): String? {
            try {
                return keystoreEncrypted.decryptData(SAMPLE_ALIAS, keystoreEncrypted.getEncryption(), keystoreEncrypted.getIv())
            } catch (e: UnrecoverableEntryException) {
                Log.e(TAG, "decryptData() called with: " + e.message, e)
            } catch (e: NoSuchAlgorithmException) {
                Log.e(TAG, "decryptData() called with: " + e.message, e)
            } catch (e: KeyStoreException) {
                Log.e(TAG, "decryptData() called with: " + e.message, e)
            } catch (e: NoSuchPaddingException) {
                Log.e(TAG, "decryptData() called with: " + e.message, e)
            } catch (e: NoSuchProviderException) {
                Log.e(TAG, "decryptData() called with: " + e.message, e)
            } catch (e: IOException) {
                Log.e(TAG, "decryptData() called with: " + e.message, e)
            } catch (e: InvalidKeyException) {
                Log.e(TAG, "decryptData() called with: " + e.message, e)
            } catch (e: IllegalBlockSizeException) {
                e.printStackTrace()
            } catch (e: BadPaddingException) {
                e.printStackTrace()
            } catch (e: InvalidAlgorithmParameterException) {
                e.printStackTrace()
            }
            return ""
        }
    }

}
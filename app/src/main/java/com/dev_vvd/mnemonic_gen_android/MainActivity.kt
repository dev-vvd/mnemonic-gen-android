package com.dev_vvd.mnemonic_gen_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dev_vvd.mnemonic_gen_android.databinding.ActivityMainBinding
import java.security.SecureRandom
import org.apache.commons.codec.binary.Hex
import org.apache.commons.lang3.StringUtils
import java.math.BigInteger
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)

        val entropy = getEntropy(16)

        val hexEntropy = Hex.encodeHexString(entropy)
        Log.d(tag, "Entropy as Hex: $hexEntropy")

        val sha256Entropy = MessageDigest.getInstance("sha256").digest(entropy)
        Log.d(tag, "SHA256 of entropy: ${Hex.encodeHexString(sha256Entropy)}")

        val checksumBits = entropy.size * 8 / 32
        Log.d(tag, "checksum bits: $checksumBits")

        Log.d(tag, "raw bytes: ${entropy.contentToString()}")

        val bigIntEntropy = BigInteger(1, entropy)
        Log.d(tag, "Entropy as BigInt: $bigIntEntropy")

        val binEntropy = bigIntEntropy.toString(2)
        Log.d(tag, "Entropy as binary: $binEntropy")

        val bigIntSha256Entropy = BigInteger(1, sha256Entropy)
        Log.d(tag, "SHA256 of entropy as BigInt: $bigIntSha256Entropy")

        val binSha256Entropy = bigIntSha256Entropy.toString(2)
        Log.d(tag, "SHA256 of entropy as binary: $binSha256Entropy")

        Log.d(tag, "binSha256Entropy size: ${binSha256Entropy.length}")

        val padding = 256 - binSha256Entropy.length
        Log.d(tag, "padding: $padding")
        val res = StringUtils.leftPad(binSha256Entropy, 256, "0")
        Log.d(tag, "res: $res")
        Log.d(tag, "res len: ${res.length}")
    }

    private fun getEntropy(bytes: Int): ByteArray =
        ByteArray(bytes).apply {
            SecureRandom().nextBytes(this)
        }
}
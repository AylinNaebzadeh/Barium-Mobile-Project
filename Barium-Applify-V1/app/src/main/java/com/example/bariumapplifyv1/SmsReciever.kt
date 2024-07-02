import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log


class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle: Bundle? = intent?.extras
        try {
            if (bundle != null) {
                val pdus = bundle["pdus"] as Array<*>
                for (pdu in pdus) {
                    val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                    val message = smsMessage.messageBody
                    val sender = smsMessage.originatingAddress

                    Log.d("SmsReceiver", "Message received from $sender: $message")

                    // Example response to sender
                    val responseMessage = "Received your message: $message"
                    SmsUtils.sendSmsResponse(context, sender, responseMessage)
                }
            }
        } catch (e: Exception) {
            Log.e("SmsReceiver", "Exception: ${e.message}")
        }
    }

    private fun processReceivedSms(context: Context?, message: String, sender: String?) {
        val password = "your_password"
        if (message.startsWith(password)) {
            val actualMessage = message.removePrefix(password)
            // Further processing of the actualMessage
        } else {
            Log.d("SmsReceiver", "Invalid password in message from $sender")
        }
    }
}

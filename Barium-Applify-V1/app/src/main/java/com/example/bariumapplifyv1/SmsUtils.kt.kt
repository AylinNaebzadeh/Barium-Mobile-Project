import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log

object `SmsUtils.kt` {

    fun sendSmsResponse(context: Context?, phoneNumber: String, message: String) {
        val smsManager = SmsManager.getDefault()
        val sentIntent = PendingIntent.getBroadcast(context, 0, Intent("SMS_SENT"), 0)
        val deliveredIntent = PendingIntent.getBroadcast(context, 0, Intent("SMS_DELIVERED"), 0)

        context?.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (resultCode) {
                    RESULT_OK -> Log.d("SmsUtils", "SMS sent successfully")
                    else -> {
                        Log.d("SmsUtils", "SMS sending failed, retrying...")
                        sendSmsResponse(context, phoneNumber, message)
                    }
                }
            }
        }, IntentFilter("SMS_SENT"))

        context?.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (resultCode) {
                    RESULT_OK -> Log.d("SmsUtils", "SMS delivered successfully")
                    else -> Log.d("SmsUtils", "SMS delivery failed")
                }
            }
        }, IntentFilter("SMS_DELIVERED"))

        smsManager.sendTextMessage(phoneNumber, null, message, sentIntent, deliveredIntent)
    }
}

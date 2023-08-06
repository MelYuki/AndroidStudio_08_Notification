package be.melyuki.demo08_notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    // Plus d'exemple dispo : https://developer.android.com/develop/ui/views/notifications/build-notification

    private lateinit var btnNotif : Button
    private val CHANNEL_ID_DEMO = "MyNotif"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotifChannel()

        btnNotif = findViewById(R.id.btn_main_send_notif)
        btnNotif.setOnClickListener { openNotif() }
    }

    private fun createNotifChannel() {

        // Créer le canal de notif
        val name = "Notif Channel"
        val imp = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(CHANNEL_ID_DEMO, name, imp).apply {
            description = "Ceci est la demo :o"
            vibrationPattern = longArrayOf(200, 50, 200, 10, 200, 10, 200)
        }

        // Enregistrer auprès du systeme Android
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun openNotif() {
        // Intent à lancer depuis la notif
        val notifIntent = Intent(this, OtherActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 131313, notifIntent,PendingIntent.FLAG_IMMUTABLE)

        // Config la notif
        val notifBuilder = Notification.Builder(this, CHANNEL_ID_DEMO).apply {
            // Config du contenu
            setSmallIcon(R.mipmap.ic_launcher)
            setTitle("Demo notif ♥")
            setContentText("Ceci est la demo pour générer un notif 🍔")
            setShowWhen(true)

            // Action sur le click de la notif
            // - Ajout d'un intent
            setContentIntent(pendingIntent)
            // - Suppression auto de celle-ci
            setAutoCancel(true)
        }

        // Création de la notif
        val notification = notifBuilder.build()

        // L'envoi vers Android
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1337, notification)
    }
}